
initializeScript();

function initializeScript() {

    var calendarEl = document.getElementById('calendar');
	var calendar = new FullCalendar.Calendar(calendarEl, {
		initialView : 'dayGridMonth', // 초기 로드 될때 보이는 캘린더 화면(기본 설정: 달)
		headerToolbar : { // 헤더에 표시할 툴 바
			start : 'today',
			center : 'prevYear prev title next nextYear',
			end : 'dayGridMonth,dayGridWeek,dayGridDay'
		},
		titleFormat : function(date) {
			return date.date.year + '년 ' + (parseInt(date.date.month) + 1) + '월';
		},
        // dayNames: ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'],
        buttonText : {
            today: '오늘',
            month: '월간',
            week: '주간',
            day: '일간'
        },
        dayCellContent: function(info) {
            var number = document.createElement('a');
            number.classList.add('fc-daygrid-day-number');
            number.innerHTML = info.dayNumberText.replace('일','');
            if (info.view.type === 'dayGridMonth') {
                return {
                    html: number.outerHTML
                };
            }
            return {
                domNodes: []
            };
        },
		//initialDate: '2021-07-15', // 초기 날짜 설정 (설정하지 않으면 오늘 날짜가 보인다.)
		selectable : true, // 달력 일자 드래그 설정가능
		droppable : true,
		editable : true,
		nowIndicator: true, // 현재 시간 마크
		locale: 'ko' // 한국어 설정
	});
	calendar.render();

}