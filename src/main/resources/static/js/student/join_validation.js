// 이름 입력란 관련 요소들 가져오기
const studentNameInput = document.querySelector('#studentName');
const studentNameErrorMessage = document.querySelector('#studentNameErrorMessage');

// 이름 입력란 유효성 검사 함수
function validateStudentName() {
  const studentName = studentNameInput.value;
  // 정규 표현식을 사용하여 한글과 영어 이외의 문자를 찾음
  const invalidCharacters = studentName.match(/[^가-힣a-zA-Z]/g);

  if (invalidCharacters) {
    // 한글과 영어 이외의 문자가 입력되면 오류 메시지 출력
    studentNameErrorMessage.textContent = "한글 또는 영어만 입력 가능합니다";
  } else {
    // 한글과 영어만 입력된 경우 오류 메시지 제거
    studentNameErrorMessage.textContent = "";
  }
}
// 값이 변경될 때마다 validateEmployeeName 함수 호출
studentNameInput.addEventListener('input', validateStudentName);


// 휴대전화 입력란 관련 요소들 가져오기
const studentPhoneInput = document.querySelector('#studentPhone');
const studentPhoneErrorMessage = document.querySelector('#studentPhoneErrorMessage');

// 휴대전화 입력란 유효성 검사 함수
function validateStudentPhone() {
  const studentPhone = studentPhoneInput.value;
  // 정규 표현식을 사용하여 숫자 이외의 문자를 찾음
  const invalidCharacters = studentPhone.match(/[^0-9]/g);

  if (invalidCharacters) {
    // 숫자 이외의 문자가 입력되면 오류 메시지 출력
    studentPhoneErrorMessage.textContent = "숫자만 입력 가능합니다";
  } else {
    // 숫자만 입력된 경우 오류 메시지 제거
    studentPhoneErrorMessage.textContent = "";
  }
}
// 값이 변경될 때마다 validateEmployeePhone 함수 호출
studentPhoneInput.addEventListener('input', validateStudentPhone);

