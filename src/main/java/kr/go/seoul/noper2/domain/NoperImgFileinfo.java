package kr.go.seoul.noper2.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoperImgFileinfo {
    private String fileSeq;
    private String gmskk;
    private String gmdjgb;
    private String gmseqco;
    private String fileFlag;
    private String logicalFilename;
    private String physicalFilename;
    private String filePath;
    private String fileEnd;
    private BigDecimal fileSize;
    private String registId;
    private String registTs;
    private String updateId;
    private Date updateTs;
    private String fileFlagTemp;
    private String admSeq;

    @Builder
    public NoperImgFileinfo(String fileSeq, String gmskk, String gmdjgb, String gmseqco, String fileFlag, String logicalFilename, String physicalFilename, String filePath, String fileEnd, BigDecimal fileSize, String registId, String registTs, String updateId, Date updateTs, String fileFlagTemp, String admSeq) {
        this.fileSeq = fileSeq;
        this.gmskk = gmskk;
        this.gmdjgb = gmdjgb;
        this.gmseqco = gmseqco;
        this.fileFlag = fileFlag;
        this.logicalFilename = logicalFilename;
        this.physicalFilename = physicalFilename;
        this.filePath = filePath;
        this.fileEnd = fileEnd;
        this.fileSize = fileSize;
        this.registId = registId;
        this.registTs = registTs;
        this.updateId = updateId;
        this.updateTs = updateTs;
        this.fileFlagTemp = fileFlagTemp;
        this.admSeq = admSeq;
    }
}
