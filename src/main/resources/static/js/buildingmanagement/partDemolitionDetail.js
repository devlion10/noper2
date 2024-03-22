$(document).ready(function () {
    $('#desau').summernote({
        height: 300,                 // 에디터 높이
        minHeight: null,             // 최소 높이
        maxHeight: null,             // 최대 높이
        focus: true,                  // 에디터 로딩후 포커스를 맞출지 여부
        lang: "ko-KR",					// 한글 설정
        placeholder: '최대 2048자까지 쓸 수 있습니다'	//placeholder 설정
    });

    compensationGridOptions.api.setRowData(partDemolitionCompensationList);
    tenantGridOptions.api.setRowData(partDemolitionTenantList);

    keyEventUtil.setInputTypeByForm("partDemolitionCompensationForm");
    keyEventUtil.setInputTypeByForm("partDemolitionTenantForm");
});

// 건물주 보상 현황 삭제
class cpsDeleteRenderer {
    // gets called once before the renderer is used
    init(params) {
        // create the cell
        this.deleteDiv = document.createElement('div');
        this.deleteDiv.innerHTML = `
          <span>
              <button class="deleteBtn btn btn-danger btn-sm">삭제</button>
          </span>
       `;

        // get references to the elements we want
        this.deleteBtn = this.deleteDiv.querySelector('.deleteBtn');

        // ajax 실행
        this.eventListener = () => this.compensationDelete(params);
        this.deleteBtn.addEventListener('click', this.eventListener);
    }

    compensationDelete(params) {
        let gmjilno = params.data.gmjilno;
        let gmjname = params.data.gmjname;

        $("#gmjilnoCpsDelIp").val(gmjilno);

        let partDemolitionCompensationDeleteForm = $("#partDemolitionCompensationDeleteForm").serializeObject();

        let url = "/api/unlicensedBuilingManagement/partDemolition/detail/compensationDelete";
        let type = "post";
        let json = JSON.stringify(partDemolitionCompensationDeleteForm);
        let contentType = "application/json";


        snAlert.confirm(gmjname + " 건물주 보상 정보를 삭제하시겠습니까?").then((resultChk) => {
            if (resultChk.isConfirmed) {
                ajaxCall(url, type, json, contentType, function (result) {
                    if (result.length != 0) {
                        snAlert.alert(gmjname + " 건물주 보상 정보가 삭제되었습니다.")
                        compensationGridOptions.api.setRowData(result);
                    }
                });
            }
        });
    }

    getGui() {
        return this.deleteDiv;
    }
}

// 세입자 정보 삭제
class tenantDeleteRenderer {
    // gets called once before the renderer is used
    init(params) {
        // create the cell
        this.deleteDiv = document.createElement('div');
        this.deleteDiv.innerHTML = `
          <span>
              <button class="deleteBtn btn btn-danger btn-sm">삭제</button>
          </span>
       `;

        // get references to the elements we want
        this.deleteBtn = this.deleteDiv.querySelector('.deleteBtn');

        // ajax 실행
        this.eventListener = () => this.tenantDelete(params);
        this.deleteBtn.addEventListener('click', this.eventListener);
    }

    tenantDelete(params) {
        let seipilno = params.data.seipilno;
        let sename = params.data.sename;

        $("#seipilnoTntDelIp").val(seipilno);

        let partDemolitionTenantDeleteForm = $("#partDemolitionTenantDeleteForm").serializeObject();

        let url = "/api/unlicensedBuilingManagement/partDemolition/detail/tenantDelete";
        let type = "post";
        let json = JSON.stringify(partDemolitionTenantDeleteForm);
        let contentType = "application/json";


        snAlert.confirm(sename + " 세입자 보상 정보를 삭제하시겠습니까?").then((resultChk) => {
            if (resultChk.isConfirmed) {
                ajaxCall(url, type, json, contentType, function (result) {
                    if (result.length != 0) {
                        snAlert.alert(sename + " 세입자 보상 정보가 삭제되었습니다.")
                        tenantGridOptions.api.setRowData(result);
                    }
                });
            }
        });
    }

    getGui() {
        return this.deleteDiv;
    }
}

