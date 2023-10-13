  const detailLinks = document.querySelectorAll('.detail-link');  // "상세보기" 링크를 모두 선택합니다.
  const detailModalBackground = document.getElementById('detailModalBackground');
  const detailModal = document.getElementById('detailModal');
  const closeDetailModalBtn = document.getElementById('closeDetailModal');
  const detailContent = document.getElementById('detailContent');

  // "상세보기" 링크를 클릭했을 때 모달을 열고 상세 정보를 가져와 표시합니다.
  function openDetailModal(event) {
    event.preventDefault();  // 기본 링크 동작을 중지합니다.

    const employeeNo = this.getAttribute('data-employee-no');
    // 이제 'employeeNo'를 사용하여 해당 사원의 상세 정보를 가져오는 요청을 서버에 보내고, 그 응답을 detailContent에 업데이트합니다.
    // 여기에서는 서버 요청 및 응답 부분을 처리하지 않았으므로, 해당 부분을 서버와 연결하여 구현해야 합니다.
    
    // detailContent.innerHTML = '여기에 서버에서 받은 상세 정보를 표시하는 HTML을 넣으세요';
    
    detailModal.style.display = 'block';
    detailModalBackground.style.display = 'block';
  }

  closeDetailModalBtn.onclick = function() {
    detailModal.style.display = 'none';
    detailModalBackground.style.display = 'none';
  }

  // "상세보기" 링크에 클릭 이벤트 리스너를 추가합니다.
  detailLinks.forEach(link => {
    link.addEventListener('click', openDetailModal);
  });
});