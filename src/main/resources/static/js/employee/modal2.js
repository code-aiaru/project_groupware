// 모달 열기 버튼 클릭 시
const detailModalBackground = document.getElementById('detailModalBackground');
const detailModal = document.getElementById('detailModal');
const closeDetailModalBtn = document.getElementById('closeDetailModal');
const detailModalContent = document.getElementById('detailContent');

//document.addEventListener('click', function (event) {
//    if (event.target && event.target.id === 'openDetailModalBtn') {
//        const employeeNo = event.target.getAttribute('data-employee-no');
//        openDetailModal(employeeNo);
//    }
//});

//closeDetailModalBtn.onclick = function() {
//  detailModal.style.display = 'none';
//  detailModalBackground.style.display = 'none';
//}

// 페이지 로딩 시 모달 초기 상태 설정
window.onload = function() {
  detailModal.style.display = 'none';
}

// 모달 바깥을 클릭해도 모달이 닫히지않도록
detailModalContent.addEventListener('click', function(event) {
  event.stopPropagation();
});

// 모달 내용 업데이트 함수
//function updateDetailModal(data) {
//    // 사원 정보를 받아서 모달 내용을 업데이트
//    const detailContent = document.getElementById('detailContent');
//    detailContent.innerHTML = `
//        <ul>
//            <li>
//                <span>아이디</span>
//                <span>${data.employeeId}</span>
//            </li>
//            <li>
//                <span>이름</span>
//                <span>${data.employeeName}</span>
//            </li>
//            <li>
//                <span>부서</span>
//                <span>${data.employeeDep}</span>
//            </li>
//            <li>
//                <span>직급</span>
//                <span>${data.employeePosition}</span>
//            </li>
//            <li>
//                <span>휴대전화번호</span>
//                <span>${data.employeePhone}</span>
//            </li>
//            <!-- 다른 정보도 필요한대로 추가 -->
//        </ul>
//    `;
//}

// 상세보기 모달 열기 함수
$(document).ready(function() {
    // 상세보기 버튼 클릭 시 모달 열기
    $("#openDetailModal").click(function() {
        // 선택된 사원의 employeeNo 값을 가져옵니다.
        var employeeNo = $(this).data("employee-no");

        // 서버로 요청을 보내어 사원의 상세 정보를 얻어옵니다.
        $.ajax({
            url: "/employee/detail/" + employeeNo, // 서버에서 정보를 얻어올 엔드포인트 URL
            method: "GET",
            success: function(response) {
                // 서버에서 반환한 정보를 모달 내의 적절한 위치에 표시합니다.
//                $("#employeeName").text(response.employeeName);
//                $("#employeeId").text(response.employeeId);
//                $("#employeeGender").text(response.employeeGender);
                // 나머지 정보도 동일한 방식으로 표시합니다.

                // 모달 열기
                $("#detailModalBackground").show();
                $("#detailModal").show();
            },
            error: function() {
                console.log(employeeNo);
                alert("사원 정보를 불러오는 중 오류가 발생했습니다.");
            }
        });
    });

    // 상세보기 모달 닫기
    $("#closeDetailModal").click(function() {
        $("#detailModalBackground").hide();
        $("#detailModal").hide();
    });
});



