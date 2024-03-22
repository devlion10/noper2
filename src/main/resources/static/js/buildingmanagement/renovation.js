const rowData = [];

// let the grid know which columns and what data to use
const renovationGridOptions = {
    columnDefs : [
        { headerName:"No.", field: "rn", cellStyle: {textAlign: "center"}, width: 50},
        { headerName:"대장번호", field: "skkseqno", cellStyle: {textAlign: "center"}, width: 100, cellClass:["underLine"]},
        { headerName:"접수번호", field: "balno", cellStyle: {textAlign: "center"}, width: 70},
        { headerName:"접수일자", field: "gbsgoilV", cellStyle: {textAlign: "center"}, width: 70},
        { headerName:"신고내용", field: "gbscotent", cellStyle: {textAlign: "center"}, width: 120},
        { headerName:"처리내용", field: "chcontent", cellStyle: {textAlign: "center"}, width: 120},
        { headerName:"준공일", field: "jgongV", cellStyle: {textAlign: "center"}, width: 70},
        { headerName:"건물주", field: "gmjname", cellStyle: {textAlign: "center"}, width: 70}
    ],
    defaultColDef: {
        headerClass: ["center", "justCenter"],
        cellClass: ["center", "justCenter"],
        sortable: true,
        editable: false,
        floatingFilter: false,
        resizable: true,
        cellDataType: false,
        editable: false,
        enableRowGroup: false,
        enablePivot: false,
        enableValue: false,
        minWidth: 50,
    },
    pagination: true,
    paginationPageSize: 10,
    rowData: rowData,
    onGridReady: function (event) {
        // 행 넓이 자동 조절
        event.api.sizeColumnsToFit();
    },
    onCellClicked: (params) => {
        if(params.colDef.field === 'skkseqno') {
            goRenovationDetail(params);
        }
    },
    domLayout: 'autoHeight',
    animateRows: true,
    overlayNoRowsTemplate: `
        <div>
            <p style="color: #6c757d">조회된 데이터가 없습니다.</p>
        </div>
    `
};

const goRenovationDetail = (params) => {
    let gmskk = params.data.gmskk;
    let gmdjgb = params.data.gmdjgb;
    let gmseqco = params.data.gmseqco;
    let gmilno = params.data.gmilno;
    let balno = params.data.balno;
    let gbsgoil = params.data.gbsgoil;
    let gbscotent = params.data.gbscotent;
    let chcontent = params.data.chcontent;
    let jgong = params.data.jgong;
    let gmjname = params.data.gmjname;

    // let str = "<input type='hidden' name='gmilno' id='gmilnoDtlIp' value='"+gmilno+"'/>";

    $("#gmskkDtlIp").val(gmskk);
    $("#gmdjgbDtlIp").val(gmdjgb);
    $("#gmseqcoDtlIp").val(gmseqco);
    // if (gmilno != null && gmilno != ""){
    //     $("#renovationDetailForm").append(str);
    // }
    $("#gmilnoDtlIp").val(gmilno);
    $("#balnoDtlIp").val(balno);
    $("#gbsgoilDtlIp").val(gbsgoil);
    // $("#gbscotentDtlIp").val(gbscotent);
    // $("#chcontentDtlIp").val(chcontent);
    $("#jgongDtlIp").val(jgong);
    $("#gmjnameDtlIp").val(gmjname);


    // 클릭한 행의 noticeSeq 값을 사용하여 URL 생성
    // const detailUrl = `/unlcebldmng/renovation/detail?gmskk=${gmskk}&gmdjgb=${gmdjgb}&gmseqco=${gmseqco}&gmilno=${gmilno}`;
    // const detailUrl = `/unlcebldmng/renovation/detail?params=${params}`;

    $("#renovationDetailForm").submit();

    // 새 탭으로 상세 페이지 열기
    // window.open(detailUrl, '_blank');
    // window.location.href = detailUrl;
}

// setup the grid after the page has finished loading
document.addEventListener('DOMContentLoaded', () => {
    const gridDiv = document.querySelector('#myGrid');
    new agGrid.Grid(gridDiv, renovationGridOptions);
});

// agGrid 행 조절
function onPageSizeChanged() {
    var value = document.getElementById('page-size').value;
    renovationGridOptions.api.paginationSetPageSize(Number(value));
}

$(document).ready(function(){
    // 페이지 로드시 검색 수행
    //search();
});

let searchData = '';
// 초기 조회 formData
searchData = JSON.stringify($("#renovationForm").serializeObject());

//새로고침 버튼 클릭 시 조회
$("#btnReload").click(() => {
    document.getElementById("renovationForm").reset();
    calendarInit();
    search()
});

//검색 버튼 클릭 시 조회
$("#btnSearch").click(() => {
    search()
});

