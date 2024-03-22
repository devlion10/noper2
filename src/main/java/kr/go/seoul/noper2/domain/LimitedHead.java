package kr.go.seoul.noper2.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LimitedHead {
    private Long admSeq;
    private String skkCd;
    private String bjdCd;
    private String hjdCd;
    private String cSah;
    private String cBuh;
    private String cJi;
    private String cOh;
    private String buildFlag;
    private BigDecimal groundArea;
    private String jydCd;
    private String writeReason;
    private String degb;
    private String bigo;
    private String illegal;
    private BigDecimal buildMj;
    private String stCd;
    private String owner1Zip1;
    private String owner1Zip2;
    private String owner1Addr1;
    private String owner1Addr2;
    private String owner1Name;
    private String confirmName;
    private String registId;
    private Date registTs;
    private String updateId;
    private Date updateTs;

    @Builder
    public LimitedHead(Long admSeq, String skkCd, String bjdCd, String hjdCd, String cSah, String cBuh, String cJi, String cOh, String buildFlag, BigDecimal groundArea, String jydCd, String writeReason, String degb, String bigo, String illegal, BigDecimal buildMj, String stCd, String owner1Zip1, String owner1Zip2, String owner1Addr1, String owner1Addr2, String owner1Name, String confirmName, String registId, Date registTs, String updateId, Date updateTs) {
        this.admSeq = admSeq;
        this.skkCd = skkCd;
        this.bjdCd = bjdCd;
        this.hjdCd = hjdCd;
        this.cSah = cSah;
        this.cBuh = cBuh;
        this.cJi = cJi;
        this.cOh = cOh;
        this.buildFlag = buildFlag;
        this.groundArea = groundArea;
        this.jydCd = jydCd;
        this.writeReason = writeReason;
        this.degb = degb;
        this.bigo = bigo;
        this.illegal = illegal;
        this.buildMj = buildMj;
        this.stCd = stCd;
        this.owner1Zip1 = owner1Zip1;
        this.owner1Zip2 = owner1Zip2;
        this.owner1Addr1 = owner1Addr1;
        this.owner1Addr2 = owner1Addr2;
        this.owner1Name = owner1Name;
        this.confirmName = confirmName;
        this.registId = registId;
        this.registTs = registTs;
        this.updateId = updateId;
        this.updateTs = updateTs;
    }
}
