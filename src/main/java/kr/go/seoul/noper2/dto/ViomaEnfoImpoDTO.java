package kr.go.seoul.noper2.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ViomaEnfoImpoDTO {
    private Long impoSeq;
    private String gmskk;
    private String impoDate;
    private Long impoAmount;
}

