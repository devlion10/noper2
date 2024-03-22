// specify the data
const rowData = [];

// let the grid know which columns and what data to use
const noperSiteChkGridOptions = {
    columnDefs : [
        { headerName:"No.", field: "rn", cellStyle: {textAlign: "center"}, width:50},
        { headerName:"점검일", field: "chkDate", cellStyle: {textAlign: "center"}, width:70},
        {
            headerName: "점검자",
            groupId: "checker",
            children: [
                {headerName: "직급", field: "chkChargeClspos", type: "", cellStyle: {textAlign: "center"}, width:60},
                {headerName: "성명", field: "chkChargeNm", type: "", cellStyle: {textAlign: "center"}, width:60}
            ],
            cellStyle: {textAlign: "center"}
        },
        {
            headerName: "점검 건물",
            groupId: "inspecGm",
            children: [
                {headerName: "건물등재번호", field:"gmbunho", width:100},
                {headerName: "건축물 소재지", field:"newAddr2", width:90},
                {headerName: "건물주", field:"gmjname", width:60},
                {headerName: "면적(㎡)", field:"gmmunjuk", width:60},
                {headerName: "층수", field:"gmFloors", width:45},
                {headerName: "구조", field:"gmgzcd", width:45},
                {headerName: "용도", field:"gmyd", width:45}
            ],
        cellStyle: {textAlign: "center"}
        },
        { headerName:"위반여부", field: "violYn", width:50 },
        { headerName:"조치여부", field: "trsctCntt", width:50 },
        { headerName:"확인", field: "confirm1", width:50 }
    ],
    defaultColDef: {
        headerClass: ["center", "justCenter"], cellClass: ["center", "justCenter"],
        sortable: true,
        editable: false,
        floatingFilter: false,
        resizable: true,
        cellDataType: false,
        editable: false,
        enableRowGroup: false,
        enablePivot: false,
        enableValue: false,
        minWidth: 30
    },

    rowGroupPanelShow: 'always',
    rowSelection: 'multiple',

    pagination: true,
    paginationPageSize: 10,
    rowData: rowData,

    onCellClicked: (params) => { if(params.colDef.field === 'qaSubject') goDetail(params.data.qaSeq)
    },
    onGridReady: function (event) {
        // 행 넓이 자동 조절
        event.api.sizeColumnsToFit();
    },
    overlayNoRowsTemplate: `
        <div>
            <p style="color: #6c757d">조회된 데이터가 없습니다.</p>
        </div>
    `
};

// setup the grid after the page has finished loading
document.addEventListener('DOMContentLoaded', () => {
    const gridDiv = document.querySelector('#myGrid');
    new agGrid.Grid(gridDiv, noperSiteChkGridOptions);
});

/*
document.getElementById('printBtn').addEventListener('click', (e) => {
    const url = new URL(window.location.href);
    const userName = e.target.getAttribute('data-user')
    const gmskk = url.searchParams.get('gmskk');
    const gmdjgb = url.searchParams.get('gmdjgb');
    const gmseqco = url.searchParams.get('gmseqco');
    const chkDate = url.searchParams.get('chkDate');
    const gmjname = url.searchParams.get('gmjname');
    const confirm1 = url.searchParams.get('confirm1');

})
*/

$(document).ready(function(){
    setDateBox();
    // 페이지 로드시 검색 수행
    //search();
});

// 점검년도 selectBox 연도 표시
function setDateBox(){
    let dt = new Date();
    let year = "";
    let com_year = dt.getFullYear();
    let str = "";
    str = '<select name="chkDate" id="chkDate" class="form-select required w100 mt-1" title="점검년도">';
    str += '<option value="" selected="selected">전체</option>';
    // 2007년부터 올해까지 출력.
    for(let y = (2007); y <= (com_year); y++){
        //str += "<option value='"+ y +"'>"+ y + "년" +"</option>";
        str += "<option value='"+y+"'>"+y+"</option>";
    }

    $("#chkDate").html(str);
}

// 새로고침 버튼 클릭 시 조회
$("#btnReload").click(() => {
    document.getElementById("noperSiteChkForm").reset();
});

// 현장점검대장현황 버튼 클릭 시 조회 reportㄴ
$("#reportCallBtn").click(() => {
    let user = $("#userName").val();
    searchData = JSON.stringify($("#noperSiteChkForm").serializeObject());
    let jsonData = JSON.parse(searchData);
    let data = {
              findGmskk: jsonData.gmskk
            , findGmdjgb: jsonData.gmdjgb
            , findGmseqco: jsonData.gmseqco
            , chkDateYear: jsonData.chkDate
            , findGmjname: jsonData.gmjname
            , findConfirm1: jsonData.confirm1
            , user: user
        };
    snReport.open('SPT_CHCK_STTUS', data);
});

//검색 버튼 클릭 시 조회
$("#btnSearch").click(() => {
    search()
});

var searchData = "";
// 초기 폼데이터 저장
searchData = JSON.stringify($("#noperSiteChkForm").serializeObject());

//조회 실행
function search(callback) {
    searchData = JSON.stringify($("#noperSiteChkForm").serializeObject());

    let url = "/api/unlicensedBuilingManagement/noperSiteChk/search";
    let type = "post";
    let json = searchData;
    let contentType = "application/json";
    ajaxCall(url, type, json, contentType, function (result) {
        for (let i = 0; i < result.length; i++) {
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

        }
        noperSiteChkGridOptions.api.setRowData(result);
        $("#totCnt > h3").text("Total " + (result ? result.length : 0));
        if (typeof callback === 'function') {
            callback(result)
        }
    });
}

// agGrid 행 조절
function onPageSizeChanged() {
    var value = document.getElementById('page-size').value;
    noperSiteChkGridOptions.api.paginationSetPageSize(Number(value));
}

// 엑셀다운로드
$('#excelDownBtn').click(() => {


    snExcelDownload.excelDownload($.ajax({
        url: '/unlcebldmng/download/noperSiteChkExcel',
        type: 'post',
        cache: false,
        contentType: 'application/json; charset=utf-8',
        data: searchData,
        xhrFields: {
            responseType: "blob",
        },
    }), "현장점검대장현황");
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
