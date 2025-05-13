package admin.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import poly.entity.ChiNhanh;
import poly.entity.NhanVien;
import poly.entity.TaiKhoan;

@Transactional
@Controller
@RequestMapping("/staff/")
public class NhanVienShowController {
    @Autowired
    SessionFactory factory;

    @Autowired
    ServletContext context;

    @RequestMapping("profile")
    public String profile(ModelMap model, HttpSession session, RedirectAttributes redirectAttributes) {
        // Lấy thông tin từ session
        TaiKhoan taiKhoan = (TaiKhoan) session.getAttribute("tk");
        NhanVien nhanVien = (NhanVien) session.getAttribute("user");
        String matKhau = (String) session.getAttribute("mk");

        // Kiểm tra xem người dùng đã đăng nhập chưa
        if (taiKhoan == null || nhanVien == null) {
            redirectAttributes.addFlashAttribute("message",
                    new Message("error", "Vui lòng đăng nhập để xem thông tin!"));
            return "redirect:/login.htm";
        }

        // Thêm thông tin vào model
        model.addAttribute("tk", taiKhoan);
        model.addAttribute("user", nhanVien);
        model.addAttribute("mk", matKhau);

        // Lấy thông tin chi nhánh
        ChiNhanh chiNhanh = nhanVien.getChiNhanh();
        model.addAttribute("chiNhanh", chiNhanh);

        return "staff/profile";
    }

    @RequestMapping("password")
    public String change(ModelMap model, HttpSession session, RedirectAttributes redirectAttributes) {
        // Lấy thông tin từ session
        TaiKhoan taiKhoan = (TaiKhoan) session.getAttribute("tk");
        NhanVien nhanVien = (NhanVien) session.getAttribute("user");

        // Kiểm tra xem người dùng đã đăng nhập chưa
        if (taiKhoan == null || nhanVien == null) {
            redirectAttributes.addFlashAttribute("message",
                    new Message("error", "Vui lòng đăng nhập để thay đổi mật khẩu!"));
            return "redirect:/login.htm";
        }

        // Thêm thông tin vào model
        model.addAttribute("tk", taiKhoan);
        model.addAttribute("user", nhanVien);

        return "staff/password";
    }
}