const rowData = [{}];
// 건물주보상현황 그리드 옵션
const compensationGridOptions = {
    columnDefs: [
        {headerName: "소유자", field: "gmjname", cellStyle: {textAlign: "center"},cellClass:["underLine"], width: 105},
        {
            headerName: "소유자 주소", cellStyle: {textAlign: "center"}, groupId: "juso",
            children: [
                {headerName: "도로명주소", field: "gmjNewAddr1", cellStyle: {textAlign: "center"}, width: 270},
                {headerName: "지번주소", field: "gmjNewAddr2", cellStyle: {textAlign: "center"}, width: 270}
/*
                {headerName: "도로명주소", field: "gmjNewAddr1", cellStyle: {textAlign: "left"}, width: 270},
                {headerName: "지번주소", field: "gmjNewAddr2", cellStyle: {textAlign: "left"}, width: 270}
*/
            ]
        },
        {headerName: "소유자 주민번호", field: "gmjjumin", cellStyle: {textAlign: "center"}, width: 150 },
        {headerName: "보상금 지급일", field: "gmjbsiljaV", cellStyle: {textAlign: "center"}, width: 120 },
        {headerName: "보상금 지급액", field: "gmjbsgum", cellStyle: {textAlign: "right"}, width: 120 },
        {headerName: "입주권 지급일", field: "gmjjbiljaV", cellStyle: {textAlign: "center"}, width: !canDelete ? 200 : 120 },
        {headerName: "삭제", field: "deleteCps", cellStyle: {textAlign: "center"},
            width: 80,
            cellRenderer: cpsDeleteRenderer,
            hide: !canDelete
        },
    ],
    defaultColDef: {
        sortable: true,
        editable: false,
        floatingFilter: false,
        resizable: true,
        cellDataType: false,
        editable: false,
        enableRowGroup: false,
        enablePivot: false,
        enableValue: false,
    },

    pagination: true,
    paginationPageSize: 10,
    rowData: rowData,
    onCellClicked: (params) => {
        if(params.colDef.field === 'gmjname') {
            let gmjilno = params.data.gmjilno;
            let gmbiljaV = params.data.gmbiljaV;
            let gmbsdong = params.data.gmbsdong;
            let gmbfoors = params.data.gmbfoors;
            let gmbmjuk = params.data.gmbmjuk;
            let gmbgunp = params.data.gmbgunp;
            let gmbtojimjuk = params.data.gmbtojimjuk;
            let gmbtojimj = params.data.gmbtojimj;
            let gmbsau = params.data.gmbsau;
            let gmjname = params.data.gmjname;
            let gmjjgn = params.data.gmjjgn;
            let gmjjumin1 = params.data.gmjjumin1;
            let gmjjumin2 = params.data.gmjjumin2;
            // let gmjNewAddr1 = params.data.gmjNewAddr1;
            // let gmjNewAddr2 = params.data.gmjNewAddr2;
            let gmskkcd = params.data.gmskkcd;
            let gmbjdcd = params.data.gmbjdcd;
            let gmjsna = params.data.gmjsna;
            let gmcsah = params.data.gmcsah;
            let gmcbuh = params.data.gmcbuh;
            let gmcji = params.data.gmcji;
            let gmjbsiljaV = params.data.gmjbsiljaV;
            /*
            if(params.data.gmjbsiljaV != null){
                gmjbsiljaV = moment(params.data.gmjbsilja).format('yyyy-MM-DD');
            }
            */
            let gmjbsgum = params.data.gmjbsgum;
            let gmjjbilja = params.data.gmjjbilja;
            // if(gmjjbilja != ""){
            //     gmjjbilja = gmjjbilja.format('YYYY-MM-DD');
            // }
            let gmjigu = params.data.gmjigu;
            let gmjjbph = params.data.gmjjbph;
            let bidak = params.data.bidak;
            let issueorgkey = params.data.issueorgkey;
            let gmjzip1 = params.data.gmjzip1;
            let gmjzip2 = params.data.gmjzip2;
            let gmjjname = params.data.gmjjname;
            let naBjdongNo = params.data.naBjdongNo;
            let naRoadNm = params.data.naRoadNm;
            let naMainBun = params.data.naMainBun;
            let naSubBun = params.data.naSubBun;
            let naEtc = params.data.naEtc;
            let naSigunguCdOwner = params.data.naSigunguCdOwner;
            let gmjjumin = params.data.gmjjumin;

            $("#gmjilno").val(gmjilno);
            $("#gmbilja").val(gmbiljaV);
            $("#gmbsdongTemp").val(gmbsdong);
            $("#gmbfoorsTemp").val(gmbfoors);
            $("#gmbmjukTemp").val(gmbmjuk);
            $("#gmbgunpTemp").val(gmbgunp);
            $("#gmbtojimjukTemp").val(gmbtojimjuk);
            $("#gmbtojimjTemp").val(gmbtojimj);
            $("#gmbsauTemp").val(gmbsau);
            $("#gmjnameCps").val(gmjname);
            $("#gmjjgnCps").val(gmjjgn);
            $("#gmjjumin1Cps").val(gmjjumin1);
            $("#gmjjumin2Cps").val(gmjjumin2);
            // $("#buildingNewAddr1").val(gmjNewAddr1);
            // $("#buildingNewAddr2").val(gmjNewAddr2);
            $("#gmskkcd").val(gmskkcd);
            $("#gmbjdcd").val(gmbjdcd);
            $("#gmjsna").val(gmjsna);
            $("#gmcsah").val(gmcsah);
            $("#gmcbuh").val(gmcbuh);
            $("#gmcji").val(gmcji);
            $("#gmjbsiljaCps").val(gmjbsiljaV);
            $("#gmjbsgumCps").val(gmjbsgum);
            $("#gmjjbiljaCps").val(gmjjbilja);
            $("#gmjiguCps").val(gmjigu);
            $("#gmjjbphCps").val(gmjjbph);
            $("#bidakCps").val(bidak);
            $("#issueorgkeyRpt").val(issueorgkey);
            $("#gmjzip1").val(gmjzip1);
            $("#gmjzip2").val(gmjzip2);
            $("#gmjjname").val(gmjjname);
            $("#naBjdongNo").val(naBjdongNo);
            $("#naRoadNm").val(naRoadNm);
            $("#naMainBun").val(naMainBun);
            $("#naSubBun").val(naSubBun);
            $("#naEtc").val(naEtc);
            $("#naSigunguCdOwner").val(naSigunguCdOwner);
            $("#gmjjuminCps").val(gmjjumin);
        }
    },
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

// 세입자현황 그리드 옵션
const tenantGridOptions = {
    columnDefs: [
        {headerName: "세입자", field: "sename", cellStyle: {textAlign: "center"}, width: 100},
        {headerName: "아파트명", field: "sejaptname", cellStyle: {textAlign: "left"}, width: 285},
        {headerName: "아파트 지구", field: "sejigu", cellStyle: {textAlign: "center"}, width: 120},
        {headerName: "동호수", field: "sejdongho", cellStyle: {textAlign: "center"}, width: 120},
        {headerName: "세입자 주민번호", field: "sejjumin", cellStyle: {textAlign: "center"}, width: 150},
        {headerName: "보상금 지급일", field: "sebsiljaV", cellStyle: {textAlign: "center"}, width: 120},
        {headerName: "보상금 지급액", field: "sebsgum", cellStyle: {textAlign: "right"}, width: 120},
        {headerName: "아파트 지급일", field: "sejaptiljaV", cellStyle: {textAlign: "center"}, width: !canDelete ? 220 : 120},
        {headerName: "삭제", field: "deleteTnt", cellStyle: {textAlign: "center"},
            width: 80,
            cellRenderer: tenantDeleteRenderer,
            hide: !canDelete
        }
    ],
    defaultColDef: {
        sortable: true,
        editable: true,
        floatingFilter: false,
        resizable: true,
        cellDataType: false,
        editable: false,
        enableRowGroup: false,
        enablePivot: false,
        enableValue: false,
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
    const compensationGridDiv = document.querySelector('#compensationGridDiv');
    const tenantGridDiv = document.querySelector('#tenantGridDiv');

    new agGrid.Grid(compensationGridDiv, compensationGridOptions);
    new agGrid.Grid(tenantGridDiv, tenantGridOptions);
});

// $(".nav-link").click(() => {
//     moveScroll();
// });
//
// function moveScroll(){
//     location.href="#statusBuildingOwnerTab";
// }

// 세입자현황 새로고침 버튼 클릭 시 form Reset
$("#tenantResetBtn").click(() => {
    document.getElementById("partDemolitionTenantForm").reset();
});

// 세입자현황 입력
$("#tenantAddBtn").click(() => {
    var form = $("#partDemolitionTenantForm");

    validation(form);
    if(validationFlag) {
        let sejjumin1 = $("#sejjumin1Tnt").val();
        let sejjumin2 = $("#sejjumin2Tnt").val();
        let sejjumin = sejjumin1 + '-' + sejjumin2;

        $("#sejjuminTnt").val(sejjumin);

        const partDemolitionTenantForm = $("#partDemolitionTenantForm").serializeObject();

        let url = "/api/unlicensedBuilingManagement/partDemolition/detail/tenantAdd";
        let type = "post";
        let json = JSON.stringify(partDemolitionTenantForm);
        let contentType = "application/json";

        snAlert.confirm("세입자현황을 추가하시겠습니까?").then((resultChk) => {
            if (resultChk.isConfirmed) {
                ajaxCall(url, type, json, contentType, function (result) {
                    if (result.length != 0) {
                        snAlert.alert("세입자보상현황이 추가되었습니다.")
                        tenantGridOptions.api.setRowData(result);
                        document.getElementById("partDemolitionTenantForm").reset();
                    }
                });
            }
        });
    }
});



// 건물주보상현황 입력 - 버튼 클릭 시 form Reset
$("#compensationResetBtn").click(() => {
    document.getElementById("partDemolitionCompensationForm").reset();
});

// 건물주보상현황 입력
$("#compensationAddBtn").click(() => {
    var form = $("#partDemolitionCompensationForm");

    validation(form);
    if(validationFlag) {

        let gmjjumin1 = $("#gmjjumin1Cps").val();
        let gmjjumin2 = $("#gmjjumin2Cps").val();
        let gmjjumin = gmjjumin1 + '-' +gmjjumin2;
        $("#gmjjuminCps").val(gmjjumin);

        let gmbgunp = $("#gmbgunpTemp").val();
        $("#gmbgunpTemp").val(gmbgunp);

        let gmbtojimj = $("#gmbtojimjTemp").val();
        $("#gmbtojimjTemp").val(gmbtojimj);

        const partDemolitionCompensationForm = $("#partDemolitionCompensationForm").serializeObject();
        let url = "/api/unlicensedBuilingManagement/partDemolition/detail/compensationAdd";
        let type = "post";
        let json = JSON.stringify(partDemolitionCompensationForm);
        let contentType = "application/json";

        snAlert.confirm("건물주보상현황을 추가하시겠습니까?").then((resultChk) => {
            if (resultChk.isConfirmed) {
                ajaxCall(url, type, json, contentType, function (result) {
                    if (result.length != 0) {
                        snAlert.alert("건물주보상현황이 추가되었습니다.")
                        compensationGridOptions.api.setRowData(result);
                        document.getElementById("partDemolitionCompensationForm").reset();
                    }
                });
            }
        });
    }
});

// 취소 버튼 클릭시
$("#cancelBtn").click(() => {
    snAlert.confirm("확인을 누르시면 입력한 정보가\n 모두 삭제됩니다.\n그래도 진행하시겠습니까?").then((resultChk) => {
        if (resultChk.isConfirmed) {
            $("#listBtn").click();
        }
    });
});

if (document.querySelector("#gmbmjukTemp") != null) {
    // 부분철거 건물면적 입력시 ㎡ -> 평 변환 입력
    document.querySelector("#gmbmjukTemp").onblur = function(){
        let gmbmjuk = $("#gmbmjukTemp").val();

        let gmbgunp = Number((gmbmjuk * 0.3025).toFixed(2));
        $("#gmbgunpTemp").val(gmbgunp);
    };
}

if (document.querySelector("#gmbtojimjukTemp") != null) {
    // 부분철거 토지면적 입력시 ㎡ -> 평 변환 입력
    document.querySelector("#gmbtojimjukTemp").onblur = function() {
        let gmbtojimjuk = $("#gmbtojimjukTemp").val();

        let gmbtojimj = Number((gmbtojimjuk * 0.3025).toFixed(2));
        $("#gmbtojimjTemp").val(gmbtojimj);
    };
}

// 주소찾기 btn 있을때만 동작
if(document.querySelector("#jusoBtn") != null){
    document.querySelector("#jusoBtn").addEventListener("click", (e)=>{
        const w = window.open('/common/juso', 'partDemolition', 'width=500px,height=500px');
        w.focus();
    });
}
/*
전체 도로명주소 : roadFullAddr,
도로명주소(참고항목 제외) : roadAddrPart1,
고객 입력 상세 주소 : addrDetail,
도로명주소 참고항목 : roadAddrPart2,
도로명주소(영문) : engAddr,
지번주소 : jibunAddr,
우편번호 : zipNo,
행정구역코드 : admCd,
도로명코드 : rnMgtSn,
건물관리번호 : bdMgtSn,
상세건물명 : detBdNmList,
건물명 : bdNm,
공동주택여부(1 : 공동주택, 0 : 비공동주택) : bdKdcd,
시도명 : siNm,
시군구명 : sggNm,
읍면동명 : emdNm,
법정리명 : liNm,
도로명 : rn,
지하여부(0 : 지상, 1 : 지하) : udrtYn,
건물본번 : buldMnnm,
건물부번 : buldSlno,
산여부(0 : 대지, 1 : 산) : mtYn,
지번본번(번지) : lnbrMnnm,
지번부번(호) : lnbrSlno,
읍면동일련번호 : emdNo
*/


// 주소데이터 받기
function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn,
                      detBdNmList, bdNm, bdKdcd, siNm, sggNm, emdNm, liNm, rn, udrtYn, buldMnnm, buldSlno, mtYn, lnbrMnnm, lnbrSlno, emdNo){
    $('input[name=newAddr1]').val(roadFullAddr);
    $('input[name=newAddr2]').val(jibunAddr + " " + addrDetail);
    $('input[name=gmskkcd]').val(admCd.substring(0, 5));
    $('input[name=gmbjdcd]').val(admCd.substring(5, 10));
    $('input[name=gmcsah]').val(mtYn);
    $('input[name=gmcbuh]').val(lnbrMnnm);
    $('input[name=gmcji]').val(lnbrSlno);

    // console.log($('input[name=gmskkcd]').val());
    // console.log($('input[name=gmbjdcd]').val());
    // console.log($('input[name=gmcsah]').val());
    // console.log($('input[name=gmcbuh]').val());
    // console.log($('input[name=gmcji]').val());
}

