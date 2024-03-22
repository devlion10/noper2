$(document).ready(function(){

   /* // 각자 화면 개발할 때의
    const type = {
        type:'ledgerGreenBeltLayout',
        fileName: 750,
        modalId: 'modal-1',
        size: 200,
        readOnly: 'N'
    }
    const uploader = new snFileUpload.Uploader("myId", type);*/

    $('#listBtn').click(() => {location.href="/bldmngingb/ledger";});
    //$('#resetBtn').click(() => { $("#ledgerAddForm")[0].reset();});
    $('#resetBtn').click(() => {
        $("#ledgerLimitedAddForm")[0].reset();
        /*$("#ledgerOwnerAddForm")[0].reset();
        $("#ledgerDongAddForm")[0].reset();
        $("#legerStructuresAddForm")[0].reset();
        $("#legerGroundAddForm")[0].reset();
        $("#legerPermitAddForm")[0].reset();
        $("#siteChkAddForm")[0].reset();*/
    });

    $('#saveBtn').click(() => {
        // ledgerLimitedAddForm 폼 전체 유효성 검사
        document.querySelectorAll('[readonly]').forEach((el) => {
            if(el.value === '') {
                el.classList.remove('is-valid');
                el.classList.add('is-invalid')
            } else {
                el.classList.remove('is-invalid');
                el.classList.add('is-valid')
            }
        })
        if (!$("#ledgerLimitedAddForm")[0].checkValidity()) {
            snAlert.error("알림", "필수 입력 항목을 확인해주세요.");
            return;
        }

        let data = {
            legerLimited: $("#ledgerLimitedAddForm").serializeObject()
            /*legerOwner: $("#ledgerOwnerAddForm").serializeObject(),
            legerDong: $("#ledgerDongAddForm").serializeObject(),
            legerStructures: $("#legerStructuresAddForm").serializeObject(),
            legerGround: $("#legerGroundAddForm").serializeObject(),
            legerPermit: $("#legerPermitAddForm").serializeObject()*/
        };

        /*$.post({
            url: '/api/bldmngingb/ledgerAdmSeq',
            success: (res) => {
                uploader.parentKey = res
                const reFlagVal = $('#fileFlagTemp').val()
                uploader.setFlag(reFlagVal);
                uploader.uploadFiles();
                console.log(res);
            }
        });*/

        snAlert.confirm('저장하시겠습니까?').then((res) => {
            if (res.isConfirmed) {
                $.post({
                    url: "/api/bldmngingb/ledgerAdd",
                    data: JSON.stringify(data),
                    contentType: 'application/json; charset=utf-8'
                }).done(
                    e => snAlert.success("알림", "등록 완료").then((result) => {
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


});


