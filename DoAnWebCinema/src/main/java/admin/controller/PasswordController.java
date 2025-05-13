package admin.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import poly.entity.NhanVien;
import poly.entity.TaiKhoan;
import externalFunc.func;

@Transactional
@Controller
@RequestMapping("/admin/")
public class PasswordController {

    @Autowired
    SessionFactory factory;

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

        return "admin/password";
    }

    @RequestMapping(value = "/change/password", method = RequestMethod.POST)
    public String updatePass(ModelMap model, HttpSession session, HttpServletRequest request,
                            RedirectAttributes redirectAttributes) throws NoSuchAlgorithmException {
        // Lấy thông tin từ session
        TaiKhoan taiKhoan = (TaiKhoan) session.getAttribute("tk");
        String currentPassword = (String) session.getAttribute("mk");

        // Kiểm tra xem người dùng đã đăng nhập chưa
        if (taiKhoan == null) {
            redirectAttributes.addFlashAttribute("message",
                    new Message("error", "Vui lòng đăng nhập để thay đổi mật khẩu!"));
            return "redirect:/login.htm";
        }

        // Lấy tham số từ request
        String oldPass = request.getParameter("oldPass");
        String newPass = request.getParameter("newPass");
        String cfnewPass = request.getParameter("confirmPass");

        // Xác thực đầu vào
        if (oldPass == null || oldPass.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("message",
                    new Message("error", "Mật khẩu cũ không được để trống!"));
            return "redirect:/admin/password.htm";
        }

        if (newPass == null || newPass.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("message",
                    new Message("error", "Mật khẩu mới không được để trống!"));
            return "redirect:/admin/password.htm";
        }

        if (newPass.trim().length() < 8) {
            redirectAttributes.addFlashAttribute("message",
                    new Message("error", "Mật khẩu mới phải có ít nhất 8 ký tự!"));
            return "redirect:/admin/password.htm";
        }

        // Mã hóa mật khẩu cũ để so sánh
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(oldPass.trim().getBytes());
        byte[] digest = md.digest();
        String oldPassHash = func.bytesToHex(digest).toLowerCase();

        // So sánh mật khẩu cũ
        if (!oldPassHash.equals(taiKhoan.getPassword().trim())) {
            redirectAttributes.addFlashAttribute("message",
                    new Message("error", "Mật khẩu cũ không đúng!"));
            return "redirect:/admin/password.htm";
        }

        // Kiểm tra mật khẩu mới và xác nhận
        if (!newPass.equals(cfnewPass)) {
            redirectAttributes.addFlashAttribute("message",
                    new Message("error", "Mật khẩu xác nhận không khớp!"));
            return "redirect:/admin/password.htm";
        }

        // Kiểm tra mật khẩu yếu
        if (newPass.trim().matches("^(01234567|12345678|password)$")) {
            redirectAttributes.addFlashAttribute("message",
                    new Message("error", "Mật khẩu quá yếu, vui lòng chọn mật khẩu khác!"));
            return "redirect:/admin/password.htm";
        }

        // Mã hóa mật khẩu mới
        md.reset();
        md.update(newPass.trim().getBytes());
        byte[] digestNew = md.digest();
        String newPassHash = func.bytesToHex(digestNew).toLowerCase();

        // Cập nhật mật khẩu
        Session dbSession = factory.getCurrentSession();
        taiKhoan.setPassword(newPassHash);
        dbSession.update(taiKhoan);

        // Cập nhật mật khẩu trong session
        session.setAttribute("mk", newPass);

        redirectAttributes.addFlashAttribute("message",
                new Message("success", "Đổi mật khẩu thành công!"));
        return "redirect:/admin/password.htm";
    }
}