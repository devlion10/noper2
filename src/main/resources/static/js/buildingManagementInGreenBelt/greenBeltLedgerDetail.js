let jusoCheck;

$(document).ready(function(){
    $('#listBtn').click(() => {location.href="/bldmngingb/ledger";});

    document.querySelector("#buildingJusoBtn").addEventListener("click", (e) => {
        jusoCheck = 'building'
        const w = window.open('/common/juso', 'buildingJuso', 'width=500px,height=500px');
        w.focus();
    });

    document.querySelector("#ownerJusoBtn").addEventListener("click", (e) => {
        jusoCheck = 'owner'
        const w = window.open('/common/juso', 'ownerJuso', 'width=500px,height=500px');
        w.focus();
    });

    document.querySelector("#ownerTempJusoBtn").addEventListener("click", (e) => {
        jusoCheck = 'ownerTemp'
        const w = window.open('/common/juso', 'ownerTempJuso', 'width=500px,height=500px');
        w.focus();
    });

    /*var buildFlagValue = buildFlag;
    $('input[name="buildFlag"]').each(function() {
        if ($(this).val() === buildFlagValue) {
            $(this).prop('checked', true);
        }
    });*/

    // 대장상세 건축물현황 저장
    $('#limitedSaveBtn').click(() => {
        $("input[name=limitedParam]").val("Mody");
        $.post({
            url: "/api/bldmngingb/ledgerLimitedUpdate",
            data: JSON.stringify($("#ledgerLimitedAddForm").serializeObject()),
            contentType: 'application/json; charset=utf-8'
        }).done(e => {
            snAlert.success("알림", "등록 완료").then((result) => {
                // Handle the success response here
            });
        });
    });
    // 대장상세 건축물현황 폐쇄
    $('#delLedgerBtn').click(() => {
        $("input[name=limitedParam]").val("Dell");
        snAlert.confirm('폐쇄처리를 하시겠습니까?').then((res) => {
            if (res.isConfirmed) {
                $.post({
                    url: "/api/bldmngingb/deleteLimitedHead",
                    data: JSON.stringify($("#ledgerLimitedAddForm").serializeObject()),
                    contentType: 'application/json; charset=utf-8'
                }).done(
                    e => snAlert.success("알림", "폐쇄처리 완료").then((result) => {
                        if (result.isConfirmed) {
                            location.href = "/bldmngingb/ledger";
                        } else if (result.isDismissed) {
                            snAlert.close();
                        }
                    })
                );
            }
        });
    });


    //소유자현황
    var ownerFormData = new Array();
    var ownerFormDataCnt = 0;
    if(legerOwnerObj.length > 0){
        ownerFormDataCnt = legerOwnerObj.length;
    }
    $('#ownerAddBtn').click(() => {
        $("input[name=ownerParam]").val("Add");
        $("input[name=chDateTemp]").val(moment($("input[name=chDate]").val()).format('YYYYMMDD'));
        $.post({
            url: "/api/bldmngingb/ownerAction",
            data: JSON.stringify($("#ledgerOwnerAddForm").serializeObject()),
            contentType: 'application/json; charset=utf-8'
        }).done(e=>{
            ownerGridOptions.api.setRowData(e);
            updateOwnerTotalCount(e.length);
        });
    });
    $('#ownerModyBtn').click(() => {
        var ownerSeq = $("input[name=ownerSeq]").val();
        if (ownerSeq) {
            ownerFormData.push(ownerSeq);
        }
        $("input[name=ownerParam]").val("Mody");
        $("input[name=chDateTemp]").val(moment($("input[name=chDate]").val()).format('YYYYMMDD'));
        var requestData = $("#ledgerOwnerAddForm").serializeObject();
        requestData.ownerSeq = ownerSeq;
        $.post({
            url: "/api/bldmngingb/ownerAction",
            data: JSON.stringify(requestData),
            contentType: 'application/json; charset=utf-8'
        }).done(e=>{
            ownerGridOptions.api.setRowData(e);
            updateOwnerTotalCount(e.length);
        });
    });
    $('#ownerResetBtn').click(() => {
        $("#ledgerOwnerAddForm")[0].reset();
        $("#ownerModyBtn").hide();
        $("#ownerAddBtn").show();
    });
    function updateOwnerTotalCount(count) {
        $("#legerOwnerTotCnt > span").text(count);
    }


    // 동별현황
    var dongFormData = new Array();
    var dongFormDataCnt = 0;
    if (legerDongObj.length > 0) {
        dongFormDataCnt = legerDongObj.length;
    }
    $('#dongAddBtn').click(() => {
        $("input[name=dongParam]").val("Add");
        $("input[name=dongPermitDateTemp]").val(moment($("input[name=dongPermitDate]").val()).format('YYYYMMDD'));
        $("input[name=dongCompDateTemp]").val(moment($("input[name=dongCompDate]").val()).format('YYYYMMDD'));
        $.post({
            url: "/api/bldmngingb/dongAction",
            data: JSON.stringify($("#ledgerDongAddForm").serializeObject()),
            contentType: 'application/json; charset=utf-8'
        }).done(e => {
            dongGridOptions.api.setRowData(e);
            updateDongTotalCount(e.length);
        });
    });
    $('#dongModyBtn').click(() => {
        var dongSeq = $("input[name=dongSeq]").val();
        if (dongSeq) {
            dongFormData.push(dongSeq);
        }
        $("input[name=dongParam]").val("Mody");
        $("input[name=dongPermitDateTemp]").val(moment($("input[name=dongPermitDate]").val()).format('YYYYMMDD'));
        $("input[name=dongCompDateTemp]").val(moment($("input[name=dongCompDate]").val()).format('YYYYMMDD'));
        var requestData = $("#ledgerDongAddForm").serializeObject();
        requestData.dongSeq = dongSeq;

        $.post({
            url: "/api/bldmngingb/dongAction",
            data: JSON.stringify(requestData),
            contentType: 'application/json; charset=utf-8'
        }).done(e => {
            dongGridOptions.api.setRowData(e);
            updateDongTotalCount(e.length);
        });
    });
    $('#dongResetBtn').click(() => {
        $("#ledgerDongAddForm")[0].reset();
        $("#dongModyBtn").hide();
        $("#dongAddBtn").show();
    });
    function updateDongTotalCount(count) {
        $("#legerDongTotCnt > span").text(count);
    }

    // 구조물현황
    var structuresFormData = new Array();
    var structuresFormDataCnt = 0;
    if (legerStructuresObj.length > 0) {
        structuresFormDataCnt = legerStructuresObj.length;
    }
    $('#stAddBtn').click(() => {
        $("input[name=stParam]").val("Add");
        $("input[name=stPermitDateTemp]").val(moment($("input[name=stPermitDate]").val()).format('YYYYMMDD'));
        $("input[name=stCompDateTemp]").val(moment($("input[name=stCompDate]").val()).format('YYYYMMDD'));

        $.post({
            url: "/api/bldmngingb/structuresAction",
            data: JSON.stringify($("#legerStructuresAddForm").serializeObject()),
            contentType: 'application/json; charset=utf-8'
        }).done(e => {
            structuresGridOptions.api.setRowData(e);
            updateStructuresTotalCount(e.length);
        });
    });
    $('#stModyBtn').click(() => {
        var stSeq = $("input[name=stSeq]").val();
        if (stSeq) {
            structuresFormData.push(stSeq);
        }
        $("input[name=stParam]").val("Mody");
        $("input[name=stPermitDateTemp]").val(moment($("input[name=stPermitDate]").val()).format('YYYYMMDD'));
        $("input[name=stCompDateTemp]").val(moment($("input[name=stCompDate]").val()).format('YYYYMMDD'));

        var requestData = $("#legerStructuresAddForm").serializeObject();
        requestData.stSeq = stSeq;

        $.post({
            url: "/api/bldmngingb/structuresAction",
            data: JSON.stringify(requestData),
            contentType: 'application/json; charset=utf-8'
        }).done(e => {
            structuresGridOptions.api.setRowData(e);
            updateStructuresTotalCount(e.length);
        });
    });
    $('#stResetBtn').click(() => {
        $("#legerStructuresAddForm")[0].reset();
        $("#stModyBtn").hide();
        $("#stAddBtn").show();
    });
    function updateStructuresTotalCount(count) {
        $("#legerStructuresTotCnt > span").text(count);
    }

    // 대지현황
    var groundFormData = new Array();
    var groundFormDataCnt = 0;
    if (legerGroundObj.length > 0) {
        groundFormDataCnt = legerGroundObj.length;
    }
    $('#groundAddBtn').click(() => {
        $("input[name=groundParam]").val("Add");

        $.post({
            url: "/api/bldmngingb/groundAction",
            data: JSON.stringify($("#legerGroundAddForm").serializeObject()),
            contentType: 'application/json; charset=utf-8'
        }).done(e => {
            groundGridOptions.api.setRowData(e);
            updateGroundTotalCount(e.length);
        });
    });
    $('#groundModyBtn').click(() => {
        var groundSeq = $("input[name=groundSeq]").val();
        if (groundSeq) {
            groundFormData.push(groundSeq);
        }
        $("input[name=groundParam]").val("Mody");

        var requestData = $("#legerGroundAddForm").serializeObject();
        requestData.groundSeq = groundSeq;

        $.post({
            url: "/api/bldmngingb/groundAction",
            data: JSON.stringify(requestData),
            contentType: 'application/json; charset=utf-8'
        }).done(e => {
            groundGridOptions.api.setRowData(e);
            updateGroundTotalCount(e.length);
        });
    });
    $('#groundResetBtn').click(() => {
        $("#legerGroundAddForm")[0].reset();
        $("#groundModyBtn").hide();
        $("#groundAddBtn").show();
    });
    function updateGroundTotalCount(count) {
        $("#legerGroundTotCnt > span").text(count);
    }

    // 허가신고사항
    var permitFormData = new Array();
    var permitFormDataCnt = 0;
    if (legerPermitObj.length > 0){
        permitFormDataCnt = legerPermitObj.length;
    }
    $('#permitAddBtn').click(() => {
        $("input[name=permitParam]").val("Add");
        $("input[name=permitDateTemp]").val(moment($("input[name=permitDate]").val()).format('YYYYMMDD'));
        $("input[name=permitCompDateTemp]").val(moment($("input[name=permitCompDate]").val()).format('YYYYMMDD'));

        $.post({
            url: "/api/bldmngingb/permitAction",
            data: JSON.stringify($("#legerPermitAddForm").serializeObject()),
            contentType: 'application/json; charset=utf-8'
        }).done(e => {
            permitGridOptions.api.setRowData(e);
            updatePermitTotalCount(e.length);
        });
    });
    $('#permitModyBtn').click(() => {
        var permitSeq = $("input[name=permitSeq]").val();
        $("input[name=permitDateTemp]").val(moment($("input[name=permitDate]").val()).format('YYYYMMDD'));
        $("input[name=permitCompDateTemp]").val(moment($("input[name=permitCompDate]").val()).format('YYYYMMDD'));

        if (permitSeq) {
            permitFormData.push(permitSeq);
        }
        $("input[name=permitParam]").val("Mody");

        var requestData = $("#legerPermitAddForm").serializeObject();
        requestData.permitSeq = permitSeq;

        $.post({
            url: "/api/bldmngingb/permitAction",
            data: JSON.stringify(requestData),
            contentType: 'application/json; charset=utf-8'
        }).done(e => {
            permitGridOptions.api.setRowData(e);
            updatePermitTotalCount(e.length);
        });
    });
    $('#permitResetBtn').click(() => {
        $("#legerPermitAddForm")[0].reset();
        $("#permitModyBtn").hide();
        $("#permitAddBtn").show();
    });
    function updatePermitTotalCount(count) {
        $("#legerPermitTotCnt > span").text(count);
    }


});

