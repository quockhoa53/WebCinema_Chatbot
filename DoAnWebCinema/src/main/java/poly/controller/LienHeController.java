package poly.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import poly.entity.KhachHang;

@Controller
public class LienHeController {
    @RequestMapping("lienhe")
    public String lienhe(ModelMap model, HttpSession session) {
        KhachHang khachHang = (KhachHang) session.getAttribute("user");

        model.addAttribute("user", khachHang);
        model.addAttribute("login", khachHang != null);

        return "lienhe";
    }
}