$(document).ready(function(){
    var cnt = 0;
    const siteChkSelOjbect = "";
    if(legerOwnerObj != "undefined"){
        console.log("legerOwnerObj");
        console.log(legerOwnerObj);
        ownerGridOptions.api.setRowData(legerOwnerObj);
        if(legerOwnerObj.length > 0){
            $("#legerOwnerTotCnt > span").text(legerOwnerObj.length);
        }else{
            $("#legerOwnerTotCnt > span").text(cnt);
        }
    }
    if(legerDongObj != "undefined"){
        console.log("legerDongObj");
        console.log(legerDongObj);
        dongGridOptions.api.setRowData(legerDongObj);
        if(legerDongObj.length > 0){
            $("#legerDongTotCnt > span").text(legerDongObj.length);
        }else{
            $("#legerDongTotCnt > span").text(cnt);
        }
    }
    if(legerStructuresObj != "undefined"){
        console.log("legerStructuresObj");
        console.log(legerStructuresObj);
        structuresGridOptions.api.setRowData(legerStructuresObj);
        if(legerStructuresObj.length > 0){
            $("#legerStructuresTotCnt > span").text(legerStructuresObj.length);
        }else{
            $("#legerStructuresTotCnt > span").text(cnt);
        }
    }
    if(legerGroundObj != "undefined"){
        console.log("legerGroundObj");
        console.log(legerGroundObj);
        groundGridOptions.api.setRowData(legerGroundObj);
        if(legerGroundObj.length > 0){
            $("#legerGroundTotCnt > span").text(legerGroundObj.length);
        }else{
            $("#legerGroundTotCnt > span").text(cnt);
        }
    }
    if(legerPermitObj != "undefined"){
        console.log("legerPermitObj");
        console.log(legerPermitObj);
        permitGridOptions.api.setRowData(legerPermitObj);
        if(legerPermitObj.length > 0){
            $("#legerPermitTotCnt > span").text(legerPermitObj.length);
        }else{
            $("#legerPermitTotCnt > span").text(cnt);
        }
    }

});