function calculateSum() {
    var underScale = parseFloat(document.querySelector("input[name='underScale']").value) || 0;
    var floor1Scale = parseFloat(document.querySelector("input[name='floor1Scale']").value) || 0;
    var floor2Scale = parseFloat(document.querySelector("input[name='floor2Scale']").value) || 0;
    var floor3Scale = parseFloat(document.querySelector("input[name='floor3Scale']").value) || 0;
    var floor4Scale = parseFloat(document.querySelector("input[name='floor4Scale']").value) || 0;

    var sum = underScale + floor1Scale + floor2Scale + floor3Scale + floor4Scale;

    document.querySelector("#sumResult").value = sum;
}


function jusoCallBack(roadFullAddr, roadAddrPart1, addrDetail, roadAddrPart2, engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn,
                      detBdNmList, bdNm, bdKdcd, siNm, sggNm, emdNm, liNm, rn, udrtYn, buldMnnm, buldSlno, mtYn, lnbrMnnm, lnbrSlno, emdNo) {
    console.log(arguments);

    // 분리된 관리기관 코드를 설정
    const admCdStr = admCd.toString();
    const skkCd = admCdStr.substring(0, 5);
    const bjdCd = admCdStr.substring(5);

    //$('#skkCd').val(skkCd); // 관리기관코드 admCd 앞 5자리
    $('#bjdCd').val(bjdCd); // 관리기관코드 admCd 뒤 5자리

    if (jusoCheck === 'building') {
        $('#buildAddr1').val(arguments[0]); // 도로명 roadFullAddr
        $('#buildAddr2').val(arguments[5]); // 지번 jibunAddr
        $('#gmcSahBuilding').val(arguments[21]); // 대지/산 mtYn
        console.log(bjdCd)
        $('#bjdCdBuilding').val(bjdCd); // 법정동 admCd 뒤 5자리
        $('#gmcbuhBuilding').val(lnbrMnnm); // 번 lnbrMnnm
        $('#gmcjiBuilding').val(lnbrSlno); // 지 lnbrSlno
        $('#gmcohBuilding').val(addrDetail); // 기타 addrDetail
    } else if(jusoCheck === 'owner') {
        $('#owner1Addr1').val(arguments[0]); // 소유자 도로명 roadFullAddr
        $('#owner1Addr2').val(arguments[5]); // 소유자 지번 jibunAddr
        $('#gmcSahOwner').val(arguments[21]); // 대지/산 mtYn
        $('#bjdCdOwner').val(bjdCd); // 법정동 admCd 뒤 5자리
        $('#gmcbuhOwner').val(lnbrMnnm); // 번 lnbrMnnm
        $('#gmcjiOwner').val(lnbrSlno); // 지 lnbrSlno
        $('#gmcohOwner').val(addrDetail); // 기타 addrDetail
        $('#owner1Zip1').val(zipNo); // 우편번호1 zipNo
    } else if(jusoCheck === 'ownerTemp'){
        $('#ownerAddr1').val(arguments[0]); // 도로명 roadFullAddr
        $('#ownerAddr2').val(arguments[5]); // 지번 jibunAddr
        $('#ownerZip1').val(zipNo); // 우편번호1 zipNo
    }
}
