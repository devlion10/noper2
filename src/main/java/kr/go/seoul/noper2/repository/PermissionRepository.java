package kr.go.seoul.noper2.repository;

import kr.go.seoul.noper2.domain.*;
import kr.go.seoul.noper2.dto.PermissionListDTO;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;

@Mapper
public interface PermissionRepository {
    void addPermission(Permission permission);
    void addPermissionList(PermissionList permission);
    List<PermissionMenu> getPermissionMenuList();
    String getPermissionMaxId(String perId);
    Boolean permissionNameCheck(String perName);

    Permission getPermission(String perId);
    List<Permission> permissionList(SearchParam param);
    List<Permission> permissionListByPerName(SearchParam param);
    List<Permission> permissionListByRegistName(SearchParam param);
    List<Permission> permissionListByUpdateName(SearchParam param);
    List<User> permissionUserList(SearchParam param);
    List<User> permissionUserListByUserName(SearchParam param);
    List<User> permissionUserListByDeptName(SearchParam param);
    List<User> permissionUserListByTelNo(SearchParam param);
    List<PermissionList> getPermissionSelectedList(String perId);
    void addPermissionUser(User user);
    void deletePermissionUser(User user);
    void updatePermission(Permission permission);
    void deletePermission(String perId);
    void deletePermissionList(String perId);
    List<User> permissionUserListByPerId(String perId);
}
