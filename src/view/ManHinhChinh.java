package view;

import controller.QuanLyGiaoDien;
import javax.swing.*;
import java.awt.*;

public class ManHinhChinh extends JFrame {
    private QuanLyGiaoDien quanLyGiaoDien;

    public ManHinhChinh(QuanLyGiaoDien qlgd) {
        this.quanLyGiaoDien = qlgd;
        initComponents();
        setTitle("Hệ Thống Quản Lý Khách Sạn");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setLayout(new GridLayout(4, 1, 20, 20)); 
        JLabel lblTitle = new JLabel("KHÁCH SẠN PATA", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        
        JButton btnKhachHang = new JButton("1. Khách Hàng");
        JButton btnLeTan = new JButton("2. Lễ Tân");
        JButton btnQuanLy = new JButton("3. Quản Lý Khách Sạn");

        add(lblTitle); add(btnKhachHang); add(btnLeTan); add(btnQuanLy);

        btnKhachHang.addActionListener(e -> quanLyGiaoDien.chuyenManHinh("DANG_NHAP_CHUNG"));
        btnLeTan.addActionListener(e -> quanLyGiaoDien.chuyenManHinh("DANG_NHAP_CHUNG"));
        btnQuanLy.addActionListener(e -> quanLyGiaoDien.chuyenManHinh("DANG_NHAP_CHUNG"));
        
        pack();
        setSize(400, 300);
    }
}