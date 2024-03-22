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
    * 기존무허가 대장관리 상세에서 기존무허가 건축물관리 부분철거신고등록으로 이동했을 때, 자동 데이터 기입 로직
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

// 부분철거신고 버튼
$("#btnPartDemolitionMovePage").click(() => {
    let daejangBunho = $("#daejangBunho").text();

    if ( daejangBunho == '' ) {
        $("#gmskk").focus();
        snAlert.alert("대장내역이 없습니다.\n대장내역을 먼저 조회하세요.");
    } else {
        snAlert.confirm("위 내역으로 대장을\n부분철거신고 하시겠습니까?\n부분철거신고 후 건물주보상현황 및 세입자현황을 추가하시기 바랍니다.").then((resultChk) => {
            if (resultChk.isConfirmed) {
                $("#partDemolitionDetailMovePageForm").submit();
            }
        });
    }
});

// 취소
$("#cancelBtn").click(() => {
    snAlert.confirm("확인을 누르시면 입력한 정보가\n 모두 삭제됩니다.\n그래도 진행하시겠습니까?").then((resultChk) => {
        if (resultChk.isConfirmed) {
            $("#listBtn").click();
        }
    });
});

// function movePageDetail(gmskk, gmdjgb, gmseqco){
//     let str = '';
//         str += '<input type="hidden" name="gmskk" id="gmskkDtl" value="'+gmskk+'"/>';
//         str += '<input type="hidden" name="gmdjgb" id="gmdjgbDtl" value="'+gmdjgb+'"/>';
//         str += '<input type="hidden" name="gmseqco" id="gmseqcoDtl" value="'+gmseqco+'"/>';
//     $("#demolitionDetailForm").append(str);
//     $("#demolitionDetailForm").submit();
// }

// 검색 조건 - 새로고침 버튼 클릭 시 form Reset
$("#btnReset").click(() => {
    document.getElementById("partDemolitionReportSearchForm").reset();
});

