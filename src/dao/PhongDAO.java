package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.DatabaseConnection;
import model.Phong;
import model.LoaiPhong; 

public class PhongDAO {

    // 1. LẤY DANH SÁCH TẤT CẢ CÁC PHÒNG (Hiển thị cho quản lý/lễ tân)
    public List<Phong> getAllPhong() {
        List<Phong> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM Phong"; 
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                String maPhong = rs.getString("maPhong");
                String tenLoai = rs.getString("loaiPhong"); 
                String trangThai = rs.getString("tinhTrang");
                
                // Tạo đối tượng LoaiPhong từ tên lấy trong DB
                LoaiPhong loaiPhongObj = new LoaiPhong(tenLoai); 

                Phong p = new Phong(maPhong, loaiPhongObj, trangThai);
                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); if (ps != null) ps.close(); if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return list;
    }

    // 2. TÌM TẤT CẢ PHÒNG TRỐNG (Dùng cho chức năng xem danh sách)
    public List<Phong> getPhongTrong() {
        List<Phong> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM Phong WHERE tinhTrang = 'Trống'";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                String maPhong = rs.getString("maPhong");
                String tenLoai = rs.getString("loaiPhong");
                String trangThai = rs.getString("tinhTrang");

                LoaiPhong loaiPhongObj = new LoaiPhong(tenLoai);
                Phong p = new Phong(maPhong, loaiPhongObj, trangThai);
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
             try { if (rs != null) rs.close(); if (ps != null) ps.close(); if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return list;
    }
    
    // 3. CẬP NHẬT TRẠNG THÁI PHÒNG (Dùng sau khi Đặt phòng/Check-out)
    public boolean updateTrangThai(String maPhong, String trangThaiMoi) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DatabaseConnection.getConnection();
            String sql = "UPDATE Phong SET tinhTrang = ? WHERE maPhong = ?";
            
            ps = conn.prepareStatement(sql);
            ps.setString(1, trangThaiMoi);
            ps.setString(2, maPhong);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
             try { if (ps != null) ps.close(); if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }
    
    // 4. LẤY GIÁ TIỀN CỦA PHÒNG
    public double getGiaPhong(String maPhong) {
        double gia = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
             conn = DatabaseConnection.getConnection();
             String sql = "SELECT giaPhong FROM Phong WHERE maPhong = ?";
             ps = conn.prepareStatement(sql);
             ps.setString(1, maPhong);
             rs = ps.executeQuery();
             if(rs.next()){
                 gia = rs.getDouble("giaPhong");
             }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
             try { if (rs != null) rs.close(); if (ps != null) ps.close(); if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return gia;
    }

    // 5. 🔥 HÀM MỚI: TÌM 1 PHÒNG TRỐNG THEO LOẠI (Hỗ trợ tự động xếp phòng)
    public Phong getPhongTrongDauTienTheoLoai(String loaiPhongCanTim) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Phong p = null;
        try {
            conn = DatabaseConnection.getConnection();
            // Lấy 1 phòng trống đầu tiên khớp với loại phòng
            // Dùng LIMIT 1 để lấy duy nhất 1 phòng
            String sql = "SELECT * FROM Phong WHERE loaiPhong LIKE ? AND tinhTrang = 'Trống' LIMIT 1";
            
            ps = conn.prepareStatement(sql);
            // Dùng % để tìm kiếm linh hoạt (VD: 'VIP' tìm được cả 'Phòng VIP')
            ps.setString(1, "%" + loaiPhongCanTim + "%"); 
            
            rs = ps.executeQuery();
            if (rs.next()) {
                String maPhong = rs.getString("maPhong");
                String tenLoai = rs.getString("loaiPhong");
                String trangThai = rs.getString("tinhTrang");
                
                LoaiPhong lp = new LoaiPhong(tenLoai); 
                p = new Phong(maPhong, lp, trangThai); 
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); if (ps != null) ps.close(); if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return p;
    }
}