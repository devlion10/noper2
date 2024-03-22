package kr.go.seoul.noper2.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ViomaStatus {
    private String gmtype;
    private String gmsub;
    private String gmNCnt;
    private String gmECnt;
    private String impoCnt;
    private String impoAmt;
    private String collCnt;
    private String collAmt;
    private String nImpoAmt;
    private String gmgNCnt;
    private String gmgECnt;
    private String gmhCnt;

    @Builder
    public ViomaStatus(String gmtype, String gmsub, String gmNCnt, String gmECnt, String impoCnt, String impoAmt, String collCnt, String collAmt, String nImpoAmt, String gmgNCnt, String gmgECnt, String gmhCnt) {
        this.gmtype = gmtype;
        this.gmsub = gmsub;
        this.gmNCnt = gmNCnt;
        this.gmECnt = gmECnt;
        this.impoCnt = impoCnt;
        this.impoAmt = impoAmt;
        this.collCnt = collCnt;
        this.collAmt = collAmt;
        this.nImpoAmt = nImpoAmt;
        this.gmgNCnt = gmgNCnt;
        this.gmgECnt = gmgECnt;
        this.gmhCnt = gmhCnt;
    }
}
