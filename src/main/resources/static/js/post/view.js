
initializePostView();

function initializePostView() {

    console.log('index.js 전역변수 참조.', window.globalClickedURL);  // index.js에서 선언된 전역 변수 참조

    const urlRequest = new URL(window.globalClickedURL);
    const id = urlRequest.searchParams.get('id');

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



        const editButton = document.getElementById('editButton');

        editButton.addEventListener('click', () => {
            const clientPasswordInput = document.getElementById('passwordInput');
            const password = clientPasswordInput.value;
            const urlRequest = new URL(window.globalClickedURL);
            const id = urlRequest.searchParams.get('id');

            // 서버로 비밀번호 검증 요청을 보냅니다.
            fetch(`/api/posts/${id}/verifyPassword?password=${enteredPassword}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ password }),
            })
            .then(response => {
                if (response.status === 200) {
                    // 비밀번호가 일치하는 경우, 페이지 이동 또는 다른 작업 수행
                    window.location.href = `/post/update.html`; // 수정: 페이지 이동 경로 설정
                } else {
                    // 비밀번호가 일치하지 않는 경우에 대한 처리
                    alert('비밀번호가 일치하지 않습니다. 수정 권한이 없습니다.');
                }
            })
            .catch(error => {
                console.error('비밀번호 확인 요청 실패: ' + error);
            });
        });

        // verifyPassword 함수 정의 - 필요에 따라 추가
        function verifyPassword() {
            // 이 함수를 사용하여 비밀번호 검증 로직을 정의할 수 있습니다.
        }




}

