package controller;

import dao.DatPhongDAO;
import dao.PhongDAO;
import model.DatPhong;
import model.KhachHang;
import model.Phong;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class DichVuDatPhong {
    
    // Khai báo 2 DAO cần thiết
    private PhongDAO phongDAO;
    private DatPhongDAO datPhongDAO;

    public DichVuDatPhong() {
        this.phongDAO = new PhongDAO();
        this.datPhongDAO = new DatPhongDAO();
    }
    
    // 1. LẤY DANH SÁCH PHÒNG TRỐNG (Để hiển thị lên bảng cho khách chọn)
    public List<Phong> timPhongTrong() {
        return phongDAO.getPhongTrong();
    }

    // 2. CHỨC NĂNG ĐẶT PHÒNG (Core Logic)
    public boolean xuLyDatPhong(KhachHang khachHang, Phong phongChon, LocalDate ngayNhan, LocalDate ngayTra) {
        
        // B1: Tính số ngày ở
        long soNgayO = ChronoUnit.DAYS.between(ngayNhan, ngayTra);
        if (soNgayO <= 0) soNgayO = 1; // Tối thiểu tính 1 ngày

        // B2: Lấy giá phòng chính xác từ Database
        double giaPhong = phongDAO.getGiaPhong(phongChon.getSoPhong());
        
        // B3: Tính tổng tiền
        double tongTien = giaPhong * soNgayO;

        // B4: Tạo mã đặt phòng tự động (DP + thời gian)
        String maDP = "DP" + (System.currentTimeMillis() % 100000);

        // B5: Tạo đối tượng DatPhong
        DatPhong donMoi = new DatPhong(maDP, phongChon, khachHang, ngayNhan, ngayTra, tongTien);

        // B6: Gọi DAO để lưu xuống SQL
        boolean ketQuaLuu = datPhongDAO.insert(donMoi);

        // B7: Nếu lưu thành công -> Cập nhật trạng thái phòng thành "Đã đặt"
        if (ketQuaLuu) {
            phongDAO.updateTrangThai(phongChon.getSoPhong(), "Đã đặt");
            return true;
        }

        return false;
    }

    // 3. XEM LỊCH SỬ ĐẶT PHÒNG (Dành cho khách hàng xem lại)
    public List<DatPhong> xemLichSu(String maKH) {
        return datPhongDAO.getLichSuDatPhong(maKH);
    }
}