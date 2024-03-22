package kr.go.seoul.noper2.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ViomaResi {
    private Long resiSeq;
    private String gmskk;
    private String resiName;
    private String resiTelno;
    private String resiForigb;
    private String resiBirth;
    private String resiAddress;
    private String resiNewAddress;

    @Builder
    public ViomaResi(Long resiSeq, String gmskk, String resiName, String resiTelno, String resiForigb, String resiBirth, String resiAddress, String resiNewAddress) {
        this.resiSeq = resiSeq;
        this.gmskk = gmskk;
        this.resiName = resiName;
        this.resiTelno = resiTelno;
        this.resiForigb = resiForigb;
        this.resiBirth = resiBirth;
        this.resiAddress = resiAddress;
        this.resiNewAddress = resiNewAddress;
    }
}