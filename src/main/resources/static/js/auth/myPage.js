$(() => {
    document.getElementById('container').classList.add('justCenter')

    $('#newPwCk').on('blur', function () {
        const repw = $('#newPwCk')[0];
        const npw = $('#newPw')[0];
        const pwVal = $('#newPw').val();
        const repwVal = $('#newPwCk').val();
        if (pwVal === repwVal && repwVal !== '') {
            repw.classList.remove("is-invalid");
        } else {
            repw.classList.add("is-invalid");
        }
        if(!repw.classList.contains("is-invalid") && !npw.classList.contains("is-invalid")) {
            $('#changePw').removeAttr('disabled');
        } else {
            $('#changePw').attr('disabled', true);
        }
    })

    $('#cancelBtn').click(() => location.href = '/')

    $('#changePw').click(function () {
        $('#modalUserId').val($('#userId').val());
        $.post('/api/user/update/password', $('#resetPasswordModalForm').serialize(), function (result) {
            if(result === 'success') {
                snAlert.alert('비밀번호가 변경되었습니다.').then(() => {$('#findPasswordModal').modal('toggle')});
            } else {
                snAlert.alert('기존 비밀번호를 다시 입력해주세요')
            }
        })
    })

    $('#formId').submit(function (e) {
        const form = $(this)[0];
        e.preventDefault();
        e.stopPropagation();
        if (form.checkValidity() === false) return false;

        snAlert.confirm('사용자 정보를 수정하시겠습니까?').then((a) => {
            if (a.isConfirmed) {
                const userId = document.getElementById(`userId`).getAttribute(`data-userid`)
                $.ajax({
                    type: "POST",
                    url: "/api/user/info/update",
                    data: JSON.stringify({
                        userId: `${userId}`,
                        userName: `${form.userName.value}`,
                        personalId: `${form.personalId1.value}${form.personalId2.value}`.toLowerCase(),
                        eMail: `${form.eMail1.value}@${form.eMail2.value}`,
                        telNo: `${form.telNo.value}`,
                    }),
                    contentType : "application/json;charset=UTF-8",
                    success: function () {
                        const msg = '수정이 완료되었습니다.'
                        snAlert.alert(msg)
                    },
                });
            }
        })
    });
})