package kr.go.seoul.noper2.domain;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ViomaMaster {
    private String rn;
    private String gmskk;
    private String gmskkcd;
    private String gmbjdcd;
    private String gmhjdcd;
    private String gmcsah;
    private String gmaddr;
    private String gmnaddr;
    private String gmgunil;
    private String deptCd;
    private String dejimj;
    private String dejijmg;
    private String dejisg;
    private String dejiju;
    private String dejijg;
    private String dejigu;
    private String gcname;
    private String gcforigb;
    private String gcbirth;
    private String gctelno;
    private String gcaddr;
    private String gcnaddr;
    private String gmtype;
    private String gmdate;
    private String gmname;
    private String gmftype;
    private String gmcdate;
    private String gmmunjuk;
    private String gmfloor;
    private String gmbsub;
    private String gmasub;
    private String gmgdate;
    private String gmjdate;
    private String gmhdate;
    private String gmhcdate;
    private String gmhcont;
    private String gmsname;
    private String gmsnumber;
    private String gmgname;
    private String gmgnumber;
    private String gminfo;
    private String gmrst;
    private String gmmst;
    private String gmfacseq;
    private String registId;
    private String registTs;
    private String updateId;
    private String updateTs;

    @Builder
    public ViomaMaster(String rn, String gmskk, String gmskkcd, String gmbjdcd, String gmhjdcd, String gmcsah,String gmaddr, String gmnaddr, String gmgunil, String deptCd, String dejimj, String dejijmg, String dejisg, String dejiju, String dejijg, String dejigu, String gcname, String gcforigb, String gcbirth, String gctelno, String gcaddr, String gcnaddr, String gmtype, String gmdate, String gmname, String gmftype, String gmcdate, String gmmunjuk, String gmfloor, String gmbsub, String gmasub, String gmgdate, String gmjdate, String gmhdate, String gmhcdate, String gmhcont, String gmsname, String gmsnumber, String gmgname, String gmgnumber, String gminfo, String gmrst, String gmmst, String gmfacseq, String registId, String registTs, String updateId, String updateTs) {
        this.rn = rn;
        this.gmskk = gmskk;
        this.gmskkcd = gmskkcd;
        this.gmbjdcd = gmbjdcd;
        this.gmhjdcd = gmhjdcd;
        this.gmcsah = gmcsah;
        this.gmaddr = gmaddr;
        this.gmnaddr = gmnaddr;
        this.gmgunil = gmgunil;
        this.deptCd = deptCd;
        this.dejimj = dejimj;
        this.dejijmg = dejijmg;
        this.dejisg = dejisg;
        this.dejiju = dejiju;
        this.dejijg = dejijg;
        this.dejigu = dejigu;
        this.gcname = gcname;
        this.gcforigb = gcforigb;
        this.gcbirth = gcbirth;
        this.gctelno = gctelno;
        this.gcaddr = gcaddr;
        this.gcnaddr = gcnaddr;
        this.gmtype = gmtype;
        this.gmdate = gmdate;
        this.gmname = gmname;
        this.gmftype = gmftype;
        this.gmcdate = gmcdate;
        this.gmmunjuk = gmmunjuk;
        this.gmfloor = gmfloor;
        this.gmbsub = gmbsub;
        this.gmasub = gmasub;
        this.gmgdate = gmgdate;
        this.gmjdate = gmjdate;
        this.gmhdate = gmhdate;
        this.gmhcdate = gmhcdate;
        this.gmhcont = gmhcont;
        this.gmsname = gmsname;
        this.gmsnumber = gmsnumber;
        this.gmgname = gmgname;
        this.gmgnumber = gmgnumber;
        this.gminfo = gminfo;
        this.gmrst = gmrst;
        this.gmmst = gmmst;
        this.gmfacseq = gmfacseq;
        this.registId = registId;
        this.registTs = registTs;
        this.updateId = updateId;
        this.updateTs = updateTs;
    }
}
