package poly.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import admin.controller.Message;
import poly.entity.DanhGiaPhim;
import poly.entity.KhachHang;
import poly.entity.Phim;

@Transactional
@Controller
public class SearchController {
    @Autowired
    SessionFactory factory;

    public Phim layPhim(String tenPhim) {
        Session session = factory.getCurrentSession();
        String hql = "FROM Phim WHERE tenPhim LIKE :tenPhim ORDER BY LENGTH(tenPhim) ASC";
        Query query = session.createQuery(hql);
        query.setParameter("tenPhim", "%" + tenPhim.trim() + "%");

        List<Phim> list = query.list();

        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @RequestMapping("/search")
    public String search(ModelMap model, @ModelAttribute("search") String search, HttpSession session, RedirectAttributes redirectAttributes) {
        Session dbSession = factory.getCurrentSession();
        String s = search.trim();
        Phim p = layPhim(s);
        if (p == null) {
            redirectAttributes.addFlashAttribute("message", new Message("error", "Không tìm thấy phim!"));
            return "redirect:/home.htm";
        }

        KhachHang khachHang = (KhachHang) session.getAttribute("user");
        int ratingScore = 0;

        if (khachHang != null) {
            DanhGiaPhim existingRating = (DanhGiaPhim) dbSession.createQuery(
                    "FROM DanhGiaPhim WHERE phim.maPhim = :maPhim AND khachhang.maKH = :maKH")
                    .setParameter("maPhim", p.getMaPhim())
                    .setParameter("maKH", khachHang.getMaKH())
                    .uniqueResult();
            ratingScore = (existingRating != null) ? existingRating.getDiem() : 0;
            redirectAttributes.addFlashAttribute("login", true);
        } else {
            redirectAttributes.addFlashAttribute("login", false);
        }

        redirectAttributes.addFlashAttribute("ratingScore", ratingScore);
        return "redirect:/film/detail/ma=" + p.getMaPhim() + ".htm";
    }

    @RequestMapping(value = "/saveRating", method = RequestMethod.POST)
    public String saveRating(@RequestParam("rating") int rating, @RequestParam("maPhim") int maPhim, HttpSession session, RedirectAttributes redirectAttributes) {
        KhachHang khachHang = (KhachHang) session.getAttribute("user");
        if (khachHang == null) {
            redirectAttributes.addFlashAttribute("message", new Message("error", "Vui lòng đăng nhập để đánh giá phim!"));
            return "redirect:/login.htm";
        }

        Session dbSession = factory.openSession();
        Transaction t = null;
        try {
            t = dbSession.beginTransaction();
            Phim phim = (Phim) dbSession.get(Phim.class, maPhim);
            if (phim == null) {
                redirectAttributes.addFlashAttribute("message", new Message("error", "Phim không tồn tại!"));
                return "redirect:/error.htm";
            }

            DanhGiaPhim existingRating = (DanhGiaPhim) dbSession.createQuery(
                    "FROM DanhGiaPhim WHERE phim.maPhim = :maPhim AND khachhang.maKH = :maKH")
                    .setParameter("maPhim", maPhim)
                    .setParameter("maKH", khachHang.getMaKH())
                    .uniqueResult();
            if (existingRating != null) {
                existingRating.setDiem(rating);
                dbSession.update(existingRating);
            } else {
                DanhGiaPhim danhGiaPhim = new DanhGiaPhim();
                danhGiaPhim.setDiem(rating);
                danhGiaPhim.setPhim(phim);
                danhGiaPhim.setKhachHang(khachHang);
                danhGiaPhim.setNgayDanhGia(new Date());
                dbSession.save(danhGiaPhim);
            }

            t.commit();
            return "redirect:/film/detail/ma=" + maPhim + ".htm";
        } catch (Exception e) {
            if (t != null) t.rollback();
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", new Message("error", "Lưu đánh giá thất bại!"));
            return "redirect:/error.htm";
        } finally {
            dbSession.close();
        }
    }
}