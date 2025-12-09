package model;

public class LeTan extends NguoiDung {
    
    // 1. THÊM THUỘC TÍNH maNV (Quan trọng để định danh trong Database)
    private String maNV; 
    
    private String gioLam;
    private double luong;
    private double luongTheoGio;

    // 2. THÊM CONSTRUCTOR RỖNG (Bắt buộc để DAO khởi tạo khi load từ SQL)
    public LeTan() {
    }

    // 3. CẬP NHẬT CONSTRUCTOR ĐẦY ĐỦ (Thêm tham số maNV)
    public LeTan(String maNV, String ten, String cccd, String tk, String mk, double luongGio) {
        // Gán các thuộc tính của cha (NguoiDung)
        this.ten = ten;
        this.soCCCD = cccd;
        this.taiKhoan = tk;
        this.matKhau = mk;
        
        // Gán thuộc tính riêng
        this.maNV = maNV;
        this.luongTheoGio = luongGio;
        
        // Giá trị mặc định
        this.luong = 0.0;
        this.gioLam = "Chưa cập nhật";
    }

    // --- CÁC GETTER/SETTER BẮT BUỘC CHO DAO ---
    
    public String getMaNV() { 
        return maNV; 
    }
    
    public void setMaNV(String maNV) { 
        this.maNV = maNV; 
    }

    // --- CÁC GETTER/SETTER KHÁC (GIỮ NGUYÊN) ---
    public double getLuong() { return luong; }
    public void setLuong(double luong) { this.luong = luong; }
    
    public String getGioLam() { return gioLam; }
    public void setGioLam(String gioLam) { this.gioLam = gioLam; }
    
    public double getLuongTheoGio() { return luongTheoGio; }
    public void setLuongTheoGio(double luongTheoGio) { this.luongTheoGio = luongTheoGio; }
    
    @Override
    public String toString() {
        // Hiển thị Tên và Mã NV để dễ chọn trong danh sách
        return this.ten + " - " + (this.maNV != null ? this.maNV : "Chưa có mã");
    }
}