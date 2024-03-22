class Violated {
    constructor() {
        this.gridStyleValue = { width: 100, height: 500 };
        this.columnDefs = [
            {headerName: "No.", field: "rn", cellStyle: {textAlign: "center"}, width: 70},
            {headerName: "관리번호", field: 'gmskk', cellStyle: {textAlign: "left"}, width: 100,
                cellRenderer: function(params){
                    let url = '/viobldmng/info?'
                    return `<a style="text-decoration: underline" href="${url}gmskk=${params.data.gmskk}">${params.data.gmskk}</a>`;
                }},
            {headerName: "자치구", field: 'gmskkcd', cellStyle: {textAlign: "center"}, width: 100},
            {headerName: "위반구분", field: 'gmtype', cellStyle: {textAlign: "center"}, width: 100},
            {
                headerName: "건축물주소",
                groupId: 'buildAddress',
                children: [
                    {headerName: "도로명주소", cellStyle: {textAlign: "left"}, field: 'gmnaddr', width: 250},
                    {headerName: "지번주소", cellStyle: {textAlign: "left"}, field: 'gmaddr', width: 250}
                ]
            },
            {headerName: "위반용도", field: 'gmbsub', cellStyle: {textAlign: "center"}, width: 100},
            {headerName: "신규적발", field: 'gmasub', cellStyle: {textAlign: "center"}, width: 100},
            {headerName: "조치완료", field: 'gmhcdate', cellStyle: {textAlign: "center"}, width: 100},
            {headerName: "고발", field: 'gmgdate', cellStyle: {textAlign: "center"}, width: 100},
            {headerName: "고발 조치완료", field: 'gmjdate', cellStyle: {textAlign: "center"}, width: 150},
            {headerName: "행정대집행", field: 'gmhdate', cellStyle: {textAlign: "center"}, width: 100},
            {headerName: "적발일자", field: 'gmdate', cellStyle: {textAlign: "center"}, width: 100}
        ];
        this.defaultColDef = {
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
            minWidth: 50,
        };
        this.gridOptions = {
            columnDefs: this.columnDefs,
            defaultColDef: this.defaultColDef,
            pagination: true,
            paginationPageSize: 10,
            domLayout: 'autoHeight',
            animateRows: true,
            rowData: [],
            onCellClicked(params) {
                if (params.colDef.field === 'gmskk') {
                    const form = document.createElement("form");
                    const gmskkElement = document.createElement("input");
                    form.appendChild(gmskkElement);
                    document.body.appendChild(form);
                    gmskkElement.type = 'text';
                    gmskkElement.name = 'gmskk';
                    gmskkElement.value = params.data.gmskk;
                    form.action = '/viobldmng/info';
                    form.method = 'get';
                    form.submit();
                }
            },
            overlayNoRowsTemplate: `
                <div>
                    <p style="color: #6c757d">조회된 데이터가 없습니다.</p>
                </div>
            `
        };
    }

    name(name) {
        const input = document.querySelector(`input[name=${name}]`);
        if (input) return input;
        else console.error(`Element name:'${name}' not found.`);
    }

    gridStyle(gridDiv) {
        if (gridDiv && gridDiv.style) {
            Object.assign(gridDiv.style, {
                width: `${this.gridStyleValue.width}%`,
                height: `${this.gridStyleValue.height}px`,
            });
        } else {
            console.error("Invalid GridStyle Element.");
        }
    }
    excelAjax(url, fileName) {
        snExcelDownload.excelDownload($.ajax({
            url: url,
            type: 'POST',
            cache: false,
            data: $('#searchForm').serialize(),
            xhrFields: {
                responseType: "blob",
            },
        }), fileName)
    }
}

const departColumnDefs = [
    {headerName: "No.", field: "rn", cellStyle: {textAlign: "center"}, width: 70},
    {headerName: "관리번호", field: 'gmskk', cellStyle: {textAlign: "left"}, width: 100,
        cellRenderer: function(params){
            let url = '/viobldmng/info?'
            return `<a style="text-decoration: underline" href="${url}gmskk=${params.data.gmskk}">${params.data.gmskk}</a>`;
        }},
    {headerName: "위반구분", field: 'gmtype', cellStyle: {textAlign: "center"}, width: 100},
    {
        headerName: "건축물주소",
        groupId: 'buildAddress',
        children: [
            {headerName: "도로명주소", cellStyle: {textAlign: "left"}, field: 'gmnaddr', width: 250},
            {headerName: "지번주소", cellStyle: {textAlign: "left"}, field: 'gmaddr', width: 250}
        ]
    },
    {headerName: "위반용도", field: 'gmbsub', cellStyle: {textAlign: "center"}, width: 100},
    {headerName: "신규적발", field: 'gmasub', cellStyle: {textAlign: "center"}, width: 100},
    {headerName: "조치완료", field: 'gmhcdate', cellStyle: {textAlign: "center"}, width: 100},
    {headerName: "고발", field: 'gmgdate', cellStyle: {textAlign: "center"}, width: 100},
    {headerName: "고발 조치완료", field: 'gmjdate', cellStyle: {textAlign: "center"}, width: 150},
    {headerName: "행정대집행", field: 'gmhdate', cellStyle: {textAlign: "center"}, width: 100},
    {headerName: "적발일자", field: 'gmdate', cellStyle: {textAlign: "center"}, width: 100}
];

const violated = new Violated();
document.addEventListener('DOMContentLoaded', () => {
    const gridDiv = document.querySelector('#myGrid')
    const grid = new agGrid.Grid(gridDiv, violated.gridOptions);
    if(!isAdmin) grid.gridOptions.api.setColumnDefs(departColumnDefs)

    // 캘린더
    const myCalendar = new customCalendar('calendarSelect1', 'calendarRemote1', 'startDate1', 'endDate1');
    myCalendar.setClass('buttonDiv', 'btn-group-sm row ms-4 col-5')
    myCalendar.setClass('dateSpan', 'col-1 mx-1 text-center');

    document.querySelector('button[name=excelDownload]').addEventListener('click', () => {
        violated.excelAjax('/viobldmng/excel', '위반건축물현황')
    });
    if(document.querySelector('button[name=saveBtn]')) document.querySelector('button[name=saveBtn]').addEventListener('click', () => {
        const a = document.createElement('a');
        a.href = "/viobldmng/create"
        a.click()
    })

    $('#listCnt').change(function() {
        grid.gridOptions.api.paginationSetPageSize(Number(this.value));
    })

    $('#searchForm').submit(function () {
        console.log($('#searchForm').serialize());
        $.get('/api/viobldmng/list', $('#searchForm').serialize(), function (data) {
            $('#total > h3').text("Total "+data.length)
            grid.gridOptions.api.setRowData([]);
            grid.gridOptions.api.setRowData(data);
        })
        return false;
    });

    if($('#statusToList').val() === 'true') $('#searchForm').submit();
});