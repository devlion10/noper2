$(document).ready(function(){
    var cnt = 0;

    const siteChkSelOjbect = "";
    if(noperOwnerObj != "undefined"){
        ownerGridOptions.api.setRowData(noperOwnerObj);
        if(noperOwnerObj.length > 0){
            $("#NoperOwnerTotCnt > span").text("Total "+ noperOwnerObj.length);
        }else{
            $("#NoperOwnerTotCnt > span").text("Total "+ cnt);
        }
    }
    if(noperOwnerChangeObj != "undefined"){
        ownerChangeGridOptions.api.setRowData(noperOwnerChangeObj);
        if(noperOwnerChangeObj.length > 0){
            $("#NoperOwnerChangeTotCnt > span").text(noperOwnerChangeObj.length);
        } else {
            $("#NoperOwnerChangeTotCnt > span").text(cnt);
        }
    }
    if(noperFixObj != "undefined"){
        noperFixGridOptions.api.setRowData(noperFixObj);
        if(noperFixObj.length > 0){
            $("#findnoperFixTotCnt > span").text(noperFixObj.length);
        } else {
            $("#findnoperFixTotCnt > span").text(cnt);
        }
    }

    if(noperSiteChkObj != "undefined"){
        if(noperSiteChkObj.violYn == "Y"){
            noperSiteChkObj.violYnTxt = "해당사항없음";
            noperSiteChkObj.trsctCntt = "해당사항없음";
        }
        siteChkGridOptions.api.setRowData(noperSiteChkObj);
        if(noperSiteChkObj.length > 0){
            $("#noperSiteChkTotCnt > span").text(noperSiteChkObj.length);
        }else{
            $("#noperSiteChkTotCnt > span").text(cnt);
        }
    }

});

// 건물주현황
const ownerGridOptions ={
    columnDefs : [
        { headerName: "No.", field: "rn", width: 70, cellStyle: {textAlign: "center"}, /*checkboxSelection: checkboxSelection,*/},
        { headerName: "건물주명", field: "gmjname", cellStyle: {textAlign: "center"} },
        { headerName: "주민번호", field: "gmjjumin", cellStyle: {textAlign: "center"},
            valueGetter: function (params) {
                let gmjjumin = '-'
                const gmjjuminLength = params.data.gmjjumin != null ? params.data.gmjjumin.length : 0
                if (gmjjuminLength === 13) {
                    // 13자리일 경우(하이픈이 없는 경우) 중간에 하이픈을 추가해줌
                    gmjjumin = params.data.gmjjumin.substring(0, 6) + "-" + params.data.gmjjumin.substring(6, 13);
                } else {
                    gmjjumin = gmjjuminNullCheck(params.data.gmjjumin1) + "-" + gmjjuminNullCheck(params.data.gmjjumin2);
                }
                return gmjjumin;
            }
        },
        { headerName: "건물주 주소", cellStyle: {textAlign: "center", width: 400},
          groupId: 'buildAddr', cellStyle: {textAlign: "center"},
          children: [
            { headerName: "도로명주소", field: "newAddr1", type: "addrColumn", cellStyle: {textAlign: "center"}, width:370 },
            { headerName: "지번주소", field: "newAddr2", type: "addrColumn", cellStyle: {textAlign: "center"}, width:370,
                valueGetter: function (params) {
                    if(params.data.gmjsna != null){
                        return params.data.gmjsna + params.data.gmjjname;
                    }else{
                        return params.data.newAddr2;
                    }
                }
            }
          ]
        }
    ],
    defaultColDef: {
        headerClass: ["center", "justCenter"],
        cellClass: ["center", "justCenter"],
        sortable: true,
        resizable: true,
    },
    pagination: true,
    paginationPageSize: 10,
    onGridReady: function (event) {
        // 행 넓이 자동 조절
        event.api.sizeColumnsToFit();
    }
};
// 소유자변경이력 현황
const ownerChangeGridOptions ={
    columnDefs : [
        { headerName: "No.", field: "rn", width:70, cellStyle: {textAlign: "center"}, /*checkboxSelection: checkboxSelection,*/},
        { headerName: "접수일자", field: "sujilja", width:120, cellStyle: {textAlign: "center"},
            valueGetter: function (params) {
                var sujilja = params.data.sujilja.slice(0, 4)+"-"+params.data.sujilja.slice(4, 6)+"-"+params.data.sujilja.slice(6, 8);
                return sujilja
            }
        },
        { headerName: "변경사유", field: "bksu", width:250, cellStyle: {textAlign: "center"}},
        { headerName: "처리일자", field: "crilja", width:120, cellStyle: {textAlign: "center"},
            valueGetter: function (params) {
                var crilja = params.data.crilja.slice(0, 4)+"-"+params.data.crilja.slice(4, 6)+"-"+params.data.crilja.slice(6, 8);
                return crilja
            }
        },
        { headerName: "현소유자", field: "gmjname", width:120, cellStyle: {textAlign: "center"} },
        { headerName: "현소유자 주민번호", field: "gmjjumin", width:160, cellStyle: {textAlign: "center"},
            valueGetter: function (params) {
                let gmjjumin = '-'
                const gmjjuminLength = params.data.gmjjumin != null ? params.data.gmjjumin.length : 0
                if (gmjjuminLength === 13) {
                    // 13자리일 경우(하이픈이 없는 경우) 중간에 하이픈을 추가해줌
                    gmjjumin = params.data.gmjjumin.substring(0, 6) + "-" + params.data.gmjjumin.substring(6, 13);
                } else {
                    gmjjumin = gmjjuminNullCheck(params.data.gmjjumin1) + "-" + gmjjuminNullCheck(params.data.gmjjumin2);
                }
                return gmjjumin;
            }
        },
        { headerName: "전소유자", field: "jgmjname", width:120, cellStyle: {textAlign: "center"}},
        { headerName: "전소유자 주민번호", field: "jgmjjumin", width:185, cellStyle: {textAlign: "center"},
            cellRenderer: function(params){
                let jgmjjumin = '-'
                const jgmjjuminLength = params.data.jgmjjumin != null ? params.data.jgmjjumin.length : 0
                if (jgmjjuminLength === 13) {
                    // 13자리일 경우(하이픈이 없는 경우) 중간에 하이픈을 추가해줌
                    jgmjjumin = params.data.jgmjjumin.substring(0, 6) + "-" + params.data.jgmjjumin.substring(6, 13);
                } else {
                    jgmjjumin = gmjjuminNullCheck(params.data.jgmjjumin1) + "-" + gmjjuminNullCheck(params.data.jgmjjumin2);
                }
                return jgmjjumin;
            }
        },
        /*{ headerName: "보고일자", field: "crilja", cellStyle: {textAlign: "center"}}*/
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
        // 행 넓이 자동 조절
        event.api.sizeColumnsToFit();
    }
};
// 주민번호 null 체크
const gmjjuminNullCheck = (e) => e != null ? e : ''

