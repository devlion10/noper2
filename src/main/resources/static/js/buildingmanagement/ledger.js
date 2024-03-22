const rowData = [];
$(document).ready(function(){
    var searchData  = "";
    var findListData = "";
    // 초기 폼데이터 저장
    $('input[name=gmskk]').val($('#gmskk').val());
    searchData = JSON.stringify($("#ledgerFindForm").serializeObject());
    if($('input[name=legerFlag]').val() == 'newLedger'){
        $('input[name=popFlag]').val('newLedger');
    }
    const myCalendar = new customCalendar('calendarSelect1', 'calendarRemote1', 'gmgunil1', 'gmgunil2');
    // 건축일자
    if ($('#ledgerFindForm')[0].gmgunil1.value !== '' && $('#ledgerFindForm')[0].gmgunil2.value !== '') {
        if ($("#gmgunil1").val() != '') {
            $("input[name=gmgunil1]").val(moment($("#gmgunil1").val()).format('YYYY-MM-DD'));
        }
        if ($("#gmgunil2").val() != '') {
            $("input[name=gmgunil2]").val(moment($("#gmgunil2").val()).format('YYYY-MM-DD'));
        }
        document.getElementById('calendarSelect1').value = calendarType;
        // 버튼클릭
        CalendarButtonClickEvent(calendarBtn);
    }
    // 유효성 체크
    const valuedate = () => {
       // 대장번호 selectbox
       if($("select[name=gmdjgb]").val() == '전체') $("select[name:gmdjgb]").val("");
       // 소유구분 selectbox
       if($("select[name=tojisg]").val() == '전체') $("select[name:tojisg]").val("");
       // 용도 selectbox
       if($("select[name=gmyd]").val() == '전체') $("select[name:gmyd]").val("");
       // 관리행정도 selectbox
       if($("select[name=gmhjdcd]").val() == '전체') $("select[name:gmhjdcd]").val("");
    };

    const userName = $("#ledgerFindForm input[name=userName]").val();

    $('#ledgerGmjPrint').click(function(e) {
        var jsonData = JSON.parse(searchData);
        /*
        if( jsonData.gmskk == null || jsonData.gmskk == "" ){
            return snAlert.alert("대장번호를 입력하여\n조회 후 출력해주세요.");
        }
        */
        const data = {
            // 건물주 포함
            REGSTR_STTUS_RBLDNR: {gmskk: $("input[name=gmskk]").val(), gmdjgb: $("#gmdjgb").val(), gmseqco: jsonData.gmseqco, daejang: "", gmjname: "", tojisg: jsonData.tojisg, gmyd: jsonData.gmyd, gmgunil1:jsonData.gmgunil1, gmgunil2: jsonData.gmgunil2, gmseqco2: jsonData.gmseqco2, gmhjdcd: "", gmbjdcd: '', gmcsah: jsonData.gmcsah, gmcbuh: jsonData.gmcbuh, gmcji: jsonData.gmcji, searchJuso: jsonData.searchJuso, user: userName},
        }
        snReport.open('REGSTR_STTUS_RBLDNR', data.REGSTR_STTUS_RBLDNR);
    });
    $('#ledgerPrint').click(function(e) {
        var jsonData = JSON.parse(searchData);
        /*
        if( jsonData.gmskk == null || jsonData.gmskk == "" ){
            return snAlert.alert("대장번호를 입력하여\n조회 후 출력해주세요.");
        }
        */
        const data = {
            // 건물주 미포함
            REGSTR_STTUS: {GMSKK: $("input[name=gmskk]").val(), GMDJGB: $("#gmdjgb").val(), GMSEQCO: jsonData.gmseqco, DAEJANG: "", GMJNAME: "", TOJISG: jsonData.tojisg, GMYD: jsonData.gmyd, GMGUNIL1: jsonData.gmgunil1, GMGUNIL2: jsonData.gmgunil2, GMSEQCO2: jsonData.gmseqco2, GMHJDCD: "", GMBJDCD: '', GMCSAH: jsonData.gmcsah, GMCBUH: jsonData.gmcbuh, GMCJI: jsonData.gmcji, searchJuso: jsonData.searchJuso, user: userName},
        }
        snReport.open('REGSTR_STTUS', data.REGSTR_STTUS);
     });



    $('#btnReload').click(() => {
        $("#ledgerFindForm")[0].reset();
        $('#calendarRemote1').html("");
        $('#startDate').val("");
        $('#endDate').val("");
        $('#gmgunil1').val("");
        $('#gmgunil2').val("");

    });
    //$('#newLegerAddBtn').click(() => {location.href="/unlcebldmng/ledgerAdd";});
    $('#newLegerAddBtn').click(() => { goNewLedger();});
    //$('#newLegerListBtn').click(() => {location.href="/unlcebldmng/newLedger";});
    //document.getElementById("btnSearch").addEventListener('click',findList);
    $('#btnSearch').click(function(){
        if($('#gmskk').val() === '11000'){
            $('input[name=gmskk]').val('');
        }else{
            $('input[name=gmskk]').val($('#gmskk').val());
        }
        searchData = JSON.stringify($("#ledgerFindForm").serializeObject());
        findList();
    });

    // 엑셀다운로드
    $('#csvBtn').click(() => {
        //snGrid.exportCsv();
        var excelFileText = "";
        if($('input[name=legerFlag]').val() == "newLedger"){
            excelFileText = "신규대장관리"
        }else{
            excelFileText = "대장관리"
        }
        snExcelDownload.excelDownload($.ajax({
             url: '/unlcebldmng/download/ledgerExcel',
             type: 'post',
             cache: false,
             contentType: 'application/json; charset=utf-8',
             data: searchData,
             xhrFields: {
                responseType: "blob",
             },
        }), excelFileText);
/*
         snExcelDownload.excelDownload($.ajax({
             url: '/download/excel',
             type: 'GET',
             cache: false,
             xhrFields: {
                 responseType: "blob",
             },
         }), 'fileName')
 */
    });

    var ledgerFindForm =  JSON.stringify($("#ledgerFindForm").serializeObject());


    // 대장 리스트
    function findList() {
       valuedate();
       if($("#startDate").val() != ''){
            $("input[name=gmgunil1]").val(moment($("#startDate").val()).format('YYYYMMDD'));
       }
       if($("#endDate").val() != ''){
            $("input[name=gmgunil2]").val(moment($("#endDate").val()).format('YYYYMMDD'));
       }
       if($("#gmgunil1").val() != ''){
         $("input[name=gmgunil1]").val(moment($("#gmgunil1").val()).format('YYYYMMDD'));
       }
      if($("#gmgunil2").val() != ''){
        $("input[name=gmgunil2]").val(moment($("#gmgunil2").val()).format('YYYYMMDD'));
       }

        $('.loading-background')[0].style.display = 'block'
       $.post({
           url: "/api/unlicensedBuilingManagement/search",
           data: JSON.stringify($("#ledgerFindForm").serializeObject()),
           contentType: 'application/json; charset=utf-8'}).done(e=>{
               $('#totCnt > h3').text("Total " + e.length);
               LedgerListGridOptions.api.setRowData(e)
           $('.loading-background')[0].style.display = 'none'
               findListData = e;

           });

    }
    // 주소찾기 btn 있을때만 동작
    if(document.querySelector("#jusoBtn") != null){
         document.querySelector("#jusoBtn").addEventListener("click", (e)=>{
             const w = window.open('/common/juso', 'juso', 'width=500px,height=500px');
             w.focus();
         });
    }

    // 화면 초기에 들어오면 검색 자동으로 실행
     $('#btnSearch').click();

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
/*
     if(document.querySelector("#gmskk") != null){
        document.querySelector("#gmskk").addEventListener("change", (e)=>{
            $('#noperNumGmskk').val($('#gmskk').val());
            $.post({
                url: "/api/unlicensedBuilingManagement/bldSearch",
                data: JSON.stringify($("#ledgerFindForm").serializeObject()),
                contentType: 'application/json; charset=utf-8'}).done(e=>{
                    if(e.length > 1){
                        $('#gmhjdcd').empty();
                        $('#gmhjdcd').append('<option value="">전체</option>');
                        for ( var i = 0; i < e.length; i++ ) {
                          $('#gmhjdcd').append('<option value='+e[i].hjdcd+'>'+e[i].hnam+'</option>');
                        }
                    }
                });
        });
     }*/

    function CalendarButtonClickEvent(value){
        myCalendar.changeDateDiv(value);
    }
});

