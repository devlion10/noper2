package kr.go.seoul.noper2.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CmpBjdongMgm {
    private String sigunguCd;
    private String bjdongCd;
    private String hjdongCd;
    private String sidoMn;
    private String sigunguNm;
    private String bjdongNm;
    private String hjdongNm;
    private String changeSigunguCd;
    private String changeBjdongCd;
    private String changeHjdongCd;
    private String applyStrtDay;
    private String applyEndDay;
    @Builder
    public CmpBjdongMgm(String sigunguCd, String bjdongCd, String hjdongCd, String sidoMn, String sigunguNm, String bjdongNm, String hjdongNm, String changeSigunguCd, String changeBjdongCd, String changeHjdongCd, String applyStrtDay, String applyEndDay) {
        this.sigunguCd = sigunguCd;
        this.bjdongCd = bjdongCd;
        this.hjdongCd = hjdongCd;
        this.sidoMn = sidoMn;
        this.sigunguNm = sigunguNm;
        this.bjdongNm = bjdongNm;
        this.hjdongNm = hjdongNm;
        this.changeSigunguCd = changeSigunguCd;
        this.changeBjdongCd = changeBjdongCd;
        this.changeHjdongCd = changeHjdongCd;
        this.applyStrtDay = applyStrtDay;
        this.applyEndDay = applyEndDay;
    }
}