//조회 실행
function search(callback) {
    searchData = JSON.stringify($("#renovationForm").serializeObject());

    let url = "/api/unlicensedBuilingManagement/renovation/search";
    let type = "post";
    let json = searchData;
    let contentType = "application/json";
    ajaxCall(url, type, json, contentType, function (result) {
        /*for (let i = 0; i < result.length; i++) {
            if (result[i].trsctCntt == null) {
                result[i].trsctCntt = 'X';
            } else {
                result[i].trsctCntt = 'O';
            }

            if (result[i].violYn == 'N') {
                result[i].violYn = 'X';
            } else {
                result[i].violYn = 'O';
            }

            if (result[i].confirm1 == 'N') {
                result[i].confirm1 = '미승인';
            } else {
                result[i].confirm1 = '승인';
            }

        }*/
        renovationGridOptions.api.setRowData(result);
        $("#totCnt > h3").text("Total " + (result ? result.length : 0));
        if (typeof callback === 'function') {
            callback(result)
        }
    });
}

// 주소찾기 btn 있을때만 동작
// if(document.querySelector("#jusoBtn") != null){
//     document.querySelector("#jusoBtn").addEventListener("click", (e)=>{
//         const w = window.open('/common/juso', 'demolition', 'width=500px,height=500px');
//         w.focus();
//     });
// }
/*
전체 도로명주소 : roadFullAddr,
도로명주소(참고항목 제외) : roadAddrPart1,
고객 입력 상세 주소 : addrDetail,
도로명주소 참고항목 : roadAddrPart2,
도로명주소(영문) : engAddr,
지번주소 : jibunAddr,
우편번호 : zipNo,
행정구역코드 : admCd,
도로명코드 : rnMgtSn,
건물관리번호 : bdMgtSn,
상세건물명 : detBdNmList,
건물명 : bdNm,
공동주택여부(1 : 공동주택, 0 : 비공동주택) : bdKdcd,
시도명 : siNm,
시군구명 : sggNm,
읍면동명 : emdNm,
법정리명 : liNm,
도로명 : rn,
지하여부(0 : 지상, 1 : 지하) : udrtYn,
건물본번 : buldMnnm,
건물부번 : buldSlno,
산여부(0 : 대지, 1 : 산) : mtYn,
지번본번(번지) : lnbrMnnm,
지번부번(호) : lnbrSlno,
읍면동일련번호 : emdNo
*/

// 주소데이터 받기
// function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn,
//                       detBdNmList, bdNm, bdKdcd, siNm, sggNm, emdNm, liNm, rn, udrtYn, buldMnnm, buldSlno, mtYn, lnbrMnnm, lnbrSlno, emdNo){
//     console.log(arguments);
//     $('input[name=newAddr1]').val(roadFullAddr);
//     $('input[name=newAddr2]').val(jibunAddr + " " + addrDetail);
//     $('input[name=gmskkcd]').val(admCd.substring(0, 5));
//     $('input[name=gmbjdcd]').val(admCd.substring(5, 10));
//     $('input[name=gmcsah]').val(mtYn);
//     $('input[name=gmcbuh]').val(lnbrMnnm);
//     $('input[name=gmcji]').val(lnbrSlno);
//
//     // console.log($('input[name=gmskkcd]').val());
//     // console.log($('input[name=gmbjdcd]').val());
//     // console.log($('input[name=gmcsah]').val());
//     // console.log($('input[name=gmcbuh]').val());
//     // console.log($('input[name=gmcji]').val());
// }

// 대장출력 버튼 클릭
$('#reportCallBtn').click(() => {
    searchData = JSON.stringify($("#renovationForm").serializeObject());
    let jsonData = JSON.parse(searchData);

    let gmhjdcd = "";
    let gmbjdcd = "";
    let gmcbuh = "";
    let gmcji = "";

    let user = $("#userName").val();

    const data = {
          gmskk: jsonData.gmskk
        , gmdjgb: jsonData.gmdjgb
        , gmseqco: jsonData.gmseqco
        , gmseqco2: jsonData.gmseqco2
        , gmjname: jsonData.gmjname
        , gmskkcd: jsonData.gmskk
        , gmhjdcd: gmhjdcd
        , gmbjdcd: gmbjdcd
        , gmcsah: jsonData.gmcsah
        , gmcbuh: gmcbuh
        , gmcji: gmcji
        , gmgunil1: jsonData.gmgunil1
        , gmgunil2: jsonData.gmgunil2
        , gbsgoil1: jsonData.gbsgoil1
        , gbsgoil2: jsonData.gbsgoil2
        , jgong1: jsonData.jgong1
        , jgong2: jsonData.jgong2
        , searchJuso: jsonData.searchJuso
        , user: user
    }

    snReport.open('RPAIR_STTUS', data);
});
// 엑셀다운로드 버튼 클릭
$('#excelDownBtn').click(() => {

    snExcelDownload.excelDownload($.ajax({
        url: '/unlcebldmng/download/renovationExcel',
        type: 'post',
        cache: false,
        contentType: 'application/json; charset=utf-8',
        data: searchData,
        xhrFields: {
            responseType: "blob",
        },
    }), "개보수신고현황");
});

//ajax 공통
function ajaxCall(url, type, param, contentType, callback) {
    $.ajax({
        url: url,
        type: type,
        data: param,
        contentType: contentType,
        success: function (result) {
            return callback(result);
        }
    });
}