// 기본현황, 대장내역, 건물 소유자현황 조회
$("#btnSearch").click(() => {
    let gmskk = $("#gmskk").val();
    let gmdjgb = $("#gmdjgb").val();
    let gmseqco = $("#gmseqco").val();

    if (gmskk === '' || gmdjgb === '' || gmseqco === '') {
        snAlert.alert("조회할 대장번호를 입력해주세요.");
        return;
    }

    const partDemolitionReportSearchForm = $("#partDemolitionReportSearchForm").serializeObject();

    let url = "/api/unlicensedBuilingManagement/partDemolition/add/search";
    let type = "post";
    let json = JSON.stringify(partDemolitionReportSearchForm);
    let contentType = "application/json";

    ajaxCall(url, type, json, contentType, function (result) {
        if (result.partDemolitionAddNoperInfo === null || result.partDemolitionAddNoperInfo === undefined) {
            snAlert.alert('해당 대장은 없는 대장입니다.')
            return;
        }
        if (result.partDemolitionAddNoperInfo[0].degb === 'Y') {
            snAlert.alert('철거된 대장입니다.')
            return;
        }

        let gmskk = result.partDemolitionAddNoperInfo[0].gmskk;
        let gmdjgb = result.partDemolitionAddNoperInfo[0].gmdjgb;
        let gmseqco = result.partDemolitionAddNoperInfo[0].gmseqco;
        let daejangBunho = result.partDemolitionAddNoperInfo[0].daejangBunho;
        let dhbh = result.partDemolitionAddNoperInfo[0].dhbh;
        let dhgbh = result.partDemolitionAddNoperInfo[0].dhgbh;
        let dongbh = result.partDemolitionAddNoperInfo[0].dongbh;
        let donggbh = result.partDemolitionAddNoperInfo[0].donggbh;
        let daejang = result.partDemolitionAddNoperInfo[0].daejang;
        let newAddr1 = result.partDemolitionAddNoperInfo[0].newAddr1;
        let newAddr2 = result.partDemolitionAddNoperInfo[0].newAddr2;
        let gmhjdcd = result.partDemolitionAddNoperInfo[0].gmhjdcd;
        let gmhjdnm = result.partDemolitionAddNoperInfo[0].gmhjdnm;
        let gmgzcd = result.partDemolitionAddNoperInfo[0].gmgzcd;
        let gmyd = result.partDemolitionAddNoperInfo[0].gmyd;
        let gmbsdong = result.partDemolitionAddNoperInfo[0].gmbsdong;
        let gmmunjuk = result.partDemolitionAddNoperInfo[0].gmmunjuk;
        let gmgunp = result.partDemolitionAddNoperInfo[0].gmgunp;
        let currentGmbtojimjuk = result.partDemolitionAddNoperInfo[0].currentGmbtojimjuk;
        let currentGmbtojimj = result.partDemolitionAddNoperInfo[0].currentGmbtojimj;
        let currentGmbmjuk = result.partDemolitionAddNoperInfo[0].currentGmbmjuk;
        let currentGmbgunp = result.partDemolitionAddNoperInfo[0].currentGmbgunp;
        let gmgunil = result.partDemolitionAddNoperInfo[0].gmgunil;
        let tojimunjuk = result.partDemolitionAddNoperInfo[0].tojimunjuk;
        let tojimj = result.partDemolitionAddNoperInfo[0].tojimj;
        let tojijmg = result.partDemolitionAddNoperInfo[0].tojijmg;
        let tojijmgnm = result.partDemolitionAddNoperInfo[0].tojijmgnm;
        let tojidp = result.partDemolitionAddNoperInfo[0].tojidp;
        let jigujdg = result.partDemolitionAddNoperInfo[0].jigujdg;
        let jigugb = result.partDemolitionAddNoperInfo[0].jigugb;
        let jiguug = result.partDemolitionAddNoperInfo[0].jiguug;
        let jigudz = result.partDemolitionAddNoperInfo[0].jigudz;
        let gmfoors = result.partDemolitionAddNoperInfo[0].gmfoors;
        let tojisg = result.partDemolitionAddNoperInfo[0].tojisg;

        let gmskkcd = result.partDemolitionAddNoperInfo[0].gmskkcd;
        let gmbjdcd = result.partDemolitionAddNoperInfo[0].gmbjdcd;
        let gmcsah = result.partDemolitionAddNoperInfo[0].gmcsah;
        let gmcbuh = result.partDemolitionAddNoperInfo[0].gmcbuh;
        let gmcji = result.partDemolitionAddNoperInfo[0].gmcji;
        let gmcoh = result.partDemolitionAddNoperInfo[0].gmcoh;

        let naBjdongNo = result.partDemolitionAddNoperInfo[0].naBjdongNo;
        if (naBjdongNo == null){
            naBjdongNo = "";
        }
        let naRoadCd = result.partDemolitionAddNoperInfo[0].naRoadCd;
        if (naRoadCd == null){
            naRoadCd = "";
        }
        let naRoadNm = result.partDemolitionAddNoperInfo[0].naRoadNm;
        if (naRoadNm == null){
            naRoadNm = "";
        }
        let naMainBun = result.partDemolitionAddNoperInfo[0].naMainBun;
        if (naMainBun == null){
            naMainBun = "";
        }
        let naSubBun = result.partDemolitionAddNoperInfo[0].naSubBun;
        if (naSubBun == null){
            naSubBun = "";
        }
        let naEtc = result.partDemolitionAddNoperInfo[0].naEtc;
        if (naEtc == null){
            naEtc = "";
        }
        let naSigunguCd = result.partDemolitionAddNoperInfo[0].naSigunguCd;
        if (naSigunguCd == null){
            naSigunguCd = "";
        }

        $("#gmskkMpIp").val(gmskk);
        $("#gmdjgbMpIp").val(gmdjgb);
        $("#gmseqcoMpIp").val(gmseqco);

        $("#daejangBunho").text(daejangBunho);
        $("#dhbh").text(dhbh);
        $("#dhgbh").text(dhgbh);
        $("#dongbh").text(dongbh);
        $("#donggbh").text(donggbh);
        $("#daejang").text(daejang);
        $("#newAddr1").text(newAddr1);
        $("#newAddr2").text(newAddr2);
        $("#gmhjdcd").text(gmhjdcd);
        $("#gmhjdnm").text(gmhjdnm);
        $("#gmgzcd").text(gmgzcd);
        $("#gmyd").text(gmyd);
        $("#gmbsdong").text(gmbsdong);
        $("#gmmunjuk").text(gmmunjuk+"㎡/ ");
        $("#gmgunp").text(gmgunp+"평");
        $("#currentGmbmjuk").text(currentGmbmjuk+"㎡/ ");
        $("#currentGmbgunp").text(currentGmbgunp+"평");
        $("#gmgunil").text(gmgunil);
        $("#tojimunjuk").text(tojimunjuk+"㎡/ ");
        $("#tojimj").text(tojimj+"평");
        $("#currentGmbtojimjuk").text(currentGmbtojimjuk+"㎡/ ");
        $("#currentGmbtojimj").text(currentGmbtojimj+"평");
        $("#tojijmg").text(tojijmg);
        $("#tojijmgnm").text(tojijmgnm);
        $("#tojidp").text(tojidp);
        $("#jigujdg").text(jigujdg);
        $("#jigugb").text(jigugb);
        $("#jiguug").text(jiguug);
        $("#jigudz").text(jigudz);
        $("#gmfoors").text(gmfoors);
        $("#tojisg").text(tojisg);
        $("#gmcoh").text(gmcoh);

        let formDataStr = '<input type="hidden" name="gmskk" value="'+gmskk+'"/>';
        formDataStr += '<input type="hidden" name="gmdjgb" value="'+gmdjgb+'"/>';
        formDataStr += '<input type="hidden" name="gmseqco" value="'+gmseqco+'"/>';
        formDataStr += '<input type="hidden" name="dhbh" value="'+dhbh+'"/>';
        formDataStr += '<input type="hidden" name="dhgbh" value="'+dhgbh+'"/>';
        formDataStr += '<input type="hidden" name="dongbh" value="'+dongbh+'"/>';
        formDataStr += '<input type="hidden" name="donggbh" value="'+donggbh+'"/>';
        formDataStr += '<input type="hidden" name="daejang" value="'+daejang+'"/>';
        formDataStr += '<input type="hidden" name="gmskkcd" value="'+gmskkcd+'"/>';
        formDataStr += '<input type="hidden" name="gmbjdcd" value="'+gmbjdcd+'"/>';
        formDataStr += '<input type="hidden" name="gmhjdcd" value="'+gmhjdcd+'"/>';
        formDataStr += '<input type="hidden" name="gmcsah" value="'+gmcsah+'"/>';
        formDataStr += '<input type="hidden" name="gmcbuh" value="'+gmcbuh+'"/>';
        formDataStr += '<input type="hidden" name="gmcji" value="'+gmcji+'"/>';
        formDataStr += '<input type="hidden" name="gmcoh" value="'+gmcoh+'"/>';
        formDataStr += '<input type="hidden" name="gmgzcd" value="'+gmgzcd+'"/>';
        formDataStr += '<input type="hidden" name="gmgunp" value="'+gmgunp+'"/>';
        formDataStr += '<input type="hidden" name="gmfoors" value="'+gmfoors+'"/>';
        formDataStr += '<input type="hidden" name="gmyd" value="'+gmyd+'"/>';
        formDataStr += '<input type="hidden" name="gmgunil" value="'+gmgunil+'"/>';
        formDataStr += '<input type="hidden" name="tojimj" value="'+tojimj+'"/>';
        formDataStr += '<input type="hidden" name="tojisg" value="'+tojisg+'"/>';
        formDataStr += '<input type="hidden" name="tojijmg" value="'+tojijmg+'"/>';
        formDataStr += '<input type="hidden" name="tojidp" value="'+tojidp+'"/>';
        formDataStr += '<input type="hidden" name="jigujdg" value="'+jigujdg+'"/>';
        formDataStr += '<input type="hidden" name="jigugb" value="'+jigugb+'"/>';
        formDataStr += '<input type="hidden" name="jiguug" value="'+jiguug+'"/>';
        formDataStr += '<input type="hidden" name="jigudz" value="'+jigudz+'"/>';
        formDataStr += '<input type="hidden" name="gmmunjuk" value="'+gmmunjuk+'"/>';
        formDataStr += '<input type="hidden" name="tojimunjuk" value="'+tojimunjuk+'"/>';
        formDataStr += '<input type="hidden" name="gmbsdong" value="'+gmbsdong+'"/>';
        formDataStr += '<input type="hidden" name="naBjdongNo" value="'+naBjdongNo+'"/>';

        formDataStr += '<input type="hidden" name="naRoadCd" value="'+naRoadCd+'"/>';

        formDataStr += '<input type="hidden" name="naRoadNm" value="'+naRoadNm+'"/>';
        formDataStr += '<input type="hidden" name="naMainBun" value="'+naMainBun+'"/>';
        formDataStr += '<input type="hidden" name="naSubBun" value="'+naSubBun+'"/>';
        formDataStr += '<input type="hidden" name="naEtc" value="'+naEtc+'"/>';
        formDataStr += '<input type="hidden" name="naSigunguCd" value="'+naSigunguCd+'"/>';

        $("#formDataDiv").html(formDataStr);
    });
});

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