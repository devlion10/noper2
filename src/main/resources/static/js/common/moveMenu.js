const moveMenu = (() => {
    const baseUrl = location.href.split('/')[0] + '//' + location.href.split('/')[2];

    /**
     * 네비게이션 span태그 안에 텍스트로 맵핑
     * 텍스트 변경시에는 MENU_ENUM도 같이 변경
     *
     * ex) 관리자 메뉴>사용자관리 - 사용자관리를 사용자 관리로 변경시
     *      관리자 메뉴>사용자관리 -> 관리자 메뉴>사용자 관리
     */
    const MENU_ENUM = {
        '기존무허가 건축물관리': '/unlcebldmng/ledger',
        '기존무허가 건축물관리>대장관리': '/unlcebldmng/ledger',
        '기존무허가 건축물관리>대장관리>대장현황': '/unlcebldmng/ledger',
        '기존무허가 건축물관리>대장관리>신규대장현황': '/unlcebldmng/newLedger',
        '기존무허가 건축물관리>확인원관리': '/unlcebldmng/confirmation',
        '기존무허가 건축물관리>소유자변경관리': '/unlcebldmng/ownerChange',
        '기존무허가 건축물관리>철거신고관리': '/unlcebldmng/demolition',
        '기존무허가 건축물관리>철거신고관리>철거신고현황': '/unlcebldmng/demolition',
        '기존무허가 건축물관리>철거신고관리>부분철거신고현황': '/unlcebldmng/partDemolition',
        '기존무허가 건축물관리>개보수신고관리': '/unlcebldmng/renovation/list',
        '기존무허가 건축물관리>일일처리내역': '/unlcebldmng/DailyHistory/list',
        '기존무허가 건축물관리>현장점검 대장현황': '/unlcebldmng/noperSiteChk/list',
        '기존무허가 건축물관리>통계/관리': '/unlcebldmng/ledgerStatus',
        '기존무허가 건축물관리>통계/관리>관리 현황': '/unlcebldmng/ledgerStatus',
        '기존무허가 건축물관리>통계/관리>기간별 통계': '/unlcebldmng/ledgerStatus/period',
        '기존무허가 건축물관리>항공사진측량 현황도': '/unlcebldmng/aerialPhoto/list',
        '개발제한구역내 건축물관리': '/bldmngingb/ledger',
        '개발제한구역내 건축물관리>대장현황': '/bldmngingb/ledger',
        '개발제한구역내 건축물관리>일일처리내역': '/bldmngingb/dailyHistory',
        '위반건축물관리': '/viobldmng/list',
        '위반건축물관리>위반건축물 현황': '/viobldmng/list',
        '위반건축물관리>위반건축물 등록': '/viobldmng/create',
        '위반건축물관리>위반건축물 통계': '/viobldmng/status',
        '게시판': '/board/user/list',
        '게시판>가입신청 현황': '/board/user/list',
        '게시판>공지사항': '/board/notice/list',
        '게시판>Q&A': '/board/questionAndAnswer/list',
        '게시판>GPKI 인증서 안내': '/board/GPKI',
        '관리자 메뉴': '/admin/user/list',
        '관리자 메뉴>사용자관리': '/admin/user/list',
        '관리자 메뉴>운영관리': '/admin/session-management',
        '관리자 메뉴>운영관리>로그인관리': '/admin/session-management',
        '관리자 메뉴>운영관리>로그인관리>로그인 세션 만료시간 설정': '/admin/session-management',
        '관리자 메뉴>권한 관리': '/admin/permit/list'
    }

    Object.freeze(MENU_ENUM)

    document.addEventListener('DOMContentLoaded', () => {
        if(document.querySelector('.title_wrap') !== null && document.querySelector('.title_wrap').querySelector('.loc') !== null) {
            const navigation = document.querySelector('.title_wrap').querySelector('.loc');
            let parentMenu = '';
            navigation.querySelector('.bi-house-fill').style.cursor = 'pointer';
            Array.from(navigation.children).forEach((el) => {
                if(el.tagName !== 'SPAN' || MENU_ENUM[parentMenu + el.textContent.trim()] === undefined) return;
                parentMenu += el.textContent.trim();
                el.style.cursor = 'pointer';
                const locationHref = baseUrl + MENU_ENUM[parentMenu];
                el.addEventListener('click', () => {
                    location.href = locationHref;
                })
                parentMenu += '>';
            })
            navigation.querySelector('.bi-house-fill').style.cursor = 'pointer';
            navigation.querySelector('.bi-house-fill').addEventListener('click', () => {
                location.href = '/';
            });
        }
    })

    return {

    }
})()