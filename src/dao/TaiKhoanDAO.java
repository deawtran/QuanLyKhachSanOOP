package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.DatabaseConnection;

public class TaiKhoanDAO {

    // 1. Kiểm tra đăng nhập
    // Trả về: Vai trò (KhachHang, LeTan, QuanLy) nếu đúng, null nếu sai
    public String checkLogin(String username, String password) {
        String vaiTro = null;
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT vaiTro FROM TaiKhoan WHERE tenDangNhap = ? AND matKhau = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                vaiTro = rs.getString("vaiTro");
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vaiTro;
    }

    // 2. Đăng ký tài khoản mới (Mặc định vai trò là KhachHang)
    public boolean dangKyTaiKhoan(String username, String password) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "INSERT INTO TaiKhoan(tenDangNhap, matKhau, vaiTro) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, "KhachHang"); // Mặc định role

            int result = ps.executeUpdate();
            conn.close();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // 3. Kiểm tra xem tên đăng nhập đã tồn tại chưa
    public boolean kiemTraTonTai(String username) {
        boolean tonTai = false;
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM TaiKhoan WHERE tenDangNhap = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                tonTai = true;
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tonTai;
    }
}