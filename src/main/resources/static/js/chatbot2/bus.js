const chatbotLog = document.querySelector('.chatbot_log');



function addBusMessageToLog(busResponse, type) {
        const messageDiv = document.createElement('div');
        messageDiv.classList.add('message', 'bot');

    // 버스 정보
    if (type === 'bus') {
        const messageText = document.createElement('p');
        messageText.textContent = busResponse;
        messageText.classList.add('message_box');

        messageDiv.appendChild(messageText);
    } else if(type==='station'){
        // 정류장 정보
        const stationListDiv = document.createElement('div');
        stationListDiv.classList.add('message_box');

        (busResponse.msgBody.itemList).forEach(item => {
            const busRouteId = item.busRouteId; //노선ID  *****
            const busRouteNm = item.busRouteNm; //노선명   ***
            const seq = item.seq;               //순번    *****
            const station = item.station;       //정류소 고유 ID *****
            const stationNm = item.stationNm;   //정류소 명 ***
            const direction = item.direction;   //진행방향  ***

            const messageHTML =
            `<button id="seq${seq}" class="btn">${seq} : ${stationNm}</button>`;

            const stationInfoDiv = document.createElement('div');
            stationInfoDiv.innerHTML = messageHTML;
            stationListDiv.appendChild(stationInfoDiv);

            stationInfoDiv.querySelector(`#seq${seq}`).addEventListener('click', function() {
                // 정류장 값 전달
                sendTimeRequest({ busRouteId, station, seq });
            });
        });

        messageDiv.appendChild(stationListDiv);

    } else if(type==='time'){
        // 도착 시간 정보
        const timeListDiv = document.createElement('div');
        timeListDiv.classList.add('message_box');

        (busResponse.msgBody.itemList).forEach(item => {
            const busRouteId = item.busRouteId;    //노선ID  *****
            const busRouteAbrv = item.busRouteAbrv;//노선명
            const arrmsg1 = item.arrmsg1           //첫번째 버스 도착 메세지
            const arrmsg2 = item.arrmsg2           //두번째 버스 도착 메세지
            const stId = item.stId;                //정류소 고유 ID *****
            const stNm = item.stNm;                //정류소 명 ***

            const messageHTML =
            `<p>${stNm}</p>
            <p>${arrmsg1}</p>
            <p>${arrmsg2}</p>`;

            const timeInfoDiv = document.createElement('div');
            timeInfoDiv.innerHTML = messageHTML;
            timeListDiv.appendChild(timeInfoDiv);
        });

        messageDiv.appendChild(timeListDiv);

    } else {
        const messageText = document.createElement('p');
        messageText.textContent = busResponse;
        messageText.classList.add('message_box');

        messageDiv.appendChild(messageText);
    }

    chatbotLog.appendChild(messageDiv);
    chatbotLog.scrollTop = chatbotLog.scrollHeight; // 항상 최신 메시지 보이도록 스크롤 조정
}


//async function stationTime(busRouteId, stId, ord) {
////    const stationSeq = inputValue.trim();
//    const response = await fetch(`/api/rest/arrive/getArrInfoByRoute`)
//}

async function sendBusMessage(inputValue) {
    const message = inputValue.trim();
    if (message) {
        const response = await fetch(`/api/chatbot2/chat?message=${encodeURIComponent(message)}`);

        if(message.includes('정류장')){
            const busResponse = await response.json();
            console.log('sendBus inputValue: ', busResponse);
            console.log('type : station');

            addBusMessageToLog(busResponse, 'station'); // 테스트
        }else if(message.includes('시간')){
            const busResponse = await response.json();
            console.log('type : time');
            console.log('sendBus inputValue: ', busResponse);

            addBusMessageToLog(busResponse, 'time');    // 테스트
        }else{
            const busResponse = await response.text();
            console.log('type : bus');
            console.log('sendBus inputValue: ', busResponse);

            addBusMessageToLog(busResponse, 'bus');     // 테스트
        }
    }
}

function sendTimeRequest(data) {
    fetch('/api/bus/stationTime', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    })
    .then(response => response.json())
    .then(responseData => {
        console.log('서버 응답:', responseData);
         addBusMessageToLog(responseData, 'time');
    })
    .catch(error => {
        console.error('에러 발생:', error);
    });
}

// export
export { addBusMessageToLog };
export { sendBusMessage };
