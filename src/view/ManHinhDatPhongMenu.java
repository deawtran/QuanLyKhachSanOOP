package view;

import controller.QuanLyGiaoDien;
import model.KhachHang;
import javax.swing.*;
import java.awt.*;

public class ManHinhDatPhongMenu extends JFrame {
    private QuanLyGiaoDien quanLyGiaoDien;
    private KhachHang khachHangHienTai;
    
    public ManHinhDatPhongMenu(QuanLyGiaoDien qlgd, KhachHang kh) {
        this.quanLyGiaoDien = qlgd;
        this.khachHangHienTai = kh;
        initComponents();
        setTitle("Menu Đặt Phòng - " + kh.getTen());
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        setLayout(new GridLayout(4, 1, 15, 15)); 
        
        JLabel lblHeader = new JLabel("Menu Đặt Phòng", SwingConstants.CENTER);
        
        JButton btnTraCuu = new JButton("1. Tra Cứu Thông Tin Phòng");
        JButton btnDSPhongDaDat = new JButton("2. Danh Sách Phòng Đã Đặt"); // NÚT XEM DANH SÁCH ĐÃ ĐẶT
        JButton btnQuayLai = new JButton("Quay Lại Menu Chính");

        // --- XỬ LÝ SỰ KIỆN ---
        btnTraCuu.addActionListener(e -> quanLyGiaoDien.chuyenManHinh("TRA_CUU_PHONG", khachHangHienTai)); 

        // 🔥 Chuyển sang màn hình Danh sách đã đặt (Image 15)
        btnDSPhongDaDat.addActionListener(e -> {
            quanLyGiaoDien.chuyenManHinh("DANH_SACH_PHONG_DA_DAT", khachHangHienTai); 
        });
        
        // Quay lại Menu Chính (ManHinhKhachHang)
        btnQuayLai.addActionListener(e -> quanLyGiaoDien.chuyenManHinh("", khachHangHienTai)); 
        
        add(lblHeader);
        add(btnTraCuu);
        add(btnDSPhongDaDat); 
        add(btnQuayLai);
    }
}
