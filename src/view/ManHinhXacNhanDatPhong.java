package view;

import controller.QuanLyGiaoDien;
import model.KhachHang;
import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

/** Màn hình Xác nhận Đặt Phòng */
public class ManHinhXacNhanDatPhong extends JFrame {
    private QuanLyGiaoDien quanLyGiaoDien;
    private KhachHang khachHangHienTai;
    private JLabel lblChiTiet;
    
    private double tongTienThanhToan = 0; // Đổi sang double cho chuẩn tiền tệ

    public ManHinhXacNhanDatPhong(QuanLyGiaoDien qlgd) {
        this.quanLyGiaoDien = qlgd;
        initComponents();
        setTitle("Xác Nhận Đặt Phòng");
        setLocationRelativeTo(null);
    }

    public void setKhachHang(KhachHang kh) {
        this.khachHangHienTai = kh;
        hienThiChiTiet();
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTitle = new JLabel("XÁC NHẬN ĐẶT PHÒNG", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        lblChiTiet = new JLabel("", SwingConstants.LEFT);
        lblChiTiet.setVerticalAlignment(SwingConstants.TOP);
        lblChiTiet.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(lblChiTiet);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        JButton btnThanhToan = new JButton("Xác Nhận và Thanh Toán");
        JButton btnQuayLai = new JButton("Quay Lại");

        JPanel pnlSouth = new JPanel(new GridLayout(1, 2, 10, 10));
        pnlSouth.add(btnQuayLai);
        pnlSouth.add(btnThanhToan);
        
        mainPanel.add(pnlSouth, BorderLayout.SOUTH);
        add(mainPanel);
        
        btnThanhToan.addActionListener(e -> {
            if (khachHangHienTai != null) {
                // Lưu tổng tiền vào temp data để màn hình thanh toán dùng
                khachHangHienTai.putTempData("tongTienThanhToan", tongTienThanhToan); 
                quanLyGiaoDien.chuyenManHinh("THANH_TOAN_DAT_PHONG", khachHangHienTai);
            }
        });
        
        btnQuayLai.addActionListener(e -> 
            // Quay lại màn hình tra cứu hoặc danh sách phòng (Tuỳ luồng của bạn)
            quanLyGiaoDien.chuyenManHinh("TRA_CUU_PHONG", khachHangHienTai)
        );
        
        setSize(550, 450);
    }

    private void hienThiChiTiet() {
        if (khachHangHienTai == null) return;
        
        // --- 1. LẤY DỮ LIỆU AN TOÀN (Tránh lỗi ép kiểu) ---
        String checkInStr = (String) khachHangHienTai.getTempData("checkInDate", "N/A");
        String checkOutStr = (String) khachHangHienTai.getTempData("checkOutDate", "N/A");
        
        // Lấy số đêm (Chuyển đổi an toàn từ Object -> String -> Long)
        long soDem = getSafeLong(khachHangHienTai.getTempData("soDem", 0));
        
        // Lấy số phòng
        int soPhong = getSafeInt(khachHangHienTai.getTempData("soPhong", 1));
        
        // Lấy tên loại phòng
        String loaiPhongChon = (String) khachHangHienTai.getTempData("loaiPhongChon", "Phòng Thường");
        
        // Lấy giá phòng (Quan trọng: Xử lý Double)
        double giaPhongMotDem = getSafeDouble(khachHangHienTai.getTempData("giaPhongMotDem", 0.0));
        
        // --- 2. TÍNH TOÁN TỔNG TIỀN ---
        // Nếu số đêm = 0 (do lỗi nhập liệu hoặc test), ta tạm tính là 1 để hiện tiền
        long soDemTinhToan = (soDem > 0) ? soDem : 1; 
        
        tongTienThanhToan = giaPhongMotDem * soDemTinhToan * soPhong; 
        
        // --- 3. HIỂN THỊ DỮ LIỆU ---
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(localeVN);
        
        String tenKH = khachHangHienTai.getTen();
        
        String htmlContent = "<html><body style='font-family: Arial; font-size: 14px;'>"
            + "<h1>Thông Tin Xác Nhận Đặt Phòng</h1><hr>"
            + "<b>Khách hàng:</b> " + tenKH + "<br>"
            + "<b>Loại Phòng:</b> " + loaiPhongChon + "<br>"
            + "<b>Giá/Đêm:</b> " + currencyFormatter.format(giaPhongMotDem) + "<br>"
            + "<b>Số lượng phòng:</b> " + soPhong + "<br>"
            + "<hr>"
            + "<b>Ngày nhận phòng:</b> " + checkInStr + "<br>"
            + "<b>Ngày trả phòng:</b> " + checkOutStr + "<br>"
            + "<b>Tổng số đêm:</b> " + soDemTinhToan + "<br>"
            + "<hr style='border: 1px solid #ccc;'>"
            + "<h3 style='color: red;'>Tổng chi phí: " + currencyFormatter.format(tongTienThanhToan) + "</h3>"
            + "<p><i>Vui lòng kiểm tra kỹ thông tin trước khi thanh toán.</i></p>"
            + "</body></html>";
        
        lblChiTiet.setText(htmlContent);
    }
    
    // --- CÁC HÀM TIỆN ÍCH GIÚP TRÁNH LỖI CRASH ---
    
    private long getSafeLong(Object obj) {
        if (obj == null) return 0;
        try {
            return Long.parseLong(obj.toString());
        } catch (Exception e) { return 0; }
    }
    
    private int getSafeInt(Object obj) {
        if (obj == null) return 0;
        try {
            return Integer.parseInt(obj.toString());
        } catch (Exception e) { return 0; }
    }
    
    private double getSafeDouble(Object obj) {
        if (obj == null) return 0.0;
        try {
            return Double.parseDouble(obj.toString());
        } catch (Exception e) { return 0.0; }
    }
}