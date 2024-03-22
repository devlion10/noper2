let validationFlag = false;
const rowData = [];
let searchData = "";
$(document).ready(function(){
    let cnt = 0;
    searchData = JSON.stringify($("#ownerChangeFindForm").serializeObject());
    // 주소찾기 btn 있을때만 동작
    if(document.querySelector("#jusoBtn") != null){
         document.querySelector("#jusoBtn").addEventListener("click", (e)=>{
             const w = window.open('/common/juso', 'juso', 'width=500px,height=500px');
             w.focus();
         });
    }


    // 기존무허가 대장관리 상세에서 기존무허가 소유자변경관리로 이동했을 때, 자동 대장번호 기입 로직
    const url = new URL(window.location.href);
    const urlGmskk = url.searchParams.get('gmskk');
    const urlGmdjgb = url.searchParams.get('gmdjgb');
    const urlGmseqco = url.searchParams.get('gmseqco');
    if (url.pathname === '/unlcebldmng/ownerChange' && urlGmskk != null && urlGmdjgb != null && urlGmseqco != null) {
        document.getElementById('gmdjgb').value = urlGmdjgb
        document.getElementById('gmseqcoInput1').value = urlGmseqco
        document.getElementById('gmseqcoInput2').value = urlGmseqco
    }
    if (document.getElementById('ownerChangeReasonGrid') != null) {
        // ownerChangeReasonGridOptions.api.setRowData();
    }


    if($("#ownerChangeFindForm input[name=gmskk]").val() != ""){
        $("#ownerChangeFindForm input[name=gmskk]").attr("disabled", true);
    }

    $('#newListBtn').click(() => {
        location.href="/unlcebldmng/ownerChange";
    });
    $('#changeBtn').click(() => {
        location.href="/unlcebldmng/ownerChangeAdd?addFlage=change";
    });
    $('#absorptionBtn').click(() => {
        location.href="/unlcebldmng/ownerChangeAdd?addFlage=absorption";
    });
    $('#ownerListBtn').click(() => {
        location.href="/unlcebldmng/ownerChange";
    });
    $('#saveBtn').click(() => {
        let gmbunho = "";

        // 소유자 변경현황 수 체크
        /*let ownerChangeGridData = []
        ownerChangeGridOptions.api.rowModel.forEachNode(e => ownerChangeGridData.push(e.data));*/

        console.log($("#suilnoDtl").val())
        if($("#suilnoDtl").val() != ""){
            gmbunho = $("#gmbunhoDtl").val() + "-" + $("#suilnoDtl").val() + `&gmjilno=${$("#gmjilnoDtl").val()}`
        }else{
            gmbunho = $("#gmbunho").val();
        }

        snAlert.confirm('수정 화면으로 이동하시겠습니까?')
            .then(e => {
                if (e.isConfirmed) {
                    let href = "/unlcebldmng/ownerChangeAdd?addFlage=change&gmbunho="+gmbunho;
                    location.href= href;
                }
            })
    });


    if($('input[name=ownerChangeFlag]').val() == 'list'){
        // 페이지 로드시 검색 수행
        //findList();
    }
    if(ownerChangeObj != "" && ownerChangeObj != "false" ){
        ownerChangeGridOptions.api.setRowData(ownerChangeObj);
        ownerChangeReasonGridOptions.api.setRowData(ownerChangeObj)
        // var todayOwnerChangeObj = ownerInfoList;
        ownerGridOptions.api.setRowData(ownerInfoList);
        if(ownerChangeObj.length > 0){
            $("#ownerChangeTotCnt > span").text(ownerChangeObj.length);
            $("#ownerChangeReasonTotCnt > span").text(ownerChangeObj.length);
        }else{
            $("#ownerChangeTotCnt > span").text(cnt);
        }
    }else if(addNowOwnerGridObj != "" && addNowOwnerGridObj != "false"){
        addNowOwnerGridOptions.api.setRowData(addOwnerInfoList);
    }


    const valuedate = () => {
       // 대장번호 selectbox
       if($("select[name=gmdjgb]").val() == '전체') $("select[name:gmdjgb]").val("");
       // 소유구분 selectbox
       if($("select[name=tojisg]").val() == '전체') $("select[name:tojisg]").val("");
       // 용도 selectbox
       if($("select[name=gmyd]").val() == '전체') $("select[name:gmyd]").val("");
       // 관리행정도 selectbox
       if($("select[name=gmhjdcd]").val() == '전체') $("select[name:gmhjdcd]").val("");
       if($("select[name=chgFlag]").val() == '전체') $("select[name:chgFlag]").val("");
    };


    $('#resetBtn').click(() => {
        $("#ownerChangeFindForm")[0].reset();
        $("#sujiljaCalendarRemote").html("");
        $("#criljaCalendarRemote").html("");
        $("#sujilja1").val("");
        $("#sujilja2").val("");
        $("#crilja1").val("");
        $("#crilja2").val("");
    });
    $('#newLegerAddBtn').click(() => {location.href="/unlcebldmng/ledgerAdd";});
    $('#newLegerListBtn').click(() => {location.href="/unlcebldmng/newLedger";});
    // 엑셀 다운로드
    //$('#csvBtn').click(() => {snGrid.exportCsv();});
    $('#csvBtn').click(() => {
        snExcelDownload.excelDownload($.ajax({
             url: '/unlcebldmng/download/ownerChangeExcel',
             type: 'post',
             cache: false,
             contentType: 'application/json; charset=utf-8',
             data: searchData,
             xhrFields: {
                responseType: "blob",
             },
        }), "기존무허가 소유자변경관리");
    });

     if(ownerChangeObj == ""){
        $('#searchBtn').click(function(){
            $('input[name=gmskk]').val($('#gmskkTmp').val());
            searchData = JSON.stringify($("#ownerChangeFindForm").serializeObject());
            findList();
        });
     }
     $('#btnSearch').click(() => {
        if(gmbunho != "" && gmbunho != null){
            snAlert.alert("수정페이지에서는 <br> 조회가 불가능합니다.");
            return;
        }
        if($('#gmdjgb').val() === '' || $('#gmseqco').val() === ''){
            snAlert.alert("조회할 대장번호를 입력해주세요.");
            return;
        }
        $('input[name=ownerChangeFlag]').val("absorptionDetail");
        $('#ownerChangeFindForm input[name=gmskk]').val($('#ownerChangeFindForm input[name=gmskkTmp]').val());
        // 소유자 (변경 / 병합) 신고의 조회버튼 클릭 시 사용되는 Ajax
         $.post({
             url: "/api/unlicensedBuilingManagement/ownerChangeDetail",
             data: JSON.stringify($("#ownerChangeFindForm").serializeObject()),
             contentType: 'application/json; charset=utf-8'
         }).done(e => {
             if (e.absorptionNoperMaster === null) {
                 snAlert.alert('해당 대장은 없는 대장입니다.')
                 return
             }
             if (e.absorptionNoperMaster.degb === 'Y') {
                 snAlert.alert('철거된 대장입니다.')
                 return;
             }
             $('.dfGmskk').text(`${e.absorptionNoperMaster.gmskk}-${e.absorptionNoperMaster.gmdjgbName}-${e.absorptionNoperMaster.gmseqco}`);
             $('.dfGmgzcd').text(e.absorptionNoperMaster.gmgzcd);
             $('.dfTojisg').text(e.absorptionNoperMaster.tojisg);
             $('.dfGmmunjuk').text(`${e.absorptionNoperMaster.gmgunp}평(${e.absorptionNoperMaster.gmmunjuk}㎡)`);
             $('.dfTojimunjuk').text(`${e.absorptionNoperMaster.tojimj}평(${e.absorptionNoperMaster.tojimunjuk}㎡)`);
             $('.dfGmskkname').text(e.absorptionNoperMaster.gmskkname);
             $('.dfNaRoadNm').text(e.absorptionNoperMaster.newaddr1);
             addNowOwnerGridOptions.api.setRowData(e.absorptionNoperOwner)
         });
     });

    let ownerChangeFindForm =  JSON.stringify($("#ownerChangeFindForm").serializeObject());
    function findList() {
       //valuedate();
       $('input[name=gmskk]').val($('#gmskkTmp').val());
       $.post({
           url: "/api/unlicensedBuilingManagement/ownerChange",
           data: JSON.stringify($("#ownerChangeFindForm").serializeObject()),
           contentType: 'application/json; charset=utf-8'}).done(e=>{
               $('#totCnt > h3').text("Total " + e.length);
               gridOptions.api.setRowData(e)
           });
    }
     $('#noperNumSearchDiv2').click(() => {
        if(gmbunho != "" && gmbunho != null){
            snAlert.alert("수정페이지에서는 <br> 찾기가 불가능합니다.");
            return;
        }
     });

     $('.ownerChangeBtn').click((e) => {
        const ownerChangeFlag = $('input[name=ownerChangeFlag]')
        if(ownerChangeFlag.val() === "absorptionDetail"){
            $('input[name=gmjilno]').val("1");
        }
        if(e.currentTarget.innerText === "저장"){
            ownerChangeFlag.val("mody");
        }else if(e.currentTarget.innerText === "삭제"){
            ownerChangeFlag.val("dell");
        }else{
            ownerChangeFlag.val("save");
        }

        if($("input[name=sujiljaTemp]").val() !== ""){
            $("input[name=sujilja]").val(moment($("input[name=sujiljaTemp]").val()).format('YYYYMMDD'));
        }else{
            $("input[name=sujilja]").val("");
        }
        if($("input[name=sgpjdateTemp]").val() !== ""){
            $("input[name=sgpjdate]").val(moment($("input[name=sgpjdateTemp]").val()).format('YYYYMMDD'));
        }else{
            $("input[name=sgpjdate]").val("");
        }
        $("input[name=gmjjumin]").val($('input[name=gmjjumin1]').val() + '-' + $('input[name=gmjjumin2]').val());
        $("input[name=gmjjgn]").val($("#gmjjgnTemp option:selected").val());
        if($('input[name=gmcsahTemp]').val() === "대지"){
          $('input[name=gmcsah]').val("0");
        }else if($('input[name=gmcsahTemp]').val() === "산"){
          $('input[name=gmcsah]').val("1");
        }else{
          $('input[name=gmcsah]').val("");
        }
        // change 변경, absorption 병합
        $("input[name=changDivision]").val(modalFlage);

        let form = $('#ownerChangeFindForm');

        validation(form);

        const newAddr1 = $('#ownerChangeFindForm input[name=newaddr1]')
        const newAddr2 = $('#ownerChangeFindForm input[name=newaddr2]')
        if(newAddr1.val() === "" || newAddr2.val() === ""){
            newAddr1.addClass("is-invalid");
            newAddr2.addClass("is-invalid");
            return;
        }else {
            newAddr1.removeClass("is-invalid");
            newAddr2.removeClass("is-invalid");
        }
        
        // 대장 번호 조회 확인
        if(form.find("[name='gmseqco']").val() === "") {
            snAlert.alert('대장정보가 없습니다.\n번호찾기 버튼을 클릭하여\n 대장을 선택해주세요.')
            return;
        }

        // 기존소유자에 대한 정보 선택 확인
        if (form.find("[name='jgmjname']").val() === "") {
            snAlert.alert('현소유자를 선택해주세요.')
            return;
        }

        // 수정체크
        const modifyCheck = gmbunho != null;

        let confirmTxt="";
        if(e.currentTarget.innerText === "삭제" && modalFlage === "change"){
            confirmTxt = "소유자이력을 삭제하시겠습니까?";
        }else if(modalFlage === "change"){
            if (modifyCheck) {
                confirmTxt = `소유자변경신고 수정 하시겠습니까?`;
            } else {
                confirmTxt = `소유자변경신고 하시겠습니까?`;
            }
        }else{
            confirmTxt = "소유자병합신고 하시겠습니까?";
        }
        let fileFlag = "";
        if(ownerChangeFlag.val() === "save"){
            fileFlag = "change"
        }else{
            fileFlag = "absorption"
        }

        let seq = authSkk +"-"+$("#ownerChangeFindForm select[name=gmdjgb]").val()+"-"+$("#ownerChangeFindForm input[name=gmseqco]").val();
        $("input[name=issue]").val($("#issueTemp option:selected").val());
        if(validationFlag){
            snAlert.confirm(confirmTxt).then((resultChk) => {
                if (resultChk.isConfirmed) {
                    $.post({
                        url: "/api/unlicensedBuilingManagement/ownerChange",
                        data: JSON.stringify($("#ownerChangeFindForm").serializeObject()),
                        contentType: 'application/json; charset=utf-8'
                    }).done(e => {
                        uploader.parentKey = seq;
                        uploader.flag = $("#ownerChangeFindForm input[name=gmjilno]").val() + "-" + fileFlag;
                        uploader.uploadFiles(false);
                        uploader.clearFiles();
                        snAlert.success(`${modifyCheck ? '수정' : '저장'}되었습니다.`).then(e => {
                            const gmdjgb = (e) => {
                                switch (e) {
                                    case '0':
                                        return '등재'
                                    case '1':
                                        return '미등재'
                                    case '2':
                                        return '항측판독'
                                }
                            }
                            location.href = modifyCheck
                                ? `/unlcebldmng/ownerChangeDetail?gmbunho=${authSkk}-${gmdjgb($("#ownerChangeFindForm select[name=gmdjgb]").val())}-${$("#ownerChangeFindForm input[name=gmseqco]").val()}`
                                : `/unlcebldmng/ownerChange`;
                        })
                    });
                }
            });
        }
     });
     const userName = $("#ownerChangeFindForm input[name=userName]").val();
     if(document.querySelector("#declarationBtn") != null){
         document.querySelector("#declarationBtn").addEventListener("click", (e)=>{
            let jsonData = JSON.stringify($("#ownerChangeFindForm").serializeObject());
             let data = {
                OWNER_NM_CHANGE_DCLRT: {seal: $('input[name=gmskk]').val(), skkCd: $('input[name=gmskk]').val(), gmskk: $('input[name=gmskk]').val(), gmdjgb: JSON.parse(jsonData).gmdjgb, gmseqco: JSON.parse(jsonData).gmseqco, suilno: JSON.parse(jsonData).suilno, gmjilno: JSON.parse(jsonData).gmjilno, user: userName},
             }

            snReport.open('OWNER_NM_CHANGE_DCLRT', data.OWNER_NM_CHANGE_DCLRT);
         });
     }
     if(document.querySelector("#reportListBtn") != null){
         document.querySelector("#reportListBtn").addEventListener("click", (e)=>{
             let jsonData = JSON.parse(searchData);
             /*
             if( jsonData.gmskk == null || jsonData.gmskk == "" ){
                 return snAlert.alert("대장번호를 입력하여\n조회 후 출력해주세요.");
             }
             */
             let data = {
                 OWNER_NM_CHANGE_STTUS: {
                     seal: $('#ownerChangeFindForm input[name=gmskk]').val(),
                     gmskk: $('#ownerChangeFindForm input[name=gmskk]').val(),
                     gmdjgb: jsonData.gmdjgb,
                     gmseqco: jsonData.gmseqco,
                     daejang: "",
                     gmskkcd: "",
                     gmhjdcd: "",
                     gmbjdcd: "",
                     gmcsah: jsonData.gmcsah,
                     gmcbuh: "",
                     changDivision: jsonData.changDivision,
                     gmcji: "",
                     sujilja1: jsonData.sujilja1,
                     sujilja2: jsonData.sujilja2,
                     crilja1: jsonData.crilja1,
                     crilja2: jsonData.crilja2,
                     gmjname: jsonData.gmjname,
                     gmseqco2: jsonData.gmseqco2,
                     searchJuso: jsonData.searchJuso,
                     user: userName
                 }
             }
             snReport.open('OWNER_NM_CHANGE_STTUS', data.OWNER_NM_CHANGE_STTUS);
         });
     }
});

