package model;

public class NguoiDung {
    protected String ten;
    protected String soDienthoai; // Đổi sang String để giữ số 0 ở đầu
    protected String soCCCD;
    protected String taiKhoan;
    protected String matKhau;

    public NguoiDung() {
    }

    public NguoiDung(String ten, String soDienthoai, String soCCCD, String taiKhoan, String matKhau) {
        this.ten = ten;
        this.soDienthoai = soDienthoai;
        this.soCCCD = soCCCD;
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
    }

    // --- CÁC GETTER CẦN THIẾT CHO DAO ---
    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }

    // 🔥 QUAN TRỌNG: Phải có hàm này thì DAO mới không lỗi dòng kh.getSoDienthoai()
    public String getSoDienthoai() { return soDienthoai; }
    public void setSoDienthoai(String soDienthoai) { this.soDienthoai = soDienthoai; }

    public String getSoCCCD() { return soCCCD; }
    public void setSoCCCD(String soCCCD) { this.soCCCD = soCCCD; }

    public String getTaiKhoan() { return taiKhoan; }
    public void setTaiKhoan(String taiKhoan) { this.taiKhoan = taiKhoan; }

    public String getMatKhau() { return matKhau; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }
}