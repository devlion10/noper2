package kr.go.seoul.noper2.dto;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class NoperFixDTO extends UserDTO {
    // NUMEBER
    private String fixRn;
    // 분기
    private String fixParam;
    // 기관 코드
    private String gmskk;
    // 등재 코드
    private String gmdjgb;
    // 무허가관리번호
    private String gmseqco;
    // 개보수일련번호
    private String gmilno;
    // 개보수발급순번
    private String gmbalno;
    // 개보수발급순번
    private String gmbalno2;
    // 개보수신고일
    private String gbsgoil;
    // 개보수신고내용
    private  String gbscotent;
    // 위반여부
    private String wbgb;
    // 조치내
    private String chcontent;
    // 준공일자
    private String jgong;
    // 현장확인자직명
    private String hhgname;
    // 현장확인자성명
    private String hhname;
    // 신고담당
    private String sinda;
    // 신고담당1
    private String sinda1;
    // 준공담당
    private String jungon;
    // 준공담당1
    private String jungon1;
    // 비고
    private String bigo;
    // 발급기관키
    private String issueorgkey;
    // 등록자
    private String registId;
    // 등록일시
    private String registTs;
    // 수정자
    private String updateId;
    // 수정일시
    private String updateTs;

    // 등록부서
    private String deptCd;
    // 변경업무구분
    private String workid;
    // 변경구분
    private String crgb;
    // 유저ID
    private String userId;
    // 유저ID
    private String userName;
}
