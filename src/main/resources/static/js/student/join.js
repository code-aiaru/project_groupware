//$(document).ready(function() {
//
//    // 회원가입 폼 전송(submit) 이벤트 처리
//    $('#signupForm').submit(function(event) {
//        var phoneInput = $('#studentPhone').val();
//        var postCode = $('#studentPostCode').val();
//        var detailAddress = $('#studentDetailAddress').val();
//
//       if (!/^[0-9]{10,11}$/.test(phoneInput)) {
//            alert("휴대전화번호는 10~11자리의 숫자만 입력 가능합니다.");
//            event.preventDefault();
//        } else if (postCode === "") {
//            alert("우편번호를 입력해주세요.");
//            event.preventDefault();
//        } else if (detailAddress === "") {
//            alert("상세주소를 입력해주세요.");
//            event.preventDefault();
//        }
//    });
//});
$(document).ready(function () {

    // 회원가입 폼 전송(submit) 이벤트 처리
    $('#signupForm').submit(function (event) {
        var phoneInput = $('#studentPhone').val();
        var postCode = $('#studentPostCode').val();
        var detailAddress = $('#studentDetailAddress').val();

        if (!/^[0-9]{10,11}$/.test(phoneInput)) {
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