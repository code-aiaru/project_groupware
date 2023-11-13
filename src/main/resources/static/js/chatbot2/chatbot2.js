// imports
import { addMovieMessageToLog } from '/js/chatbot2/movie.js';
import { sendMovieMessage } from '/js/chatbot2/movie.js';

import { addBusMessageToLog } from '/js/chatbot2/bus.js';
import { sendBusMessage } from '/js/chatbot2/bus.js';

import { addWeatherMessageToLog } from '/js/chatbot2/weather.js'; // 송원철
import { sendWeatherMessage } from '/js/chatbot2/weather.js'; // 송원철





document.addEventListener('DOMContentLoaded', function() {

    const chatbot = document.getElementById('chatbot');
    const chatbotLog = document.querySelector('.chatbot_log');
    let isChatbotInit = true; // 초기 메시지 출력용 플래그
    let askingAbout; // 질문 범주 저장용 변수 (ex. 영화, 날씨, 버스)
    let askingFor; // 세부 범주
    let responseType; // 질문에 대한 답변 방법 (enum. TEXT, VALUE)
    let previousSelectionIds = []; // 뒤로가기를 위한 이전 선택지 id 저장용


    // 챗봇 열고 닫기 관련 로직 =============================================================================================
    
    // 챗봇 가시성 여부
    document.addEventListener('click', function(e) {
        const chatbotButton = e.target.closest('#chatbot_icon');
        const clickedInsideChatbot = e.target.closest('#chatbot');
    
        if (chatbotButton) {
            showHideToggleChatbot();
            initProcessChatbot();
            e.stopPropagation();
        } else if (!clickedInsideChatbot) {
            hideChatbot();
        }
    });

    // 챗봇 버튼 함수
    async function initProcessChatbot() {
        if (isChatbotInit && previousSelectionIds.length === 0) {
            await resetChatbotLog();
            await requestScenarioAndSelections(0);
            isChatbotInit = false;
            console.log('isChatbotInit : ', isChatbotInit);
        }
    }
    
    function showHideToggleChatbot() {
        if (chatbot.style.display === 'none') {
            chatbot.style.display = 'block';
        } else if (chatbot.style.display === 'block') {
            chatbot.style.display = 'none';
        }
    }

    // 챗봇의 바깥 쪽을 클릭 시, 챗봇을 숨기는 함수
    function hideChatbot() {
        if (chatbot.style.display === 'block') {
            chatbot.style.display = 'none';
        }
    }

    // 챗봇로그 리셋
    async function resetChatbotLog() {
        const chatbotLog = document.querySelector('.chatbot_log');
        chatbotLog.innerHTML = '';
    }
  
    // ==================================================================================================================


    // 선택지 관련 로직 ====================================================================================================
    
    // 선택지 클릭시
    document.addEventListener('click', function(e) {
        if (e.target.getAttribute('data-selection-id') !== null || e.target.getAttribute('data-selection-action')) {
            handleSelectionClick(e);
        }
    });
    
    // 선택지 hover시 색상변경
    document.addEventListener('mouseover', function(e) {
        if (e.target.getAttribute('data-selection-id') !== null || e.target.getAttribute('data-selection-action') !== null) {
            e.target.classList.add('--selectable');
        }
    });
    document.addEventListener('mouseout', function(e) {
        if (e.target.getAttribute('data-selection-id') !== null || e.target.getAttribute('data-selection-action') !== null) {
            e.target.classList.remove('--selectable');
        }
    });

    // 선택지 처리 로직
    function handleSelectionClick(event) {
        const selectionText = event.target;
        const selectionId = selectionText.getAttribute('data-selection-id');
        const actionType = selectionText.getAttribute('data-selection-action');
        
        addMessageToLog(selectionText.textContent, 'user');
        
        if (selectionId) { // 선택지 선택시
            const scenarioId = selectionText.getAttribute('data-scenario-id');
            askingFor = selectionText.getAttribute('data-asking-for');
            console.log('askingFor : ', askingFor);
            previousSelectionIds.push(scenarioId);
            requestScenarioAndSelections(selectionId);
        } else if (actionType === 'back') { // 뒤로가기 선택시
            if (previousSelectionIds.length > 0) {
                const previousScenarioId = previousSelectionIds.pop();
                requestScenarioAndSelections(previousScenarioId);
            } else {
                console.error('뒤로 가기를 할 수 없습니다.');
            }
        } else if (actionType === 'end') { // 종료 선택시
            previousSelectionIds = [];
            isChatbotInit = true;
            disablePreviousSelectables();

        } else {
            console.error('알 수 없는 선택지입니다.');
        }
    }
    
    // 기존 선택지 무력화
    function disablePreviousSelectables() {
        const selectedIds = document.querySelectorAll('[data-selection-id]');
        selectedIds.forEach(element => element.removeAttribute('data-selection-id'));
        
        const actionTypes = document.querySelectorAll('[data-selection-action]');
        actionTypes.forEach(element => element.removeAttribute('data-selection-action'));

        const selectables = document.querySelectorAll('.--selectable');
        selectables.forEach(element => element.classList.remove('--selectable'));

    }
    
    // 선택지 생성 및 출력
    function createSelectionElements(selections, scenarioId) {
        disablePreviousSelectables();

        console.log('responseType', responseType);
        const selectionDiv = document.createElement('div');
        selectionDiv.classList.add('selection');
        
        selections.forEach(selection => {
            const selectionText = document.createElement('span');
            selectionText.textContent = selection.selection;
            selectionText.classList.add('selection_box');
            // selectionText.classList.add('--selectable');
            selectionText.setAttribute('data-selection-id', selection.id);
            selectionText.setAttribute('data-scenario-id', scenarioId);
            selectionText.setAttribute('data-asking-for', selection.selection);
            selectionDiv.appendChild(selectionText);
        });
        
        if (responseType === null) {
            // '종료' 혹은 '뒤로가기' 선택지를 출력
            const selectionToGoBackText = document.createElement('span');
            selectionToGoBackText.classList.add('selection_box');
            // selectionToGoBackText.classList.add('--selectable');
            if (scenarioId == 0) {
                selectionToGoBackText.textContent = '종료';
                selectionToGoBackText.setAttribute('data-selection-action', 'end');
            } else {
                selectionToGoBackText.textContent = '뒤로가기';
                selectionToGoBackText.setAttribute('data-selection-action', 'back');
            }
            selectionDiv.appendChild(selectionToGoBackText);
        }
        
        // else {
        //     responseType = 'TEXT';
        // }
        
        chatbotLog.appendChild(selectionDiv);
        chatbotLog.scrollTop = chatbotLog.scrollHeight;
    }


    // ==================================================================================================================


    // 챗봇 입출력 관련 로직 ================================================================================================
    
    // 챗봇 내부쿼리 초기화
    function resetAskingQueries() {
        askingAbout = null;
        askingFor = null;
        requestScenarioAndSelections(0);
    }

    // 텍스트 입력으로 요청
    async function textRequest(inputValue) {

        if (inputValue.includes('영화')) {
            await sendMovieMessage(inputValue);
        } else if (inputValue.includes('날씨')) {
            await sendWeatherMessage(inputValue);
        } else if (inputValue.includes('버스')) {
            await sendBusMessage(inputValue);
        }

        resetAskingQueries();
    }

    // (봇에게 대답) 텍스트 입력으로 요청
    async function answerToBot(inputValue) {
        await redirectMessageTo(inputValue);
        resetAskingQueries();
    }

    // 챗봇 로그창에 송수신한 메시지 출력
    function addMessageToLog(message, sender) {
        const messageDiv = document.createElement('div');
        messageDiv.classList.add('message', sender);

        const messageText = document.createElement('p');
        messageText.classList.add('message_box');
        messageText.textContent = message;

        messageDiv.appendChild(messageText);
        chatbotLog.appendChild(messageDiv);
        chatbotLog.scrollTop = chatbotLog.scrollHeight;
    }

    // 챗봇 메시지 보내기. (엔터키)
    document.addEventListener('keyup', async function(event) {
        const chatbotInput = document.getElementById('chatbot_input');

        if (event.key === 'Enter' && event.target === chatbotInput) {
            const inputValue = event.target.value;
            event.target.value = '';
            addMessageToLog(inputValue, 'user');
            console.log('inputValue : ', inputValue);

            if (askingAbout && askingFor && responseType == 'TEXT') {
                answerToBot(inputValue);
            } else if (inputValue.includes('영화') || inputValue.includes('날씨') || inputValue.includes('버스')) {
                textRequest(inputValue);
            } else {
                sendMessage(inputValue);
            }
        }
    });

    // ==================================================================================================================

    // 통신 관련 로직 ======================================================================================================

    // 일반 챗봇 메세지 통신
    async function sendMessage(inputValue) {
        const message = inputValue.trim();
        if (message) {
            const response = await fetch(`/api/chatbot2/chat?message=${encodeURIComponent(message)}`);
            const text = await response.text();
            console.log(text); 
            addMessageToLog(text, 'bot');
        }
    }

    // 다른 js 파일에 있는 함수로 메세지를 보냄.
    async function redirectMessageTo(inputValue) {

        let requestMessageParts = [];
    
        if (askingAbout) requestMessageParts.push(askingAbout);
        if (askingFor) requestMessageParts.push(askingFor);
        if (inputValue) requestMessageParts.push(inputValue);
    
        const requestMessage = requestMessageParts.join(' ');
        console.log('requestMessage : ', requestMessage);
    
        const actions = {
            '영화': sendMovieMessage,
            '날씨': sendWeatherMessage,
            '버스': sendBusMessage
        };
        
        if (actions[askingAbout]) {
            await actions[askingAbout](requestMessage);
        } else {
            console.log('VALUE 값 송신 실패. 에러 발생.');
        }
    }

    // 시나리오 수신
    async function requestScenarioAndSelections(scenarioId) {
        console.log('scenarioId : ', scenarioId);
        try {
            const url = `/api/chatbot2/scenario?id=${scenarioId}`;
            const response = await fetch(url);
            if (!response.ok) {
                throw new Error('서버로부터 응답을 받지 못했습니다.');
            }
            const data = await response.json();
            
            responseType = data.scenario.scenarioResponseType;
            askingAbout = data.scenario.scenarioFor;
            console.log('responseType : ', responseType);
            console.log('askingAbout : ', askingAbout);

            if (!isChatbotInit && data.scenario.inform.includes('안녕하세요.')) {
                data.scenario.inform = removeGreeting(data.scenario.inform);
                console.log('if문 안의 인폼', data.scenario.inform);
            }

            console.log(data.scenario.inform);
            addMessageToLog(data.scenario.inform, 'bot'); 
            createSelectionElements(data.selections, scenarioId);

            if (data.scenario.scenarioResponseType == 'VALUE') {
                await redirectMessageTo(null);
                resetAskingQueries();
            }

        } catch (error) {
            console.error('오류 발생:', error);
        }
    }

    function removeGreeting(inform) {
        return inform.replace('안녕하세요. ', '');
    }
    // ==================================================================================================================

});