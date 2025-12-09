package view;

import controller.DichVuDatPhong;
import controller.QuanLyGiaoDien;
import model.KhachHang;
import model.Phong; 
import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter; // Import thêm cái này
import java.util.Locale;

public class ManHinhThanhToanDatPhong extends JFrame {
    private QuanLyGiaoDien quanLyGiaoDien;
    private KhachHang khachHangHienTai;
    
    private double tongTienCanThanhToan = 0; 
    private final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

    private JLabel lblTongTien;
    private JComboBox<String> cmbPhuongThuc;
    private JTextField txtMaGiaoDich;

    public ManHinhThanhToanDatPhong(QuanLyGiaoDien qlgd) {
        this.quanLyGiaoDien = qlgd;
        initComponents();
        setTitle("Khách sạn PATA - Thanh toán đặt phòng");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(550, 450); 
    }
    
    public void setKhachHang(KhachHang kh) {
        this.khachHangHienTai = kh;
        hienThiThongTinThanhToan();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        JLabel lblTitle = new JLabel("THANH TOÁN ĐẶT PHÒNG", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        JPanel pnlCenter = new JPanel(new GridLayout(3, 1, 10, 15));

        // --- Tổng tiền ---
        lblTongTien = new JLabel("Tổng tiền: 0 VNĐ", SwingConstants.CENTER); 
        lblTongTien.setFont(new Font("Arial", Font.BOLD, 20));
        lblTongTien.setForeground(new Color(200, 0, 0));
        
        // --- Phương thức Thanh toán ---
        JPanel pnlPhuongThuc = new JPanel(new GridLayout(2, 2, 10, 5));
        cmbPhuongThuc = new JComboBox<>(new String[]{"Thẻ Tín dụng/Ghi nợ (Online)", "Chuyển khoản Ngân hàng (Online)", "Thanh toán tại Lễ tân (Sau)"});
        txtMaGiaoDich = new JTextField();
        
        pnlPhuongThuc.add(new JLabel("Chọn Phương thức:"));
        pnlPhuongThuc.add(cmbPhuongThuc);
        pnlPhuongThuc.add(new JLabel("Mã giao dịch/Tham chiếu:"));
        pnlPhuongThuc.add(txtMaGiaoDich);
        
        pnlCenter.add(lblTongTien);
        pnlCenter.add(pnlPhuongThuc);
        mainPanel.add(pnlCenter, BorderLayout.CENTER);

        // --- Panel Nút ---
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        JButton btnBack = new JButton("Quay lại");
        JButton btnThanhToan = new JButton("Hoàn tất Thanh toán"); 
        
        btnThanhToan.setBackground(new Color(0, 128, 0));
        btnThanhToan.setForeground(Color.WHITE);
        btnThanhToan.setFont(new Font("Arial", Font.BOLD, 14));
        
        pnlButtons.add(btnBack);
        pnlButtons.add(btnThanhToan);
        mainPanel.add(pnlButtons, BorderLayout.SOUTH);

        add(mainPanel);
        
        // --- Logic Xử lý ---
        btnThanhToan.addActionListener(e -> xuLyThanhToanLuuDatabase());
        btnBack.addActionListener(e -> 
            quanLyGiaoDien.chuyenManHinh("XAC_NHAN_DAT_PHONG", khachHangHienTai)
        );
    }
    
    private void hienThiThongTinThanhToan() {
        if (khachHangHienTai == null) return;
        
        // Lấy dữ liệu tiền từ màn hình trước
        Object tongTienObj = khachHangHienTai.getTempData("tongTienThanhToan", 0.0);
        
        // --- FIX BUG: Chuyển đổi an toàn sang double (Fix lỗi Lỗi Dữ Liệu) ---
        try {
            // Chuyển sang String rồi parse lại Double -> Chấp nhận cả Long, Integer, Double
            tongTienCanThanhToan = Double.parseDouble(tongTienObj.toString());
        } catch (Exception e) {
            tongTienCanThanhToan = 0;
            e.printStackTrace(); // In lỗi ra console để debug
        }
        
        setTitle("Thanh toán đặt phòng - " + khachHangHienTai.getTen());
        
        if (tongTienCanThanhToan > 0) {
            lblTongTien.setText("TỔNG TIỀN CẦN THANH TOÁN: " + currencyFormatter.format(tongTienCanThanhToan));
        } else {
            // Nếu vẫn lỗi, hãy kiểm tra lại ManHinhXacNhanDatPhong có putTempData không
            lblTongTien.setText("TỔNG TIỀN: LỖI DỮ LIỆU! (0 VNĐ)"); 
        }
    }
    
    private void xuLyThanhToanLuuDatabase() {
        // 1. Kiểm tra tiền
        if (tongTienCanThanhToan <= 0) {
             JOptionPane.showMessageDialog(this, "Lỗi: Tổng tiền không hợp lệ. Vui lòng quay lại màn hình trước.", "Lỗi", JOptionPane.ERROR_MESSAGE);
             return;
        }

        // 2. Lấy dữ liệu cần thiết từ TempData
        Phong phongChon = (Phong) khachHangHienTai.getTempData("phongChon", null);
        
        // 3. Xử lý ngày tháng AN TOÀN (Fix lỗi Crash ở Image 605a66)
        LocalDate ngayDen = null;
        LocalDate ngayDi = null;
        
        try {
            String sDen = (String) khachHangHienTai.getTempData("checkInDate", ""); 
            String sDi = (String) khachHangHienTai.getTempData("checkOutDate", "");
            
            // Dùng hàm parse thông minh (tự thử các format)
            ngayDen = parseDateAnToan(sDen);
            ngayDi = parseDateAnToan(sDi);
            
            if (ngayDen == null || ngayDi == null) throw new Exception("Ngày rỗng hoặc sai định dạng");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi định dạng ngày tháng! (" + e.getMessage() + ")", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Kiểm tra lần cuối
        if (phongChon == null) {
            JOptionPane.showMessageDialog(this, "Thiếu thông tin phòng! (Vui lòng chọn phòng ở bước trước)", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 4. GỌI SERVICE ĐỂ LƯU XUỐNG DB
        DichVuDatPhong service = quanLyGiaoDien.getDichVuDatPhong();
        
        boolean ketQua = service.xuLyDatPhong(khachHangHienTai, phongChon, ngayDen, ngayDi);
        
        if (ketQua) {
             JOptionPane.showMessageDialog(this, 
                 "Thanh toán và Đặt phòng thành công!\nMã phòng: " + phongChon.getSoPhong(), 
                 "Thành công", JOptionPane.INFORMATION_MESSAGE);
                 
             quanLyGiaoDien.chuyenManHinh("DAT_PHONG_THANH_CONG", khachHangHienTai);
        } else {
             JOptionPane.showMessageDialog(this, 
                 "Đặt phòng thất bại! Có thể phòng vừa bị người khác đặt.", 
                 "Lỗi hệ thống", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // --- HÀM PHỤ TRỢ: PARSE NGÀY AN TOÀN ---
    private LocalDate parseDateAnToan(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) return null;
        try {
            return LocalDate.parse(dateStr); // Thử yyyy-MM-dd (ISO)
        } catch (Exception e1) {
            try {
                // Nếu lỗi, thử tiếp định dạng Việt Nam (dd/MM/yyyy)
                return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy")); 
            } catch (Exception e2) {
                return null; // Bó tay
            }
        }
    }
}