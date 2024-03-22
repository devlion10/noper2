class ViolatedStatus {
    constructor() {
        this.gridStyleValue = { width: 100, height: 500 };
        this.columnDefs = [
            {
                headerName: "위반내역",
                field: "gmtype",
                rowSpan: function (params) {
                    let gmtype = params.data?.gmtype;
                    if(gmtype === '기타 위반건축물' || gmtype === '무단 용도변경 건축물' || gmtype === '위법시공 건축물' || gmtype === '무허가 건축물') {
                        return params.data?.countRow;
                    } else if(gmtype === '계') {
                        return 1;
                    } else {
                        return 0;
                    }
                },
                colSpan: function (params) {
                    if(params.data.gmtype === '계') return 2;
                    else return 1;
                },
                cellStyle: {
                    textAlign: 'center',
                    alignItem: 'center',
                    display: 'flex',
                    justifyContent: 'center',
                    alignItems: 'center'
                },
                cellClassRules: {
                    'bg-white border-light border-1': 'value !== undefined',
                    'fw-bold text-danger': 'value === "계"'
                },
                width: 150
            },
            {headerName: "위반용도", field: 'gmsub', cellStyle: {textAlign: "center"}, width: 100, cellClassRules: {'fw-bold': (params) => { return params.data.gmtype === '계'}}},
            {headerName: "신규적발", field: 'gmNCnt', cellStyle: {textAlign: "center"}, width: 60, cellClassRules: {'fw-bold': (params) => { return params.data.gmtype === '계'}}},
            {headerName: "조치완료", field: 'gmECnt', cellStyle: {textAlign: "center"}, width: 60, cellClassRules: {'fw-bold': (params) => { return params.data.gmtype === '계'}}},
            {headerName: "부과건수", field: 'impoCnt', cellStyle: {textAlign: "center"}, width: 60, cellClassRules: {'fw-bold': (params) => { return params.data.gmtype === '계'}}},
            {headerName: "부과금액(천원)", field: 'impoAmt', cellStyle: {textAlign: "right"}, width: 130, cellClassRules: {'fw-bold': (params) => { return params.data.gmtype === '계'}}},
            {headerName: "징수건수", field: 'collCnt', cellStyle: {textAlign: "center"}, width: 60, cellClassRules: {'fw-bold': (params) => { return params.data.gmtype === '계'}}},
            {headerName: "징수금액(천원)", field: 'collAmt', cellStyle: {textAlign: "right"}, width: 130, cellClassRules: {'fw-bold': (params) => { return params.data.gmtype === '계'}}},
            {headerName: "미납금액(천원)", field: 'nimpoAmt', cellStyle: {textAlign: "right"}, width: 130, cellClassRules: {'fw-bold': (params) => { return params.data.gmtype === '계'}}},
            {headerName: "고발건수", field: 'gmgNCnt', cellStyle: {textAlign: "center"}, width: 60, cellClassRules: {'fw-bold': (params) => { return params.data.gmtype === '계'}}},
            {headerName: "고발에 의한 조치완료", field: 'gmgECnt', cellStyle: {textAlign: "center"}, width: 100, cellClassRules: {'fw-bold': (params) => { return params.data.gmtype === '계'}}},
            {headerName: "행정대집행 건수", field: 'gmhCnt', cellStyle: {textAlign: "center"}, width: 100, cellClassRules: {'fw-bold': (params) => { return params.data.gmtype === '계'}}},
            {headerName: "상세", width: 100, cellRenderer: function (params) {
                    return $(`<a onclick='' style="text-decoration: underline" href="#">현황 검색</a>`)
                        .click(() => goViolatedList(params.data.gmtype1, params.data.gmsub))[0];
                }},
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
            wrapHeaderText: true,
            autoHeaderHeight: true,
        };
        this.gridOptions = {
            columnDefs: this.columnDefs,
            defaultColDef: this.defaultColDef,
            pagination: false,
            domLayout: 'autoHeight',
            animateRows: true,
            rowData: [],
            overlayNoRowsTemplate: `
                <div>
                    <p style="color: #6c757d">조회된 데이터가 없습니다.</p>
                </div>
            `,
            suppressRowTransform: true,
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
            type: 'GET',
            cache: false,
            xhrFields: {
                responseType: "blob",
            },
        }), fileName)
    }
}
const violated = new ViolatedStatus();
const format = date => moment(date).format('YYYY.MM.DD')
const dateCheck = date => isNaN(new Date(date).getTime())

function goViolatedList(violationType, useType) {
    const form = document.createElement("form");
    const violationTypeElement = document.createElement("input");
    const useTypeElement = document.createElement("input");
    const statusToListElement = document.createElement("input");
    form.appendChild(violationTypeElement);
    form.appendChild(useTypeElement);
    form.appendChild(statusToListElement);
    document.body.appendChild(form);
    violationTypeElement.type = 'text';
    violationTypeElement.name = 'violationType';
    violationTypeElement.value = violationType;
    useTypeElement.type = 'text';
    useTypeElement.name = 'useType';
    useTypeElement.value = useType;
    statusToListElement.type = 'text';
    statusToListElement.name = 'statusToList';
    statusToListElement.value = 'true';
    form.action = '/viobldmng/list';
    form.method = 'get';
    form.submit();
}

document.addEventListener('DOMContentLoaded', () => {
    const gridDiv = document.querySelector('#myGrid')
    gridDiv && gridDiv.style ? violated.gridStyle(gridDiv) : console.error('Invalid Grid Style Element');
    const grid = new agGrid.Grid(gridDiv, violated.gridOptions);
    document.querySelector('button[name=excelDownload]').addEventListener('click', () => {
        snExcelDownload.excelDownload($.ajax({
            url: '/viobldmng/status/excel',
            type: 'post',
            cache: false,
            contentType: 'application/json; charset=utf-8',
            data: $('#searchForm').serialize(),
            xhrFields: {
                responseType: "blob",
            },
        }), '위반건축물통계');
    });
    const myCalendar = new customCalendar('calendarSelect1', 'calendarRemote1', 'startDate1', 'endDate1');
    myCalendar.setButtonValue('first', {text: '3개월', date: '3m'})
    myCalendar.setButtonValue('second', {text: '6개월', date: '6m'})
    myCalendar.setButtonValue('third', {text: '12개월', date: '1y'})
    myCalendar.setClass('buttonDiv', 'btn-group-sm row ms-4 col-5');
    myCalendar.setClass('dateSpan', 'col-1 text-center');

    $('#searchForm').submit(function () {
        $.get('/api/viobldmng/status', $('#searchForm').serialize(), function (data) {
            $('#skk').text($('#skkCd').val() === '' ? '전체' : $('#skkCd option:selected').text())
            let rows = [];
            let count = 1;
            grid.gridOptions.api.setRowData([]);
            for(let i = 0; i < data.length; i++) {
                rows.push({...data[i], gmtype1: data[i].gmtype, countRow: 0});
                if(i > 0) {
                    if(data[i].gmtype === data[i-1].gmtype) {
                        rows[i].gmtype = null;
                        count++;
                    } else {
                        rows[i - count].countRow = count;
                        count = 1;
                    }
                }
            }
            console.log(rows)
            grid.gridOptions.api.setRowData(rows);
        })
        return false;
    });

});