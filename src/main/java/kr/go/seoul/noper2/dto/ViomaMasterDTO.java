package kr.go.seoul.noper2.dto;

import kr.go.seoul.noper2.util.annotations.ExcelOrder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
public class ViomaMasterDTO {
    private String rn;
    @ExcelOrder(order = 1, headerName = "관리번호")
    private String gmskk;
    private String gmskkcd;
    @ExcelOrder(order = 2, headerName = "자치구")
    private String gmbjdcd;
    private String gmhjdcd;
    private String gmcsah;
    @ExcelOrder(order = 3, headerName = "지번주소")
    private String gmaddr;
    @ExcelOrder(order = 4, headerName = "도로명주소")
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
    @ExcelOrder(order = 11, headerName = "적발일자")
    private String gmdate;
    private String gmname;
    private String gmftype;
    private String gmcdate;
    private String gmmunjuk;
    private String gmfloor;
    @ExcelOrder(order = 5, headerName = "위반용도")
    private String gmbsub;
    @ExcelOrder(order = 6, headerName = "신규적발")
    private String gmasub;
    @ExcelOrder(order = 8, headerName = "고발")
    private String gmgdate;
    @ExcelOrder(order = 9, headerName = "고발 조치완료")
    private String gmjdate;
    @ExcelOrder(order = 10, headerName = "행정대집행")
    private String gmhdate;
    @ExcelOrder(order = 7, headerName = "조치완료")
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
}
