package poly.controller;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import admin.controller.Message;
import poly.entity.ChiTietGhePhong;
import poly.entity.KhachHang;
import poly.entity.LichChieu;
import poly.entity.Phim;
import poly.entity.TaiKhoan;
import poly.entity.Ve;

import externalFunc.func;

@Transactional
@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    SessionFactory factory;

    @Autowired
    ServletContext context;

    public List<Ve> layLVKH(Integer maKH) {
        Session session = factory.getCurrentSession();
        String hql = "FROM Ve WHERE maKH = :maKH";
        Query query = session.createQuery(hql);
        query.setParameter("maKH", maKH);
        return query.list();
    }

    @RequestMapping("/account.htm")
    public String account(ModelMap model, HttpSession session, RedirectAttributes redirectAttributes) {
        KhachHang khachHang = (KhachHang) session.getAttribute("user");
        TaiKhoan taiKhoan = (TaiKhoan) session.getAttribute("tk");
        String matKhau = (String) session.getAttribute("mk");

        if (khachHang == null || taiKhoan == null) {
            redirectAttributes.addFlashAttribute("message",
                    new Message("error", "Vui lòng đăng nhập để xem thông tin tài khoản!"));
            return "redirect:/login.htm";
        }

        List<Ve> listVe = layLVKH(khachHang.getMaKH());
        model.addAttribute("user", khachHang);
        model.addAttribute("tk", taiKhoan);
        model.addAttribute("lv", listVe);
        model.addAttribute("login", true);

        if (matKhau.equals("01234567")) {
            model.addAttribute("message", new Message("warning", "Nhớ thay đổi mật khẩu mới!"));
        }

        return "customer/account";
    }

    @RequestMapping(value = "/update/{id}.htm", method = RequestMethod.POST)
    public String edit(ModelMap model, @RequestParam("tenKH") String tenKH, @RequestParam("gioiTinh") boolean gioiTinh,
                       @RequestParam("soDT") String soDT, @RequestParam("ngaySinh") String ngaySinh,
                       @RequestParam("diaChi") String diaChi, @RequestParam("photo") MultipartFile photo,
                       @PathVariable("id") Integer maKH, HttpSession session, RedirectAttributes redirectAttributes) {
        KhachHang khachHang = (KhachHang) session.getAttribute("user");
        if (khachHang == null || !khachHang.getMaKH().equals(maKH)) {
            redirectAttributes.addFlashAttribute("message",
                    new Message("error", "Không có quyền cập nhật thông tin này!"));
            return "redirect:/login.htm";
        }

        boolean valid = true;
        if (tenKH.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("errors_tenKH", "Tên khách hàng không được để trống!");
            valid = false;
        }
        if (soDT.trim().isEmpty() || !soDT.matches("\\d{10,11}")) {
            redirectAttributes.addFlashAttribute("errors_soDT", "Số điện thoại phải là 10-11 chữ số!");
            valid = false;
        }
        if (diaChi.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("errors_diaChi", "Địa chỉ không được để trống!");
            valid = false;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date ngaySinhDate;
        try {
            ngaySinhDate = formatter.parse(ngaySinh);
        } catch (ParseException e) {
            redirectAttributes.addFlashAttribute("errors_ngaySinh", "Ngày sinh không hợp lệ!");
            valid = false;
            ngaySinhDate = null;
        }

        if (!valid) {
            redirectAttributes.addFlashAttribute("message", new Message("error", "Cập nhật thất bại!"));
            return "redirect:/customer/account.htm";
        }

        String photoPath = null;
        if (!photo.isEmpty()) {
            String fileName = photo.getOriginalFilename();
            if (!fileName.matches(".*\\.(jpg|jpeg|png|gif)$")) {
                redirectAttributes.addFlashAttribute("message",
                        new Message("error", "Chỉ chấp nhận file ảnh (jpg, jpeg, png, gif)!"));
                return "redirect:/customer/account.htm";
            }
            try {
                photoPath = context.getRealPath("/uploads/" + fileName);
                photo.transferTo(new File(photoPath));
                khachHang.setAvatar(fileName);
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("message",
                        new Message("error", "Lỗi lưu file ảnh!"));
                return "redirect:/customer/account.htm";
            }
        }

        khachHang.setTenKH(tenKH.trim());
        khachHang.setGioiTinh(gioiTinh);
        khachHang.setSoDT(soDT.trim());
        khachHang.setNgaySinh(ngaySinhDate);
        khachHang.setDiaChi(diaChi.trim());

        Session dbSession = factory.getCurrentSession();
        dbSession.update(khachHang);

        redirectAttributes.addFlashAttribute("message", new Message("success", "Cập nhật thành công!"));
        return "redirect:/customer/account.htm";
    }

    @RequestMapping(value = "/change/password", method = RequestMethod.POST)
    public String updatePass(ModelMap model, HttpSession session, HttpServletRequest request,
                            RedirectAttributes redirectAttributes) throws NoSuchAlgorithmException {
        KhachHang khachHang = (KhachHang) session.getAttribute("user");
        TaiKhoan taiKhoan = (TaiKhoan) session.getAttribute("tk");

        if (khachHang == null || taiKhoan == null) {
            redirectAttributes.addFlashAttribute("message",
                    new Message("error", "Vui lòng đăng nhập để thay đổi mật khẩu!"));
            return "redirect:/login.htm";
        }

        String oldPass = request.getParameter("oldPass");
        String newPass = request.getParameter("newPass");
        String cfnewPass = request.getParameter("confirmPass");

        if (oldPass == null || oldPass.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("message",
                    new Message("error", "Mật khẩu cũ không được để trống!"));
            return "redirect:/customer/account.htm";
        }

        if (newPass == null || newPass.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("message",
                    new Message("error", "Mật khẩu mới không được để trống!"));
            return "redirect:/customer/account.htm";
        }

        if (newPass.trim().length() < 8) {
            redirectAttributes.addFlashAttribute("message",
                    new Message("error", "Mật khẩu mới phải có ít nhất 8 ký tự!"));
            return "redirect:/customer/account.htm";
        }

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(oldPass.trim().getBytes());
        byte[] digest = md.digest();
        String oldPassHash = func.bytesToHex(digest).toLowerCase();

        if (!oldPassHash.equals(taiKhoan.getPassword().trim())) {
            redirectAttributes.addFlashAttribute("message",
                    new Message("error", "Mật khẩu cũ không đúng!"));
            return "redirect:/customer/account.htm";
        }

        if (!newPass.equals(cfnewPass)) {
            redirectAttributes.addFlashAttribute("message",
                    new Message("error", "Mật khẩu xác nhận không khớp!"));
            return "redirect:/customer/account.htm";
        }

        if (newPass.trim().matches("^(01234567|12345678|password)$")) {
            redirectAttributes.addFlashAttribute("message",
                    new Message("error", "Mật khẩu quá yếu, vui lòng chọn mật khẩu khác!"));
            return "redirect:/customer/account.htm";
        }

        md.reset();
        md.update(newPass.trim().getBytes());
        byte[] digestNew = md.digest();
        String newPassHash = func.bytesToHex(digestNew).toLowerCase();

        taiKhoan.setPassword(newPassHash);
        Session dbSession = factory.getCurrentSession();
        dbSession.update(taiKhoan);

        session.setAttribute("mk", newPass);

        redirectAttributes.addFlashAttribute("message",
                new Message("success", "Đổi mật khẩu thành công!"));
        return "redirect:/customer/account.htm";
    }

    public Phim layPhim(Integer id) {
        Session session = factory.getCurrentSession();
        String hql = "FROM Phim WHERE MaPhim = :id";
        Query query = session.createQuery(hql);
        query.setParameter("id", id);
        return (Phim) query.uniqueResult();
    }

    public LichChieu layLC(Integer id) {
        Session session = factory.getCurrentSession();
        String hql = "FROM LichChieu WHERE MaSuatChieu = :id";
        Query query = session.createQuery(hql);
        query.setParameter("id", id);
        return (LichChieu) query.uniqueResult();
    }

    public ChiTietGhePhong LayIdGhe(Integer id) {
        Session session = factory.getCurrentSession();
        String hql = "FROM ChiTietGhePhong WHERE IdGheDat = :id";
        Query query = session.createQuery(hql);
        query.setParameter("id", id);
        return (ChiTietGhePhong) query.uniqueResult();
    }

    public ChiTietGhePhong layThongTinGhe(String tenGhe, String maPhong) {
        Session session = factory.getCurrentSession();
        String hql = "SELECT ct FROM ChiTietGhePhong ct JOIN ct.ghe g JOIN ct.phong p WHERE g.tenGhe = :tenGhe AND p.maPhong = :maPhong";
        Query query = session.createQuery(hql);
        query.setParameter("tenGhe", tenGhe);
        query.setParameter("maPhong", maPhong);
        query.setTimeout(60);
        return (ChiTietGhePhong) query.uniqueResult();
    }

    public List<Ve> layVe(Integer id) {
        Session session = factory.getCurrentSession();
        String hql = "FROM Ve WHERE dsLichChieu.maSC = :id";
        Query query = session.createQuery(hql);
        query.setParameter("id", id);
        return query.list();
    }

    public List<Ve> DanhSachVeDat(Integer maSC, List<Integer> idsGheDat, String maPhong) {
        Session session = factory.getCurrentSession();
        String hql = "FROM Ve v WHERE v.dsLichChieu.maSC = :maSC AND v.chiTietGhePhong.idGheDat IN (:idsGheDat) AND v.chiTietGhePhong.phong.maPhong = :maPhong";
        Query query = session.createQuery(hql);
        query.setParameter("maSC", maSC);
        query.setParameterList("idsGheDat", idsGheDat);
        query.setParameter("maPhong", maPhong);
        return query.list();
    }

    public List<Ve> layDanhSachVeTonTai(Integer maSC, List<Integer> idsGheDat, boolean trangThaiDat) {
        Session session = factory.getCurrentSession();
        String hql = "FROM Ve v WHERE v.dsLichChieu.maSC = :maSC AND v.chiTietGhePhong.idGheDat IN (:idsGheDat) AND v.trangThaiDat = :trangThaiDat";
        Query query = session.createQuery(hql);
        query.setParameter("maSC", maSC);
        query.setParameterList("idsGheDat", idsGheDat);
        query.setParameter("trangThaiDat", trangThaiDat);
        return query.list();
    }

    @RequestMapping("/danh-sach-ghe/{maSC}")
    public String danhSachGhe(@PathVariable("maSC") Integer maSC, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        KhachHang khachHang = (KhachHang) session.getAttribute("user");
        if (khachHang == null) {
            redirectAttributes.addFlashAttribute("message",
                    new Message("error", "Vui lòng đăng nhập để xem danh sách ghế!"));
            return "redirect:/login.htm";
        }

        LichChieu lc = layLC(maSC);
        if (lc == null) {
            redirectAttributes.addFlashAttribute("message",
                    new Message("error", "Không tìm thấy lịch chiếu!"));
            return "redirect:/error.htm";
        }

        Session dbSession = factory.getCurrentSession();
        String hqlGhe = "FROM ChiTietGhePhong WHERE phong.maPhong = :maPhong";
        Query queryGhe = dbSession.createQuery(hqlGhe);
        queryGhe.setParameter("maPhong", lc.getDsPhong().getMaPhong());
        List<ChiTietGhePhong> danhSachGhe = queryGhe.list();

        String hqlVe = "SELECT ct, COALESCE(v.trangThaiDat, false), v.chiTietGhePhong.idGheDat " +
                       "FROM ChiTietGhePhong ct LEFT JOIN ct.veList v " +
                       "WHERE v.dsLichChieu.maSC = :maSC OR v.dsLichChieu.maSC IS NULL";
        Query queryVe = dbSession.createQuery(hqlVe);
        queryVe.setParameter("maSC", maSC);
        List<Object[]> danhSachVe = queryVe.list();

        Map<Integer, Boolean> trangThaiDatMap = danhSachVe.stream()
                .filter(chitietVe -> chitietVe[0] != null)
                .collect(Collectors.toMap(
                        chitietVe -> (Integer) chitietVe[2],
                        chitietVe -> (Boolean) chitietVe[1],
                        (existing, replacement) -> existing));

        Map<String, List<Map<String, Object>>> gheTheoHang = danhSachGhe.stream()
                .filter(chitiet -> chitiet != null)
                .collect(Collectors.groupingBy(
                        chitiet -> chitiet.getGhe().getTenGhe().substring(0, 1),
                        Collectors.mapping(chitiet -> {
                            Map<String, Object> gheInfo = new HashMap<>();
                            gheInfo.put("idGhe", chitiet.getIdGheDat());
                            gheInfo.put("tenGhe", chitiet.getGhe().getTenGhe());
                            gheInfo.put("trangThaiDat", trangThaiDatMap.getOrDefault(chitiet.getIdGheDat(), false));
                            gheInfo.put("gia", chitiet.getLoaiGhe().getGia());
                            return gheInfo;
                        }, Collectors.toList())));

        model.addAttribute("gheTheoHang", gheTheoHang);
        model.addAttribute("lc", lc);
        return "customer/danh-sach-ghe";
    }

    @RequestMapping("/payment/{maSC}.htm")
    public String payment(Model model, @PathVariable("maSC") Integer maSC, HttpSession session, RedirectAttributes redirectAttributes) {
        KhachHang khachHang = (KhachHang) session.getAttribute("user");
        TaiKhoan taiKhoan = (TaiKhoan) session.getAttribute("tk");

        if (khachHang == null || taiKhoan == null) {
            redirectAttributes.addFlashAttribute("message",
                    new Message("error", "Vui lòng đăng nhập để đặt vé!"));
            return "redirect:/login.htm";
        }

        Session dbSession = factory.getCurrentSession();
        LichChieu lc = layLC(maSC);
        if (lc == null) {
            redirectAttributes.addFlashAttribute("message",
                    new Message("error", "Không tìm thấy lịch chiếu!"));
            return "redirect:/error.htm";
        }

        Phim phim = layPhim(lc.getDsPhim().getMaPhim());
        Query query = dbSession.createQuery(
                "SELECT ctdg.phanTramThayDoi, ctdg.thaydoigia.lyDo " +
                "FROM ChiTietThayDoiGia ctdg WHERE ctdg.dsLichChieu.maSC = :maSC");
        query.setParameter("maSC", maSC);
        Object[] result = (Object[]) query.uniqueResult();

        Float phanTramThayDoi = result != null ? (Float) result[0] : 0f;
        String lyDoThayDoi = result != null ? (String) result[1] : "";

        model.addAttribute("user", khachHang);
        model.addAttribute("tk", taiKhoan);
        model.addAttribute("lv", layLVKH(khachHang.getMaKH()));
        model.addAttribute("login", true);
        model.addAttribute("phim", phim);
        model.addAttribute("lc", lc);
        model.addAttribute("phantramthaydoi", phanTramThayDoi);
        model.addAttribute("lydothaydoi", lyDoThayDoi);

        danhSachGhe(maSC, model, session, redirectAttributes);
        return "customer/payment";
    }

    @RequestMapping("/payment_1/{maSC}/{ghe}.htm")
    public String payment1(ModelMap model, @PathVariable("maSC") Integer maSC, @PathVariable("ghe") String ghe,
                          HttpSession session, RedirectAttributes redirectAttributes) {
        KhachHang khachHang = (KhachHang) session.getAttribute("user");
        if (khachHang == null) {
            redirectAttributes.addFlashAttribute("message",
                    new Message("error", "Vui lòng đăng nhập để đặt vé!"));
            return "redirect:/login.htm";
        }

        LichChieu lc = layLC(maSC);
        if (lc == null) {
            redirectAttributes.addFlashAttribute("message",
                    new Message("error", "Không tìm thấy lịch chiếu!"));
            return "redirect:/error.htm";
        }

        List<String> danhSachGhe = Arrays.asList(ghe.split(","));
        List<Ve> danhSachVe = new ArrayList<>();
        List<ChiTietGhePhong> thongTinGheDat = new ArrayList<>();
        List<Integer> idsGheDat = new ArrayList<>();
        double tongTien = 0;

        Session dbSession = factory.getCurrentSession();
        Query query = dbSession.createQuery(
                "SELECT ctdg.phanTramThayDoi, ctdg.thaydoigia.lyDo " +
                "FROM ChiTietThayDoiGia ctdg WHERE ctdg.dsLichChieu.maSC = :maSC");
        query.setParameter("maSC", maSC);
        Object[] result = (Object[]) query.uniqueResult();

        Float phanTramThayDoi = result != null ? (Float) result[0] : 0f;
        String lyDoThayDoi = result != null ? (String) result[1] : "";

        for (String tenGhe : danhSachGhe) {
            ChiTietGhePhong gheDat = layThongTinGhe(tenGhe, lc.getDsPhong().getMaPhong());
            if (gheDat != null) {
                thongTinGheDat.add(gheDat);
                idsGheDat.add(gheDat.getIdGheDat());
            }
        }

        List<Ve> veTonTaiList = layDanhSachVeTonTai(maSC, idsGheDat, false);
        Map<Integer, Ve> veTonTaiMap = veTonTaiList.stream()
                .collect(Collectors.toMap(v -> v.getChiTietGhePhong().getIdGheDat(), v -> v));

        for (ChiTietGhePhong gheDat : thongTinGheDat) {
            Ve veTonTai = veTonTaiMap.get(gheDat.getIdGheDat());
            if (veTonTai != null) {
                veTonTai.setTrangThaiDat(true);
                dbSession.update(veTonTai);
                danhSachVe.add(veTonTai);
            } else {
                Ve v = new Ve();
                v.setDsLichChieu(lc);
                v.setNgayBan(new Date());
                v.setTrangThaiDat(true);
                v.setChiTietGhePhong(gheDat);
                dbSession.save(v);
                danhSachVe.add(v);
            }

            double gia = gheDat.getLoaiGhe().getGia();
            if (phanTramThayDoi != 0) {
                gia *= phanTramThayDoi;
            }
            tongTien += gia;
        }

        model.addAttribute("lc", lc);
        model.addAttribute("danhSachVe", danhSachVe);
        model.addAttribute("ghe", danhSachGhe);
        model.addAttribute("tongTien", tongTien);
        model.addAttribute("login", true);
        model.addAttribute("user", khachHang);
        model.addAttribute("phantramthaydoi", phanTramThayDoi);
        model.addAttribute("lydothaydoi", lyDoThayDoi);

        return "customer/payment_1";
    }

    @RequestMapping(value = "/update-ticket-status", method = RequestMethod.POST)
    public String updateTicketStatus(@RequestParam Integer maSC, @RequestParam String ghe,
                                    @RequestParam String maPhong, HttpSession session, RedirectAttributes redirectAttributes) {
        KhachHang khachHang = (KhachHang) session.getAttribute("user");
        if (khachHang == null) {
            redirectAttributes.addFlashAttribute("message",
                    new Message("error", "Vui lòng đăng nhập để cập nhật trạng thái vé!"));
            return "redirect:/login.htm";
        }

        LichChieu lc = layLC(maSC);
        if (lc == null) {
            redirectAttributes.addFlashAttribute("message",
                    new Message("error", "Không tìm thấy lịch chiếu!"));
            return "redirect:/error.htm";
        }

        List<String> danhSachGhe = Arrays.asList(ghe.split(","));
        List<Integer> idsGheDat = new ArrayList<>();
        for (String tenGhe : danhSachGhe) {
            ChiTietGhePhong gheDat = layThongTinGhe(tenGhe, lc.getDsPhong().getMaPhong());
            if (gheDat != null) {
                idsGheDat.add(gheDat.getIdGheDat());
            }
        }

        Session dbSession = factory.getCurrentSession();
        List<Ve> danhSachVe = DanhSachVeDat(maSC, idsGheDat, maPhong);

        for (Ve ve : danhSachVe) {
            ve.setTrangThaiDat(false);
            dbSession.update(ve);
        }

        return "redirect:/customer/payment_1/" + maSC + ".htm";
    }

    @RequestMapping(value = "/cancel-booking", method = RequestMethod.POST)
    public String cancelBooking(@RequestParam Integer maSC, @RequestParam String ghe, HttpSession session, RedirectAttributes redirectAttributes) {
        KhachHang khachHang = (KhachHang) session.getAttribute("user");
        if (khachHang == null) {
            redirectAttributes.addFlashAttribute("message",
                    new Message("error", "Vui lòng đăng nhập để hủy đặt vé!"));
            return "redirect:/login.htm";
        }

        LichChieu lc = layLC(maSC);
        if (lc == null) {
            redirectAttributes.addFlashAttribute("message",
                    new Message("error", "Không tìm thấy lịch chiếu!"));
            return "redirect:/error.htm";
        }

        List<String> danhSachGhe = Arrays.asList(ghe.split(","));
        List<Integer> idsGheDat = new ArrayList<>();
        for (String tenGhe : danhSachGhe) {
            ChiTietGhePhong gheDat = layThongTinGhe(tenGhe, lc.getDsPhong().getMaPhong());
            if (gheDat != null) {
                idsGheDat.add(gheDat.getIdGheDat());
            }
        }

        Session dbSession = factory.getCurrentSession();
        List<Ve> danhSachVe = DanhSachVeDat(maSC, idsGheDat, lc.getDsPhong().getMaPhong());

        for (Ve ve : danhSachVe) {
            ve.setTrangThaiDat(false);
            dbSession.update(ve);
        }

        return "redirect:/customer/payment/" + maSC + ".htm";
    }

    @RequestMapping("/payment_2/{maSC}/{ghe}.htm")
    public String payment2(ModelMap model, @PathVariable("maSC") Integer maSC, @PathVariable("ghe") String ghe,
                          HttpSession session, RedirectAttributes redirectAttributes) {
        KhachHang khachHang = (KhachHang) session.getAttribute("user");
        if (khachHang == null) {
            redirectAttributes.addFlashAttribute("message",
                    new Message("error", "Vui lòng đăng nhập để xác nhận đặt vé!"));
            return "redirect:/login.htm";
        }

        LichChieu lc = layLC(maSC);
        if (lc == null) {
            redirectAttributes.addFlashAttribute("message",
                    new Message("error", "Không tìm thấy lịch chiếu!"));
            return "redirect:/error.htm";
        }

        List<String> danhSachGhe = Arrays.asList(ghe.split(","));
        List<Ve> danhSachVe = new ArrayList<>();
        List<Integer> idsGheDat = new ArrayList<>();
        double tongTien = 0;

        Session dbSession = factory.getCurrentSession();
        Query query = dbSession.createQuery(
                "SELECT ctdg.phanTramThayDoi, ctdg.thaydoigia.lyDo " +
                "FROM ChiTietThayDoiGia ctdg WHERE ctdg.dsLichChieu.maSC = :maSC");
        query.setParameter("maSC", maSC);
        Object[] result = (Object[]) query.uniqueResult();

        Float phanTramThayDoi = result != null ? (Float) result[0] : 0f;
        String lyDoThayDoi = result != null ? (String) result[1] : "";

        for (String tenGhe : danhSachGhe) {
            ChiTietGhePhong gheDat = layThongTinGhe(tenGhe, lc.getDsPhong().getMaPhong());
            if (gheDat != null) {
                idsGheDat.add(gheDat.getIdGheDat());
            }
        }

        List<Ve> veTonTai = DanhSachVeDat(maSC, idsGheDat, lc.getDsPhong().getMaPhong());
        for (Ve ve : veTonTai) {
            ve.setKhachHang(khachHang);
            ve.setTrangThaiDat(true);
            dbSession.update(ve);
            danhSachVe.add(ve);

            double gia = ve.getChiTietGhePhong().getLoaiGhe().getGia();
            if (phanTramThayDoi != 0) {
                gia *= phanTramThayDoi;
            }
            tongTien += gia;
        }

        model.addAttribute("lc", lc);
        model.addAttribute("danhSachVe", danhSachVe);
        model.addAttribute("ghe", danhSachGhe);
        model.addAttribute("tongTien", tongTien);
        model.addAttribute("login", true);
        model.addAttribute("user", khachHang);
        model.addAttribute("phantramthaydoi", phanTramThayDoi);
        model.addAttribute("lydothaydoi", lyDoThayDoi);

        return "customer/payment_2";
    }
}