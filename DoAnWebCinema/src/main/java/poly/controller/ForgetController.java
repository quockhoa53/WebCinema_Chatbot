package poly.controller;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import admin.controller.Message;
import poly.bean.Validator_check;
import poly.entity.TaiKhoan;
import externalFunc.func;

@Transactional
@Controller
@RequestMapping("/forget")
public class ForgetController {
    @Autowired
    SessionFactory factory;

    @Autowired
    JavaMailSender mailer;

    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap model, HttpSession session) {
        // Kiểm tra trạng thái đăng nhập
        if (session.getAttribute("user") != null || session.getAttribute("tk") != null) {
            session.removeAttribute("user");
            session.removeAttribute("tk");
            session.removeAttribute("mk");
            model.addAttribute("message", new Message("info", "Bạn đã được đăng xuất để đặt lại mật khẩu."));
        }
        model.addAttribute("login", false);
        return "forget";
    }

    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public String reset(ModelMap model, @ModelAttribute("email") String email, RedirectAttributes redirectAttributes) {
        email = email != null ? email.trim() : "";
        if (email.isEmpty()) {
            redirectAttributes.addFlashAttribute("message",
                    new Message("error", "Email không được để trống!"));
            return "redirect:/forget.htm";
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            redirectAttributes.addFlashAttribute("message",
                    new Message("error", "Email không đúng định dạng!"));
            return "redirect:/forget.htm";
        }

        Session session = factory.getCurrentSession();
        String hql = "FROM TaiKhoan WHERE email = :email";
        Query query = session.createQuery(hql);
        query.setParameter("email", email);
        TaiKhoan taiKhoan = (TaiKhoan) query.uniqueResult();

        if (taiKhoan == null) {
            redirectAttributes.addFlashAttribute("message",
                    new Message("error", "Email không tồn tại!"));
            return "redirect:/forget.htm";
        }

        // Tạo mật khẩu ngẫu nhiên
        String newPassword = generateRandomPassword();
        String hashedPassword;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(newPassword.getBytes());
            byte[] digest = md.digest();
            hashedPassword = func.bytesToHex(digest).toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            redirectAttributes.addFlashAttribute("message",
                    new Message("error", "Lỗi hệ thống khi tạo mật khẩu!"));
            return "redirect:/forget.htm";
        }

        // Cập nhật mật khẩu
        String hqlUpdate = "UPDATE TaiKhoan SET password = :password WHERE email = :email";
        Query queryUpdate = session.createQuery(hqlUpdate);
        queryUpdate.setParameter("password", hashedPassword);
        queryUpdate.setParameter("email", email);
        int affectedRows = queryUpdate.executeUpdate();

        if (affectedRows == 0) {
            redirectAttributes.addFlashAttribute("message",
                    new Message("error", "Không thể cập nhật mật khẩu!"));
            return "redirect:/forget.htm";
        }

        // Gửi email
        try {
            MimeMessage mail = mailer.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setFrom("movie@support.com", "Movie Support");
            helper.setTo(email);
            helper.setReplyTo("movie@support.com", "Movie Support");
            helper.setSubject("LẤY LẠI MẬT KHẨU");
            String body = "Mật khẩu mới của bạn là: " + newPassword + "<br>" +
                          "Vui lòng đổi mật khẩu ngay sau khi đăng nhập.";
            helper.setText(body, true);
            mailer.send(mail);

            redirectAttributes.addFlashAttribute("message",
                    new Message("success", "Vào email của bạn để lấy mật khẩu mới!"));
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message",
                    new Message("error", "Không thể gửi email, vui lòng thử lại sau!"));
        }

        return "redirect:/forget.htm";
    }

    private String generateRandomPassword() {
        // Tạo mật khẩu ngẫu nhiên an toàn hơn
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return uuid.substring(0, 12); // Lấy 12 ký tự đầu
    }
}