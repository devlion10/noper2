var validationFlag = false;
$(document).ready(function(){
    $('input[name=popFlag]').val("confirmation");
    $('.delRow').hide();
    $('.modyFom').hide();
    $('#saveBtn').hide();
    $('#deleteBtn').click(() => {
        $('.delRow').show();
        $('.delText').hide();
        $('input[name=delc]').focus();
        if($('#deleteBtn').hasClass("progDel")){
            if($('input[name=delc]').val() == ""){
              snAlert.alert('삭제사유를 입력해주세요.').then((result) => {
                if (result.isConfirmed) {
                   $('input[name=delc]').focus();
                }
              });
            }else{
                $("input[name=confirmationFlag]").val("del");
                $.post({
                    url: "/api/unlicensedBuilingManagement/confirmation",
                    data: JSON.stringify($("#confirmationForm").serializeObject()),
                    contentType: 'application/json; charset=utf-8'}).done((e) =>
                        snAlert.success("알림", "삭제 완료").then((result) => {
                            if (result.isConfirmed) {
                                location.href="/unlcebldmng/confirmation";
                            }
                        })
                );
            }
        }
        $('#deleteBtn').addClass("progDel");
    });

    $("#confirmationListBtn").click(() => {
            location.href="/unlcebldmng/confirmation";
    });

    $("#modyBtn").click(() => {
        $('.modyFom').show();
        $('.detailFom').hide();
        $('#modyBtn').hide();
        $('#saveBtn').show();
    });
    $('#saveBtn').click(async () => {
        $("input[name=confirmationFlag]").val("mody");
        $("input[name=jsilja]").val(moment($("input[name=jsiljaTemp]").val()).format('YYYYMMDD'));
        $("input[name=trilja]").val(moment($("input[name=triljaTemp]").val()).format('YYYYMMDD'));
        const result = await snAlert.confirm('확인원 발급 하시겠습니까?');


        if (result.isConfirmed) {
            $.post({
                url: "/api/unlicensedBuilingManagement/confirmation",
                data: JSON.stringify($("#confirmationForm").serializeObject()),
                contentType: 'application/json; charset=utf-8'
            }).done((e) => {
                    snAlert.success("알림", "등록 완료").then((result) => {
                        //if (result.isConfirmed) {
                        //    location.href="/unlcebldmng/ledger";
                        //}
                    })
            });
        } else {
        }
    });

    /*
    * 기존무허가 대장관리 상세에서 기존무허가 건축물관리 확인원 발급으로 이동했을 때, 자동 데이터 기입 로직
    * modal.js 에 있는 함수를 그대로 사용함.
    */
    const url = new URL(window.location.href);
    const urlGmskk = url.searchParams.get('gmskk');
    const urlGmdjgb = url.searchParams.get('gmdjgb');
    const urlGmseqco = url.searchParams.get('gmseqco');
    if (urlGmskk != null && urlGmdjgb != null && urlGmseqco != null) {
        $.post({
            url:'/common/api/noperNumSearch',
            data: JSON.stringify({
                gmdjgb: `${urlGmdjgb}`,
                gmjjuminTemp1: "",
                gmseqco: `${urlGmseqco}`,
                gmskk: `${urlGmskk}`,
                popFlag: "confirmation"
            }),
            contentType: 'application/json; charset=utf-8',
            success: (data) => {
                const result = {data: data.noperNumList[0]}
                confirmationDetail(result)
            }
        })
    }

    $('#confirmationAddBtn').click(() => {
        $("input[name=confirmationFlag]").val("add");
        $("input[name=jsilja]").val(moment($("input[name=jsiljaTemp]").val()).format('YYYYMMDD'));
        $("input[name=trilja]").val(moment($("input[name=triljaTemp]").val()).format('YYYYMMDD'));

        if($('input[name=gmcsahTemp]').val() == "대지"){
            $('input[name=gmcsah]').val("0");
        }else if($('input[name=gmcsahTemp]').val() == "산"){
            $('input[name=gmcsah]').val("1");
        }else{
            $('input[name=gmcsah]').val("");
          }
        var form = $('#confirmationAddForm');

        validation(form);

        if($('#confirmationAddForm input[name=roadaddr]').val() === "" || $('#confirmationAddForm input[name=jibunaddr]').val() === ""){
            $('#confirmationAddForm input[name=roadaddr]').addClass("is-invalid");
            $('#confirmationAddForm input[name=jibunaddr]').addClass("is-invalid");
            return;
        }else {
            $('#confirmationAddForm input[name=roadaddr]').removeClass("is-invalid");
            $('#confirmationAddForm input[name=jibunaddr]').removeClass("is-invalid");
        }

        //gmskk=11230&amp;gmdjgb=0&amp;gmseqco=128&amp;conilno=202323&amp;jumincheck=1&amp;user=일반사용자
        var userName = $('#confirmationAddForm [name="userName"]').val();
        var gmskk = $('#confirmationAddForm [name="gmskk"]').val();
        var gmdjgb = $('#confirmationAddForm [name="gmdjgb"]').val();
        var gmseqco = $('#confirmationAddForm [name="gmseqco"]').val();
        var conilno = $('#confirmationAddForm [name="conilno"]').val();
        var jumincheck ="";
        if($("#jumincheck").is(':checked')){
            jumincheck = "1";
        }else{
            jumincheck = "0"
        }

        if( gmskk === null || gmskk === "" ){
            return snAlert.alert("대장정보가 없습니다.\n번호찾기 버튼을 클릭하여\n 대장을 선택해주세요.");
        }
        var data = {
            CNFIRM_PRINT: {seal: skkCd, skkCd: skkCd, gmskk: gmskk, gmdjgb: gmdjgb, gmseqco: gmseqco, conilno: conilno, jumincheck: jumincheck, user: userName},
        }
        if(validationFlag) {
            $.post({
                url: "/api/unlicensedBuilingManagement/confirmation",
                data: JSON.stringify($("#confirmationAddForm").serializeObject()),
                contentType: 'application/json; charset=utf-8'
            }).done((result) => {
                snAlert.success("알림", "등록 완료").then((e) => {
                    const conilnoList = result[0].conilnoList
                    const reportData = data.CNFIRM_PRINT
                    conilnoList.forEach(conilno => {
                        snReport.open('CNFIRM_PRINT', {...reportData, conilno: conilno});
                    })
                })
            });
        }
    });
    /*
    $("#modyBtn").click(() => {
        const form = $('#confirmationForm')[0];
        var param = "gmskk="+form.gmskk.value+"&gmdjgb="+form.gmdjgb.value+"&gmseqco="+form.gmseqco.value+"&conilno="+form.conilno.value+"&flag=mody";
        location.href="/unlcebldmng/confirmationAdd?"+param;
     });
     */
    // 주소찾기 btn 있을때만 동작
    if(document.querySelector("#jusoBtn") != null){
         document.querySelector("#jusoBtn").addEventListener("click", (e)=>{
             const w = window.open('/common/juso', 'juso', 'width=500px,height=500px');
             w.focus();
         });
    }
});

 function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn,
     detBdNmList, bdNm, bdKdcd, siNm, sggNm, emdNm, liNm, rn, udrtYn, buldMnnm, buldSlno, mtYn, lnbrMnnm, lnbrSlno, emdNo){

     $('input[name=roadaddr]').val(roadFullAddr);
     $('input[name=jibunaddr]').val(jibunAddr);
     //$('input[name=gmskkcd]').val(admCd.substring(0, 5));
     //$('input[name=gmbjdcd]').val(admCd.substring(5, 10));
     if(mtYn == "0"){ // 대지 산
         $('input[name=gmcsahTemp]').val("대지");
     }else{
          $('input[name=gmcsahTemp]').val("산");
     }
     //$('#formGmcbuh').val(lnbrMnnm);
     //$('#formGmcji').val(lnbrSlno);
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