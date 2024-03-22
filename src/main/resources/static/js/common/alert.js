const snAlert = (($) => {

    let im = "";
    const argsChk = (tx) => typeof tx === undefined ? '' : tx;

    function _alert(msg){
        return Swal.fire(msg);
    }

    function _confirm(title){
        const options = {
            icon: 'question',
            title: title,
            showDenyButton: false,
            showCancelButton: true,
            confirmButtonText: '확인',
            cancelButtonText: `취소`,
            reverseButtons: true
        }
        return Swal.fire(options);
    }

    function _error(msg, tx) {
        tx = argsChk(tx)
        const options = {
            icon: 'error',
            title: msg,
            text: tx
        }
        return Swal.fire(options);
    }

    function _success(msg, tx) {
        tx = argsChk(tx)
        const options = {
            icon: 'success',
            title: msg,
            text: tx
        }
        return Swal.fire(options);
    }

    function _ajaxSuccess(url) {
        const Toast = Swal.mixin({
            toast: true,
            position: 'bottom-end',
            showConfirmButton: false,
            timer: 1000,
            didOpen: (toast) => {
                toast.addEventListener('mouseenter', Swal.stopTimer)
                toast.addEventListener('mouseleave', Swal.resumeTimer)
            }
        })
        return Toast.fire({
            icon: 'success',
            title: `URL: ${url}\nRequest successful`
        })
    }

    return {
        alert:_alert,
        confirm:_confirm,
        success:_success,
        error: _error,
        ajaxSuccess: _ajaxSuccess
    }
})();

/*
 * 무조건 로그인이 되어있을 때, js에서 menuId를 체크해야할 경우 사용
 * @param menuId - 조회할 메뉴아이디
 * @param auth - login 인증 여부
 * @param type - menuID의 어느 값을 조회할 것인지
 */
// function menuIdCheck(menuId, auth, type) {
//     if (auth) {
//         let response;
//         $.get({
//             url: `/api/user/menuId/check?menuId=${menuId}`,
//             success: (res) => {
//                 switch (type) {
//                     case 'select' : response = res.canSelect; break;
//                     case 'insert' : response = res.canInsert; break;
//                     case 'update' : response = res.canUpdate; break;
//                     case 'delete' : response = res.canDelete; break;
//                 }
//             },
//         })
//         return response
//     } else {
//         location.href= '/access-denied'
//     }
// }

function menuIdCheck(auth, element) {
    if (auth) {
        $.ajax({
            type: "GET",
            url: "/api/user/menuId/check?menuId=HH",
            contentType : "application/json;charset=UTF-8",
            success: (res) => {
                res.canSelect ? OPENMAP.openAerialPhotoMap(element) : location.href= '/access-denied'
            },
        });
    } else {
        location.href= '/access-denied'
    }
}