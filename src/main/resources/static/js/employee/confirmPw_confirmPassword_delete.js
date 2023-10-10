$(function() {
 var isPasswordValid = false; // 현재 비밀번호 유효성 여부를 나타내는 변수

 // 비밀번호 확인 버튼 클릭 이벤트 핸들러
 $('#checkCurrentPasswordBtn').click(function() {
     var currentPassword = $('#currentPassword').val();
     var employeeNo = $('#employeeNo').val();

     $.ajax({
         url: '/employee/checkAdminPassword',
         method: 'POST',
         data: { employeeNo: employeeNo, currentPassword: currentPassword },
         success: function(response) {
             if (response.valid) {
                 $('#currentPasswordMessage').css('color', 'green').text('현재 비밀번호가 일치합니다.');
                 isPasswordValid = true; // 비밀번호가 일치함을 표시
             } else {
                 $('#currentPasswordMessage').css('color', 'red').text('현재 비밀번호가 일치하지 않습니다.');
                 isPasswordValid = false; // 비밀번호가 일치하지 않음을 표시
             }
         },
         error: function(xhr, status, error) {
             $('#currentPasswordMessage').css('color', 'red').text('서버 오류가 발생했습니다. 다시 시도해주세요.');
             console.error(error);
             isPasswordValid = false; // 서버 오류 발생 시 처리
         }
     });
 });

 // "사원 삭제" 버튼 클릭 시 이벤트 핸들러
 $('#deleteBtn').click(function(e) {
     e.preventDefault(); // 폼 제출을 막음

     // 비밀번호가 유효한 경우에만 확인/취소 팝업 띄우기
     if (isPasswordValid && confirm("삭제 후 복구할 수 없습니다. 정말 삭제하시겠습니까?")) {
         // '확인'을 선택한 경우 폼 제출
         $('#deleteForm').submit();
     }
 });
});

