package kr.go.seoul.noper2.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 세션에 객체를 담기위해 만들어
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoperUserSession {
    private String userId;
    private String userPw;
    private String userName;
    private String skkCd;
    private String deptCd;
    private String deptName;
    private String registId;
    private String perId;

    @Builder
    public NoperUserSession(String userId, String userPw, String userName, String skkCd, String deptCd, String deptName, String registId, String perId) {
        this.userId = userId;
        this.userPw = userPw;
        this.userName = userName;
        this.skkCd = skkCd;
        this.deptCd = deptCd;
        this.deptName = deptName;
        this.registId = registId;
        this.perId = perId;
    }
}