// // 보상금 콤마
// let gmjbsgumCpsInput = document.querySelector('#gmjbsgumCps');
// gmjbsgumCpsInput.addEventListener('keyup', function (e) {
//     let value = e.target.value;
//     value = Number(value.replaceAll(',', ''));
//     if(isNaN(value)) {
//         gmjbsgumCpsInput.value = 0;
//     } else {
//         const formatValue = value.toLocaleString('ko-KR');
//         gmjbsgumCpsInput.value = formatValue;
//     }
// });
//
// // 이사비용 콤마
// let seisgumTntInput = document.querySelector('#seisgumTnt');
// seisgumTntInput.addEventListener('keyup', function (e) {
//     let value = e.target.value;
//     value = Number(value.replaceAll(',', ''));
//     if(isNaN(value)) {
//         seisgumTntInput.value = 0;
//     } else {
//         const formatValue = value.toLocaleString('ko-KR');
//         seisgumTntInput.value = formatValue;
//     }
// });
//
// // 주거대책비 콤마
// let sebsgumTntInput = document.querySelector('#sebsgumTnt');
// sebsgumTntInput.addEventListener('keyup', function (e) {
//     let value = e.target.value;
//     value = Number(value.replaceAll(',', ''));
//     if(isNaN(value)) {
//         sebsgumTntInput.value = 0;
//     } else {
//         const formatValue = value.toLocaleString('ko-KR');
//         sebsgumTntInput.value = formatValue;
//     }
// });

