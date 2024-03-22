$('#close').click(() => {
    snAlert.confirm('취소 시 변경된 사항은 저장되지 않습니다.\n취소 후 메인화면으로 이동 하시겠습니까?')
        .then((result) => {
            if (result.isConfirmed) {
                location.href = '/'
            }
        })
})
$('#submit').click(() => {
    const value = $('#session').val()
    $.ajax({
        method: "PUT",
        url: `/api/login/session-time/${value}`,
        success: () => snAlert.alert('변경된 사항을 저장하였습니다.').then(() => location.href = '/')
    })
})
$(() => {
    $.get({
        url: "/api/session-time",
        success: (res) => {
            $('#session').val(res).prop('selected', true)
        },
    })
})