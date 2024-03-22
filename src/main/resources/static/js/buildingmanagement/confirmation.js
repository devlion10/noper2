const rowData = [];
$(document).ready(function(){
    var searchData  = "";
    searchData = JSON.stringify($("#confirmationFindForm").serializeObject());
    const valuedate = () => {
       // 대장번호 selectbox
       if($("select[name=gmdjgb]").val() == '전체') $("select[name:gmdjgb]").val("");
    };

    // 기존무허가 대장관리 상세에서 기존무허가 소유자변경관리로 이동했을 때, 자동 대장번호 기입 로직
    const url = new URL(window.location.href);
    const urlGmskk = url.searchParams.get('gmskk');
    const urlGmdjgb = url.searchParams.get('gmdjgb');
    const urlGmseqco = url.searchParams.get('gmseqco');
    if (url.pathname === '/unlcebldmng/confirmation' && urlGmskk != null && urlGmdjgb != null && urlGmseqco != null) {
        document.getElementById('gmskk').value = urlGmskk
        document.getElementById('gmdjgb').value = urlGmdjgb
        document.getElementById('gmseqco').value = urlGmseqco
        document.getElementById('gmseqco2').value = urlGmseqco
    }


    // 페이지 로드시 검색 수행
    //findList();
    $('#resetBtn').click(() => {
        $("#confirmationFindForm")[0].reset();
        $("#jsiljaCalendarRemote").html("");
        $('#startDate').val("");
        $('#endDate').val("");
        $('#jsilja1').val("");
        $('#jsilja2').val("");
        $("#triljaCalendarRemote").html("");
        $('#trilja1').val("");
        $('#trilja2').val("");
    });
    $('#confirmationAddBtn').click(() => {location.href="/unlcebldmng/confirmationAdd";});
    $('input[name=gmskk]').val($('input[name=gmskkTemp]').val());

    // 그리드 csv 다운로드
    //$('#csvBtn').click(() => {snGrid.exportCsv();});

    // 검색
    $('#searchBtn').click(function(){
        $('input[name=gmskk]').val($('#gmskk').val());
        searchData = JSON.stringify($("#confirmationFindForm").serializeObject());
        findList();
    });

    function findList() {
       valuedate();
       $.post({
           url: "/api/unlicensedBuilingManagement/confirmation",
           data: JSON.stringify($("#confirmationFindForm").serializeObject()),
           contentType: 'application/json; charset=utf-8'}).done(e=>{
               $('#totCnt > h3').text("Total "+e.length);
               confirmListGridOptions.api.setRowData(e)
           });

    }
    // 주소찾기 btn 있을때만 동작
    if(document.querySelector("#jusoBtn") != null){
         document.querySelector("#jusoBtn").addEventListener("click", (e)=>{
             const w = window.open('/common/juso', 'juso', 'width=500px,height=500px');
             w.focus();
         });
    }

     // 엑셀다운로드
     $('#csvBtn').click(() => {
         snExcelDownload.excelDownload($.ajax({
              url: '/unlcebldmng/download/confirmationExcel',
              type: 'post',
              cache: false,
              contentType: 'application/json; charset=utf-8',
              data: searchData,
              xhrFields: {
                 responseType: "blob",
              },
         }), "기존무허가 확인원관리");
     });

    const userName = $("input[name=userName]").val();
    $('#certificationBtn').click(function(e){
        $('input[name=gmskk]').val($('*[name=gmskkTemp]').val());
        var jsonData = JSON.parse(searchData);
        const data = {
         CNFIRM_STTUS: {gmskk: $('input[name=gmskk]').val(), gmdjgb: jsonData.gmdjgb, gmseqco: jsonData.gmseqco, daejang: "", gmjname:jsonData.gmjname, gmskkcd: jsonData.gmskkcd, gmhjdcd: '', gmbjdcd: jsonData.gmbjdcd, gmcsah: jsonData.gmcsah, gmcbuh: jsonData.gmcbuh, gmcji: jsonData.gmcji, jsilja1: jsonData.jsilja1, jsilja2: jsonData.jsilja2, trilja1: jsonData.trilja1, trilja2: jsonData.trilja2, gmseqco2: jsonData.gmseqco2, searchJuso: jsonData.searchJuso, user: userName},
        }
        snReport.open('CNFIRM_STTUS', data.CNFIRM_STTUS)
    });
});


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
const confirmListGridOptions ={
    columnDefs : [
        { headerName: "No.", field: "rn", width:100, cellStyle: {textAlign: "center"}, /*checkboxSelection: checkboxSelection,*/},
        { headerName: "대장번호", field: "gmbunho", width:160,
            cellRenderer: function(params){
                // 대장 상세 진입
                let flag = $('input[name=confirmationFlag]').val();
                let detailUrl = "";
                if(flag == "lsit"){
                    detailUrl = `/unlcebldmng/confirmationDetail?gmbunho=`+params.data.gmbunho+`&conilno=`+params.data.conilno;
                }else{
                    detailUrl = `/unlcebldmng/confirmationDetail?gmbunho=`+params.data.gmbunho;
                }
                // 새 탭으로 상세 페이지 열기
                //window.open(detailUrl, '_blank');
                return '<a class="ag-theme-alpine" style="text-decoration: underline" href="'+detailUrl+'">' + params.data.gmbunho + '</a>';
            }
        },
        { headerName: "건물주소",
          groupId: 'buildAddr',
          children: [
            { headerName: "도로명주소", field: "newaddr1", type: "addrColumn", width:220,
             cellRenderer: function(params){
                 if(params.data.naRoadNm != null && params.data.naRoadNm != 'undefined' && params.data.newaddr1 == null){
                     var naSubBun = "";
                     if(params.data.naSubBun != null){
                          naSubBun = "-"+params.data.naSubBun;
                     }
                     return params.data.naRoadNm +' '+params.data.naMainBun+naSubBun
                 }else if(params.data.newaddr1 != null) {
                     return params.data.newaddr1
                 }else{
                     return '-';
                 }
             }
             },
            { headerName: "지번주소", field: "newaddr2", type: "addrColumn", width:220},
            { headerName: "대지/산", field: "gmcsah", type: "addrColumn", width:100}
          ]
        },
        { headerName: "건물주", field: "gmju", width:100},
        { headerName: "신청인", field: "sinname", width:100 },
        { headerName: "발급번호", field: "balNo", width:110 },
        { headerName: "발급용도", field: "bigo", width:150 },
        { headerName: "접수일자", field: "jsilja", width:130,
            valueFormatter: function (params) {
                return moment(params.value).format('yyyy-MM-DD');
            }
        },
        { headerName: "처리일자", field: "trilja", width:130,
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
        cellDataType: false
    },
    pagination: true,
    paginationPageSize: 10,
    rowData: rowData,
    domLayout: 'autoHeight',
    animateRows: true,
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
   const confirmListGriddDiv = document.querySelector('#confirmListGrid');
   new agGrid.Grid(confirmListGriddDiv, confirmListGridOptions);
 });

 /*
 function onBtnExportDataAsCsv() {
 gridOptions.api.exportDataAsCsv();
 }
 */
 // 리스트 갯수
 function onPageSizeChanged() {
   var value = document.getElementById('page-size').value;
   confirmListGridOptions.api.paginationSetPageSize(Number(value));
 }
 // 주소데이터 받기
  function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn,
     detBdNmList, bdNm, bdKdcd, siNm, sggNm, emdNm, liNm, rn, udrtYn, buldMnnm, buldSlno, mtYn, lnbrMnnm, lnbrSlno, emdNo){
     $('input[name=roadaddr]').val(roadFullAddr);
     $('input[name=jibunaddr]').val(jibunAddr);
     $('input[name=roadaddr]').val(roadFullAddr);
     $('input[name=jibunaddr]').val(jibunAddr);
     $('input[name=gmskkcd]').val(admCd.substring(0, 5));
     $('input[name=gmbjdcd]').val(admCd.substring(5, 10));
     $('input[name=gmcsah]').val(mtYn);
     $('input[name=gmcbuh]').val(lnbrMnnm);
     $('input[name=gmcji]').val(lnbrSlno);
  }


  //달력
