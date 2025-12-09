package controller;

import model.DatPhong;
import model.KhachHang;
import view.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class QuanLyGiaoDien {
    
    private JFrame manHinhHienTai;
    
    // --- CÁC SERVICE KẾT NỐI DATABASE ---
    private DichVuNguoiDung dichVuNguoiDung; // Quản lý Login/Register (DAO: TaiKhoan, KhachHang)
    private DichVuDatPhong dichVuDatPhong;   // Quản lý Đặt phòng/Lịch sử (DAO: DatPhong, Phong)
    private DichVuPhong dichVuPhong;         // Quản lý Tra cứu phòng (DAO: Phong)
    private DichVuThongKe dichVuThongKe;     // (Tùy chọn)
    
    // Quản lý danh sách màn hình
    private Map<String, JFrame> danhSachManHinh;
    
    // Lưu phiên đăng nhập hiện tại (Để các màn hình con có thể truy xuất ai đang dùng)
    private KhachHang khachHangDangNhap;

    public QuanLyGiaoDien() {
        // 1. KHỞI TẠO CÁC SERVICE (Đã kết nối SQL bên trong)
        dichVuNguoiDung = new DichVuNguoiDung();
        dichVuDatPhong = new DichVuDatPhong();
        dichVuPhong = new DichVuPhong();
        dichVuThongKe = new DichVuThongKe();
        
        danhSachManHinh = new HashMap<>();
        
        // 2. KHỞI TẠO CÁC VIEW CƠ BẢN
        // Lưu ý: Các View này sẽ gọi ngược lại quanLyGiaoDien.getDichVu...() để lấy dữ liệu
        
        ManHinhChinh manHinhChinh = new ManHinhChinh(this);
        ManHinhDangNhapChung manHinhLoginChung = new ManHinhDangNhapChung(this, dichVuNguoiDung);
        ManHinhDangKyKhachHang dangKyKH = new ManHinhDangKyKhachHang(this, dichVuNguoiDung);
        
        // Các màn hình quản lý (Admin/Lễ tân)
        ManHinhQuanLyKS qlksMenu = new ManHinhQuanLyKS(this); 
        ManHinhThongKe thongKeScreen = new ManHinhThongKe(this, dichVuThongKe); 
        ManHinhQLLeTanMenu qlLeTanMenu = new ManHinhQLLeTanMenu(this, dichVuNguoiDung); 
        ManHinhLeTanMenu leTanMenu = new ManHinhLeTanMenu(this);
        ManHinhLeTanQLKhach leTanQLKhach = new ManHinhLeTanQLKhach(this);
        
        // Các màn hình luồng đặt phòng
        ManHinhThongBaoLoaiPhong thongBaoLoaiPhong = new ManHinhThongBaoLoaiPhong(this); 
        ManHinhXacNhanDatPhong xacNhanDatPhong = new ManHinhXacNhanDatPhong(this); 
        ManHinhThanhToanDatPhong thanhToanDatPhong = new ManHinhThanhToanDatPhong(this); 
        ManHinhDatPhongThanhCong datPhongThanhCong = new ManHinhDatPhongThanhCong(this); 
        
        // Các màn hình lịch sử & hủy phòng
        ManHinhDanhSachPhongDaDat dsPhongDaDat = new ManHinhDanhSachPhongDaDat(this);
        ManHinhXemChiTietPhongDaDat chiTietPhongDaDat = new ManHinhXemChiTietPhongDaDat(this);
        ManHinhDanhSachPhongDeHuy dsPhongDeHuy = new ManHinhDanhSachPhongDeHuy(this);
        ManHinhXacNhanHuyPhong xacNhanHuyPhong = new ManHinhXacNhanHuyPhong(this);
        ManHinhHuyPhongThanhCong huyPhongThanhCong = new ManHinhHuyPhongThanhCong(this);
        
        // 3. ĐĂNG KÝ VÀO MAP
        danhSachManHinh.put("CHINH", manHinhChinh);
        danhSachManHinh.put("DANG_NHAP_CHUNG", manHinhLoginChung); 
        danhSachManHinh.put("DANG_KY_KH", dangKyKH); 
        danhSachManHinh.put("QLKS_MENU", qlksMenu); 
        danhSachManHinh.put("THONG_KE_DOANH_THU", thongKeScreen); 
        danhSachManHinh.put("QL_LE_TAN_MENU", qlLeTanMenu); 
        danhSachManHinh.put("LE_TAN_MENU", leTanMenu); 
        danhSachManHinh.put("LE_TAN_QL_KHACH_HANG_MENU", leTanQLKhach);
        
        danhSachManHinh.put("THONG_BAO_LOAI_PHONG", thongBaoLoaiPhong); 
        danhSachManHinh.put("XAC_NHAN_DAT_PHONG", xacNhanDatPhong);
        danhSachManHinh.put("THANH_TOAN_DAT_PHONG", thanhToanDatPhong);
        danhSachManHinh.put("DAT_PHONG_THANH_CONG", datPhongThanhCong);
        
        danhSachManHinh.put("DANH_SACH_PHONG_DA_DAT", dsPhongDaDat);
        danhSachManHinh.put("XEM_CHI_TIET_PHONG_DA_DAT", chiTietPhongDaDat);
        danhSachManHinh.put("DANH_SACH_PHONG_DE_HUY", dsPhongDeHuy);
        danhSachManHinh.put("XAC_NHAN_HUY_PHONG", xacNhanHuyPhong);
        danhSachManHinh.put("HUY_PHONG_THANH_CONG", huyPhongThanhCong);
        
        // Khởi động ứng dụng
        manHinhHienTai = manHinhChinh;
        manHinhHienTai.setVisible(true);
    }
    
    // --- LOGIC CHUYỂN MÀN HÌNH ---
    public void chuyenManHinh(String tenManHinh) {
        chuyenManHinh(tenManHinh, null);
    }

    public void chuyenManHinh(String tenManHinh, Object data) {

        JFrame manHinhMoi = danhSachManHinh.get(tenManHinh);

        // --- XỬ LÝ DỮ LIỆU TRUYỀN GIỮA CÁC MÀN HÌNH ---
        if (data != null) {

            // 1. TRƯỜNG HỢP: Đăng nhập thành công -> Chuyển vào Dashboard
            // Logic: Nếu data là KhachHang nhưng tên màn hình rỗng (hoặc quy định riêng)
            // thì tạo màn hình Dashboard cho khách
            if (tenManHinh.equals("") && data instanceof KhachHang) { 
                this.khachHangDangNhap = (KhachHang) data; // Lưu session
                manHinhMoi = new ManHinhKhachHang(this, this.khachHangDangNhap); 
            } 
            
            // 2. TRƯỜNG HỢP: Luồng chức năng của Khách hàng
            else if (data instanceof KhachHang) { 
                KhachHang kh = (KhachHang) data;
                this.khachHangDangNhap = kh; // Cập nhật session đảm bảo đồng bộ
                
                // Các màn hình cần tạo mới mỗi lần vào (để reset dữ liệu cũ)
                if (tenManHinh.equals("DAT_PHONG_MENU")) { 
                    manHinhMoi = new ManHinhDatPhongMenu(this, kh); 
                } 
                else if (tenManHinh.equals("TRA_CUU_PHONG")) { 
                    manHinhMoi = new ManHinhTraCuuPhong(this, kh); 
                } 

                // Các màn hình dùng lại (Singleton trong Map) -> Cần set dữ liệu mới vào
                else {
                    manHinhMoi = danhSachManHinh.get(tenManHinh); 
                    if (manHinhMoi == null) { return; } 

                    // Ép kiểu và truyền dữ liệu user vào View
                    if (manHinhMoi instanceof ManHinhThongBaoLoaiPhong) { ((ManHinhThongBaoLoaiPhong) manHinhMoi).setKhachHang(kh); } 
                    else if (manHinhMoi instanceof ManHinhXacNhanDatPhong) { ((ManHinhXacNhanDatPhong) manHinhMoi).setKhachHang(kh); } 
                    else if (manHinhMoi instanceof ManHinhThanhToanDatPhong) { ((ManHinhThanhToanDatPhong) manHinhMoi).setKhachHang(kh); } 
                    else if (manHinhMoi instanceof ManHinhDatPhongThanhCong) { ((ManHinhDatPhongThanhCong) manHinhMoi).setKhachHang(kh); } 
                    
                    // View lịch sử
                    else if (manHinhMoi instanceof ManHinhDanhSachPhongDaDat) { ((ManHinhDanhSachPhongDaDat) manHinhMoi).setKhachHang(kh); }
                    else if (manHinhMoi instanceof ManHinhDanhSachPhongDeHuy) { ((ManHinhDanhSachPhongDeHuy) manHinhMoi).setKhachHang(kh); }
                }
            } 
            
            // 3. TRƯỜNG HỢP: Xem chi tiết / Hủy phòng (Truyền object DatPhong)
            else if (data instanceof DatPhong) {
                DatPhong dp = (DatPhong) data;
                KhachHang kh = dp.getKhachHang(); // Lấy lại user từ đơn đặt phòng
                
                manHinhMoi = danhSachManHinh.get(tenManHinh);
                if (manHinhMoi == null) return;
                
                if (manHinhMoi instanceof ManHinhXemChiTietPhongDaDat) {
                    ((ManHinhXemChiTietPhongDaDat) manHinhMoi).setKhachHang(kh); 
                    ((ManHinhXemChiTietPhongDaDat) manHinhMoi).setChiTietDatPhong(dp);
                }
                else if (manHinhMoi instanceof ManHinhXacNhanHuyPhong) {
                    ((ManHinhXacNhanHuyPhong) manHinhMoi).setKhachHang(kh); 
                    ((ManHinhXacNhanHuyPhong) manHinhMoi).setDatPhongDeHuy(dp);
                }
            }
        }
        
        // --- HIỂN THỊ MÀN HÌNH MỚI ---
        if (manHinhMoi != null) {
            if (manHinhHienTai != null) { manHinhHienTai.setVisible(false); }
            
            manHinhHienTai = manHinhMoi;
            manHinhHienTai.setVisible(true);
            manHinhHienTai.toFront(); 

            // Refresh dữ liệu (Load lại danh sách từ Database khi mở màn hình)
            if (manHinhMoi instanceof ManHinhDanhSachPhongDaDat) {
                ((ManHinhDanhSachPhongDaDat) manHinhMoi).taiDuLieu();
            } else if (manHinhMoi instanceof ManHinhDanhSachPhongDeHuy) {
                ((ManHinhDanhSachPhongDeHuy) manHinhMoi).taiDuLieu();
            }
        } else {
            if (!tenManHinh.equals("")) {
                JOptionPane.showMessageDialog(null, "Lỗi: Không tìm thấy màn hình " + tenManHinh, "Lỗi Navigation", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // --- GETTERS (Để các View con gọi Service) ---
    
    public DichVuPhong getDichVuPhong() { 
        return dichVuPhong; 
    }
    
    public DichVuNguoiDung getDichVuNguoiDung() { 
        return dichVuNguoiDung; 
    }
    
    // 🔥 MỚI: Cung cấp Service Đặt phòng cho View (Để gọi hàm insert / update)
    public DichVuDatPhong getDichVuDatPhong() {
        return dichVuDatPhong;
    }
    
    // 🔥 SỬA: Không trả về user1 fix cứng nữa, mà trả về user đang login
    public KhachHang getKhachHangHienTai() { 
        return this.khachHangDangNhap;
    }
    
    // Hàm set user khi login thành công (nếu cần dùng ở ngoài)
    public void setKhachHangHienTai(KhachHang kh) {
        this.khachHangDangNhap = kh;
    }
}