$(document).ready(function() {
    $('#noticeContents').summernote({
        height: 300,
        minHeight: null,
        maxHeight: null,
        focus: true,
        lang: "ko-KR",
        placeholder: '최대 2048자까지 쓸 수 있습니다'
    });

    // 수정 버튼 클릭 시 수정 페이지로 이동
    $('#modifyBtn').click(function() {
        var noticeSeq = $("input[name='noticeSeq']").val();
        if (noticeSeq) {
            location.href = "/board/notice/update?noticeSeq=" + noticeSeq;
        }
    });

    $('#deleteBtn').click(() => {
        const selectedRows = data.gridOptions.api.getSelectedRows();
        const noticeSeqList = selectedRows.map(row => row.noticeSeq);

        snAlert.confirm('삭제하시겠습니까?').then((res) => {
            if (res.isConfirmed) {
                if (noticeSeqList.length > 0) {
                    const url = "/api/board/notice/deleteList";
                    $.ajax({
                        type: "POST",
                        url: url,
                        data: JSON.stringify(noticeSeqList),
                        contentType: 'application/json'
                    }).done(
                        e => snAlert.success("알림", "삭제 완료").then((result) => {
                            if (result.isConfirmed) {
                                location.href = "/board/notice/list";
                            } else if (result.isDismissed) {
                                snAlert.close();
                            }
                        })
                    );
                }
            }
        });
    });

});

const data = (() => {
    const columnDefs = [
        {
            width: 50,
            minWidth: 50,
            headerCheckboxSelection: true,
            checkboxSelection: true,
            cellStyle: { textAlign: "center" }
        },
        {
            headerName: "No.", field: "cnt", width: 180, minWidth: 120, suppressSizeToFit: true,
            cellRenderer: function (params) {
                const isPinned = params.data.pinYn === "Y";
                return `<span>${params.value}</span>`;
            }
        },
        { headerName: "Seq", field: "noticeSeq", hide: true },
        {
            headerName: "제목", field: "noticeSubject", width: 530, minWidth: 120, suppressSizeToFit: true,
            cellRenderer: function (params) {
                const noticeSeq = params.data.noticeSeq;
                const isPinned = params.data.pinYn === "Y";
                return `<a class="ag-theme-alpine" style="text-decoration: underline" href="/board/notice/detail?noticeSeq=${noticeSeq}">${params.value}</a>`;
            }
        },
        {
            headerName: "작성자", field: "userNm", width: 140, minWidth: 120, suppressSizeToFit: true,
            cellRenderer: function (params) {
                const isPinned = params.data.pinYn === "Y";
                return `<span>${params.value}</span>`;
            }
        },
        {
            headerName: "조회수", field: "readCnt", width: 135, minWidth: 120, suppressSizeToFit: true,
            cellRenderer: function (params) {
                const isPinned = params.data.pinYn === "Y";
                return `<span>${params.value}</span>`;
            }
        },
        {
            headerName: "작성일자", field: "registTs", width: 180, minWidth: 120, suppressSizeToFit: true,
            cellRenderer: function (params) {
                const isPinned = params.data.pinYn === "Y";
                return `<span>${params.value}</span>`;
            }
        }
    ];

    // specify the data
    const rowData = [
        { 선택: "", cnt:"", noticeSubject: "", registId: "", readCnt: "", registTs:""}
    ];

    const gridOptions = {
        columnDefs: columnDefs,
        defaultColDef: {
            sortable: true
        },
        pagination: true,
        paginationPageSize: 10,
        domLayout: 'autoHeight',
        animateRows: true,
        rowData: rowData,
        rowGroupPanelShow: 'always',
        rowSelection: 'multiple',
        pinnedTopRowData: []
    };
    // 고정 참고
    gridOptions.isExternalFilterPresent = () => {return true;}
    gridOptions.doesExternalFilterPass = (node) => {
        return node.data.visible;
    }

    return {
        gridOptions: gridOptions
    }
})();

// 고정 참고
var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
    return new bootstrap.Tooltip(tooltipTriggerEl)
})


document.addEventListener('DOMContentLoaded', () => {
    const gridDiv = document.querySelector('#myGrid');
    const gridOptions = data.gridOptions;
    new agGrid.Grid(gridDiv, gridOptions);
});


// agGrid 행 조절
function onPageSizeChanged() {
    var value = document.getElementById('page-size').value;
    data.gridOptions.api.paginationSetPageSize(Number(value));
}

// 공지사항 고정 게시글
function flexSwitchCheckChecked() {
    var temp = [];
    data.gridOptions.api.forEachNode(node => temp.push(node.data));

    if ($('#flexSwitchCheckChecked').is(":checked")) {
        temp.forEach(item => {
            item.visible = item.pinYn == "N";
        });
        data.gridOptions.api.setPinnedTopRowData(temp.filter(item => item.pinYn == "Y"));
    } else {
        temp.forEach(item => {
            item.visible = true;
        });
        data.gridOptions.api.setPinnedTopRowData([]);
    }
    data.gridOptions.api.onFilterChanged();
}


