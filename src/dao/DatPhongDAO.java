package dao;

import java.sql.Connection;
import java.sql.Date; // Lưu ý import này để làm việc với SQL
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.DatabaseConnection;
import model.DatPhong;
import model.KhachHang;
import model.Phong;
import model.LoaiPhong;

public class DatPhongDAO {

    // 1. TẠO ĐƠN ĐẶT PHÒNG MỚI (Lưu xuống SQL)
    public boolean insert(DatPhong dp) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DatabaseConnection.getConnection();
            
            // SQL Insert: Cần khớp với bảng DatPhong trong Database
            // maDatPhong, maKH, maPhong, ngayNhan, ngayTra, tienDatCoc, tongTien, trangThai
            String sql = "INSERT INTO DatPhong (maDatPhong, maKH, maPhong, ngayNhan, ngayTra, tienDatCoc, tongTien, trangThai) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            
            ps = conn.prepareStatement(sql);
            
            ps.setString(1, dp.getMaDatPhong());
            
            // Lấy maKH từ đối tượng KhachHang bên trong DatPhong
            ps.setString(2, dp.getKhachHang().getMaKH()); 
            
            // Lấy maPhong từ đối tượng Phong bên trong DatPhong
            ps.setString(3, dp.getPhong().getSoPhong()); 
            
            // --- QUAN TRỌNG: Chuyển LocalDate -> java.sql.Date ---
            ps.setDate(4, Date.valueOf(dp.getNgayNhan()));
            ps.setDate(5, Date.valueOf(dp.getNgayTra()));
            
            // Tiền đặt cọc: Model chưa có, tạm thời để 0 hoặc 30% tổng tiền tùy logic của bạn
            double tienCoc = 0; 
            ps.setDouble(6, tienCoc);
            
            ps.setDouble(7, dp.getTongTien());
            ps.setString(8, "Đã đặt"); // Set trạng thái mặc định trong DB

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try { if (ps != null) ps.close(); if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }

    // 2. XEM LỊCH SỬ ĐẶT PHÒNG CỦA 1 KHÁCH HÀNG
    public List<DatPhong> getLichSuDatPhong(String maKHCanTim) {
        List<DatPhong> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM DatPhong WHERE maKH = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, maKHCanTim);
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                String maDP = rs.getString("maDatPhong");
                String maPhong = rs.getString("maPhong");
                // Date SQL -> LocalDate Java
                java.time.LocalDate ngayNhan = rs.getDate("ngayNhan").toLocalDate();
                java.time.LocalDate ngayTra = rs.getDate("ngayTra").toLocalDate();
                double tongTien = rs.getDouble("tongTien");
                String trangThai = rs.getString("trangThai");

                // --- TÁI TẠO ĐỐI TƯỢNG CON (Phong & KhachHang) ---
                
                // 1. Tạo Phong giả (chỉ cần mã phòng để hiển thị)
                // Lưu ý: new LoaiPhong("Unknown") cần constructor LoaiPhong(String)
                Phong p = new Phong(maPhong, new LoaiPhong("Unknown"), "Unknown");
                
                // 2. Tạo KhachHang giả (đã có mã từ tham số truyền vào)
                KhachHang kh = new KhachHang();
                kh.setMaKH(maKHCanTim);

                DatPhong dp = new DatPhong(maDP, p, kh, ngayNhan, ngayTra, tongTien);
                dp.setTrangThai(trangThai); // Cập nhật trạng thái đúng từ DB
                
                list.add(dp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
             try { if (rs != null) rs.close(); if (ps != null) ps.close(); if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return list;
    }

    // 3. CẬP NHẬT TRẠNG THÁI ĐƠN ĐẶT PHÒNG (Dùng cho Hủy phòng, Check-in, Check-out)
    // --- Đây là hàm mới thêm vào ---
    public boolean updateTrangThai(String maDP, String trangThaiMoi) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DatabaseConnection.getConnection();
            String sql = "UPDATE DatPhong SET trangThai = ? WHERE maDatPhong = ?";
            
            ps = conn.prepareStatement(sql);
            ps.setString(1, trangThaiMoi);
            ps.setString(2, maDP);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
             try { if (ps != null) ps.close(); if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }
}