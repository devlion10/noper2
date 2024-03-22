$(document).ready(function(){
    $('#ledgerAddForm input[name=newaddr1]').addClass="is-invalid";
    $('#cansellBtn').click(() => { snAlert.confirm("대장등록을 취소 하시겠습니까?").then((resultChk) => {if (resultChk.isConfirmed) {location.href="/unlcebldmng/ledger";}})});
    $('#listBtn').click(() => {location.href="/unlcebldmng/ledger";});
    $('#newListBtn').click(() => {location.href="/unlcebldmng/newLedger";});
    $('#resetBtn').click(() => { $("#ledgerAddForm")[0].reset();});
    // 주소찾기 btn 있을때만 동작
    if(document.querySelector("#jusoBtn") != null){
         document.querySelector("#jusoBtn").addEventListener("click", (e)=>{
             const w = window.open('/common/juso', 'juso', 'width=500px,height=500px');
             w.focus();
         });
    }


    $('#deleteBtn').click(() => {
        $.post({
            url: "/api/unlicensedBuilingManagement/ledgerDell",
            data: JSON.stringify($("#ledgerAddForm").serializeObject()),
            contentType: 'application/json; charset=utf-8'}).done(
                e=>
                snAlert.success("알림", "삭제 완료").then((result) => {
                      if (result.isConfirmed) {
                          location.href="/unlcebldmng/newLedger";
                      }
                  })
            );
    });

    $('#approvalBtn').click(() => {
        $.post({
            url: "/api/unlicensedBuilingManagement/ledgerApproval",
            data: JSON.stringify($("#ledgerAddForm").serializeObject()),
            contentType: 'application/json; charset=utf-8'}).done(
                e=>
                snAlert.success("알림", "승인 완료").then((result) => {
                      if (result.isConfirmed) {
                          location.href="/unlcebldmng/newLedger";
                      }
                  })
            );
    });

    $('#ledgerAddForm').submit(function(e) {
        e.preventDefault();
        if($("input[name=gmgunilDate]").val() == ""){
          $("input[name=gmgunil]").val("");
        }else{
          $("input[name=gmgunil]").val(moment($("input[name=gmgunilDate]").val()).format('YYYYMMDD'));
        }
        if($("input[name=decisionDate]").val() == ""){
          $("input[name=decision]").val("");
        }else{
          $("input[name=decision]").val(moment($("input[name=decisionDate]").val()).format('YYYYMMDD'));
        }
        $("input[name=tojimj]").attr("disabled", false);
        $("input[name=gmgunp]").attr("disabled", false);
        if($('input[name=gmcsahTemp]').val() == "대지"){
            $('input[name=gmcsah]').val("0");
        }else if($('input[name=gmcsahTemp]').val() == "산"){
            $('input[name=gmcsah]').val("1");
        }else if($('input[name=gmcsahTemp]').val() == "블록"){
            $('input[name=gmcsah]').val("2");
        }else{
          $('input[name=gmcsah]').val("");
        }
        if($('input[name=newaddr1]').val() === "" || $('input[name=newaddr2]').val() === ""){
            $('#ledgerAddForm input[name=newaddr1]').addClass("is-invalid");
            $('#ledgerAddForm input[name=newaddr2]').addClass("is-invalid");
            return;
        }else {
            $('input[name=newaddr1]').removeClass("is-invalid");
            $('input[name=newaddr2]').removeClass("is-invalid");
        }
        document.querySelectorAll('[readonly]').forEach((el) => {
            if(el.value === '') {
                el.classList.remove('is-valid');
                el.classList.add('is-invalid')
            } else {
                el.classList.remove('is-invalid');
                el.classList.add('is-valid')
            }
        })
        if (document.getElementById('ledgerAddForm').checkValidity() == true) {
            snAlert.confirm("신규대장을 등록하시겠습니까?").then((resultChk) => {
                if (resultChk.isConfirmed) {
                    $.post('/api/unlicensedBuilingManagement/ledgerAdd', $("#ledgerAddForm").serializeObject(), function (data) {
                        snAlert.success("알림", "등록 완료").then((result) => {
                           if (result.isConfirmed) {location.href="/unlcebldmng/ledger";}
                        });
                     });
                    return false;
                }
            });
        }
    });
});



  // 주소데이터 받기
  function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn,
     detBdNmList, bdNm, bdKdcd, siNm, sggNm, emdNm, liNm, rn, udrtYn, buldMnnm, buldSlno, mtYn, lnbrMnnm, lnbrSlno, emdNo){
     $('input[name=newaddr1]').val(roadFullAddr);
     $('input[name=newaddr2]').val(jibunAddr);
     $('input[name=gmskkcd]').val(admCd.substring(0, 5)); //시군구코드
     $('input[name=gmbjdcd]').val(admCd.substring(5, 10)); // 법정동코드
     if(mtYn == "0"){ // 대지 산
        $('input[name=gmcsahTemp]').val("대지");
     }else if(mtYn == "1"){
        $('input[name=gmcsahTemp]').val("산");
     }else{
        $('input[name=gmcsahTemp]').val("블록");
     }
     $('input[name=gmcsah]').val(mtYn);
     $('input[name=gmcbuh]').val(lnbrMnnm); // 본번
     $('input[name=gmcji]').val(lnbrSlno); // 부번
     $('input[name=gmcoh]').val(addrDetail); // 새주소그외
     $('input[name=naEtc]').val(addrDetail); // 새주소그외
     $('input[name=naRoadCd]').val(rnMgtSn); // 도로명코드
     $('input[name=naRoadNm]').val(rn); // 도로명
     $('input[name=naSigunguCd]').val(admCd.substring(0, 5));
     $('input[name=naBjdongNo]').val(admCd.substring(5, 8));
     $('input[name=naMainBun]').val(buldMnnm); // 건물본번
     $('input[name=naSubBun]').val(buldSlno); // 건물부번
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



