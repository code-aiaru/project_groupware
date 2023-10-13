// 모달 열기 버튼 클릭 시
const detailModalBackground = document.getElementById('detailModalBackground');
const detailModal = document.getElementById('detailModal');
const closeDetailModalBtn = document.getElementById('closeDetailModal');
const detailModalContent = document.getElementById('detailContent');

document.addEventListener('click', function (event) {
    if (event.target && event.target.id === 'openDetailModalBtn') {
        const employeeNo = event.target.getAttribute('data-employee-no');
        openDetailModal(employeeNo);
    }
});

closeDetailModalBtn.onclick = function() {
  detailModal.style.display = 'none';
  detailModalBackground.style.display = 'none';
}

// 페이지 로딩 시 모달 초기 상태 설정
window.onload = function() {
  detailModal.style.display = 'none';
}

// 모달 바깥을 클릭해도 모달이 닫히지않도록
detailModalContent.addEventListener('click', function(event) {
  event.stopPropagation();
});

// 모달 내용 업데이트 함수
function updateDetailModal(data) {
    // 사원 정보를 받아서 모달 내용을 업데이트
    const detailContent = document.getElementById('detailContent');
    detailContent.innerHTML = `
        <ul>
            <li>
                <span>아이디</span>
                <span>${data.employeeId}</span>
            </li>
            <li>
                <span>이름</span>
                <span>${data.employeeName}</span>
            </li>
            <li>
                <span>부서</span>
                <span>${data.employeeDep}</span>
            </li>
            <li>
                <span>직급</span>
                <span>${data.employeePosition}</span>
            </li>
            <li>
                <span>휴대전화번호</span>
                <span>${data.employeePhone}</span>
            </li>
            <!-- 다른 정보도 필요한대로 추가 -->
        </ul>
    `;
}

// 상세보기 모달 열기 함수
function openDetailModal(employeeNo) {
    console.log("Employee No:", employeeNo); // 확인용 로그
    const url = "/employee/detail/" + employeeNo;
    fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => response.json())
    .then(data => {
        if (data.error) {
            alert('사원 정보를 가져오지 못했습니다.');
        } else {
            updateDetailModal(data);
            detailModal.style.display = 'block';
            detailModalBackground.style.display = 'block';
        }
    })
}


