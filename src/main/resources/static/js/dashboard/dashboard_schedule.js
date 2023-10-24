
var calendar = null;

initializeScript();

function initializeScript() {

    var calendarEl = document.getElementById('calendar');
	calendar = new FullCalendar.Calendar(calendarEl, {

        /* =====================
                Options 
        ===================== */

        locale                      : 'ko',    
        // allDaySlot                  : true,
        displayEventTime            : true,
        displayEventEnd             : true,
        firstDay                    : 0, //월요일이 먼저 오게 하려면 1
        weekends                    : false,
        // selectable                  : true,
        // editable                    : true,
        slotLabelFormat             : 'HH:mm',
        dayPopoverFormat            : 'MM/DD dddd',
        longPressDelay              : 0,
        eventLongPressDelay         : 0,
        selectLongPressDelay        : 0,  
        fixedWeekCount              : false,
        // aspectRatio                 : 10,
        // handleWindowResize          : false,
        // showNonCurrentDates: false

        /* =====================
              Options End 
        ===================== */

        events: function(fetchInfo, successCallback, failureCallback) {
            readAllScheduleRequest(fetchInfo, successCallback, failureCallback);
        },


	});

    calendar.render();

    // 스케줄 조회
    function readAllScheduleRequest(fetchInfo, successCallback, failureCallback) {
        // 예를 들어, 서버에서 이벤트를 가져오는 AJAX 요청이 있다고 가정하겠습니다.
        $.ajax({
            url: '/api/full-calendar',  // 이 URL은 실제 서버에 맞게 수정되어야 합니다.
            type: 'GET',
            dataType: 'json',
            success: function(events) {
                const processedEvents = events.map(event => {
                    let prefix = "";
                    switch(event.target) {
                        case 'ALL':
                            prefix = "[전체]";
                            break;
                        case 'ADMIN':
                            prefix = "[관리자]";
                            break;
                        case 'EMPLOYEE':
                            prefix = "[사원]";
                            break;
                        case 'PERSONAL':
                            prefix = "[개인]";
                            break;
                    }
                    event.title = prefix + " " + event.title;  // 이벤트 제목 앞에 접두사를 추가합니다.
                    return event;
                });
                successCallback(processedEvents);  // 처리된 이벤트들을 FullCalendar에 전달합니다.
            },
            error: function(error) {
                failureCallback('There was an error while fetching events.');
            }
        });
    }

}

