package model;

public class LoaiPhong {
    private String maLoai;
    private String tenLoai;
    private double giaCoBan;
    
    // Hai thuộc tính này quan trọng cho việc Lọc phòng theo số người
    private int sucChuaToiDa; 
    private String moTa;

    public LoaiPhong() {
    }

    // 1. Constructor đơn giản (Dùng cho PhongDAO load từ DB lên)
    public LoaiPhong(String tenLoai) {
        this.tenLoai = tenLoai;
        this.maLoai = tenLoai; // Tạm gán mã giống tên
        this.sucChuaToiDa = 2; // Mặc định
        this.moTa = "";
    }
    
    // 2. Constructor đầy đủ (Dùng cho DichVuPhong tạo Metadata)
    public LoaiPhong(String maLoai, String tenLoai, double giaCoBan, int sucChuaToiDa, String moTa) {
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
        this.giaCoBan = giaCoBan;
        this.sucChuaToiDa = sucChuaToiDa;
        this.moTa = moTa;
    }

    // --- Getters & Setters ---
    public String getMaLoai() { return maLoai; }
    public void setMaLoai(String maLoai) { this.maLoai = maLoai; }

    public String getTenLoai() { return tenLoai; }
    public void setTenLoai(String tenLoai) { this.tenLoai = tenLoai; }

    public double getGiaCoBan() { return giaCoBan; }
    public void setGiaCoBan(double giaCoBan) { this.giaCoBan = giaCoBan; }

    // Các hàm bị thiếu gây lỗi -> Đã bổ sung:
    public int getSucChuaToiDa() { return sucChuaToiDa; }
    public void setSucChuaToiDa(int sucChuaToiDa) { this.sucChuaToiDa = sucChuaToiDa; }

    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }

    @Override
    public String toString() {
        return tenLoai; 
    }
}