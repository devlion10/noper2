// specify the data
const rowData = [];
let grid;

// let the grid know which columns and what data to use
const qnaGridOptions = {
    columnDefs : [
        {
            width: 50,
            minWidth: 50,
            headerCheckboxSelection: true, // 헤더 컬럼에 체크 박스 기능 추가(2023-01-17, ANDUM)
            checkboxSelection: true, // Row 데이터에 체크 박스 추가(2023-01-17, ANDUM)
            cellStyle: {textAlign: "center"} // 여기
        },
        { headerName:"No.", field: "cnt", width: 60, minWidth:60, suppressSizeToFit: true, cellStyle: {textAlign: "center"}},
        { headerName:"qaSeq", field: "qaSeq", hide:true},
        { headerName:"문의유형", field: "qaType", width: 120, minWidth:120, suppressSizeToFit: true, cellStyle: {textAlign: "center"}},
        { headerName:"제목", field: "qaSubject" , suppressMenu: true, flex: 1,
            cellRenderer: (p) => {
                return $(`<a onclick='' style="text-decoration: underline" href="#">${p.data.qaSubject}</a>`)
                    .click(() => goDetail(p.data.qaSeq))[0];
            }
        },
        { headerName:"작성자", field: "userNm", width: 150, minWidth:120, suppressSizeToFit: true, cellStyle: {textAlign: "center"}},
        { headerName:"작성자ID", field: "registId", hide:true},
        { headerName:"작성일자", field: "registTs", width: 160, minWidth:120, suppressSizeToFit: true, cellStyle: {textAlign: "center"}},
        { headerName:"조회수", field: "readCnt", width: 100, minWidth:100, suppressSizeToFit: true, cellStyle: {textAlign: "center"}},
        { headerName:"답변상태", field: "qaFlag", width: 120, minWidth:120, suppressSizeToFit: true, cellStyle: {textAlign: "center"}}
    ],
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
        minWidth: 100
    },

    rowGroupPanelShow: 'always',
    rowSelection: 'multiple',
    pagination: true,
    paginationPageSize: 10,
    domLayout: 'autoHeight',
    animateRows: true,
    rowData: rowData,
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

qnaGridOptions.isExternalFilterPresent = () => {return true;}
qnaGridOptions.doesExternalFilterPass = (node) => {
    return node.data.visible;
}

// agGrid 행 조절
function onPageSizeChanged() {
    var value = document.getElementById('page-size').value;
    qnaGridOptions.api.paginationSetPageSize(Number(value));
}

// setup the grid after the page has finished loading
document.addEventListener('DOMContentLoaded', () => {
    const gridDiv = document.querySelector('#myGrid');
    grid = new agGrid.Grid(gridDiv, qnaGridOptions);
});

const goDetail = (qaSeq) => {
    // 클릭한 행의 noticeSeq 값을 사용하여 URL 생성
    const detailUrl = `/board/questionAndAnswer/detail?qaSeq=${qaSeq}`;
    // 새 탭으로 상세 페이지 열기
    // window.open(detailUrl, '_blank');
    location.href = detailUrl;
}

//화면 로드시 조회화면 데이터 출력.
$(document).ready(function () {
    const myCalendar = new customCalendar(document.getElementById('calendarSelect1'),
                                                             document.getElementById('calendarRemote1'),
                                                             document.getElementById('startDate1'),
                                                             document.getElementById('endDate1'));
    myCalendar.setClass('buttonDiv', 'btn-group-sm row ms-4 col-5')
    myCalendar.setClass('dateSpan', 'col-1 mx-2 text-center')
});


//새로고침
$("#btnReload").click(() => {
    calendarRemoteInit();
    document.getElementById("qnaFindForm").reset();
});

// 삭제
$("#deleteBtn").click(() => {
    const checkedRows = qnaGridOptions.api.getSelectedRows()

    if (!checkedRows.length) {
        snAlert.alert("삭제할 데이터를 선택해주십시오.");
        return
    }

    const deleteParams = checkedRows.map((item) => {
        return {qaSeq: item.qaSeq}
    });

    let url = "/api/questionAndAnswer/deleteList";
    let type = "post";
    let json = JSON.stringify(deleteParams);
    let contentType = "application/json";

    snAlert.confirm("삭제하시겠습니까?").then((resultChk) => {
        if (resultChk.isConfirmed) {
            ajaxCall(url, type, json, contentType, function (result) {
                if (result === deleteParams.length) {
                    snAlert.alert("삭제 되었습니다.")
                    search();
                }
            });
        }
    });
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
};

function search(callback) {
    const qnaFindForm = $("#qnaFindForm").serializeObject();

    let url = "/api/questionAndAnswer/search";
    let type = "post";
    let json = JSON.stringify(qnaFindForm);
    let contentType = "application/json";
    ajaxCall(url, type, json, contentType, function (result) {
        $.each(result.findQaList, function (index, item) {
            if (this.qaFlag === '1') {
                this.qaFlag = "답변대기";
            } else {
                this.qaFlag = "답변완료";
            }
        });
        qnaGridOptions.api.setRowData(result.findQaList);
        $("#totCnt > h3").text("Total " + (result.findQaList ? result.findQaList.length : 0));

        if (typeof callback === 'function') {
            callback(result)
        }

    });

    return false;
}

function flexSwitchCheckChecked() {
    var temp = [];
    grid.gridOptions.api.forEachNode(node => temp.push(node.data));

    if ($('#flexSwitchCheckChecked').is(":checked")) {
        temp.forEach(item => {
            item.visible = item.fixFlag == "N";
        });
        grid.gridOptions.api.setPinnedTopRowData(temp.filter(item => item.fixFlag == "Y"));
    } else {
        temp.forEach(item => {
            item.visible = true;
        });
        grid.gridOptions.api.setPinnedTopRowData([]);
    }
    grid.gridOptions.api.onFilterChanged();
}

