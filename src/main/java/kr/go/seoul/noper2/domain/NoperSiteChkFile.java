package kr.go.seoul.noper2.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoperSiteChkFile {
    private String fileSeq;
    private String gmskk;
    private String gmdjgb;
    private String gmseqco;
    private String chkilno;
    private String fileFlag;
    private String logicalFilename;
    private String physicalFilename;
    private String filePath;
    private String fileEnd;
    private BigDecimal fileSize;
    private String regDtime;
    private String regId;
    private String changId;
    private String changDtime;
    private String delleteAt;

    @Builder
    public NoperSiteChkFile(String fileSeq, String gmskk, String gmdjgb, String gmseqco, String chkilno, String fileFlag, String logicalFilename, String physicalFilename, String filePath, String fileEnd, BigDecimal fileSize, String regDtime, String regId, String changId, String changDtime, String delleteAt) {
        this.fileSeq = fileSeq;
        this.gmskk = gmskk;
        this.gmdjgb = gmdjgb;
        this.gmseqco = gmseqco;
        this.chkilno = chkilno;
        this.fileFlag = fileFlag;
        this.logicalFilename = logicalFilename;
        this.physicalFilename = physicalFilename;
        this.filePath = filePath;
        this.fileEnd = fileEnd;
        this.fileSize = fileSize;
        this.regDtime = regDtime;
        this.regId = regId;
        this.changId = changId;
        this.changDtime = changDtime;
        this.delleteAt = delleteAt;
    }
}
