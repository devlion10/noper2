package kr.go.seoul.noper2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
    private String userId;
    private String personalId;
    private String userPw;
    private String userName;
    private String skkCd;
    private String deptCd;
    private String telNo;
    @JsonProperty("eMail")
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
    private String crgb;
    private String workid;
    private String newyn;
    private String gmskk;
    private String gmdjgb;
    private String gmseqco;
    private String dn;
    private String deptName;

    // 동 정보 직접입력
    private String bjdCdDirect;
    // 부서명 직접입력
    private String deptCdDirect;
    private String userPrntNm;
    private String userPrntNm2;
    private String userKey;
    private String key;
    private String cdKey;
}
