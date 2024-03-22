package kr.go.seoul.noper2.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DongInfo {
    private Long dongSeq;
    private Long admSeq;
    private String dongNo;
    private String dongJydCd;
    private String dongJydName;
    private String dongStcd;
    private String dongStName;
    private BigDecimal underScale;
    private BigDecimal floor1Scale;
    private BigDecimal floor2Scale;
    private BigDecimal floor3Scale;
    private BigDecimal floor4Scale;
    private BigDecimal floorsSumScale;
    private String dongPermitDate;
    private String dongPermitNo;
    private String dongCompDate;
    private String registId;
    private Date registTs;
    private String updateId;
    private Date updateTs;

    @Builder
    public DongInfo(Long dongSeq, Long admSeq, String dongNo, String dongJydCd, String dongJydName, String dongStcd, String dongStName, BigDecimal underScale, BigDecimal floor1Scale, BigDecimal floor2Scale, BigDecimal floor3Scale, BigDecimal floor4Scale, BigDecimal floorsSumScale, String dongPermitDate, String dongPermitNo, String dongCompDate, String registId, Date registTs, String updateId, Date updateTs) {
        this.dongSeq = dongSeq;
        this.admSeq = admSeq;
        this.dongNo = dongNo;
        this.dongJydCd = dongJydCd;
        this.dongJydName = dongJydName;
        this.dongStcd = dongStcd;
        this.dongStName = dongStName;
        this.underScale = underScale;
        this.floor1Scale = floor1Scale;
        this.floor2Scale = floor2Scale;
        this.floor3Scale = floor3Scale;
        this.floor4Scale = floor4Scale;
        this.floorsSumScale = floorsSumScale;
        this.dongPermitDate = dongPermitDate;
        this.dongPermitNo = dongPermitNo;
        this.dongCompDate = dongCompDate;
        this.registId = registId;
        this.registTs = registTs;
        this.updateId = updateId;
        this.updateTs = updateTs;
    }
}
