package poly.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import poly.entity.KhachHang;
import poly.entity.Phim;

@Transactional
@Controller
public class PhimController {
    @Autowired
    SessionFactory factory;

    @RequestMapping("phimsapchieu")
    public String list(@RequestParam(value = "tab", required = false, defaultValue = "home") String tab,
                       ModelMap model, HttpSession session, RedirectAttributes redirectAttributes) {
        // Lấy thông tin khách hàng từ session
        KhachHang khachHang = (KhachHang) session.getAttribute("user");

        // Nếu trang yêu cầu đăng nhập, kiểm tra và chuyển hướng
        // Comment đoạn này nếu trang phimsapchieu là công khai
        /*
        if (khachHang == null) {
            redirectAttributes.addFlashAttribute("message",
                    new Message("error", "Vui lòng đăng nhập để xem danh sách phim!"));
            return "redirect:/login.htm";
        }
        */

        List<Phim> phimListSapChieu = getMoviesSapChieu();
        model.addAttribute("phimListSapChieu", phimListSapChieu);

        List<Phim> phimListDangChieu = getMoviesDangChieu();
        model.addAttribute("phimListDangChieu", phimListDangChieu);

        model.addAttribute("tab", tab);
        model.addAttribute("user", khachHang); // Có thể là null nếu chưa đăng nhập
        String currentDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        model.addAttribute("currentDate", currentDate);

        return "phimsapchieu";
    }

    // Phim sắp chiếu
    public List<Phim> getMoviesSapChieu() {
        Session session = factory.getCurrentSession();
        String hql = "FROM Phim WHERE NgayKhoiChieu > :currentDate AND MaTT = 2";
        Query query = session.createQuery(hql);
        query.setParameter("currentDate", new Date());
        return query.list();
    }

    // Phim đang chiếu
    public List<Phim> getMoviesDangChieu() {
        Session session = factory.getCurrentSession();
        String hql = "FROM Phim WHERE NgayKhoiChieu <= :currentDate AND MaTT = 1";
        Query query = session.createQuery(hql);
        query.setParameter("currentDate", new Date());
        return query.list();
    }
}