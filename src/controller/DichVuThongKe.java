package controller;

import java.util.Random;

public class DichVuThongKe {
    private Random random = new Random();

    /**
     * Lấy tỷ lệ doanh thu giả định so với các kỳ trước.
     * @return double[] {tỷ lệ Ngày, tỷ lệ Tháng, tỷ lệ Năm}
     */
    public double[] layTyLeDoanhThu() {
        // Tỷ lệ Ngẫu nhiên cho 3 kỳ (giả lập tăng trưởng/suy giảm)
        double tyLeNgay = (random.nextDouble() * 20) - 10; // -10% đến +10%
        double tyLeThang = (random.nextDouble() * 30) - 15; // -15% đến +15%
        double tyLeNam = (random.nextDouble() * 40) - 20; // -20% đến +20%
        
        return new double[]{tyLeNgay, tyLeThang, tyLeNam};
    }
}