/**
 * 캘린더 사용시 호출
 * 필수요소: <select id="calendarSelect">
 *              <option value="all">전체기간</option>
*               <option value="simple">기간선택</option>
 *              <option value="set">기간설정</option>
 *          </select>,
 *          <input type="hidden" id="startDate">,
 *          <input type="hidden" id="endDate">,
 *          <div id="calendarRemote" class="col-sm-4"></div>
 */
var calendar = (() => {


    document.addEventListener('DOMContentLoaded', function () {
        let calendarSelect = document.getElementById('calendarSelect');
        let calendarRemote = document.getElementById('calendarRemote');
        let startDate = document.getElementById('startDate');
        let endDate = document.getElementById('endDate');

        function calendarRemoteInit () {
            calendarRemote.innerHTML = '';
            startDate.value = '';
            endDate.value = '';
        }

        /**
         * @return{void} simple 옵션의 calenderRemote
         */
        function calendarRemoteToCalendarOptionSimple () {
            calendarRemoteInit();
            const buttonBox = document.createElement("div");
            const buttonDiv = document.createElement("div");
            const buttonToday = document.createElement("button");
            const buttonWeek = document.createElement("button");
            const buttonMonth = document.createElement("button");
            const comment = document.createElement("p");

            buttonDiv.appendChild(buttonToday);
            buttonDiv.appendChild(buttonWeek);
            buttonDiv.appendChild(buttonMonth);
            buttonBox.appendChild(buttonDiv);
            buttonBox.appendChild(comment);
            calendarRemote.appendChild(buttonBox);

            buttonBox.className = "col-12 d-flex justify-content-around align-items-center"
            buttonDiv.className = "btn-group-sm row col-5";
            buttonToday.className = "col-3 ms-1 btn btn-outline-dark";
            buttonWeek.className = "col-3 ms-1 btn btn-outline-dark";
            buttonMonth.className = "col-3 ms-1 btn btn-outline-dark";
            comment.className = "col-8 hstack";

            buttonToday.innerText = "오늘";
            buttonWeek.innerText = "1주일";
            buttonMonth.innerText = "1개월";

            buttonToday.type = "button";
            buttonWeek.type = "button";
            buttonMonth.type = "button";

            buttonToday.addEventListener('click', function() {
                const today = new Date();
                const todayStr = today.toISOString().split('T')[0];
                comment.innerText = `※ ${todayStr}`;
                startDate.value = todayStr;
                endDate.value = todayStr;
                buttonToday.classList.add('btn-clicked');
                buttonWeek.classList.remove('btn-clicked');
                buttonMonth.classList.remove('btn-clicked');
            })

            buttonWeek.addEventListener('click', function () {
                const today = new Date();
                const todayStr = today.toISOString().split('T')[0];
                const weekAgoDay = new Date(today.setDate(today.getDate() - 7));
                const weekAgoDayStr = weekAgoDay.toISOString().split('T')[0];
                comment.innerText = `※ ${weekAgoDayStr} - ${todayStr}`;
                startDate.value = weekAgoDayStr;
                endDate.value = todayStr;
                buttonToday.classList.remove('btn-clicked');
                buttonWeek.classList.add('btn-clicked');
                buttonMonth.classList.remove('btn-clicked');
            })

            buttonMonth.addEventListener('click', function () {
                const today = new Date();
                const todayStr = today.toISOString().split('T')[0];
                const monthAgoDay = new Date(today.setMonth(today.getMonth() - 1));
                const monthAgoDayStr = monthAgoDay.toISOString().split('T')[0];
                comment.innerText = `※ ${monthAgoDayStr} - ${todayStr}`;
                startDate.value = monthAgoDayStr;
                endDate.value = todayStr;
                buttonToday.classList.remove('btn-clicked');
                buttonWeek.classList.remove('btn-clicked');
                buttonMonth.classList.add('btn-clicked');
            })

            buttonToday.click();
        }

        function calendarRemoteToCalendarOptionSet () {
            calendarRemoteInit();

            const dateDiv = document.createElement("div");
            const dateStart = document.createElement("input");
            const dateSpan = document.createElement("span");
            const dateEnd = document.createElement("input");

            dateStart.onkeydown = function() {return false;};
            dateEnd.onkeydown = function() {return false;};

            dateDiv.appendChild(dateStart);
            dateDiv.appendChild(dateSpan);
            dateDiv.appendChild(dateEnd);
            calendarRemote.appendChild(dateDiv);

            dateDiv.className = "row";

            dateStart.id = 'dateStart';
            dateEnd.id = 'dateEnd';

            dateStart.className = "col-3";
            dateSpan.className = "col-1 ms-2 text-center";
            dateEnd.className = "col-3";

            dateStart.type = 'date';
            dateEnd.type = 'date';

            const today = new Date();
            const todayStr = today.toISOString().split('T')[0];
            const dayAgoDay = new Date(today.setDate(today.getMonth() - 1));
            const dayAgoDayStr = dayAgoDay.toISOString().split('T')[0];

            dateStart.value = dayAgoDayStr;
            dateSpan.innerText = '~';
            dateEnd.value = todayStr;
            startDate.value = dayAgoDayStr;
            endDate.value = todayStr;

            dateStart.addEventListener('input', function () {
                startDate.value = dateStart.value;
            });

            dateEnd.addEventListener('input', function () {
                endDate.value = dateEnd.value;
            });
        }

        /**
         * 캘린더 옵션 아이디 설정
         * @param{string} optionId option의 아이디 (전체: all, 기간선택: simple, 기간 설정: set)
         * @return void
         */
        function setCalendar (optionId) {
            switch (optionId) {
                case "all":
                    startDate.value = "";
                    endDate.value = "";
                    calendarRemoteInit();
                    break;
                case "simple":
                    calendarRemoteToCalendarOptionSimple();
                    break;
                case "set":
                    calendarRemoteToCalendarOptionSet();
                    break;
            }
        }

        if(calendarSelect != null && calendarRemote != null && startDate != null && endDate != null) {
            calendarSelect.addEventListener('change', function () { setCalendar(calendarSelect.value) });
            setCalendar(calendarSelect.value);
            if(startDate.getAttribute('startDate') || document.getElementById('dateStart') != null) document.getElementById('dateStart').value = startDate.getAttribute('startDate');
            if(endDate.getAttribute('endDate') || document.getElementById('dateEnd') != null) document.getElementById('dateEnd').value = endDate.getAttribute('endDate');
        }
    })

    return {

    }
})();

