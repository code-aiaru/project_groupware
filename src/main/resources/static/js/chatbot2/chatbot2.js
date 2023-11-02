// import
import { addMovieMessageToLog } from '/js/chatbot2/movie.js';
import { sendMovieMessage } from '/js/chatbot2/movie.js';

import { addBusMessageToLog } from '/js/chatbot2/bus.js';
import { sendBusMessage } from '/js/chatbot2/bus.js';

document.addEventListener('DOMContentLoaded', function() {

    const chatbot = document.getElementById('chatbot');
    const chatbotLog = document.querySelector('.chatbot_log');
    let isChatbotInit = false; // 초기 메시지 출력용 플래그
    let scenarioFor; // 시나리오 종류
    let selectionId = []; // 반환된 선택지 id (받기 전용)
    let selectionOpt = []; // 반환된 선택지 (받기 전용)
    let selectionNum; // 선택지 개수 (받기 전용)

    // 챗봇 열기.
    document.addEventListener('click', function(e) {
        if (e.target.closest('button[data-chatbot]')) {
            if (chatbot.style.display == 'none') {
                chatbot.style.display = 'block';
            } else {
                chatbot.style.display = 'none';
            }
        }

        // 첫번째 시퀀스일 경우
        if (isChatbotInit == false) {
            displayScenarioAndSelections(0); // 기본값으로 1을 설정
            isChatbotInit = true;
        }
    });



    // 선택지
    const selectionTarget = document.querySelector('[data-selection]');

    function showSelection(selections) {
        const selectionDiv = document.createElement('div');
        selectionDiv.classList.add('selection');
        
        // 선택지 출력
        selections.forEach(selection => {
            const selectionText = document.createElement('span');
            selectionText.textContent = selection.selection;
            selectionText.classList.add('selection_box');
            selectionText.setAttribute('data-selection-id', selection.id);
            selectionText.addEventListener('click', function() {
                // 선택지를 클릭하면 해당 선택지의 ID를 가지고 다시 서버에 요청
                displayScenarioAndSelections(selection.id);
            });
            selectionDiv.appendChild(selectionText);
        });
    
        // 그 전단계 선택지를 출력
        const selectionText = document.createElement('span');
        selectionText.textContent = '뒤로가기';
        selectionText.classList.add('selection_box');
        selectionText.setAttribute('data-selection', 'back');
        selectionDiv.appendChild(selectionText);
    
        chatbotLog.appendChild(selectionDiv);
        chatbotLog.scrollTop = chatbotLog.scrollHeight; // 항상 최신 메시지 보이도록 스크롤 조정      
    }

    // 선택지를 클릭할 시
    // selectionTarget.addEventListener('click', function(event) {
        
    //     if (isChatbotInit == 0) {
    //         const selectionClicked = event.target.closest('span[data-selection="go"]');
            
    //         if (selectionClicked) {
    //             if (selectionClicked.textContent == '영화') {
    //                 scenarioFor = '영화';
    //             } else if (selectionClicked.textContent == '날씨') {
    //                 scenarioFor = '날씨';
    //             } else if (selectionClicked.textContent == '버스') {
    //                 scenarioFor = '버스';
    //             } 
    //         }
    //     } 

      
    //     sendSelection(inputValue, isChatbotInit, scenarioFor)
    // });







    // 입력해서 선택해야하는 경우

    
    
    
    
    
    
    
    
    
    
    
    
    
    function addMessageToLog(message, type) {
        const messageDiv = document.createElement('div');
        messageDiv.classList.add('message', type);
        // messageDiv.textContent = message;
        const messageText = document.createElement('p');
        messageText.textContent = message;
        messageText.classList.add('message_box');

        messageDiv.appendChild(messageText);
        chatbotLog.appendChild(messageDiv);
        chatbotLog.scrollTop = chatbotLog.scrollHeight; // 항상 최신 메시지 보이도록 스크롤 조정
    }

    // 챗봇 메시지 보내기.
    const chatbotInput = document.getElementById('chatbot_input');
    chatbotInput.addEventListener('keyup', function(event) {
        if (event.key === 'Enter') {
            // 입력된 값을 가져오고, input을 비웁니다.
            const inputValue = event.target.value;
            event.target.value = '';

 			// 입력된 값을 다른 함수로 보냅니다.
            sendMovieMessage(inputValue);

//            // 버스 답변용
//            sendBusMessage(inputValue);
        }
    });

    // 일반 챗봇 메세지 통신
    async function sendMessage(inputValue) {
        const message = inputValue.trim();
        if (message) {
            addMessageToLog(message, 'user'); // 사용자 메시지 로그에 추가
            const response = await fetch(`/api/chatbot2/chat?message=${encodeURIComponent(message)}`);
            const text = await response.text();
            console.log(text); // 서버에서 받은 응답을 콘솔에 출력
            addMessageToLog(text, 'bot'); // 챗봇 응답 로그에 추가
        }
    }


    async function displayScenarioAndSelections(scenarioId) {
        try {
            // 서버에 시나리오와 선택지를 요청
            const url = `/api/chatbot2/scenario?id=${scenarioId}`;
            const response = await fetch(url); // 수정: fetch의 결과를 response 변수에 저장
            if (!response.ok) {
                throw new Error('서버로부터 응답을 받지 못했습니다.');
            }
            // 응답을 JSON 형태로 파싱
            const data = await response.json();
            
            // 서버로부터 받은 시나리오 메시지를 화면에 표시
            addMessageToLog(data.scenario.anouncement, 'bot');
            
            // 서버로부터 받은 선택지 데이터를 showSelection 함수에 전달
            showSelection(data.selections);
        } catch (error) {
            console.error('오류 발생:', error);
        }
    }

});

