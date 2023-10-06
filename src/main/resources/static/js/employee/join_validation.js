//// 아이디 입력란 관련 요소들 가져오기
//const employeeIdInput = document.querySelector('#employeeId');
//const employeeIdErrorMessage = document.querySelector('#employeeIdErrorMessage');
//
//// 아이디 입력란 유효성 검사 함수
//function validateEmployeeId() {
//  const employeeId = employeeIdInput.value;
//  // 정규 표현식을 사용하여 한글과 영어 이외의 문자를 찾음
//  const invalidCharacters = employeeId.match(/[^a-zA-Z0-9]/g);
//
//  if (invalidCharacters) {
//    // 한글과 영어 이외의 문자가 입력되면 오류 메시지 출력
//    employeeIdErrorMessage.textContent = "영어와 숫자만 입력 가능합니다";
//  } else {
//    // 한글과 영어만 입력된 경우 오류 메시지 제거
//    employeeIdErrorMessage.textContent = "";
//  }
//}
//// 값이 변경될 때마다 validateEmployeeName 함수 호출
//employeeIdInput.addEventListener('input', validateEmployeeId);

$(document).ready(function () {
    // 아이디 입력 필드
    var $employeeIdField = $('#employeeId');

    // 아이디 입력 필드의 keyup 이벤트 리스너
    $employeeIdField.on('keyup', function () {
        var employeeId = $employeeIdField.val();

        // 입력 필드에 3글자 이상의 영어와 숫자만 입력 가능하도록 검사
        var idPattern = /^[a-zA-Z0-9]+$/;
        if (employeeId.length < 3) {
            $('#employeeIdErrorMessage').text('3글자 이상 입력해주세요.').show();
            $('#idAvailable').hide();
            $('#idNotAvailable').hide();
        } else if (!idPattern.test(employeeId)) {
            $('#employeeIdErrorMessage').text('영어와 숫자만 입력 가능합니다.').show();
            $('#idAvailable').hide();
            $('#idNotAvailable').hide();
        } else {
            $('#employeeIdErrorMessage').hide();
            $('#idAvailable').hide();
            $('#idNotAvailable').hide();
        }
    });
});


// 이름 입력란 관련 요소들 가져오기
const employeeNameInput = document.querySelector('#employeeName');
const employeeNameErrorMessage = document.querySelector('#employeeNameErrorMessage');

// 이름 입력란 유효성 검사 함수
function validateEmployeeName() {
  const employeeName = employeeNameInput.value;
  // 정규 표현식을 사용하여 한글과 영어 이외의 문자를 찾음
  const invalidCharacters = employeeName.match(/[^가-힣a-zA-Z]/g);

  if (invalidCharacters) {
    // 한글과 영어 이외의 문자가 입력되면 오류 메시지 출력
    employeeNameErrorMessage.textContent = "한글 또는 영어만 입력 가능합니다";
  } else {
    // 한글과 영어만 입력된 경우 오류 메시지 제거
    employeeNameErrorMessage.textContent = "";
  }
}
// 값이 변경될 때마다 validateEmployeeName 함수 호출
employeeNameInput.addEventListener('input', validateEmployeeName);



// 휴대전화 입력란 관련 요소들 가져오기
const employeePhoneInput = document.querySelector('#employeePhone');
const employeePhoneErrorMessage = document.querySelector('#employeePhoneErrorMessage');

// 휴대전화 입력란 유효성 검사 함수
function validateEmployeePhone() {
  const employeePhone = employeePhoneInput.value;
  // 정규 표현식을 사용하여 숫자 이외의 문자를 찾음
  const invalidCharacters = employeePhone.match(/[^0-9]/g);

  if (invalidCharacters) {
    // 숫자 이외의 문자가 입력되면 오류 메시지 출력
    employeePhoneErrorMessage.textContent = "숫자만 입력 가능합니다";
  } else {
    // 숫자만 입력된 경우 오류 메시지 제거
    employeePhoneErrorMessage.textContent = "";
  }
}
// 값이 변경될 때마다 validateEmployeePhone 함수 호출
employeePhoneInput.addEventListener('input', validateEmployeePhone);

