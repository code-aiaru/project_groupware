
initializePostView();

function initializePostView() {

       const searchParams = new URLSearchParams(location.search);
               const id = Number(searchParams.get('id'));

    console.log(id); // id 파라미터의 값을 출력합니다.

    findPost(id);

    // 게시글 상세정보 조회
    async function findPost(id) {

        // 2. API 호출
        const url = `/api/posts/${id}`;
        const response = await fetch(url);
        const post = await response.json();

        document.getElementById('postTitle').textContent = post.title;
        document.getElementById('postWriter').textContent = '작성자: ' + post.writer;


        // 3. 에디터 콘텐츠 렌더링
        document.querySelector('#editorContent').innerHTML = post.content;
    }


async function performDelete() {
    const searchParams = new URLSearchParams(location.search);
    const id = Number(searchParams.get('id'));
    const passwordInput = document.getElementById('passwordInput').value;

    try {
        // 서버로 DELETE 요청을 보냅니다.
        const response = await fetch(`/api/posts/delete/${id}?password=${passwordInput}`, {
            method: 'DELETE'
        });

        if (response.status === 200) {
            // 삭제 성공 시, 페이지 이동 또는 다른 작업 수행
            alert('게시물 삭제 성공.');
            window.location.href = `/post/list.html`; // 페이지 이동 경로 설정
        } else if (response.status === 401) {
            // 비밀번호가 일치하지 않는 경우에 대한 처리
            alert('비밀번호가 일치하지 않습니다.');
        } else {
            // 삭제 실패 시에 대한 처리
            alert('게시물 삭제 실패.');
        }
    } catch (error) {
        console.error('삭제 요청 실패: ' + error);
    }

}


    const postDeleteButton = document.getElementById('postDeleteButton');

    postDeleteButton.addEventListener('click', function(event) {

        console.log('click actiaveted');
        const button = event.target.closest('button');  
        if (button) {

            performDelete();

        }
    });

}




