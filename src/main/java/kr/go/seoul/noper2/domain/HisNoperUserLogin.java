package kr.go.seoul.noper2.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HisNoperUserLogin {
    private Long loginSeq;
    private String userId;
    private String loginIp;
    private Date loginTs;

    @Builder
    public HisNoperUserLogin(Long loginSeq, String userId, String loginIp, Date loginTs) {
        this.loginSeq = loginSeq;
        this.userId = userId;
        this.loginIp = loginIp;
        this.loginTs = loginTs;
    }
}