// 개보수신고내역 현황
const noperFixGridOptions ={
    suppressClickEdit: true,
    onCellClicked(params) {
        if(params.colDef.field === 'gmbalno'){
            const form = $('#ledgerFixAddForm')[0];
            form.gmbalno.value = params.data.gmbalno;
            form.gbsgoilTemp.value = moment(params.data.gbsgoil).format('YYYY-MM-DD');
            form.gmilno.value = params.data.gmilno;
            form.jgongTemp.value = moment(params.data.jgong).format('YYYY-MM-DD');
            form.wbgb.value = params.data.wbgb;
            form.gbscotent.value = params.data.gbscotent;
            form.chcontent.value = params.data.chcontent;
            form.hhgname.value = params.data.hhgname;
            form.sinda.value = params.data.sinda;
            form.jungon.value = params.data.jungon;
            form.hhname.value = params.data.hhname;
            form.sinda1.value = params.data.sinda1;
            form.jungon1.value = params.data.jungon1;
            $('#fixAddBtn').hide();
            $('#fixModyBtn').show();
        }
        if(params.colDef.headerName == '삭제'){
            $("input[name=gmilno]").val(params.data.gmilno);
            $("input[name=gmbalno]").val(params.data.gmbalno);
            $("input[name=fixParam]").val("Dell");
            $.post({
                url: "/api/unlicensedBuilingManagement/fixAction",
                data: JSON.stringify($("#ledgerFixAddForm").serializeObject()),
                contentType: 'application/json; charset=utf-8'}).done(e=>{
                  noperFixGridOptions.api.setRowData(e);
            });
        }
    },
    editType: "fullRow",
    columnDefs : [
        { headerName: "No.", field: "gmilno", width: 50, cellStyle: {textAlign: "center"}, /*checkboxSelection: checkboxSelection,*/},
        { headerName: "접수일자", field: "gbsgoil", width: 120, cellStyle: {textAlign: "center"} },
        { headerName: "접수번호", field: "gmbalno", width: 107, cellStyle: {textAlign: "center", 'text-decoration': 'underline', cursor: 'pointer'}},
        { headerName: "신고내용", field: "gbscotent", width: 315, cellStyle: {textAlign: "center"} },
        { headerName: "준공일자", field: "jgong", width: 120, cellStyle: {textAlign: "center"} },
        { headerName: "현장직명", field: "hhgname", width: 150, cellStyle: {textAlign: "center"} },
        { headerName: "현장성명", field: "hhname", width: !canUpdate ? 270 : 150, cellStyle: {textAlign: "center"} },
        { headerName: "삭제", width: 120, cellStyle: {textAlign: "center"},
          cellRenderer: actionCellRenderer,
          editable: false,
            hide: !canUpdate
        }
    ],
    defaultColDef: {
        headerClass: ["center", "justCenter"], cellClass: ["center", "justCenter"],
       // width: 160,
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
    }
};

