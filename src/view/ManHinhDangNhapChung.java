package view;

import controller.DichVuNguoiDung;
import controller.QuanLyGiaoDien;
import javax.swing.*;
import java.awt.*;

public class ManHinhDangNhapChung extends JFrame {
    private QuanLyGiaoDien quanLyGiaoDien;
    private DichVuNguoiDung dichVu;
    private JTextField txtTaiKhoan;
    private JPasswordField txtMatKhau;

    public ManHinhDangNhapChung(QuanLyGiaoDien qlgd, DichVuNguoiDung dv) {
        this.quanLyGiaoDien = qlgd;
        this.dichVu = dv;
        initComponents();
        setTitle("Đăng Nhập Hệ Thống");
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        setLayout(new GridLayout(4, 2, 10, 10));

        txtTaiKhoan = new JTextField();
        txtMatKhau = new JPasswordField();
        JButton btnDangNhap = new JButton("Đăng Nhập");
        JButton btnDangKy = new JButton("Đăng Ký (Nếu là KH)");
        JButton btnQuayLai = new JButton("Quay Lại");

        add(new JLabel("Tài Khoản:")); add(txtTaiKhoan);
        add(new JLabel("Mật Khẩu:")); add(txtMatKhau);
        add(btnQuayLai); add(btnDangKy); 
        add(new JLabel("")); add(btnDangNhap);
        
        btnDangNhap.addActionListener(e -> xuLyDangNhap());
        btnDangKy.addActionListener(e -> quanLyGiaoDien.chuyenManHinh("DANG_KY_KH")); 
        btnQuayLai.addActionListener(e -> quanLyGiaoDien.chuyenManHinh("CHINH")); 

        pack(); 
        setSize(400, 200);
    }

    private void xuLyDangNhap() {
        String tk = txtTaiKhoan.getText();
        String mk = new String(txtMatKhau.getPassword());
        
        Object user = dichVu.dangNhapChung(tk, mk);
        
        if (user != null) {
            JOptionPane.showMessageDialog(this, "Đăng nhập thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            quanLyGiaoDien.chuyenManHinh("", user); 
        } else {
            JOptionPane.showMessageDialog(this, "Tài khoản hoặc mật khẩu không đúng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}