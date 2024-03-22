var validationFlag = false;
$(document).ready(function () {
    $('#desau').summernote({
        height: 300,                 // 에디터 높이
        minHeight: null,             // 최소 높이
        maxHeight: null,             // 최대 높이
        focus: true,                  // 에디터 로딩후 포커스를 맞출지 여부
        lang: "ko-KR",					// 한글 설정
        placeholder: '최대 2048자까지 쓸 수 있습니다'	//placeholder 설정
    });

    /*
    * 기존무허가 대장관리 상세에서 기존무허가 건축물관리 철거신고등록으로 이동했을 때, 자동 데이터 기입 로직
    * modal.js 에 있는 함수를 그대로 사용함.
    */
    const url = new URL(window.location.href);
    const urlGmskk = url.searchParams.get('gmskk');
    const urlGmdjgb = url.searchParams.get('gmdjgb');
    const urlGmseqco = url.searchParams.get('gmseqco');
    if (urlGmskk != null && urlGmdjgb != null && urlGmseqco != null) {
        document.getElementById('gmdjgb').value = urlGmdjgb
        document.getElementById('gmseqco').value = urlGmseqco
        document.getElementById('gmseqco2').value = urlGmseqco
        $('#btnSearch').click()
    }
});

// 취소
$("#cancelBtn").click(() => {
    snAlert.confirm("확인을 누르시면 입력한 정보가\n 모두 삭제됩니다.\n그래도 진행하시겠습니까?").then((resultChk) => {
        if (resultChk.isConfirmed) {
            $("#listBtn").click();
        }
    })
});

// 신고사항 등록
$("#saveBtn").click(() => {
    let gmskk = $("#gmskk").val();
    let gmdjgb = $("#gmdjgb").val();
    let gmseqco = $("#gmseqco").val();

    if (gmskk == '' || gmdjgb == '' || gmseqco == '') {
        $("#gmskk").focus();
        return snAlert.alert("대장내역이 없습니다.\n 대장내역을 먼저 조회하세요");
    } else {
        var form = $('#demolitionReportAddForm');

        validation(form);
        if (validationFlag) {
            const demolitionReportAddForm = $("#demolitionReportAddForm").serializeObject();

            let url = "/api/unlicensedBuilingManagement/demolition/add/reportSave";
            let type = "post";
            let json = JSON.stringify(demolitionReportAddForm);
            let contentType = "application/json";

            snAlert.confirm("위 내역으로 대장을\n철거신고 하시겠습니까?\n\n철거신고 후 건물주보상현황 및\n 세입자현황을 추가하시기 바랍니다.").then((resultChk) => {
                if (resultChk.isConfirmed) {
                    ajaxCall(url, type, json, contentType, function (result) {
                        if (result != null) {
                            snAlert.alert("철거신고 되었습니다.").then((resultChk) => {
                                if (resultChk.isConfirmed) {
                                    // 확인을 눌렀을 때
                                    movePageDetail(gmskk, gmdjgb, gmseqco);
                                }
                            });
                        }
                    });
                }
            });
        }
    }
});

function movePageDetail(gmskk, gmdjgb, gmseqco){
    let str = '';
        str += '<input type="hidden" name="gmskk" id="gmskkDtl" value="'+gmskk+'"/>';
        str += '<input type="hidden" name="gmdjgb" id="gmdjgbDtl" value="'+gmdjgb+'"/>';
        str += '<input type="hidden" name="gmseqco" id="gmseqcoDtl" value="'+gmseqco+'"/>';
    $("#demolitionDetailForm").append(str);
    $("#demolitionDetailForm").submit();
}

// 검색 조건 - 새로고침 버튼 클릭 시 form Reset
$("#btnReset").click(() => {
    document.getElementById("demolitionReportSearchForm").reset();
});

