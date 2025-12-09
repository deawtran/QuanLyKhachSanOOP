package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.DatabaseConnection;
import model.KhachHang;

public class KhachHangDAO {

    // 1. THÊM KHÁCH HÀNG MỚI (Dùng cho chức năng Đăng Ký)
    public boolean addKhachHang(KhachHang kh) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DatabaseConnection.getConnection();
            
            // Câu lệnh SQL khớp với thứ tự cột trong Database KhachHang
            // Các cột: maKH, hoTen, soDienThoai, CCCD, diaChi, tenDangNhap
            String sql = "INSERT INTO KhachHang (maKH, hoTen, soDienThoai, CCCD, diaChi, tenDangNhap) VALUES (?, ?, ?, ?, ?, ?)";
            
            ps = conn.prepareStatement(sql);
            
            ps.setString(1, kh.getMaKH());
            ps.setString(2, kh.getTen()); 
            
            // Dùng String.valueOf để đảm bảo an toàn dù model lưu sdt là int hay String
            ps.setString(3, String.valueOf(kh.getSoDienthoai())); 
            
            ps.setString(4, kh.getSoCCCD());
            ps.setString(5, kh.getDiaChi());
            ps.setString(6, kh.getTaiKhoan());

            return ps.executeUpdate() > 0; // Trả về true nếu thêm thành công

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            // Đóng kết nối an toàn
            try { if (ps != null) ps.close(); if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }

    // 2. LẤY THÔNG TIN KHÁCH HÀNG THEO USERNAME (Dùng sau khi Login thành công)
    public KhachHang getByUsername(String username) {
        KhachHang kh = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM KhachHang WHERE tenDangNhap = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            
            rs = ps.executeQuery();
            
            if (rs.next()) {
                // Lấy dữ liệu từ SQL ra
                String maKH = rs.getString("maKH");
                String hoTen = rs.getString("hoTen");
                String sdt = rs.getString("soDienThoai");
                String cccd = rs.getString("CCCD");
                String diaChi = rs.getString("diaChi");
                String tk = rs.getString("tenDangNhap");
                
                // Tạo đối tượng KhachHang để trả về
                // (Constructor này phải khớp với model.KhachHang hiện tại của bạn)
                kh = new KhachHang(maKH, hoTen, sdt, cccd, diaChi, tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); if (ps != null) ps.close(); if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return kh;
    }

    // 3. CẬP NHẬT THÔNG TIN KHÁCH HÀNG (Sửa hồ sơ)
    public boolean updateKhachHang(KhachHang kh) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DatabaseConnection.getConnection();
            
            // Chỉ cho phép sửa: Tên, SĐT, CCCD, Địa chỉ
            // Không sửa: Mã KH và Tên đăng nhập (vì là khóa chính/định danh)
            String sql = "UPDATE KhachHang SET hoTen=?, soDienThoai=?, CCCD=?, diaChi=? WHERE maKH=?";
            
            ps = conn.prepareStatement(sql);
            ps.setString(1, kh.getTen());
            ps.setString(2, String.valueOf(kh.getSoDienthoai()));
            ps.setString(3, kh.getSoCCCD());
            ps.setString(4, kh.getDiaChi());
            ps.setString(5, kh.getMaKH()); // Điều kiện WHERE

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try { if (ps != null) ps.close(); if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }
}