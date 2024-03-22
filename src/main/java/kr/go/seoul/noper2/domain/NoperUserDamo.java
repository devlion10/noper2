package kr.go.seoul.noper2.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoperUserDamo {
    private String userId;
    private String secPersonalId;
    private String userPw;
    private String userName;
    private String skkCd;
    private String deptCd;
    private String telNo;
    private String email;
    private String perId;
    private String registId;
    private String registTs;
    private String updateId;
    private Date updateTs;
    private String deleteId;
    private Date deleteTs;
    private String dongInfo;
    private String joinFlag;
    private String loginFlag;
    private String dn;
    private String chargeWork;

    @Builder
    public NoperUserDamo(String userId, String secPersonalId, String userPw, String userName, String skkCd, String deptCd, String telNo, String email, String perId, String registId, String registTs, String updateId, Date updateTs, String deleteId, Date deleteTs, String dongInfo, String joinFlag, String loginFlag, String dn, String chargeWork) {
        this.userId = userId;
        this.secPersonalId = secPersonalId;
        this.userPw = userPw;
        this.userName = userName;
        this.skkCd = skkCd;
        this.deptCd = deptCd;
        this.telNo = telNo;
        this.email = email;
        this.perId = perId;
        this.registId = registId;
        this.registTs = registTs;
        this.updateId = updateId;
        this.updateTs = updateTs;
        this.deleteId = deleteId;
        this.deleteTs = deleteTs;
        this.dongInfo = dongInfo;
        this.joinFlag = joinFlag;
        this.loginFlag = loginFlag;
        this.dn = dn;
        this.chargeWork = chargeWork;
    }
}
