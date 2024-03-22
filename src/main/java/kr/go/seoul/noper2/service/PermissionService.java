package kr.go.seoul.noper2.service;

import kr.go.seoul.noper2.domain.Permission;
import kr.go.seoul.noper2.domain.PermissionList;
import kr.go.seoul.noper2.domain.SearchParam;
import kr.go.seoul.noper2.domain.User;
import kr.go.seoul.noper2.dto.*;
import kr.go.seoul.noper2.repository.PermissionRepository;
import kr.go.seoul.noper2.util.TypeCasting;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionService {
    private final PermissionRepository permissionRepository;
    public List<PermissionMenuDTO> getPermissionMenuList() {
        return TypeCasting.changeTypeList(permissionRepository.getPermissionMenuList(), PermissionMenuDTO.class);
    }

    public Boolean permissionNameCheck(String perName) {
        return !permissionRepository.permissionNameCheck(perName);
    }

    public void addPermission(PermissionDTO permission, List<String> menuIds) {
        String perId = "";
        List<PermissionListDTO> perLists = new ArrayList<>();
        for(String menuId : menuIds) {
            switch (menuId) {
                case "KG":
                    perId += "KG";
                    break;
                case "LI":
                    perId += "LI";
                    break;
                case "VM":
                    perId += "VM";
                    break;
                case "NB":
                    perId += "NB";
                    break;
                case "AD":
                    perId += "AD";
            }
            if(perId.length() > 2) perId.replaceAll("NB", "");
            PermissionListDTO perList = new PermissionListDTO();
            perList.setMenuId(menuId);
            perLists.add(perList);
        }
        if(perId.length() > 2) perId = "AD";
        perId += permissionRepository.getPermissionMaxId(perId);
        permission.setPerId(perId);
        permissionRepository.addPermission(TypeCasting.changeType(permission, Permission.class));
        for(PermissionListDTO perList : perLists) {
            perList.setPerId(perId);
            permissionRepository.addPermissionList(TypeCasting.changeType(perList, PermissionList.class));
        }
    }

    public void addPermissionUser(PermissionDTO permission, List<String> userIds) {
        for(String userId : userIds) {
            UserDTO user = new UserDTO();
            user.setUserId(userId);
            user.setPerId(permission.getPerId());
            permissionRepository.addPermissionUser(TypeCasting.changeType(user, User.class));
        }
    }

    public void deletePermissionUser(PermissionDTO permission, List<String> userIds) {
        for(String userId : userIds) {
            UserDTO user = new UserDTO();
            user.setUserId(userId);
            user.setPerId(permission.getPerId());
            permissionRepository.deletePermissionUser(TypeCasting.changeType(user, User.class));
        }
    }

    public List<UserDTO> getPermissionUserListByPerId(String perId) {
        return TypeCasting.changeTypeList(permissionRepository.permissionUserListByPerId(perId), UserDTO.class);
    }

    public List<PermissionDTO> getPermissionList(SearchParamDTO param, String adCk, String unCk) {
        List<Permission> result;
        param.setCalendarType(param.getCalendarType() == null || param.getCalendarType().equals("all") ? "" : param.getCalendarType());
        param.setStartDate(param.getStartDate() == null || param.getStartDate().isEmpty() ? "2000-01-01" : param.getStartDate());
        param.setEndDate(param.getEndDate() == null || param.getEndDate().isEmpty() ? "2099-12-31" : param.getEndDate());
        param.setSearchType(param.getSearchType() == null || param.getSearchType().equals("all") ? "" : param.getSearchType());
        param.setSearchText(param.getSearchText() == null  ? "" : param.getSearchText());
        param.setJoinType((adCk == null ? "" : "AD") + (unCk == null ? "" : "UN"));

        switch (param.getSearchType()) {
            case "perName":
                result = permissionRepository.permissionListByPerName(TypeCasting.changeType(param, SearchParam.class));
                break;
            case "registName":
                result = permissionRepository.permissionListByRegistName(TypeCasting.changeType(param, SearchParam.class));
                break;
            case "updateName":
                result = permissionRepository.permissionListByUpdateName(TypeCasting.changeType(param, SearchParam.class));
                break;
            default:
                result = permissionRepository.permissionList(TypeCasting.changeType(param, SearchParam.class));
                break;
        }
        return TypeCasting.changeTypeList(result, PermissionDTO.class);
    }

    public List<UserDTO> getPermissionUserList(SearchParamDTO param) {
        List<User> result;
        List<UserDTO> users = new ArrayList<>();
        param.setSkkCd(param.getSkkCd() == null ? "" : param.getSkkCd());
        param.setBjdCd(param.getBjdCd() == null ? "" : param.getBjdCd());
        param.setDeptCd(param.getDeptCd() == null ? "" : param.getSearchType());
        param.setPerId(param.getPerId() == null ? "" : param.getPerId());
        param.setSearchType(param.getSearchType() == null || param.getSearchType().equals("all") ? "" : param.getSearchType());
        param.setSearchText(param.getSearchText() == null  ? "" : param.getSearchText());
        switch (param.getSearchType()) {
            case "userName":
                result = permissionRepository.permissionUserListByUserName(TypeCasting.changeType(param, SearchParam.class));
                break;
            case "deptName":
                result = permissionRepository.permissionUserListByDeptName(TypeCasting.changeType(param, SearchParam.class));
                break;
            case "telNo":
                result = permissionRepository.permissionUserListByTelNo(TypeCasting.changeType(param, SearchParam.class));
                break;
            default:
                result = permissionRepository.permissionUserList(TypeCasting.changeType(param, SearchParam.class));
                break;
        }
        result.forEach(user -> users.add(TypeCasting.changeType(user, UserDTO.class)));
        return users;
    }

    public List<PermissionListDTO> getPermissionSelectedList(String perId) {
        return TypeCasting.changeTypeList(permissionRepository.getPermissionSelectedList(perId), PermissionListDTO.class);
    }

    public PermissionDTO getPermission(String perId) {
        return TypeCasting.changeType(permissionRepository.getPermission(perId), PermissionDTO.class);
    }

    public void updatePermission(PermissionDTO permission, List<String> menuIds) {
        String perId = permission.getPerId();
        permissionRepository.deletePermissionList(perId);
        for(String menuId : menuIds) {
            PermissionListDTO perList = new PermissionListDTO();
            perList.setPerId(perId);
            perList.setMenuId(menuId);
            permissionRepository.addPermissionList(TypeCasting.changeType(perList, PermissionList.class));
        }
        permissionRepository.updatePermission(TypeCasting.changeType(permission, Permission.class));
    }

    public void deletePermission(String perId) {
        permissionRepository.deletePermissionList(perId);
        permissionRepository.deletePermission(perId);
    }
}
