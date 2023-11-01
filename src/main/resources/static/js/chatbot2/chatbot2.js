document.addEventListener('DOMContentLoaded', function() {

    // 챗봇 열기.
    const chatbot = document.getElementById('chatbot');

    document.addEventListener('click', function(e) {
        if (e.target.closest('button[data-chatbot]')) {
            if (chatbot.style.display == 'none') {
                chatbot.style.display = 'block';
            } else {
                chatbot.style.display = 'none';
            }
        }
    });

    // 챗봇 메시지 보내기.
    const chatbotInput = document.getElementById('chatbot_input');
    chatbotInput.addEventListener('keyup', function(event) {
        if (event.key === 'Enter') {
            // 입력된 값을 가져오고, input을 비웁니다.
            const inputValue = event.target.value;
            event.target.value = '';

            // 입력된 값을 다른 함수로 보냅니다.
            sendMessage(inputValue);
        }
    });

    // 챗봇 로그에 띄우기.
    const chatbotLog = document.querySelector('.chatbot_log');
function addMessageToLog(message, type) {
    const messageDiv = document.createElement('div');
    messageDiv.classList.add('message', type);

    // 챗봇 응답 메시지 처리
    if (type === 'bot') {
        const movieListDiv = document.createElement('div');
        movieListDiv.classList.add('movie-list');

        message.forEach(movie => {
            const movieName = movie.movieNm;
            const rank = movie.rank;
            const salesChange = movie.salesChange;
            const messageHTML = `<p>영화 순위: ${rank}, 영화 제목: ${movieName}</p>`;
            const movieInfoDiv = document.createElement('div');
            movieInfoDiv.innerHTML = messageHTML;
            movieListDiv.appendChild(movieInfoDiv);
        });

        messageDiv.appendChild(movieListDiv);
    } else {
        // 사용자 메시지 처리
        const messageText = document.createElement('p');
        messageText.textContent = message;
        messageText.classList.add('message_box');
        messageDiv.appendChild(messageText);
    }

    chatbotLog.appendChild(messageDiv);
    chatbotLog.scrollTop = chatbotLog.scrollHeight; // 항상 최신 메시지 보이도록 스크롤 조정
}
async function sendMessage(inputValue) {
    const message = inputValue.trim();
    if (message) {
        addMessageToLog(message, 'user'); // 사용자 메시지 로그에 추가
        const response = await fetch(`/api/chatbot2/chat?message=${encodeURIComponent(message)}`);
        const jsonResponse = await response.json(); // JSON 형식으로 파싱
        const botResponse = jsonResponse.boxOfficeResult.weeklyBoxOfficeList; // 원하는 데이터 추출
        console.log(botResponse); // 서버에서 받은 응답 데이터를 콘솔에 출력

        // 이제 원하는 데이터를 화면에 표시합니다.
        addMessageToLog(botResponse, 'bot'); // 챗봇 응답 로그에 추가
    }
}



});
