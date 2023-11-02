const chatbotLog = document.querySelector('.chatbot_log');

let messageDiv; // messageDiv 변수를 함수 외부에서 선언
let messageText; // messageText 변수를 함수 외부에서 선언

function addBusMessageToLog(message, type) {
    messageDiv = document.createElement('div');
    messageDiv.classList.add('message', type);

    // 챗봇 응답 메시지 처리
    if (type === 'bot') {
        messageText = document.createElement('p');
        messageText.textContent = message;
        messageText.classList.add('message_box');
    } else {
        // 사용자 메시지 처리
        messageText = document.createElement('p');
        messageText.textContent = message;
        messageText.classList.add('message_box');
    }

    messageDiv.appendChild(messageText);
    chatbotLog.appendChild(messageDiv);
    chatbotLog.scrollTop = chatbotLog.scrollHeight; // 항상 최신 메시지 보이도록 스크롤 조정
}

async function sendBusMessage(inputValue) {
    const message = inputValue.trim();
    if (message) {
        addBusMessageToLog(message, 'user'); // 사용자 메시지 로그에 추가
        const response = await fetch(`/api/chatbot2/chat?message=${encodeURIComponent(message)}`);
//        const jsonResponse = await response.json();
        const botResponse = await response.text();;

        console.log(botResponse);
        addBusMessageToLog(botResponse, 'bot'); // 챗봇 응답 로그에 추가
    }
}

// export
export { addBusMessageToLog };
export { sendBusMessage };
