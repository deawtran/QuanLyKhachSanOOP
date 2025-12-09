package view;

import controller.QuanLyGiaoDien;
import javax.swing.*;
import java.awt.*;

public class ManHinhLeTanQLKhach extends JFrame {
    private QuanLyGiaoDien quanLyGiaoDien;

    public ManHinhLeTanQLKhach(QuanLyGiaoDien qlgd) {
        this.quanLyGiaoDien = qlgd;
        initComponents();
        setTitle("Lễ Tân - Quản lý Khách Hàng");
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setLayout(new GridLayout(4, 1, 10, 10)); 
        
        JButton btnTraCuu = new JButton("1. Tra cứu thông tin Khách hàng ");
        JButton btnCapNhat = new JButton("2. Cập nhật thông tin Khách hàng ");
        JButton btnDangKy = new JButton("3. Đăng ký tài khoản Khách hàng");
        JButton btnQuayLai = new JButton("4. Quay Lại");
        
        add(btnTraCuu); add(btnCapNhat); add(btnDangKy); add(btnQuayLai);

        btnTraCuu.addActionListener(e -> 
            JOptionPane.showMessageDialog(this, "Chức năng Tra cứu đang phát triển. ")
        );
        
        btnDangKy.addActionListener(e -> 
            quanLyGiaoDien.chuyenManHinh("DANG_KY_KH") // Tái sử dụng Image 3
        );

        btnQuayLai.addActionListener(e -> 
            quanLyGiaoDien.chuyenManHinh("LE_TAN_MENU")
        );
        
        pack();
        setSize(400, 350);
    }
}