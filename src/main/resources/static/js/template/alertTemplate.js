// promise
document.getElementById('btn')
    .addEventListener('click', () => {
        snAlert.confirm('titleText')
            .then((result) => {
                if (result.isConfirmed) {
                    // 확인을 눌렀을 때
                    console.log('확인함.')
                } else {
                    // 취소를 눌렀을 때
                    console.log('취소함.')
                }
            })
    });

// 둘중에 익숙한걸 사용하시면 됩니다.

// async / await
$('#btn').click(async () => {
    const result = await snAlert.confirm('titleText');
    if (result.isConfirmed) {
        // 확인을 눌렀을 때
        console.log('확인함.')
    } else {
        // 취소를 눌렀을 때
        console.log('취소함.')
    }
})