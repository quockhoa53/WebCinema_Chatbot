package junit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.*;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.ModelMap;

import poly.controller.CustomerController;
import poly.entity.*;

@RunWith(MockitoJUnitRunner.class)
public class PaymentControllerTest {

    @Mock
    private SessionFactory factory;

    @Mock
    private Session session;

    @Mock
    private Transaction transaction;

    @Mock
    private Query query;

    private ModelMap model;

    @InjectMocks
    private CustomerController realCustomerController;

    private CustomerController customerController; // Sẽ là mock dùng để override method riêng

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        model = new ModelMap();

        when(factory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        when(session.createQuery(anyString())).thenReturn(query);

        // ⚠️ Đây là mock của controller để có thể mock các method như layLC()
        customerController = Mockito.spy(realCustomerController);
    }

    @Test
    public void testPayment_KhongThayDoiGia_TinhTienDung() {
        int maSC = 1;
        String ghe = "A1,A2";

        // Lịch chiếu và phòng
        LichChieu lc = new LichChieu();
        PhongChieu phong = new PhongChieu();
        phong.setMaPhong("P10");
        lc.setDsPhong(phong);

        ChiTietGhePhong ghe1 = mockChiTietGhe(1, 1); // ghế thường - 45000
        ChiTietGhePhong ghe2 = mockChiTietGhe(2, 2); // ghế vip - 50000

        // Không có thay đổi giá
        when(query.uniqueResult()).thenReturn(null);

        // ✅ Bắt đầu mock các method trong controller
        doReturn(lc).when(customerController).layLC(maSC);
        doReturn(ghe1).when(customerController).layThongTinGhe("A1", "P10");
        doReturn(ghe2).when(customerController).layThongTinGhe("A2", "P10");
        doReturn(Collections.emptyList()).when(customerController).layDanhSachVeTonTai(eq(maSC), anyList(), eq(false));

        String viewName = customerController.payment1(model, maSC, ghe);

        // 👉 In thông tin ra để quan sát
        System.out.println("========== Test: Không Thay Đổi Giá ==========");
        System.out.printf("👀 View name         : %s%n", viewName);
        System.out.printf("💰 Tổng tiền         : %d%n", model.get("tongTien"));
        System.out.printf("🪑 Danh sách ghế     : %s%n", model.get("ghe"));
        System.out.printf("📅 Lịch chiếu        : %s%n", model.get("lc"));
        System.out.printf("🎟️ Danh sách vé      : %s%n", model.get("danhSachVe"));
        System.out.println("===============================================");

        assertEquals("customer/payment_1", viewName);
        assertEquals(95000, model.get("tongTien"));
    }

    @Test
    public void testPayment_CoThayDoiGia_TinhTienDung() {
        int maSC = 2;
        String ghe = "A1,A2";

        // Tạo lịch chiếu và phòng
        LichChieu lc = new LichChieu();
        PhongChieu phong = new PhongChieu();
        phong.setMaPhong("P20");
        lc.setDsPhong(phong);

        // Tạo ghế
        ChiTietGhePhong ghe1 = mockChiTietGhe(1, 1); // ghế thường
        ChiTietGhePhong ghe2 = mockChiTietGhe(2, 2); // ghế vip

        // ⚠️ Có thay đổi giá → giảm 10%
        Float phanTramThayDoi = 0.9f;
        String lyDoThayDoi = "Khuyến mãi lễ";

        when(query.uniqueResult()).thenReturn(new Object[]{phanTramThayDoi, lyDoThayDoi});

        doReturn(lc).when(customerController).layLC(maSC);
        doReturn(ghe1).when(customerController).layThongTinGhe("A1", "P20");
        doReturn(ghe2).when(customerController).layThongTinGhe("A2", "P20");
        doReturn(Collections.emptyList()).when(customerController).layDanhSachVeTonTai(eq(maSC), anyList(), eq(false));

        String viewName = customerController.payment1(model, maSC, ghe);

        // 👉 In chi tiết ra console
        System.out.println("========== Test: Có Thay Đổi Giá ==========");
        System.out.printf("👀 View name             : %s%n", viewName);
        System.out.printf("💰 Tổng tiền (đã giảm)  : %d%n", model.get("tongTien"));
        System.out.printf("📉 Phần trăm thay đổi   : %.2f%n", model.get("phantramthaydoi"));
        System.out.printf("📢 Lý do thay đổi        : %s%n", model.get("lydothaydoi"));
        System.out.println("===========================================");

        // ✅ Kiểm tra
        assertEquals("customer/payment_1", viewName);
        assertEquals(85500, model.get("tongTien"));
        assertEquals(phanTramThayDoi, model.get("phantramthaydoi"));
        assertEquals(lyDoThayDoi, model.get("lydothaydoi"));
    }
    
    @Test
    public void testPayment_GheKhongTonTai_TinhTienSai() {
        int maSC = 3;
        String ghe = "A3,B4"; // giả định không tồn tại

        LichChieu lc = new LichChieu();
        PhongChieu phong = new PhongChieu();
        phong.setMaPhong("P30");
        lc.setDsPhong(phong);

        when(query.uniqueResult()).thenReturn(null);

        doReturn(lc).when(customerController).layLC(maSC);
        doReturn(null).when(customerController).layThongTinGhe("A3", "P30"); // ghế không tồn tại
        doReturn(null).when(customerController).layThongTinGhe("B4", "P30"); // ghế không tồn tại
        doReturn(Collections.emptyList()).when(customerController).layDanhSachVeTonTai(eq(maSC), anyList(), eq(false));

        String viewName = customerController.payment1(model, maSC, ghe);

        System.out.println("========== Test: Ghế Không Tồn Tại ==========");
        System.out.printf("👀 View name            : %s%n", viewName);
        System.out.printf("💰 Tổng tiền            : %d%n", model.get("tongTien"));
        System.out.printf("🪑 Danh sách ghế        : %s%n", model.get("ghe"));
        System.out.printf("🎟️ Danh sách vé         : %s%n", model.get("danhSachVe"));
        System.out.println("=============================================");

        assertEquals("customer/payment_1", viewName);
        assertEquals(0, model.get("tongTien")); // vì không có ghế nào để tính
    }
    
//    @Test
//    public void testPayment_DanhSachGheRong() {
//        int maSC = 5;
//        String ghe = ""; // không chọn ghế
//
//        LichChieu lc = new LichChieu();
//        PhongChieu phong = new PhongChieu();
//        phong.setMaPhong("P50");
//        lc.setDsPhong(phong);
//
//        when(query.uniqueResult()).thenReturn(null);
//        doReturn(lc).when(customerController).layLC(maSC);
//
//        String viewName = customerController.payment1(model, maSC, ghe);
//
//        System.out.println("View: " + viewName);
//        assertEquals("redirect:/customer/payment_1/" + maSC + "/.htm", viewName);
//    }

    private ChiTietGhePhong mockChiTietGhe(int id, int loaiId) {
        ChiTietGhePhong ghe = new ChiTietGhePhong();
        ghe.setIdGheDat(id);
        LoaiGhe loai = new LoaiGhe();
        loai.setIdLoaiGhe(loaiId);
        ghe.setLoaiGhe(loai);
        return ghe;
    }
}


