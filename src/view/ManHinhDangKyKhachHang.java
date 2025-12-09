package view;

import controller.DichVuNguoiDung;
import controller.QuanLyGiaoDien;
import javax.swing.*;
import java.awt.*;

public class ManHinhDangKyKhachHang extends JFrame {
    private QuanLyGiaoDien quanLyGiaoDien;
    private DichVuNguoiDung dichVu;
    
    // Thêm txtSDT và txtDiaChi
    private JTextField txtTen, txtCCCD, txtTaiKhoan, txtSDT, txtDiaChi;
    private JPasswordField txtMatKhau;

    public ManHinhDangKyKhachHang(QuanLyGiaoDien qlgd, DichVuNguoiDung dv) {
        this.quanLyGiaoDien = qlgd;
        this.dichVu = dv;
        initComponents();
        setTitle("Đăng Ký Khách Hàng");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Đóng form này không tắt app
    }
    
    private void initComponents() {
        // Tăng số dòng lên 8 để chứa thêm 2 trường mới
        setLayout(new GridLayout(8, 2, 10, 10));
        
        txtTen = new JTextField(); 
        txtCCCD = new JTextField();
        txtSDT = new JTextField();      // Mới
        txtDiaChi = new JTextField();   // Mới
        txtTaiKhoan = new JTextField(); 
        txtMatKhau = new JPasswordField();
        
        JButton btnDangKy = new JButton("Đăng Ký");
        JButton btnQuayLai = new JButton("Quay Lại");

        add(new JLabel("Họ và Tên:")); add(txtTen);
        add(new JLabel("Số CCCD:")); add(txtCCCD);
        add(new JLabel("Số Điện Thoại:")); add(txtSDT);    // Mới
        add(new JLabel("Địa Chỉ:")); add(txtDiaChi);       // Mới
        add(new JLabel("Tên Đăng Nhập:")); add(txtTaiKhoan);
        add(new JLabel("Mật Khẩu:")); add(txtMatKhau);
        
        add(btnQuayLai); add(btnDangKy);
        
        btnDangKy.addActionListener(e -> xuLyDangKy());
        btnQuayLai.addActionListener(e -> quanLyGiaoDien.chuyenManHinh("DANG_NHAP_CHUNG")); 

        // Tăng chiều cao form lên một chút cho đẹp
        pack(); 
        setSize(400, 350); 
    }

    private void xuLyDangKy() {
        String ten = txtTen.getText().trim();
        String cccd = txtCCCD.getText().trim();
        String sdt = txtSDT.getText().trim();       // Lấy dữ liệu mới
        String diaChi = txtDiaChi.getText().trim(); // Lấy dữ liệu mới
        String tk = txtTaiKhoan.getText().trim();
        String mk = new String(txtMatKhau.getPassword());
        
        // Kiểm tra rỗng cơ bản
        if(ten.isEmpty() || cccd.isEmpty() || sdt.isEmpty() || tk.isEmpty() || mk.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Gọi hàm dangKyKhachHang với ĐỦ 6 THAM SỐ khớp với DichVuNguoiDung mới
        if (dichVu.dangKyKhachHang(ten, cccd, tk, mk, sdt, diaChi)) {
            JOptionPane.showMessageDialog(this, "Đăng ký thành công! Vui lòng đăng nhập.", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            quanLyGiaoDien.chuyenManHinh("DANG_NHAP_CHUNG");
        } else {
            JOptionPane.showMessageDialog(this, "Tài khoản đã tồn tại hoặc lỗi hệ thống.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}