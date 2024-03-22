package kr.go.seoul.noper2.global.auth;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 실제 SecurityConxtexHolder에 담기는 세션 클래스입니다.
 */
@Getter
@Setter
public class UserAuthentication implements Serializable {
    // 유저 아이디
    private String userId;
    // 성명
    private String userName;
    // 부서 코드
    private String deptCd;
    // 부서 이름
    private String deptName;
    // 시군구 코드
    private String skkCd;
    // 등록자
    private String registId;
    // 권한이 관리자인지 구분
    private Boolean isAdmin;
}