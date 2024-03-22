package kr.go.seoul.noper2.controller;

import kr.go.seoul.noper2.domain.User;
import kr.go.seoul.noper2.dto.LoginDTO;
import kr.go.seoul.noper2.dto.SearchParamDTO;
import kr.go.seoul.noper2.dto.UserDTO;
import kr.go.seoul.noper2.global.auth.UserAuthentication;
import kr.go.seoul.noper2.service.CodeService;
import kr.go.seoul.noper2.service.LoginService;
import kr.go.seoul.noper2.service.UserService;
import kr.go.seoul.noper2.util.DateFormat;
import kr.go.seoul.noper2.util.annotations.MenuId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UserController {
    private final LoginService loginService;
    private final CodeService codeService;
    private final UserService userService;

    @GetMapping("/find-id")
    public String findId(Model model) {
        model.addAttribute("result", 0);
        return "pages/auth/findID";
    }
    @GetMapping("/find-pw")
    public String findPw(Model model) {
        model.addAttribute("result", 0);
        return "pages/auth/findPW";
    }
    @PostMapping("/find-id")
    public String findId(LoginDTO.FindUserIdRequestDTO dto, Model model) throws NoSuchElementException, SQLException, IOException {
        LoginDTO.FindUserIdResponseDTO responseDTO = loginService.getUserIdAndRegistTs(dto);
        model.addAttribute("id", responseDTO.getId());
        model.addAttribute("date", DateFormat.localDateTime(responseDTO.getDate()));
        model.addAttribute("result", responseDTO.getBool());
        return "pages/auth/findID";
    }

    @PostMapping("/find-pw")
    public String findPw(LoginDTO.FindUserPwRequestDTO dto, Model model) throws NoSuchElementException, SQLException, IOException {
        LoginDTO.FindUserPwResponseDTO responseDTO = loginService.userPwCheck(dto);
        model.addAttribute("result", responseDTO.getBool());
        model.addAttribute("id", responseDTO.getId());
        return "pages/auth/findPW";
    }

    @GetMapping("/access-denied")
    public String accessDenied(HttpServletRequest request, Model model) {
        String referer = request.getHeader("Referer");
        model.addAttribute("referer", referer != null ? referer : "/");
        return "pages/auth/blank";
    }
    @GetMapping("/login-success")
    public String loginSuccess(@AuthenticationPrincipal UserAuthentication auth, HttpServletRequest request) throws NoSuchElementException, SQLException, IOException {
        log.debug("Login Success : {}", auth.getUserId());
        String ip = request.getRemoteAddr();
        if (!ip.equals("0:0:0:0:0:0:0:1")) {
            loginService.saveLoginHistory(auth.getUserId(), ip);
        }
        return "redirect:/";
    }
    @GetMapping("/logout-success")
    public String logoutSuccess() {
        log.debug("Logout Success");
        return "redirect:/logout";
    }
    @GetMapping("/login-fail")
    public String loginFail(@RequestParam("error") String error,
                            HttpServletResponse response,
                            RedirectAttributes redirectAttributes) {
        Cookie errorCookie = new Cookie("error", error);
        response.addCookie(errorCookie);
        redirectAttributes.addFlashAttribute("loginFailRedirect", true);
        return "redirect:/";
    }
    @MenuId("ML")
    @GetMapping("/admin/session-management")
    public String sessionManagement() {
        return "pages/auth/sessionManagement";
    }

    @MenuId("NONE")
    @GetMapping("/@{userId}")
    public String myPage(@PathVariable String userId, @AuthenticationPrincipal UserAuthentication auth, Model model) {
        if (!userId.equals(auth.getUserId())) return "redirect:/";
        UserDTO user = userService.selectUserView(userId, "register");
        model.addAttribute("user", user);
        return "pages/auth/myPage";
    }
}
