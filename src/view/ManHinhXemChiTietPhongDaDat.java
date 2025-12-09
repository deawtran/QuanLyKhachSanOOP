package view;

import controller.QuanLyGiaoDien;
import model.DatPhong;
import model.KhachHang;
import javax.swing.*;
import java.awt.*;

public class ManHinhXemChiTietPhongDaDat extends JFrame {
    private QuanLyGiaoDien quanLyGiaoDien;
    private KhachHang khachHangHienTai;
    private DatPhong datPhongHienTai;
    
    // Các Components cần thiết
    private JLabel lblMaDat, lblPhong, lblNgayNhan, lblNgayTra, lblTongTien, lblTrangThai;
    private JButton btnHuyPhong, btnQuayLai;

    public ManHinhXemChiTietPhongDaDat(QuanLyGiaoDien qlgd) {
        this.quanLyGiaoDien = qlgd;
        initComponents();
        setTitle("Chi Tiết Phòng Đã Đặt");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void setKhachHang(KhachHang kh) { this.khachHangHienTai = kh; }
    
    public void setChiTietDatPhong(DatPhong dp) { 
        this.datPhongHienTai = dp;
        hienThiThongTin();
    }
    
    private void initComponents() {
        setLayout(new GridLayout(7, 2, 10, 10));
        
        lblMaDat = new JLabel();
        lblPhong = new JLabel();
        lblNgayNhan = new JLabel();
        lblNgayTra = new JLabel();
        lblTongTien = new JLabel();
        lblTrangThai = new JLabel();
        btnHuyPhong = new JButton("HỦY PHÒNG");
        btnQuayLai = new JButton("Quay Lại");

        add(new JLabel("Mã Đặt:")); add(lblMaDat);
        add(new JLabel("Phòng:")); add(lblPhong);
        add(new JLabel("Check-in:")); add(lblNgayNhan);
        add(new JLabel("Check-out:")); add(lblNgayTra);
        add(new JLabel("Tổng Tiền:")); add(lblTongTien);
        add(new JLabel("Trạng Thái:")); add(lblTrangThai);
        add(btnQuayLai); add(btnHuyPhong);

        // Nút Hủy phòng chỉ nên hoạt động khi trạng thái là CHO_NHAN
        btnHuyPhong.addActionListener(e -> {
            if (datPhongHienTai != null && datPhongHienTai.getTrangThai().equals("CHO_NHAN")) {
                 // Chuyển sang màn hình Xác nhận (Image 18)
                quanLyGiaoDien.chuyenManHinh("XAC_NHAN_HUY_PHONG", datPhongHienTai); 
            } else {
                JOptionPane.showMessageDialog(this, "Chỉ có thể hủy phòng khi trạng thái là 'Chờ Nhận'.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Quay lại màn hình Danh sách phòng đã đặt
        btnQuayLai.addActionListener(e -> quanLyGiaoDien.chuyenManHinh("DANH_SACH_PHONG_DA_DAT", khachHangHienTai));
    }
    
    private void hienThiThongTin() {
        if (datPhongHienTai != null) {
            lblMaDat.setText(datPhongHienTai.getMaDatPhong());
            lblPhong.setText(datPhongHienTai.getPhong().getSoPhong() + " - " + datPhongHienTai.getPhong().getLoaiPhong().getTenLoai());
            lblNgayNhan.setText(datPhongHienTai.getNgayNhan().toString());
            lblNgayTra.setText(datPhongHienTai.getNgayTra().toString());
            lblTongTien.setText(String.format("%,d VNĐ", datPhongHienTai.getTongTien()));
            lblTrangThai.setText(datPhongHienTai.getTrangThai());
        }
    }
}
