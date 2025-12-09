package view;

import controller.QuanLyGiaoDien;
import model.KhachHang;
import javax.swing.*;
import java.awt.*;

public class ManHinhKhachHang extends JFrame {
    private QuanLyGiaoDien quanLyGiaoDien;
    private KhachHang khachHangHienTai;

    public ManHinhKhachHang(QuanLyGiaoDien qlgd, KhachHang kh) {
        this.quanLyGiaoDien = qlgd;
        this.khachHangHienTai = kh;
        initComponents();
        setTitle("Menu Khách Hàng - Xin chào, " + kh.getTen());
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    private void initComponents() {
        setLayout(new GridLayout(4, 1, 15, 15));
        
        JLabel lblWelcome = new JLabel("Chào mừng, " + khachHangHienTai.getTen() + " - Chọn chức năng:", SwingConstants.CENTER);
        
        JButton btnDatPhong = new JButton("1. Đặt phòng"); // Dẫn đến Menu con (Image 6)
        JButton btnHuyPhong = new JButton("2. Hủy Phòng"); // Dẫn đến Danh sách các phòng có thể hủy (Image 17)
        JButton btnDangXuat = new JButton("3. Đăng Xuất");
        
        add(lblWelcome);
        add(btnDatPhong);
        add(btnHuyPhong);
        add(btnDangXuat);
        
        // 🔥 LOGIC: Đặt phòng -> Menu con
        btnDatPhong.addActionListener(e -> {
            quanLyGiaoDien.chuyenManHinh("DAT_PHONG_MENU", khachHangHienTai);
        }); 

        // 🔥 LOGIC: Hủy Phòng -> Danh sách để hủy (Image 17)
        btnHuyPhong.addActionListener(e -> {
            quanLyGiaoDien.chuyenManHinh("DANH_SACH_PHONG_DE_HUY", khachHangHienTai);
        });
        
        btnDangXuat.addActionListener(e -> {
            quanLyGiaoDien.chuyenManHinh("DANG_NHAP_CHUNG", null);
        });
    }
}