class BtnCellRenderer
{
    init (params) {
        this.params = params;

        this.eGui = document.createElement('a');
        this.eGui.innerText = params.colDef.headerName;
        this.eGui.href = '#';
        this.eGui.style.textDecoration = 'underline';

        this.eGui.btnClickedHandler = this.btnClickedHandler.bind(this);
        this.eGui.addEventListener('click', (e)=>{
            this.btnClickedHandler(e);
        });
    }

    getGui() {
        return this.eGui;
    }

    destory() {
        this.eGui.removeEventListener('click', this.btnClickedHandler);
    }

    btnClickedHandler(event) {
        this.params.clicked(this.params.data);
    }
}

const permitList = (function () {
    let modalGrid1;
    let modalGrid2;

    const copyPermit = (perId) => {

    }

    const gridOptions = {
        columnDefs: [
            { headerName: "권한구분", field: 'perGb' },
            { headerName: "권한명", field: 'perName', cellRenderer: (params) => {
                    return $('<a style="text-decoration: underline" href="#">' + params.data.perName + '</a>').click(() => {
                        const form = document.createElement("form");
                        const perIdElement = document.createElement("input");
                        form.appendChild(perIdElement);
                        document.body.appendChild(form);
                        perIdElement.type = 'text';
                        perIdElement.name = 'perId';
                        perIdElement.value = params.data.perId;
                        form.action = '/admin/permit/info';
                        form.method = 'post';
                        form.submit();
                    })[0];
                } },
            { headerName: "등록자", field: 'registId' },
            { headerName: "등록일자", field: 'registTs' },
            { headerName: "수정자", field: 'updateId' },
            { headerName: "수정일자", field: 'updateTs' },
            { headerName: "권한복사", field: 'perCopy', cellRenderer: (params) => {
                    return $('<a style="text-decoration: underline" href="#">권한복사</a>').click(() => {
                        snAlert.confirm('선택한 권한을 복사하여, \n신규 권한으로 등록 할 수 있습니다. \n신규 권한을 위해 권한 수정 \n화면으로 이동 하시겠습니까?').then((confirmed) => {
                            if (confirmed.isConfirmed) {
                                const form = document.createElement("form");
                                const userIdElement = document.createElement("input");
                                form.appendChild(userIdElement);
                                document.body.appendChild(form);
                                userIdElement.type = 'text';
                                userIdElement.name = 'perId';
                                userIdElement.value = params.data.perId;
                                form.action = '/admin/permit/copy';
                                form.method = 'post';
                                form.submit();
                            }
                        })
                    })[0];
                }},
            //{ headerName: "권한부여", field: 'perGive', cellRenderer: BtnCellRenderer, cellRendererParams: { clicked: function (data) { $('#perId').val(data.perId); $('#modalSearchForm').submit(); $('#exampleModal').modal('show'); }}},
        ],
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
        getRowId: (params) => { if(params.data.perId != null) { return params.data.perId } else { return '' }},

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
    };

    const modalGridOptions1 = {
        columnDefs: [
            { headerName: "이름", field: 'userName', headerCheckboxSelection: true, checkboxSelection: true },
            { headerName: "자치구", field: 'skkCd' },
            { headerName: "부서명", field: 'deptCd' },
            { headerName: "전화번호", field: 'telNo' },
        ],
        headerClass: ["center", "justCenter"], cellClass: ["center", "justCenter"],
        // width: 160,
        sortable: true,
        editable: false,
        floatingFilter: false,
        resizable: true,
        cellDataType: false,
        editable: false,
        enableRowGroup: false,
        enablePivot: false,
        enableValue: false,
        minWidth: 100,
        pagination: true,
        rowGroupPanelShow: 'always',
        rowSelection: 'multiple',
        //autoGroupColumnDef: autoGroupColumnDef,
        /*
        columnTypes: {
            addrColumn: {width: 150, filter:false},
        },
        */
        rowData: [],
        getRowId: (params) => { if(params.data.userId != null) { return params.data.userId } else { return '' }},

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
    };

    const modalGridOptions2 = {
        columnDefs: [
            { headerName: "이름", field: 'userName', headerCheckboxSelection: true, checkboxSelection: true },
            { headerName: "자치구", field: 'skkCd' },
            { headerName: "부서명", field: 'deptCd' },
            { headerName: "전화번호", field: 'telNo' },
        ],
        headerClass: ["center", "justCenter"], cellClass: ["center", "justCenter"],
        // width: 160,
        sortable: true,
        editable: false,
        floatingFilter: false,
        resizable: true,
        cellDataType: false,
        editable: false,
        enableRowGroup: false,
        enablePivot: false,
        enableValue: false,
        minWidth: 100,
        pagination: true,
        rowGroupPanelShow: 'always',
        rowSelection: 'multiple',
        //autoGroupColumnDef: autoGroupColumnDef,
        /*
        columnTypes: {
            addrColumn: {width: 150, filter:false},
        },
        */
        rowData: [],
        getRowId: (params) => { if(params.data.userId != null) { return params.data.userId } else { return '' }},

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
    };

    document.addEventListener('DOMContentLoaded', () => {
        const gridDiv = document.querySelector('#permitListGrid');
        const modalGridDiv1 = document.querySelector('#modalGrid1');
        const modalGridDiv2 = document.querySelector('#modalGrid2');
        const grid = new agGrid.Grid(gridDiv, gridOptions);
        new agGrid.Grid(modalGridDiv1, modalGridOptions1);
        new agGrid.Grid(modalGridDiv2, modalGridOptions2);
        const myCalendar = new customCalendar('calendarSelect1', 'calendarRemote1', 'startDate1', 'endDate1');
        myCalendar.setClass('buttonDiv', 'btn-group-sm row ms-4 col-5');
        myCalendar.setClass('dateSpan', 'col-1 text-center');


        $('#listCnt').change(function() {
            grid.gridOptions.api.paginationSetPageSize(Number(this.value));
        })
    });

    $(document).ready(function () {
        $('#searchForm').submit(function () {
            $.get('/api/user/permit/select/list', $('#searchForm').serialize(), function (data) {
                let json = [];
                $('#total > h3').text("Total "+data.length)
                for(let d of data) { json.push({...d, 'perGb': d.perId.substring(0, 2) === 'AD' ? '관리자' : '사용자'}) }
                gridOptions.api.setRowData([]);
                gridOptions.api.setRowData(json);
            })
            return false;
        })

        $('#searchForm').submit();

        $('#modalSearchForm').submit(function () {
            $.get('/api/user/permit/user/select/list', $('#modalSearchForm').serialize(), function (data) {
                const gridData1 = [];
                const gridData2 = [];
                for(let d of data) { if(d.perId !== $('#perId').val()) { gridData1.push(d) } else { gridData2.push(d) }}
                modalGridOptions1.api.setRowData([]);
                modalGridOptions1.api.setRowData(gridData1);
                modalGridOptions2.api.setRowData([]);
                modalGridOptions2.api.setRowData(gridData2);
            })
            return false;
        })

        $('#modalUpBtn').click(function () {
            const tArr = []
            modalGridOptions1.api.getRenderedNodes().forEach(e => tArr.push(e.data));
            const dArr = [];
            modalGridOptions2.api.getRenderedNodes().forEach(e => dArr.push(e.data));
            const sArr = modalGridOptions2.api.getSelectedRows();
            const diff = dArr.filter(d => !sArr.includes(d))
            modalGridOptions1.api.setRowData([]);
            modalGridOptions1.api.setRowData([...tArr, ...sArr]);
            modalGridOptions2.api.setRowData([]);
            modalGridOptions2.api.setRowData(diff);
        })

        $('#modalDownBtn').click(function () {
            const tArr = [];
            modalGridOptions2.api.getRenderedNodes().forEach(e => tArr.push(e.data));
            const dArr = [];
            modalGridOptions1.api.getRenderedNodes().forEach(e => dArr.push(e.data));
            const sArr = modalGridOptions1.api.getSelectedRows();
            const diff = dArr.filter(d => !sArr.includes(d));
            modalGridOptions1.api.setRowData([]);
            modalGridOptions1.api.setRowData(diff);
            modalGridOptions2.api.setRowData([]);
            modalGridOptions2.api.setRowData([...tArr, ...sArr]);
        })

        $('#modalCancel').click(function () {
            snAlert.confirm('취소시 권한 부여가 저장되지 않습니다. 취소 하시겠습니까?').then((data) => { if(data.isConfirmed) $('#exampleModal').modal('toggle') })
        })

        $('#modalSave').click(function () {
            const userId = [];
            modalGridOptions2.api.getRenderedNodes().forEach(e => userId.push(e.data.userId));
            $.post('/api/user/permit/user/update', {
                'perId': $('#perId').val(),
                'userId': userId
            }, function () {
                snAlert.alert('권한부여가 완료되었습니다.').then(() => {$('#exampleModal').modal('toggle')})
            })
        })

        $('#skkCd').change(function () {
            const bdjElement = $('#bjdCd');
            $.ajax({
                type: "GET",
                url: "/api/code/bjdong/list",
                data: `skkCd=${$(this).val()}`,
                dataType: "json",
                success: function (data) {
                    let options = "<option value='' selected>동 선택</option>";
                    for(const bjdInfo of data) {
                        options += `<option value="${bjdInfo.key}">${bjdInfo.dpname}</option>`
                    }
                    bdjElement.html(options);
                },
            })
        })

        $('#crtPerBtn').click(function () {
            location.href = '/admin/permit/add';
        })
    });

    return {

    };
})();