package model;

import java.time.LocalDate;

public class DatPhong {
    private String maDatPhong;
    private KhachHang khachHang;
    private Phong phong;
    private LocalDate ngayNhan;
    private LocalDate ngayTra;
    private double tongTien;
    private String trangThai; 

    // Constructor cần thiết cho DichVuPhong.java
    public DatPhong(String maDatPhong, Phong phong, KhachHang khachHang, LocalDate ngayNhan, LocalDate ngayTra, double tongTien) {
        this.maDatPhong = maDatPhong;
        this.phong = phong;
        this.khachHang = khachHang;
        this.ngayNhan = ngayNhan;
        this.ngayTra = ngayTra;
        this.tongTien = tongTien;
        this.trangThai = "Mới";
    }

    // --- GETTERS BẮT BUỘC ---
    public String getMaDatPhong() { return maDatPhong; }
    public KhachHang getKhachHang() { return khachHang; }
    public Phong getPhong() { return phong; }
    public double getTongTien() { return tongTien; }
    public String getTrangThai() { return trangThai; }
    public LocalDate getNgayNhan() { return ngayNhan; }
    public LocalDate getNgayTra() { return ngayTra; }
    
    // --- SETTER BẮT BUỘC ---
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
    
    /** * CẦN THIẾT cho Logic Lưu trữ (Persistence Fix) 
     * DichVuPhong sẽ gọi phương thức này để gán ID cuối cùng trước khi lưu.
     */
    public void setMaDatPhong(String maDatPhong) {
        this.maDatPhong = maDatPhong;
    }
}