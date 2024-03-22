document.addEventListener('submit', (e) => {
    e.preventDefault();
    e.stopPropagation();
    const form = document.getElementById('formId')
    if (form.checkValidity() === false) return false;
    const inputData = $('#formId').serializeObject()
    inputData.emailName = inputData.emailName.toLowerCase()
    inputData.emailDomain = inputData.emailDomain.toLowerCase()
    $.ajax({
        type: "POST",
        url: "/api/find-id",
        data: inputData,
        success: (res) => {
            if (res.bool === 1) {
                $('#formId').submit()
            } else {
                snAlert.error('해당 정보와 일치하는 사용자를 찾을 수 없습니다.')
            }
        },
    });
})
$(() => {
    document.getElementById('container').classList.add('justCenter')
})