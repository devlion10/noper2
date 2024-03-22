package kr.go.seoul.noper2.dto;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class NoperConfirmDocDTO extends UserDTO{
    // 신규 여부
    private String newyn;
    // 기관 코드
    private String gmskk;
    // 등재 코드
    private String gmdjgb;
    //무허가관리번호
    private String gmseqco;
    //확인원발급번호
    private String conilno;
    //접수일자
    private String jsilja;
    //처리일자
    private String trilja;
    //신청자
    private String sinname;
    //신청인주민구분
    private String sinjugb;
    //신청인주민구분
    private String sinjumin;
    private String sinjumin1;
    private String sinjumin2;
    //발급통수
    private String bgsu;
    //담당전화번호
    private String chargetel;
    //신청인우편번호1
    private String sinzip1;
    //신청인우편번호2
    private String sinzip2;
    //신청인특수주소
    private String sinsna;
    //신청인외주소
    private String sinjname;
    //비고사항
    private String bigo;
    //새주소_법정동_번호
    private String naBjdongNo;
    //새주소_도로_명
    private String naRoadNm;
    //새주소_본_번
    private String naMainBun;
    //새주소_부_번
    private String naSubBun;
    //새주소_위치부분
    private String naEtc;
    //새주소_시군구_코드
    private String naSigunguCd;
    private String newAddr1;
    private String newAddr2;
    //발급기관키
    private String issueorgkey;
    //발급기관키
    private String issueorgnm;
    //삭제구분
    private String delgb;
    //삭제사유
    private String delc;
    private String updateId;
}
