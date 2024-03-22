package kr.go.seoul.noper2.service;

import kr.go.seoul.noper2.domain.*;
import kr.go.seoul.noper2.dto.*;
import kr.go.seoul.noper2.repository.UserRepository;
import kr.go.seoul.noper2.util.EmailUtil;
import kr.go.seoul.noper2.util.TypeCasting;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailUtil emailUtil;

    public void createUser(UserDTO user, String type) {
        /*
        switch (type) {
            case "join":
                user.setUserId(userRepository.selectMaxUserId("NJ"));
                user.setJoinFlag("N");
                user.setCompFlag("N");
                user.setLoginFlag("N");
                break;
            case "register":
                userRepository.deleteUser(user.getUserId());
                user.setUserId(userRepository.selectMaxUserId(user.getPerId().substring(0, 2)));
                user.setJoinFlag("Y");
                user.setCompFlag("N");
                user.setLoginFlag("Y");
                user.setUserPw(passwordEncoder.encode(user.getUserPw()));
                break;
        }
        userRepository.createUser(TypeCasting.changeType(user, User.class));

         */
        String subject = "";
        String content = "";
        switch (type) {
            case "join":
                user = directInputCheck(user);
                user.setUserId(userRepository.selectMaxJoinUserId("NJ"));
                userRepository.createJoinUser(TypeCasting.changeType(user, User.class));
                subject = "서울시 기존무허가건축물관리 시스템 가입신청 관련";
                content = "안녕하세요 서울시 기존무허가건축물관리 시스템입니다.<br>" +
                        "<br>" +
                        "%s님이 서울시 기존무허가건축물관리 시스템에 가입신청을 하였습니다. <br>" +
                        "<a href='http://98.33.11.180:8080/board/user/list'>가입신청 내역을 확인해주세요.</a><br>";
                emailUtil.sendEmailToMe(subject, String.format(content, user.getUserName()));
                break;
            case "register":
                user = directInputCheck(user);
                String oriPw = user.getUserPw();
                userRepository.deleteUser(user.getUserId());
                user.setUserId(userRepository.selectMaxRegisterUserId(user.getPerId().substring(0, 2)));
                user.setUserPw(passwordEncoder.encode(user.getUserPw()));
                userRepository.createRegisterUser(TypeCasting.changeType(user, User.class));
                List<String> targetEmails = new ArrayList<>();
                targetEmails.add(user.getEMail());
                subject = "서울시 기존무허가건축물관리 시스템 가입승인 안내";
                content = "안녕하세요 서울시 기존무허가건축물관리 시스템입니다.<br>" +
                        "<br>" +
                        "%s님이 신청하신 서울시 기존무허가건축물관리 시스템 <br>" +
                        "가입이 승인되어 아이디/비밀번호를 안내 드립니다.<br>" +
                        "해당 비밀번호는 초기 비밀번호로, 비밀번호 변경 후 사용바랍니다.<br>" +
                        "<br>" +
                        " - 아이디 : <span>%s</span><br>" +
                        " - 비밀번호: <span>%s</span><br>" +
                        "<br>" +
                        "보다 자세한 내용은 서울시 기존무허가건축물관리 시스템 <br>" +
                        "메인 페이지 하단 GPKI 인증서 사용 안내를 확인해주세요.<br>" +
                        "<br>" +
                        "감사합니다.";
                emailUtil.sendEmail(subject, String.format(content, user.getUserName(), user.getUserId(), oriPw), targetEmails);
                emailUtil.sendEmailToMe(subject, String.format(content, user.getUserName(), user.getUserId(), oriPw));
                break;
        }
    }

    public void createPermission(PermissionDTO permission, List<String> menuIds, String parents) {
        String perFId = "";
        List<PermissionListDTO> perLists = new ArrayList<>();
        for(String menuId : menuIds) {
            PermissionListDTO perList = new PermissionListDTO();
            perList.setMenuId(menuId);
            perLists.add(perList);
        }
        if(parents.length() > 2) {
            if(parents.contains("AD")) {
                perFId = "AD";
            } else if(parents.contains("KG")) {
                perFId = "KG";
            } else if(parents.contains("LI")) {
                perFId = "LI";
            } else if(parents.contains("VM")) {
                perFId = "VM";
            }
        } else {
            perFId = "NB";
        }
        String perId = userRepository.selectMaxPermissionId(perFId);
        permission.setPerId(perId);
        userRepository.createPermission(TypeCasting.changeType(permission, Permission.class));
        perLists.forEach(pl -> {
            pl.setPerId(perId);
            userRepository.createPermissionList(TypeCasting.changeType(pl, PermissionList.class));
        });
    }

    public void updateUser(UserDTO user, String type) {
        /*
        switch (type) {
            case "join":
                user.setJoinFlag("N");
                user.setCompFlag("N");
                user.setLoginFlag("N");
                user.setCompAt("");
                break;
            case "register":
                user.setJoinFlag("Y");
                user.setCompFlag("N");
                user.setLoginFlag("Y");
                break;
        }
        user.setCompAt("");
        userRepository.updateUser(TypeCasting.changeType(user, User.class));
         */
        switch (type) {
            case "join":
                user = directInputCheck(user);
                userRepository.updateJoinUser(TypeCasting.changeType(user, User.class));
                break;
            case "register":
                user = directInputCheck(user);
                userRepository.updateRegisterUser(TypeCasting.changeType(user, User.class));
                break;
        }
    }

    /**
     * UserDTO 로 동정보 혹은 부서정보에 대한 직접입력 로직을 수행.
     * @param dto:UserDTO - direct 처리할 UserDTO
     * @return dto:UserDTO - direct 처리가 된 UserDTO
     */
    public UserDTO directInputCheck(UserDTO dto) {
        if (dto.getDongInfo() != null && dto.getDongInfo().equals("direct")) {
            // 테이블에 이미 있으면 해당 값 return, 없으면 "0" return
            String bjdCdDirect = userRepository.checkBjdCdExists(dto);
            if (bjdCdDirect.equals("0")) {
                dto.setUserPrntNm(dto.getBjdCdDirect() + "장");
                dto.setUserPrntNm2(dto.getBjdCdDirect() + "장(인)");
                userRepository.saveBjdCd(dto);
                dto.setDongInfo(userRepository.findBjdCd(dto));
            } else {
                dto.setDongInfo(bjdCdDirect);
            }
        }

        if (dto.getDeptCd() != null && dto.getDeptCd().equals("direct")) {
            // 테이블에 이미 있으면 해당 값 return, 없으면 "0" return
            String deptCdDirect = userRepository.checkDeptCdExists(dto);
            if (deptCdDirect.equals("0")) {
                userRepository.saveDeptCd(dto);
                dto.setDeptCd(userRepository.findDeptCd(dto));
            } else {
                dto.setDeptCd(deptCdDirect);
            }
        }

        return dto;
    }

    public String updateUserPassword(UserDTO user, String currentPw, boolean isAdmin) throws NoSuchElementException {
        String userPw = userRepository.findUserById(user.getUserId()).orElseThrow(NoSuchElementException::new).getUserPw();
        if(isAdmin || passwordEncoder.matches(currentPw, userPw)) {
            user.setUserPw(passwordEncoder.encode(user.getUserPw()));
            userRepository.updateUserPassword(TypeCasting.changeType(user, User.class));
            return "success";
        } else {
            return "fail";
        }
    }

    public void updatePermission(PermissionDTO permission, List<String> menuIds) {
        String perId = permission.getPerId();
        userRepository.deletePermissionListAll(perId);
        for(String menuId : menuIds) {
            PermissionListDTO perList = new PermissionListDTO();
            perList.setPerId(perId);
            perList.setMenuId(menuId);
            userRepository.createPermissionList(TypeCasting.changeType(perList, PermissionList.class));
        }
        userRepository.updatePermission(TypeCasting.changeType(permission, Permission.class));
    }

    public void updatePermissionUser(String perId, List<String> userIds) {
        userRepository.removeUserPermissionByPerId(perId);
        for(String userId : userIds) {
            UserDTO user = new UserDTO();
            user.setUserId(userId);
            user.setPerId(perId);
            userRepository.updateUserPermission(TypeCasting.changeType(user, User.class));
        }
    }

    public void allowJoinUser(String userId) {
        userRepository.allowJoinUser(userId);
        userRepository.createRegisterUser(userRepository.selectJoinUser(userId));
    }

    public void denyJoinUser(UserDTO user) {
        userRepository.denyJoinUser(TypeCasting.changeType(user, User.class));
    }

    public void removeUserPermission(List<String> userIds) {
        for(String userId : userIds) {
            userRepository.removeUserPermission(userId);
        }
    }

    public void deleteUser(String userId, String type) {
        String[] users = userId.split(",");
        if (type.equals("join")) {
            for(String user : users) {
                userRepository.deleteJoinUser(user);
            }
        } else {
            for(String user : users) {
                userRepository.deleteRegisterUser(user);
            }
        }
    }

    public void deletePermission(String perId) {
        userRepository.deletePermissionListAll(perId);
        userRepository.deletePermission(perId);
    }

    public List<UserDTO> selectUserList(SearchParamDTO param, String type) {
        List<UserDTO> users = null;
        param.setCalendarType(param.getCalendarType() == null || param.getCalendarType().equals("all") ? "" : param.getCalendarType());
        param.setStartDate(param.getStartDate() == null || param.getStartDate().isEmpty() ? "2000-01-01" : param.getStartDate());
        param.setEndDate(param.getEndDate() == null || param.getEndDate().isEmpty() ? "2099-12-31" : param.getEndDate());
        param.setSkkCd(param.getSkkCd() == null ? "" : param.getSkkCd());
        param.setBjdCd(param.getBjdCd() == null ? "" : param.getBjdCd());
        param.setPerId(param.getPerId() == null ? "" : param.getPerId());
        param.setDeptCd(param.getDeptCd() == null ? "" : param.getDeptCd());
        param.setJoinType(param.getJoinType() == null || param.getJoinType().equals("all") ? "" : param.getJoinType());
        param.setCompType(param.getCompType() == null ? "" : param.getCompType());
        param.setSearchType(param.getSearchType() == null || param.getSearchType().equals("all") ? "" : param.getSearchType());
        param.setSearchText(param.getSearchText() == null  ? "" : param.getSearchText());

        switch (type) {
            case "join":
                if (param.getJoinType().equals("NN")) {
                    param.setJoinType("N");
                    param.setCompType("Y");
                }
                users = TypeCasting.changeTypeList(userRepository.selectJoinUserList(TypeCasting.changeType(param, SearchParam.class)), UserDTO.class);
                break;
            case "guest":
                users = TypeCasting.changeTypeList(userRepository.selectJoinUserListFromGuest(TypeCasting.changeType(param, SearchParam.class)), UserDTO.class);
                break;
            case "register":
                users = TypeCasting.changeTypeList(userRepository.selectRegisterUserList(TypeCasting.changeType(param, SearchParam.class)), UserDTO.class);
                break;
            case "permit":
                users = TypeCasting.changeTypeList(userRepository.selectPermissionOfUserList(TypeCasting.changeType(param, SearchParam.class)), UserDTO.class);
                break;
        }
        return users;
    }

    public List<PermissionDTO> selectPermissionList(SearchParamDTO param, String adCk, String unCk) {
        param.setCalendarType(param.getCalendarType() == null || param.getCalendarType().equals("all") ? "" : param.getCalendarType());
        param.setStartDate(param.getStartDate() == null || param.getStartDate().isEmpty() ? "2000-01-01" : param.getStartDate());
        param.setEndDate(param.getEndDate() == null || param.getEndDate().isEmpty() ? "2099-12-31" : param.getEndDate());
        param.setSearchType(param.getSearchType() == null || param.getSearchType().equals("all") ? "" : param.getSearchType());
        param.setSearchText(param.getSearchText() == null  ? "" : param.getSearchText());
        param.setJoinType((adCk == null ? "" : "AD") + (unCk == null ? "" : "UN"));
        return TypeCasting.changeTypeList(userRepository.selectPermissionList(TypeCasting.changeType(param, SearchParam.class)), PermissionDTO.class);
    }

    public List<UserDTO> selectUserListByPermissionId(String perId) {
        return TypeCasting.changeTypeList(userRepository.selectUserListByPermissionId(perId), UserDTO.class);
    }
    public List<PermissionMenuDTO> selectPermissionSelectedMenuList(String perId) {
        return TypeCasting.changeTypeList(userRepository.selectPermissionSelectedMenuList(perId), PermissionMenuDTO.class);
    }

    public List<PermissionMenuDTO> selectPermissionMenuList() {
        return TypeCasting.changeTypeList(userRepository.selectPermissionMenuList(), PermissionMenuDTO.class);
    }

    public UserDTO selectUser(String userId, String type) {
        UserDTO user = TypeCasting.changeType((type.equals("join") ? userRepository.selectJoinUser(userId) : userRepository.selectRegisterUser(userId)), UserDTO.class);
        if(user.getEMail() == null || user.getEMail().equals("@")) user.setEMail("none");
        return user;
    }

    public UserDTO selectUserView(String userId, String type) {
        return TypeCasting.changeType((type.equals("join") ? userRepository.selectJoinUserView(userId) : userRepository.selectRegisterUserView(userId)), UserDTO.class);
    }

    public PermissionDTO selectPermission(String perId) {
        return TypeCasting.changeType(userRepository.selectPermission(perId), PermissionDTO.class);
    }

    public boolean checkPermissionName(String perName) {
        return !userRepository.checkPermissionName(perName);
    }

}
