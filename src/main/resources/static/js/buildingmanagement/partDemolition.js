$(document).ready(function(){
    // 기존무허가 대장관리 상세에서 기존무허가 소유자변경관리로 이동했을 때, 자동 대장번호 기입 로직
    const url = new URL(window.location.href);
    const urlGmskk = url.searchParams.get('gmskk');
    const urlGmdjgb = url.searchParams.get('gmdjgb');
    const urlGmseqco = url.searchParams.get('gmseqco');
    if (url.pathname === '/unlcebldmng/partDemolition' && urlGmskk != null && urlGmdjgb != null && urlGmseqco != null) {
        document.getElementById('gmskk').value = urlGmskk
        document.getElementById('gmdjgb').value = urlGmdjgb
        document.getElementById('gmseqco').value = urlGmseqco
        document.getElementById('gmseqco2').value = urlGmseqco
    }
});

// specify the data
const rowData = [];
const myCalendar = new customCalendar(document.getElementById('calendarSelect1'), document.getElementById('calendarRemote1'), document.getElementById('startDate1'), document.getElementById('endDate1'));

// let the grid know which columns and what data to use
const partDemolitionGridOptions = {
    columnDefs : [
         { headerName:"No.", field: "rn", width:60}
        ,{ headerName:"대장번호", field: "skkseq", cellClass:["underLine"], cellStyle: {textAlign: "center"}, width:120}
        ,{ headerName:"건물주소", width:60,
            groupId: "juso",
            children: [
                {headerName:"도로명주소", field:"newAddr1"},
                {headerName:"지번주소", field:"newAddr2"},
                {headerName:"대지/산", field:"gmcsah", width:70}
            ]
        }
        ,{ headerName:"소유자", field: "gmjname", width:80 }
        ,{ headerName:"소유자주민번호", field: "gmjjumin", width:110 }
        ,{ headerName:"철거사유", field: "gmbsau", width:100 }
        ,{ headerName:"철거일자", field: "gmbilja", width:80,
            valueFormatter: function (params) {
                return moment(params.value).format('yyyy-MM-DD');
            }
        }
    ],
    defaultColDef: {
        headerClass: ["center", "justCenter"], cellClass: ["center", "justCenter"],
        sortable: true,
        editable: false,
        floatingFilter: false,
        resizable: true,
        cellDataType: false,
        enableRowGroup: false,
        enablePivot: false,
        enableValue: false,
    },

    rowGroupPanelShow: 'always',
    rowSelection: 'multiple',

    pagination: true,
    paginationPageSize: 10,
    rowData: rowData,

    onCellClicked: (params) => {
        if(params.colDef.field === 'skkseq') {
            goPartDemolitionDetail(params);
        }
    },
    onGridReady: function (event) {
        // 행 넓이 자동 조절
        event.api.sizeColumnsToFit();
    },
    domLayout: 'autoHeight',
    animateRows: true,
    overlayNoRowsTemplate: `
        <div>
            <p style="color: #6c757d">조회된 데이터가 없습니다.</p>
        </div>
    `
};

// agGrid 행 조절
function onPageSizeChanged() {
    var value = document.getElementById('page-size').value;
    partDemolitionGridOptions.api.paginationSetPageSize(Number(value));
}

const goPartDemolitionDetail = (params) => {
    let gmskk = params.data.gmskk;
    let gmdjgb = params.data.gmdjgb;
    let gmseqco = params.data.gmseqco;

    $("#gmskkDtl").val(gmskk);
    $("#gmdjgbDtl").val(gmdjgb);
    $("#gmseqcoDtl").val(gmseqco);

    $("#partDemolitionDetailForm").submit();

}

// setup the grid after the page has finished loading
document.addEventListener('DOMContentLoaded', () => {
    const gridDiv = document.querySelector('#myGrid');
    new agGrid.Grid(gridDiv, partDemolitionGridOptions);

});

// 주소찾기 btn 있을때만 동작
// if(document.querySelector("#jusoBtn") != null){
//     document.querySelector("#jusoBtn").addEventListener("click", (e)=>{
//         const w = window.open('/common/juso', 'partDemolition', 'width=500px,height=500px');
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
//     // console.log(arguments);
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

$(function(){
    // 페이지 로드시 검색 수행
    //search();
});

// 새로고침 버튼 클릭 시 form 리셋
$("#btnReload").click(() => {
    document.getElementById("partDemolitionForm").reset();
    $("#calendarRemote1").html("");
});

// 조회 버튼 클릭 시 조회
$("#btnSearch").click(() => {
    search()
});

var searchData = "";
searchData = JSON.stringify($("#partDemolitionForm").serializeObject());

// 조회 실행
function search(callback) {
    searchData = JSON.stringify($("#partDemolitionForm").serializeObject());

    let url = "/api/unlicensedBuilingManagement/partDemolition/search";
    let type = "post";
    let json = searchData;
    let contentType = "application/json";
    ajaxCall(url, type, json, contentType, function (result) {
        // console.log(result);
        partDemolitionGridOptions.api.setRowData(result);
        $("#totCnt > h3").text("Total " + (result ? result.length : 0));
        if (typeof callback === 'function') {
            callback(result)
        }
    });
}

// 대장출력 버튼 클릭(report)
$("#reportCallBtn").click(() => {
    searchData = JSON.stringify($("#partDemolitionForm").serializeObject());
    let jsonData = JSON.parse(searchData);
    let user = $("#userName").val();
    let data = {
            gmskk: jsonData.gmskk
            , gmdjgb: jsonData.gmdjgb
            , gmseqco: jsonData.gmseqco
            , gmseqco2: jsonData.gmseqco2
            , daejang: jsonData.daejang
            , gmjname: jsonData.gmjname
            , gmskkcd: jsonData.gmskkcd
            , gmhjdcd: jsonData.gmhjdcd
            , gmbjdcd: jsonData.gmbjdcd
            , gmcsah: jsonData.gmcsah
            , gmcbuh: jsonData.gmcbuh
            , gmcji: jsonData.gmcji
            , gmbilja1: jsonData.gmbilja1.replaceAll('-', '')
            , gmbilja2: jsonData.gmbilja2.replaceAll('-', '')
            , searchJuso: jsonData.searchJuso
            , user: user
        };

    snReport.open('PART_REMVL_STTUS', data);
});

// 엑셀다운로드
$('#excelDownBtn').click(() => {

    snExcelDownload.excelDownload($.ajax({
        url: '/unlcebldmng/download/partDemolitionExcel',
        type: 'post',
        cache: false,
        contentType: 'application/json; charset=utf-8',
        data: searchData,
        xhrFields: {
            responseType: "blob",
        },
    }), "부분철거신고현황");
});

//ajax 공통
function ajaxCall(url, type, param, contentType, callback) {
    $.ajax({
        url : url,
        type : type,
        data : param,
        contentType: contentType,
        success: function (result){
            return callback(result);
        }
    });
};