// 주소데이터 받기
  function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn,
     detBdNmList, bdNm, bdKdcd, siNm, sggNm, emdNm, liNm, rn, udrtYn, buldMnnm, buldSlno, mtYn, lnbrMnnm, lnbrSlno, emdNo){
     $('input[name=roadaddr]').val(roadFullAddr);
     $('input[name=jibunaddr]').val(jibunAddr);
     $('input[name=gmskkcd]').val(admCd.substring(0, 5));
     $('input[name=gmbjdcd]').val(admCd.substring(5, 10));
     $('input[name=gmcsah]').val(mtYn);
     $('input[name=gmcbuh]').val(lnbrMnnm);
     $('input[name=gmcji]').val(lnbrSlno);
  }

/*
var checkboxSelection = function (params) {
  // we put checkbox on the name if we are not doing grouping
  return params.columnApi.getRowGroupColumns().length === 0;
};
var headerCheckboxSelection = function (params) {
  // we put checkbox on the name if we are not doing grouping
  return params.columnApi.getRowGroupColumns().length === 0;
};
var autoGroupColumnDef = {
  headerName: 'Group',
  minWidth: 170,
  field: 'athlete',
  valueGetter: (params) => {
    if (params.node.group) {
      return params.node.key;
    } else {
      return params.data[params.colDef.field];
    }
  },
  headerCheckboxSelection: true,
  cellRenderer: 'agGroupCellRenderer',
  cellRendererParams: {
    checkbox: true,
  },
};
*/
const LedgerListGridOptions ={
    columnDefs : [
        { headerName: "No.", field: "rn", cellStyle: {textAlign: "center"}, width: 100 /*checkboxSelection: checkboxSelection,*/},
        { headerName: "대장번호", field: "gmbunho", cellStyle: {textAlign: "center"}, width: 200,
            cellRenderer: function(params){
                // 대장 상세 진입
                let flag = $('input[name=legerFlag]').val();
                let detailUrl = "";

                /*if(flag == "ledger"){
                    detailUrl = `/unlcebldmng/ledgerDtail?gmbunho=`+params.data.gmbunho;
                }else{
                    detailUrl = `/unlcebldmng/newLedgerDtail?gmbunho=`+params.data.gmbunho;
                }
                // 새 탭으로 상세 페이지 열기
                //window.open(detailUrl, '_blank');
                return '<a class="ag-theme-alpine" style="text-decoration: underline" href="'+detailUrl+'">' + params.data.gmbunho + '</a>';*/
                return $(`<a class="ag-theme-alpine" onclick='' style="text-decoration: underline" href="#">${params.data.gmbunho}</a>`)
                                        .click(() => goLedgerInfo(flag,params.data.gmbunho))[0]
            }
        },
        { headerName: "건물주소", cellStyle: {textAlign: "center", width: 100},
          groupId: 'buildAddr', cellStyle: {textAlign: "center"},
          children: [
            { headerName: "도로명주소", field: "newaddr1", type: "addrColumn", cellStyle: {textAlign: "center"}, width:250,
                cellRenderer: function(params){
                    if(params.data.naRoadNm != null && params.data.naRoadNm != 'undefined' && params.data.newaddr1 == null){
                        var naSubBun = "";
                        if(params.data.naSubBun == null){
                            return params.data.naRoadNm +' '+params.data.naMainBun
                        }else{
                            return params.data.naRoadNm +' '+params.data.naMainBun+"-"+params.data.naSubBun
                        }
                    }else if(params.data.newaddr1 != null) {
                        return params.data.newaddr1
                    }else{
                        return '-';
                    }
                }
            },
            { headerName: "지번주소", field: "newaddr2", type: "addrColumn", cellStyle: {textAlign: "center"}, width:250},
            { headerName: "대지/산", field: "gmcsahGb", type: "addrColumn", cellStyle: {textAlign: "center"}, width: 120}
          ]
        },
        { headerName: "건물주", field: "gmjname", cellStyle: {textAlign: "center"}, width: 150,
            cellRenderer: function (params) {
                return params.value !== null ? params.value : '-'
            }
        },
        { headerName: "건물구조", field: "gmgzcd", cellStyle: {textAlign: "center"}, width: 130 },
        { headerName: "용도", field: "gmyd", cellStyle: {textAlign: "center"}, width: 90 },
        { headerName: "건물면적(㎡)", field: "gmmunjuk", cellStyle: {textAlign: "right"}, width: 130,
            valueFormatter: function (params) {
                if( params.value != null){
                    return  params.value.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");
                }else{
                    return;
                }
            }
        },
         { headerName: "토지면적(㎡)", field: "tojimunjuk", cellStyle: {textAlign: "right"}, width: 130,
                    valueFormatter: function (params) {
                        if( params.value != null){
                            return  params.value.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");
                        }else{
                            return;
                        }
                    }
                },
                { headerName: "건축일자", field: "gmgunil", cellStyle: {textAlign: "center"}, width: 130,
                    valueFormatter: function (params) {
                        if(params.value != null){
                            if(params.value.length == 8){
                                return moment(params.value).format('yyyy-MM-DD');
                            }else if(params.value.length == 6 && params.value.indexOf('이전') == -1 ){
                                return params.value.substring(0,4)+ "-"+params.value.substring(4,6);
                            }else{
                                return params.value;
                            }
                        }else{return "-";}
                    }
                }
    ],
    suppressClickEdit: true,
    editType: "fullRow",
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
  LedgerListGridOptions.api.paginationSetPageSize(Number(value));
}

 // setup the grid after the page has finished loading
 document.addEventListener('DOMContentLoaded', () => {
   const LedgerListGrid = document.querySelector('#LedgerListGrid');
   new agGrid.Grid(LedgerListGrid, LedgerListGridOptions);
 });

 function onBtnExportDataAsCsv() {
 LedgerListGridOptions.api.exportDataAsCsv();
}

