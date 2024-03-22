package kr.go.seoul.noper2.domain;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    private String userId;
    private String personalId;
    private String userPw;
    private String userName;
    private String skkCd;
    private String deptCd;
    private String telNo;
    private String eMail;
    private String perId;
    private String registId;
    private String registTs;
    private String updateId;
    private String updateTs;
    private String deleteId;
    private String deleteTs;
    private String dongInfo;
    private String joinFlag;
    private String compFlag;
    private String compAt;
    private String loginFlag;
    private String workCharge;
    private String dn;
    private String deptName;

    @Builder
    public User(String userId, String personalId, String userPw, String userName, String skkCd, String deptCd, String telNo, String eMail, String perId, String registId,
                String registTs, String updateId, String updateTs, String deleteId, String deleteTs, String dongInfo, String joinFlag, String compFlag, String compAt, String loginFlag, String workCharge, String deptName) {
        this.userId = userId;
        this.personalId = personalId;
        this.userPw = userPw;
        this.userName = userName;
        this.skkCd = skkCd;
        this.deptCd = deptCd;
        this.telNo = telNo;
        this.eMail = eMail;
        this.perId = perId;
        this.registId = registId;
        this.registTs = registTs;
        this.updateId = updateId;
        this.updateTs = updateTs;
        this.deleteId = deleteId;
        this.deleteTs = deleteTs;
        this.dongInfo = dongInfo;
        this.joinFlag = joinFlag;
        this.compFlag = compFlag;
        this.compAt = compAt;
        this.loginFlag = loginFlag;
        this.workCharge = workCharge;
        this.deptName = deptName;
    }
}
