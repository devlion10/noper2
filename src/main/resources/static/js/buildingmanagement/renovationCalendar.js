/**
 * 캘린더 사용시 호출
 * 필수요소:
 *      // 건축기간(개보수신고)
 *      <select id="gmgunilSelect">
 *          <option value="gmgunilAll">전체기간</option>
 *          <option value="gmgunilSimple">기간선택</option>
 *          <option value="gmgunilSet">기간설정</option>
 *      </select>
 *      <input type="hidden" id="gmgunil1" name="gmgunil1">
 *      <input type="hidden" id="gmgunil2" name="gmgunil2">
 *      <div id="gmgunilRemote" class="col-sm-4"></div>
 *
 *      // 접수일자(개보수신고)
 *      <select id="gbsgoilSelect">
 *          <option value="gbsgoilAll">전체기간</option>
 *          <option value="gbsgoilSimple">기간선택</option>
 *          <option value="gbsgoilSet">기간설정</option>
 *          </select>
 *      <input type="hidden" id="gbsgoil1" name="gbsgoil1">
 *      <input type="hidden" id="gbsgoil2" name="gbsgoil2">
 *      <div id="gbsgoilRemote" class="col-sm-4"></div>
 *
 *      // 준공일자(개보수신고)
 *      <select id="jgongSelect">
 *          <option value="jgongAll">전체기간</option>
 *          <option value="jgongSimple">기간선택</option>
 *          <option value="jgongSet">기간설정</option>
 *      </select>
 *      <input type="hidden" id="jgong1" name="jgong1">
 *      <input type="hidden" id="jgong2" name="jgong2">
 *      <div id="jgongRemote" class="col-sm-4"></div>
 *
 *      // 철거일자(철거신고)
 *      <select id="deiljaSelect">
 *          <option value="deiljaAll">전체기간</option>
 *          <option value="deiljaSimple">기간선택</option>
 *          <option value="deiljaSet">기간설정</option>
 *      </select>
 *      <input type="hidden" id="deilja" name="deilja">
 *      <input type="hidden" id="deilja1" name="deilja">
 *      <div id="deiljaRemote" class="col-sm-4"></div>
 *
 *      // 부분철거일자(부분철거신고)
 *      <select id="gmbiljaSelect">
 *          <option value="gmbiljaAll">전체기간</option>
 *          <option value="gmbiljaSimple">기간선택</option>
 *          <option value="gmbiljaSet">기간설정</option>
 *      </select>
 *      <input type="hidden" id="gmbilja1" name="gmbilja1">
 *      <input type="hidden" id="gmbilja2" name="gmbilja2">
 *      <div id="gmbiljaRemote" class="col-sm-4"></div>
 *
 *      // 작성일자(Q&A)
 *      <select id="calendarSelect">
 *          <option value="all">전체기간</option>
 *          <option value="simple">기간선택</option>
 *          <option value="set">기간설정</option>
 *      </select>,
 *      <input type="hidden" id="startDate">,
 *      <input type="hidden" id="endDate">,
 *      <div id="calendarRemote" class="col-sm-4"></div>
 */

