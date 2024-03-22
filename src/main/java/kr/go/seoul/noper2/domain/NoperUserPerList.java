package kr.go.seoul.noper2.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoperUserPerList {
    private String menuId;
    private BigDecimal perId;

    @Builder
    public NoperUserPerList(String menuId, BigDecimal perId) {
        this.menuId = menuId;
        this.perId = perId;
    }
}
