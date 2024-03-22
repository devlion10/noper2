package kr.go.seoul.noper2.repository;

import kr.go.seoul.noper2.domain.*;
import kr.go.seoul.noper2.dto.LoginDTO;
import kr.go.seoul.noper2.dto.UserDTO;
import org.apache.ibatis.annotations.Param;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserRepository {
    Optional<NoperUserSession> findByUsername(String userName);
    Optional<String> findSession();
    void saveHistory(HisNoperUserLogin hisNoperUserLogin);
    void updateSessionTime(String minute);
    void createJoinUser(User user);
    void createRegisterUser(User user);
    void createUser(User user);

    /**
     * 법정동코드 값 체크
     * @param dto - UserDTO
     * @return bjdCd - 테이블에 값이 없으면 0 
     */
    String checkBjdCdExists(UserDTO dto);
    /**
     * 부서코드 값 체크
     * @param dto - UserDTO
     * @return deptCd - 테이블에 값이 없으면 0
     */
    String checkDeptCdExists(UserDTO dto);
    void saveBjdCd(UserDTO dto);
    void saveDeptCd(UserDTO dto);
    void createPermission(Permission permission);
    void createPermissionList(PermissionList permissionMenu);
    void updateJoinUser(User user);
    void updateRegisterUser(User user);
    void updateUser(User user);
    void updateUserPassword(User user);
    void updateUserPermission(User user);
    void updatePermission(Permission permission);
    void allowJoinUser(String userId);
    void denyJoinUser(User userId);
    void removeUserPermission(String userId);
    void removeUserPermissionByPerId(String perId);
    void deleteJoinUser(String userId);
    void deleteRegisterUser(String userId);
    void deleteUser(String userId);
    void deletePermission(String perId);
    void deletePermissionListAll(String perId);
    List<User> selectJoinUserList(SearchParam searchParam);
    List<UserDTO> selectJoinUserListFromGuest(SearchParam param);
    List<User> selectRegisterUserList(SearchParam searchParam);
    List<User> selectPermissionOfUserList(SearchParam searchParam);
    List<Permission> selectPermissionList(SearchParam searchParam);
    List<User> selectUserListByPermissionId(String perId);
    List<PermissionMenu> selectPermissionSelectedMenuList(String perId);
    List<PermissionMenu> selectPermissionMenuList();
    User selectJoinUser(String userId);
    User selectRegisterUser(String userId);
    User selectUser(String userId);
    User selectJoinUserView(String userId);
    User selectRegisterUserView(String userId);
    User selectUserView(String userId);
    Permission selectPermission(String perId);
    boolean checkPermissionName(String perName);
    String selectMaxPermissionId(String perId);
    String selectMaxJoinUserId(String userId);
    String selectMaxRegisterUserId(String userId);
    String selectMaxUserId(String userId);
    String findUserByGpkiDn(String dn);
    Optional<NoperUserDamo> findUserById(String id);
    void updateUserByDn(@Param("dn") String dn, @Param("id") String id);
    Optional<NoperUserDamo> findUserIdByUserInfo(LoginDTO.FindUserIdRequestDTO dto);
    Optional<String> findUserPwByUserInfo(LoginDTO.FindUserPwRequestDTO dto);
    void updatePassword(LoginDTO.ResetUserPwRequestDTO dto);

    void deletePermissionUserByPermissionId(String perId);

    String findBjdCd(UserDTO dto);

    String findDeptCd(UserDTO dto);
}
