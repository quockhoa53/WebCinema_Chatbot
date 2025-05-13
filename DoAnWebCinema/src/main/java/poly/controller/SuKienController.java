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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import admin.controller.Message;
import poly.entity.KhachHang;
import poly.entity.SuKien;

@Transactional
@Controller
public class SuKienController {
    @Autowired
    SessionFactory factory;

    @RequestMapping(value = "sukienDetail", method = RequestMethod.GET)
    public String SuKienDetail(@RequestParam("id") int id, ModelMap model, HttpSession session, RedirectAttributes redirectAttributes) {
        SuKien sukien = getSuKienById(id);
        if (sukien == null) {
            redirectAttributes.addFlashAttribute("message", new Message("error", "Không tìm thấy sự kiện!"));
            return "redirect:/trangchu.htm";
        }

        KhachHang khachHang = (KhachHang) session.getAttribute("user");

        model.addAttribute("sukien", sukien);
        model.addAttribute("user", khachHang);
        model.addAttribute("login", khachHang != null);
        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        model.addAttribute("currentDate", currentDate);

        return "sukienDetail";
    }

    public List<SuKien> getSuKien() {
        Session session = factory.getCurrentSession();
        String hql = "FROM SuKien";
        Query query = session.createQuery(hql);
        return query.list();
    }

    public SuKien getSuKienById(int id) {
        Session session = factory.getCurrentSession();
        String hql = "FROM SuKien WHERE MaSK = :id";
        Query query = session.createQuery(hql);
        query.setParameter("id", id);
        return (SuKien) query.uniqueResult();
    }
}