// 소유자현황
const ownerGridOptions ={
    suppressClickEdit: true,
    onCellClicked(params) {
        if (params.colDef.field === 'preOwnerName') {
            const form = $('#ledgerOwnerAddForm')[0];
            form.chDate.value = moment(params.data.chDate).format('YYYY-MM-DD');
            form.preOwnerName.value = params.data.preOwnerName;
            form.confirmName.value = params.data.confirmName;
            form.ownerAddr1.value = params.data.ownerAddr1;
            form.ownerAddr2.value = params.data.ownerAddr2;
            form.ownerSeq.value = params.data.ownerSeq;
            console.log(params.data);
            $('#ownerAddBtn').hide();
            $('#ownerModyBtn').show();
        }
        if(params.colDef.headerName == '삭제'){
            const ownerSeq = params.data.ownerSeq;
            const admSeq = params.data.admSeq;
            $.post({
                url: "/api/bldmngingb/ownerAction",
                data: JSON.stringify({
                    ownerParam: "Dell",
                    admSeq: admSeq,
                    ownerSeq: ownerSeq
                }),
                contentType: 'application/json; charset=utf-8'
            }).done(e => {
                ownerGridOptions.api.setRowData(e);
                $("#legerOwnerTotCnt > span").text(e.length);
            });
        }
    },
    editType: "fullRow",
    columnDefs : [
        { headerName: "No.", field: "ownerRn", cellStyle: {textAlign: "center"}, width: 80 },
        { headerName: "ownerSeq", field: "ownerSeq", hide: true},
        {
            headerName: "양도양수일자",
            field: "chDate",
            cellStyle: { textAlign: "center" },
            width: 120,
            valueFormatter: function (params) {
                return params.data.chDate ? moment(params.data.chDate).format('YYYY-MM-DD') : '';
            }
        },
        { headerName: "주소", cellStyle: {textAlign: "center"},
            groupId: 'ownerAddr', cellStyle: {textAlign: "center"},
            children: [
                { headerName: "도로명주소", field: "ownerAddr1", type: "addrColumn", cellStyle: {textAlign: "center"}, width:350 },
                { headerName: "지번주소", field: "ownerAddr2", type: "addrColumn", cellStyle: {textAlign: "center"}, width:350}
            ]
        },
        { headerName: "양수자성명", field: "preOwnerName", cellStyle: {textAlign: "center", 'text-decoration': 'underline', cursor: 'pointer'}, width: 100 },
        { headerName: "확인자", field: "confirmName", cellStyle: {textAlign: "center"}, width: !canUpdate ? 180 : 100 },
        { headerName: "삭제",
            width: 80,
            cellRenderer: actionCellRenderer,
            hide: !canUpdate
        }
    ],
    defaultColDef: {
        headerClass: ["center", "justCenter"], cellClass: ["center", "justCenter"],
        sortable: true,
        resizable: true,
    },
    pagination: true,
    paginationPageSize: 10,
    onGridReady: function (event) {
        // 행 넓이 자동 조절
        event.api.sizeColumnsToFit();
    },
    domLayout: 'autoWidth'
};
// 동별현황
const dongGridOptions ={
    suppressClickEdit: true,
    onCellClicked(params) {
        if (params.colDef.field === 'dongNo') {
            const form = $('#ledgerDongAddForm')[0];
            form.dongNo.value = params.data.dongNo;
            form.dongJydCd.value = params.data.dongJydCd;
            form.dongStCd.value = params.data.dongStCd;
            form.underScale.value = params.data.underScale;
            form.floor1Scale.value = params.data.floor1Scale;
            form.floor2Scale.value = params.data.floor2Scale;
            form.floor3Scale.value = params.data.floor3Scale;
            form.floor4Scale.value = params.data.floor4Scale;
            form.floorsSumScale.value = params.data.floorsSumScale;
            form.dongPermitDate.value = moment(params.data.dongPermitDate).format('YYYY-MM-DD');
            form.dongPermitNo.value = params.data.dongPermitNo;
            form.dongCompDate.value = moment(params.data.dongCompDate).format('YYYY-MM-DD');
            form.dongSeq.value = params.data.dongSeq;
            console.log(params.data);
            $('#dongAddBtn').hide();
            $('#dongModyBtn').show();
        }
        if(params.colDef.headerName == '삭제'){
            const dongSeq = params.data.dongSeq;
            const admSeq = params.data.admSeq;
            $.post({
                url: "/api/bldmngingb/dongAction",
                data: JSON.stringify({
                    dongParam: "Dell",
                    admSeq: admSeq,
                    dongSeq: dongSeq
                }),
                contentType: 'application/json; charset=utf-8'
            }).done(e => {
                dongGridOptions.api.setRowData(e);
                $("#legerDongTotCnt > span").text(e.length);
            });
        }
    },
    editType: "fullRow",
    columnDefs : [
        { headerName: "No.", field: "dongRn", cellStyle: {textAlign: "center"}, width: 120 },
        { headerName: "dongSeq", field: "dongSeq", hide: true },
        { headerName: "동별번호", field: "dongNo", cellStyle: {textAlign: "center", 'text-decoration': 'underline', cursor: 'pointer'}, width: 120 },
        { headerName: "용도코드", field: "dongJydCd", hide: true},
        { headerName: "용도", field: "dongJydListName", width: 180},
        { headerName: "구조코드", field: "dongStCd", hide: true },
        { headerName: "구조", field: "dongStListName", width: 170 },
        { headerName: "규모(m2)", field: "floorsSumScale", width: 130 },
        { headerName: "허가(신고)일", field: "dongPermitDate", width: 130,
            valueFormatter: function (params) {
                return params.data.dongPermitDate ? moment(params.data.dongPermitDate).format('YYYY-MM-DD') : '';
            }
        },
        { headerName: "허가(신고)번호", field: "dongPermitNo", width: 130 },
        { headerName: "준공일자", field: "dongCompDate", width: !canUpdate ? 210 : 130,
            valueFormatter: function (params) {
                return params.data.dongCompDate ? moment(params.data.dongCompDate).format('YYYY-MM-DD') : '';
            }
        },
        { headerName: "삭제",
            width: 80,
            cellRenderer: actionCellRenderer,
            editable: false,
            hide: !canUpdate
        }
    ],
    defaultColDef: {
        headerClass: ["center", "justCenter"], cellClass: ["center", "justCenter"],
        sortable: true,
        resizable: true,
    },
    pagination: true,
    paginationPageSize: 10,
    onGridReady: function (event) {
        // 행 넓이 자동 조절
        event.api.sizeColumnsToFit();
    },
    domLayout: 'autoWidth'
};
// 구조물현황
const structuresGridOptions ={
    suppressClickEdit: true,
    onCellClicked(params) {
        if (params.colDef.field === 'stDongNo') {
            const form = $('#legerStructuresAddForm')[0];
            form.stDongNo.value = params.data.stDongNo;
            form.stJydCd.value = params.data.stJydCd;
            form.stJydName.value = params.data.stJydName;
            form.stCd.value = params.data.stCd;
            form.stName.value = params.data.stName;
            form.stArea.value = params.data.stArea;
            form.stHeight.value = params.data.stHeight;
            form.stPermitDate.value = moment(params.data.stPermitDate).format('YYYY-MM-DD');
            form.stPermitNo.value = params.data.stPermitNo;
            form.stCompDate.value = moment(params.data.stCompDate).format('YYYY-MM-DD');
            form.stSeq.value = params.data.stSeq;
            console.log(params.data);
            $('#stAddBtn').hide();
            $('#stModyBtn').show();
        }
        if(params.colDef.headerName == '삭제'){
            const stSeq = params.data.stSeq;
            const admSeq = params.data.admSeq;
            $.post({
                url: "/api/bldmngingb/structuresAction",
                data: JSON.stringify({
                    stParam: "Dell",
                    admSeq: admSeq,
                    stSeq: stSeq
                }),
                contentType: 'application/json; charset=utf-8'
            }).done(e => {
                structuresGridOptions.api.setRowData(e);
                $("#legerStructuresTotCnt > span").text(e.length);
            });
        }
    },
    editType: "fullRow",
    columnDefs : [
        { headerName: "No.", field: "stRn", cellStyle: {textAlign: "center"}, width: 100 },
        { headerName: "stSeq", field: "stSeq", hide: true },
        { headerName: "동별번호", field: "stDongNo", cellStyle: {textAlign: "center", 'text-decoration': 'underline', cursor: 'pointer'}, width: 100 },
        { headerName: "용도(시설물) 코드", field: "stJydCd", hide: true},
        { headerName: "용도(시설물)", field: "stJydListName", width: 160},
        { headerName: "구조 코드", field: "stCd", hide: true },
        { headerName: "구조", field: "stListName", width: 160 },
        { headerName: "면적", field: "stArea", width: 100 },
        { headerName: "높이", field: "stHeight", width: 100 },
        {
            headerName: "허가(신고)일",
            field: "stPermitDate",
            width: 130,
            valueFormatter: function (params) {
                return params.data.stPermitDate ? moment(params.data.stPermitDate).format('YYYY-MM-DD') : '';
            }
        },
        { headerName: "허가(신고)번호", field: "stPermitNo", width: 140 },
        {
            headerName: "준공일자",
            field: "stCompDate",
            width: !canUpdate ? 200 : 100,
            valueFormatter: function (params) {
                return params.data.stCompDate ? moment(params.data.stCompDate).format('YYYY-MM-DD') : '';
            }
        },
        { headerName: "삭제",
            width: 70,
          cellRenderer: actionCellRenderer,
          editable: false,
            hide: !canUpdate
        }
    ],
    defaultColDef: {
        headerClass: ["center", "justCenter"], cellClass: ["center", "justCenter"],
        sortable: true,
        editable: true,
        resizable: true,
        minWidth: 100,
    },
    pagination: true,
    paginationPageSize: 10,
    onGridReady: function (event) {
        // 행 넓이 자동 조절
        event.api.sizeColumnsToFit();
    },
    domLayout: 'autoWidth'
};
// 대지현황
const groundGridOptions ={
    suppressClickEdit: true,
    onCellClicked(params) {
        if (params.colDef.field === 'groundArea') {
            const form = $('#legerGroundAddForm')[0];
            form.groundArea.value = params.data.groundArea;
            form.groundContents.value = params.data.groundContents;
            form.chaArea.value = params.data.chaArea;
            form.farmArea.value = params.data.farmArea;
            form.groundEtc.value = params.data.groundEtc;
            form.groundSeq.value = params.data.groundSeq;
            console.log(params.data);
            $('#groundAddBtn').hide();
            $('#groundModyBtn').show();
        }
        if(params.colDef.headerName == '삭제'){
            const groundSeq = params.data.groundSeq;
            const admSeq = params.data.admSeq;
            $.post({
                url: "/api/bldmngingb/groundAction",
                data: JSON.stringify({
                    groundParam: "Dell",
                    admSeq: admSeq,
                    groundSeq: groundSeq
                }),
                contentType: 'application/json; charset=utf-8'
            }).done(e => {
                groundGridOptions.api.setRowData(e);
                $("#legerGroundTotCnt > span").text(e.length);
            });
        }
    },
    editType: "fullRow",
    columnDefs : [
        { headerName: "No.", field: "groundRn", cellStyle: {textAlign: "center"}, width: 130 },
        { headerName: "groundSeq", field: "groundSeq", hide: true },
        { headerName: "토지면적", field: "groundArea", cellStyle: {textAlign: "center", 'text-decoration': 'underline', cursor: 'pointer'}, width: 180 },
        { headerName: "대지", field: "groundContents", width: 180},
        { headerName: "형질변경", field: "chaArea", width: 170 },
        { headerName: "농지전용", field: "farmArea", width: 150 },
        { headerName: "기타", field: "groundEtc", width: !canUpdate ? 380 : 180 },
        { headerName: "삭제",
            cellRenderer: actionCellRenderer,
            editable: false,
            hide: !canUpdate
        }
    ],
    defaultColDef: {
        headerClass: ["center", "justCenter"], cellClass: ["center", "justCenter"],
        sortable: true,
        resizable: true,
        minWidth: 100,
    },
    pagination: true,
    paginationPageSize: 10,
    onGridReady: function (event) {
        event.api.sizeColumnsToFit();
    },
    domLayout: 'autoWidth'
};
// 허가(신고)현황
const permitGridOptions ={
    suppressClickEdit: true,
    onCellClicked(params) {
        if (params.colDef.field === 'actFlag') {
            const form = $('#legerPermitAddForm')[0];
            form.permitNo.value = params.data.permitNo;
            form.permitDate.value = moment(params.data.permitDate).format('YYYY-MM-DD');
            form.permitCompDate.value = moment(params.data.permitCompDate).format('YYYY-MM-DD');
            form.actFlag.value = params.data.actFlag;
            form.permitStCd.value = params.data.permitStCd;
            form.permitStName.value = params.data.permitStName;
            form.permitArea.value = params.data.permitArea;
            form.permitJydCd.value = params.data.permitJydCd;
            form.permitJydName.value = params.data.permitJydName;
            form.registInfo.value = params.data.registInfo;
            form.confirmInfo.value = params.data.confirmInfo;
            form.permitSeq.value = params.data.permitSeq;
            console.log(params.data);
            $('#permitAddBtn').hide();
            $('#permitModyBtn').show();
        }
        if(params.colDef.headerName == '삭제'){
            const permitSeq = params.data.permitSeq;
            const admSeq = params.data.admSeq;
            $.post({
                url: "/api/bldmngingb/permitAction",
                data: JSON.stringify({
                    permitParam: "Dell",
                    admSeq: admSeq,
                    permitSeq: permitSeq
                }),
                contentType: 'application/json; charset=utf-8'
            }).done(e => {
                permitGridOptions.api.setRowData(e);
                $("#legerPermitTotCnt > span").text(e.length);
            });
        }
    },
    editType: "fullRow",
    columnDefs : [
        { headerName: "No.", field: "permitRn", cellStyle: {textAlign: "center"}, width: 100 },
        { headerName: "permitSeq", field: "permitSeq", hide: true },
        { headerName: "행위구분", field: "actFlag", cellStyle: {textAlign: "center", 'text-decoration': 'underline', cursor: 'pointer'}, width: 120 },
        { headerName: "구조 코드", field: "permitStCd", hide: true},
        { headerName: "구조", field: "permitJydListName", width: 160},
        { headerName: "규모(m2)", field: "permitArea", width: 120 },
        { headerName: "용도 코드", field: "permitJydCd", hide: true },
        { headerName: "용도", field: "permitStListName", width: 130 },
        {
            headerName: "허가(신고)일",
            field: "permitDate",
            width: 120,
            valueFormatter: function (params) {
                return params.data.permitDate ? moment(params.data.permitDate).format('YYYY-MM-DD') : '';
            }
        },
        { headerName: "허가(신고)번호", field: "permitNo", width: 130 },
        {
            headerName: "준공일자",
            field: "permitCompDate",
            width: 120,
            valueFormatter: function (params) {
                return params.data.permitCompDate ? moment(params.data.permitCompDate).format('YYYY-MM-DD') : '';
            }
        },
        { headerName: "작성자", field: "registInfo", width:!canUpdate ? 190 : 100 },
        { headerName: "삭제",
            width: 70,
            cellRenderer: actionCellRenderer,
            editable: false,
            hide: !canUpdate
        }
    ],
    defaultColDef: {
        headerClass: ["center", "justCenter"], cellClass: ["center", "justCenter"],
        sortable: true,
        resizable: true,
        minWidth: 100,
    },
    pagination: true,
    paginationPageSize: 10,
    onGridReady: function (event) {
        event.api.sizeColumnsToFit();
    },
    domLayout: 'autoWidth'
};

 document.addEventListener('DOMContentLoaded', () => {
   var gridDiv = document.querySelector('#ownerGrid');
   var gridDiv2 = document.querySelector('#dongGrid');
   var gridDiv3 = document.querySelector('#structureGrid');
   var gridDiv4 = document.querySelector('#groundGrid');
   var gridDiv5 = document.querySelector('#permitGrid');
   //var gridDiv6 = document.querySelector('#imgFileGrid');

   new agGrid.Grid(gridDiv,  ownerGridOptions);
   new agGrid.Grid(gridDiv2,  dongGridOptions);
   new agGrid.Grid(gridDiv3,  structuresGridOptions);
   new agGrid.Grid(gridDiv4,  groundGridOptions);
   new agGrid.Grid(gridDiv5,  permitGridOptions);
   //new agGrid.Grid(gridDiv6,  imgFileGridOptions);

 });

 function onBtnExportDataAsCsv() {
 gridOptions.api.exportDataAsCsv();
}



function actionCellRenderer(params) {
  let eGui = document.createElement("div");
  // checks if the rowIndex matches in at least one of the editing cells
  eGui.innerHTML = '<button type="button" id="fixListDelBtn" class="btn btn-sm btn-danger" data-action="delete">삭제</button>';
  return eGui;
}