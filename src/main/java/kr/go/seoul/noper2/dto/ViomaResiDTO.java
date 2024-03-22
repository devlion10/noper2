package kr.go.seoul.noper2.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ViomaResiDTO {
    private Long resiSeq;
    private String gmskk;
    private String resiName;
    private String resiTelno;
    private String resiForigb;
    private String resiBirth;
    private String resiAddress;
    private String resiNewAddress;
}
