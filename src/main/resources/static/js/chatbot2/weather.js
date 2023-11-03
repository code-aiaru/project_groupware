const chatbotLog = document.querySelector('.chatbot_log');

function addWeatherMessageToLog(message, type) {
    const messageDiv = document.createElement('div');
    messageDiv.classList.add('message', type);

    if (type === 'weatherInfoResult') {
        const weatherInfoDiv = document.createElement('div');
        weatherInfoDiv.classList.add('weather');

        const weatherInfo = message.WeatherDto;
        const cityName = weatherInfo.cityName;
        const temperature = weatherInfo.temp;
        const feelsLike = weatherInfo.feels_like;
        const tempMin = weatherInfo.temp_min;
        const tempMax = weatherInfo.temp_max;
        const humidity = weatherInfo.humidity;
        const messageHTML = `
            <p>도시: ${cityName}</p>
            <p>온도: ${temperature}°C</p>
            <p>체감 온도: ${feelsLike}°C</p>
            <p>최저 온도: ${tempMin}°C</p>
            <p>최고 온도: ${tempMax}°C</p>
            <p>습도: ${humidity}%</p>
        `;

        weatherInfoDiv.innerHTML = messageHTML;
        messageDiv.appendChild(weatherInfoDiv);
    } else {
        const messageText = document.createElement('p');
        messageText.textContent = message;
        messageText.classList.add('message_box');
        messageDiv.appendChild(messageText);
    }

    chatbotLog.appendChild(messageDiv);
    chatbotLog.scrollTop = chatbotLog.scrollHeight;
}

async function sendWeatherMessage(city) {
    if (city) {
        const message = `${city}`; // "도시" 형식의 메시지 생성
        addWeatherMessageToLog(message, 'user');
        const response = await fetch(`/api/chatbot2/chat?message=${encodeURIComponent(message)}`);

        if (response.status === 200) {
            const weatherInfo = await response.json();
            addWeatherMessageToLog(weatherInfo, 'weatherInfoResult');
            return weatherInfo; // 날씨 정보를 반환
        } else {
            const errorMessage = await response.text();
            addWeatherMessageToLog(errorMessage, 'error');
            return errorMessage; // 에러 메시지를 반환
        }
    }
}


export { addWeatherMessageToLog };
export { sendWeatherMessage };
