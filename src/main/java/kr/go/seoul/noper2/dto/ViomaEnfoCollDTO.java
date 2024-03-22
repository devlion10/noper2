package kr.go.seoul.noper2.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ViomaEnfoCollDTO {
    private Long collSeq;
    private String gmskk;
    private String collDate;
    private Long collAmount;
}

