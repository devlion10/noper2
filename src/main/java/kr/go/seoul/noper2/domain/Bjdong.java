package kr.go.seoul.noper2.domain;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bjdong {
    private String key;
    private String dpname;

    @Builder
    public Bjdong(String key, String dpname) {
        this.key = key;
        this.dpname = dpname;
    }
}