var startDate = null; 
var endDate = null;
var calendar = null;

initializeScript();

function initializeScript() {

    var calendarEl = document.getElementById('calendar');
	calendar = new FullCalendar.Calendar(calendarEl, {

        /* =====================
                Options 
        ===================== */

        locale                      : 'ko',    
        // nextDayThreshold            : "09:00:00",
        // defaultTimedEventDuration   : '01:00:00',
        allDaySlot                  : true,
        displayEventTime            : true,
        displayEventEnd             : true,
        firstDay                    : 0, //월요일이 먼저 오게 하려면 1
        weekends                    : false,
        selectable                  : true,
        editable                    : true,
        slotLabelFormat             : 'HH:mm',
        dayPopoverFormat            : 'MM/DD dddd',
        longPressDelay              : 0,
        eventLongPressDelay         : 0,
        selectLongPressDelay        : 0,  
        headerToolbar               : { // 헤더에 표시할 툴 바
                                        start : 'today',
                                        center : 'prevYear prev title next nextYear',
                                        end : 'dayGridMonth,dayGridWeek,dayGridDay'
		},
        buttonText                  : {
                                        today  : '오늘',
                                        month  : '월간',
                                        week   : '주간',
                                        day    : '일간'
        },  

        /* =====================
              Options End 
        ===================== */

        select: function (info) {
            showContextMenu(info.jsEvent, info.start, info.end);
        },

        events: function(fetchInfo, successCallback, failureCallback) {
            $.ajax({
                url: '/api/full-calendar',
                type: 'GET',
                dataType: 'json',
                success: function(response) {
                    successCallback(response);
                },
                error: function() {
                    failureCallback('There was an error while fetching events.');
                }
            });
        },







	});

    calendar.render();


    // 모달 관련 로직 ==========================================================================

    // '하루종일' 체크박스의 변경 이벤트에 리스너를 추가합니다.
    $('#schedule-isAllDay').on('change', function() {
        if ($(this).prop('checked')) {
            // 체크박스가 체크되면 startTime과 endTime 입력란을 비활성화하고 값을 지웁니다.
            $(".start-time").prop('disabled', true).val('00:00');
            $(".end-time").prop('disabled', true).val('00:00');
        } else {
            // 체크박스가 해제되면 startTime과 endTime 입력란을 활성화하고 초기값으로 설정합니다.
            $(".start-time").prop('disabled', false).val("00:00");
            $(".end-time").prop('disabled', false).val("23:59");
        }
    });

    // "취소" 버튼 클릭 시 로직
    $('#submit-schedule-cancel_btn').on('click', function() {
        $('#modal').hide(); // 모달 창 숨기기
        $('#schedule-isAllDay').prop('checked', false);
        $('#schedule-name').val(''); 
        $('#schedule-target').prop('selectedIndex', 0);
        $('#schedule-color').prop('selectedIndex', 0);
        $(".start-time").prop('disabled', false).val("00:00");
        $(".end-time").prop('disabled', false).val("23:59");
    });

    // "추가" 버튼 클릭 시 로직 (이벤트 추가)
    $('#submit-schedule_btn').on('click', function() {

        console.log("submit activated");

        // 사용자 입력 정보 가져오기
        var isAllDay = $('#schedule-isAllDay').prop('checked');
        var title = $('#schedule-name').val();
        var target = $('#schedule-target').val();
        var color = $('#schedule-color').val();

        // 사용자가 입력한 시간 가져오기
        var startTime = $(".start-time").val().split(':'); // ["HH", "mm"]
        var endTime = $(".end-time").val().split(':');     // ["HH", "mm"]

        // startDate와 endDate에 시간 반영
        startDate.setHours(parseInt(startTime[0]), parseInt(startTime[1]));
        endDate.setHours(parseInt(endTime[0]), parseInt(endTime[1]));
        
        // 이벤트 객체 생성
        var newEvent = {
            title: title,
            start: startDate,
            end: endDate,
            allDay: isAllDay,
            target: target,
            color: color
        };
        
        // allDay 로직상, 하루를 늘려주어야 정상적으로 저장, 출력이 된다.
        if (newEvent.allDay) {
            newEvent.end = new Date(newEvent.end);
            newEvent.end.setDate(newEvent.end.getDate() + 1);
        }

        // AJAX call to send the newEvent object as JSON to the server
        $.ajax({
            url: '/api/full-calendar',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(newEvent),
            success: function(response) {
                console.log('Event data sent successfully:', response);
                // 다른 후속 처리 로직
            },
            error: function(error) {
                console.error('Error sending event data:', error);
            }
        });

        // 이벤트 추가
        calendar.addEvent(newEvent);
        calendar.render();

        // 모달 창 숨기기
        $('#modal').hide();

        // 입력 정보 초기화
        $('#schedule-isAllDay').prop('checked', false);
        $('#schedule-name').val(''); 
        $('#schedule-target').prop('selectedIndex', 0);
        $('#schedule-color').prop('selectedIndex', 0);
    });

}


function showContextMenu(e, start, end) {
    startDate = start;
    endDate = end;
    var $contextMenu = $("#context-menu");

    $contextMenu.css({
        display: "block",
        left: e.pageX,
        top: e.pageY
    });

    // context-menu의 버튼을 클릭할 시
    $contextMenu.off("click").on("click", "button", function(event) {
        event.preventDefault();

        if ($(this).data('role') == 'addSchedule') {
            

            $(".start-date").val(formatDate(startDate));
            endDate.setDate(endDate.getDate() - 1);
            $(".end-date").val(formatDate(endDate));

            // 시간을 설정하고 싶다면 아래와 같이 할 수 있습니다.
            $(".start-time").val("00:00");
            $(".end-time").val("23:59");
            showModalMenu();
        }

        $contextMenu.hide();
        $("body").off("click", bodyClickHandler);
    });

    // context-menu 외의 엘레멘트를 클릭할 시
    function bodyClickHandler() {
        $contextMenu.hide();
        $("body").off("click", bodyClickHandler);
    }

    setTimeout(function() {
        $("body").on("click", bodyClickHandler);
    }, 0);

    e.stopPropagation();

    $contextMenu.on("click", function(event) {
        event.stopPropagation();
    });
}

function showModalMenu() {
    var $modalMenu = $("#modal");

    $modalMenu.css({
        display: "block",
    });
}

function formatDate(date) {
    var yyyy = date.getFullYear().toString();
    var mm = (date.getMonth() + 1).toString(); // 0-based index
    var dd = date.getDate().toString();

    return yyyy + '-' + (mm[1] ? mm : '0' + mm[0]) + '-' + (dd[1] ? dd : '0' + dd[0]);
}