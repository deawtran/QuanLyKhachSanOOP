package model;

import java.util.HashMap; // Import bắt buộc
import java.util.Map;     // Import bắt buộc

public class KhachHang extends NguoiDung {
    private String maKH;
    private String diaChi;

    // 🔥 QUAN TRỌNG: Biến này dùng để truyền dữ liệu giữa các màn hình
    // (Ví dụ: truyền ngày đặt, phòng chọn từ màn hình Tra Cứu sang màn hình Xác Nhận)
    private Map<String, Object> tempData = new HashMap<>();

    public KhachHang() {
    }

    // 1. CONSTRUCTOR ĐẦY ĐỦ (Dùng cho DAO và Đăng Ký)
    // Khớp với thứ tự cột trong DB và hàm addKhachHang
    public KhachHang(String maKH, String hoTen, String soDienThoai, String cccd, String diaChi, String taiKhoan) {
        this.maKH = maKH;
        this.ten = hoTen;
        this.soDienthoai = soDienThoai;
        this.soCCCD = cccd;
        this.diaChi = diaChi;
        this.taiKhoan = taiKhoan;
    }
    
    // 2. CONSTRUCTOR RÚT GỌN (Legacy - Có thể giữ lại nếu code cũ còn dùng)
    public KhachHang(String ten, String cccd, String tk, String mk) {
        this.ten = ten;
        this.soCCCD = cccd;
        this.taiKhoan = tk;
        this.matKhau = mk;
    }

    // --- GETTER / SETTER CƠ BẢN ---
    public String getMaKH() { return maKH; }
    public void setMaKH(String maKH) { this.maKH = maKH; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
    
    // --- 🔥 CÁC HÀM XỬ LÝ TEMP DATA (BẮT BUỘC PHẢI CÓ) ---
    
    public void putTempData(String key, Object value) {
        if (tempData == null) tempData = new HashMap<>();
        tempData.put(key, value);
    }

    public Object getTempData(String key, Object defaultValue) {
        if (tempData == null) return defaultValue;
        return tempData.getOrDefault(key, defaultValue);
    }
    
    @Override
    public String toString() {
        return this.ten; 
    }
}