// import
import { addMovieMessageToLog } from '/js/chatbot2/movie.js';
import { sendMovieMessage } from '/js/chatbot2/movie.js';



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

    // 첫 시작메시지

    // 물어보고싶은거? 1. 영화  2. 날

    // 1

    //


    // 사용자 선택

    // 안내메시지

    // 선택

    // 안내메시지

    // 챗봇 메시지 보내기.
    const chatbotInput = document.getElementById('chatbot_input');
    chatbotInput.addEventListener('keyup', function(event) {
        if (event.key === 'Enter') {
            // 입력된 값을 가져오고, input을 비웁니다.
            const inputValue = event.target.value;
            event.target.value = '';

            // 입력된 값을 다른 함수로 보냅니다.
            sendMovieMessage(inputValue);
        }
    });

});

