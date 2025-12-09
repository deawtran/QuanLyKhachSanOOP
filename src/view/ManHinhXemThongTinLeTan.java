package view;

import controller.QuanLyGiaoDien;
import model.LeTan;
import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

public class ManHinhXemThongTinLeTan extends JFrame {
    private QuanLyGiaoDien quanLyGiaoDien;
    private LeTan leTanHienTai;
    private JLabel lblChiTiet;

    public ManHinhXemThongTinLeTan(QuanLyGiaoDien qlgd, LeTan lt) {
        this.quanLyGiaoDien = qlgd;
        this.leTanHienTai = lt;
        initComponents();
        hienThiChiTiet();
        setTitle("Xem Thông Tin Lễ Tân: " + lt.getTen());
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        lblChiTiet = new JLabel("", SwingConstants.LEFT);
        lblChiTiet.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JButton btnQuayLai = new JButton("Quay Lại");

        add(lblChiTiet, BorderLayout.CENTER);
        add(btnQuayLai, BorderLayout.SOUTH);
        
        btnQuayLai.addActionListener(e -> quanLyGiaoDien.chuyenManHinh("CHI_TIET_LE_TAN", leTanHienTai));
        
        pack(); setSize(500, 350);
    }
    
    private void hienThiChiTiet() {
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(localeVN);

        String htmlContent = "<html>"
            + "<h1>Thông Tin Chi Tiết Lễ Tân</h1><hr>"
            + "<b>Tên Lễ Tân:</b> " + leTanHienTai.getTen() + "<br>"
            + "<b>Số CCCD:</b> " + leTanHienTai.getSoCCCD() + "<br>"
            + "<hr>"
            + "<b style='color:blue;'>Lương theo giờ:</b> " + currencyFormatter.format(leTanHienTai.getLuongTheoGio()) + "<br>"
            + "<b style='color:red;'>Tổng lương (từ đầu tháng):</b> " + currencyFormatter.format(leTanHienTai.getLuong())
            + "</html>";
        lblChiTiet.setText(htmlContent);
    }
}