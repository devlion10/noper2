package kr.go.seoul.noper2.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoperOwnerChangeFile {
    private String fileSeq;
    private String fileSub;
    private String cradate;
    private String suilno;
    private String gmjilno;
    private String gmskk;
    private String gmdjgb;
    private String gmseqco;
    private String logicalFilename;
    private String physicalFilename;
    private String filePath;
    private String fileEnd;
    private BigDecimal fileSize;
    private String registId;
    private Date registTs;
    private String updateId;
    private Date updateTs;

    @Builder
    public NoperOwnerChangeFile(String fileSeq, String fileSub, String cradate, String suilno, String gmjilno, String gmskk, String gmdjgb, String gmseqco, String logicalFilename, String physicalFilename, String filePath, String fileEnd, BigDecimal fileSize, String registId, Date registTs, String updateId, Date updateTs) {
        this.fileSeq = fileSeq;
        this.fileSub = fileSub;
        this.cradate = cradate;
        this.suilno = suilno;
        this.gmjilno = gmjilno;
        this.gmskk = gmskk;
        this.gmdjgb = gmdjgb;
        this.gmseqco = gmseqco;
        this.logicalFilename = logicalFilename;
        this.physicalFilename = physicalFilename;
        this.filePath = filePath;
        this.fileEnd = fileEnd;
        this.fileSize = fileSize;
        this.registId = registId;
        this.registTs = registTs;
        this.updateId = updateId;
        this.updateTs = updateTs;
    }
}
