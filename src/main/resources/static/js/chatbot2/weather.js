//const chatbotLog = document.querySelector('.chatbot_log');
//
//function addWeatherMessageToLog(message, type) {
//    const messageDiv = document.createElement('div');
//    messageDiv.classList.add('message', type);
//
//    if (type === 'wBot') {
//        const weatherInfo = message.botResponse;
//        const cityName = weatherInfo.cityName;
//        const temp = weatherInfo.temp;
//        const feels_like = weatherInfo.feels_like;
//        const temp_min = weatherInfo.temp_min;
//        const temp_max = weatherInfo.temp_max;
//        const humidity = weatherInfo.humidity;
//        const messageHTML = `
//            <p>도시: ${cityName}</p>
//            <p>온도: ${temp}°C</p>
//            <p>체감 온도: ${feels_like}°C</p>
//            <p>최저 온도: ${temp_min}°C</p>
//            <p>최고 온도: ${temp_max}°C</p>
//            <p>습도: ${humidity}%</p>
//        `;
//        const weatherInfoDiv = document.createElement('div');
//        weatherInfoDiv.classList.add('weather');
//        weatherInfoDiv.innerHTML = messageHTML;
//        messageDiv.appendChild(weatherInfoDiv);
//    } else {
//        const messageText = document.createElement('p');
////        messageText.textContent = "날씨 정보를 찾을 수 없습니다.";
////        messageText.classList.add('message_box');
////        messageDiv.appendChild(messageText);
//    }
//
//    chatbotLog.appendChild(messageDiv);
//    chatbotLog.scrollTop = chatbotLog.scrollHeight;
//}
//
//async function sendWeatherMessage(inputValue) {
//    const message = inputValue.trim();
//    if (message) {
//        addWeatherMessageToLog(message, 'user');
//        const response = await fetch(`/api/chatbot2/chat?message=${encodeURIComponent(message)}`);
//
//        if (response.status === 200) {
//            const botResponse = await response.text();
//            console.log(botResponse);
//            addWeatherMessageToLog(botResponse, 'wBot');
//        } else {
//            const errorMessage = await response.text();
//            addWeatherMessageToLog(errorMessage, 'error');
//        }
//    }
//}
//
//export { addWeatherMessageToLog };
//export { sendWeatherMessage };
//
const chatbotLog = document.querySelector('.chatbot_log');

let messageDiv; // messageDiv 변수를 함수 외부에서 선언
let messageText; // messageText 변수를 함수 외부에서 선언

function addWeatherMessageToLog(message, type) {
    messageDiv = document.createElement('div');
    messageDiv.classList.add('message', type);

    // 챗봇 응답 메시지 처리
        if (type === 'bot') {
            messageText = document.createElement('p');
            messageText.textContent = message;
            messageText.classList.add('message_box');
        } 
//         else {
//             if (message.startsWith("날씨현재")) {
//                     message = message.substring(4); // "날씨현재"를 제외한 나머지 부분만 추출
//                 }
//             // 사용자 메시지 처리
//             messageText = document.createElement('p');
//             messageText.textContent = message;
//             messageText.classList.add('message_box');
//             // 메시지 상자를 감추는 스타일 적용
// //            messageText.style.display = 'none';
//         }
    messageDiv.appendChild(messageText);
    chatbotLog.appendChild(messageDiv);
    chatbotLog.scrollTop = chatbotLog.scrollHeight;
}

async function sendWeatherMessage(inputValue) {
    const message = inputValue.trim();
    if (message) {
        // addWeatherMessageToLog(message, 'user');
        const response = await fetch(`/api/chatbot2/chat?message=${encodeURIComponent(message)}`);

        if (response.status === 200) {
            const botResponse = await response.text();
            console.log(botResponse);
            addWeatherMessageToLog(botResponse, 'bot');
        } else {
            const errorMessage = await response.text();
            addWeatherMessageToLog(errorMessage, 'error');
        }
    }
}

export { addWeatherMessageToLog };
export { sendWeatherMessage };

