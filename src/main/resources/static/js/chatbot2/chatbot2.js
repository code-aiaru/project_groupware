// import
import { addMovieMessageToLog } from '/js/chatbot2/movie.js';
import { sendMovieMessage } from '/js/chatbot2/movie.js';

import { addBusMessageToLog } from '/js/chatbot2/bus.js';
import { sendBusMessage } from '/js/chatbot2/bus.js';

import { addWeatherMessageToLog } from '/js/chatbot2/weather.js'; // 송원철
import { sendWeatherMessage } from '/js/chatbot2/weather.js'; // 송원철

document.addEventListener('DOMContentLoaded', function() {

    const chatbot = document.getElementById('chatbot');
    const chatbotLog = document.querySelector('.chatbot_log');
    let isChatbotInit = false; // 초기 메시지 출력용 플래그
    let askingAbout; // 질문 범주 저장용 변수 (영화, 날씨, 버스)

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


    // 뒤로가기 기능을 위해 선택지 ID를 변수에 저장
    let previousSelectionIds = [];
    
    // // 선택지 출력용
    // function showSelection(selections, scenarioId) {
    //     console.log('previousSelectionId : ', scenarioId);

    //     const selectionDiv = document.createElement('div');
    //     selectionDiv.classList.add('selection');

    //     // 선택지 출력
    //     selections.forEach(selection => {
    //         const selectionText = document.createElement('span');
    //         selectionText.textContent = selection.selection;
    //         selectionText.classList.add('selection_box');
    //         selectionText.setAttribute('data-selection-id', selection.id);
    //         selectionText.addEventListener('click', function() {
                
    //             // 선택한 선택지의 텍스트를 사용자가 보낸 것처럼 메시지 로그에 추가
    //             addMessageToLog(selection.selection, 'user');
    //             // 현재 시나리오 ID를 이전 선택지 목록에 추가
    //             previousSelectionIds.push(scenarioId);
    //             // 선택지를 클릭하면 해당 선택지의 ID를 가지고 다시 서버에 요청
    //             displayScenarioAndSelections(selection.id);

    //         });
    //         selectionDiv.appendChild(selectionText);
    //     });
    
    //     // 그 전단계 선택지를 출력
    //     const selectionToGoBackText = document.createElement('span');
    //     selectionToGoBackText.classList.add('selection_box');
    //     if (scenarioId == 0) {
    //         selectionToGoBackText.textContent = '종료';
    //         selectionToGoBackText.setAttribute('data-selection', 'end');
    //     } else {
    //         selectionToGoBackText.textContent = '뒤로가기';
    //         selectionToGoBackText.setAttribute('data-selection', 'back');
    //     }
    //     selectionToGoBackText.addEventListener('click', function() {
                
    //         if (previousSelectionIds.length > 0) {
    //             addMessageToLog('뒤로가기', 'user');
    //             const previousScenarioId = previousSelectionIds.pop(); // 이전 선택지 ID를 가져오고 목록에서 제거
    //             displayScenarioAndSelections(previousScenarioId);
    //         } else {
    //             addMessageToLog('종료', 'user');
    //             previousSelectionIds = []; // 뒤로가기용 배열 초기화
    //         }

    //     });

    //     selectionDiv.appendChild(selectionToGoBackText);
    
    //     chatbotLog.appendChild(selectionDiv);
    //     chatbotLog.scrollTop = chatbotLog.scrollHeight; // 항상 최신 메시지 보이도록 스크롤 조정      
    // }
    document.addEventListener('click', function(e) {
        if (e.target.classList.contains('selection_box')) {
            handleSelectionClick(e);
        }
    });

    function handleSelectionClick(event) {
        const selectionText = event.target;
        const selectionId = selectionText.getAttribute('data-selection-id');
        const actionType = selectionText.getAttribute('data-selection');
        
        addMessageToLog(selectionText.textContent, 'user');
        
        if (selectionId) { // 선택지 선택시
            const scenarioId = selectionText.getAttribute('data-scenario-id');
            previousSelectionIds.push(scenarioId);
            displayScenarioAndSelections(selectionId);
        } else if (actionType === 'back') { // 뒤로가기 선택시
            if (previousSelectionIds.length > 0) {
                const previousScenarioId = previousSelectionIds.pop();
                displayScenarioAndSelections(previousScenarioId);
            } else {
                console.error('뒤로 가기를 할 수 없습니다.');
            }
        } else if (actionType === 'end') { // 종료 선택시
            previousSelectionIds = [];
        } else {
            console.error('알 수 없는 선택지입니다.');
        }
    }
    
    // 선택지 생성 및 출력
    function showSelection(selections, scenarioId) {
        const selectionDiv = document.createElement('div');
        selectionDiv.classList.add('selection');
    
        selections.forEach(selection => {
            const selectionText = document.createElement('span');
            selectionText.textContent = selection.selection;
            selectionText.classList.add('selection_box');
            selectionText.setAttribute('data-selection-id', selection.id);
            selectionText.setAttribute('data-scenario-id', scenarioId);
            selectionDiv.appendChild(selectionText);
        });
    
        // 그 전단계 선택지를 출력
        const selectionToGoBackText = document.createElement('span');
        selectionToGoBackText.classList.add('selection_box');
        if (scenarioId == 0) {
            selectionToGoBackText.textContent = '종료';
            selectionToGoBackText.setAttribute('data-selection', 'end');
        } else {
            selectionToGoBackText.textContent = '뒤로가기';
            selectionToGoBackText.setAttribute('data-selection', 'back');
        }
        
        selectionDiv.appendChild(selectionToGoBackText);
    
        chatbotLog.appendChild(selectionDiv);
        chatbotLog.scrollTop = chatbotLog.scrollHeight;
    }

    
    
    
    
    
    // 챗봇 로그창에 송수신한 메시지 출력
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
            
            console.log('inputValue : ', inputValue);
            if (inputValue.includes('영화')) {
                sendMovieMessage(inputValue);
            } else if (inputValue.includes('날씨')) {
                sendWeatherMessage(inputValue);
            } else if (inputValue.includes('버스')) {
                sendBusMessage(inputValue);
            } else {
                sendMessage(inputValue);
            }
            // 인풋창 초기화
            event.target.value = '';
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
        console.log('scenarioId : ', scenarioId);
        try {
            const url = `/api/chatbot2/scenario?id=${scenarioId}`;
            const response = await fetch(url);
            if (!response.ok) {
                throw new Error('서버로부터 응답을 받지 못했습니다.');
            }
            const data = await response.json();
            
            // 서버로부터 받은 시나리오 메시지를 화면에 표시
            addMessageToLog(data.scenario.inform, 'bot');
            // 서버로부터 받은 선택지 데이터를 showSelection 함수에 전달
            showSelection(data.selections, scenarioId);

        } catch (error) {
            console.error('오류 발생:', error);
        }
    }

});

