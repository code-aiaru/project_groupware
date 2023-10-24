$(document).ready(function() {
     $("#tempPasswordForm").submit(function(event) {
         event.preventDefault();

         var email = $("#employeeEmail").val();
         var phone = $("#employeePhone").val();

         // 서버로 이메일과 휴대전화번호 일치 여부를 확인 요청
         $.ajax({
             type: "GET",
             url: "/api/check-emailPhoneMatching",
             data: {
                 employeeEmail: email,
                 employeePhone: phone
             },
             success: function(response) {
                 if (response.matching) {
                     // 이메일과 휴대전화번호가 모두 일치할 경우 임시비밀번호 발급 요청
                     $.ajax({
                         type: "POST",
                         url: "/api/send-mail/password",
                         data: JSON.stringify({ email: email, phone: phone }),
                         contentType: "application/json; charset=utf-8",
                         dataType: "json",
                         success: function(response) {
//                             $("#tempEmail").text("입력한 이메일: " + email);
                             $("#tempPasswordMessage").text(email + "로 임시비밀번호를 전송했습니다.").show();
                             openModal();
                         },
                         error: function(xhr, textStatus, errorThrown) {
                             $("#tempPasswordMessage").text("이메일 임시비밀번호 전송에 실패했습니다.").show();
                         }
                     });
                 } else {
                     $("#tempPasswordMessage").text("이메일과 휴대전화번호가 일치하지 않습니다.").show();
                     openModal();
                 }
             },
             error: function(xhr, textStatus, errorThrown) {
                 $("#tempPasswordMessage").text("일치 여부 확인에 실패했습니다.").show();
             }
         });
     });
});

// 모달 열기
function openModal() {
    var modal = document.getElementById("tempModal");
    modal.style.display = "block";
}

// 모달 닫기
function closeModal() {
    var modal = document.getElementById("tempModal");
    modal.style.display = "none";
}

// 창 닫기 버튼 이벤트 처리
var closeModalButton = document.getElementById("closeModal");
    if (closeModalButton) {
      closeModalButton.onclick = closeModal;
}

