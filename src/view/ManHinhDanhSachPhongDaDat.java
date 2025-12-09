package view;



import controller.QuanLyGiaoDien;

import model.DatPhong;

import model.KhachHang;

import controller.DichVuPhong; 

import java.awt.*;

import java.util.List;

import javax.swing.*;

import javax.swing.table.DefaultTableModel;

import java.text.NumberFormat;

import java.util.Locale;

import java.awt.event.MouseAdapter;

import java.awt.event.MouseEvent;



public class ManHinhDanhSachPhongDaDat extends JFrame {

    private QuanLyGiaoDien quanLyGiaoDien;

    private KhachHang khachHangHienTai;

    private JTable tblDatPhong;

    private DefaultTableModel model;

    private List<DatPhong> danhSachDatPhong;



    public ManHinhDanhSachPhongDaDat(QuanLyGiaoDien qlgd) {

        this.quanLyGiaoDien = qlgd;

        initComponents();

        setTitle("Danh Sách Phòng Đã Đặt"); 

        setSize(800, 500); 

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }



    private void initComponents() {

        String[] columnNames = {"Mã Đặt", "Phòng", "Loại", "Check-in", "Check-out", "Tổng tiền", "Trạng thái"};

        model = new DefaultTableModel(columnNames, 0);

        tblDatPhong = new JTable(model);

        tblDatPhong.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        

        JPanel mainPanel = new JPanel(new BorderLayout());

        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        

        mainPanel.add(new JScrollPane(tblDatPhong), BorderLayout.CENTER);



        JButton btnChiTiet = new JButton("Xem Chi Tiết (Image 16)");

        JButton btnQuayLai = new JButton("Quay Lại");



        btnChiTiet.addActionListener(e -> xuLyXemChiTiet());

        btnQuayLai.addActionListener(e -> quanLyGiaoDien.chuyenManHinh("DAT_PHONG_MENU", khachHangHienTai));



        JPanel pnlButtons = new JPanel(new FlowLayout());

        pnlButtons.add(btnChiTiet);

        pnlButtons.add(btnQuayLai);

        mainPanel.add(pnlButtons, BorderLayout.SOUTH);

        add(mainPanel);

        

        // Xử lý Double-Click để xem chi tiết

        tblDatPhong.addMouseListener(new MouseAdapter() {

            @Override

            public void mouseClicked(MouseEvent e) {

                if (e.getClickCount() == 2) {

                    xuLyXemChiTiet();

                }

            }

        });

    }



    public void setKhachHang(KhachHang kh) { this.khachHangHienTai = kh; }



    /**

     * Phương thức tải dữ liệu đã được bảo vệ khỏi lỗi Null.

     */

    public void taiDuLieu() {

        model.setRowCount(0); // Xóa dữ liệu cũ

        if (khachHangHienTai == null) return;

        

        // 1. Kiểm tra Service

        DichVuPhong dichVuPhong = quanLyGiaoDien.getDichVuPhong();

        if (dichVuPhong == null) return; // Nếu service chưa được khởi tạo, thoát

        

        this.danhSachDatPhong = dichVuPhong.getDanhSachPhongDaDat(khachHangHienTai);

        

        if (danhSachDatPhong == null || danhSachDatPhong.isEmpty()) {

            JOptionPane.showMessageDialog(this, "Bạn chưa có phòng nào được đặt. (Image 14)", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

            return;

        }

        

        Locale localeVN = new Locale("vi", "VN");

        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(localeVN);



        for (DatPhong dp : danhSachDatPhong) {

            // 🔥 BẢO VỆ DỮ LIỆU: Đảm bảo các đối tượng lồng nhau không phải là null

            if (dp == null || dp.getPhong() == null || dp.getPhong().getLoaiPhong() == null) continue;



            Object[] row = new Object[]{

                dp.getMaDatPhong(),

                dp.getPhong().getSoPhong(),

                dp.getPhong().getLoaiPhong().getTenLoai(),

                dp.getNgayNhan().toString(),

                dp.getNgayTra().toString(),

                currencyFormatter.format(dp.getTongTien()),

                dp.getTrangThai()

            };

            model.addRow(row);

        }

    }

    

    private void xuLyXemChiTiet() {

        int selectedRow = tblDatPhong.getSelectedRow();

        if (selectedRow == -1 || danhSachDatPhong == null) return;

        

        DatPhong phongChon = danhSachDatPhong.get(selectedRow);

        

        // Chuyển sang màn hình chi tiết (Image 16)

        quanLyGiaoDien.chuyenManHinh("XEM_CHI_TIET_PHONG_DA_DAT", phongChon);

    }

}