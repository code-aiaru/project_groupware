

initializeNotice();

function initializeNotice() {

    const noticeLoadSection = document.getElementById('notice-render-area');

    getDataFromServer();


    function getDataFromServer() {
        fetch(`/api/dashboard/notice`)
        .then(response => {
            if (!response.ok) {
                throw new Error('응답이 올바르지 않습니다.');
            }
            return response.json();
        })
        .then(data => {
            const notices = data.lastFiveArticlesFromNotice.content; // 제목 뿐만 아니라, 전체 공지 데이터를 가져옵니다.
            displayNoticeTitles(notices);
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error.message);
        });
    }

    function displayNoticeTitles(notices) {
        let noticeHtml = '<ul>';
        for (let notice of notices) {
            // let cleanContent = stripHTML(notice.content);
            // noticeHtml += `<li>[${notice.writer}] ${notice.title} - ${cleanContent}</li>`;
            noticeHtml += `<li><a href="/notice/view.html?id=${notice.id}" data-ajax>[${notice.writer}] ${notice.title}</a></li>`;
            // console.log(notice.id);
        }
        noticeHtml += '</ul>';
    
        noticeLoadSection.innerHTML = noticeHtml;
    }

    // function stripHTML(html) {
    //     const doc = new DOMParser().parseFromString(html, 'text/html');
    //     return doc.body.textContent || "";
    // }
    


}

