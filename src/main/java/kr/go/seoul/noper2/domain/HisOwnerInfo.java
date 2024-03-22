package kr.go.seoul.noper2.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HisOwnerInfo {
    private String crdate;
    private Long ownerSeq;
    private Long admSeq;
    private String chDate;
    private String owner1Addr1;
    private String owner1Addr2;
    private String owner1Zip1;
    private String owner1Zip2;
    private String preOwner1Name;
    private String confirmName;
    private String crgb;
    private String workid;
    private String registId;
    private Date registTs;
    private String updateId;
    private Date updateTs;
    private String bscd;

    @Builder
    public HisOwnerInfo(String crdate, Long ownerSeq, Long admSeq, String chDate, String owner1Addr1, String owner1Addr2, String owner1Zip1, String owner1Zip2, String preOwner1Name, String confirmName, String crgb, String workid, String registId, Date registTs, String updateId, Date updateTs, String bscd) {
        this.crdate = crdate;
        this.ownerSeq = ownerSeq;
        this.admSeq = admSeq;
        this.chDate = chDate;
        this.owner1Addr1 = owner1Addr1;
        this.owner1Addr2 = owner1Addr2;
        this.owner1Zip1 = owner1Zip1;
        this.owner1Zip2 = owner1Zip2;
        this.preOwner1Name = preOwner1Name;
        this.confirmName = confirmName;
        this.crgb = crgb;
        this.workid = workid;
        this.registId = registId;
        this.registTs = registTs;
        this.updateId = updateId;
        this.updateTs = updateTs;
        this.bscd = bscd;
    }
}