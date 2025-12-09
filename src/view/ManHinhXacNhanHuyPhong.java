package view;

import controller.QuanLyGiaoDien;
import model.DatPhong;
import model.KhachHang;
import javax.swing.*;
import java.awt.*;

public class ManHinhXacNhanHuyPhong extends JFrame {
    private QuanLyGiaoDien quanLyGiaoDien;
    private KhachHang khachHangHienTai;
    private DatPhong datPhongDeHuy;
    
    private JLabel lblThongTin;
    private JButton btnXacNhan, btnHuy;

    public ManHinhXacNhanHuyPhong(QuanLyGiaoDien qlgd) {
        this.quanLyGiaoDien = qlgd;
        initComponents();
        setTitle("Xác Nhận Hủy Phòng");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void setKhachHang(KhachHang kh) { this.khachHangHienTai = kh; }
    
    public void setDatPhongDeHuy(DatPhong dp) { 
        this.datPhongDeHuy = dp;
        if (dp != null) {
            lblThongTin.setText("<html><center>Xác nhận hủy đặt phòng <b>" + dp.getMaDatPhong() + "</b>?<br>(Phòng " + dp.getPhong().getSoPhong() + ")</center></html>");
        }
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        
        lblThongTin = new JLabel("", SwingConstants.CENTER);
        JPanel pnlButtons = new JPanel(new FlowLayout());
        btnXacNhan = new JButton("Xác nhận Hủy");
        btnHuy = new JButton("Hủy bỏ");
        
        pnlButtons.add(btnXacNhan);
        pnlButtons.add(btnHuy);
        
        add(lblThongTin, BorderLayout.CENTER);
        add(pnlButtons, BorderLayout.SOUTH);

        // 🔥 LOGIC: THỰC HIỆN HỦY PHÒNG (ĐÃ SỬA)
        btnXacNhan.addActionListener(e -> {
            if (datPhongDeHuy != null) {
                // SỬA LỖI Ở ĐÂY: Lấy mã String truyền vào thay vì truyền cả object
                boolean success = quanLyGiaoDien.getDichVuPhong().huyPhong(datPhongDeHuy.getMaDatPhong());
                
                if (success) {
                    JOptionPane.showMessageDialog(this, "Hủy phòng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    // Chuyển sang màn hình thông báo thành công
                    quanLyGiaoDien.chuyenManHinh("HUY_PHONG_THANH_CONG", khachHangHienTai); 
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể hủy phòng (Có thể trạng thái không phải 'Chờ Nhận').", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Quay lại màn hình Danh sách phòng để hủy
        btnHuy.addActionListener(e -> quanLyGiaoDien.chuyenManHinh("DANH_SACH_PHONG_DE_HUY", khachHangHienTai));
    }
}