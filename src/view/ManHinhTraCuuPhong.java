package view;

import controller.QuanLyGiaoDien;
import model.KhachHang;
import model.Phong; // Import Phong
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class ManHinhTraCuuPhong extends JFrame {
    private QuanLyGiaoDien quanLyGiaoDien;
    private KhachHang khachHangHienTai;
    
    private JTextField txtNgayCheckIn, txtNgayCheckOut;
    private JComboBox<String> cboLoaiPhong; 
    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public ManHinhTraCuuPhong(QuanLyGiaoDien qlgd, KhachHang kh) {
        this.quanLyGiaoDien = qlgd;
        this.khachHangHienTai = kh;
        initComponents();
        setTitle("Khách sạn PATA - Đặt phòng nhanh");
        setSize(500, 480); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel lblTitle = new JLabel("ĐẶT PHÒNG NHANH", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        JPanel pnlInputFields = new JPanel(new GridLayout(4, 2, 10, 15));

        txtNgayCheckIn = new JTextField(LocalDate.now().plusDays(1).format(DATE_FORMAT));
        txtNgayCheckOut = new JTextField(LocalDate.now().plusDays(3).format(DATE_FORMAT));
        
        String[] loaiPhong = {"Thường", "VIP", "VVIP"};
        cboLoaiPhong = new JComboBox<>(loaiPhong);

        pnlInputFields.add(new JLabel("Ngày check-in (YYYY-MM-DD):")); pnlInputFields.add(txtNgayCheckIn);
        pnlInputFields.add(new JLabel("Ngày check-out (YYYY-MM-DD):")); pnlInputFields.add(txtNgayCheckOut);
        pnlInputFields.add(new JLabel("Chọn Loại Phòng:")); pnlInputFields.add(cboLoaiPhong);
        // Bỏ bớt các trường không cần thiết để tập trung vào lỗi
        
        mainPanel.add(pnlInputFields, BorderLayout.CENTER);

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
        JButton btnQuayLai = new JButton("Quay lại");
        JButton btnNext = new JButton("Tìm & Đặt Ngay"); 
        
        pnlButtons.add(btnQuayLai); pnlButtons.add(btnNext);
        mainPanel.add(pnlButtons, BorderLayout.SOUTH);

        add(mainPanel);
        
        btnNext.addActionListener(e -> xuLyTuDongTimPhong());
        btnQuayLai.addActionListener(e -> quanLyGiaoDien.chuyenManHinh("DAT_PHONG_MENU", khachHangHienTai));
    }

    private void xuLyTuDongTimPhong() {
        String checkInStr = txtNgayCheckIn.getText();
        String checkOutStr = txtNgayCheckOut.getText();
        String loaiMuonDat = (String) cboLoaiPhong.getSelectedItem();
        
        try {
            LocalDate checkIn = LocalDate.parse(checkInStr, DATE_FORMAT);
            LocalDate checkOut = LocalDate.parse(checkOutStr, DATE_FORMAT);
            
            if (!checkIn.isBefore(checkOut)) {
                JOptionPane.showMessageDialog(this, "Ngày check-out phải sau ngày check-in.", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // --- 1. GỌI DAO ĐỂ TÌM PHÒNG TRỐNG ---
            // (Hàm này phải tồn tại trong PhongDAO như hướng dẫn trước)
            Phong phongTimDuoc = new dao.PhongDAO().getPhongTrongDauTienTheoLoai(loaiMuonDat);
            
            if (phongTimDuoc == null) {
                JOptionPane.showMessageDialog(this, 
                    "Rất tiếc! Không tìm thấy phòng trống loại '" + loaiMuonDat + "'.\n(Hãy kiểm tra lại Database hoặc chọn loại khác)", 
                    "Hết phòng", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // --- 2. LƯU DỮ LIỆU QUAN TRỌNG VÀO TEMP ---
            long soDem = ChronoUnit.DAYS.between(checkIn, checkOut);
            double giaPhong = new dao.PhongDAO().getGiaPhong(phongTimDuoc.getSoPhong());
            
            khachHangHienTai.putTempData("checkInDate", checkInStr);
            khachHangHienTai.putTempData("checkOutDate", checkOutStr);
            khachHangHienTai.putTempData("soDem", soDem); 
            khachHangHienTai.putTempData("soPhong", 1);
            
            // 🔥 LƯU PHÒNG TÌM ĐƯỢC (Không được để null)
            khachHangHienTai.putTempData("phongChon", phongTimDuoc); 
            khachHangHienTai.putTempData("loaiPhongChon", loaiMuonDat);
            khachHangHienTai.putTempData("giaPhongMotDem", giaPhong);
            
            // --- 3. CHUYỂN MÀN HÌNH ---
            quanLyGiaoDien.chuyenManHinh("XAC_NHAN_DAT_PHONG", khachHangHienTai); 

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi Hệ Thống", JOptionPane.ERROR_MESSAGE);
        }
    }
}