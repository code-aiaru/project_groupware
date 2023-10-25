function setApproval(status) {
    document.getElementById("approvalStatus").value = status;
    const scriptBack=document.querySelector('.scriptBack');
    // AJAX 요청 실행
    $.ajax({
        type: 'POST',
        url: '/post/approval/ap',  // 서버의 URL
        data: $('#approvalAp').serialize(),  // 폼 데이터 수집
        success: function (response) {
            // 서버로부터의 응답을 처리하는 코드 (예: 메시지 표시, 페이지 리다이렉트 등)
            console.log(response);
            const redirection = document.getElementById('redirection');
            scriptBack.style.display='none';
            redirection.click();
        },
        error: function (error) {
            // 오류 발생 시 처리하는 코드
            console.error("Error:", error);
        }
    });
}

$(document).ready(function () {
    // 폼 제출 이벤트 핸들러
    $('#approvalApForm').submit(function (event) {
        event.preventDefault();  // 폼 제출 방지
        const approvalStatus = $('input[name="ApprovalStatus"]:checked').val();
        if (approvalStatus) {
            setApproval(approvalStatus);
        }
    });
});