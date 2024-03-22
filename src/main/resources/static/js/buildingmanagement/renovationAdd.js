
// 새로고침 버튼 클릭 시 화면 reload
$("#reloadBtn").click(() => {
    snAlert.confirm("새로고침 진행 시 조회된 데이터가 삭제됩니다.\n그래도 진행하시겠습니까?").then((resultChk) => {
        if (resultChk.isConfirmed) {
            location.href = "/unlcebldmng/renovation/add";
        }
    })
});

// 취소 버튼 클릭 시 List화면으로 이동
$("#cancelBtn").click(() => {
    snAlert.confirm("확인을 누르시면 입력한 정보가\n 모두 삭제되고,\n조회화면으로 이동합니다.\n그래도 진행하시겠습니까?").then((resultChk) => {
        if (resultChk.isConfirmed) {
            $("#listBtn").click();
        }
    })
});

// 검색 버튼 클릭 시 조회
$("#searchBtn").click(() => {
    const searchResult = ($("#gmskk").val() === "" || $("#gmdjgb").val() === "" || $("#gmseqco").val() === "")
    searchResult
        ? snAlert.alert("조회할 대장번호를 입력해주세요.")
        : search();
});

// 저장 버튼 클릭 시 저장
$("#saveBtn").click(() => {
    if ($("#num").text() == null || $("#num").text() == '' ){
        $("#gmskk").focus();
        return snAlert.alert("대장내역이 없습니다.\n대장내역을 먼저 조회하세요.");
    } else {
        let gbsgoil = $("#gbsgoil").val().replaceAll("-", "");
        let jgong = $("#jgong").val().replaceAll("-", "");

        $("#gbsgoilRpIp").val(gbsgoil);
        $("#jgongRpIp").val(jgong);

        var form = $("#renovationAddForm");

        validation(form);
        if(validationFlag) {
            const renovationAddForm = $("#renovationAddForm").serializeObject();

            let url = "/api/unlicensedBuilingManagement/renovation/addSave";
            let type = "post";
            let json = JSON.stringify(renovationAddForm);
            let contentType = "application/json";

            snAlert.confirm("개보수 신고를 하시겠습니까?").then((resultChk) => {
                if (resultChk.isConfirmed) {
                    ajaxCall(url, type, json, contentType, async function (result) {
                        await snAlert.alert("개보수 신고 되었습니다.");
                        const gmskk = result.gmskk;
                        const gmdjgb = result.gmdjgb;
                        const gmseqco = result.gmseqco;
                        const gmilno = result.gmilno;
                        $("#gmskkDtlIp").val(gmskk);
                        $("#gmdjgbDtlIp").val(gmdjgb);
                        $("#gmseqcoDtlIp").val(gmseqco);
                        $("#gmilnoDtlIp").val(gmilno);
                        $("#renovationDetailForm").submit(); // 상세 이동
                    });
                }
            });
        }
    }
});

//조회 실행
function search(callback) {
    const renovationSearchForm = $("#renovationSearchForm").serializeObject();

    let url = "/api/unlicensedBuilingManagement/renovation/addSearch";
    let type = "post";
    let json = JSON.stringify(renovationSearchForm);
    let contentType = "application/json";
    ajaxCall(url, type, json, contentType, function (result) {
        // if (result != null || result != undefined || result !== "") {
        if (result != null || result !== undefined) {
            if (result.renovationAddData[0].degb === 'Y') {
                snAlert.alert('철거된 대장입니다.')
                return;
            }

            let issuerKeyArr = result.renovationIssuerKey;
            let str = "";
            issuerKeyArr.forEach(function (item) {
                str += "<option value='" + item.key + "'>" + item.prntnm + "</option>"
            });

            const resultData = result.renovationAddData[0]

            let gmskk = resultData.gmskk;
            let gmdjgb = resultData.gmdjgb;
            let gmseqco = resultData.gmseqco;
            let daejang = resultData.daejang;
            let gmjname = resultData.gmjname;

            $("#gmskkDtlIp").val(gmskk);
            $("#gmdjgbDtlIp").val(gmdjgb);
            $("#gmseqcoDtlIp").val(gmseqco);

            $("#issueorgkey").html(str);
            $("#rn").text(result.renovationAddData.length);
            $("#num").text(resultData.num);
            $("#gmgzcd").text(resultData.gmgzcd);
            $("#tojisg").text(resultData.tojisg);
            $("#gmmunjuk").text(resultData.gmmunjuk);
            $("#tojimj").text(resultData.tojimj);
            $("#buildingNewAddr1").text(resultData.buildingNewAddr1);
            $("#buildingNewAddr2").text(resultData.buildingNewAddr2);
            $("#gmjNewAddr1").text(resultData.gmjNewAddr1);
            $("#gmjNewAddr2").text(resultData.gmjNewAddr2);
            $("#gmjjumin").text(resultData.gmjjumin);
            $("#gmjname").text(resultData.gmjname);

            $("#gmskkRpIp").val(gmskk);
            $("#gmdjgbRpIp").val(gmdjgb);
            $("#gmseqcoRpIp").val(gmseqco);
            $("#daejangRpIp").val(daejang);
            $("#gmjnameRpIp").val(gmjname);
            // 단위 표시
            const gmmunjukMeter = resultData.gmmunjuk !== null ? ' ㎡' : ''
            $('.meter1').text(gmmunjukMeter)
            // 단위 표시
            const tojimjMeter = resultData.tojimj !== null ? ' ㎡' : ''
            $('.meter2').text(tojimjMeter)
        } else {
            return snAlert.alert("해당 대장은 없는 대장입니다.");
        }

        if (typeof callback === 'function') {
            callback(result)
        }
    });
}

// validation 확인
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
}

//ajax 공통
function ajaxCall(url, type, param, contentType, callback) {
    $.ajax({
        url : url,
        type : type,
        data : param,
        contentType: contentType,
        success: function (result){
            return callback(result);
        }
    });
}