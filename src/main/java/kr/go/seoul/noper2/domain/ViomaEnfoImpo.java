package kr.go.seoul.noper2.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ViomaEnfoImpo {
    private Long impoSeq;
    private String gmskk;
    private String impoDate;
    private Long impoAmount;

    @Builder
    public ViomaEnfoImpo(Long impoSeq, String gmskk, String impoDate, Long impoAmount) {
        this.impoSeq = impoSeq;
        this.gmskk = gmskk;
        this.impoDate = impoDate;
        this.impoAmount = impoAmount;
    }
}

