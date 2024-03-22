package kr.go.seoul.noper2.domain;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Code {
    private String cdid;
    private String cdkey;
    private String cdvalue;
    private String cddesc;

    @Builder
    public Code(String cdid, String cdkey, String cdvalue, String cddesc) {
        this.cdid = cdid;
        this.cdkey = cdkey;
        this.cdvalue = cdvalue;
        this.cddesc = cddesc;
    }
}
