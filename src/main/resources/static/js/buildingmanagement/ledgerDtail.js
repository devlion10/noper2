var jusoFlag = "";
var validationFlag = false;
$(document).ready(function () {
    $('#fixModyBtn').hide();
    $('#listBtn').click(() => {
        moveMenu("ledger");
    });
    $('#newListBtn').click(() => {
        moveMenu("newLedger");
    });
    $('#resetBtn').click(() => {
        $("#ledgerAddForm")[0].reset();
    });
    $('#ledgerOwnerResetBtn').click(() => {
        $("#ledgerOwnerAddForm")[0].reset();
    });
    $('#fixResetBtn').click(() => {
        $("#ledgerFixAddForm")[0].reset();
        $("#fixAddBtn").show();
        $("#fixModyBtn").hide();
    });
    $('#siteChkResetBtn').click(() => {
        $("#siteChkAddForm")[0].reset();
    });
    $('.modalBtn').hide();
    $("#siteChkModyBtn").hide();
    $(".siteChkAtion").hide();
    $("#trsctSiteChkMody").hide();
    $(".siteChkFileHide").hide();
    if ($("input[name=trsctDate]").val() == "") {
        $("trsctSiteChkSave").show();
    }


    // 주소찾기 btn 있을때만 동작
    if (document.querySelector("#jusoBtn") != null) {
        document.querySelector("#jusoBtn").addEventListener("click", (e) => {
            jusoFlag = "ledger";
            const w = window.open('/common/juso', 'juso', 'width=500px,height=500px');
            w.focus();
        });
    }
    if (document.querySelector("#jusoBtn2") != null) {
        document.querySelector("#jusoBtn2").addEventListener("click", (e) => {
            jusoFlag = "owner";
            const w = window.open('/common/juso', 'juso', 'width=500px,height=500px');
            w.focus();
        });
    }

    // 현장점검 및 조치사항의 조치사항 Input 데이터 유효성검사
    const validEleList = [
        document.querySelector('input[name=trsctDateTemp]'),
        document.querySelector('textarea[name=trsctCntt]')
    ];
    if (validEleList[0] !== null && validEleList[1] !== null) {
        validEleList.forEach(e => {
            e.addEventListener('blur', () => {
                const targetNullCheck = e.value !== '' && e.value !== null
                if (targetNullCheck) {
                    e.classList.remove('is-invalid')
                } else {
                    e.classList.add('is-invalid')
                }
            })
        })
    }

    // 대장상세 기본정보 저장
    $('#noperSaveBtn').click(() => {
        if ($("input[name=decisionDate]").val() == "") {
            $("input[name=decision]").val("");
        } else {
            $("input[name=decision]").val(moment($("input[name=decisionDate]").val()).format('YYYYMMDD'));
        }
        if ($('#ledgerAddForm input[name=gmcsahTemp]').val() == "대지") {
            $('#ledgerAddForm input[name=gmcsah]').val("0");
        } else if ($('#ledgerAddForm input[name=gmcsahTemp]').val() == "산") {
            $('#ledgerAddForm input[name=gmcsah]').val("1");
        } else if ($('#ledgerAddForm input[name=gmcsahTemp]').val() == "블록") {
            $('#ledgerAddForm input[name=gmcsah]').val("2");
        } else {
            $('#ledgerAddForm input[name=gmcsah]').val("");
        }
        // 건축일자
        const replaceGmunil = $('input[name=gmgunilDate]').val().replace(/-/g, '')
        $('input[name=gmgunil]').val(replaceGmunil)

        $("input[name=tojimj]").attr("disabled", false);
        $("input[name=gmgunp]").attr("disabled", false);
        var form = $('#ledgerAddForm');
        validation(form);
        if (validationFlag) {
            snAlert.confirm("대장을 수정하시겠습니까?").then((resultChk) => {
                if (resultChk.isConfirmed) {
                    $.post({
                        url: "/api/unlicensedBuilingManagement/ledgerUpdate",
                        //data: JSON.stringify ({ledger:$("#ledgerAddForm").serializeObject(), owner: ownerFormData, fix: fixFormData}),
                        data: JSON.stringify({ledger: $("#ledgerAddForm").serializeObject()}),
                        contentType: 'application/json; charset=utf-8'
                    }).done(
                        e =>
                            snAlert.success("알림", "수정 완료").then((result) => {
                                /*if (result.isConfirmed) {
                                    location.href="/unlcebldmng/ledger";
                                }*/
                            })
                    );
                }
            });
        }
    });


    // 건물주 추가 btn(첫 등록만 가능)
    if (noperOwnerObj.length > 0) {
        $("#ledgerOwnerResetBtn").css("display", "none");
        $("#ledgerOwnerAddBtn").css("display", "none");
    }
    /*
    $('#ledgerDtaillSvaeBtn').click(() => {

        if($('ledgerAddForm input[name=gmcsahTemp]').val() == "대지"){
            $('ledgerAddForm input[name=gmcsah]').val("0");
        }else if($('ledgerAddForm input[name=gmcsahTemp]').val() == "산"){
            $('ledgerAddForm input[name=gmcsah]').val("1");
        }else{
            $('ledgerAddForm input[name=gmcsah]').val("");
        }

        $.post({
            url: "/api/unlicensedBuilingManagement/ledgerUpdate",
            data: JSON.stringify ({ledger:$("#ledgerAddForm").serializeObject(), owner: ownerFormData,
            fix: fixFormData}),
            contentType: 'application/json; charset=utf-8'}).done(
                e=>
                snAlert.success("알림", "등록 완료").then((result) => {
                      /*if (result.isConfirmed) {
                          location.href="/unlcebldmng/ledger";
                      }
                  })
            );
    });
    */
    // 건물주 저장
    var ownerFormData = new Array();
    ;
    var ownerFormDataCnt = 0;
    if (noperOwnerObj.length != 0) {
        ownerFormDataCnt = noperOwnerObj.length;
    } else {
        ownerFormDataCnt = 0;
    }
    $('#ledgerOwnerAddBtn').click(() => {
        /*
        // 클릭시 리스트에 추가 db 노 저장
        ownerFormDataCnt++;
        $("input[name=rn]").val(ownerFormDataCnt);
        ownerFormData.push($("#ledgerOwnerAddForm").serializeObject());
        $('input[name=gmjjumin]').val($("#ledgerOwnerAddForm").serializeObject().gmjjumin1 + "-" + $("#ledgerOwnerAddForm").serializeObject().gmjjumin2);
        ownerGridOptions.api.applyTransaction({add: [$("#ledgerOwnerAddForm").serializeObject()]});
        */

        $('#ledgerOwnerAddForm [name=ownerFlag]').val('owner');
        ownerFormDataCnt++;
        $("input[name=rn]").val(ownerFormDataCnt);
        $('input[name=gmjjumin]').val($("#ledgerOwnerAddForm").serializeObject().gmjjumin1 + '-' + $("#ledgerOwnerAddForm").serializeObject().gmjjumin2);
        var form = $('#ledgerOwnerAddForm');
        validation(form);
        const ledgerOwnerAddFormJson = $("#ledgerOwnerAddForm").serializeObject()

        if (validationFlag) {
            snAlert.confirm("건물추를 추가하시겠습니까?").then((resultChk) => {
                if (resultChk.isConfirmed) {
                    $.post({
                        url: "/api/unlicensedBuilingManagement/ledgerUpdate",
                        //data: JSON.stringify ({owner: ownerFormData}),
                        data: JSON.stringify({ownerDto: ledgerOwnerAddFormJson}),
                        contentType: 'application/json; charset=utf-8'
                    }).done(
                        e => {
                            snAlert.success("알림", "등록 완료").then((result) => {
                                ledgerOwnerAddFormJson.gmjsna = null
                                ownerGridOptions.api.applyTransaction({add: [ledgerOwnerAddFormJson]});
                                /*if (result.isConfirmed) {
                                     location.href="/unlcebldmng/ledger";
                                }*/
                            })
                        }
                    );
                }
            });
        }
    });


    var fixFormData = new Array();
    var fixFormDataCnt = 0;
    if (noperFixObj.length > 0) {
        fixFormDataCnt = noperFixObj.length;
    }
    $('#fixAddBtn').click(() => {
        $("input[name=fixParam]").val("Add");
        $("input[name=gbsgoil]").val(moment($("input[name=gbsgoilTemp]").val()).format('YYYYMMDD'));
        $("input[name=jgong]").val(moment($("input[name=jgongTemp]").val()).format('YYYYMMDD'));

        var form = $('#ledgerFixAddForm');
        validation(form);
        if (validationFlag) {
            snAlert.confirm("개보수를 추가하시겠습니까?").then((resultChk) => {
                if (resultChk.isConfirmed) {
                    $.post({
                        url: "/api/unlicensedBuilingManagement/fixAction",
                        data: JSON.stringify($("#ledgerFixAddForm").serializeObject()),
                        contentType: 'application/json; charset=utf-8'
                    }).done(e => {
                        noperFixGridOptions.api.setRowData(e);
                        $("#findnoperFixTotCnt > span").text(e.length);
                    });
                }
            });
        }
        /*
        $.post({
            url: "/api/unlicensedBuilingManagement/findGmbalno",
            data: JSON.stringify ({ledger:$("#ledgerFixAddForm").serializeObject(), owner: ownerFormData}),
            contentType: 'application/json; charset=utf-8'}).done(e=>{
                fixFormDataCnt++;
                $("input[name=fixRn]").val(fixFormDataCnt);
                $("input[name=gmbalno]").val(e);
                $("input[name=gbsgoil]").val(moment($("input[name=gbsgoilTemp]").val()).format('YYYYMMDD'));
                $("input[name=jgong]").val(moment($("input[name=jgongTemp]").val()).format('YYYYMMDD'));
                fixFormData.push($("#ledgerFixAddForm").serializeObject());
                noperFixGridOptions.api.applyTransaction({add: [$("#ledgerFixAddForm").serializeObject()]});
        });*/
    });
    $('#fixModyBtn').click(() => {
        $("input[name=fixParam]").val("Mody");
        $("input[name=gbsgoil]").val(moment($("input[name=gbsgoilTemp]").val()).format('YYYYMMDD'));
        $("input[name=jgong]").val(moment($("input[name=jgongTemp]").val()).format('YYYYMMDD'));
        var form = $('#ledgerFixAddForm');
        validation(form);
        if (validationFlag) {
            snAlert.confirm("개보수를 수정하시겠습니까?").then((resultChk) => {
                if (resultChk.isConfirmed) {
                    $.post({
                        url: "/api/unlicensedBuilingManagement/fixAction",
                        data: JSON.stringify($("#ledgerFixAddForm").serializeObject()),
                        contentType: 'application/json; charset=utf-8'
                    }).done(e => {
                        noperFixGridOptions.api.setRowData(e);
                        $("#findnoperFixTotCnt > span").text(e.length);
                    });
                }
            });
        }
    });
    //
    $('#siteChkAddBtn').click(() => {
        $("input[name=sitechkParam]").val("Add");
        $("input[name=chkDate]").val(moment($("input[name=chkDateTemp]").val()).format('YYYYMMDD'));
        var form = $('#siteChkAddForm');
        validation(form);
        if (validationFlag) {
            snAlert.confirm("현장점검내역을 추가하시겠습니까?").then((resultChk) => {
                if (resultChk.isConfirmed) {
                    $.post({
                        url: "/api/unlicensedBuilingManagement/siteChkAction",
                        data: JSON.stringify($("#siteChkAddForm").serializeObject()),
                        contentType: 'application/json; charset=utf-8'
                    }).done(e => {
                        siteChkGridOptions.api.setRowData(e);
                        $("#noperSiteChkTotCnt > span").text(e.length);
                        siteChkUploader.parentKey = seq
                        siteChkUploader.uploadFiles();
                        siteChkUploader.clearFiles()
                    });
                }
            });
        }
    });

    $('#siteChkModaModylBtn').click(() => {
        siteChkModaModylBtn();
    });
    $('#siteChkModyBtn').click(() => {
        $("input[name=sitechkParam]").val("Mody");
        $("input[name=chkDate]").val(moment($("input[name=chkDateTemp]").val()).format('YYYYMMDD'));
        var form = $('#siteChkAddForm');
        validation(form);
        if (validationFlag) {
            snAlert.confirm("현장점검내역을 수정하시겠습니까?").then((resultChk) => {
                if (resultChk.isConfirmed) {
                    $.post({
                        url: "/api/unlicensedBuilingManagement/siteChkAction",
                        data: JSON.stringify($("#siteChkAddForm").serializeObject()),
                        contentType: 'application/json; charset=utf-8'
                    }).done(e => {
                        siteChkGridOptions.api.setRowData(e);
                        $("#noperSiteChkTotCnt > span").text(e.length);
                        siteChkUploader.parentKey = seq;
                        siteChkUploader.flag = $("input[name=chkilno]").val();
                        siteChkUploader.uploadFiles();
                        siteChkUploader.clearFiles();
                    });
                }
            });
        }
    });
    $('#trsctSiteChkSave').click(() => {
        $('#trsctSiteChkMody').show();
        $('#trsctSiteChkSave').hide();
        $('.siteChkAtionNon').hide();
        $('.siteChkAtion').show();
    });

    $('#trsctSiteChkMody').click(() => {
        $("input[name=sitechkParam]").val("AcMody");
        const trsctDate = $("input[name=trsctDateTemp]").val()
        $("input[name=trsctDate]").val(trsctDate !== '' ? moment(trsctDate).format('YYYYMMDD') : trsctDate);

        const form = document.getElementById('modalSiteChkActionForm')
        if (form.checkValidity() === false) {
            validEleList.forEach(e => {
                if (!e.value) e.classList.add('is-invalid')
            })
            return false;
        }

        $.post({
            url: "/api/unlicensedBuilingManagement/siteChkAction",
            data: JSON.stringify($("#modalSiteChkActionForm").serializeObject()),
            contentType: 'application/json; charset=utf-8'
        }).done(e => {
            $("#siteChkList").click();
            siteChkGridOptions.api.setRowData(e);
        });
    });

    function moveMenu(type){
          const form = document.createElement("form");
          addSearchFormElementToTargetForm(form, $('#ledgerFindForm')[0])
          switch (type) {
              case 'ledger': form.action = '/unlcebldmng/ledger'; break;
              case 'newLedger': form.action = '/unlcebldmng/newLedger'; break;
          }
          document.body.appendChild(form);
          form.method = 'get';
          form.submit();
      }

});

