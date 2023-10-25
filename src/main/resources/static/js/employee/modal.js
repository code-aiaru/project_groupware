// 모달 열기 버튼 클릭 시
const openModalBtn = document.getElementById('openModalBtn');
const modalBackground = document.getElementById('modalBackground');
const modal = document.getElementById('myModal');
const closeModalBtn = document.getElementById('closeModal');
const modalContent = document.querySelector('.modal-content');

openModalBtn.onclick = function() {
    modal.style.display = 'block';
    modalBackground.style.display = 'block';
}

closeModalBtn.onclick = function() {
    modal.style.display = 'none';
    modalBackground.style.display = 'none';
}

// 페이지 로딩 시 모달 초기 상태 설정
window.onload = function() {
modal.style.display = 'none';
}

// 모달 내부를 클릭해도 모달이 닫히지 않도록
//modal.addEventListener('click', function() {
//    modal.style.display = 'block'; // 모달 내부를 클릭한 경우 모달 상태 유지
//});

// 모달 바깥을 클릭해도 모달이 닫히지 않도록 이벤트 핸들러 추가
modalContent.addEventListener('click', function(event) {
    event.stopPropagation(); // 모달 내부를 클릭해도 모달이 닫히지 않도록 이벤트 전파 중지
});

// 여기에 폼 요소 입력 및 서버로 데이터 전송 관련 코드를 추가해야 합니다.