package poly.controller;

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

import poly.entity.KhachHang;
import poly.entity.Phim;

@Transactional
@Controller
public class home {
    @Autowired
    SessionFactory factory;

    @RequestMapping("/home")
    public String home(ModelMap model, HttpSession session) {
        KhachHang khachHang = (KhachHang) session.getAttribute("user");

        if (khachHang == null) {
            model.addAttribute("login", false);
        } else {
            model.addAttribute("login", true);
            model.addAttribute("user", khachHang);
        }

        List<Phim> phimList = getMovies();
        model.addAttribute("phimList", phimList);

        return "home";
    }

    // All phim
    public List<Phim> getMovies() {
        Session session = factory.getCurrentSession();
        String hql = "FROM Phim";
        Query query = session.createQuery(hql);
        return query.list();
    }
}