/**
 * 캘린더 사용시 호출
 * 필수요소: <select id="calendarSelect">
 *              <option value="all">전체기간</option>
*               <option value="simple">기간선택</option>
 *              <option value="set">기간설정</option>
 *          </select>,
 *          <input type="hidden" id="startDate">,
 *          <input type="hidden" id="endDate">,
 *          <div id="calendarRemote" class="col-sm-4"></div>
 */

var calendar = (function () {
    document.addEventListener('DOMContentLoaded', function () {
        const jsiljaSelect = document.getElementById('jsiljaCalendarSelect');
        const jsiljaRemote = document.getElementById('jsiljaCalendarRemote');
        const triljaSelect = document.getElementById('triljaCalendarSelect');
        const triljaRemote = document.getElementById('triljaCalendarRemote');
        const jsilja1Date = document.getElementById('jsilja1');
        const jsilja2Date = document.getElementById('jsilja2');
        const trilja1Date = document.getElementById('trilja1');
        const trilja2Date = document.getElementById('trilja2');

        function calendarRemoteInit (val) {
            if(val == "jsilja"){
                jsiljaRemote.innerHTML = '';
                jsilja1Date.value = '';
                jsilja2Date.value = '';
            }
            if(val == "trilja"){
                triljaRemote.innerHTML = '';
                trilja1Date.value = '';
                trilja2Date.value = '';
            }
        }

        /**
         * @return{void} simple 옵션의 calenderRemote
         */
        function calendarRemoteToCalendarOptionSimple (val) {
            calendarRemoteInit(val);
            const buttonBox = document.createElement("div");
            const buttonDiv = document.createElement("div");
            const buttonToday = document.createElement("button");
            const buttonWeek = document.createElement("button");
            const buttonMonth = document.createElement("button");
            const comment = document.createElement("p");

            buttonDiv.appendChild(buttonToday);
            buttonDiv.appendChild(buttonWeek);
            buttonDiv.appendChild(buttonMonth);

            buttonBox.className = "col-12 d-flex justify-content-around"
            buttonDiv.className = "btn-group-sm row col-4";

            buttonToday.className = "col-3 ms-1 btn btn-outline-dark";
            buttonWeek.className = "col-3 ms-1 btn btn-outline-dark";
            buttonMonth.className = "col-3 ms-1 btn btn-outline-dark";
            comment.className = "col-8 hstack";

            buttonToday.innerText = "오늘";
            buttonWeek.innerText = "1주일";
            buttonMonth.innerText = "1개월";

            buttonToday.type = "button";
            buttonWeek.type = "button";
            buttonMonth.type = "button";
            if(val == "jsilja"){
                buttonBox.appendChild(buttonDiv);
                buttonBox.appendChild(comment);
                jsiljaRemote.appendChild(buttonBox);
             }
            if(val == "trilja"){
              buttonBox.appendChild(buttonDiv);
              buttonBox.appendChild(comment);
              triljaRemote.appendChild(buttonBox);
            }
            buttonToday.addEventListener('click', function() {
                const today = new Date();
                const todayStr = today.toISOString().split('T')[0];
                comment.innerText = `※ ${todayStr}`;
                var nodeId = buttonToday.parentNode.parentNode.parentNode.getAttribute("id");
                if(nodeId == "jsiljaCalendarRemote"){
                    jsilja1Date.value = moment(todayStr).format('YYYYMMDD');
                    jsilja2Date.value = moment(todayStr).format('YYYYMMDD');
                }

                if(nodeId == "triljaCalendarRemote"){
                    trilja1Date.value = moment(todayStr).format('YYYYMMDD');
                    trilja2Date.value = moment(todayStr).format('YYYYMMDD');
                }
                buttonToday.classList.add('btn-clicked');
                buttonWeek.classList.remove('btn-clicked');
                buttonMonth.classList.remove('btn-clicked');
            })

            buttonWeek.addEventListener('click', function () {
                const today = new Date();
                const todayStr = today.toISOString().split('T')[0];
                const weekAgoDay = new Date(today.setDate(today.getDate() - 7));
                const weekAgoDayStr = weekAgoDay.toISOString().split('T')[0];
                comment.innerText = `※ ${weekAgoDayStr} - ${todayStr}`;
                var nodeId = buttonToday.parentNode.parentNode.parentNode.getAttribute("id");
                if(nodeId == "jsiljaCalendarRemote"){
                    jsilja1Date.value = moment(weekAgoDayStr).format('YYYYMMDD');
                    jsilja2Date.value = moment(todayStr).format('YYYYMMDD');
                }
                if(nodeId == "triljaCalendarRemote"){
                    trilja1Date.value = moment(weekAgoDayStr).format('YYYYMMDD');
                    trilja2Date.value = moment(todayStr).format('YYYYMMDD');
                }
                buttonToday.classList.remove('btn-clicked');
                buttonWeek.classList.add('btn-clicked');
                buttonMonth.classList.remove('btn-clicked');
            })

            buttonMonth.addEventListener('click', function () {
                const today = new Date();
                const todayStr = today.toISOString().split('T')[0];
                const monthAgoDay = new Date(today.setMonth(today.getMonth() - 1));
                const monthAgoDayStr = monthAgoDay.toISOString().split('T')[0];
                comment.innerText = `※ ${monthAgoDayStr} - ${todayStr}`;
                var nodeId = buttonToday.parentNode.parentNode.getAttribute("id");
                if(nodeId == "jsiljaCalendarRemote"){
                    jsilja1Date.value = moment(monthAgoDayStr).format('YYYYMMDD');
                    jsilja2Date.value = moment(todayStr).format('YYYYMMDD');
                }
                if(nodeId == "triljaCalendarRemote"){
                    trilja1Date.value = moment(monthAgoDayStr).format('YYYYMMDD');
                    trilja2Date.value = moment(todayStr).format('YYYYMMDD');
                }
                buttonToday.classList.remove('btn-clicked');
                buttonWeek.classList.remove('btn-clicked');
                buttonMonth.classList.add('btn-clicked');
            })

            buttonToday.click();
        }

        function calendarRemoteToCalendarOptionSet (val) {
            calendarRemoteInit(val);
            const dateDiv = document.createElement("div");
            const dateSpan = document.createElement("span");
            dateDiv.className = "row";
            const today = new Date();
            const todayStr = today.toISOString().split('T')[0];
            const dayAgoDay = new Date(today.setDate(today.getMonth() - 1));
            const dayAgoDayStr = dayAgoDay.toISOString().split('T')[0];
            dateSpan.innerText = '~';

            if(val == "jsilja"){
                 const dateJsilja1 = document.createElement("input");
                 const dateJsilja2 = document.createElement("input");

                dateJsilja1.onkeydown = function() {return false;};
                dateJsilja2.onkeydown = function() {return false;};

                 dateDiv.appendChild(dateJsilja1);
                 dateDiv.appendChild(dateSpan);
                 dateDiv.appendChild(dateJsilja2);
                jsiljaRemote.appendChild(dateDiv);
                dateJsilja1.id = 'dateJsilja1';
                dateJsilja2.id = 'dateJsilja2';
                dateJsilja1.className = "col-2";
                dateSpan.className = "col-1 ms-2 text-center";
                dateJsilja2.className = "col-2";
                dateJsilja1.type = 'date';
                dateJsilja2.type = 'date';
                dateJsilja1.value = dayAgoDayStr;
                dateJsilja2.value = todayStr;
                jsilja1Date.value = moment(dayAgoDayStr).format('YYYYMMDD');
                jsilja2Date.value = moment(todayStr).format('YYYYMMDD');
                dateJsilja1.addEventListener('input', function () {
                    jsilja1Date.value = moment(dateJsilja1.value).format('YYYYMMDD');
                });
                dateJsilja2.addEventListener('input', function () {
                    jsilja2Date.value = moment(dateJsilja2.value).format('YYYYMMDD');
                });
            }
            if(val == "trilja"){
                const dateTrilja1 = document.createElement("input");
                const dateTrilja2 = document.createElement("input");

                dateTrilja1.onkeydown = function() {return false;};
                dateTrilja2.onkeydown = function() {return false;};

                dateDiv.appendChild(dateTrilja1);
                dateDiv.appendChild(dateSpan);
                dateDiv.appendChild(dateTrilja2);
                triljaRemote.appendChild(dateDiv);
                dateTrilja1.id = 'dateTrilja1';
                dateTrilja2.id = 'dateTrilja2';
                dateTrilja1.className = "col-2";
                dateSpan.className = "col-1 ms-2 text-center";
                dateTrilja2.className = "col-2";
                dateTrilja1.type = 'date';
                dateTrilja2.type = 'date';
                dateTrilja1.value = dayAgoDayStr;
                dateTrilja2.value = todayStr;
                trilja1Date.value = moment(dayAgoDayStr).format('YYYYMMDD');
                trilja2Date.value = moment(todayStr).format('YYYYMMDD');
                dateTrilja1.addEventListener('input', function () {
                     trilja1Date.value = moment(dateTrilja1.value).format('YYYYMMDD');
                });
                dateTrilja2.addEventListener('input', function () {
                     trilja2Date.value = moment(dateTrilja2.value).format('YYYYMMDD');
                });
            }
        }

        /**
         * 캘린더 옵션 아이디 설정
         * @param{string} optionId option의 아이디 (전체: all, 기간선택: simple, 기간 설정: set)
         * @return void
         */
        function setCalendar (optionId, Str) {
            switch (optionId) {
                case "all":
                    calendarRemoteInit(Str);
                    break;
                case "simple":
                    calendarRemoteToCalendarOptionSimple(Str);
                    break;
                case "set":
                    calendarRemoteToCalendarOptionSet(Str);
                    break;
            }

        }

        if(jsiljaSelect != null && jsiljaRemote != null && jsilja1Date != null && jsilja2Date != null) {
            jsiljaSelect.addEventListener('change', function () { setCalendar(jsiljaSelect.value, "jsilja") });
            setCalendar(jsiljaSelect.value, "jsilja");
            if(jsilja1Date.getAttribute('jsilja1Date') || document.getElementById('dateJsilja1') != null) document.getElementById('dateJsilja1').value = dateJsilja1.getAttribute('jsilja1Date');
            if(jsilja2Date.getAttribute('jsilja2Date') || document.getElementById('dateJsilja2') != null) document.getElementById('dateJsilja2').value = dateJsilja2.getAttribute('jsilja2Date');
        }

        if(triljaSelect != null && triljaRemote != null && trilja1Date != null && trilja2Date != null) {
             triljaSelect.addEventListener('change', function () { setCalendar(triljaSelect.value, "trilja") });
             setCalendar(triljaSelect.value, "trilja");
             if(trilja1Date.getAttribute('trilja1Date') || document.getElementById('dateTrilja1') != null) document.getElementById('dateTrilja1').value = dateTrilja1.getAttribute('trilja1Date');
             if(trilja2Date.getAttribute('trilja2Date') || document.getElementById('dateTrilja2') != null) document.getElementById('dateTrilja2').value = dateTrilja2.getAttribute('trilja2Date');
        }
    })

    return {

    }
})();








