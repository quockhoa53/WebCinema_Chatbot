package admin.controller;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import poly.entity.NhanVien;
import poly.entity.TaiKhoan;

@Transactional
@Controller
@RequestMapping("/admin")
public class adminController {
    @Autowired
    SessionFactory factory;

    @RequestMapping(value = "/edit/employee/{maNV}/{email}.htm", method = RequestMethod.POST)
    public String edit(ModelMap model,
                      @RequestParam("tenNV") String tenNV,
                      @RequestParam("soDT") String soDT,
                      @RequestParam("diaChi") String diaChi,
                      @PathVariable("maNV") Integer maNV,
                      @PathVariable("email") String email,
                      RedirectAttributes redirectAttributes,
                      HttpSession session) {
        // Lấy thông tin từ session thay vì biến static
        TaiKhoan taiKhoan = (TaiKhoan) session.getAttribute("tk");
        NhanVien nhanVien = (NhanVien) session.getAttribute("user");

        // Kiểm tra xem người dùng đã đăng nhập chưa
        if (taiKhoan == null || nhanVien == null || !nhanVien.getMaNV().equals(maNV)) {
            redirectAttributes.addFlashAttribute("message",
                    new Message("error", "Bạn không có quyền chỉnh sửa thông tin này!"));
            return "redirect:/admin/profile.htm";
        }

        // Xác thực đầu vào
        if (tenNV == null || tenNV.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("message",
                    new Message("error", "Tên nhân viên không được để trống!"));
            return "redirect:/admin/profile.htm";
        }

        if (soDT == null || !soDT.trim().matches("\\d{10}")) {
            redirectAttributes.addFlashAttribute("message",
                    new Message("error", "Số điện thoại phải là 10 chữ số!"));
            return "redirect:/admin/profile.htm";
        }

        if (diaChi == null || diaChi.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("message",
                    new Message("error", "Địa chỉ không được để trống!"));
            return "redirect:/admin/profile.htm";
        }

        // Cập nhật thông tin vào cơ sở dữ liệu
        Session dbSession = factory.getCurrentSession();
        String hql = "UPDATE NhanVien SET TenNV = :tenNV, SDT = :soDT, DiaChi = :diaChi WHERE MaNV = :maNV";
        Query query = dbSession.createQuery(hql);
        query.setParameter("tenNV", tenNV.trim());
        query.setParameter("soDT", soDT.trim());
        query.setParameter("diaChi", diaChi.trim());
        query.setParameter("maNV", maNV);

        int affectedRows = query.executeUpdate();

        if (affectedRows > 0) {
            // Cập nhật đối tượng trong session
            nhanVien.setTenNV(tenNV.trim());
            nhanVien.setSoDT(soDT.trim());
            nhanVien.setDiaChi(diaChi.trim());
            session.setAttribute("user", nhanVien);

            redirectAttributes.addFlashAttribute("message",
                    new Message("success", "Cập nhật thành công!"));
        } else {
            redirectAttributes.addFlashAttribute("message",
                    new Message("error", "Cập nhật thất bại!"));
        }

        return "redirect:/admin/profile.htm";
    }
}