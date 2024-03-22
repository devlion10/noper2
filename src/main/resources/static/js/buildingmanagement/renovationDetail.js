// 수정 저장
$("#modifyBtn").click(() => {
    let gbsgoil = $("#gbsgoil").val().replaceAll("-", "");
    let jgong = $("#jgong").val().replaceAll("-", "");

    $("#gbsgoilRpIp").val(gbsgoil);
    $("#jgongRpIp").val(jgong);

    var form = $("#renovationModifyForm");

    validation(form);
    if(validationFlag) {
        const renovationAddForm = $("#renovationModifyForm").serializeObject();

        let url = "/api/unlicensedBuilingManagement/renovation/modify";
        let type = "post";
        let json = JSON.stringify(renovationAddForm);
        let contentType = "application/json";

        if ($("#gbsgoil").val() == null || $("#gbsgoil").val() == "") {
            return snAlert.alert("접수일자를 선택해주세요.");
        } else if ($("#jgong").val() == null || $("#jgong").val() == "") {
            return snAlert.alert("준공일자를 선택해주세요.");
        } else if ($("#wbgb").val() == null || $("#wbgb").val() == "") {
            return snAlert.alert("위반여부를 작성해주세요.");
        }

        snAlert.confirm("저장하시겠습니까?").then((resultChk) => {
            if (resultChk.isConfirmed) {
                ajaxCall(url, type, json, contentType, async function (result) {
                    await snAlert.alert("저장 되었습니다.");
                    $("#renovationDetailForm").submit();
                });
            }
        });
    }
});

// 신고서 버튼 클릭
$("#pills-modify-tab").click(() => {
    // url 처리
});

// 신고서 버튼 클릭
$("#reportCallBtn").click(() => {
    let gmskk = $("#gmskkRpIp").val();
    let gmdjgb = $("#gmdjgbRpIp").val();
    let gmseqco = $("#gmseqcoRpIp").val();
    let gmilno = $("#gmilnoRpIp").val();
    let user = $("#userName").val();

    let data = {
          seal: gmskk
        , gmskk: gmskk
        , gmdjgb: gmdjgb
        , gmseqco: gmseqco
        , gmilno:gmilno
        , user:user };

    snReport.open('RPAIR_DCLRT', data);
});


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
};

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
};