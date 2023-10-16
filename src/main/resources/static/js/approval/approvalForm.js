$(document).ready(function () {
    $('form').submit(function (event) {

        const paymentCheckboxes = $('.paymentCheckbox:checked');

        if (paymentCheckboxes.length === 0) {
            event.preventDefault();
            alert("결재자가 없습니다.");
        } else if(paymentCheckboxes.length === 1){
            event.preventDefault();  // 폼 제출 방지

            var formData = $(this).serialize();  // 폼 데이터 수집
    
            // AJAX 요청 실행
            $.ajax({
                type: 'POST',
                url: '/post/approval/write',  // 서버의 URL
                data: formData,
                success: function (response) {
                    // 서버로부터의 응답을 처리하는 코드 (예: 메시지 표시, 페이지 리다이렉트 등)
                    console.log(response);
                    const redirection = document.getElementById('redirection');
                    redirection.click();
                },
                error: function (error) {
                    // 오류 발생 시 처리하는 코드
                    console.error("Error:", error);
                }
            });
        }else {
            event.preventDefault();
            alert("결재자는 한명만 설정할수 있습니다.");
        }
    });
});