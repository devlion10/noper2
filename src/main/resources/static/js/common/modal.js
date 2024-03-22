class choiceRenderer{
    // gets called once before the renderer is used
    init(params) {
        // create the cell
        this.choiceDiv = document.createElement('div');
        this.choiceDiv.innerHTML = `
          <span>
              <button class="choiceBtn btn btn-sm btn-outline-secondary">선택</button>
          </span>
       `;

        // get references to the elements we want
        this.choiceBtn = this.choiceDiv.querySelector('.choiceBtn');

        // 대장번호 파라미터 값 셋팅
        this.eventListener = () => this.setParam(params);
        this.choiceBtn.addEventListener('click', this.eventListener);
    }

    // 대장번호찾기 조회값 선택버튼을 눌렀을 때
    setParam(params) {
        let gmskk = params.data.gmskk;
        let gmdjgb = params.data.gmdjgb;
        let gmseqco = params.data.gmseqco;

        $("#gmskk").val(gmskk);
        $("#gmdjgb").val(gmdjgb);
        $("#gmseqco").val(gmseqco);
        $(".gmseqco").val(gmseqco);
        $("#gmseqco2").val(gmseqco);
        $("#modalCloseBtn").click();
        $("#btnSearch").click();
        $("#searchBtn").click();
        // noperNumModalGridOptions.api.setRowData(null);
        // noperNumModalGridOptions.api.applyTransaction({update:[data]});
        // noperNumModalGridOptions.api.setRowData(data);
    }

    getGui() {
        return this.choiceDiv;
    }
}

$('#siteChkList').click(() => {
    $("#siteChkAddForm")[0].reset();
    $("#user-files3").html("");
});

const noperNumModalColumnDefs = [
    { headerName:"No.", field: "rn", width: 70, suppressSizeToFit: true, cellStyle: {textAlign: "center"}},
    { headerName:"대장번호", field: "num", width: 150, suppressSizeToFit: true, cellStyle: {textAlign: "center"}},
    { headerName:"seq", field: "seq", hide:true},
    { headerName:"건물주소",
        groupId: "gmjuso",
        children: [
            {headerName: "도로명주소", field:"newaddr1", width: 170, suppressSizeToFit: true, cellStyle: {textAlign: "center"}},
            {headerName: "지번주소", field:"newaddr2", width: 170, suppressSizeToFit: true, cellStyle: {textAlign: "center"}}
        ]
    },
    { headerName:"건물주", field: "gmjname" , width: 80, suppressSizeToFit: true, cellStyle: {textAlign: "center"}},
    { headerName:"주민번호", field: "gmjjumin", width: 150, suppressSizeToFit: true, cellStyle: {textAlign: "center"},
        valueGetter: function (params) {
            var param = "";
            if(params.data.gmjjumin != null){
            if (params.data.gmjjumin.indexOf('-') != -1) {
                // 주민번호에 -가 있을경우
                param = params.data.gmjjumin;
            }else {
                // 주민번호에 -가 없을경우
               param = params.data.gmjjumin.slice(0,6) + "-" + params.data.gmjjumin.slice(6,13);
            }
            return param;
            }
        }
    },
    { headerName:"건물구조", field: "gmgzcd", width: 90, suppressSizeToFit: true, cellStyle: {textAlign: "center"}},
    { headerName:"선택", field: "choice", width: 70, cellRenderer: choiceRenderer, cellStyle: {textAlign: "center"}},
];

// specify the data
const noperNumModalRowData = [];

