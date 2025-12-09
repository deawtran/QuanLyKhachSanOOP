package controller;

import dao.KhachHangDAO;
import dao.TaiKhoanDAO;
import dao.NhanVienDAO;
import model.KhachHang;
import model.LeTan; 
import model.QuanLyKhachSan;
import java.util.List; // Nhớ import List

public class DichVuNguoiDung {
    
    private TaiKhoanDAO taiKhoanDAO;
    private KhachHangDAO khachHangDAO;
    private NhanVienDAO nhanVienDAO;

    public DichVuNguoiDung() {
        taiKhoanDAO = new TaiKhoanDAO();
        khachHangDAO = new KhachHangDAO();
        nhanVienDAO = new NhanVienDAO();
    }
    
    // --- 1. ĐĂNG NHẬP (Kết nối Database) ---
    public Object dangNhapChung(String tk, String mk) {
        // Bước 1: Kiểm tra tài khoản và mật khẩu
        String vaiTro = taiKhoanDAO.checkLogin(tk, mk);
        
        if (vaiTro == null) {
            return null;
        }

        // Bước 2: Lấy thông tin chi tiết dựa trên vai trò
        if (vaiTro.equalsIgnoreCase("KhachHang")) {
            return khachHangDAO.getByUsername(tk);
        } 
        else if (vaiTro.equalsIgnoreCase("LeTan") || vaiTro.equalsIgnoreCase("NhanVien")) {
            // CẬP NHẬT: Gọi DAO để lấy thông tin thật của nhân viên (Mã NV, Tên...)
            // thay vì trả về new LeTan() rỗng như trước.
            return nhanVienDAO.getByUsername(tk); 
        } 
        else if (vaiTro.equalsIgnoreCase("QuanLy")) {
            System.out.println("Đăng nhập thành công với vai trò Quản Lý");
            return new QuanLyKhachSan(); 
        }

        return null;
    }

    // --- 2. ĐĂNG KÝ KHÁCH HÀNG (Kết nối Database) ---
    public boolean dangKyKhachHang(String ten, String cccd, String tk, String mk, String sdt, String diaChi) {
        if (taiKhoanDAO.kiemTraTonTai(tk)) {
            System.out.println("Tài khoản đã tồn tại!");
            return false;
        }

        boolean taoTK = taiKhoanDAO.dangKyTaiKhoan(tk, mk);
        
        if (taoTK) {
            String maKH = "KH" + (System.currentTimeMillis() % 100000);
            KhachHang khMoi = new KhachHang(maKH, ten, sdt, cccd, diaChi, tk);
            return khachHangDAO.addKhachHang(khMoi);
        }
        
        return false;
    }
    
    // --- 3. QUẢN LÝ NHÂN VIÊN (Kết nối Database) ---
    
    // Hàm cập nhật thông tin Lễ tân
    public boolean capNhatThongTinLeTan(LeTan lt, String tenMoi, String cccdMoi, double luongMoi) {
        lt.setTen(tenMoi);
        lt.setSoCCCD(cccdMoi); 
        lt.setLuongTheoGio(luongMoi);
        
        // Gọi DAO update
        return nhanVienDAO.updateNhanVien(lt);
    }
    
    // 🔥 CẬP NHẬT QUAN TRỌNG: Sửa hàm này để trả về List<LeTan>
    public List<LeTan> layDanhSachLeTan() {
        // Gọi DAO để lấy danh sách thật từ SQL
        return nhanVienDAO.getAllLeTan();
    }
}