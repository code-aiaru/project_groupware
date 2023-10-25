
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
        selectable                  : true,
        // editable                    : true,
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
            initializeContextMenu(info, 'select');
        },
        eventClick: function(info) {
            const currentUserId = document.getElementById('currentUserId').textContent;
            const currentUserRole = document.getElementById('currentUserRole').textContent;
            console.log('해당 이벤트 writer Id: ', info.event.extendedProps.writer);
            console.log('현재 로그인된 user Id: ', currentUserId);
            if (info.event.extendedProps.writer == currentUserId || currentUserRole == 'ADMIN') {
                initializeContextMenu(info, 'eventClick');
            }
        },    
        events: function(fetchInfo, successCallback, failureCallback) {
            readAllScheduleRequest(fetchInfo, successCallback, failureCallback);
        },


	});

    calendar.render();


    // 모달 관련 로직 ==========================================================================


    function initializeContextMenu(info, sourceType) {

        var $contextMenu = $("#context-menu");
        $contextMenu.css({
            display: "block",
            left: info.jsEvent.pageX,
            top: info.jsEvent.pageY
        });
        var $scheduleAddBtn = $("#schedule-add-btn");
        $scheduleAddBtn.css({
            display: "none"
        });  
        var $scheduleUpdateBtn = $("#schedule-update-btn");
        $scheduleUpdateBtn.css({
            display: "none"
        });
        var $scheduleDeleteBtn = $("#schedule-delete-btn");
        $scheduleDeleteBtn.css({
            display: "none"
        });
 
        // calendar 영역을 클릭해도 닫히지 않도록 (안정성)
        var $calendar = $("#calendar");
        $calendar.on("click", function(event) {
            event.stopPropagation();
        });

        // contextMenu 영역을 클릭해도 닫히지 않도록 (안정성)
        $contextMenu.on("click", function(event) {
            event.stopPropagation();
        });

        showContextMenu(info, sourceType);

    }
    
    function showContextMenu(info, sourceType) {

        if (sourceType === 'select') {
            console.log('select activated');
            var $scheduleAddBtn = $("#schedule-add-btn");
            $scheduleAddBtn.css({
                display: "inline-block"
            });    
        } else if (sourceType === 'eventClick') {
            console.log('eventClick activated');
            var $scheduleUpdateBtn = $("#schedule-update-btn");
            $scheduleUpdateBtn.css({
                display: "inline-block"
            });
            var $scheduleDeleteBtn = $("#schedule-delete-btn");
            $scheduleDeleteBtn.css({
                display: "inline-block"
            });
        }


        var $contextMenu = $("#context-menu");
        $contextMenu.off("click").on("click", "button", function(event) {
            event.preventDefault();
    
            if ($(this).data('role') == 'addSchedule') {
                console.log('initializeModalMenu(addSchedule) activated')
                initializeModalMenu(info, 'addSchedule');

            } else if ($(this).data('role') == 'updateSchedule') { 
                console.log('initializeModalMenu(updateSchedule) activated')
                initializeModalMenu(info, 'updateSchedule');

            } else if ($(this).data('role') == 'deleteSchedule') {
                console.log('deleteSchedule activated')
                deleteScheduleRequest(info.event.id);

            }

            $contextMenu.hide();
        });

        $("body").on("click", function(event) {
            $contextMenu.hide();
        });    
        
    }

    function initializeModalMenu(info, submitType) {
        
        var $addSubmitText = $("#add-submit-text");
        var $updateSubmitText = $("#update-submit-text");
        var $addScheduleTitle = $("#add-schedule-title");
        var $updateScheduleTitle = $("#update-schedule-title");
    
        if (submitType === 'addSchedule') {
            $addScheduleTitle.css({
                display: "block"
            });
            $addSubmitText.css({
                display: "block"
            });
            $updateScheduleTitle.css({
                display: "none"
            });    
            $updateSubmitText.css({
                display: "none"
            });

        } else if (submitType === 'updateSchedule') {
            $updateScheduleTitle.css({
                display: "block"
            });
            $updateSubmitText.css({
                display: "block"
            });
            $addSubmitText.css({
                display: "none"
            });
            $addScheduleTitle.css({
                display: "none"
            });
        }

        showModalMenu(info, submitType);
    }



    function showModalMenu(info, submitType) {

        if (submitType === 'addSchedule') {
            setAddScheduleModalData(info);

        } else if (submitType === 'updateSchedule') {
            setUpdateScheduleModalData(info);
        }

        var $modalMenu = $("#modal");
        $modalMenu.css({
            display: "block",
        });

         // "취소" 버튼 클릭 시 로직
        $('#submit-schedule-cancel_btn').on('click', function() {
            resetModal();
        });

        // "추가" 혹은 "수정" 버튼 클릭 시 로직
        $('#submit-schedule_btn').on('click', function() {
            submitSchedule(info, submitType);
        });
        
        // '하루종일' 체크박스의 변경 이벤트에 리스너를 추가합니다.
        $('#schedule-isAllDay').on('change', function() {
            if ($(this).prop('checked')) {
                // 체크박스가 체크되면 startTime과 endTime 입력란을 비활성화하고 값을 지웁니다.
                $(".start-time").prop('disabled', true).val('00:00');
                $(".end-time").prop('disabled', true).val('23:59');
            } else {
                // 체크박스가 해제되면 startTime과 endTime 입력란을 활성화하고 초기값으로 설정합니다.
                $(".start-time").prop('disabled', false).val("00:00");
                $(".end-time").prop('disabled', false).val("23:59");
            }
        });

    }

    function setAddScheduleModalData(info) {
        // info.start의 날짜를 하루 감소
        var endDate = new Date(info.end);
        endDate.setDate(endDate.getDate() - 1);

        $(".start-date").val(formatDate(info.start));
        $(".end-date").val(formatDate(endDate));
        $(".start-time").val("00:00");
        $(".end-time").val("23:59");
    }

    function setUpdateScheduleModalData(info) {

        console.log('isAllDay DB에서 가져온 값', info.event.extendedProps.isAllDay)

        // $('#schedule-name').val(info.event.title);
        $('#schedule-name').val(info.event.title.replace(/\[(전체|관리자|사원|개인)\]\s/g, "").trim());

        $('#schedule-isAllDay').prop('checked', info.event.extendedProps.isAllDay);
        if ($('#schedule-isAllDay').prop('checked')) {
            // 체크박스가 체크되면 startTime과 endTime 입력란을 비활성화하고 값을 지웁니다.
            $(".start-time").prop('disabled', true).val('00:00');
            $(".end-time").prop('disabled', true).val('23:59');
        } else {
            $(".start-date").val(formatDate(info.event.start));
            $(".end-date").val(formatDate(info.event.end));
        }

        $('#schedule-target').val(info.event.extendedProps.target);
        $('#schedule-color').val(info.event.backgroundColor);

        // Start time 설정 for KST (Korea Standard Time)
        const kstStart = new Date(info.event.start.getTime() + (9 * 60 * 60 * 1000)); // Add 9 hours in milliseconds
        $(".start-time").val(kstStart.getUTCHours().toString().padStart(2, '0') + ':' + kstStart.getUTCMinutes().toString().padStart(2, '0'));

        // End time 설정 for KST (Korea Standard Time)
        const kstEnd = new Date(info.event.end.getTime() + (9 * 60 * 60 * 1000)); // Add 9 hours in milliseconds
        $(".end-time").val(kstEnd.getUTCHours().toString().padStart(2, '0') + ':' + kstEnd.getUTCMinutes().toString().padStart(2, '0'));
    }

    function formatDate(date) {
        var yyyy = date.getFullYear().toString();
        var mm = (date.getMonth() + 1).toString(); // 0-based index
        var dd = date.getDate().toString();
        return yyyy + '-' + (mm[1] ? mm : '0' + mm[0]) + '-' + (dd[1] ? dd : '0' + dd[0]);
    }

    function resetModal() {
        $('#modal').hide();

        // 입력 정보 초기화
        $(".start-time").prop('disabled', false).val("00:00");
        $(".end-time").prop('disabled', false).val("23:59");
        $('#schedule-isAllDay').prop('checked', false);
        $('#schedule-name').val(''); 
        $('#schedule-target').prop('selectedIndex', 0);
        $('#schedule-color').prop('selectedIndex', 0);

        // 리스너 초기화
        $('#submit-schedule-cancel_btn').off('click');
        $('#submit-schedule_btn').off('click');
    }

    function submitSchedule(info, submitType) {
        // "추가" 버튼 클릭 시 로직 (이벤트 추가)
            
        console.log("submit activated");
        console.log(submitType);

        // 사용자 입력 정보 가져오기
        var isAllDay = $('#schedule-isAllDay').prop('checked');
        var title = $('#schedule-name').val();
        var target = $('#schedule-target').val();
        var color = $('#schedule-color').val();

        console.log(isAllDay);
        console.log(title);
        console.log(target);
        console.log(color);

        // 사용자가 입력한 날짜 및 시간 가져오기
        var startDate = $(".start-date").val(); 
        var endDate = $(".end-date").val();     
        var startTime = $(".start-time").val().split(':'); 
        var endTime = $(".end-time").val().split(':');     
        
        console.log('startDate: ', startDate);
        console.log('endDate: ', endDate);
        console.log('startTime: ', startTime);
        console.log('endTime: ', endTime);
        
        // 필수 입력 정보가 없을 때 alert 창 띄우기
        if (!title || !startDate || !endDate) {
            alert("필수 정보를 모두 입력해주세요!");
            return; // 함수를 여기서 종료
        }

        // startDate와 endDate에 시간 반영      
        var start = mergeDateTimeAsISO8601(startDate, startTime);
        var end = mergeDateTimeAsISO8601(endDate, endTime);

        console.log('start: ', start);
        console.log('end: ', end);

        

        if (submitType === 'addSchedule') {
            var newEvent = {
                title: title,
                start: start,
                end: end,
                isAllDay: isAllDay,
                target: target,
                color: color
            };
            addScheduleRequest(newEvent);
        } else if (submitType === 'updateSchedule') {
            console.log('subbbbbbb: ', submitType);
            var updatedEvent = {
                title: title,
                start: start,
                end: end,
                isAllDay: isAllDay,
                target: target,
                color: color
            };
            console.log('updatedEvent: ', updatedEvent);
            updateScheduleRequest(info.event.id, updatedEvent);
        }
        resetModal();
    }

    // ISO8601 형식으로 날짜를 변환
    function mergeDateTimeAsISO8601(date, time) {
        // 날짜와 시간을 Date 객체로 변환합니다.
        const dateTime = new Date(`${date}T${time[0]}:${time[1]}:00Z`);
    
        // 9시간(= 32400000 밀리초)을 뺍니다.
        dateTime.setTime(dateTime.getTime() - 9 * 60 * 60 * 1000);
    
        // Date 객체를 ISO 8601 형식 문자열로 변환합니다.
        return dateTime.toISOString();
    }



    // 스케줄 생성
    function addScheduleRequest(newEvent) {
        $.ajax({
            url: '/api/full-calendar',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(newEvent),
            success: function(response) {
                console.log('Event data sent successfully:', response);
                calendar.refetchEvents();
            },
            error: function(error) {
                console.error('Error sending event data:', error);
            }
        });
    }

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


    // 스케줄 수정
    function updateScheduleRequest(eventId, updatedEvent) {
        $.ajax({
            url: '/api/full-calendar/' + eventId,
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(updatedEvent),
            success: function(response) {
                console.log('Event updated successfully:', response);
                calendar.refetchEvents();  // 이벤트를 업데이트 한 후 캘린더 이벤트를 다시 가져옴
            },
            error: function(error) {
                console.error('Error updating event:', error);
            }
        });
    }

    // 스케줄 삭제
    function deleteScheduleRequest(id) {
        if (id) {
            $.ajax({
                url: '/api/full-calendar/' + id,
                type: 'DELETE',
                success: function(response) {
                    console.log('Event deleted successfully:', response);
                    var eventToDelete = calendar.getEventById(id);
                    if (eventToDelete) {
                        eventToDelete.remove();
                    }
                },
                error: function(error) {
                    console.error('Error deleting event:', error);
                }
            });
        } else {
            console.error('No event selected to delete.');
        }
    }

    let scheduleColorSaved;
    // 선택된 색상을, select 태그에 넣어주는 로직
    const scheduleColor = document.getElementById('schedule-color');
    scheduleColor.addEventListener('change', function() {
        var selectedOption = this.options[this.selectedIndex];
        scheduleColorSaved = selectedOption;
        this.style.backgroundColor = selectedOption.style.backgroundColor;
    });

    // 원래의 배경색으로 초기화
    scheduleColor.addEventListener("focusin", function() {
        this.style.backgroundColor = ""; // 원래의 배경색으로 초기화
    });

    scheduleColor.addEventListener("focusout", function() {
        if (scheduleColorSaved) { // 선택된 색상이 있을 경우만 배경색을 업데이트
            this.style.backgroundColor = scheduleColorSaved.style.backgroundColor;
        }
    });


}