// let the grid know which columns and what data to use
const noperNumModalGridOptions = {
    columnDefs: noperNumModalColumnDefs,
    suppressClickEdit: true,
    editType: "fullRow",
    defaultColDef: {
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

    animateRows: true,
    pagination: true,
    paginationPageSize: 10,
    rowData: noperNumModalRowData,

    //onCellClicked: (params) => { if(params.colDef.field === 'choiceBtn') goDetail(params.data.gmjjumin)
    onCellClicked: (params) => {
        if(params.colDef.field === 'choice') {
            if($('input[name=popFlag]').val() == 'confirmation'){
                confirmationDetail(params);
                $('.closeBtn').click();
            }else if($('input[name=popFlag]').val() == 'absorption' || $('input[name=popFlag]').val() == 'change'){
                 absorptionDetail(params);
                 $('.closeBtn').click();
            }else {
                $('.closeBtn').click();
            }
        }
        //goDetail(params.data.gmjjumin)


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
    const gridModalDiv = document.querySelector('#myGridNoperNumModal');
    new agGrid.Grid(gridModalDiv, noperNumModalGridOptions);

    const gmskkInput = $('#gmskk')
    const gmskkTmpInput = $('#gmskkTmp')
    const noperNumGmskk = $('#noperNumGmskk');
    const noperNumGmskkCd = $('#noperNumGmskkcd');
    // 위치시군구정보에 시군구 값 기입
    noperNumGmskkCd.val(noperNumGmskk.val());
    /* 1. 관리자 권한일 경우, 번호찾기모달의 select를 변경 시 현황의 대장번호 select 값도 변경
       2. 선택한 시군구에 해당하는 동정보로 select 값 변경 */
    noperNumGmskk.change(function () {
        const targetValue = $(this).val()
        noperNumGmskkCd.val(targetValue);
        $('#gmskk').val(targetValue)
        $('#gmskkTmp').val(targetValue)

        const noperNumSearchForm = $("#noperNumSearchForm").serializeObject();
        const url = "/common/api/ledger";
        const type = "post";
        const json = JSON.stringify(noperNumSearchForm);
        const contentType = "application/json";
        noperNumAjaxCall(url, type, json, contentType, function (result) {
            let str = "<option value='all' selected='selected'>전체</option>";
            // 법정동
            $.each(result.bjdJusoList, function (index, item) {
                let gmbjdcd = this.gmbjdcd;
                let bnam = this.bnam;
                str += "<option value='"+gmbjdcd+"'>"+bnam+"</option>"
            });
            $("#noperNumGmbjdcd").html(str);
        });
    });
    // 관리자 권한일 경우, 현황의 대장번호 select를 변경 시 번호찾기모달 select 값도 변경
    gmskkInput.change(function () {
        const targetValue = $(this).val()
        noperNumGmskk.val(targetValue);
        noperNumGmskkCd.val(targetValue);
    });
    gmskkTmpInput.change(function () {
        const targetValue = $(this).val()
        noperNumGmskk.val(targetValue);
        noperNumGmskkCd.val(targetValue);
    });
});

const confirmationDetail = (param) => {
    const data = param.data;
    const form = $('#confirmationAddForm')[0];
    $('.naRoadNm').text(data.gmskkname);
    form.naRoadNm = data.gmskkname;
    console.log(data)
    $('.gmmunjuk').text(data.gmmunjuk+'㎡');
    form.gmmunjuk.value = data.gmmunjuk;
    $('.tojimunjuk').text(data.tojimunjuk+'㎡');
    form.tojimunjuk.value = data.tojimunjuk;
    $('.gmgzcd').text(data.gmgzcd);
    form.gmgzcd.value = data.gmgzcd;
    $('.naEtc').text(data.gmroad);
    form.naEtc.value = data.gmroad;
    $('.tojisg').text(data.tojisg);
    form.tojisg.value = data.tojisg;
    $('.gmbunho').text(data.num);
    form.gmskk.value = data.gmskk;
    form.gmdjgb.value = data.gmdjgb;
    form.gmseqco.value = data.gmseqco;

    $('.gmjname').text(data.gmjname);
    form.gmjname.value = data.gmjname;
    $('.gmjjumin').text(data.gmjjumin);
    form.gmjjumin.value = data.gmjjumin;
    $('.gmskkname').text(data.gmskkname);
    form.gmskkname.value = data.gmskkname;
    $('.gmjsna').text(data.roadgmjsna);
    form.gmjsna.value = data.roadgmjsna;
    form.gmjjname.value = data.gmjjname;
    $('.newaddr').text(
        data.gmjsna != null
            ? `${data.gmjsna}${data.gmjjname !== null ? ` ${data.gmjjname}` : ''}`
            : data.gmjsna2);
}

// 소유자 병합신고
const absorptionDetail = (param) => {
    const data = param.data;
    const form = $('#ownerChangeFindForm')[0];
    form.gmskk.value = data.gmskk;
    form.gmdjgb.value = data.gmdjgb;
    form.gmseqco.value = data.gmseqco;
}

//화면 로드시 조회화면 데이터 출력.
$('#noperNumSearchDiv').on('show.bs.modal', function () {
    noperNumModalOnload();
})

// 검색 조건 - 새로고침 버튼 클릭 시 form Reset
$("#btnNoperNumReset").click(() => {
    document.getElementById("noperNumSearchForm").reset();
    $('#noperNumGmskkcd').val($('#noperNumGmskk').val())
});

//검색
$("#btnNoperNumSearch").click(() => {
    noperNumSearch();
});

function noperNumModalOnload(callback) {
    const noperNumSearchForm = $("#noperNumSearchForm").serializeObject();

    let url = "/common/api/ledger";
    let type = "post";
    let json = JSON.stringify(noperNumSearchForm);
    let contentType = "application/json";
    noperNumAjaxCall(url, type, json, contentType, function (result) {
        let str1 = "<option value='all' selected='selected'>전체</option>";
        let str2 = "<option value='all' selected='selected'>전체</option>";
        let str3 = "<option value='all' selected='selected'>전체</option>";
        $.each(result.gmdjgbList, function (index, item) {
            let cdkey = this.cdkey;
            let gmdjgb = this.cdvalue;
            str1 += "<option value='"+cdkey+"'>"+gmdjgb+"</option>"
        });
            $("#noperNumGmdjgb").html(str1);

        $.each(result.gmcsahList, function (index, item) {
            let cdkey = this.cdkey;
            let gmcsah = this.cdvalue;
            str2 += "<option value='"+cdkey+"'>"+gmcsah+"</option>"
        });
            $("#noperNumGmcsah").html(str2);

        $.each(result.bjdJusoList, function (index, item) {
            let gmbjdcd = this.gmbjdcd;
            let bnam = this.bnam;
            str3 += "<option value='"+gmbjdcd+"'>"+bnam+"</option>"
        });
            $("#noperNumGmbjdcd").html(str3);

        if (typeof callback === 'function') {
            callback(result)
        }
    });
}

function noperNumSearch(callback) {
    const noperNumSearchFormJson = $("#noperNumSearchForm").serializeObject();
    const urlPath = new URL(location.href).pathname;
    if (urlPath === '/unlcebldmng/demolition') {
        // url이 /unlcebldmng/demolition 이라면
        noperNumSearchFormJson.degb = 'Y'
    } else if (urlPath === '/unlcebldmng/partDemolition') {
        // url이 /unlcebldmng/partDemolition 이라면
        noperNumSearchFormJson.degb = 'C'
    } else if (urlPath === '/unlcebldmng/confirmation') {
        // url이 /unlcebldmng/confirmation 이라면
        noperNumSearchFormJson.degb = 'R'
    } else {
        // 기타
        noperNumSearchFormJson.degb = 'N'
    }
    const noperNumSearchForm = JSON.stringify(noperNumSearchFormJson);
    const formData = JSON.parse(noperNumSearchForm);

    if (formData.gmjjuminTemp1 != "" && formData.gmjjuminTemp2 == "") {
        return snAlert.alert("주민번호 13자리를\n입력후 조회해주세요.");
    }
    if (formData.gmjjuminTemp1 == "" && formData.gmjjuminTemp2 != "") {
        return snAlert.alert("주민번호 13자리를\n입력후 조회해주세요.");
    }

    let url = "/common/api/noperNumSearch";
    let type = "post";
    let json = noperNumSearchForm;
    let contentType = "application/json";

    noperNumAjaxCall(url, type, json, contentType, function (result) {
        noperNumModalGridOptions.api.setRowData(result.noperNumList);

        if (typeof callback === 'function') {
            callback(result)
        }
    });
}

//ajax 공통
function noperNumAjaxCall(url, type, param, contentType, callback) {
    $.ajax({
        url: url,
        type: type,
        data: param,
        contentType: contentType,
        success: function (result) {
            return callback(result);
        }
    })
};

class customChoiceRenderer{
    // gets called once before the renderer is used
    init(params) {
        // create the cell
        this.choiceDiv = document.createElement('div');
        this.choiceDiv.innerHTML = `
          <span>
              <button class="choiceBtn btn btn-sm btn-outline-secondary">선택</button>
          </span>
       `;

        // get references to the elements we want
        this.choiceBtn = this.choiceDiv.querySelector('.choiceBtn');

        // 대장번호 파라미터 값 셋팅
        this.eventListener = () => this.setParam(params);
        this.choiceBtn.addEventListener('click', this.eventListener);
    }

    setParam(params) {
        let base = params.data.gmskk.split('-');
        let gmskk = base[0];
        let gmdjgb = base[1];
        let gmseqco = base[2];

        $("#gmskk").val(gmskk);
        $("#gmdjgb").val(gmdjgb).change(

        );
        $("#startGmseqco").val(gmseqco);
        $("#endGmseqco").val(gmseqco);

        $("#modalCloseBtn").click();
        // noperNumModalGridOptions.api.setRowData(null);
        // noperNumModalGridOptions.api.applyTransaction({update:[data]});
        // noperNumModalGridOptions.api.setRowData(data);
    }

    getGui() {
        return this.choiceDiv;
    }
}

const viomaNumModalColumnDefs = [
    { headerName:"No", field: "rn", width: 60, minWidth:60, suppressSizeToFit: true, cellStyle: {textAlign: "center"}, sort:'desc'},
    { headerName:"대장번호", field: "gmskk", width: 60, minWidth:60, suppressSizeToFit: true, cellStyle: {textAlign: "center"}},
    { headerName:"seq", field: "seq", hide:true},
    { headerName:"건물주소",
        groupId: "gmjuso",
        children: [
            {headerName: "도로명주소", field:"newAddr1", width: 200, minWidth:120, suppressSizeToFit: true, cellStyle: {textAlign: "center"}},
            {headerName: "지번주소", field:"newAddr2", width: 200, minWidth:120, suppressSizeToFit: true, cellStyle: {textAlign: "center"}}
/*
            {headerName: "도로명주소", field:"gmnaddr", width: 200, minWidth:120, suppressSizeToFit: true, cellStyle: {textAlign: "center"}},
            {headerName: "지번주소", field:"gmaddr", width: 200, minWidth:120, suppressSizeToFit: true, cellStyle: {textAlign: "center"}}
*/
        ]
    },
    { headerName:"건물주", field: "gcname" , width: 60, minWidth:60, suppressSizeToFit: true, cellStyle: {textAlign: "center"}},
    { headerName:"생년월일", field: "gcbirth", width: 200, minWidth:60, suppressSizeToFit: true, cellStyle: {textAlign: "center"}},
    { headerName:"건물구조", field: "gmmst", width: 60, minWidth:60, suppressSizeToFit: true, cellStyle: {textAlign: "center"}},
    { headerName:"선택", field: "choice", width: 60, minWidth:60, cellRenderer: customChoiceRenderer},
];

// specify the data
const viomaNumModalRowData = [];

// let the grid know which columns and what data to use
const viomaNumModalGridOptions = {
    columnDefs: viomaNumModalColumnDefs,
    defaultColDef: {
        headerClass: ["center", "justCenter"], cellClass: ["center", "justCenter"],
        // width: 160,
        sortable: true,
        editable: false,
        floatingFilter: false,
        resizable: true,
        cellDataType: false,
        enableRowGroup: false,
        enablePivot: false,
        enableValue: false,
        minWidth: 100,
        flex: 1,
        filter: true,
    },

    rowGroupPanelShow: 'always',
    rowSelection: 'multiple',

    pagination: true,
    paginationPageSize: 10,
    rowData: viomaNumModalRowData,

    //onCellClicked: (params) => { if(params.colDef.field === 'choiceBtn') goDetail(params.data.gmjjumin)
    onCellClicked: (params) => {
        if(params.colDef.field === 'choice') {
            if($('input[name=popFlag]').val() == 'confirmation'){
                confirmationDetail(params);
                $('.closeBtn').click();
            }else if($('input[name=popFlag]').val() == 'absorption' || $('input[name=popFlag]').val() == 'change'){
                absorptionDetail(params);
                $('.closeBtn').click();
            }else {
                $('.closeBtn').click();
            }
        }
        //goDetail(params.data.gmjjumin)


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
    const gridModalDiv = document.querySelector('#myGridViomaNumModal');
    const grid = new agGrid.Grid(gridModalDiv, viomaNumModalGridOptions);

    $('#viomaNumSearchForm').submit(function () {
        $.get('/api/viobldmng/list', $('#viomaNumSearchForm').serialize(), function (data) {
            grid.gridOptions.api.setRowData([]);
            grid.gridOptions.api.setRowData(data);
        })
        return false;
    })

    $('#viomaNumSearchForm').submit();
});