// 기본현황, 대장내역, 건물 소유자현황 조회
$("#btnSearch").click(() => {
    document.getElementById("demolitionReportAddForm").reset();

    let gmskk = $("#gmskk").val();
    let gmdjgb = $("#gmdjgb").val();
    let gmseqco = $("#gmseqco").val();

    if (gmskk == '' || gmdjgb == '' || gmseqco == '') {
        snAlert.alert("조회할 대장번호를 입력해주세요.");
        return;
    }

    const demolitionReportSearchForm = $("#demolitionReportSearchForm").serializeObject();

    let url = "/api/unlicensedBuilingManagement/demolition/add/search";
    let type = "post";
    let json = JSON.stringify(demolitionReportSearchForm);
    let contentType = "application/json";

    ajaxCall(url, type, json, contentType, function (result) {
        if (result.demolitionIssuerKey === null || result.demolitionNoperInfo === null || result.demolitionNoperInfo === undefined) {
            snAlert.alert('해당 대장은 없는 대장입니다.')
            return;
        }
        if (result.demolitionNoperInfo[0].degb === 'Y') {
            snAlert.alert('철거된 대장입니다.')
            return;
        }
        let daejangBunho = result.demolitionNoperInfo[0].daejangBunho;
        let dhbh = result.demolitionNoperInfo[0].dhbh;
        let dhgbh = result.demolitionNoperInfo[0].dhgbh;
        let dongbh = result.demolitionNoperInfo[0].dongbh;
        let donggbh = result.demolitionNoperInfo[0].donggbh;
        let daejang = result.demolitionNoperInfo[0].daejang;
        let newAddr1 = result.demolitionNoperInfo[0].newAddr1;
        let newAddr2 = result.demolitionNoperInfo[0].newAddr2;
        let bjuso = result.demolitionNoperInfo[0].bjuso;
        let gmhjdcd = result.demolitionNoperInfo[0].gmhjdcd;
        let gmhjdnm = result.demolitionNoperInfo[0].gmhjdnm;
        let gmgzcd = result.demolitionNoperInfo[0].gmgzcd;
        let gmyd = result.demolitionNoperInfo[0].gmyd;
        let gmbsdong = result.demolitionNoperInfo[0].gmbsdong;
        if (gmbsdong == null) {
            gmbsdong = 0;
        }
        let gmmunjuk = result.demolitionNoperInfo[0].gmmunjuk;
        let gmgunp = result.demolitionNoperInfo[0].gmgunp;
        let currentGmbtojimjuk = result.demolitionNoperInfo[0].currentGmbtojimjuk;
        let currentGmbtojimj = result.demolitionNoperInfo[0].currentGmbtojimj;
        let currentGmbmjuk = result.demolitionNoperInfo[0].currentGmbmjuk;
        let currentGmbgunp = result.demolitionNoperInfo[0].currentGmbgunp;
        let gmgunil = result.demolitionNoperInfo[0].gmgunil;
        let tojimunjuk = result.demolitionNoperInfo[0].tojimunjuk;
        let tojimj = result.demolitionNoperInfo[0].tojimj;
        let tojijmg = result.demolitionNoperInfo[0].tojijmg;
        let tojijmgnm = result.demolitionNoperInfo[0].tojijmgnm;
        let tojidp = result.demolitionNoperInfo[0].tojidp;
        let jigujdg = result.demolitionNoperInfo[0].jigujdg;
        let jigugb = result.demolitionNoperInfo[0].jigugb;
        let jiguug = result.demolitionNoperInfo[0].jiguug;
        let jigudz = result.demolitionNoperInfo[0].jigudz;
        if (jigudz == null) {
            jigudz = 0;
        }
        let gmfoors = result.demolitionNoperInfo[0].gmfoors;
        if (gmfoors == null) {
            gmfoors = 0;
        }
        let tojisg = result.demolitionNoperInfo[0].tojisg;

        let gmskkcd = result.demolitionNoperInfo[0].gmskkcd;
        let gmbjdcd = result.demolitionNoperInfo[0].gmbjdcd;
        let gmcsah = result.demolitionNoperInfo[0].gmcsah;
        let gmcbuh = result.demolitionNoperInfo[0].gmcbuh;
        let gmcji = result.demolitionNoperInfo[0].gmcji;
        let gmcoh = result.demolitionNoperInfo[0].gmcoh;
        let naBjdongNo = result.demolitionNoperInfo[0].naBjdongNo;

        // if (naBjdongNo == null || naBjdongNo.equals("")){
        if (naBjdongNo == null) {
            naBjdongNo = "";
        }
        let naRoadCd = result.demolitionNoperInfo[0].naRoadCd;
        if (naRoadCd == null) {
            naRoadCd = "";
        }
        let naRoadNm = result.demolitionNoperInfo[0].naRoadNm;
        if (naRoadNm == null) {
            naRoadNm = "";
        }
        let naMainBun = result.demolitionNoperInfo[0].naMainBun;
        if (naMainBun == null) {
            naMainBun = "";
        }
        let naSubBun = result.demolitionNoperInfo[0].naSubBun;
        if (naSubBun == null) {
            naSubBun = "";
        }
        let naEtc = result.demolitionNoperInfo[0].naEtc;
        if (naEtc == null) {
            naEtc = "";
        }
        let naSigunguCd = result.demolitionNoperInfo[0].naSigunguCd;
        if (naSigunguCd == null) {
            naSigunguCd = "";
        }

        $("#daejangBunho").text(daejangBunho);
        $("#dhbh").text(dhbh);
        $("#dhgbh").text(dhgbh);
        $("#dongbh").text(dongbh);
        $("#donggbh").text(donggbh);
        $("#daejang").text(daejang);
        $("#newAddr1").text(newAddr1);
        $("#newAddr2").text(newAddr2);
        $("#bjuso").text(bjuso);
        $("#gmhjdcd").text(gmhjdcd);
        $("#gmhjdnm").text(gmhjdnm);
        $("#gmgzcd").text(gmgzcd);
        $("#gmyd").text(gmyd);
        $("#gmbsdong").text(gmbsdong);
        $("#gmmunjuk").text(gmmunjuk + "㎡ / ");
        $("#gmgunp").text(gmgunp + "평");
        $("#currentGmbtojimjuk").text(currentGmbtojimjuk + "㎡ / ");
        $("#currentGmbtojimj").text(currentGmbtojimj + "평");
        $("#currentGmbmjuk").text(currentGmbmjuk + "㎡ / ");
        $("#currentGmbgunp").text(currentGmbgunp + "평");
        $("#gmgunil").text(gmgunil);
        $("#tojimunjuk").text(tojimunjuk + "㎡ / ");
        $("#tojimj").text(tojimj + "평");
        $("#tojijmg").text(tojijmg);
        $("#tojijmgnm").text(tojijmgnm);
        $("#tojidp").text(tojidp);
        $("#jigujdg").text(jigujdg);
        $("#jigugb").text(jigugb);
        $("#jiguug").text(jiguug);
        $("#jigudz").text(jigudz);
        $("#gmfoors").text(gmfoors);
        $("#tojisg").text(tojisg);

        let formDataStr = '<input type="hidden" name="gmskk" value="' + gmskk + '"/>';
        formDataStr += '<input type="hidden" name="gmdjgb" value="' + gmdjgb + '"/>';
        formDataStr += '<input type="hidden" name="gmseqco" value="' + gmseqco + '"/>';
        formDataStr += '<input type="hidden" name="dhbh" value="' + dhbh + '"/>';
        formDataStr += '<input type="hidden" name="dhgbh" value="' + dhgbh + '"/>';
        formDataStr += '<input type="hidden" name="dongbh" value="' + dongbh + '"/>';
        formDataStr += '<input type="hidden" name="donggbh" value="' + donggbh + '"/>';
        formDataStr += '<input type="hidden" name="daejang" value="' + daejang + '"/>';
        formDataStr += '<input type="hidden" name="gmskkcd" value="' + gmskkcd + '"/>';
        formDataStr += '<input type="hidden" name="gmbjdcd" value="' + gmbjdcd + '"/>';
        formDataStr += '<input type="hidden" name="gmhjdcd" value="' + gmhjdcd + '"/>';
        formDataStr += '<input type="hidden" name="gmcsah" value="' + gmcsah + '"/>';
        formDataStr += '<input type="hidden" name="gmcbuh" value="' + gmcbuh + '"/>';
        formDataStr += '<input type="hidden" name="gmcji" value="' + gmcji + '"/>';
        formDataStr += '<input type="hidden" name="gmcoh" value="' + gmcoh + '"/>';
        formDataStr += '<input type="hidden" name="gmgzcd" value="' + gmgzcd + '"/>';
        formDataStr += '<input type="hidden" name="gmgunp" value="' + gmgunp + '"/>';
        formDataStr += '<input type="hidden" name="gmfoors" value="' + gmfoors + '"/>';
        formDataStr += '<input type="hidden" name="gmyd" value="' + gmyd + '"/>';
        formDataStr += '<input type="hidden" name="gmgunil" value="' + gmgunil + '"/>';
        formDataStr += '<input type="hidden" name="tojimj" value="' + tojimj + '"/>';
        formDataStr += '<input type="hidden" name="tojisg" value="' + tojisg + '"/>';
        formDataStr += '<input type="hidden" name="tojijmg" value="' + tojijmg + '"/>';
        formDataStr += '<input type="hidden" name="tojidp" value="' + tojidp + '"/>';
        formDataStr += '<input type="hidden" name="jigujdg" value="' + jigujdg + '"/>';
        formDataStr += '<input type="hidden" name="jigugb" value="' + jigugb + '"/>';
        formDataStr += '<input type="hidden" name="jiguug" value="' + jiguug + '"/>';
        formDataStr += '<input type="hidden" name="jigudz" value="' + jigudz + '"/>';
        formDataStr += '<input type="hidden" name="gmmunjuk" value="' + gmmunjuk + '"/>';
        formDataStr += '<input type="hidden" name="tojimunjuk" value="' + tojimunjuk + '"/>';
        formDataStr += '<input type="hidden" name="gmbsdong" value="' + gmbsdong + '"/>';
        formDataStr += '<input type="hidden" name="naBjdongNo" value="' + naBjdongNo + '"/>';

        formDataStr += '<input type="hidden" name="naRoadCd" value="' + naRoadCd + '"/>';

        formDataStr += '<input type="hidden" name="naRoadNm" value="' + naRoadNm + '"/>';
        formDataStr += '<input type="hidden" name="naMainBun" value="' + naMainBun + '"/>';
        formDataStr += '<input type="hidden" name="naSubBun" value="' + naSubBun + '"/>';
        formDataStr += '<input type="hidden" name="naEtc" value="' + naEtc + '"/>';
        formDataStr += '<input type="hidden" name="naSigunguCd" value="' + naSigunguCd + '"/>';
        formDataStr += '<input type="hidden" name="newAddr1" value="' + newAddr1 + '"/>';
        formDataStr += '<input type="hidden" name="newAddr2" value="' + newAddr2 + '"/>';

        let str;
        let key;
        let prntnm;
        for (let i = 0; i < result.demolitionIssuerKey.length; i++) {
            key = result.demolitionIssuerKey[i].key;
            prntnm = result.demolitionIssuerKey[i].prntnm;
            if (key == result.demolitionNoperInfo[0].issueorgkey) {
                str += "<option value='" + key + "' selected='selected'>" + prntnm + "</option>";
            } else {
                str += "<option value='" + key + "'>" + prntnm + "</option>";
            }
        }

        let deuhStr;
        let cdkey;
        let cdvalue;
        for (let j = 0; j < result.demolitionDeuh.length; j++) {
            cdkey = result.demolitionDeuh[j].cdkey;
            cdvalue = result.demolitionDeuh[j].cdvalue;
            if (cdkey == result.demolitionNoperInfo[0].deuh) {
                deuhStr += "<option value='" + cdkey + "' selected='selected'>" + cdvalue + "</option>";
            } else {
                deuhStr += "<option value='" + cdkey + "'>" + cdvalue + "</option>";
            }
        }

        $("#issueorgkeyRpt").html(str);
        $("#deuhRpt").html(deuhStr);
        $("#formDataDiv").html(formDataStr);
    });
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