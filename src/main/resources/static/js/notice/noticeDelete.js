function deletePost() {
  const searchParams = new URLSearchParams(location.search);
  const id = Number(searchParams.get('id'));

  try {
    // 서버로 삭제 요청을 보냅니다.
    fetch(`/api/posts/notice/delete/${id}/`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
      },
    })
      .then((response) => {
        if (response.status === 200) {
          // 삭제 성공 시, 페이지 이동 또는 다른 작업 수행
          alert('게시물 삭제 성공.');
          window.location.href = `/post/notice/list.html`; // 수정: 페이지 이동 경로 설정
        } else {
          // 삭제 실패 시에 대한 처리
          alert('삭제에 실패했습니다.');
        }
      })
      .catch((error) => {
        console.error('삭제 요청 실패: ' + error);
      });
  } catch (error) {
    console.error('삭제 요청 실패: ' + error);
  }
}

document.addEventListener('DOMContentLoaded', function () {
  const DeleteButton = document.getElementById('DeleteButton');

  if (DeleteButton) {
    DeleteButton.addEventListener('click', deletePost);
  } else {
    console.error('DeleteButton을 찾을 수 없습니다.');
  }
});