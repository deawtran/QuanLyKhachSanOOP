package view;

import controller.QuanLyGiaoDien;
import controller.DichVuPhong;
import model.DatPhong;
import model.KhachHang;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ManHinhDanhSachPhongDeHuy extends JFrame {
    private QuanLyGiaoDien quanLyGiaoDien;
    private KhachHang khachHangHienTai;
    private JTable table;
    private DefaultTableModel tableModel;
    private List<DatPhong> danhSachDatPhong; // Lưu list gốc để lấy object

    public ManHinhDanhSachPhongDeHuy(QuanLyGiaoDien qlgd) {
        this.quanLyGiaoDien = qlgd;
        initComponents();
        setTitle("Danh Sách Phòng Đang Đặt");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void setKhachHang(KhachHang kh) {
        this.khachHangHienTai = kh;
        taiDuLieu();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        
        JLabel lblTitle = new JLabel("CHỌN PHÒNG MUỐN HỦY", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblTitle, BorderLayout.NORTH);

        // Bảng dữ liệu
        String[] columnNames = {"Mã ĐP", "Phòng", "Ngày Check-in", "Tổng tiền", "Trạng thái"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel pnlButtons = new JPanel(new FlowLayout());
        JButton btnHuyPhong = new JButton("Hủy Phòng Này");
        JButton btnQuayLai = new JButton("Quay Lại");
        
        btnHuyPhong.setBackground(Color.RED);
        btnHuyPhong.setForeground(Color.WHITE);

        pnlButtons.add(btnQuayLai);
        pnlButtons.add(btnHuyPhong);
        add(pnlButtons, BorderLayout.SOUTH);

        btnQuayLai.addActionListener(e -> quanLyGiaoDien.chuyenManHinh("DAT_PHONG_MENU", khachHangHienTai));
        btnHuyPhong.addActionListener(e -> xuLyChonHuy());
    }

    public void taiDuLieu() {
        if (khachHangHienTai == null) return;
        tableModel.setRowCount(0);
        
        // Gọi Controller lấy danh sách
        DichVuPhong service = quanLyGiaoDien.getDichVuPhong();
        danhSachDatPhong = service.getDanhSachPhongDeHuy(khachHangHienTai);
        
        for (DatPhong dp : danhSachDatPhong) {
            Object[] row = {
                dp.getMaDatPhong(),
                dp.getPhong().getSoPhong(), // Quan trọng: Phải hiển thị số phòng
                dp.getNgayNhan(),
                String.format("%,.0f", dp.getTongTien()),
                dp.getTrangThai()
            };
            tableModel.addRow(row);
        }
    }

    private void xuLyChonHuy() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            // Lấy đối tượng DatPhong từ danh sách gốc dựa trên index dòng
            DatPhong dpDuocChon = danhSachDatPhong.get(selectedRow);
            
            // Chuyển sang màn hình xác nhận
            quanLyGiaoDien.chuyenManHinh("XAC_NHAN_HUY_PHONG", dpDuocChon);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để hủy!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }
}