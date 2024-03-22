package kr.go.seoul.noper2.controller;

import kr.go.seoul.noper2.dto.*;
import kr.go.seoul.noper2.service.CodeService;
import kr.go.seoul.noper2.service.UserService;
import kr.go.seoul.noper2.util.TypeCasting;
import kr.go.seoul.noper2.util.annotations.MenuId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final CodeService codeService;

    @MenuId("MU")
    @RequestMapping("/user/list")
    public String userList(SearchParamDTO param, Model model) {
        model.addAttribute("searchParam", param);
        model.addAttribute("skk", codeService.findSkkList());
        if (param.getSkkCd() != null) {
            List<BjdongDTO> bjd = codeService.findBjdNameListBySkkCd(param.getSkkCd());
            model.addAttribute("bjd", bjd);
        }
        return "pages/admin/userRegisterList";
    }

    @MenuId("MU")
    @RequestMapping("/user/add")
    public String userAdd(String userId, SearchParamDTO param, Model model) {
        if (userId != null) {
            UserDTO user = userService.selectUser(userId, "register");
            model.addAttribute("user", user);
            if (user.getSkkCd() != null) {
                List<BjdongDTO> bjd = codeService.findBjdNameListBySkkCd(user.getSkkCd());
                model.addAttribute("bjd", bjd);
                model.addAttribute("dept",codeService.findDptList(user.getSkkCd()));
            }
        } else {
            UserDTO user = new UserDTO();
            user.setEMail("none");
            model.addAttribute("user", user);
        }
        model.addAttribute("searchParam", TypeCasting.dtoToMap(param));
        model.addAttribute("skk", codeService.findSkkList());
        model.addAttribute("permission", userService.selectPermissionList(new SearchParamDTO(), null, null));
        return "pages/admin/userRegisterAdd";
    }

    @MenuId("MU")
    @RequestMapping("/user/info")
    public String userInfo(String userId, SearchParamDTO param, Model model) {
        model.addAttribute("user", userService.selectUserView(userId, "register"));
        model.addAttribute("searchParam", TypeCasting.dtoToMap(param));
        return "pages/admin/userRegisterInfo";
    }

    @MenuId("MU")
    @RequestMapping("/user/modify")
    public String userModify(String userId, SearchParamDTO param, Model model) {
        UserDTO user = userService.selectUser(userId, "register");
        model.addAttribute("user", user);
        model.addAttribute("skk", codeService.findSkkList());
        model.addAttribute("dept", codeService.findDptList(user.getSkkCd()));
        model.addAttribute("searchParam", TypeCasting.dtoToMap(param));
        model.addAttribute("permission", userService.selectPermissionList(new SearchParamDTO(), null, null));
        model.addAttribute("bjd", codeService.findBjdNameListBySkkCd(user.getSkkCd()));
        return "pages/admin/userRegisterAdd";
    }

    @MenuId("MP")
    @RequestMapping("/permit/list")
    public String permitList(SearchParamDTO param, Model model) {
        List<CodeDTO> skk = codeService.findSkkList();
        model.addAttribute("searchParam", param);
        model.addAttribute("skk", skk);
        return "pages/admin/userPermitList";
    }

    @MenuId("MP")
    @RequestMapping("/permit/info")
    public String userPermitInfo(String perId, Model model) {
        model.addAttribute("perInfo", userService.selectPermission(perId));
        model.addAttribute("perList", userService.selectPermissionSelectedMenuList(perId));
        return "pages/admin/userPermitInfo";
    }

    @MenuId("MP")
    @RequestMapping("/permit/add")
    public String userPermitAdd(Model model) {
        model.addAttribute("perInfo", new PermissionDTO());
        model.addAttribute("flag", "등록");
        model.addAttribute("perList", new ArrayList<>());
        return "pages/admin/userPermitAdd";
    }

    @MenuId("MP")
    @RequestMapping("/permit/copy")
    public String userPermitCopy(String perId, Model model) {
        model.addAttribute("perInfo", new PermissionDTO());
        model.addAttribute("flag", "복사");
        model.addAttribute("perList", userService.selectPermissionSelectedMenuList(perId));
        return "pages/admin/userPermitAdd";
    }

    @MenuId("MP")
    @RequestMapping("/permit/modify")
    public String userPermitModify(String perId, Model model) {
        model.addAttribute("perInfo", userService.selectPermission(perId));
        model.addAttribute("flag", "수정");
        model.addAttribute("perList", userService.selectPermissionSelectedMenuList(perId));
        return "pages/admin/userPermitAdd";
    }
}
