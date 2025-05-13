package poly.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import admin.controller.Message;
import poly.entity.DanhGiaPhim;
import poly.entity.DaoDien;
import poly.entity.KhachHang;
import poly.entity.Phim;
import poly.entity.SuKien;

@Transactional
@Controller
@RequestMapping("/")
public class TrangChuController {

    @Autowired
    SessionFactory factory;

    @RequestMapping("trangchu")
    public String list(ModelMap model, HttpSession session) {
        updateMovieStatus();

        KhachHang khachHang = (KhachHang) session.getAttribute("user");

        List<Phim> phimList = getMovies();
        model.addAttribute("phimList", phimList);

        List<Phim> phimListHot = getMoviesHot();
        model.addAttribute("phimListHot", phimListHot);

        List<Phim> phimListNew = getMoviesNew();
        model.addAttribute("phimListNew", phimListNew);

        List<SuKien> sukienList = getSuKien();
        model.addAttribute("sukienList", sukienList);

        List<Phim> phimListDangChieu = getMoviesDangChieu();
        model.addAttribute("phimListDangChieu", phimListDangChieu);

        List<Phim> phimListSapChieu = getMoviesSapChieu();
        model.addAttribute("phimListSapChieu", phimListSapChieu);

        List<DaoDien> daoDienList = getListDaoDien();
        model.addAttribute("DaoDienList", daoDienList);

        model.addAttribute("user", khachHang);
        model.addAttribute("login", khachHang != null);

        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        model.addAttribute("currentDate", currentDate);

        Map<Integer, Object[]> thongKeDanhGiaMap = new HashMap<>();
        for (Phim phim : phimList) {
            Object[] thongKe = thongKeDanhGiaTheoPhim(phim.getMaPhim());
            thongKeDanhGiaMap.put(phim.getMaPhim(), thongKe);
        }
        model.addAttribute("thongKeDanhGiaMap", thongKeDanhGiaMap);

        Object[] phimBanChayNhat = getTopMovie();
        model.addAttribute("phimBanChayNhat", phimBanChayNhat);

        return "trangchu";
    }

    @RequestMapping("film/detail/{maPhim}.htm")
    public String payment(ModelMap model, @PathVariable("maPhim") Integer maPhim, HttpSession session, RedirectAttributes redirectAttributes) {
        Session dbSession = factory.getCurrentSession();
        String hql = "FROM Phim WHERE maPhim = :maPhim";
        Query query = dbSession.createQuery(hql);
        query.setParameter("maPhim", maPhim);
        Phim phim = (Phim) query.uniqueResult();

        if (phim == null) {
            redirectAttributes.addFlashAttribute("message",
                    new Message("error", "Không tìm thấy phim!"));
            return "redirect:/trangchu.htm";
        }

        KhachHang khachHang = (KhachHang) session.getAttribute("user");
        int ratingScore = 0;

        if (khachHang != null) {
            DanhGiaPhim existingRating = (DanhGiaPhim) dbSession.createQuery(
                    "FROM DanhGiaPhim WHERE phim.maPhim = :maPhim AND khachhang.maKH = :maKH")
                    .setParameter("maPhim", maPhim)
                    .setParameter("maKH", khachHang.getMaKH())
                    .uniqueResult();
            ratingScore = (existingRating != null) ? existingRating.getDiem() : 0;
        }

        model.addAttribute("ratingScore", ratingScore);
        model.addAttribute("phim", phim);
        model.addAttribute("user", khachHang);
        model.addAttribute("login", khachHang != null);

        return "detail";
    }

    public void updateMovieStatus() {
        Session session = factory.getCurrentSession();
        String sql = "{CALL SP_CapNhatTrangThaiPhim}";
        Query query = session.createSQLQuery(sql);
        query.executeUpdate();
    }

    public Object[] getTopMovie() {
        Session session = factory.getCurrentSession();
        String sql = "{CALL ThongKePhimBanChayNhat}";
        Query query = session.createSQLQuery(sql)
                .addScalar("MaPhim", StandardBasicTypes.INTEGER)
                .addScalar("TenPhim", StandardBasicTypes.STRING)
                .addScalar("SoLuongVe", StandardBasicTypes.INTEGER);
        List<Object[]> resultList = query.list();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    public Object[] thongKeDanhGiaTheoPhim(int maPhim) {
        Session session = factory.getCurrentSession();
        String hql = "{CALL ThongKeDanhGiaTheoPhim(:maPhim)}";
        Query query = session.createSQLQuery(hql)
                .setParameter("maPhim", maPhim);
        List<Object[]> resultList = query.list();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    public List<Phim> getMovies() {
        Session session = factory.getCurrentSession();
        String hql = "FROM Phim";
        Query query = session.createQuery(hql);
        return query.list();
    }

    public List<Phim> getMoviesHot() {
        Session session = factory.getCurrentSession();
        String hql = "FROM Phim ORDER BY diem DESC";
        Query query = session.createQuery(hql);
        query.setMaxResults(3);
        return query.list();
    }

    public List<Phim> getMoviesNew() {
        Session session = factory.getCurrentSession();
        String hql = "FROM Phim WHERE maTT != 0 ORDER BY ngayKhoiChieu DESC";
        Query query = session.createQuery(hql);
        return query.list();
    }

    public List<SuKien> getSuKien() {
        Session session = factory.getCurrentSession();
        String hql = "FROM SuKien";
        Query query = session.createQuery(hql);
        return query.list();
    }

    public List<Phim> getMoviesDangChieu() {
        Session session = factory.getCurrentSession();
        String hql = "FROM Phim WHERE ngayKhoiChieu <= :currentDate AND maTT = 1";
        Query query = session.createQuery(hql);
        query.setParameter("currentDate", new Date());
        return query.list();
    }

    public List<Phim> getMoviesSapChieu() {
        Session session = factory.getCurrentSession();
        String hql = "FROM Phim WHERE ngayKhoiChieu > :currentDate AND maTT = 2";
        Query query = session.createQuery(hql);
        query.setParameter("currentDate", new Date());
        return query.list();
    }

    public List<DaoDien> getListDaoDien() {
        Session session = factory.getCurrentSession();
        String hql = "FROM DaoDien";
        Query query = session.createQuery(hql);
        return query.list();
    }
}