// 주소데이터 받기
function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn,
    detBdNmList, bdNm, bdKdcd, siNm, sggNm, emdNm, liNm, rn, udrtYn, buldMnnm, buldSlno, mtYn, lnbrMnnm, lnbrSlno, emdNo){
    if(jusoFlag == "ledger"){
        $('#ledgerAddForm [name="newaddr1"]').val(roadFullAddr);
        $('#ledgerAddForm [name="newaddr2"]').val(jibunAddr);
        $('#ledgerAddForm [name="gmskkcd"]').val(admCd.substring(0, 5));//시군구코드
        $('#ledgerAddForm [name="gmbjdcd"]').val(admCd.substring(5, 10));// 법정동코드
        //$('#ledgerAddForm [name="gmcsah"]').val(mtYn);// 대지 산
        if(mtYn == "0"){ // 대지 산
            $('#ledgerAddForm input[name=gmcsahTemp]').val("대지");
        }else if(mtYn == "1"){
           $('#ledgerAddForm input[name=gmcsahTemp]').val("산");
        }else{
           $('#ledgerAddForm input[name=gmcsahTemp]').val("블록");
        }
        $('#ledgerAddForm [name="gmcbuh"]').val(lnbrMnnm);// 본번
        $('#ledgerAddForm [name="gmcji"]').val(lnbrSlno);// 부번
        $('#ledgerAddForm [name="gmcoh"]').val(addrDetail);// 새주소그외
        $('#ledgerAddForm [name="naEtc"]').val(addrDetail);// 새주소그외
        $('#ledgerAddForm [name="naRoadCd"]').val(rnMgtSn);// 도로명코드
        $('#ledgerAddForm [name="naRoadNm"]').val(rn);// 도로명
        $('#ledgerAddForm [name="naSigunguCd"]').val(admCd.substring(0, 5));// 새주소그외
        $('#ledgerAddForm [name="naBjdongNo"]').val(admCd.substring(5, 8));// 도로명코드
        $('#ledgerAddForm [name="naMainBun"]').val(buldMnnm);// 건물본번
        $('#ledgerAddForm [name="naSubBun"]').val(buldSlno);// 건물부번
    }
    if(jusoFlag == "owner"){
        $('#ledgerOwnerAddForm [name="newAddr1"]').val(roadFullAddr);
        $('#ledgerOwnerAddForm [name="newAddr2"]').val(jibunAddr);
        $('#ledgerOwnerAddForm [name="gmskkcd"]').val(admCd.substring(0, 5));//시군구코드
        $('#ledgerOwnerAddForm [name="gmbjdcd"]').val(admCd.substring(5, 10));// 법정동코드
        //$('#ledgerOwnerAddForm [name="gmcsah"]').val(mtYn);// 대지 산
        if(mtYn == "0"){ // 대지 산
            $('#ledgerAddForm input[name=gmcsahTemp]').val("대지");
        }else if(mtYn == "1"){
           $('#ledgerAddForm input[name=gmcsahTemp]').val("산");
        }else{
           $('#ledgerAddForm input[name=gmcsahTemp]').val("블록");
        }
        $('#ledgerOwnerAddForm [name="gmcbuh"]').val(lnbrMnnm);// 본번
        $('#ledgerOwnerAddForm [name="gmcji"]').val(lnbrSlno);// 부번
        $('#ledgerOwnerAddForm [name="gmcoh"]').val(addrDetail);// 새주소그외
        $('#ledgerOwnerAddForm [name="naEtc"]').val(addrDetail);// 새주소그외
        $('#ledgerOwnerAddForm [name="naRoadCd"]').val(rnMgtSn);// 도로명코드
        $('#ledgerOwnerAddForm [name="naRoadNm"]').val(rn);// 도로명
        $('#ledgerOwnerAddForm [name="naSigunguCd"]').val(admCd.substring(0, 5));// 새주소그외
        $('#ledgerOwnerAddForm [name="naBjdongNo"]').val(admCd.substring(5, 8));// 도로명코드
        $('#ledgerOwnerAddForm [name="naMainBun"]').val(buldMnnm);// 건물본번
        $('#ledgerOwnerAddForm [name="naSubBun"]').val(buldSlno);// 건물부번
        $('#ledgerOwnerAddForm [name="gmzip1"]').val(zipNo);// 건물부번

    }
}
async function siteChkModaModylBtn() {
    $(".siteChkFileHide").show();
    $("#siteChkAddBtn").hide();
    $("#siteChkModyBtn").show();

    $('#siteChkAddForm input[name=chkDateTemp]').val(moment(siteChkSelOjbect.data.chkDate).format('YYYY-MM-DD'));
    $('#siteChkAddForm input[name=chkChargeClspos]').val(siteChkSelOjbect.data.chkChargeClspos);
    if(siteChkSelOjbect.data.violYn == "Y"){
        $("#siteChkAddForm input:radio[name='violYn']:radio[value='Y']").prop('checked', true);
    }else{
        $("#siteChkAddForm input:radio[name='violYn']:radio[value='N']").prop('checked', true);
    }
    $("#siteChkAddForm textarea[name=violCntt]").val(siteChkSelOjbect.data.violCntt);
    $("#siteChkAddForm textarea[name=rem]").val(siteChkSelOjbect.data.rem);
    $("#siteChkAddForm input[name=chkilno]").val(siteChkSelOjbect.data.chkilno);

    await siteChkUploader.loadFiles();
    $('#user-files3').html("");
    
    // 파일 목록 영역을 조절하는 함수
    const fileDisplay = () => {
        if(siteChkUploader.getFiles().length === 0) {
            document.getElementsByClassName('siteChkFileHide')[0].style.display = 'none';
        } else {
            document.getElementsByClassName('siteChkFileHide')[0].style.display = '';
        }
    }
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
            snAlert.confirm("해당파일을 삭제하시겠습니까?").then((resultChk) => {
                if (resultChk.isConfirmed) {
                    $(e.target).closest("li").remove();
                    uploader.deleteFiles(file)
                    fileDisplay()
                    return false;
                }
            });
        });
        $("<li class='list-inline-item'>")
            .append($link)
            .append($deleteLink)
            .appendTo($("#user-files3"));
    });
    fileDisplay()
}

function fnChgPung_S1(munjuk,property){
    var pung = Math.round((munjuk * 0.3025)*100)/100;
    ledgerAddForm.elements[property].value = pung;
}

function CheckStrLen(maxlen,field){
   var temp; //들어오는 문자값...
   var msglen;
   msglen = maxlen*2;
   var value= field.value;
   l =  field.value.length;
   tmpstr = "" ;
   if (l == 0){
        value = maxlen*2;
   }else{
        for(k=0;k<l;k++){
            temp =value.charAt(k);
            if (escape(temp).length > 4) {msglen -= 2;
            }else{ msglen--;}
                if(msglen < 0){
                    snAlert.alert("총 영문 "+(maxlen*2)+"자 한글 " + maxlen + "자 까지 보내실수 있습니다.");
                    field.value= tmpstr;
                    break;
            }else{
                tmpstr += temp;
            }
        }
   }
}



function validation(forms){
         Array.from(forms).forEach(form => {
         if (!form.checkValidity()) {
             event.preventDefault();
             event.stopPropagation();
             form.classList.add('was-validated');
             return validationFlag = false;
         }else{
            return validationFlag = true;
         }
     })
};

