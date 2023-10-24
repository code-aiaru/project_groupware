// form 제출 시 리디렉트 수행
$('form').submit(function (event) {
event.preventDefault(); // 기본 제출 방지

var formData = $(this).serialize();

$.ajax({
  type: 'POST',
  url: '/post/employee/update',
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
});