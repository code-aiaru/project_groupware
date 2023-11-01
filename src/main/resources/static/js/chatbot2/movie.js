const chatbotLog = document.querySelector('.chatbot_log');

function addMovieMessageToLog(message, type) {
    const messageDiv = document.createElement('div');
    messageDiv.classList.add('message', type);

    // 챗봇 응답 메시지 처리
    if (type === 'bot') {
        const movieListDiv = document.createElement('div');
        movieListDiv.classList.add('movie-list');

        message.forEach(movie => {
            const movieName = movie.movieNm;
            const rank = movie.rank;
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






async function sendMovieMessage(inputValue) {
    const message = inputValue.trim();
    if (message) {
        addMovieMessageToLog(message, 'user'); // 사용자 메시지 로그에 추가
        const response = await fetch(`/api/chatbot2/chat?message=${encodeURIComponent(message)}`);
        const jsonResponse = await response.json(); // JSON 형식으로 파싱
        const botResponse = jsonResponse.boxOfficeResult.weeklyBoxOfficeList; // 원하는 데이터 추출
        console.log(botResponse); // 서버에서 받은 응답 데이터를 콘솔에 출력

        // 이제 원하는 데이터를 화면에 표시합니다.
        addMovieMessageToLog(botResponse, 'bot'); // 챗봇 응답 로그에 추가
    }
}

// export
export { addMovieMessageToLog };
export { sendMovieMessage };