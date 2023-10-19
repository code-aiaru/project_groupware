$(document).ready(function () {
    // 중복 확인 결과
    let isEmailAvailable = false;
    let isPhoneAvailable = false;

    // 이메일 중복 확인 버튼 클릭 시 이벤트 처리
    $('#emailCheckButton').click(function () {

        let studentEmail = $('#studentEmail').val();
        if (studentEmail.trim() === '@') {
            alert('이메일을 입력해주세요.');
            return;
        }

        $.ajax({
            url: '/api/employee/employeeEmail/check',
            type: 'GET',
            contentType: 'application/json',
            data: {
                employeeEmail: $('#studentEmail').val()
            },
            success: function (result) {
                $('#emailNotAvailable').hide();
                $('#emailAvailable').show().text(result).append($('<br />'));
                isEmailAvailable = true;
//                alert('사용 가능한 이메일입니다.');

            },
            error: function (error) {
                $('#emailAvailable').hide();
                $('#emailNotAvailable').show().text(error.responseJSON['message']).append($('<br />'));
                isEmailAvailable = false;
//                alert('이미 사용 중인 이메일입니다.');
            }
        });
    });

    // 휴대전화번호 중복 확인 버튼 클릭 시 이벤트 처리
    $('#phoneCheckButton').click(function () {

        let studentPhone = $('#studentPhone').val();
        if (studentPhone.trim() === '') {
            alert('휴대전화번호를 입력해주세요.');
            return;
        }

        $.ajax({
            url: '/api/employee/employeePhone/check',
            type: 'GET',
            contentType: 'application/json',
            data: {
                employeePhone: $('#studentPhone').val()
            },
            success: function (result) {
                $('#phoneNotAvailable').hide();
                $('#phoneAvailable').show().text(result).append($('<br />'));
                isPhoneAvailable = true;
//                alert('사용 가능한 닉네임입니다.');

            },
            error: function (error) {
                $('#phoneAvailable').hide();
                $('#phoneNotAvailable').show().text(error.responseJSON['message']).append($('<br />'));
                isPhoneAvailable = false;
//                alert('이미 사용 중인 닉네임입니다.');
            }
        });
    });

    // 회원가입 버튼 클릭 시 이벤트 처리
    $('form').submit(function (event) {
        if (!isEmailAvailable && !isPhoneAvailable) {
            event.preventDefault(); // 이벤트 중단
            alert('이메일과 휴대전화번호 중복 확인을 해주세요.');

        } else if (!isPhoneAvailable) {
            event.preventDefault(); // 이벤트 중단
            alert('휴대전화번호 중복 확인을 해주세요.');
        }
    });
});