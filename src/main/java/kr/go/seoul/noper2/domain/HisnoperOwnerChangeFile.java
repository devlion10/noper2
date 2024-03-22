package kr.go.seoul.noper2.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HisnoperOwnerChangeFile {
    private String crdate;
    private Long fileSeq;
    private String cradate;
    private String fileSub;
    private BigDecimal siulno;
    private BigDecimal gmjilno;
    private String gmskk;
    private String gmdjgb;
    private BigDecimal gmseqco;
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
    public HisnoperOwnerChangeFile(String crdate, Long fileSeq, String cradate, String fileSub, BigDecimal siulno, BigDecimal gmjilno, String gmskk, String gmdjgb, BigDecimal gmseqco, String logicalFilename, String physicalFilename, String filePath, String fileEnd, BigDecimal fileSize, String registId, Date registTs, String updateId, Date updateTs) {
        this.crdate = crdate;
        this.fileSeq = fileSeq;
        this.cradate = cradate;
        this.fileSub = fileSub;
        this.siulno = siulno;
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
