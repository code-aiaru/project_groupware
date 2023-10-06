$(document).ready(function () {
    // 중복 확인 결과
    let isIdAvailable = false;
    let isEmailAvailable = false;
    let isPhoneAvailable = false;

    // 아이디 중복 확인 버튼 클릭 시 이벤트 처리
        $('#idCheckButton').click(function () {
            $.ajax({
                url: '/api/employee/employeeId/check',
                type: 'GET',
                contentType: 'application/json',
                data: {
                    employeeId: $('#employeeId').val()
                },
                success: function (result) {
                    $('#idNotAvailable').hide();
                    $('#idAvailable').show().text(result).append($('<br />'));
                    isIdAvailable = true;
    //                alert('사용 가능한 아이디입니다.');

                },
                error: function (error) {
                    $('#idAvailable').hide();
                    $('#idNotAvailable').show().text(error.responseJSON['message']).append($('<br />'));
                    isPhoneAvailable = false;
    //                alert('이미 사용 중인 아이디입니다.');
                }
            });
        });


    // 이메일 중복 확인 버튼 클릭 시 이벤트 처리
    $('#emailCheckButton').click(function () {

    // 현재 입력된 이메일 값 가져오기
        let employeeEmail = $('#employeeEmail').val();

        $.ajax({
            url: '/api/employee/employeeEmail/check',
            type: 'GET',
            contentType: 'application/json',
            data: {
                employeeEmail: $('#employeeEmail').val()
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
        $.ajax({
            url: '/api/employee/employeePhone/check',
            type: 'GET',
            contentType: 'application/json',
            data: {
                employeePhone: $('#employeePhone').val()
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