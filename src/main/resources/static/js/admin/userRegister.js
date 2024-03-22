
const userRegister = (function () {
    let registerClicked = false;
    const gridOptions = {
        columnDefs: [
            { headerName: "자치구", field: 'skkCd', checkboxSelection: true },
            { headerName: "부서명", field: 'deptCd' },
            {
                headerName: "성명",
                field: 'userName',
                cellRenderer: (p) => {
                    return $(`<a onclick='' style="text-decoration: underline" href="#">${p.data.userName}</a>`)
                        .click(() => goUserInfo(p.data.userId, 'info'))[0];
                }
            },
            { headerName: "권한", field: 'perId' },
            { headerName: "전화번호", field: 'telNo' },
            { headerName: "이메일", field: 'email' },
            { headerName: "가입일자", field: 'registTs' },
        ],
        headerClass: ["center", "justCenter"], cellClass: ["center", "justCenter"],
        // width: 160,
        sortable: true,
        floatingFilter: false,
        resizable: true,
        cellDataType: false,
        editable: false,
        enableRowGroup: false,
        enablePivot: false,
        enableValue: false,
        minWidth: 100,
        rowGroupPanelShow: 'always',
        rowSelection: 'multiple',
        pagination: true,
        paginationPageSize: 10,
        domLayout: 'autoHeight',
        animateRows: true,
        //autoGroupColumnDef: autoGroupColumnDef,
        /*
        columnTypes: {
            addrColumn: {width: 150, filter:false},
        },
        */
        rowData: [],
        getRowId: (params) => { if(params.data.userId != null) { return params.data.userId } else { return '' }},
        // onCellClicked: (params) => {
        //     if (params.colDef.field === 'userName') {
        //         goUserInfo(params.data.userId, 'info')
        //     }
        // },

        onGridReady: function (event) {
            // 행 넓이 자동 조절
            event.api.sizeColumnsToFit();
        },
        autoGroupColumnDef: {
            headerCheckboxSelection: true,
            field: 'athlete',
            flex: 1,
            minWidth: 240,
            cellRendererParams: {
                checkbox: true,
            },
        },
        suppressRowClickSelection: true,
        suppressAggFuncInHeader: true,
    };

    function goUserInfo(userId, type) {
        const form = document.createElement("form");
        const userIdElement = document.createElement("input");
        form.appendChild(userIdElement);
        document.body.appendChild(form);
        userIdElement.type = 'text';
        userIdElement.name = 'userId';
        userIdElement.value = userId;
        addSearchFormElementToTargetForm(form, $('#searchForm')[0])
        if(type === 'modify') form.action = '/admin/user/modify';
        else form.action = '/admin/user/info';
        form.method = 'post';
        form.submit();
    }

    function moveMenu(type) {
        const form = document.createElement("form");
        addSearchFormElementToTargetForm(form, $('#searchForm')[0])
        switch (type) {
            case 'add': form.action = '/admin/user/add'; break;
            case 'list': form.action = '/admin/user/list'; break;
        }
        document.body.appendChild(form);
        form.method = 'post';
        form.submit();
    }

    function init () {
        if(document.getElementById('searchForm')) $('#searchForm').submit();
    }

    function gridStyle(gridDiv) {
        if (gridDiv && gridDiv.style) {
            Object.assign(gridDiv.style, {
                width: `100%`,
                height: `500px`,
            });
        } else {
            console.error("Invalid GridStyle Element.");
        }
    }

    function randomPassword() {
        const tmpPw = [];
        const randomStr = Math.random().toString(36).substring(2, 12);
        const sChar = ['!', '@', '#', '$', '&', '^', '*', '+', '=', '-'];
        const maxSChar = Math.floor(Math.random() * 3) + 1;
        const numLoc = [];
        const charLoc = [];
        let sCharCk = 0;
        let cCharCk = 0;
        for(let i = 0; i < randomStr.length; i++) {
            if(!isNaN(Number(randomStr[i])) && sCharCk <= maxSChar) {
                if(Math.floor(Math.random() * 2) === 0) {
                    tmpPw.push(sChar[Number(randomStr[i])]);
                    sCharCk++;
                } else {
                    tmpPw.push(randomStr[i]);
                }
                numLoc.push(i);
            } else {
                if(Math.floor(Math.random() * 2) === 0) {
                    tmpPw.push(randomStr[i].toUpperCase());
                    cCharCk++;
                } else {
                    tmpPw.push(randomStr[i]);
                }
                charLoc.push(i);
            }
        }
        if(numLoc.length > 1) {
            if(sCharCk === 0) {
                tmpPw[numLoc[Math.floor(Math.random() * numLoc.length)]] = sChar[Math.floor(Math.random() * 10)];
                sCharCk++;
            }
            if(sCharCk === numLoc.length) tmpPw[numLoc[Math.floor(Math.random() * numLoc.length)]] = Math.floor(Math.random() * 10);
        } else {
            const tL1 = charLoc.splice(Math.floor(Math.random() * charLoc.length), 1)[0];
            const tL2 = charLoc.splice(Math.floor(Math.random() * charLoc.length), 1)[0];
            const tLS = Math.floor(Math.random() * 2);
            numLoc.push(tL1);
            numLoc.push(tL2);
            tmpPw[tLS === 0 ? tL1 : tL2] = sChar[Math.floor(Math.random() * 10)];
            tmpPw[tLS === 0 ? tL2 : tL1] = Math.floor(Math.random() * 10);
        }
        if(charLoc.length > 1) {
            if(cCharCk === 0) {
                tmpPw[charLoc[Math.floor(Math.random() * numLoc.length)]] = tmpPw[charLoc[Math.floor(Math.random() * numLoc.length)]].toUpperCase();
                cCharCk++;
            }
            if(cCharCk === charLoc.length) tmpPw[charLoc[Math.floor(Math.random() * numLoc.length)]] = tmpPw[charLoc[Math.floor(Math.random() * numLoc.length)]].toLowerCase();
        } else {
            const tL1 = numLoc.splice(Math.floor(Math.random() * numLoc.length), 1)[0];
            const tL2 = numLoc.splice(Math.floor(Math.random() * numLoc.length), 1)[0];
            const tLS = Math.floor(Math.random() * 2);
            charLoc.push(tL1);
            charLoc.push(tL2);
            tmpPw[tLS === 0 ? tL1 : tL2] = String.fromCharCode(Math.floor(Math.random() * 27) + 65);
            tmpPw[tLS === 0 ? tL2 : tL1] = String.fromCharCode(Math.floor(Math.random() * 27) + 97);
        }
        return tmpPw.toString().replaceAll(',', '');
    }

    document.addEventListener('DOMContentLoaded', () =>  {
        const gridDiv = $('#registerGrid')[0];
        let registerGrid;
        if(document.getElementById('calendarSelect1')) {
            const myCalendar = new customCalendar('calendarSelect1', 'calendarRemote1', 'startDate1', 'endDate1');
            myCalendar.setClass('buttonDiv', 'btn-group-sm row col-6')
            myCalendar.setClass('dateStartView', 'col-3 w140');
            myCalendar.setClass('dateEndView', 'col-3 w140');
            myCalendar.setClass('comment', 'col-6 text-start')
            myCalendar.setClass('dateSpan', 'col-1 text-center');
            if($('#searchForm')[0].startDate.value !== '' && $('#searchForm')[0].endDate.value !== '') {
                document.getElementById('calendarSelect1').value = 'set'
                document.getElementById('calendarSelect1').dispatchEvent(new Event("change"));
            }
        }

        keyEventUtil.setInputTypeByForm('registerForm')



        if(gridDiv) {
            registerGrid = new agGrid.Grid(gridDiv, gridOptions);

            $('#searchForm').submit(function () {
                $.get('/api/user/register/select/list', $('#searchForm').serialize(), function (data) {
                    $('#total > h3').text("Total "+data.length)
                    registerGrid.gridOptions.api.setRowData([]);
                    registerGrid.gridOptions.api.setRowData(data);
                })
                return false;
            });
            $('#searchForm').submit();
        }

        if($('#userId')[0] != null && $('#userId').val().startsWith('NJ')) {
            const randomPw = randomPassword();
            $('#userPw').val(randomPw);
            $('#userPwCh').val(randomPw);
        }

        $('#skkCd').change(function () {
            const skkCd = $(this).val()
            const deptCdSelect = $('#deptCdSelect')
            const deptCdInput = $('#deptCdInput')
            const deptLabel = $('#deptCdLabel');
            const bjdCdSelect = $('#bjdCd');
            const urlPath = new URL(window.location.href).pathname;
            if (urlPath !== '/admin/user/list') { // 사용자 등록 페이지
            $('#instt').val(""); //소속 값 초기화
            resetSelect('선택','동 선택',$('#deptCdSelect'),$('#bjdCd')); // 부서 및 동 초기화
            if(skkCd === '11000') {
                inputElementChange(deptCdSelect, deptCdInput)
                inputElementChange($('#bjdCd'), null)
                inputElementChange($('#instt'),null)
            } else {
                inputElementChange(deptCdInput, deptCdSelect)
                inputElementChange(null,$('#instt'))
            }
            inputElementChange($('#bjdCd'),null);

            // 자치구별 이메일 가져오기
            let url = '/api/code/email/list'
            let type = 'GET'
            let data = `skkCd=${skkCd}`
            let contentType = "application/json";
            ajaxCall(url, type, data, contentType, function (data) {
              let emailValue = "seoul.go.kr";
               if(data.length !== 0){
                   emailValue =  data[0].cdvalue;
              }
               $('#eMail2').val(emailValue);
            });

            }else{ // 사용자 관리 페이지
                 if(skkCd !== '11000'){ /* 서울특별시 그 이외 소속선택창 초기화 */
                    $('#insttList').val('');
                     if($('#skkCd')[0] && $('#skkCd')[0].getAttribute('insttlist') !== '') {
                         $('#insttList').val($('#skkCd').attr('insttlist'));
                         $('#skkCd')[0].setAttribute('insttlist', '');
                     }
                    $('#insttList').removeClass('d-none');
                    $('#insttList').change()
                 }
                 else{ /* 서울 특별시 경우 소속선택창 활성화 X , 부서명 활성화*/
                    let url = '/api/code/dept/list'
                    let type = 'GET'
                    let data = `skkCd=${skkCd}`
                    let contentType = "application/json";
                    ajaxCall(url, type, data, contentType, function (data) {
                            let options;
                            options = "<option value='' selected>전체</option>";
                            for (const dptInfo of data) {
                                if(dptInfo.cdkey !== '11110'){
                                    options += `<option value="${dptInfo.cdkey}">${dptInfo.cdvalue}</option>`
                                }
                            }
                            $('#deptCd').html(options).removeAttr('disabled').removeClass('d-none');
                            deptLabel.attr('for', 'deptCd').text('부서명');

                    });
                     // 동일한 위치에서 bjdCd select 요소의 상태를 처리
                     bjdCdSelect.attr('disabled', 'disabled').addClass('d-none');
                     $('#insttList').addClass('d-none');
                 }
            }
        })

        /*사용자관리 소속 선택*/
        $('#insttList').change(function(){
            const urlPath = new URL(window.location.href).pathname;
            const insttValue = $(this).val();
            const deptLabel = $('#deptCdLabel');
            const bjdCdSelect = $('#bjdCd');
            const skkCd = $('#skkCd').val();
            console.log('insttList')
            if (urlPath === '/admin/user/list') {
               if(insttValue === 'gu' ){
                    /* 구청일 때 부서전체 가져오기*/
                    let url = '/api/code/dept/list'
                    let type = 'GET'
                    let data = `skkCd=${skkCd}`
                    let contentType = "application/json";
                    ajaxCall(url, type, data, contentType, function (data) {
                            let options;
                            options = "<option value='' selected>전체</option>";
                            for (const dptInfo of data) {
                                if(dptInfo.cdkey !== '11110'){  // 동사무소 제외
                                    options += `<option value="${dptInfo.cdkey}">${dptInfo.cdvalue}</option>`
                                }
                            }
                            $('#deptCd').html(options).removeAttr('disabled').removeClass('d-none');
                            deptLabel.attr('for', 'deptCd').text('부서명');
                            if($('#skkCd')[0] && $('#skkCd')[0].getAttribute('deptcd') !== '' ) {
                                $('#deptCd').val($('#skkCd').attr('deptcd'));
                                $('#skkCd')[0].setAttribute('deptcd', '');
                            }
                    });
                     // 동일한 위치에서 bjdCd select 요소의 상태를 처리
                     bjdCdSelect.attr('disabled', 'disabled').addClass('d-none');
               }else if(insttValue === 'dong'){
                     /* 동사무소 일 때 행정동 전체 가져오기*/
                    let url = '/api/code/bjdong/list'
                    let type = 'GET'
                    let data = `skkCd=${skkCd}`
                    let contentType = "application/json";
                    ajaxCall(url, type, data, contentType, function (data) {
                        let options;
                        const path = new URL(location.href).pathname
                        options = "<option value='' selected>전체</option>";
                        for (const bjdInfo of data) {
                            if(bjdInfo.key !== '000'){  // 구청 선택 제외
                                options += `<option value="${bjdInfo.key}">${bjdInfo.dpname}</option>`;
                            }
                        }
                         bjdCdSelect.html(options).removeAttr('disabled').removeClass('d-none');
                         deptLabel.attr('for', 'bjdCd').text('행정동');
                        if($('#skkCd')[0] && $('#skkCd')[0].getAttribute('bjdcd') !== '' ) {
                            bjdCdSelect.val($('#skkCd').attr('bjdcd'));
                            $('#skkCd')[0].setAttribute('bjdcd', '');
                        }
                    });
                    // 동일한 위치에서 deptCd select 요소의 상태를 처리
                    $('#deptCd').attr('disabled', 'disabled').addClass('d-none');
               }else{
                    /* 소속선택 빈값 체크 시 부서명 창으로 초기화 */
                    resetSelect('전체',null,$("#deptCd"),null);
                    deptLabel.attr('for', 'deptCd').text('부서명');
                    $('#deptCd').removeAttr('disabled').removeClass('d-none');
                    bjdCdSelect.attr('disabled', 'disabled').addClass('d-none');
               }
            }
        })

        if($('#skkCd')[0] && $('#skkCd')[0].getAttribute('skkcd') !== '' ) {
            $('#skkCd').value = $('#skkCd')[0].getAttribute('skkcd');
            $('#skkCd')[0].dispatchEvent(new Event("change"));
            $('#skkCd')[0].setAttribute('skkcd', '');
        }

        /*사용자 등록 - 소속 선택*/
        $('#instt').change(function(){
            const skkCd = $('#skkCd').val()
            const insttValue = $(this).val()
            const deptCdSelect = $('#deptCdSelect')
            const deptCdInput = $('#deptCdInput')

            if(skkCd !== ''){
                if(insttValue === 'gu'){
                    inputElementChange($('#bjdCd'),null);
                    let url = '/api/code/dept/list'
                    let type = 'GET'
                    let data = `skkCd=${skkCd}`
                    let contentType = "application/json";
                    ajaxCall(url, type, data, contentType, function (data) {
                        let options;
                        const path = new URL(location.href).pathname
                        //if (path !== '/admin/user/list') directInput()
                        options = "<option value='' selected>선택</option>";
                        for (const dptInfo of data) {
                            if(dptInfo.cdkey !== '11110'){  // 동사무소 제외
                                options += `<option value="${dptInfo.cdkey}">${dptInfo.cdvalue}</option>`
                            }
                        }
                        updateSelect(deptCdSelect, options);
                    });
                }else if(insttValue === 'dong'){
                    inputElementChange(null,$('#bjdCd'))
                    let url = '/api/code/bjdong/list'
                    let type = 'GET'
                    let data = `skkCd=${skkCd}`
                    let contentType = "application/json";
                    ajaxCall(url, type, data, contentType, function (data) {
                        let options;
                        const path = new URL(location.href).pathname
                        //if (path !== '/admin/user/list') directInput()
                        options = "<option value='' selected>동 선택</option>";
                        for (const bjdInfo of data) {
                           if(bjdInfo.key !== '000'){  // 구청 선택 제외
                               options += `<option value="${bjdInfo.key}">${bjdInfo.dpname}</option>`;
                           }
                        }
                        $('#bjdCd').html(options);
                        // bjdCd 선택 상자 업데이트
                        updateSelect($('#bjdCd'), options);
                        // 부서값 설정 및 선택 방지
                        updateSelect(deptCdSelect, "<option value='11110' selected>동사무소</option>", true);
                    });
                }else{
                    /* 소속선택 빈값 체크 시 부서명 창으로 초기화 */
                    resetSelect('선택',null,$("#deptCdSelect"),null);
                    $('#deptCdSelect').removeAttr('disabled').removeClass('d-none'); // 부서명 창 활성화
                    $('#bjdCd').attr('disabled', 'disabled').addClass('d-none'); // 동 선택 창 비활성화
                }
            }
        })

        $('#listCnt').change(function() {
            registerGrid.gridOptions.api.paginationSetPageSize(Number(this.value));
        })

        $('#reset').click(function () {
            $('#searchForm')[0].reset();
        })
        
        const urlPath = new URL(window.location.href).pathname;
        // 사용자 등록 비밀번호 확인
        if (urlPath === '/admin/user/add') {
            // 비밀번호 확인 별도 blur 처리
            const pw = document.querySelector("#userPw");
            const repw = document.querySelector("#userPwCh");
            repw.addEventListener("blur", () => {
                const pwVal = pw.value;
                const repwVal = repw.value;
                if (pwVal === repwVal && repwVal !== '') {
                    repw.classList.remove("is-invalid");
                } else {
                    repw.classList.add("is-invalid");
                }
            });
        }

        $('#registerForm').submit(function (e) {
            const form = $(this).get(0);
            /*
            const stringReg = /[가-힣\w\s]/g;
            const pwReg = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$/;
            const personalIdStartReg = /^\d{6}$/g;
            const personalIdEndReg = /^\d{7}$/g;
            const emailStartReg = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*!/g;
            const emailEndReg = /[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
            const telReg = /\d{10,11}/g;
            */
            e.preventDefault();
            e.stopPropagation();
            if (form.checkValidity() === false) return false;
            else if(!registerClicked) {
                registerClicked = true;
                const url = form.userId.value === '자동생성' || form.userId.value.startsWith('NJ')
                                        ? "/api/user/register/create"
                                        : "/api/user/register/update"
                let data = {
                    updateId: 'test',
                    userId: form.userId.value,
                    skkCd: form.skkCd.value,
                    dongInfo: form.bjdCd.value,
                    userPw: form.userPw.value,
                    deptCd: form.deptCdSelect.value,
                    perId: form.perId.value,
                    userName: form.userName.value,
                    personalId: `${form.personalId1.value}${form.personalId2.value}`,
                    eMail: `${form.eMail1.value}@${form.eMail2.value}`,
                    telNo: form.telNo.value,
                    workCharge: form.workCharge.value
                }

                // 자치구 시군구값이 서울특별시인 경우
                const skkCdCheck = data.skkCd === '11000';
                const insttValue = $('#instt').val()

                if (skkCdCheck) {
                     // data.dongInfo = 'direct';
                     // data.bjdCdDirect = skkCdCheck ? form.bjdCdDirect.value : undefined;
                     data.dongInfo = '1'; // 본청
                     data.deptCd = 'direct';
                     data.deptCdDirect = skkCdCheck ? form.deptCdInput.value : undefined;
                }else{
                    // 소속 선택 시 -   구청인 경우 dongInfo 000 / 동사무소인 경우 부서 11110
                    if(insttValue === "gu"){
                        data.dongInfo = '000'
                    }else if(insttValue === 'dong'){
                        data.deptCd = '11110'
                    }
                }

                $('.loading-background')[0].style.display = 'block'
                $.ajax({
                    type: "POST",
                    url: url,
                    data: data,
                    success: function () {
                        $('.loading-background')[0].style.display = 'none'
                        const msg = form.userPw.tagName.toUpperCase() === 'BUTTON' ? '수정이 완료되었습니다.' : `가입이 완료되었습니다. \n비밀번호는 ${form.userPw.value} 입니다.`;
                        snAlert.alert(msg).then(() => {location.href ='/admin/user/list'});
                    },
                    error: function () {
                        $('.loading-background')[0].style.display = 'none'
                        registerClicked = false;
                    }
                });
            }
            return false;
        })

        $('#registerAddButton').click(function () {
            moveMenu('add');
        })

        $('#registerModifyButton').click(function () {
            goUserInfo($('#userId').val(), 'modify');
        })

        $('#registerDeleteButton').click(function () {
            snAlert.confirm('삭제하시겠습니까?').then((data) => {
                if(data.isConfirmed) {
                    let userIdData = '';
                    if(registerGrid) registerGrid.gridOptions.api.getSelectedRows().forEach(user => userIdData += `userId=${user.userId}&`);
                    else userIdData = 'userId=' + $('#userId').val() + '&';
                    if(userIdData !== '') {
                        userIdData = userIdData.substring(0, userIdData.length - 1);
                        $.ajax({
                            type: "POST",
                            url: "/api/user/register/delete",
                            data: userIdData,
                            success: function () {
                                snAlert.alert('삭제되었습니다.').then(() => {location.href = "/admin/user/list"});
                            },
                        });
                    }
                }
            })
        })

        $('#registerCancelBtn').click(function () {
            moveMenu('list');
        })

        $('#changePw').click(function () {
            $('#modalUserId').val($('#userId').val());
            $.post('/api/user/register/update/password', $('#resetPasswordModalForm').serialize(), function (result) {
                if(result === 'success') {
                    snAlert.alert('비밀번호가 변경되었습니다.').then(() => {$('#findPasswordModal').modal('toggle')});
                } else {
                    snAlert.alert('기존 비밀번호를 다시 입력해주세요')
                }
            })
        })

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

        const directInput = (ready = false) => {
            const data = $('#registerForm').serializeObject();
            const bjdCdElement = document.getElementById('bjdCd');
            const bjdCdDirectElement = document.getElementById('bjdCdDirect');

            const skkCdCheck = data.skkCd === '11000';

            bjdCdElement.disabled = skkCdCheck;
            bjdCdElement.style.display = skkCdCheck ? 'none' : 'inline-block';

            bjdCdDirectElement.disabled = !skkCdCheck;
            bjdCdDirectElement.style.display = skkCdCheck ? 'inline-block' : 'none';

            if (!ready) {
                const validation = (e) => {
                    if (e.style.display === 'none') {
                        e.classList.remove('is-invalid')
                    }
                };

                validation(bjdCdElement)
                validation(bjdCdDirectElement)
            }
        };

        const inputElementChange = (oldEl, newEl) => {
            if(oldEl !== null) {
                oldEl.addClass('d-none');
                oldEl.removeAttr('required');
                oldEl.attr('disabled', true);
                oldEl.removeClass('is-invalid');
            }
            if(newEl !== null) {
                newEl.removeClass('d-none');
                newEl.attr('required', true);
                newEl.removeAttr('disabled');
                newEl.removeClass('is-valid');
            }
        }

        const resetSelect = (text , text2 , el , el2) => {
            let option;
            let options2;
            if(el != null){
                options = `<option value=''>${text}</option>`;
                el.html(options);
            }
            if(el2 != null){
                options2 = `<option value=''>${text2}</option>`;
                el2.html(options2);
            }
        }

        const updateSelect = (element , options , disable = false) => {
            element.html(options).removeClass('is-invalid'); // 변경 시 계속 남아있는 문제가 있어 class 삭제
            if(disable){
                element.attr('disabled', true);
                element.removeAttr('required');
            }else{
                element.removeAttr('disabled');
                element.attr('required', true);
            }
        }

        function ajaxCall(url, type, param, contentType, callback) {
            $.ajax({
                url : url,
                type : type,
                data : param,
                contentType: contentType,
                success: function (result){
                    return callback(result);
                }
            });
        };

        init();
        //if(new URL(window.location.href).pathname !== '/admin/user/list')directInput(true)
        
    });

    return {

    };
})();