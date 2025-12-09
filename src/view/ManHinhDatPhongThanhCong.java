package view;

import controller.QuanLyGiaoDien;
import model.KhachHang;
import javax.swing.*;
import java.awt.*;

/** Màn hình Đặt phòng Thành công (Image 11) - Hoàn thiện cuối cùng */
public class ManHinhDatPhongThanhCong extends JFrame {
    private QuanLyGiaoDien quanLyGiaoDien;
    private KhachHang khachHangHienTai;

    public ManHinhDatPhongThanhCong(QuanLyGiaoDien qlgd) {
        this.quanLyGiaoDien = qlgd;
        initComponents();
        setTitle("Khách sạn PATA - Đặt phòng thành công");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(550, 400);
    }

    /**
     * Phương thức nhận đối tượng Khách hàng và xóa dữ liệu tạm thời (Temp Data).
     */
    public void setKhachHang(KhachHang kh) {
        this.khachHangHienTai = kh;
        // 🔥 XÓA DỮ LIỆU TẠM THỜI
        if (kh != null) {
            kh.putTempData("checkInDate", null); 
            kh.putTempData("tongTienThanhToan", null);
            setTitle("Đặt phòng thành công cho KH: " + kh.getTen());
        }
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel lblIcon = new JLabel("✅", SwingConstants.CENTER);
        lblIcon.setFont(new Font("Arial", Font.BOLD, 50));
        lblIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblTitle = new JLabel("ĐẶT PHÒNG THÀNH CÔNG!", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(new Color(34, 139, 34));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // --- Nội dung Chi tiết và Mã Booking ---
        JLabel lblMaBooking = new JLabel("Mã Booking của bạn là: BOOKING123456", SwingConstants.CENTER);
        lblMaBooking.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Giữ lại JTextArea để hiển thị thông tin hướng dẫn
        JTextArea txtaThongTin = new JTextArea("Chi tiết đã được gửi qua email. Vui lòng mang theo CMND/CCCD khi đến nhận phòng.");
        txtaThongTin.setEditable(false);
        txtaThongTin.setFont(new Font("Arial", Font.PLAIN, 14));
        txtaThongTin.setBackground(mainPanel.getBackground());
        txtaThongTin.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel pnlText = new JPanel(new BorderLayout());
        pnlText.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        pnlText.add(txtaThongTin, BorderLayout.CENTER);
        
        // --- Nút Chức năng ---
        JButton btnXemDon = new JButton("Xem Đơn Đặt Phòng");
        JButton btnVeMenu = new JButton("Quay về Menu Chính (Image 5)"); 

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        pnlButtons.add(btnXemDon);
        pnlButtons.add(btnVeMenu);

        // --- Xây dựng Main Panel ---
        mainPanel.add(lblIcon);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(lblTitle);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(lblMaBooking);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(pnlText);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(pnlButtons);

        add(mainPanel);
        
        // --- Logic Chuyển Màn Hình ---
        
        // 🔥 1. Xem Đơn Đặt Phòng (Chuyển sang Danh sách phòng đã đặt - Image 15)
        btnXemDon.addActionListener(e -> {
            quanLyGiaoDien.chuyenManHinh("DANH_SACH_PHONG_DA_DAT", khachHangHienTai); 
            dispose();
        });
        
        // 2. Quay về Menu Chính của Khách hàng (Image 5)
        btnVeMenu.addActionListener(e -> {
            // Dùng key rỗng trong QLGD để gọi logic chuyển về ManHinhKhachHang (Image 5)
            quanLyGiaoDien.chuyenManHinh("", khachHangHienTai); 
            dispose();
        });
        
        pack(); setSize(550, 400);
    }
}