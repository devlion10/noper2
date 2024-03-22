package kr.go.seoul.noper2.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoperUserPer {
    private BigDecimal perId;

    @Builder
    public NoperUserPer(BigDecimal perId) {
        this.perId = perId;
    }
}
