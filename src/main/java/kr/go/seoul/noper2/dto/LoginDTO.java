package kr.go.seoul.noper2.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class LoginDTO {
    @Getter
    public static class LoginRequestDTO {
    }
    @Getter
    @Setter
    public static class FindUserIdRequestDTO {
        private String departmentName;
        private String name;
        private String telno;
        private String emailName;
        private String emailDomain;
        private String email;
    }
    @Getter
    @Setter
    public static class FindUserIdResponseDTO {
        private String id;
        private String date;
        private Integer bool;
    }
    @Getter
    @Setter
    public static class FindUserPwRequestDTO {
        private String id;
        private String name;
        private String telno;
        private String emailName;
        private String emailDomain;
        private String email;
    }
    @Getter
    @Setter
    public static class FindUserPwResponseDTO {
        private String id;
        private Integer bool;
    }
    @Getter
    @Setter
    public static class ResetUserPwRequestDTO {
        private String id;
        private String password;
    }
}
