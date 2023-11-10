$(document).ready(function () {

    // 회원가입 폼 전송(submit) 이벤트 처리
    $('#signupForm').submit(function (event) {
        event.preventDefault();

        var formData = $(this).serialize();

            $.ajax({
              type: 'POST',
              url: '/post/employee/join',
              data: formData,
              success: function (response) {
                console.log(response);

                const redirection = document.getElementById('redirection');
                redirection.click();
              },
              error: function (error) {
                console.error("Error:", error);
              }
            });

        var id = $('#employeeId').val();
        var password = $('#employeePassword').val();
        var confirmPassword = $('#confirmPassword').val();
        var phoneInput = $('#employeePhone').val();
        var postCode = $('#employeePostCode').val();
        var detailAddress = $('#employeeDetailAddress').val();
        var formData = $(this).serialize();

        if (id === "") {
            alert("아이디를 입력해주세요.")
            event.preventDefault();
        } else if (password !== confirmPassword) {
            alert("비밀번호와 비밀번호 확인 값이 일치하지 않습니다.");
            event.preventDefault();
        } else if (!/^[0-9]{10,11}$/.test(phoneInput)) {
            alert("휴대전화번호는 10~11자리의 숫자만 입력 가능합니다.");
            event.preventDefault();
        } else if (postCode === "") {
            alert("우편번호를 입력해주세요.");
            event.preventDefault();
        } else if (detailAddress === "") {
            alert("상세주소를 입력해주세요.");
            event.preventDefault();
        }
    });
});