function customCalendar(selectEl, remoteEl, startDateEl, endDateEl) {
    this.calendarSelect = 'string' === typeof selectEl ? document.getElementById(selectEl) : selectEl;
    this.calendarRemote = 'string' === typeof remoteEl ? document.getElementById(remoteEl) : remoteEl;
    this.startDate = 'string' === typeof startDateEl ? document.getElementById(startDateEl) : startDateEl;
    this.endDate = 'string' === typeof endDateEl ? document.getElementById(endDateEl) : endDateEl;

    this.dateFormat = '-'

    if(document.getElementById('searchForm')) {
        document.getElementById('searchForm').addEventListener('reset', () => {
            while(this.calendarRemote.hasChildNodes()) {
                this.calendarRemote.removeChild(this.calendarRemote.firstChild);
            }
            this.startDate.value = "";
            this.endDate.value = "";
        });
    }

    this.buttonBox = document.createElement("div");
    this.buttonDiv = document.createElement("div");
    this.buttonFirst = document.createElement("button");
    this.buttonSecond = document.createElement("button");
    this.buttonThird = document.createElement("button");
    this.comment = document.createElement("p");

    this.dateDiv = document.createElement("div");
    this.dateStartView = document.createElement("input");
    this.dateSpan = document.createElement("span");
    this.dateEndView = document.createElement("input");

    this.dateStartView.onkeydown = function() {return false;};
    this.dateEndView.onkeydown = function() {return false;};

    this.buttonFirstValue = '0d';
    this.buttonSecondValue = '7d';
    this.buttonThirdValue = '1m';

    this.buttonFirst.innerText = "오늘";
    this.buttonSecond.innerText = "1주일";
    this.buttonThird.innerText = "1개월";

    this.buttonFirst.type = "button";
    this.buttonSecond.type = "button";
    this.buttonThird.type = "button";

    this.buttonBox.className = "col-12 d-flex justify-content-around align-items-center";
    this.buttonDiv.className = "btn-group-sm row col-5";
    this.buttonFirst.className = "col-3 ms-1 btn btn-outline-dark";
    this.buttonSecond.className = "col-3 ms-1 btn btn-outline-dark";
    this.buttonThird.className = "col-3 ms-1 btn btn-outline-dark";
    this.comment.className = "col-8 hstack";

    this.dateStartView.id = 'dateStart';
    this.dateEndView.id = 'dateEnd';

    this.dateStartView.type = 'date';
    this.dateEndView.type = 'date';

    this.dateDiv.className = "row"
    this.dateStartView.className = "col-3";
    this.dateSpan.className = "col-1 ms-2 text-center";
    this.dateEndView.className = "col-3";

    this.dateSpan.innerText = '~';

    this.buttonBox.appendChild(this.buttonDiv);
    this.buttonDiv.appendChild(this.buttonFirst);
    this.buttonDiv.appendChild(this.buttonSecond);
    this.buttonDiv.appendChild(this.buttonThird);
    this.buttonDiv.appendChild(this.comment);

    this.dateDiv.appendChild(this.dateStartView);
    this.dateDiv.appendChild(this.dateSpan);
    this.dateDiv.appendChild(this.dateEndView);

    this.setClass = function (name, classStr) {
        switch (name) {
        case 'buttonDiv':
            this.buttonDiv.className = classStr;
            break;
        case 'comment':
            this.comment.className = classStr;
            break;
        case 'buttonToday':
            this.buttonFirst.className = classStr;
            break;
        case 'buttonWeek':
            this.buttonSecond.className = classStr;
            break;
        case 'buttonMonth':
            this.buttonThird.className = classStr;
            break;
        case 'dateDiv':
            this.dateDiv.className = classStr;
            break;
        case 'dateStartView':
            this.dateStartView.className = classStr;
            break;
        case 'dateEndView':
            this.dateEndView.className = classStr;
            break;
        case 'dateSpan':
            this.dateSpan.className = classStr;
            break;
        }
    }
    this.setButtonValue = function (name, data) {
        switch (name) {
        case 'first':
            this.buttonFirst.innerText = data.text;
            this.buttonFirstValue = data.date;
            break;
        case 'second':
            this.buttonSecond.innerText = data.text;
            this.buttonSecondValue = data.date;
            break;
        case 'third':
            this.buttonThird.innerText = data.text;
            this.buttonThirdValue = data.date;
            break;
        }
    }

    this.calculatorDate = function (dateStr) {
        if(dateStr.length < 2) {
            return null;
        }
        const today = new Date();
        const dateStrLen = dateStr.length;
        const dateCnt = dateStr.substring(0, dateStrLen - 1);
        const dateType = dateStr.substring(dateStrLen - 1, dateStrLen);

        switch(dateType){
            case 'd' :
                return new Date(today.setDate(today.getDate() - dateCnt));
            case 'm':
                return new Date(today.setMonth(today.getMonth() - dateCnt));
            case 'y':
                return new Date(today.setFullYear(today.getFullYear() - dateCnt));
            default:
                return null;
        }
    }

    this.setDateFormat = (formatStr) => {
        this.dateFormat = formatStr;
    }

    this.changeDateDiv = (param) => {
        while(this.calendarRemote.hasChildNodes()) {
            this.calendarRemote.removeChild(this.calendarRemote.firstChild);
        }
        switch (this.calendarSelect.value) {
            case "all":
                this.startDate.value = "";
                this.endDate.value = "";
                break;
            case "simple":
                this.calendarRemote.appendChild(this.buttonBox);
                this.buttonBox.appendChild(this.comment);
                switch (param) {
                    case '0' : this.buttonFirst.click(); break;
                    case '1' : this.buttonSecond.click(); break;
                    case '2' : this.buttonThird.click(); break;
                    default: this.buttonFirst.click();
                }
                break;
            case "set":
                if(this.startDate.value) this.dateStartView.value = this.startDate.value;
                if(this.endDate.value) this.dateEndView.value = this.endDate.value;
                this.calendarRemote.appendChild(this.dateDiv);
                break;
        }
    }

    this.calendarSelect.addEventListener('change', () => {
        this.changeDateDiv()
    });

    this.buttonFirst.addEventListener('click',() => {
        const today = new Date();
        const todayStr = today.toISOString().split('T')[0];
        if(this.buttonFirstValue === '0d') {
            this.comment.innerText = `※ ${todayStr}`;
            this.startDate.value = todayStr.replaceAll('-', this.dateFormat);
            this.endDate.value = todayStr.replaceAll('-', this.dateFormat);
        } else {
            const weekAgoDay = this.calculatorDate(this.buttonFirstValue);
            const weekAgoDayStr = weekAgoDay.toISOString().split('T')[0];
            this.comment.innerText = `※ ${weekAgoDayStr} - ${todayStr}`;
            this.startDate.value = weekAgoDayStr.replaceAll('-', this.dateFormat);
            this.endDate.value = todayStr.replaceAll('-', this.dateFormat);
        }
        this.buttonFirst.classList.add('btn-clicked');
        this.buttonSecond.classList.remove('btn-clicked');
        this.buttonThird.classList.remove('btn-clicked');
    });

    this.buttonSecond.addEventListener('click', () => {
        const today = new Date();
        const todayStr = today.toISOString().split('T')[0];
        if(this.buttonSecondValue === '0d') {
            this.comment.innerText = `※ ${todayStr}`;
            this.startDate.value = todayStr.replaceAll('-', this.dateFormat);
            this.endDate.value = todayStr.replaceAll('-', this.dateFormat);
        } else {
            const weekAgoDay = this.calculatorDate(this.buttonSecondValue);
            const weekAgoDayStr = weekAgoDay.toISOString().split('T')[0];
            this.comment.innerText = `※ ${weekAgoDayStr} - ${todayStr}`;
            this.startDate.value = weekAgoDayStr.replaceAll('-', this.dateFormat);
            this.endDate.value = todayStr.replaceAll('-', this.dateFormat);
        }
        this.buttonFirst.classList.remove('btn-clicked');
        this.buttonSecond.classList.add('btn-clicked');
        this.buttonThird.classList.remove('btn-clicked');
    })

    this.buttonThird.addEventListener('click', () => {
        const today = new Date();
        const todayStr = today.toISOString().split('T')[0];
        if(this.buttonThirdValue === '0d') {
            this.comment.innerText = `※ ${todayStr}`;
            this.startDate.value = todayStr.replaceAll('-', this.dateFormat);
            this.endDate.value = todayStr.replaceAll('-', this.dateFormat);
        } else {
            const weekAgoDay = this.calculatorDate(this.buttonThirdValue);
            const weekAgoDayStr = weekAgoDay.toISOString().split('T')[0];
            this.comment.innerText = `※ ${weekAgoDayStr} - ${todayStr}`;
            this.startDate.value = weekAgoDayStr.replaceAll('-', this.dateFormat);
            this.endDate.value = todayStr.replaceAll('-', this.dateFormat);
        }
        this.buttonFirst.classList.remove('btn-clicked');
        this.buttonSecond.classList.remove('btn-clicked');
        this.buttonThird.classList.add('btn-clicked');
    });

    this.dateStartView.addEventListener('input', () => {
        this.startDate.value = this.dateStartView.value.replaceAll('-', this.dateFormat);
    });

    this.dateEndView.addEventListener('input', () => {
        this.endDate.value = this.dateEndView.value.replaceAll('-', this.dateFormat);
    });

    if(this.startDate.getAttribute('startDate')) this.startDate.value = this.startDate.getAttribute('startDate');
    if(this.endDate.getAttribute('endDate')) this.endDate.value = this.endDate.getAttribute('endDate');
}