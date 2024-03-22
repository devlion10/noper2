package kr.go.seoul.noper2.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ViomaFileinfoDTO {
    private Long fileSeq;
    private String fileFlag;
    private String gmskk;
    private String logicalFilename;
    private String physicalFilename;
    private String filePath;
    private String fileEnd;
    private BigDecimal fileSize;
}
