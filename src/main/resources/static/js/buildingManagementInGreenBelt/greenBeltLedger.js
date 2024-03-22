const rowData = [];
$(document).ready(function(){
    var searchData = "";

    $('input[name=admSeq]').val($('input[name=gmskkTmp]').val());

    // 검색 버튼 클릭 시 searchData 변수에 데이터 저장
    $('#searchBtn').click(function() {
        let admSeqSearch = $('input[name=gmskkTmp]').val()+$("#admNum").val();
        $("#admSeqSearch").val(admSeqSearch);
        searchData = JSON.stringify($("#ledgerFindForm").serializeObject());
        performSearch();
    });

    // Enter 키 이벤트 처리
    $('#admSeqSearch, #ownerAllNameSearch, #owner1NameSearch, #jusoSearch, #gmcsahSearch').on('keydown', function(event) {
        if (event.key === 'Enter') {
            event.preventDefault(); // 폼 제출 방지
            // 검색 버튼 클릭과 동일한 동작 수행
            $('#searchBtn').click();
        }
    });

// 엑셀다운로드
    $('#csvBtn').click(() => {
        searchData = JSON.stringify($("#ledgerFindForm").serializeObject());

        snExcelDownload.excelDownload($.ajax({
            url: '/bldmngingb/download/ledgerExcel',
            type: 'post',
            cache: false,
            contentType: 'application/json; charset=utf-8',
            data: searchData,
            xhrFields: {
                responseType: "blob",
            },
        }), "개발제한대장관리");
    });

    function performSearch() {
        var param = {
            admSeqSearch: $('#admSeqSearch').val(), // 관리번호 검색어
            ownerAllNameSearch: $('#ownerAllNameSearch').val(), // 전체소유자 검색어
            owner1NameSearch: $('#owner1NameSearch').val(), // 현소유자 검색어
            jusoSearch: $('#jusoSearch').val(), // 건물주소 검색어
            gmcsahSearch: $('#gmcsahSearch').val() // 대지/산 검색어
            //roadaddrSearch: $('#roadaddrSearch').val(), // 도로명주소 검색어
            //jibunaddrSearch: $('#jibunaddrSearch').val() // 지번주소 검색어
        };

        // 필터링 함수
        function customFilter(data, filterText) {
            return data.includes(filterText);
        }

        // 검색 기준에 따라 표시된 행을 필터링합니다.
        gridOptions.api.setQuickFilter(param.searchTxt, customFilter);

        // 필터링 후 표시되는 총 행 수를 검색합니다.
        var visibleRowCount = gridOptions.api.getDisplayedRowCount();
        $('#totCnt > h3').text("Total "+visibleRowCount);

        $.ajax({
            url: "/api/bldmngingb/ledger/search",
            type: "post",
            data: JSON.stringify(param),
            contentType: 'application/json',
            dataType: 'json',
            success: function (result) {

                $('#totCnt > h3').text("Total "+result.findLedgerListCount);
                gridOptions.api.setRowData(result.findLedgerList);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error('Error:', textStatus, errorThrown);
            }
        });
    }

    // 페이지 로드시 검색 수행
    //performSearch();

    // 초기화 버튼 클릭 이벤트 핸들러
    $('#refreshBtn').click(function() {
        //location.reload();
        $("#ledgerFindForm")[0].reset();
    });

    // 신규 대장 추가 버튼 클릭 이벤트 핸들러
    $('#newLegerAddBtn').click(() => {
        location.href="/bldmngingb/ledgerAdd";
    });


});
const gridOptions ={
    columnDefs : [
        { headerName: "No.", field: "rn", cellStyle: {textAlign: "center"} },
        {
            headerName: "관리번호",
            groupId: 'admSeq',
            children: [
                {
                    headerName: "기관",
                    field: "admSeq",
                    type: "addrColumn",
                    width: 100,
                    cellStyle: {textAlign: "center"},
                    valueGetter: function (params) {
                        return params.data.admSeq.substring(0, 5);
                    },
                    cellRenderer: function (params) {
                        const admSeq = params.data.admSeq;
                        return `<a class="ag-theme-alpine" style="text-decoration: underline" href="/bldmngingb/ledgerDetail?admSeq=${admSeq}">${params.value}</a>`;
                    }
                },
                {
                    headerName: "연번",
                    field: "admSeq",
                    type: "addrColumn",
                    width: 100,
                    cellStyle: {textAlign: "center"},
                    valueGetter: function (params) {
                        return params.data.admSeq.substring(params.data.admSeq.length - 5);
                    },
                    cellRenderer: function (params) {
                        const admSeq = params.data.admSeq;
                        return `<a class="ag-theme-alpine" style="text-decoration: underline" href="/bldmngingb/ledgerDetail?admSeq=${admSeq}">${params.value}</a>`;
                    }
                }
            ]
        },
        { headerName: "기관번호", field: "skkCd", hide: true, cellStyle: {textAlign: "center"} },
        {
            headerName: "건물주소", cellStyle: { textAlign: "center", width: 120 },
            groupId: 'buildAddr', cellStyle: { textAlign: "center" },
            children: [
                {
                    headerName: "도로명주소",
                    field: "buildAddr1",
                    type: "addrColumn",
                    cellStyle: { textAlign: "center" },
                    width: 250
                },
                {
                    headerName: "지번주소",
                    field: "skkName",
                    type: "addrColumn",
                    cellStyle: { textAlign: "center" },
                    width: 250,
                    valueGetter: function (params) {
                        return params.data.buildAddr1 ? params.data.buildAddr2 : params.data.skkName;
                    }
                },
                {
                    headerName: "대지/산",
                    field: "csah",
                    type: "addrColumn",
                    cellStyle: { textAlign: "center" },
                    width: 50
                }
            ]
        },
        { headerName: "건물주", field: "owner1Name", cellStyle: {textAlign: "center"} },
        { headerName: "구조", field: "stCd", cellStyle: {textAlign: "center"} },
        { headerName: "용도", field: "jydCd", cellStyle: {textAlign: "center"} },
        { headerName: "건평", field: "buildMj", cellStyle: {textAlign: "center"} },
        { headerName: "면적(㎡)", field: "groundArea", cellStyle: {textAlign: "center"} },
        { headerName: "전체소유자", field: "preOwnerName", hide: true, cellStyle: {textAlign: "center"} },
        { headerName: "폐쇄", field: "degb", cellStyle: {textAlign: "center"} }
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
        minWidth: 100,
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



 // setup the grid after the page has finished loading
 document.addEventListener('DOMContentLoaded', () => {
   const gridDiv = document.querySelector('#myGrid');
   agGrid = new agGrid.Grid(gridDiv, gridOptions);
 });

// agGrid 행 갯수 조절
function onPageSizeChanged() {
    var value = document.getElementById('page-size').value;
    gridOptions.api.paginationSetPageSize(Number(value));
}
