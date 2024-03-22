document.addEventListener('DOMContentLoaded', () => {
    let joinClicked = false;
    let allowClicked = false;
    let compClicked = false;
    keyEventUtil.setInputTypeByForm('joinForm')

    // 가입 신청 목록이 아닌 자치구 선택영역이 있다면 ( 등록, 수정 )
    const notList = new URL(location.href).pathname !== '/board/user/list'


    if(document.getElementById('skkCd') != null ) {
       document.getElementById('skkCd').addEventListener('change', (e) => {
        let bdjElement = document.getElementById('bjdCd')
        let dptElement = document.getElementById('deptCd')
        let bjdCdDirect = document.getElementById('bjdCdDirect')
        let deptDirect = document.getElementById('deptDirect')
        const skkCd = e.target.value
          if(notList){
            fnSetDisplay(bdjElement,dptElement,true)   // 부서,동 초기화
            fnSetDisabled(bjdCdDirect,deptDirect) // 직접입력 창 초기화
            if(document.getElementById('instt') != null) document.getElementById('instt').value = '' // 소속 선택 ( 구청 , 동사무소 ) // 초기화

            // 자치구별 이메일 가져오기 (가입신청)
            $.ajax({
                      type: "GET",
                      url: "/api/code/email/list",
                      data: `skkCd=${skkCd}`,
                      dataType: "json",
                      success: function (data) {
                        let emailValue = "seoul.go.kr";
                        if(data.length !== 0){
                            emailValue =  data[0].cdvalue;
                        }
                            $('#eMail2').val(emailValue);
                      }
                  });
          }else{
            if(skkCd === '11000'){
                let options = "<option value='' selected>전체</option>";
                bdjElement.innerHTML = options;
            }else{
              $.ajax({
                  type: "GET",
                  url: "/api/code/bjdong/list",
                  data: `skkCd=${skkCd}`,
                  dataType: "json",
                  success: function (data) {
                      let options = "<option value='' selected>전체</option>";
                      for(const bjdInfo of data) {
                          options += `<option value="${bjdInfo.key}">${bjdInfo.dpname}</option>`
                      }
                      bdjElement.innerHTML = options;
                  }
              });
            }
          }
       })
        // 직접입력
        if (notList) {
            document.getElementById('bjdCd').addEventListener('change', (e) => {
                const bjdCdDirect = document.getElementById('bjdCdDirect')
                const bjdCd = e.target.value;
                if (bjdCd === 'direct') {
                    bjdCdDirect.removeAttribute('disabled')
                    bjdCdDirect.style.display = "block"
                    bjdCdDirect.value = ''
                } else {
                    bjdCdDirect.setAttribute('disabled', 'true')
                    bjdCdDirect.value = ''
                    bjdCdDirect.classList.remove('is-invalid')
                    bjdCdDirect.style.display = "none"
                }
            })
        }
    }



    // 소속 : 구청 또는 동사무소 선택 시
        if(document.getElementById('instt') != null){
            document.getElementById('instt').addEventListener('change',(e) => {
                const insttValue = e.target.value
                const skkCd = document.getElementById('skkCd').value
                let dptElement = document.getElementById('deptCd')
                let bdjElement = document.getElementById('bjdCd')
                let bjdCdDirect = document.getElementById('bjdCdDirect')
                let deptDirect = document.getElementById('deptDirect')

                fnSetDisplay(dptElement,bdjElement,true) // 부서,동 select box 초기화
                fnSetDisabled(bjdCdDirect,deptDirect) // 직접입력 창 초기화

                if (skkCd !== "") {
                    if (insttValue === 'gu') { // 구청인 경우 부서를 불러오기
                        $.ajax({
                            type: "GET",
                            url: "/api/code/dept/list",
                            data: `skkCd=${skkCd}`,
                            dataType: "json",
                            success: function (data) {
                                let dptOption = "<option value='' selected>부서 선택</option>";
                                dptOption += `<option value="direct">직접입력</option>`;
                                for (const dptInfo of data) {
                                    if (dptInfo.cdkey !== '11110') {
                                        dptOption += `<option value="${dptInfo.cdkey}">${dptInfo.cdvalue}</option>`
                                    }
                                }
                                let options = `<option value="000" selected>구청</option>`; //동 정보 값 설정
                                dptElement.innerHTML = dptOption;
                                bdjElement.innerHTML = options;
                            }
                        });
                        dptElement.style.display = "block";

                    } else if (insttValue === 'dong') { // 동사무소인 경우 동 정보 불러오기
                        $.ajax({
                            type: "GET",
                            url: "/api/code/bjdong/list",
                            data: `skkCd=${skkCd}`,
                            dataType: "json",
                            success: function (data) {
                                let options = `<option value="" selected>동 선택</option>`;
                                if (notList) {
                                    options += `<option value="direct">직접입력</option>`;
                                }
                                for (const bjdInfo of data) {
                                    if (bjdInfo.key !== '000') {
                                        options += `<option value="${bjdInfo.key}">${bjdInfo.dpname}</option>`
                                    }
                                }
                                let dptOption = "<option value='11110' selected>동사무소</option>"; // 부서 값 지정
                                dptElement.innerHTML = dptOption;
                                bdjElement.innerHTML = options;
                            }
                        });
                        bdjElement.style.display = "block";
                    }
                }

            });
        }

    // 직접입력
    if (document.getElementById('deptCd') != null && notList) {
        document.getElementById('deptCd').addEventListener('change', (e) => {
            const deptDirect = document.getElementById('deptDirect')
            if (e.target.value === 'direct') {
                deptDirect.removeAttribute('disabled')
                deptDirect.style.display = "block"
                deptDirect.value = ''
            } else {
                deptDirect.setAttribute('disabled', 'true')
                deptDirect.style.display = "none"
                deptDirect.classList.remove('is-invalid')
                deptDirect.value = ''
            }
        })
    }



    if(document.getElementById('reload') != null) {
        document.getElementById('reload').onclick = () => {
            location.href = "/board/user/list"
        };
    }
    if(document.getElementById('joinWriteBtn') != null ) document.getElementById('joinWriteBtn').onclick = () => { location.href = "/board/user/add" };
    if(document.getElementById('joinCancelBtn') != null ) {
        document.getElementById('joinCancelBtn').onclick = async () => {
            const alert = await snAlert.confirm('작성중인 가입신청서를\n초기화 하시겠습니까?')
            if (alert.isConfirmed) {
                $('#joinForm select, #joinForm input').each(function() {
                    $(this)[0].id === 'eMail2' ? $(this).val('seoul.go.kr') : $(this).val('')
                });
            }
        };
    }
    const listBtn = document.getElementById('listBtn');
    if (listBtn != null) {
        listBtn.onclick = async () => {
         const currentPathname = new URL(location.href).pathname;
            if (currentPathname === '/board/user/info') {
                location.href = "/board/user/list";
            }else{
                const alert = await snAlert.confirm('작성중인 가입신청서를 취소하고\n목록으로 이동하시겠습니까?')
                if (alert.isConfirmed) location.href = "/board/user/list";
            }
        }
    }

    if(document.getElementById('joinModifyBtn') != null ) document.getElementById('joinModifyBtn').onclick = () => {
        const form = document.createElement("form");
        const userIdElement = document.createElement("input");
        form.appendChild(userIdElement);
        document.body.appendChild(form);
        userIdElement.type = 'text';
        userIdElement.name = 'userId';
        userIdElement.value = document.getElementById('userId').value;
        form.action = '/board/user/modify';
        form.method = 'post';
        form.submit();
    };

    if(document.getElementById('joinForm') != null ) {
        document.getElementById('joinForm').addEventListener('submit', (e) => {
            e.preventDefault();
            e.stopPropagation();
            const form = e.target;
            /*
            const stringReg = /[가-힣\w\s]/g;
            const personalIdStartReg = /^\d{6}$/g;
            const personalIdEndReg = /^\d{7}$/g;
            const emailStartReg = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*!/g;
            const emailEndReg = /[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
            const telReg = /\d{10,11}/g;
            */
            if (form.checkValidity() === false) {
                return false;
            // } else if () {
            //     d
            } else if(!joinClicked) {
                joinClicked = true
                const url = form.userId.value ? '/api/user/join/update' : '/api/user/join/create'
                const personalIdElement = document.createElement("input");
                const eMailElement = document.createElement("input");
                const updateIdElement = document.createElement("input");
                form.appendChild(personalIdElement);
                form.appendChild(eMailElement);
                form.appendChild(updateIdElement);
                personalIdElement.type = 'hidden';
                personalIdElement.name = 'personalId';
                personalIdElement.value = form.personalId1.value + form.personalId2.value;
                eMailElement.type = 'hidden';
                eMailElement.name = 'eMail';
                eMailElement.value = form.eMail1.value + '@' + form.eMail2.value;
                updateIdElement.type = 'hidden';
                updateIdElement.name = 'updateId';
                updateIdElement.value = 'test';
                $('.loading-background')[0].style.display = 'block'
                $.ajax({
                    type: "POST",
                    url: url,
                    data: $('#joinForm').serialize(),
                    success: async function () {
                        $('.loading-background')[0].style.display = 'none'
                        await snAlert.success('신청이 완료되었습니다.');
                        location.href ='/board/user/list';
                    },
                    error: async function () {
                        $('.loading-background')[0].style.display = 'none'
                        joinClicked = false;
                        snAlert.error('신청에 실패하였습니다.');
                    }
                });
            }
        })
    }

    if(document.getElementById('joinCompBtn') != null ) {
        document.getElementById('joinCompBtn').addEventListener('click', async () => {
            const compAt = document.getElementById('compAt');
            if (!compAt.value) {
                await snAlert.alert('반려사유를 입력해주세요.')
                compAt.focus();
            } else if(!compClicked) {
                compClicked = true;
                $.ajax({
                    type: "POST",
                    url: "/api/user/join/deny",
                    data: `compAt=${compAt.value}&userId=${document.getElementById('userId').value}`,
                    success: async function () {
                        await snAlert.alert('반려되었습니다.');
                        location.href ='/board/user/list';
                    },
                    error: async function () {
                        compClicked = false;
                    }
                });
            }
        })
    }

    if(document.getElementById('joinDeleteBtn') != null ) {
        document.getElementById('joinDeleteBtn').addEventListener('click', () => {
            snAlert.confirm('삭제하시겠습니까?').then((data) => {
                if(data.isConfirmed) {
                    $.ajax({
                        type: "POST",
                        url: "/api/user/join/delete",
                        data: `userId=${document.getElementById('userId').value}`,
                        success: async function () {
                            await snAlert.success('삭제되었습니다.');
                            location.href ='/board/user/list';
                        },
                    });
                }
            })
        })
    }

    if(document.getElementById('joinAllowBtn') != null ) {
        document.getElementById('joinAllowBtn').addEventListener('click', () => {
            if(!allowClicked) {
                allowClicked = true;
                $.ajax({
                    type: "POST",
                    url: "/api/user/join/allow",
                    data: `userId=${document.getElementById('userId').value}`,
                    success: async function () {
                        await snAlert.success('가입 승인되었습니다.');
                        const form = document.createElement("form");
                        const userIdElement = document.createElement("input");
                        form.appendChild(userIdElement);
                        document.body.appendChild(form);
                        userIdElement.type = 'text';
                        userIdElement.name = 'userId';
                        userIdElement.value = document.getElementById('userId').value;
                        form.action = '/admin/user/add';
                        form.method = 'post';
                        form.submit();
                    },
                    error: async function () {
                        allowClicked = false;
                    }
                });
            }
        })
    }

    const fnSetDisplay = (e, e2, option) => {
        if (e != null) {
            e.style.display = option ? "none" : "block";
            e.value = "";
        }
        if (e2 !== null) {
            e2.style.display = option ? "none" : "block";
            e2.value = "";
        }
    };

    const fnSetDisabled = (d, d2) => {
        if (d !== null) {
            deptDirect.setAttribute('disabled', 'true')
            deptDirect.style.display = "none"
            deptDirect.value = ''
        }
        if (d2 !== null) {
            bjdCdDirect.setAttribute('disabled', 'true')
            bjdCdDirect.style.display = "none"
            bjdCdDirect.value = ''
        }
    };
})