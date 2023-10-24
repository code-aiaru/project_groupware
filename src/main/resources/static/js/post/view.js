
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
    
        // 3. 에디터 콘텐츠 렌더링
        document.querySelector('#editorContent').innerHTML = post.content;
    }

}    