// 현장정검내역 현황
const siteChkGridOptions ={
    columnDefs : [
        { headerName: "No.", field: "rn", width:80, cellStyle: {textAlign: "center"}, /*checkboxSelection: checkboxSelection,*/},
        { headerName: "위반여부", field: "violYnTxt", width:120, cellStyle: {textAlign: "center"} },
        { headerName: "위반내용", field: "violCntt", width:275, cellStyle: {textAlign: "center", 'text-decoration': 'underline', cursor: 'pointer'}},
        { headerName: "조치내용", field: "trsctCntt", width:275, cellStyle: {textAlign: "center"} },
        { headerName: "점검담당자", field: "chkChargeNm", width:150, cellStyle: {textAlign: "center"} },
        { headerName: "점검일자", field: "chkDate", width:150, cellStyle: {textAlign: "center"},
            valueFormatter: function (params) {
             return moment(params.value).format('yyyy-MM-DD');
            }
        },
        { headerName: "수정일자", field: "changDtime",  width:150, cellStyle: {textAlign: "center"},
            valueFormatter: function (params) {
                if(params.value === "-" || params.value === null){
                    return "-";
                }else{
                    return moment(params.value.substr(0, 8)).format('yyyy-MM-DD');
                }
            }
        },
        /*{ headerName: "승인여부", field: "confirm1", cellStyle: {textAlign: "center"} }*/
    ],
    defaultColDef: {
        headerClass: ["center", "justCenter"], cellClass: ["center", "justCenter"],
        sortable: true,
        resizable: true
    },
    pagination: true,
    paginationPageSize: 10,
    onCellClicked: (params) => {
        if(params.colDef.field === 'violCntt') siteModalDtail("sel", params)
    },
    onGridReady: function (event) {
        // 행 넓이 자동 조절
        event.api.sizeColumnsToFit();
    }
};


 // setup the grid after the page has finished loading
 document.addEventListener('DOMContentLoaded', () => {
   var gridDiv = document.querySelector('#ownerGrid');
   var gridDiv2 = document.querySelector('#ownerChangeGrid');
   var gridDiv3 = document.querySelector('#noperFixGrid');
   var gridDiv4 = document.querySelector('#imgFileGrid');
   var gridDiv5 = document.querySelector('#siteChkGrid');

   new agGrid.Grid(gridDiv,  ownerGridOptions);
   new agGrid.Grid(gridDiv2,  ownerChangeGridOptions);
   new agGrid.Grid(gridDiv3,  noperFixGridOptions);
   new agGrid.Grid(gridDiv5,  siteChkGridOptions);

 });

 const siteModalDtail = async (flag, params) => {
     $('.siteChkAtion').hide();
     $('.siteChkAtionNon').show();
     $('#trsctSiteChkSave').show();
     $('#trsctSiteChkMody').hide();
     await siteChkUpload(params.data.chkilno);
     let detailUrl = "";
     if(flag === "sel"){

         if(params.data.trsctDate != "undefined" || params.data.trsctDate != ""){
             $("#trsctSiteChkSave").text("조치사항 수정");
         }
         $(".modalBtn").click();
         const form = $('#ledgerFixAddForm')[0];
         siteChkSelOjbect  = params;
         $('.userInfo').text($('.bjdongNm').text()+" "+params.data.chkChargeClspos+" "+params.data.chkChargeNm);
         $('.trsctCnfmNm').text($('.bjdongNm').text()+" "+params.data.trsctCnfmClspos+" "+params.data.trsctCnfmNm);
         $('.confirm1').text(params.data.confirm1);
         $('.chkDateTemp').text(moment(params.data.chkDate).format('YYYY-MM-DD'));
         if(params.data.changDtime === "-"){
             $('.changDtime').text(params.data.changDtime);
         }else{
             $('.changDtime').text(moment(params.data.changDtime.substr(0, 8)).format('yyyy-MM-DD'));
         }
         $('.violYn').text(params.data.violYn);
         $('.violCntt').text(params.data.violCntt);
         $('.rem').text(params.data.rem);
         if(params.data.trsctCnfmNm == null){
             $('.trsctCnfmNm').text("-");
         }else{
             $('.trsctCnfmNm').text(params.data.trsctCnfmNm);
         }
         if(params.data.trsctDate == null){
             $('.trsctDate').text("-");
         }else{
             $('.trsctDate').text(moment(params.data.trsctDate.substr(0, 8)).format('yyyy-MM-DD'));
         }
         if(params.data.trsctDate == null){
             $('.trsctCntt').text("조치된 내용이 없습니다.");
         }else{
             $('.trsctCntt').text(params.data.trsctCntt);
         }
         // 임시로 조치 기관코드 적재
         $(".trsctChkChargeGmskk").val(params.data.gmskk);
         $(".trsctChkilno").val(params.data.chkilno);
         if(params.data.violYn === "N"){
             $('#trsctSiteChkSave').hide();
         }
         // 조치사항 직급
         $('input[name=trsctCnfmClspos]').val(params.data.trsctCnfmClspos)
         // 조치사항 조치일자
         const formatDate = moment(params.data.trsctDate).format('YYYY-MM-DD')
         const trsctDateTemp = $('input[name=trsctDateTemp]');
         trsctDateTemp.val(formatDate)
         if (params.data.trsctDate !== null || params.data.trsctDate !== '') trsctDateTemp[0].classList.remove('is-invalid')
         // 조치사항 조치내용
         const trsctCntt = $('textarea[name=trsctCntt]');
         trsctCntt.val(params.data.trsctCntt)
         if (params.data.trsctCntt !== null || params.data.trsctCntt !== '') trsctCntt[0].classList.remove('is-invalid')
     }
 }

 function onBtnExportDataAsCsv() {
 gridOptions.api.exportDataAsCsv();
}



