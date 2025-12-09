package model;

public class QuanLyKhachSan extends NguoiDung {

    // 🔥 1. BẮT BUỘC PHẢI THÊM CONSTRUCTOR RỖNG NÀY
    // Để DichVuNguoiDung có thể gọi: new QuanLyKhachSan()
    public QuanLyKhachSan() {
    }

    // 2. Constructor đầy đủ (Giữ nguyên cái cũ của bạn)
    public QuanLyKhachSan(String ten, String cccd, String tk, String mk) {
        this.ten = ten;
        this.soCCCD = cccd;
        this.taiKhoan = tk;
        this.matKhau = mk;
    }

    @Override
    public String toString() {
        return "Quản Lý: " + this.ten;
    }
}