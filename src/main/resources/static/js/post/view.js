
initializePostView();

function initializePostView() {
async function findPost(id) {
    const url = `/api/posts/${id}`;
    const response = await fetch(url);
    const post = await response.json();

    document.getElementById('postTitle').textContent = post.title;
    document.getElementById('postWriter').textContent = '작성자: ' + post.writer;
    document.getElementById('postWriter').textContent = post.pw;

    // 에디터 콘텐츠 렌더링
    document.querySelector('#editorContent').innerHTML = post.content;

    // 삭제 버튼 활성화 여부를 결정
    if (post.pw) {
        // 게시물에 비밀번호가 설정되어 있는 경우
        document.getElementById('passwordContainer').style.display = 'block';
    } else {
        // 게시물에 비밀번호가 설정되어 있지 않은 경우
        document.getElementById('deleteButton').addEventListener('click', deletePost);
    }
}

// 게시물 삭제
async function deletePost() {
    // 비밀번호 입력 확인
    const passwordInput = document.getElementById('passwordInput').value;

    const verifyPasswordResponse = await fetch(`/api/posts/${id}/postDelete`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ password: passwordInput }),
    });

    if (verifyPasswordResponse.status === 200) {
        // 비밀번호 확인에 성공하면 게시물 삭제를 진행합니다.
        const deleteResponse = await fetch(deleteUrl, {
            method: 'DELETE',
        });

        if (deleteResponse.status === 200) {
            alert('게시물이 삭제되었습니다.');
            // 페이지 리로드 등의 작업을 추가하세요.
        } else {
            alert('게시물 삭제에 실패했습니다.');
        }
    } else {
        alert('비밀번호가 올바르지 않습니다.');
    }
}

// 페이지 로드 시 게시물 조회를 시작
window.addEventListener('load', () => {
    // URL에서 게시물 ID 파라미터를 추출
    const urlRequest = new URL(window.globalClickedURL);
    const id = urlRequest.searchParams.get('id');

    // 게시물 조회 시작
    findPost(id);
});


}

