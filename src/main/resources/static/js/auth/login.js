$(() => {
    // MicroModal Reset
    MicroModal.init({
        disableScroll: false,
        awaitCloseAnimation: true
    })

    /*
    * 로그인 인증 여부로 GPKI 인증 여부를 확인할 지 정한다.
    * 
    * 로그인 인증 O
    * -> GPKI DN 값을 체크(있으면 1, 없으면 0)
    * 로그인 인증 X
    * -> 로그인 팝업 이벤트 추가
    */
    const formElementCheck = document.querySelector('#hiddenForm') != null;
    if (formElementCheck) {
        $.ajax({
            type: "GET",
            url: "/api/gpki-check",
            contentType : "application/json;charset=UTF-8",
            success: (res) => {
                if (res === 0) {
                    MicroModal.show(`login-container`);

                    document.querySelectorAll('.modal-closed').forEach(f => {
                        f.addEventListener('click', () => {
                            location.href = "/logout";
                        })
                    })

                    document.getElementById('loginBtn').addEventListener('click', (e) => {
                        document.getElementById('hiddenLogin').click()
                    });
                }
            }
        });
    } else {
        document.addEventListener('keydown', e => {
            if (e.key === 'Enter' || e.keyCode === '13') {
                const loginContainerCheck = document.getElementById('login-container').getAttribute('aria-hidden');
                if (loginContainerCheck === 'false') {
                    document.getElementById('loginBtn').click()
                }
            }
        })
        document.querySelectorAll('.modal-closed').forEach(f => {
            f.addEventListener('click', () => {
                MicroModal.close(`login-container`);
            })
        })
        document.getElementById('loginBtn').addEventListener('click', (e) => {
            const forms = document.querySelectorAll('.needs-validation')
            Array.from(forms).forEach(form => {
                if (!form.checkValidity()) {
                    e.preventDefault()
                    e.stopPropagation()
                } else {
                    if($('#autoLogin').is(':checked')) {
                        sessionStorage.setItem('atlg', $('#inputId').val() + '-' + $('#inputPw').val())
                    } else {
                        sessionStorage.removeItem('atlg');
                    }
                    $('#login-form').submit()
                }
            })
        });
        document.getElementById('default-login').addEventListener('click', () => {
            MicroModal.show(`login-container`);
            const forms = document.querySelectorAll('.needs-validation')
            Array.from(forms).forEach(form => {
                form.querySelectorAll("input").forEach(f => {
                    f.classList.remove("is-invalid");
                })
            })
        });
        const inputPw = document.querySelector("#inputPw")
        inputPw.addEventListener("blur", () => {
            const pwVal = inputPw.value.trim();
            if (pwVal !== '') {
                inputPw.classList.remove("is-invalid");
            } else {
                inputPw.classList.add("is-invalid");
            }
        });
    }
    /*
    * error parameter -> 에러 문구를 얼럿에 띄우기위해 사용
    * auth parameter -> 메인 화면에서 로그인 팝업을 띄우기 위해 사용
    */
    const errorValue = document.cookie.replace(/(?:(?:^|.*;\s*)error\s*=\s*([^;]*).*$)|^.*$/, "$1");
    if (errorValue != null) errorPrint(errorValue)
    function errorPrint(err) {
        const errorText = {
            0:'인증이 잘못되었습니다.',
            1:'계정이 존재하지 않습니다.\n아이디와 비밀번호를 확인하시거나 관리자에게 문의하세요.',
            2:'비밀번호가 일치하지 않습니다.',
            3:'인증서와 일치하는 사용자가 없습니다.',
            4: () => document.getElementById('default-login').click()
        }
        if(errorText[err] !== undefined) {
            if (err <= 3) {
                snAlert.error(errorText[err])
                    .then(() => {
                        deleteCookie('error')
                    });
            } else {
                errorText[err]()
                deleteCookie('error')
            }
        }
    }
    function deleteCookie(cookieName) {
        document.cookie = cookieName + '=; path=/; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
    }
})