function actionCellRenderer(params) {
  let eGui = document.createElement("div");
  // checks if the rowIndex matches in at least one of the editing cells
  eGui.innerHTML = '<button type="button" id="fixListDelBtn" class="btn btn-sm btn-danger" data-action="delete">삭제</button>';
  return eGui;
}

 async function siteChkUpload(chkilno){
      siteChkUploader.parentKey = seq;
      siteChkUploader.separator = "ledgerSiteChk";
      siteChkUploader.flag = chkilno;
      // 파일정보 로딩
      await siteChkUploader.loadFiles();
      $('#user-files2').html("");
      siteChkUploader.getFiles().forEach(file=>{
          let $link = $("<a>")
              .text(file.logicalFilename + "("+file.fileSize+")")
              .attr("title", file.logicalFilename)
              .attr("href", file.filePath+`\\`+file.physicalFilename)
              .data("fseq", file.fseq)
              .data("seq", file.gmskk+'-'+file.gmdjgb+'-'+file.gmseqco)
              .on("click", (e)=>{
                  e.preventDefault();
                  let seq = $(e.target).data("seq");
                  let fseq =$(e.target).data("fseq");
                  // 이미 파일명/확장자명을 알기때문에 전달해주면 될 듯
                  if (file.fileEnd.match(/jpg|jpeg|png|gif/ig)) {
                      return $(`<a onclick='' href='javascript:;'>${file.name}</a>`)
                              .on("click", siteChkUploader.imgPreview(file))[0];
                  } else {
                      siteChkUploader.downloadFile(file);
                  }
              });
          let $deleteLink = $(`<a href="javascript:;" class="ms-2"><i class='fa fa-trash'></i></a>`);
          $deleteLink.on("click", (e) => {
              $(e.target).closest("li").remove()
              uploader.deleteFiles(file)
          });
          $("<li class='list-inline-item'>")
              .append($link)
              .appendTo($("#user-files2"));
      });
  }

