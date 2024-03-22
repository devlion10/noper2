(()=> {
    document.getElementById('container').classList.add('justCenter')

    const form = document.getElementById('formId');
    const actionUrl = form.getAttribute('action')
    document.addEventListener('submit', (e) => {
        e.preventDefault();
        e.stopPropagation();
        if (actionUrl === "/reset-pw") {
            const pw = document.querySelector("#pw")
            const repw = document.querySelector("#repw");
            const pwVal = pw.value;
            const repwVal = repw.value;
            if (pwVal === repwVal && repwVal !== '') {
                repw.classList.remove("is-invalid");
            } else {
                repw.classList.add("is-invalid");
            }
        }
        if (form.checkValidity() === false) return false;
        ajax()
    })
    if (actionUrl === "/reset-pw") {
        const pw = document.querySelector("#pw")
        const repw = document.querySelector("#repw")
        if (pw.value !== repw.value) $('#repw').val('')
        repw.addEventListener("blur", () => {
            const pwVal = pw.value;
            const repwVal = repw.value;
            if (pwVal === repwVal && repwVal !== '') {
                repw.classList.remove("is-invalid");
            } else {
                repw.classList.add("is-invalid");
            }
        });
        document.getElementById('cancel').addEventListener('click', () => {
            snAlert.confirm('새 비밀번호 입력을 종료 후\n메인화면으로 이동하시겠습니까?')
                .then((e) => {
                    if (e.isConfirmed) {
                        location.href = "/";
                    }
                })
        });
    }
    function ajax() {
        const inputData = $('#formId').serializeObject()
        if (actionUrl === '/find-pw') {
            inputData.emailName = inputData.emailName.toLowerCase();
            inputData.emailDomain = inputData.emailDomain.toLowerCase()
        }
        $.ajax({
            type: "POST",
            url: `/api${actionUrl}`,
            data: inputData,
            success: (res) => {
                if (actionUrl === '/find-pw') {
                    if (res.bool === 1) {
                        $('#formId').submit();
                    } else {
                        snAlert.error('해당 정보와 일치하는 사용자를 찾을 수 없습니다.');
                    }
                } else {
                    if (res === 1) {
                        snAlert.success('비밀번호 재설정이 완료되었습니다.\n변경된 비밀번호로 로그인 해주세요.')
                            .then(() => {
                                location.href = '/login-fail?error=4'
                            })
                    }
                }
            },
        });
    }
})();