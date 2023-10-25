
initializeNoticeView();

function initializeNoticeView() {

    console.log('index.js 전역변수 참조.', window.globalClickedURL);  // index.js에서 선언된 전역 변수 참조


    const searchParams = new URLSearchParams(location.search);
            const id = Number(searchParams.get('id'));
    console.log(id); // id 파라미터의 값을 출력합니다.

    findNotice(id);

    // 게시글 상세정보 조회
    async function findNotice(id) {
        // 2. API 호출
        const url = `/api/posts/notice/${id}`;
        const response = await fetch(url);
        const notice = await response.json();

        document.getElementById('noticeTitle').textContent = notice.title;
        document.getElementById('noticeWriter').textContent = '작성자: ' + notice.writer;

        // 3. 에디터 콘텐츠 렌더링
        document.querySelector('#editorContent').innerHTML = notice.content;
    }



    }