// 소유자변경관리 리스트
const gridOptions ={
    columnDefs : [
        { headerName: "No.", field: "rn",  width:150, cellStyle: {textAlign: "center"}, /*checkboxSelection: checkboxSelection,*/},
        { headerName: "대장번호", field: "skkseqno1", cellStyle: {textAlign: "center"},
            cellRenderer: function(params){
                let detailUrl = `/unlcebldmng/ownerChangeDetail?gmbunho=`+params.data.skkseqno1;
                return '<a class="ag-theme-alpine" style="text-decoration: underline" href="'+detailUrl+'">' + params.data.skkseqno1 + '</a>';
            }
        },
        { headerName: "건물주소",
          groupId: 'buildAddr',
          children: [
            { headerName: "도로명주소", field: "newaddr1", type: "addrColumn", width:300 },
            { headerName: "지번주소", field: "newaddr2", type: "addrColumn", width:300},
            { headerName: "대지/산", field: "gmcsah", type: "addrColumn", width:130}
          ]
        },
        { headerName: "현소유자", field: "gmjname", width:160, cellStyle: {textAlign: "center"} },
        { headerName: "전소유자", field: "jgmjname", width:160, cellStyle: {textAlign: "center"} },
        { headerName: "변경사유", field: "bksu", cellStyle: {textAlign: "center"} },
        { headerName: "접수일자", field: "sujilja", width:160, cellStyle: {textAlign: "center"},
            valueFormatter: function (params) {
                return moment(params.value).format('yyyy-MM-DD');
            }
        },
        { headerName: "처리일자", field: "crilja", width:160, cellStyle: {textAlign: "center"},
            valueFormatter: function (params) {
                return moment(params.value).format('yyyy-MM-DD');
            }
        }
    ],
    defaultColDef: {
        headerClass: ["center", "justCenter"], cellClass: ["center", "justCenter"],
        sortable: true,
        floatingFilter: false,
        resizable: true,
        cellDataType: false,
        editable: false,
        enableRowGroup: false,
        enablePivot: false,
        enableValue: false
    },
    domLayout: 'autoHeight',
    animateRows: true,
    pagination: true,
    paginationPageSize: 10,
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

function ownerChangeDtail(val){
    location.href="/ownerChangeDtail"
}

// 소유자현황
const ownerGridOptions ={
    columnDefs : [
        /*{ headerName: "No.", field: "rn", cellStyle: {textAlign: "center"}},*/
        { headerName: "소유자", field: "gmjname", width:120, cellStyle: {textAlign: "center"} },
        { headerName: "소유자 주소",
          groupId: 'buildAddr',
          children: [
            { headerName: "도로명주소", field: "newaddr1", type: "addrColumn", width:300 },
            { headerName: "지번주소", field: "newaddr2", type: "addrColumn", width:300,
                cellRenderer: function(params){
                   if(params.data.gmjsna != null){
                        if(params.data.gmjjname != null){
                            return params.data.gmjsna + ' ' + params.data.gmjjname
                        }else {
                            return params.data.gmjsna
                        }
                   } else {
                       return params.data.newaddr2
                   }
                }
            }
          ]
        },
        { headerName: "소유자 주민번호", field: "gmjjumin", cellStyle: {textAlign: "center"},
            cellRenderer: function(params){
               if (params.data.gmjjumin.indexOf('-') != -1) {
                    return params.data.gmjjumin;
               }else{
                     return params.data.gmjjumin.substring(0, 6) + "-" + params.data.gmjjumin.substring(6, 13);
               }
            }
        },
        { headerName: "이전 소유자", field: "jgmjname", width:120, cellStyle: {textAlign: "center"} },
        { headerName: "접수일자", field: "sujilja", width:120, cellStyle: {textAlign: "center"},
            valueFormatter: function (params) {
                return moment(params.value).format('yyyy-MM-DD');
            }
        },
        { headerName: "처리일자", field: "crilja", width:120, cellStyle: {textAlign: "center"},
            valueFormatter: function (params) {
                return moment(params.value).format('yyyy-MM-DD');
            }
        }
        /*, 주석 처리 요청으로 수정함.
            { headerName: "변경", minWidth: 150,
              cellRenderer: actionCellRenderer,
              editable: false
            }
        */
    ],
    defaultColDef: {
        headerClass: ["center", "justCenter"], cellClass: ["center", "justCenter"],
        sortable: true,
        resizable: true,
        minWidth: 70,
    },
    domLayout: 'autoHeight',
    pagination: true,
    paginationPageSize: 10,
    onCellClicked: (params) => {
        /*
        if(params.event.target.dataset.action){
            let action  = params.event.target.dataset.action;
            if(action == 'changeAdd'){
                location.href="/unlcebldmng/ownerChangeAdd?addFlage=change&gmbunho="+params.data.skkseqno1;
            }
        }
        */
    },
    onGridReady: function (event) {
        // 행 넓이 자동 조절
        event.api.sizeColumnsToFit();
    }
};

// 소유자변경사유 현황
const ownerChangeReasonGridOptions ={
    columnDefs : [
        { headerName: "No.", field: "rn", width:10, cellStyle: {textAlign: "center"}},
        {
            headerName: "변경사유",
            field: "bksu",
            width:450,
            cellStyle: {textAlign: "center"},
        },
        {
            headerName: "변경자",
            field: "userName",
            width:70,
            cellStyle: {textAlign: "center"},
        },
        { headerName: "변경일자", field: "registTs", width:70, cellStyle: {textAlign: "center"},
            valueFormatter: function (params) {
                return moment(params.value).format('yyyy-MM-DD');
            }
        }
    ],
    defaultColDef: {
        headerClass: ["center", "justCenter"], cellClass: ["center", "justCenter"],
        sortable: true,
        resizable: true,
    },
    domLayout: 'autoHeight',
    pagination: true,
    paginationPageSize: 10,
    onGridReady: function (event) {
        // 행 넓이 자동 조절
        event.api.sizeColumnsToFit();
    }
};

// 소유자변경현황
const ownerChangeGridOptions ={
    columnDefs : [
        { headerName: "No.", field: "rn", width:70, cellStyle: {textAlign: "center"}},
        {
            headerName: "소유자",
            field: "gmjname",
            cellStyle: {textAlign: "center"},
            cellRenderer: params => {
                return `<a class="underLine" href="javascript:void(0);">${params.value}</a>`;
            }
        },
        { headerName: "소유자 주소",
          groupId: 'buildAddr',
          children: [
            { headerName: "도로명주소", field: "newaddr1", type: "addrColumn", width:300
            },
            { headerName: "지번주소", field: "newaddr2", type: "addrColumn", width:300,
                cellRenderer: function(params){
                   if(params.data.gmjsna != null){
                        if(params.data.gmjjname != null){
                            return params.data.gmjsna + ' ' + params.data.gmjjname
                        }else {
                            return params.data.gmjsna
                        }
                   }else{
                    return params.data.newaddr2
                   }
                }
            }
          ]
        },
        { headerName: "소유자 주민번호", field: "gmjjumin", width:150, cellStyle: {textAlign: "center"},
            cellRenderer: function(params){
                if (params.data.gmjjumin != null) {
                    if (params.data.gmjjumin.indexOf('-') !== -1) {
                        return params.data.gmjjumin;
                    } else {
                        return params.data.gmjjumin.substring(0, 6) + "-" + params.data.gmjjumin.substring(6, 13);
                    }
                } else {
                    return '-'
                }
            }
        },
        { headerName: "이전 소유자", field: "jgmjname", width:120, cellStyle: {textAlign: "center"} },
        { headerName: "접수일자", field: "sujilja", width:120, cellStyle: {textAlign: "center"},
            valueFormatter: function (params) {
                return moment(params.value).format('yyyy-MM-DD');
            }
        },
        { headerName: "처리일자", field: "crilja", width:120, cellStyle: {textAlign: "center"},
            valueFormatter: function (params) {
                return moment(params.value).format('yyyy-MM-DD');
            }
        }
    ],
    defaultColDef: {
        headerClass: ["center", "justCenter"], cellClass: ["center", "justCenter"],
        sortable: true,
        resizable: true,
    },
    domLayout: 'autoHeight',
    pagination: true,
    paginationPageSize: 10,
    onCellClicked: (params) => {
        if(params.colDef.field === 'gmjname') ownerChange(params.data)
    },
    onGridReady: function (event) {
        // 행 넓이 자동 조절
        event.api.sizeColumnsToFit();
    }
};




// 등록/수정 현소유자현황
const addNowOwnerGridOptions ={
    columnDefs : [
        { headerName: "No.", field: "rn", cellStyle: {textAlign: "center"}},
        { headerName: "성명", field: "gmjname", cellStyle: {textAlign: "center", 'text-decoration': 'underline', cursor: 'pointer'} },
        { headerName: "소유자 주소",
          groupId: 'buildAddr',
          children: [
            { headerName: "도로명주소", field: "newaddr1", type: "addrColumn", width:300,
                 cellRenderer: function(params){
                    var newaddr1;
                    if(params.data.newaddr1 == null){
                        newaddr1 = params.data.newAddr1;
                    }else {
                         newaddr1 = params.data.newaddr1;
                    }
                    return newaddr1;
                 }
            },
            { headerName: "지번주소", field: "newaddr2", type: "addrColumn", width:300,
                cellRenderer: function(params){
                    let newAddr2;
                    if (params.data.gmjsna != null){
                        newAddr2 = params.data.gmjsna + ' ' + `${params.data.gmjjname != null ? params.data.gmjjname : ''}`;
                    } else {
                        /* 화면에 따라서 데이터를 넣는 필드명이 다르다.
                           변경신고: newAddr2
                           변경신고 수정: newaddr2 */
                        newAddr2 = params.data.newaddr2 != null
                            ? params.data.newaddr2
                            : params.data.newAddr2;
                    }
                    return newAddr2;
                }
            }
          ]
        },
        { headerName: "현 소유자 주민번호", field: "gmjjumin", cellStyle: {textAlign: "center"},
            cellRenderer: function(params){
                if (params.data.gmjjumin.indexOf('-') != -1) {
                     return params.data.gmjjumin;
                }else{
                      return params.data.gmjjumin.substring(0, 6) + "-" + params.data.gmjjumin.substring(6, 13);
                }
            }
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
    onCellClicked: (params) => {
        var data = params.data;
        const form = $('#ownerChangeFindForm')[0];

        const url = new URL(window.location.href);
        const urlGmbunho = url.searchParams.get('gmbunho');
        const urlAddFlage = url.searchParams.get('addFlage');

        if (urlGmbunho == null) {
            if (urlAddFlage === 'change') {
                // - 변경신고 -
                $('.existName').text(data.gmjname);
                $('#jgmjname').text(data.gmjname);
                // form.jgmjname.value = data.gmjname;
                $('.existJumin1').text(data.gmjjumin.substring(0, 6));
                $('.existJumin2').text(data.gmjjumin.substring(7, 14));
                form.jgmjjumin.value = data.gmjjumin.substring(0, 6) + '-' + data.gmjjumin.substring(7, 14);

                if (data.newaddr1 == null) {
                    $('.existRoad').text(data.newAddr1);
                } else {
                    $('.existRoad').text(data.newaddr1);
                }
                // gmjsna(지번주소)가 없을 경우, newAddr2로
                // gmjjname( 지번 + 부번 + 건물명 + 상세주소 ) 가 없을경우 gmjsna만 기입
                if(data.gmjsna != null){
                    $('.existjibun').text(data.gmjjname != null ? data.gmjsna + ' ' + data.gmjjname : data.gmjsna);
                    form.jgmjsna.value = data.gmjjname != null ? data.gmjsna + ' ' + data.gmjjname : data.gmjsna;
                }else{
                    $('.existjibun').text(data.newAddr2);
                    form.jgmjsna.value = data.newaddr2;
                }
                $('input[name=suilno]').val(data.suilno);
                $('input[name=gmjilno]').val(data.gmjilno);
                $('input[name=jnewAddr1]').val(data.newAddr1);
                $('input[name=jnewAddr2]').val(data.newAddr2);
                // 변경신고할시 현재소유자는 전소유자가 되어야해서 input에 현재 소유자 정보를 기입
                form.jgmjjgn.value = data.gmjjgn;
                form.jgmjzip1.value = data.gmjzip1;
                form.jgmjzip2.value = data.gmjzip2;
                form.jgmjname.value = data.gmjname;
            } else {
                // - 병합신고 -
                // form.gmjname.value = data.gmjname
                // form.gmjjgnTemp.value = data.gmjjgn == null ? '1': data.gmjjgn
                // form.gmjjumin1.value = data.gmjjumin1
                // form.gmjjumin2.value = data.gmjjumin2
                // form.newaddr1.value = data.newAddr1
                // form.newaddr2.value = data.newAddr2
            }
        } else {
            // - 수정 -
            $('input[name=suilno]').val(data.suilno);
            $('input[name=gmjilno]').val(data.gmjilno);
            // 성명
            form.gmjname.value = data.gmjname
            // 주민번호
            form.gmjjgnTemp.value = data.gmjjgn
            form.gmjjumin1.value = data.gmjjumin.substring(0, 6)
            form.gmjjumin2.value = data.gmjjumin.substring(7, 14)
            // 소유자주소
            form.newaddr1.value = data.newaddr1
            form.newaddr2.value = data.newaddr2
        }

            /*
        if(params.event.target.dataset.action){
            let action  = params.event.target.dataset.action;
            if(action == 'changeAdd'){
                location.href="/unlcebldmng/ownerChangeAdd?gmbunho="+params.data.skkseqno1;
            }
        }
        */
    },
    onGridReady: function (event) {
        // 행 넓이 자동 조절
        event.api.sizeColumnsToFit();
    }
};

 // setup the grid after the page has finished loading
 document.addEventListener('DOMContentLoaded', () => {
  if(ownerChangeObj == ""){
    var gridDiv = document.querySelector('#myGrid');
    new agGrid.Grid(gridDiv, gridOptions);
  }else if(addNowOwnerGridObj != "" && addNowOwnerGridObj != 'false'){
    var addNowOwnerGridDetail = document.querySelector('#nowOwnerGrid');
    new agGrid.Grid(addNowOwnerGridDetail, addNowOwnerGridOptions);
  }else{
    var ownerGridDetail = document.querySelector('#ownerGrid');
    new agGrid.Grid(ownerGridDetail, ownerGridOptions);

    var ownerChangeReasonGridDetail = document.querySelector('#ownerChangeReasonGrid');
    new agGrid.Grid(ownerChangeReasonGridDetail, ownerChangeReasonGridOptions);

    var ownerChangeDetail = document.querySelector('#ownerChangeGrid');
    new agGrid.Grid(ownerChangeDetail, ownerChangeGridOptions);
  }
 });

 const ownerChange = (data) => {
    /*
    detailUrl = `/unlcebldmng/ownerChangeDetail?gmbunho=${gmbunho}`;
     // 새 탭으로 상세 페이지 열기
     window.open(detailUrl, '_blank');
     */
     $(".gmbunho").val(data.skkseqno1);
     $(".detailCrilja").text(data.crilja);
     $(".detailSujilja").text(data.sujilja);
     $(".detailBksu").text(data.bksu);
     // 신고필증번호
     $(".detailSgpno").text(data.sgpjno);
     // 매매가격
     $(".detailSqlesprice").text(data.salesprice);
     // 신고필증 신고일자
     $(".detailSgpjdate").text(data.sgpjdate);
     // 비고
     $(".bigo").text(data.bigo);
     // 발급기관
     $(".detailIssueorgkey").text(data.issueorgkey);
     // $("input[name=suilno]").val(data.suilno);
     // $("input[name=gmjilno]").val(data.gmjilno);
     $(".suilno").val(data.suilno);
     $(".gmjilno").val(data.gmjilno);
     $("#selectedOwner").text(data.gmjname)

     /*
    $.post({
        url: "/api/unlicensedBuilingManagement/search",
        data: JSON.stringify($("#ledgerFindForm").serializeObject()),
        contentType: 'application/json; charset=utf-8'}).done(e=>{
            $('#totCnt > span').text(e.length);
            gridOptions.api.setRowData(e)
        });
    */

    $('#user-files').html("");
    fileTest(data);
 }
 /*
 function onBtnExportDataAsCsv() {
    gridOptions.api.exportDataAsCsv();
  }
  */
  /*function actionCellRenderer(params) {
    let eGui = document.createElement("div");
    // checks if the rowIndex matches in at least one of the editing cells
    eGui.innerHTML = '<button type="button" id="addOwnerChangeBtn" class="deleteBtn btn btn-secondary btn-sm" data-action="changeAdd">변경신고</button>';
    return eGui;
  }*/
// agGrid 행 조절
function onPageSizeChanged() {
  var value = document.getElementById('page-size').value;
  gridOptions.api.paginationSetPageSize(Number(value));
}
  // 주소데이터 받기
  function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn,
     detBdNmList, bdNm, bdKdcd, siNm, sggNm, emdNm, liNm, rn, udrtYn, buldMnnm, buldSlno, mtYn, lnbrMnnm, lnbrSlno, emdNo){
     $('input[name=newaddr1]').val(roadFullAddr);
     $('input[name=newaddr2]').val(jibunAddr);
     $('input[name=gmjsna]').val(jibunAddr);
     $('input[name=gmskkcd]').val(admCd.substring(0, 5));
     $('input[name=gmbjdcd]').val(admCd.substring(5, 10));
     if(mtYn == "0"){ // 대지 산
        $('input[name=gmcsahTemp]').val("대지");
     }else{
        $('input[name=gmcsahTemp]').val("산");
     }
     $('input[name=gmcsah]').val(mtYn);
     $('input[name=gmcbuh]').val(lnbrMnnm);
     $('input[name=gmcji]').val(lnbrSlno);
     $('input[name=naEtc]').val(addrDetail);

     // console.log('도로 코드: ', rnMgtSn)
     // console.log('도로 명: ', rn)
     // console.log('본 번: ', buldMnnm)
     // console.log('부 번: ', buldSlno)
     // console.log('시군구코드: ', admCd.substring(0, 5))
  }


     async function fileTest(data){
        uploader.parentKey = data.skkseqno2;
            await uploader.loadFiles();
            uploader.getFiles().forEach(file=>{
                let $link = $("<a>")
                    .text(`${file.name} (${file.vsize})`)
                    .attr("title", file.name)
                    .attr("href", `${file.name}`)
                    .data("fseq", file.fseq)
                    .data("seq", file.seq)
                    .on("click", (e)=>{
                        e.preventDefault();
                        let seq = $(e.target).data("seq");
                        let fseq =$(e.target).data("fseq");

                        // 이미 파일명/확장자명을 알기때문에 전달해주면 될 듯
                        uploader.downloadFile(file);
                    });
                $("<li class='list-inline-item'>")
                    .append($link)
                    .appendTo($("#user-files"));
            });
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
        const sujiljaSelect = document.getElementById('sujiljaCalendarSelect');
        const sujiljaRemote = document.getElementById('sujiljaCalendarRemote');
        const criljaSelect = document.getElementById('criljaCalendarSelect');
        const criljaRemote = document.getElementById('criljaCalendarRemote');
        const sujilja1Date = document.getElementById('sujilja1');
        const sujilja2Date = document.getElementById('sujilja2');
        const crilja1Date = document.getElementById('crilja1');
        const crilja2Date = document.getElementById('crilja2');

        function calendarRemoteInit (val) {
            if(val == "sujilja"){
                sujiljaRemote.innerHTML = '';
                sujilja1Date.value = '';
                sujilja2Date.value = '';
            }
            if(val == "crilja"){
                criljaRemote.innerHTML = '';
                crilja1Date.value = '';
                crilja2Date.value = '';
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

            buttonToday.className = "col-3 w60 ms-1 btn btn-outline-dark";
            buttonWeek.className = "col-3 w60 ms-1 btn btn-outline-dark";
            buttonMonth.className = "col-3 w60 ms-1 btn btn-outline-dark";
            comment.className = "col-8 ms-5 hstack";

            buttonToday.innerText = "오늘";
            buttonWeek.innerText = "1주일";
            buttonMonth.innerText = "1개월";

            buttonToday.type = "button";
            buttonWeek.type = "button";
            buttonMonth.type = "button";

            if(val === "sujilja"){
                buttonBox.appendChild(buttonDiv);
                buttonBox.appendChild(comment);
                sujiljaRemote.appendChild(buttonBox);
             }
            if(val === "crilja"){
                buttonBox.appendChild(buttonDiv);
                buttonBox.appendChild(comment);
                criljaRemote.appendChild(buttonBox);
            }
            buttonToday.addEventListener('click', function() {
                const today = new Date();
                const todayStr = today.toISOString().split('T')[0];
                comment.innerText = `※ ${todayStr}`;
                var nodeId = buttonToday.parentNode.parentNode.parentNode.getAttribute("id");
                if(nodeId == "sujiljaCalendarRemote"){
                    sujilja1Date.value = moment(todayStr).format('YYYYMMDD');
                    sujilja2Date.value = moment(todayStr).format('YYYYMMDD');
                }
                if(nodeId == "criljaCalendarRemote"){
                    crilja1Date.value = moment(todayStr).format('YYYYMMDD');
                    crilja2Date.value = moment(todayStr).format('YYYYMMDD');
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
                if(nodeId == "sujiljaCalendarRemote"){
                    sujilja1Date.value = moment(weekAgoDayStr).format('YYYYMMDD');
                    sujilja2Date.value = moment(todayStr).format('YYYYMMDD');
                }
                if(nodeId == "criljaCalendarRemote"){
                    crilja1Date.value = moment(weekAgoDayStr).format('YYYYMMDD');
                    crilja2Date.value = moment(todayStr).format('YYYYMMDD');
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
                var nodeId = buttonToday.parentNode.parentNode.parentNode.getAttribute("id");
                if(nodeId == "sujiljaCalendarRemote"){
                    sujilja1Date.value = moment(monthAgoDayStr).format('YYYYMMDD');
                    sujilja2Date.value = moment(todayStr).format('YYYYMMDD');
                }
                if(nodeId == "criljaCalendarRemote"){
                    crilja1Date.value = moment(monthAgoDayStr).format('YYYYMMDD');
                    crilja2Date.value = moment(todayStr).format('YYYYMMDD');
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

            if(val == "sujilja"){
                 const dateSujilja1 = document.createElement("input");
                 const dateSujilja2 = document.createElement("input");

                dateSujilja1.onkeydown = function() {return false;};
                dateSujilja2.onkeydown = function() {return false;};

                 dateDiv.appendChild(dateSujilja1);
                 dateDiv.appendChild(dateSpan);
                 dateDiv.appendChild(dateSujilja2);
                sujiljaRemote.appendChild(dateDiv);
                dateSujilja1.id = 'dateSujilja1';
                dateSujilja2.id = 'dateSujilja2';
                dateSujilja1.className = "col-3";
                dateSpan.className = "col-1 ms-2 text-center";
                dateSujilja2.className = "col-3";
                dateSujilja1.type = 'date';
                dateSujilja2.type = 'date';
                dateSujilja1.value = dayAgoDayStr;
                dateSujilja2.value = todayStr;
                sujilja1Date.value = moment(dayAgoDayStr).format('YYYYMMDD');
                sujilja2Date.value = moment(todayStr).format('YYYYMMDD');
                dateSujilja1.addEventListener('input', function () {
                    sujilja1Date.value = moment(dateSujilja1.value).format('YYYYMMDD');
                });
                dateSujilja2.addEventListener('input', function () {
                    sujilja2Date.value = moment(dateSujilja2.value).format('YYYYMMDD');
                });
            }
            if(val == "crilja"){
                const dateCrilja1 = document.createElement("input");
                const dateCrilja2 = document.createElement("input");

                dateCrilja1.onkeydown = function() {return false;};
                dateCrilja2.onkeydown = function() {return false;};

                dateDiv.appendChild(dateCrilja1);
                dateDiv.appendChild(dateSpan);
                dateDiv.appendChild(dateCrilja2);
                criljaRemote.appendChild(dateDiv);
                dateCrilja1.id = 'dateCrilja1';
                dateCrilja2.id = 'dateCrilja2';
                dateCrilja1.className = "col-3";
                dateSpan.className = "col-1 ms-2 text-center";
                dateCrilja2.className = "col-3";
                dateCrilja1.type = 'date';
                dateCrilja2.type = 'date';
                dateCrilja1.value = dayAgoDayStr;
                dateCrilja2.value = todayStr;
                crilja1Date.value = moment(dayAgoDayStr).format('YYYYMMDD');
                crilja2Date.value = moment(todayStr).format('YYYYMMDD');
                dateCrilja1.addEventListener('input', function () {
                     crilja1Date.value = moment(dateCrilja1.value).format('YYYYMMDD');
                });
                dateCrilja2.addEventListener('input', function () {
                     crilja2Date.value = moment(dateCrilja2.value).format('YYYYMMDD');
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

        if(sujiljaSelect != null && sujiljaRemote != null && sujilja1Date != null && sujilja2Date != null) {
            sujiljaSelect.addEventListener('change', function () { setCalendar(sujiljaSelect.value, "sujilja") });
            setCalendar(sujiljaSelect.value, "sujilja");
            if(sujilja1Date.getAttribute('sujilja1Date') || document.getElementById('dateSujilja1') != null) document.getElementById('dateSujilja1').value = dateSujilja1.getAttribute('sujilja1Date');
            if(sujilja2Date.getAttribute('sujilja2Date') || document.getElementById('dateSujilja2') != null) document.getElementById('dateSujilja2').value = dateSujilja2.getAttribute('sujilja2Date');
        }

        if(criljaSelect != null && criljaRemote != null && crilja1Date != null && crilja2Date != null) {
             criljaSelect.addEventListener('change', function () { setCalendar(criljaSelect.value, "crilja") });
             setCalendar(criljaSelect.value, "crilja");
             if(crilja1Date.getAttribute('crilja1Date') || document.getElementById('dateCrilja1') != null) document.getElementById('dateCrilja1').value = dateCrilja1.getAttribute('crilja1Date');
             if(crilja2Date.getAttribute('crilja2Date') || document.getElementById('dateCrilja2') != null) document.getElementById('dateCrilja2').value = dateCrilja2.getAttribute('crilja2Date');
        }
    })

    return {

    }
})();

function validation(forms) {
    Array.from(forms).forEach(form => {
        document.querySelectorAll('[readonly]').forEach((el) => {
            if (el.value === '') {
                el.classList.remove('is-valid');
                el.classList.add('is-invalid')
            } else {
                el.classList.remove('is-invalid');
                el.classList.add('is-valid')
            }
        })
        if (!form.checkValidity()) {
            event.preventDefault();
            event.stopPropagation();
            form.classList.add('was-validated');
            return validationFlag = false;
        } else {
            return validationFlag = true;
        }
    });
}
