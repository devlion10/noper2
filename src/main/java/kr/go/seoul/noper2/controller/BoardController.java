package kr.go.seoul.noper2.controller;

import kr.go.seoul.noper2.dto.*;
import kr.go.seoul.noper2.global.auth.UserAuthentication;
import kr.go.seoul.noper2.service.CodeService;
import kr.go.seoul.noper2.service.UserService;
import kr.go.seoul.noper2.util.annotations.MenuId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {
    private final UserService userService;
    private final CodeService codeService;


    @MenuId("NONE")
    @RequestMapping("/user/list")
    public String registerUserList(@AuthenticationPrincipal UserAuthentication auth, SearchParamDTO param, Model model) {
        List<CodeDTO> skk = codeService.findSkkList();
        model.addAttribute("searchParam", param);
        model.addAttribute("skk", skk);
        if (param.getSkkCd() != null) {
            List<BjdongDTO> bjd = codeService.findBjdNameListBySkkCd(param.getSkkCd());
            model.addAttribute("bjd", bjd);
        }
        return "pages/board/userAllowList";
    }

    @MenuId("NONE")
    @RequestMapping("/user/info")
    public String registerUserInfo(@AuthenticationPrincipal UserAuthentication auth, String userId, Model model) {
        UserDTO user = userService.selectUserView(userId, "join");
        model.addAttribute("user", user);
        model.addAttribute("admin", auth != null && auth.getIsAdmin());
        model.addAttribute("userCk", auth != null && auth.getUserId().equals(user.getRegistId()));
        return "pages/board/userAllowInfo";
    }

    @MenuId("NONE")
    @GetMapping("/user/add")
    public String registerUserAdd(Model model) {
        UserDTO user = new UserDTO();
        user.setEMail("none");
        model.addAttribute("user", user);
        model.addAttribute("skk", codeService.findSkkList());
        model.addAttribute("permission", userService.selectPermissionList(new SearchParamDTO(), null, null).stream().filter(p -> !"NB0001".equals(p.getPerId()) && !"AD0001".equals(p.getPerId()) && !"AD0002".equals(p.getPerId())).collect(Collectors.toList()));
        return "pages/board/userAllowAdd";
    }

    @MenuId("NONE")
    @PostMapping("/user/modify")
    public String registerUserModify(String userId, Model model) {
        UserDTO user = userService.selectUser(userId, "join");
        model.addAttribute("user", user);
        model.addAttribute("skk", codeService.findSkkList());
        model.addAttribute("bjd", codeService.findBjdNameListBySkkCd(user.getSkkCd()));
        model.addAttribute("dept", codeService.findDptList(user.getSkkCd()));
        model.addAttribute("permission", userService.selectPermissionList(new SearchParamDTO(), null, null).stream().filter(p -> !"NB0001".equals(p.getPerId()) && !"AD0001".equals(p.getPerId())).collect(Collectors.toList()));
        return "pages/board/userAllowAdd";
    }

    @MenuId("NONE")
    @GetMapping("/GPKI")
    public String GPKIInformation() {
        return "pages/board/GPKIInformation";
    }
}
