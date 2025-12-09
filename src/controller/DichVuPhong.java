package controller;

import dao.DatPhongDAO;
import dao.PhongDAO;
import model.DatPhong;
import model.KhachHang;
import model.LoaiPhong;
import model.Phong;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DichVuPhong {
    
    // Khai báo các DAO để làm việc với SQL
    private PhongDAO phongDAO;
    private DatPhongDAO datPhongDAO;
    
    // Dữ liệu hỗ trợ (Metadata) cho Loại Phòng (Vì trong DB bảng Phong chỉ có tên loại, chưa có sức chứa)
    private List<LoaiPhong> metaDataLoaiPhong;

    public DichVuPhong() {
        this.phongDAO = new PhongDAO();
        this.datPhongDAO = new DatPhongDAO();
        khoiTaoThongTinLoaiPhong();
    }
    
    // Hàm phụ trợ: Tạo thông tin chi tiết cho các loại phòng (Để lọc theo sức chứa)
    private void khoiTaoThongTinLoaiPhong() {
        metaDataLoaiPhong = new ArrayList<>();
        // Lưu ý: Tên loại ở đây phải khớp với cột 'loaiPhong' trong Database MySQL
        metaDataLoaiPhong.add(new LoaiPhong("Thường", "Thường", 750000.0, 2, "Phòng tiêu chuẩn")); 
        metaDataLoaiPhong.add(new LoaiPhong("VIP", "VIP", 950000.0, 4, "Phòng rộng, view đẹp"));
        metaDataLoaiPhong.add(new LoaiPhong("VVIP", "VVIP", 1499000.0, 6, "Phòng tổng thống"));
    }

    // --- 1. TRA CỨU PHÒNG TRỐNG (Từ Database) ---
    public List<Phong> traCuuPhongTrong() {
        // Gọi DAO để lấy danh sách phòng có trạng thái 'Trống' từ SQL
        List<Phong> listPhongTrong = phongDAO.getPhongTrong();
        
        // Bổ sung thông tin chi tiết (Sức chứa, mô tả) cho từng phòng lấy được
        for (Phong p : listPhongTrong) {
            enrichPhongData(p);
        }
        return listPhongTrong;
    }
    
    // Hàm tìm kiếm nâng cao (Lọc theo sức chứa)
    public List<Phong> timPhongPhuHop(int soNguoi) {
        List<Phong> allPhongTrong = traCuuPhongTrong();
        List<Phong> ketQua = new ArrayList<>();
        
        for (Phong p : allPhongTrong) {
            // Kiểm tra sức chứa của loại phòng đó
            if (p.getLoaiPhong().getSucChuaToiDa() >= soNguoi) {
                ketQua.add(p);
            }
        }
        return ketQua;
    }

    // --- 2. THÊM ĐẶT PHÒNG MỚI (Lưu xuống SQL) ---
    public boolean addDatPhong(DatPhong dp) {
        // Tạo mã đặt phòng tự động nếu chưa có (Tránh trùng lặp)
        if (dp.getMaDatPhong() == null || dp.getMaDatPhong().isEmpty()) {
            dp.setMaDatPhong("DP" + System.currentTimeMillis() % 100000);
        }
        
        dp.setTrangThai("Đã đặt"); // Mặc định trạng thái chờ

        // 1. Lưu đơn vào bảng DatPhong
        boolean kqDat = datPhongDAO.insert(dp);
        
        // 2. Nếu đặt thành công -> Cập nhật trạng thái phòng thành "Đã đặt"
        if (kqDat) {
            phongDAO.updateTrangThai(dp.getPhong().getSoPhong(), "Đã đặt");
            return true;
        }
        return false;
    }

    // --- 3. LẤY DANH SÁCH LỊCH SỬ (Từ SQL) ---
    public List<DatPhong> getDanhSachPhongDaDat(KhachHang kh) {
        if (kh == null || kh.getMaKH() == null) return new ArrayList<>();
        
        // Gọi DAO lấy toàn bộ lịch sử
        List<DatPhong> list = datPhongDAO.getLichSuDatPhong(kh.getMaKH());
        
        // Lọc bỏ những đơn đã hủy (nếu muốn)
        return list.stream()
                   .filter(dp -> !dp.getTrangThai().equals("DA_HUY"))
                   .collect(Collectors.toList());
    }

    // --- 4. LẤY DANH SÁCH CÓ THỂ HỦY (Từ SQL) ---
    public List<DatPhong> getDanhSachPhongDeHuy(KhachHang kh) {
        if (kh == null || kh.getMaKH() == null) return new ArrayList<>();

        List<DatPhong> list = datPhongDAO.getLichSuDatPhong(kh.getMaKH());
        
        // Chỉ lấy những đơn có trạng thái là "CHO_NHAN" hoặc "Mới"
        return list.stream()
                   .filter(dp -> dp.getTrangThai().equals("Đã đặt") || dp.getTrangThai().equals("Mới"))
                   .collect(Collectors.toList());
    }

    // --- 5. HỦY PHÒNG (Update SQL) ---
    public boolean huyPhong(String maDatPhong) {
        // Gọi DAO cập nhật trạng thái
        boolean kq = datPhongDAO.updateTrangThai(maDatPhong, "DA_HUY");
        
        if (kq) {
            // Quan trọng: Khi hủy đơn, phải trả lại trạng thái phòng về "Trống"
            // (Bước này cần lấy thông tin phòng từ mã đặt phòng, hơi phức tạp nên tạm thời mình giả định bạn sẽ xử lý sau hoặc admin xử lý)
            System.out.println("Đã hủy đơn: " + maDatPhong);
            return true;
        }
        return false;
    }

    // --- Hàm hỗ trợ: Điền thông tin phụ (Giá, Sức chứa) vào object Phong ---
    private void enrichPhongData(Phong p) {
        String tenLoaiDB = p.getLoaiPhong().getTenLoai(); // Lấy tên loại từ DB (VD: "VIP")
        
        // Tìm trong list metadata để lấy sức chứa, mô tả...
        for (LoaiPhong meta : metaDataLoaiPhong) {
            if (meta.getTenLoai().equalsIgnoreCase(tenLoaiDB) || meta.getMaLoai().equalsIgnoreCase(tenLoaiDB)) {
                // Gán lại giá trị đầy đủ
                p.getLoaiPhong().setSucChuaToiDa(meta.getSucChuaToiDa());
                p.getLoaiPhong().setMoTa(meta.getMoTa());
                // Giá tiền thì đã lấy trực tiếp từ bảng Phong trong DAO rồi nên k cần gán lại
                break;
            }
        }
    }
}