// 대장 상세
function goLedgerInfo(flag , gmbunho){
        const form = document.createElement("form"); form.id = "listForm";
        const el = document.createElement("input");
        el.type = "hidden";
        el.name =  "gmbunho";
        el.value = gmbunho;

        form.appendChild(el);
        form.appendChild(getCalendarButtonClickedValue());
        document.body.appendChild(form);
        addSearchFormElementToTargetForm(form, $('#ledgerFindForm')[0]);

        if(flag == "ledger"){
            form.action = "/unlcebldmng/ledgerDtail";
            document.getElementById('listForm').querySelector('[name="gmskk"]').value = document.getElementById('gmskk').value;// gmskkTemp
        }else{
            form.action = "/unlcebldmng/newLedgerDtail";
        }
        form.method = 'get'
        form.submit();
}

function getCalendarButtonClickedValue(){
    const buttons = document.querySelectorAll('#calendarRemote1 .btn');
    let selectedIndex = -1; // 선택된 버튼의 인덱스를 저장할 변수
    buttons.forEach((button, index) => {
        if (button.classList.contains('btn-clicked')) {
            selectedIndex = index; // btn-clicked 클래스를 가진 버튼을 찾으면 인덱스를 저장합니다.
            return; // 선택된 버튼을 찾았으므로 반복을 멈춥니다.
        }
    });

    const calendarEl = document.createElement("input");
    calendarEl.type = "hidden";
    calendarEl.name =  "calendarBtn";
    calendarEl.value =  selectedIndex;

    return calendarEl;
}

// 신규대장발급 이동
function goNewLedger(){
    const form = document.createElement("form");
    document.body.appendChild(form);
    addSearchFormElementToTargetForm(form, $('#ledgerFindForm')[0])
    form.action = "/unlcebldmng/ledgerAdd";
    form.method = 'get'
    form.submit();
}
