package view;

import controller.QuanLyGiaoDien;
import controller.DichVuDatPhong;
import model.KhachHang;
import model.Phong;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class ManHinhThongBaoLoaiPhong extends JFrame {
    private QuanLyGiaoDien quanLyGiaoDien;
    private KhachHang khachHangHienTai;
    
    private JList<Phong> listPhong;
    private DefaultListModel<Phong> listModel;

    public ManHinhThongBaoLoaiPhong(QuanLyGiaoDien qlgd) {
        this.quanLyGiaoDien = qlgd;
        initComponents();
        setTitle("Danh sách phòng phù hợp");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void setKhachHang(KhachHang kh) {
        this.khachHangHienTai = kh;
        hienThiDanhSachPhong();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTitle = new JLabel("CHỌN PHÒNG BẠN MUỐN ĐẶT", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        // List hiển thị phòng
        listModel = new DefaultListModel<>();
        listPhong = new JList<>(listModel);
        listPhong.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listPhong.setFont(new Font("Arial", Font.PLAIN, 14));
        
        // Tạo renderer để hiển thị thông tin phòng đẹp hơn
        listPhong.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Phong) {
                    Phong p = (Phong) value;
                    // Hiển thị: P001 - VIP - 500.000 VNĐ
                    double gia = new dao.PhongDAO().getGiaPhong(p.getSoPhong()); // Lấy giá chính xác
                    setText("Phòng " + p.getSoPhong() + " - Loại: " + p.getLoaiPhong().getTenLoai() + " - Giá: " + String.format("%,.0f", gia) + " VNĐ");
                }
                return this;
            }
        });
        
        mainPanel.add(new JScrollPane(listPhong), BorderLayout.CENTER);

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnBack = new JButton("Quay lại");
        JButton btnChon = new JButton("Đặt Phòng Này");
        
        btnChon.setBackground(new Color(0, 102, 204));
        btnChon.setForeground(Color.WHITE);

        pnlButtons.add(btnBack);
        pnlButtons.add(btnChon);
        mainPanel.add(pnlButtons, BorderLayout.SOUTH);

        add(mainPanel);

        // Sự kiện nút
        btnBack.addActionListener(e -> quanLyGiaoDien.chuyenManHinh("TRA_CUU_PHONG", khachHangHienTai));
        btnChon.addActionListener(e -> xuLyChonPhong());
    }

    private void hienThiDanhSachPhong() {
        listModel.clear();
        if (khachHangHienTai == null) return;

        // Lấy danh sách từ temp data (được lưu ở bước 1)
        Object data = khachHangHienTai.getTempData("listPhongTimDuoc", null);
        
        if (data instanceof List) {
            List<Phong> dsPhong = (List<Phong>) data;
            for (Phong p : dsPhong) {
                listModel.addElement(p); 
            }
        }
    }

    private void xuLyChonPhong() {
        // Lấy phòng người dùng chọn trên giao diện
        Phong phongDuocChon = listPhong.getSelectedValue();

        if (phongDuocChon == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng click chọn một phòng trong danh sách!", "Chưa chọn phòng", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 🔥 LƯU PHÒNG ĐÃ CHỌN VÀO TEMPDATA ĐỂ TRUYỀN SANG BƯỚC SAU
        khachHangHienTai.putTempData("phongChon", phongDuocChon);
        
        // Lưu thêm giá và loại để hiển thị
        double gia = new dao.PhongDAO().getGiaPhong(phongDuocChon.getSoPhong());
        khachHangHienTai.putTempData("giaPhongMotDem", gia);
        khachHangHienTai.putTempData("loaiPhongChon", phongDuocChon.getLoaiPhong().getTenLoai());
        
        // Chuyển sang màn hình Xác Nhận (Image 9)
        quanLyGiaoDien.chuyenManHinh("XAC_NHAN_DAT_PHONG", khachHangHienTai);
    }
}