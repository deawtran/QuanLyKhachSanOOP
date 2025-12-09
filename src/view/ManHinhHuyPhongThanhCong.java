package view;

import controller.QuanLyGiaoDien;
import model.KhachHang;
import java.awt.*;
import javax.swing.*;

/** Màn hình Hoàn thành hủy phòng */
public class ManHinhHuyPhongThanhCong extends JFrame {
    private QuanLyGiaoDien quanLyGiaoDien;
    private KhachHang khachHangHienTai;

    public ManHinhHuyPhongThanhCong(QuanLyGiaoDien quanLyGiaoDien) {
        this.quanLyGiaoDien = quanLyGiaoDien;
        setTitle("Hủy Phòng Thành Công");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        setLayout(new BorderLayout(10, 10));

        // Icon hoặc thông báo đẹp hơn chút
        JLabel lblThongBao = new JLabel("<html><div style='text-align: center;'>"
                + "<h2 style='color: blue;'>Hủy Phòng Thành Công!</h2>"
                + "Phòng đã được hủy khỏi hệ thống.<br>"
                + "Trạng thái phòng đã trở về 'Trống'."
                + "</div></html>", SwingConstants.CENTER);
        
        JButton btnVeMenu = new JButton("Về Menu Khách Hàng");
        
        // Style nút
        btnVeMenu.setFont(new Font("Arial", Font.BOLD, 14));
        btnVeMenu.setBackground(new Color(0, 153, 76)); // Màu xanh lá
        btnVeMenu.setForeground(Color.WHITE);

        // 🔥 SỬA LỖI Ở ĐÂY: Dùng chuỗi rỗng "" để về Dashboard Khách Hàng
        btnVeMenu.addActionListener(e -> {
            if (khachHangHienTai != null) {
                quanLyGiaoDien.chuyenManHinh("", khachHangHienTai); 
            } else {
                // Phòng trường hợp mất session, quay về đăng nhập
                quanLyGiaoDien.chuyenManHinh("DANG_NHAP_CHUNG");
            }
        });

        add(lblThongBao, BorderLayout.CENTER);
        
        JPanel pnlSouth = new JPanel(new FlowLayout());
        pnlSouth.add(btnVeMenu);
        add(pnlSouth, BorderLayout.SOUTH);
    }
    
    public void setKhachHang(KhachHang kh) {
        this.khachHangHienTai = kh;
    }
}