package view;

import controller.QuanLyGiaoDien;
import model.LeTan;
import javax.swing.*;
import java.awt.*;

public class ManHinhLeTanMenu extends JFrame {
    private QuanLyGiaoDien quanLyGiaoDien;
    private LeTan leTanHienTai;

    public ManHinhLeTanMenu(QuanLyGiaoDien qlgd) {
        this.quanLyGiaoDien = qlgd;
        initComponents();
        setTitle("Lễ Tân - Menu Chính");
        setLocationRelativeTo(null);
    }
    
    public void setLeTan(LeTan lt) {
        this.leTanHienTai = lt;
        setTitle("Lễ Tân: " + lt.getTen() + " - Menu Chính");
    }

    private void initComponents() {
        setLayout(new GridLayout(5, 1, 10, 10)); 
        
        JButton btnQLKhachHang = new JButton("1. Quản lý Khách Hàng (Image 14)");
        JButton btnQLPhong = new JButton("2. Quản lý Phòng");
        JButton btnCheckIn = new JButton("3. Check-in");
        JButton btnCheckOut = new JButton("4. Check-out");
        JButton btnDangXuat = new JButton("5. Đăng Xuất");
        
        add(btnQLKhachHang); add(btnQLPhong); add(btnCheckIn); add(btnCheckOut); add(btnDangXuat);

        btnQLKhachHang.addActionListener(e -> 
            quanLyGiaoDien.chuyenManHinh("LE_TAN_QL_KHACH_HANG_MENU")
        );
        
        btnDangXuat.addActionListener(e -> quanLyGiaoDien.chuyenManHinh("CHINH"));
        
        pack();
        setSize(400, 400);
    }
}