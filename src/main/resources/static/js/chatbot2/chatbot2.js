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
            const inputValue = event.target.value;
            event.target.value = '';
            sendMessage(inputValue);
        }
    });

    // 챗봇 로그에 띄우기.
    const chatbotLog = document.querySelector('.chatbot_log');

    function addMessageToLog(message, type) {
        const messageDiv = document.createElement('div');
        messageDiv.classList.add('message', type);
        const messageText = document.createElement('div');

        if (type === 'user') {
            messageText.classList.add('message_box', 'user-message-box');
        } else if (type === 'bot') {
            messageText.classList.add('message_box', 'bot-message-box');
            try {
                const data = JSON.parse(message);
                const weeklyBoxOfficeList = data.boxOfficeResult.weeklyBoxOfficeList;

                const table = document.createElement('table');
                table.classList.add('movie-table');

                // Table header
                const tableHeader = document.createElement('thead');
                const tableHeaderRow = document.createElement('tr');
                for (const key in weeklyBoxOfficeList[0]) {
                    if (weeklyBoxOfficeList[0].hasOwnProperty(key)) {
                        const th = document.createElement('th');
                        th.textContent = key;
                        tableHeaderRow.appendChild(th);
                    }
                }
                tableHeader.appendChild(tableHeaderRow);
                table.appendChild(tableHeader);

                // Table data
                const tableBody = document.createElement('tbody');
                for (const movie of weeklyBoxOfficeList) {
                    const row = document.createElement('tr');
                    for (const key in movie) {
                        if (movie.hasOwnProperty(key)) {
                            const cell = document.createElement('td');
                            cell.textContent = movie[key];
                            row.appendChild(cell);
                        }
                    }
                    tableBody.appendChild(row);
                }
                table.appendChild(tableBody);

                messageText.appendChild(table);
            } catch (error) {
                messageText.textContent = message;
            }
        } else {
            messageText.textContent = message;
        }

        messageDiv.appendChild(messageText);
        chatbotLog.appendChild(messageDiv);
        chatbotLog.scrollTop = chatbotLog.scrollHeight;
    }

    async function sendMessage(inputValue) {
        const message = inputValue.trim();
        if (message) {
            addMessageToLog(message, 'user');
            const response = await fetch(`/api/chatbot2/chat?message=${encodeURIComponent(message)}`);
            const text = await response.text();
            console.log(text);
            addMessageToLog(text, 'bot');
        }
    }
});