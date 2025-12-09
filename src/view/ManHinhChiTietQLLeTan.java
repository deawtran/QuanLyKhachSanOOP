package view;

import controller.QuanLyGiaoDien;
import model.LeTan;
import javax.swing.*;
import java.awt.*;

public class ManHinhChiTietQLLeTan extends JFrame {
    private QuanLyGiaoDien quanLyGiaoDien;
    private LeTan leTanHienTai;

    public ManHinhChiTietQLLeTan(QuanLyGiaoDien qlgd, LeTan lt) {
        this.quanLyGiaoDien = qlgd;
        this.leTanHienTai = lt;
        initComponents();
        setTitle("Quản lý: " + lt.getTen());
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setLayout(new GridLayout(3, 1, 10, 10));

        JButton btnXemThongTin = new JButton("1. Xem thông tin Lễ Tân ");
        JButton btnCapNhat = new JButton("2. Cập nhật thông tin Lễ Tân");
        JButton btnQuayLai = new JButton("3. Quay Lại");

        add(btnXemThongTin); add(btnCapNhat); add(btnQuayLai);

        btnXemThongTin.addActionListener(e -> quanLyGiaoDien.chuyenManHinh("XEM_CHI_TIET_LE_TAN", leTanHienTai));
        btnCapNhat.addActionListener(e -> quanLyGiaoDien.chuyenManHinh("CAP_NHAT_LE_TAN", leTanHienTai));
        btnQuayLai.addActionListener(e -> quanLyGiaoDien.chuyenManHinh("QL_LE_TAN_MENU"));
        
        pack(); setSize(400, 300);
    }
}