package view;

import controller.QuanLyGiaoDien;
import controller.DichVuThongKe;
import javax.swing.*;
import java.awt.*;

public class ManHinhThongKe extends JFrame {
    private QuanLyGiaoDien quanLyGiaoDien;
    private DichVuThongKe dichVuThongKe;
    private JLabel lblNgay, lblThang, lblNam;

    public ManHinhThongKe(QuanLyGiaoDien qlgd, DichVuThongKe dvtk) {
        this.quanLyGiaoDien = qlgd;
        this.dichVuThongKe = dvtk;
        initComponents();
        setTitle("Thống Kê Doanh Thu");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    private void initComponents() {
        setLayout(new GridLayout(5, 1, 10, 10));
        JLabel lblTitle = new JLabel("Cảnh báo mức doanh thu", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        
        lblNgay = new JLabel("Đang tải...", SwingConstants.CENTER);
        lblThang = new JLabel("Đang tải...", SwingConstants.CENTER);
        lblNam = new JLabel("Đang tải...", SwingConstants.CENTER);
        JButton btnQuayLai = new JButton("Quay Lại Menu QLKS");

        add(lblTitle); add(lblNgay); add(lblThang); add(lblNam); add(btnQuayLai);
        
        // Logic chuyển màn hình
        btnQuayLai.addActionListener(e -> quanLyGiaoDien.chuyenManHinh("QLKS_MENU"));
        
        pack(); setSize(500, 300);
    }
    
    /**
     * Phương thức này được gọi từ QuanLyGiaoDien khi màn hình được hiển thị.
     */
    public void taiThongKe() {
        double[] tyLe = dichVuThongKe.layTyLeDoanhThu();
        
        // Cập nhật các nhãn với định dạng màu sắc
        lblNgay.setText(formatTyLe("Hôm nay so với hôm qua:", tyLe[0]));
        lblThang.setText(formatTyLe("Tháng này so với tháng trước:", tyLe[1]));
        lblNam.setText(formatTyLe("Năm nay so với năm trước:", tyLe[2]));
    }
    
    /**
     * Hàm định dạng chuỗi thành HTML với màu sắc dựa trên giá trị (dương/âm).
     */
    private String formatTyLe(String prefix, double tyLe) {
        String color = tyLe >= 0 ? "green" : "red";
        String dau = tyLe >= 0 ? "+" : "";
        
        // Sử dụng HTML để hiển thị màu sắc và dấu +
        return String.format("<html>%s <b style='color:%s;'>%s%.2f%%</b></html>", prefix, color, dau, tyLe);
    }
}