package kr.go.seoul.noper2.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ViomaEnfoColl {
    private Long collSeq;
    private String gmskk;
    private String collDate;
    private Long collAmount;

    @Builder
    public ViomaEnfoColl(Long collSeq, String gmskk, String collDate, Long collAmount) {
        this.collSeq = collSeq;
        this.gmskk = gmskk;
        this.collDate = collDate;
        this.collAmount = collAmount;
    }
}

