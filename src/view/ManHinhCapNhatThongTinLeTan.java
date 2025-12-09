package view;

import controller.QuanLyGiaoDien;
import controller.DichVuNguoiDung;
import model.LeTan;
import javax.swing.*;
import java.awt.*;

public class ManHinhCapNhatThongTinLeTan extends JFrame {
    private QuanLyGiaoDien quanLyGiaoDien;
    private DichVuNguoiDung dichVu;
    private LeTan leTanHienTai;
    
    private JTextField txtTen, txtCCCD, txtLuongGio;

    public ManHinhCapNhatThongTinLeTan(QuanLyGiaoDien qlgd, DichVuNguoiDung dv, LeTan lt) {
        this.quanLyGiaoDien = qlgd;
        this.dichVu = dv;
        this.leTanHienTai = lt;
        initComponents();
        taiThongTinHienTai();
        setTitle("Cập Nhật Thông Tin Lễ Tân: " + lt.getTen());
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setLayout(new GridLayout(5, 2, 10, 10));
        txtTen = new JTextField(); txtCCCD = new JTextField(); txtLuongGio = new JTextField();
        JButton btnLuu = new JButton("Lưu");
        JButton btnHuy = new JButton("Hủy");

        add(new JLabel("Tên Lễ Tân:")); add(txtTen);
        add(new JLabel("Số CCCD:")); add(txtCCCD);
        add(new JLabel("Lương (theo giờ):")); add(txtLuongGio);
        add(new JLabel("")); add(new JLabel(""));
        add(btnHuy); add(btnLuu);
        
        btnLuu.addActionListener(e -> xuLyCapNhat());
        btnHuy.addActionListener(e -> quanLyGiaoDien.chuyenManHinh("CHI_TIET_LE_TAN", leTanHienTai)); 
        
        pack(); setSize(450, 250);
    }
    
    private void taiThongTinHienTai() {
        txtTen.setText(leTanHienTai.getTen());
        txtCCCD.setText(leTanHienTai.getSoCCCD());
        txtLuongGio.setText(String.valueOf(leTanHienTai.getLuongTheoGio()));
    }
    
    private void xuLyCapNhat() {
        try {
            String tenMoi = txtTen.getText();
            String cccdMoi = txtCCCD.getText();
            double luongMoi = Double.parseDouble(txtLuongGio.getText());

            dichVu.capNhatThongTinLeTan(leTanHienTai, tenMoi, cccdMoi, luongMoi);
            
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            quanLyGiaoDien.chuyenManHinh("QL_LE_TAN_MENU");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Lương theo giờ phải là số hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}