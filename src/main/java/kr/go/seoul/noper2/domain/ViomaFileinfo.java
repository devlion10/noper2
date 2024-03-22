package kr.go.seoul.noper2.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
public class ViomaFileinfo {
    private Long fileSeq;
    private String fileFlag;
    private String gmskk;
    private String logicalFilename;
    private String physicalFilename;
    private String filePath;
    private String fileEnd;
    private BigDecimal fileSize;

    @Builder
    public ViomaFileinfo(Long fileSeq, String fileFlag, String gmskk, String logicalFilename, String physicalFilename, String filePath, String fileEnd, BigDecimal fileSize) {
        this.fileSeq = fileSeq;
        this.fileFlag = fileFlag;
        this.gmskk = gmskk;
        this.logicalFilename = logicalFilename;
        this.physicalFilename = physicalFilename;
        this.filePath = filePath;
        this.fileEnd = fileEnd;
        this.fileSize = fileSize;
    }
}
