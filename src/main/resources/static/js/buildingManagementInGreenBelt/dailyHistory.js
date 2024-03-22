const rowData = [];
$(document).ready(function() {
    var searchData = "";

    // 검색 버튼 클릭 시 searchData 변수에 데이터 저장
    $('#searchBtn').on('click', function() {
        searchData = JSON.stringify($("#historyFindForm").serializeObject());
        performSearch();
    });

    // 엑셀다운로드
    $('#csvBtn').click(() => {
        const serialData = $("#historyFindForm").serializeObject();
        searchData = JSON.stringify({...serialData, skkcdSearch: $('#skkcdSearch').val(), workidSearch: $('#workidSearch').val()})
        snExcelDownload.excelDownload($.ajax({
            url: '/bldmngingb/download/dailyHistoryExcel',
            type: 'post',
            cache: false,
            contentType: 'application/json; charset=utf-8',
            data: searchData,
            xhrFields: {
                responseType: "blob",
            },
        }), "개발제한구역내일일처리내역");
    });

    // Enter key search
    $('#searchTxt').on('keydown', function(event) {
        if (event.key === 'Enter') {
            event.preventDefault();
            $('#searchBtn').click();
        }
    });

    // 새로고침 버튼 클릭 이벤트 핸들러
    $('#refreshBtn').click(function() {
        $("#historyFindForm")[0].reset();
    });

    function performSearch() {
        var param = {
            skkcdSearch: $('#skkcdSearch').val(), // 기관명 검색 조건
            workidSearch: $('#workidSearch').val(), // 업무구분 검색 조건
            searchTxt: $('#searchTxt').val(), // 작업자 검색어
            stDate: $('#startDate').val(), // 시작 날짜
            endDate: $('#endDate').val() // 종료 날짜
        };

        // 검색 기준에 따라 표시된 행을 필터링합니다.
        gridOptions.api.setQuickFilter(param.searchTxt);

        // 필터링 후 표시되는 총 행 수를 검색합니다.
        var visibleRowCount = gridOptions.api.getDisplayedRowCount();
        $('#totCnt > h3').text("Total "+visibleRowCount);

        $.ajax({
            url: "/api/bldmngingb/dailyHistory/search",
            type: "post",
            data: JSON.stringify(param),
            contentType: 'application/json',
            dataType: 'json',
            success: function (result) {
                $('#totCnt > h3').text("Total "+result.findHistoryListCount);
                gridOptions.api.setRowData(result.findHistoryList);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error('Error:', textStatus, errorThrown);
            }
        });
    }

    // 대장출력 버튼 클릭 이벤트
    document.getElementById('printBtn').addEventListener('click', (e) => {
        const userName = e.target.getAttribute('data-user')
        searchData = JSON.stringify($("#historyFindForm").serializeObject());
        const skkcdValue = ($('#skkcdSearch').val() !== null) ? $('#skkcdSearch').val() : "";

        let jsonData = JSON.parse(searchData);
        let data = {
                gmskk: skkcdValue
                , username: jsonData.searchTxt
                , crdate1: jsonData.stDate
                , crdate2: jsonData.endDate
                , workid: $('#workidSearch option:selected').val()
                , user: userName
            };

        snReport.open('DAIL_PROCESS_RESTRICT', data);
    })


});

/*const historyFindForm = $("#historyFindForm").serializeArray();
let param = {};
historyFindForm.map(function(data,index){
    param[data.name] = data.value;
});*/

const gridOptions = {
    columnDefs: [
        { headerName: "No.", field: "historySeq", cellStyle: { textAlign: "center" }, width: 120 },
        { headerName: "업무구분", field: "workName" },
        { headerName: "workid", field: "workId", hide: true },
        { headerName: "작업구분", field: "crgb", width: 120 },
        { headerName: "작업내용", field: "etc", width: 600 },
        {
            headerName: "작업시각",
            field: "crdate",
            valueFormatter: function (params) {
                // crdate 값을 "YYYYMMDDHHmmss" 형태로 파싱
                const dateString = params.value;
                const year = dateString.substring(0, 4);
                const month = dateString.substring(4, 6);
                const day = dateString.substring(6, 8);
                const hour = dateString.substring(8, 10);
                const minute = dateString.substring(10, 12);
                const second = dateString.substring(12, 14);

                // 날짜 및 시간을 Date 객체로 생성
                const dateObj = new Date(year, month - 1, day, hour, minute, second);

                if (!isNaN(dateObj.getTime())) {
                    // 유효한 Date 객체인 경우
                    const formattedDate = `${year}.${month}.${day} ${hour}:${minute}:${second}`;
                    return formattedDate;
                }
                // Date 객체가 유효하지 않은 경우 또는 파싱에 실패한 경우
                return params.value; // 원래 값 그대로 반환
            }
        },
        { headerName: "작업자", field: "bscdUsername" }
    ],
    defaultColDef: {
        headerClass: ["center", "justCenter"],
        cellClass: ["center", "justCenter"],
        sortable: true,
        editable: false,
        floatingFilter: false,
        resizable: true,
        cellDataType: false,
        enableRowGroup: false,
        enablePivot: false,
        enableValue: false,
        minWidth: 100
    },
    pagination: true,
    paginationPageSize: 10,
    rowData: rowData,
    onGridReady: function (params) {
        params.api.sizeColumnsToFit();
    },
    domLayout: 'autoHeight',
    animateRows: true,
    rowGroupPanelShow: 'always',
    rowSelection: 'multiple',
    overlayNoRowsTemplate: `
        <div>
            <p style="color: #6c757d">조회된 데이터가 없습니다.</p>
        </div>
    `
};

// setup the grid after the page has finished loading
document.addEventListener('DOMContentLoaded', () => {
    const gridDiv = document.querySelector('#myGrid');
    new agGrid.Grid(gridDiv, gridOptions);
});


var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
    return new bootstrap.Tooltip(tooltipTriggerEl);
});


function onPageSizeChanged() {
    var value = document.getElementById('page-size').value;
    gridOptions.api.paginationSetPageSize(Number(value));
}