$('form').submit(function(e) {
    e.preventDefault();

    $.ajax({
        type: 'POST',
        url: $(this).attr('action'),
        data: $(this).serialize(),
        success: function(response) {
            if (response === 'IdNotFound') {
                showNotFoundId("일치하는 아이디를 찾을 수 없습니다.");
            } else {
                showFoundId(response);
            }
        },
        error: function(xhr, textStatus, errorThrown) {
            if (xhr.status === 404 && xhr.responseText === 'IdNotFound') {
                showNotFoundId("일치하는 아이디를 찾을 수 없습니다");
            }
        }
    });
});


// 모달 열기
function openModal() {
  var modal = document.getElementById("findIdModal");
  modal.style.display = "block";
}

// 모달 닫기
function closeModal() {
  var modal = document.getElementById("findIdModal");
  modal.style.display = "none";
}

// 창 닫기 버튼 이벤트 처리
var closeModalButton = document.getElementById("closeModal");
    if (closeModalButton) {
      closeModalButton.onclick = closeModal;
}

// 찾은 아이디 표시
function showFoundId(id) {
  var foundIdElement = document.getElementById("foundId");
  foundIdElement.textContent = "찾은 아이디: " + id;

  // "복사" 버튼 표시
  var copyButton = document.getElementById("copyButton");
  copyButton.style.display = "block";

  openModal(); // 모달 열기
}

// 틀렸을 경우
function showNotFoundId(id) {
  var foundIdElement = document.getElementById("foundId");
  foundIdElement.textContent = id;

  // 아이디를 찾지 못했을 때 "복사" 버튼 숨기기
  var copyButton = document.getElementById("copyButton");
  copyButton.style.display = "none";

  openModal(); // 모달 열기
}


// 클립보드에 텍스트 복사 함수
function copyToClipboard(text) {
    const tempElement = document.createElement("textarea");
    tempElement.value = text;
    document.body.appendChild(tempElement);
    tempElement.select();
    document.execCommand("copy");
    document.body.removeChild(tempElement);
}

// "복사" 버튼 클릭 이벤트 처리
var copyButton = document.getElementById("copyButton");
if (copyButton) {
    copyButton.onclick = function () {
        var foundIdElement = document.getElementById("foundId");
        var foundId = foundIdElement.textContent;
        foundId = foundId.replace("찾은 아이디: ", ""); // 아이디 텍스트에서 "찾은 아이디: " 부분 제거
        copyToClipboard(foundId);
        alert("아이디가 복사되었습니다: " + foundId);
    };
}