package kr.go.seoul.noper2.dto;

import lombok.Data;
import lombok.NoArgsConstructor;


//소유자 변경 이력 DTO
@Data
@NoArgsConstructor
public class NoperOwnerDTO {
    private String ownerFlag;
    // 주민번호 개발/운영 분기
    private String isLocal;
    // 기관 코드
    private String gmskk;
    // 등재 코드
    private String gmdjgb;
    // 무허가 관리코드
    private String gmseqco;
    // 건물주 일련번호
    private String gmjilno;
    // 등록자
    private String registId;
    private String rn;
    private String suilno;
    private String sujilja;
    // 건물주 이름
    private String gmjname;
    // 건물주 주민
    private String gmjjgn;
    private String gmjjumin;
    private String gmjjumin1;
    private String gmjjumin2;
    // 건물주 우편번호1
    private String gmjzip1;
    // 건물주 우편번호1
    private String gmjzip2;
    // 건물주 외 주소
    private String gmjjname;
    // 건물주 특수지
    private String gmjsna;
    private String naSigunguCdOwner;
    private String newAddrTemp1;
    private String newAddrTemp2;
    private String jgmjname;
    private String jgmjjgn;
    private String jgmjjumin;
    private String jgmjjumin1;
    private String jgmjjumin2;
    private String jgmjzip1;
    private String jgmjzip2;
    private String jgmjjname;
    private String jgmjsna;
    private String crilja;
    private String bksu;
    private String naBjdongNo;
    private String gmjsajuso;
    private String gmzip1;
    private String gmzip2;
    // 새주소_도로_명
    private String naRoadNm;
    // 새주소_도로_코드
    private String naRoadCd;
    // 새주소_본_번
    private String naMainBun;
    // 새주소_부_번
    private String naSubBun;
    // 새주소_위치부분
    private String naEtc;
    private String naSigunguCd;
    private String jNaBjdongNo;
    private String jNaRoadNm;
    private String jNaMainBun;
    private String jNaSubBun;
    private String jNaEtc;
    private String jNaSigunguCd;
    private String newAddr1;
    private String newAddr2;
    private String jNewAddr1;
    private String jNewAddr2;
    private String gubogoilja;
    private String userId;

}
