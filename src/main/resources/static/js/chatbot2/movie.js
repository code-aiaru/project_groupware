const chatbotLog = document.querySelector('.chatbot_log');

function addMovieMessageToLog(message, type) {
    const messageDiv = document.createElement('div');
    messageDiv.classList.add('message', type);

    if (type === 'boxOfficeResult') {
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
    } else if (type === 'movieInfoResult') {
        const movieInfoDiv = document.createElement('div');
        movieInfoDiv.classList.add('movie-info');

        const movieInfo = message.movieInfo;
        const movieName = movieInfo.movieNm;
        const genre = movieInfo.genres.map(genre => genre.genreNm).join(', ');
        const watchGrade = movieInfo.audits.map(audit => audit.watchGradeNm).join(', ');
        const directors = movieInfo.directors.map(director => director.peopleNm).join(', ');
        const actors = movieInfo.actors.map(actor => `${actor.peopleNm} (${actor.cast})`).join(', ');
        const showTime = movieInfo.showTm;
        const nations = movieInfo.nations.map(nation => nation.nationNm).join(', ');

        const messageHTML = `
            <p>영화 제목: ${movieName}</p>
            <p>장르: ${genre}</p>
            <p>관람 등급: ${watchGrade}</p>
            <p>감독: ${directors}</p>
            <p>배우: ${actors}</p>
            <p>상영 시간: ${showTime}분</p>
            <p>국가: ${nations}</p>
        `;

        movieInfoDiv.innerHTML = messageHTML;
        messageDiv.appendChild(movieInfoDiv);
    } else {
        const messageText = document.createElement('p');
        messageText.textContent = message;
        messageText.classList.add('message_box');
        messageDiv.appendChild(messageText);
    }

    chatbotLog.appendChild(messageDiv);
    chatbotLog.scrollTop = chatbotLog.scrollHeight;
}

async function sendMovieMessage(inputValue) {
    const message = inputValue.trim();
    if (message) {
        addMovieMessageToLog(message, 'user');
        const response = await fetch(`/api/chatbot2/chat?message=${encodeURIComponent(message)}`);
        const jsonResponse = await response.json();

        const boxOfficeResult = jsonResponse.boxOfficeResult;
        const movieInfoResult = jsonResponse.movieInfoResult;

        if (boxOfficeResult && boxOfficeResult.weeklyBoxOfficeList) {
            addMovieMessageToLog(boxOfficeResult.weeklyBoxOfficeList, 'boxOfficeResult');
        }

        if (movieInfoResult) {
            addMovieMessageToLog(movieInfoResult, 'movieInfoResult');
        }
    }
}

export { addMovieMessageToLog };
export { sendMovieMessage };