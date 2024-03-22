const snReport = (() => {

    const url =  async () => {
        try {
            return await $.ajax({
                type: "GET",
                url: `/common/api/report/url`,
            });
        } catch (e) {
            const txt = "리포트 URL을 가져오는 중 오류가 발생했습니다."
            snAlert.error(txt)
            throw new Error(txt);
        }
    }

    async function _open(reportID, param) {
        if (typeof reportID === 'undefined' || typeof param === 'undefined') {
            throw new Error('파라미터가 부족합니다.');
        }
        const fullScreen = 'yes'
        const resizable = 'yes'
        const width = 1536
        const height = 1080
        const options = `fullscreen=${fullScreen},
                                  resizable=${resizable},
                                  menubar=no, 
                                  toolbar=no,
                                  scrollbars=yes,
                                  width=${width},
                                  height=${height}`
        try {
            const reportURL = await url()
            const queryParams = objectToQueryString({ reportID, ...param });
            window.open(`${reportURL}?${queryParams}`, '', options);
        } catch (e) {
            snAlert.error('보고서를 나타내는 도중 에러가 발생하였습니다.')
            console.error(e)
        }
    }

    function objectToQueryString(obj) {
        return Object.keys(obj)
            .map(key => key + '=' + encodeURIComponent(obj[key]))
            .join('&');
    }

    return {
        open:_open
    }
})();