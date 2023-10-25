

initializeNoticeList();

function initializeNoticeList() {

    const noticeLoadSection = document.getElementById('notice-list');

    getDataFromServer();


    function getDataFromServer() {
        fetch(`/api/posts/notice`)
        .then(response => {
            if (!response.ok) {
                throw new Error('응답이 올바르지 않습니다.');
            }
            return response.json();
        })
        .then(data => {
            const notices = data.listFromNotice.content; // 제목 뿐만 아니라, 전체 공지 데이터를 가져옵니다.
            displayNoticeTitles(notices);
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error.message);
        });
    }

   function displayNoticeTitles(notices) {
       let noticeHtml = '';
       for (let index = 0; index < notices.length; index++) {
           let notice = notices[index];
           // let cleanContent = stripHTML(notice.content);
           // noticeHtml += `<li>[${notice.writer}] ${notice.title} - ${cleanContent}</li>`;
           noticeHtml += `
               <tr>
                   <td>${index + 1}</td>
                   <td class="left"><a href="/notice/view.html?id=${notice.id}" data-ajax> ${notice.title}</a></td>
               </tr>
           `;
       }

       noticeLoadSection.innerHTML = noticeHtml;
   }


}

