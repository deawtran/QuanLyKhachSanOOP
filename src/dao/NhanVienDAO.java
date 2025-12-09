package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.DatabaseConnection;
import model.LeTan;

public class NhanVienDAO {

    // 1. LẤY DANH SÁCH TẤT CẢ LỄ TÂN (Dùng cho ManHinhQLLeTanMenu)
    public List<LeTan> getAllLeTan() {
        List<LeTan> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DatabaseConnection.getConnection();
            // Lấy tất cả nhân viên có chức vụ là 'Lễ tân'
            String sql = "SELECT * FROM NhanVien WHERE chucVu = 'Lễ tân'"; 
            ps = conn.prepareStatement(sql);
            
            rs = ps.executeQuery();
            while (rs.next()) {
                LeTan lt = new LeTan();
                lt.setMaNV(rs.getString("maNV"));
                lt.setTen(rs.getString("hoTen"));
                lt.setLuongTheoGio(rs.getDouble("luongTheoGio"));
                
                // Lưu ý: Database của bạn bảng NhanVien KHÔNG CÓ cột CCCD.
                // Mình tạm lấy cột 'gioiTinh' gán vào CCCD để code không bị lỗi hiển thị
                lt.setSoCCCD(rs.getString("gioiTinh")); 
                
                lt.setTaiKhoan(rs.getString("tenDangNhap"));
                // Mật khẩu nằm ở bảng TaiKhoan, nếu cần phải join bảng, nhưng tạm thời để trống hoặc null
                
                list.add(lt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); if (ps != null) ps.close(); if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return list;
    }

    // 2. CẬP NHẬT THÔNG TIN NHÂN VIÊN (Dùng cho ManHinhCapNhatThongTinLeTan)
    public boolean updateNhanVien(LeTan lt) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DatabaseConnection.getConnection();
            
            // Database bảng NhanVien: maNV, hoTen, gioiTinh, ngaySinh, chucVu, luongTheoGio...
            // Vì UI bạn nhập CCCD nhưng DB không có cột CCCD, mình sẽ lưu tạm vào cột 'gioiTinh'
            // để dữ liệu không bị mất. (Bạn nên thêm cột CCCD vào SQL sau này nhé)
            String sql = "UPDATE NhanVien SET hoTen=?, gioiTinh=?, luongTheoGio=? WHERE maNV=?";
            
            ps = conn.prepareStatement(sql);
            
            ps.setString(1, lt.getTen());
            ps.setString(2, lt.getSoCCCD()); // Lưu tạm CCCD vào cột Giới Tính
            ps.setDouble(3, lt.getLuongTheoGio());
            ps.setString(4, lt.getMaNV()); // Điều kiện WHERE (Không được null)

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try { if (ps != null) ps.close(); if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }
    
    // 3. LẤY THÔNG TIN THEO USERNAME (Dùng cho DichVuNguoiDung -> Đăng Nhập)
    public LeTan getByUsername(String username) {
        LeTan lt = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM NhanVien WHERE tenDangNhap = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            
            rs = ps.executeQuery();
            if (rs.next()) {
                lt = new LeTan();
                lt.setMaNV(rs.getString("maNV"));
                lt.setTen(rs.getString("hoTen"));
                lt.setLuongTheoGio(rs.getDouble("luongTheoGio"));
                lt.setSoCCCD(rs.getString("gioiTinh")); // Map tạm
                lt.setTaiKhoan(username);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); if (ps != null) ps.close(); if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return lt;
    }
}