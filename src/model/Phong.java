package model;

public class Phong {
    private String soPhong;
    private LoaiPhong loaiPhong; 
    private String trangThai;

    // PHƯƠNG THỨC KHỞI TẠO CẦN 3 THAM SỐ (Đúng với lời gọi trong Service)
    public Phong(String soPhong, LoaiPhong loaiPhong, String trangThai) {
        this.soPhong = soPhong;
        this.loaiPhong = loaiPhong;
        this.trangThai = trangThai;
    }
    
    // --- Getters/Setters CẦN THIẾT ---
    public String getSoPhong() { return soPhong; }
    public LoaiPhong getLoaiPhong() { return loaiPhong; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
}