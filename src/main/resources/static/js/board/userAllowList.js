const userAllowList = (()=>{
    let joinGrid;

    const goUserInfo = (userId) => {
        const form = document.createElement("form");
        const userIdElement = document.createElement("input");
        form.appendChild(userIdElement);
        document.body.appendChild(form);
        userIdElement.type = 'text';
        userIdElement.name = 'userId';
        userIdElement.value = userId;
        form.action = '/board/user/info';
        form.method = 'post';
        form.submit();
    }

    const gridOptions = {
        columnDefs: [
            { headerName: "자치구", field: 'skkCd', checkboxSelection: true },
            { headerName: "부서명", field: 'deptCd' },
            {
                headerName: "신청자명",
                field: 'userName',
                cellRenderer: (p) => {
                    return $(`<a onclick='' style="text-decoration: underline" href="#">${p.data.userName}</a>`)
                        .click(() => goUserInfo(p.data.userId))[0];
                }
            },
            { headerName: "전화번호", field: 'telNo' },
            { headerName: "신청일자", field: 'registTs' },
            { headerName: "승인여부", field: 'joinFlag' },
        ],
        headerClass: ["center", "justCenter"], cellClass: ["center", "justCenter"],
        // width: 160,
        sortable: true,
        floatingFilter: false,
        resizable: true,
        cellDataType: false,
        editable: false,
        enableRowGroup: false,
        enablePivot: false,
        enableValue: false,
        minWidth: 100,
        rowGroupPanelShow: 'always',
        rowSelection: 'multiple',
        pagination: true,
        paginationPageSize: 10,
        domLayout: 'autoHeight',
        animateRows: true,
        //autoGroupColumnDef: autoGroupColumnDef,
        /*
        columnTypes: {
            addrColumn: {width: 150, filter:false},
        },
        */
        rowData: [],
        getRowId: (params) => { if(params.data.userId != null) { return params.data.userId } else { return '' }},
        // onCellClicked: (params) => { if(params.colDef.field === 'userName') goUserInfo(params.data.userId) },

        onGridReady: function (event) {
            // 행 넓이 자동 조절
            event.api.sizeColumnsToFit();
        },
        autoGroupColumnDef: {
            headerCheckboxSelection: true,
            field: 'athlete',
            flex: 1,
            minWidth: 240,
            cellRendererParams: {
                checkbox: true,
            },
        },
        suppressRowClickSelection: true,
        suppressAggFuncInHeader: true,
        overlayNoRowsTemplate: `
                <div>
                    <p style="color: #6c757d">조회된 데이터가 없습니다.</p>
                </div>
            `
    };

    document.addEventListener('DOMContentLoaded', () => {
        const gridDiv = document.querySelector('#myGrid');
        joinGrid = new agGrid.Grid(gridDiv, gridOptions);
        const myCalendar = new customCalendar(document.getElementById('calendarSelect1'), document.getElementById('calendarRemote1'), document.getElementById('startDate1'), document.getElementById('endDate1'))
        myCalendar.setClass('buttonDiv', 'btn-group-sm row col-6');
        myCalendar.setClass('dateStartView', 'col-4');
        myCalendar.setClass('dateEndView', 'col-4');
        myCalendar.setClass('comment', 'col-6 text-start');
        myCalendar.setClass('dateSpan', 'col-1 mx-2 text-center');
    });

    document.getElementById('searchForm').onsubmit = () => { return setRowData() }
    document.getElementById('joinListDeleteBtn').addEventListener('click',  () => {
        let userIdData = '';
        joinGrid.gridOptions.api.getSelectedRows().forEach(user => userIdData += `userId=${user.userId}&`);
        if(userIdData !== '') {
            userIdData = userIdData.substring(0, userIdData.length - 1);
            snAlert.confirm('삭제하시겠습니까?').then((data) => {
                if(data.isConfirmed) {
                    $.ajax({
                        type: "POST",
                        url: "/api/user/join/delete",
                        data: userIdData,
                        success: function () {
                            snAlert.alert('삭제되었습니다.').then(() => {location.href = "/board/user/list"});

                        },
                    });
                }
            })
        }
    });
    $('#listCnt').change(function() {
        joinGrid.gridOptions.api.paginationSetPageSize(Number(this.value));
    })
    function setRowData() {
        $.get('/api/user/join/select/list', $('#searchForm').serializeObject(), function (data) {
            $('#total > h3').text("Total "+data.length)
            gridOptions.api.setRowData([]);
            gridOptions.api.setRowData(data);
        })
        return false;
    }

    setRowData();

    return {
        goUserInfo: goUserInfo,
    }
})();