// 신고서 버튼 클릭
$("#reportCallBtn").click(() => {
    let gmskk = $("#gmskk").val();
    let gmdjgb = $("#gmdjgb").val();
    let gmseqco = $("#gmseqco").val();
    let gmjilno = $("#gmjilno").val();
    let user = $("#userName").val();
    if ( gmjilno == 0 ){
        return snAlert.alert("신고서를 출력할 건물주보상현황을 먼저 선택하세요");
    }
    let data = "";

    if ( !isAdmin ){
        data = {
            seal : !isAdmin
            , gmskk: gmskk
            , gmdjgb: gmdjgb
            , gmseqco: gmseqco
            , gmjilno:gmjilno
            , user: user
        };
    } else {
        data = {
            gmskk: gmskk
            , gmdjgb: gmdjgb
            , gmseqco: gmseqco
            , gmjilno:gmjilno
            , user: user
        };
    }
    snReport.open('PART_REMVL_DCLRT', data);
});

// validation 확인
function validation(forms){
    Array.from(forms).forEach(form => {
        if (!form.checkValidity()) {
            console.log(!form.checkValidity());
            event.preventDefault();
            event.stopPropagation();
            form.classList.add('was-validated');
            return validationFlag = false;
        }else{
            return validationFlag = true;
        }
    })
};

// ajax 공통
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