// 작성일자(Q&A)
document.addEventListener('DOMContentLoaded', function () {
    const calendarSelect = document.getElementById('calendarSelect');
    const calendarRemote = document.getElementById('calendarRemote');

    const startDate = document.getElementById('startDate');
    const endDate = document.getElementById('endDate');

    /**
     * @return{void} simple 옵션의 calenderRemote
     */
    function calendarRemoteToCalendarOptionSimple () {
        calendarRemoteInit();

        const buttonDiv = document.createElement("div");
        const buttonToday = document.createElement("button");
        const buttonWeek = document.createElement("button");
        const buttonMonth = document.createElement("button");
        const comment = document.createElement("p");

        buttonDiv.appendChild(buttonToday);
        buttonDiv.appendChild(buttonWeek);
        buttonDiv.appendChild(buttonMonth);
        calendarRemote.appendChild(buttonDiv);
        calendarRemote.appendChild(comment);

        buttonDiv.className = "btn-group-sm row";
        buttonToday.className = "btn btn-outline-dark";
        buttonWeek.className = "btn btn-outline-dark";
        buttonMonth.className = "btn btn-outline-dark";
        comment.className = "col-7 hstack";

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
        })

        buttonWeek.addEventListener('click', function () {
            const today = new Date();
            const todayStr = today.toISOString().split('T')[0];
            const weekAgoDay = new Date(today.setDate(today.getDate() - 7));
            const weekAgoDayStr = weekAgoDay.toISOString().split('T')[0];
            comment.innerText = `※ ${weekAgoDayStr} - ${todayStr}`;
            startDate.value = weekAgoDayStr;
            endDate.value = todayStr;
        })

        buttonMonth.addEventListener('click', function () {
            const today = new Date();
            const todayStr = today.toISOString().split('T')[0];
            const monthAgoDay = new Date(today.setMonth(today.getMonth() - 1));
            const monthAgoDayStr = monthAgoDay.toISOString().split('T')[0];
            comment.innerText = `※ ${monthAgoDayStr} - ${todayStr}`;
            startDate.value = monthAgoDayStr;
            endDate.value = todayStr;
        })

        buttonToday.click();
    }

    function calendarRemoteToCalendarOptionSet () {
        calendarRemoteInit();

        const dateDiv = document.createElement("div");
        const dateStart = document.createElement("input");
        const dateEnd = document.createElement("input");

        dateStart.onkeydown = function() {return false;};
        dateEnd.onkeydown = function() {return false;};

        dateDiv.appendChild(dateStart);
        dateDiv.appendChild(dateEnd);
        calendarRemote.appendChild(dateDiv);

        dateDiv.className = "row";

        dateStart.id = 'dateStart';
        dateEnd.id = 'dateEnd';

        dateStart.className = "w140";
        dateEnd.className = "w140 ml6";

        dateStart.type = 'date';
        dateEnd.type = 'date';

        const today = new Date();
        const todayStr = today.toISOString().split('T')[0];
        const dayAgoDay = new Date(today.setDate(today.getMonth() - 1));
        const dayAgoDayStr = dayAgoDay.toISOString().split('T')[0];

        dateStart.value = dayAgoDayStr;
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

    if (document.getElementById('gmgunilSelect')) {
        const calendar = new customCalendar('gmgunilSelect', 'gmgunilRemote', 'gmgunil1', 'gmgunil2');
    }
    if (document.getElementById('gbsgoilSelect')) {
        const calendar = new customCalendar('gbsgoilSelect', 'gbsgoilRemote', 'gbsgoil1', 'gbsgoil2');
    }
    if (document.getElementById('jgongSelect')) {
        const calendar = new customCalendar('jgongSelect', 'jgongRemote', 'jgong1', 'jgong2');
    }
})


function calendarInit(){
    gmgunilRemote.innerHTML = '';
    gmgunil1.value = '';
    gmgunil2.value = '';

    gbsgoilRemote.innerHTML = '';
    gbsgoil1.value = '';
    gbsgoil2.value = '';

    jgongRemote.innerHTML = '';
    jgong1.value = '';
    jgong2.value = '';

    deiljaRemote.innerHTML = '';
    deilja.value = '';
    deilja1.value = '';

    gmbiljaRemote.innerHTML = '';
    gmbilja1.value = '';
    gmbilja2.value = '';

}

// 작성일자(Q&A)
function calendarRemoteInit () {
    calendarRemote.innerHTML = '';
    startDate.value = '';
    endDate.value = '';
}

// 건축일자(건축기간) 초기화
function gmgunilRemoteInit () {
    gmgunilRemote.innerHTML = '';
    gmgunil1.value = '';
    gmgunil2.value = '';
}
// 접수일자 초기화
function gbsgoilRemoteInit () {
    gbsgoilRemote.innerHTML = '';
    gbsgoil1.value = '';
    gbsgoil2.value = '';
}
// 준공일자 초기화
function jgongRemoteInit () {
    jgongRemote.innerHTML = '';
    jgong1.value = '';
    jgong2.value = '';
}
// 철거일자 초기화
function deiljaRemoteInit () {
    deiljaRemote.innerHTML = '';
    deilja.value = '';
    deilja1.value = '';
}

// 부분철거일자 초기화
function gmbiljaRemoteInit () {
    gmbiljaRemote.innerHTML = '';
    gmbilja1.value = '';
    gmbilja2.value = '';
}
