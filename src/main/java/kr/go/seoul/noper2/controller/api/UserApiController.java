package kr.go.seoul.noper2.controller.api;

import kr.go.seoul.noper2.dto.*;
import kr.go.seoul.noper2.global.auth.UserAuthentication;
import kr.go.seoul.noper2.service.InterceptorService;
import kr.go.seoul.noper2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserApiController {
    private final UserService userService;
    private final InterceptorService interceptorService;

    @PostMapping("/join/create")
    public void createJoinUser(UserDTO user) {
        userService.createUser(user, "join");
    }

    @PostMapping("/register/create")
    public void createRegisterUser (@AuthenticationPrincipal UserAuthentication auth, UserDTO user) {
        user.setRegistId(auth.getUserId());
        userService.createUser(user, "register");
    }

    @PostMapping("/permit/create")
    public void createPermission (@AuthenticationPrincipal UserAuthentication auth, PermissionDTO permission, @RequestParam(value = "menuIds[]") List<String> menuIds, String parents) {
        permission.setRegistId(auth.getUserId());
        userService.createPermission(permission, menuIds, parents);
    }

    @PostMapping("/join/update")
    public void updateJoinUser (UserDTO user) {
        userService.updateUser(user, "join");
    }

    @PostMapping("/register/update")
    public void updateRegisterUser (@AuthenticationPrincipal UserAuthentication auth, UserDTO user) {
        user.setUpdateId(auth.getUserId());
        userService.updateUser(user, "register");
    }

    @PostMapping("/update/password")
    public String updateUserPassword (@AuthenticationPrincipal UserAuthentication auth, String currentPw, String newPw) throws NoSuchElementException {
        UserDTO user = new UserDTO();
        user.setUserId(auth.getUserId());
        user.setUserPw(newPw);
        return userService.updateUserPassword(user, currentPw, auth.getIsAdmin());
    }

    @PostMapping("/register/update/password")
    public String updateRegisterUserPassword (@AuthenticationPrincipal UserAuthentication auth, String userId, String currentPw, String newPw) throws NoSuchElementException {
        UserDTO user = new UserDTO();
        user.setUserId(userId);
        user.setUserPw(newPw);
        return userService.updateUserPassword(user, currentPw, auth.getIsAdmin());
    }

    @PostMapping("/permit/update")
    public void updatePermission (@AuthenticationPrincipal UserAuthentication auth, PermissionDTO permission, @RequestParam(value = "menuIds[]") List<String> menuIds) {
        permission.setUpdateId(auth.getUserId());
        userService.updatePermission(permission, menuIds);
    }

    @PostMapping("/permit/user/update")
    public void updatePermissionUser (String perId, @RequestParam(value = "userId[]") List<String> userId) {
        userService.updatePermissionUser(perId, userId);
    }

    @PostMapping("/join/allow")
    public void allowJoinUser(String userId) {
        userService.allowJoinUser(userId);
    }

    @PostMapping("/join/deny")
    public void denyJoinUser(UserDTO user) {
        userService.denyJoinUser(user);
    }

    @PostMapping("/permit/user/remove")
    public void removeUserPermission(@RequestParam(value = "userId[]") List<String> userId) {
        userService.removeUserPermission(userId);
    }

    @PostMapping("/join/delete")
    public void deleteJoinUser(String userId) {
        userService.deleteUser(userId, "join");
    }

    @PostMapping("/register/delete")
    public void deleteRegisterUser(String userId) {
        userService.deleteUser(userId, "register");
    }

    @PostMapping("/permit/delete")
    public void deletePermission (String perId) {
        userService.deletePermission(perId);
    }

    @GetMapping("/join/select")
    public UserDTO selectJoinUser(String userId) {
        return userService.selectUserView(userId, "join");
    }

    @GetMapping("/register/select")
    public UserDTO selectRegisterUser(String userId) {
        return userService.selectUserView(userId, "register");
    }

    @GetMapping("/permit/check/name")
    public boolean checkPermitName(String perName) {
        return userService.checkPermissionName(perName);
    }

    @GetMapping("/permit/select")
    public PermissionDTO selectPermission (String perId) {
        return userService.selectPermission(perId);
    }

    @GetMapping("/join/select/list")
    public List<UserDTO> selectJoinUserList(@AuthenticationPrincipal UserAuthentication auth, SearchParamDTO param) {
        if(auth == null || !auth.getIsAdmin()) {
            return userService.selectUserList(param, "guest");
        } else {
            return userService.selectUserList(param, "join");
        }
    }

    @GetMapping("/register/select/list")
    public List<UserDTO> selectRegisterUserList(SearchParamDTO param) {
        return userService.selectUserList(param, "register");
    }

    @GetMapping("/permit/select/list")
    public List<PermissionDTO> selectPermissionList (SearchParamDTO param, String adCk, String unCk) {
        return userService.selectPermissionList(param, adCk, unCk);
    }

    @GetMapping("/permit/user/select/list")
    public List<UserDTO> selectPermissionUserList (SearchParamDTO param) {
        return userService.selectUserList(param, "permit");
    }

    @GetMapping("/permit/user/perId/select/list")
    public List<UserDTO> selectPermissionUserList (String perId) {
        return userService.selectUserListByPermissionId(perId);
    }

    @GetMapping("/permit/user/perId/delete/list")
    public void deletePermissionUserList (@RequestParam(value = "userId[]") List<String> userIds) {
        userService.removeUserPermission(userIds);
    }

    @GetMapping("/permit/menu/list")
    public List<PermissionMenuDTO> selectPermissionMenuList () {
        return userService.selectPermissionMenuList();
    }

    @PostMapping("/info/update")
    public void updateUser(@AuthenticationPrincipal UserAuthentication auth,
                           @RequestBody UserDTO user) {
        user.setUpdateId(auth.getUserId());
        userService.updateUser(user, "register");
    }

    @GetMapping("/menuId/check")
    public ResponseEntity<InterceptorDTO> aerialPhotoCheck(@RequestParam String menuId, @AuthenticationPrincipal UserAuthentication auth) throws NoSuchElementException {
        ConcurrentMap<String, Object> map = new ConcurrentHashMap<>();
        map.put("menuId", menuId);
        map.put("userId", auth.getUserId());
        return ResponseEntity.ok(interceptorService.getMyPermission(map));
    }
}
