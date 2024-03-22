package kr.go.seoul.noper2.dto;

import lombok.Data;


// 기존무허가 이미지파일관리 DTO
@Data
public class NoperImgFileinfoDTO {
    // 기관 코드
    private String gmskk;
    // 등재 코드
    private String gmdjgb;
    // 무허가 관리코드
    private String fileSeq;
    private String fileFlag;
    private String logicalFilename;
    private String physicalFilename;
    private String filePath;
    private String fileEnd;
    private String fileSize;
    private String registTs;
}
