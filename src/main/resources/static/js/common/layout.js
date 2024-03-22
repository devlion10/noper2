$(async () => {
    // Logout Button Alert
    $("#logout").click(() => {
        snAlert.confirm("로그아웃 하시겠습니까?")
            .then((result) => {
                if (result.isConfirmed) {
                    location.href = "/logout";
                }
            })
    })
    // Session Time Button 
    $("#timerBtn").click(async () => {
        timer = await sessionTime() * 60
    })
    // security session check
    const sessionCheck = await $.ajax({
        type: "GET",
        url: "/api/session-check",
        success: (data) => {
            return data;
        },
    });
    // 기본 세션 유지 시간 조회 값 - timer = 초단위
    let timer = await sessionTime() * 60;
    let interval;
    // 세션 확인 후, 타이머 작동
    if (sessionCheck) {
        startInterval()
    }
    // 시간을 mm:ss 형태로 포맷 후, 화면 변경
    function displayTimer(timer) {
        const fmtTime = formatTime(timer);
        $('#time').html(fmtTime)
    }
    // 시간을 mm:ss 형태로 포맷
    function formatTime(timer) {
        const firstFormat = Math.floor(timer / 60);
        const secondFormat = timer % 60;
        const firstCheck = () => {
            // 1분이면 001분으로 변경함 (빈자리에 0을 채움)
             switch(firstFormat.toString().length) {
                case 1:
                    return `00${firstFormat}`
                case 2:
                    return `0${firstFormat}`
                case 3:
                    return `${firstFormat}`
            }
        }
        const secondCheck = secondFormat.toString().length === 1 ? `0${secondFormat}` : secondFormat
        return `${firstCheck()}:${secondCheck}`
    }
    // 세션 유지 기본 값 조회
    function sessionTime() {
        return  $.ajax({
            type: "GET",
            url: "/api/session-time",
            success: (data) => {
                return data;
            },
        })
    }
    /*
    [ 1초마다 실행되는 함수 ]
    10분 혹은 3분 남았을 경우 시간 연장 메시지

    연장을 할 시:
        - timer 객체 값 초기화
        - 새로운 세션 시간을 가져와서 분 단위로 변환하여 설정
        - startInterval 함수 호출하여 타이머 시작

    연장을 안할 시:
        - 남은 시간이 10분인지 체크
        - 10분이라면, 타이머 값을 599초 (5분 59초)로 설정 (600초로 할 경우, 무한루프상태가 됨)
        - 3분이라면, 타이머값을 179초 (2분 59초)로 설정 (180초로 할 경우, 무한루프상태가 됨)
        - startInterval 함수 호출하여 타이머 이어나감
    */
    function startInterval() {
        interval = setInterval(() => {
            // 초 단위인 timer 를 분 단위로 변경
            const replaceTimer = timer / 60
            // 10분 또는 3분 남았을 때
            if (replaceTimer === 10 || replaceTimer === 3 ) {
                clearInterval(interval)
                // 화면 표출 시간 조정
                displayTimer(replaceTimer === 10 ? 600 : 180)
                Swal.fire({
                    icon: 'question',
                    title: `${replaceTimer}분 후 자동 로그아웃 됩니다.\n로그인 시간을 연장 하시겠습니까?`,
                    showDenyButton: false,
                    showCancelButton: true,
                    confirmButtonText: '로그인 연장',
                    cancelButtonText: `취소`
                }).then(async (result) => {
                    if (result.isConfirmed) {
                        timer = await sessionTime() * 60;
                        startInterval()
                    } else {
                        timer = replaceTimer === 10 ? 599 : 179
                        startInterval()
                    }
                });
            } else if (timer > 0) {
                displayTimer(timer)
                timer--;
            } else {
                clearInterval(interval)
                snAlert.alert('세션정보가 만료되었습니다.\n다시 로그인해 주십시오.')
                    .then(() => {
                        location.href = '/logout'
                    })
            }
        }, 1000)
    }

    // 전역 Form Validation
    (() => {
        'use strict'
        const forms = document.querySelectorAll('.needs-validation')
        Array.from(forms).forEach(form => {

            form.querySelectorAll("input[required],select[required],textarea[required]").forEach(e=>{
                const minlength = e.getAttribute("minlength");
                const maxlength = e.getAttribute("maxlength");
                const min = e.getAttribute("min");
                const max = e.getAttribute("max");
                const pattern = e.getAttribute("pattern");

                const fEmpty = ()=>e.value.trim() == "" ? true :false;
                const fMinLength = minlength ? ()=>e.value.length < parseInt(minlength) : ()=>false;
                const fMaxLength = maxlength ? ()=>e.value.length > parseInt(maxlength) : ()=>false;
                const fMin = min ? ()=>parseInt(e.value) < min : ()=>false;
                const fMax = max ? ()=>parseInt(e.value) > max : ()=>false;
                const fPattern = pattern ? ()=>!new RegExp(pattern, 'igm').test(e.value) : ()=>false;

                e.addEventListener("blur", (m)=>{
                    if (m.target.getAttribute('disableCommon') === 'true') return false;
                    if (fEmpty() || fMinLength() || fMaxLength() || fMin() || fMax() || fPattern()) {
                        e.classList.add("is-invalid");
                    } else {
                        e.classList.remove("is-invalid");
                    }
                });
            });

            form.addEventListener('submit', event => {
                if (!form.checkValidity()) {
                    event.preventDefault()
                    event.stopPropagation()
                }
                form.classList.add('was-validated')
            }, false)
        })
    })();
})

// 스크롤 탑버튼 표출
window.addEventListener('scroll', () => {
    const scrollTop = $(window).scrollTop() === 0;
    const element = document.getElementById('topButton');

    if (scrollTop) {
        if(element != null) element.parentNode.removeChild(element)
    } else {
        const elementHTML =
            `<div id="topButton" class="topButtonContainer">
                <button onclick="window.scrollTo({top: 0, left: 0, behavior: 'instant'})" class="topButton">
                    <i class="bi bi-chevron-up"></i>
                </button>
                <p class="topButtonTx">Top</p>
             </div>`
        if(element == null) document.body.insertAdjacentHTML('beforeend', elementHTML);
    }
})

/**
 * input 숫자 입력 input tag 글자 제한
 * @param element input element
 * @param maxLength : int 입력최대길이
 */
function numberMaxLength(element, maxLength) {
    element.value = element.value.replace(/\D/g, '').slice(0, maxLength);
}

/**
 * input tag 입력 값을 upperCase 시키고 반환
 * @param element input element
 */
function textUppercase(element) {
    element.value = element.value.toUpperCase()
}



