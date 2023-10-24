
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



     const DeleteButton = document.getElementById('DeleteButton');

  document.addEventListener('DOMContentLoaded', function() {
      const DeleteButton = document.getElementById('DeleteButton');

      if (DeleteButton) {
          DeleteButton.addEventListener('click', async function() {

    const searchParams = new URLSearchParams(location.search);
            const id = Number(searchParams.get('id'));

              try {
                  // 서버로 삭제 요청을 보냅니다.
                  const response = await fetch(`/api/posts/notice/delete/${id}/`, {
                      method: 'DELETE',
                      headers: {
                          'Content-Type': 'application/json',
                      },
                  });

                  if (response.status === 200) {
                      // 삭제 성공 시, 페이지 이동 또는 다른 작업 수행
                                            alert('게시물 삭제 성공.');
                      window.location.href = `/post/notice/list.html`; // 수정: 페이지 이동 경로 설정
                  } else {
                      // 삭제 실패 시에 대한 처리
                      alert('삭제에 실패했습니다.');
                  }
              } catch (error) {
                  console.error('삭제 요청 실패: ' + error);
              }
          });
      } else {
          console.error('DeleteButton을 찾을 수 없습니다.');
      }
  });


    }

