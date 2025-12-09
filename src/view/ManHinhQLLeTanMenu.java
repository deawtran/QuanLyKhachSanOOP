package view;

import controller.QuanLyGiaoDien;
import controller.DichVuNguoiDung;
import model.LeTan;
import javax.swing.*;
import java.awt.*;
import java.util.List; // Sửa import: Dùng List thay vì Map

public class ManHinhQLLeTanMenu extends JFrame {
    private QuanLyGiaoDien quanLyGiaoDien;
    private DichVuNguoiDung dichVu;
    private JList<LeTan> listLeTan; 
    private DefaultListModel<LeTan> listModel;

    public ManHinhQLLeTanMenu(QuanLyGiaoDien qlgd, DichVuNguoiDung dv) {
        this.quanLyGiaoDien = qlgd;
        this.dichVu = dv;
        initComponents();
        setTitle("Quản Lý Lễ Tân");
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        listModel = new DefaultListModel<>();
        listLeTan = new JList<>(listModel);
        
        JButton btnXemChiTiet = new JButton("Xem/Cập Nhật Chi Tiết");
        JButton btnQuayLai = new JButton("Quay Lại");

        JPanel pnlSouth = new JPanel(new GridLayout(1, 2, 10, 10));
        pnlSouth.add(btnQuayLai); pnlSouth.add(btnXemChiTiet);
        
        add(new JScrollPane(listLeTan), BorderLayout.CENTER);
        add(pnlSouth, BorderLayout.SOUTH);
        
        btnXemChiTiet.addActionListener(e -> xuLyXemChiTiet());
        btnQuayLai.addActionListener(e -> quanLyGiaoDien.chuyenManHinh("QLKS_MENU"));
        
        pack(); setSize(500, 400);
        
        // Load dữ liệu ngay khi mở màn hình
        taiDuLieuLeTan();
    }
    
    // --- PHẦN SỬA LỖI CHÍNH ---
    public void taiDuLieuLeTan() {
        listModel.clear();
        
        // 1. Gọi hàm lấy danh sách (Hàm này trả về List<LeTan>)
        // Lưu ý: Đảm bảo bạn đã cập nhật DichVuNguoiDung như hướng dẫn bên dưới
        Object data = dichVu.layDanhSachLeTan();
        
        if (data instanceof List) {
            List<LeTan> ds = (List<LeTan>) data;
            
            // 2. Duyệt List và thêm vào Model
            for (LeTan lt : ds) { 
                listModel.addElement(lt); 
            }
        }
        
        if (!listModel.isEmpty()) { listLeTan.setSelectedIndex(0); }
    }
    
    private void xuLyXemChiTiet() {
        LeTan selectedLeTan = listLeTan.getSelectedValue();
        if (selectedLeTan != null) {
            // Chuyển màn hình và truyền đối tượng LeTan đã chọn
            quanLyGiaoDien.chuyenManHinh("CHI_TIET_LE_TAN", selectedLeTan);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một Lễ Tân.", "Lỗi", JOptionPane.WARNING_MESSAGE);
        }
    }
}