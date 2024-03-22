package kr.go.seoul.noper2.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class FileDTO {
    private String businessName;
    private String seq;
    private String saveFolder;
    private String originFile;
    private String saveFile;
    private Long fileSeq;
    private String admSeq;
    private String fileFlag;
    private String flag;
    private String logicalFilename;
    private String physicalFilename;
    private String filePath;
    private String fileEnd;
    private BigDecimal fileSize;
    private String registId;
    private String registTs;
    private String updateId;
    private String gmskk;
    private String gmdjgb;
    private String gmseqco;
    private Date updateTs;
    private String chkilno;
    private String regDtime;
    private String regId;
    private String changId;
    private String changDtime;
    private String delleteAt;
    private String gmjilno;
    private String suilno;
    private String fileSub;

    @Getter
    @Builder
    public static class ResponseFileDTO {
        private Long fSeq;
        private String seq;
        private String gmskk;
        private String gmdjgb;
        private String gmseqco;
        private String isClient;
        private String logicalFilename;
        private String physicalFilename;
        private BigDecimal fileSize;
        private String fileEnd;
        private String flag;
        private String filePath;
        private String fileFlag;
        private String registTs;
        private String chkilno;
        private String regDtime;
        private String regId;
        private String changId;
        private String changDtime;
        private String delleteAt;
        private String suilno;
        private String gmjilno;
        private String fileSub;
    }

    @Getter
    @Builder
    public static class RequestDeleteDTO {
        private String type;
        private String fseq;
        private String seq;
        private String flag;
        private String gmskk;
        private String gmdjgb;
        private String gmseqco;
        private String physicalFilename;
        private String filePath;
        private String suilno;
        private String gmjilno;
        private String fileSub;
        private String chkilno;
        private String registId;
        private String regId;
        private String suerId;
    }
    @Getter
    @Setter
    public static class RequestDownloadDTO {
        private String seq;
        private String fseq;
        private String type;
        private String name;
        private String flag;
        private List<GridDTO> grid;
        private String gmskk;
        private String gmdjgb;
        private String gmseqco;
        private String physicalFilename;
        private String filePath;
        private String fileFlag;
        private String suilno;
        private String fileSub;
    }
    @Getter
    @Builder
    public static class GridDTO {
        private String seq;
        private String fseq;
        private String name;
        private String physicalFilename;
        private String logicalFilename;
        private String flag;
        private String fileFlag;
    }
    @Getter
    @Builder
    public static class ResponseYmlDTO {
        private List<String> extensions;
        private Long size;
    }
}
