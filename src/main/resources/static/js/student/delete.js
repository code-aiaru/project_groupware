$(document).ready(function () {
    $("#deleteButton").on("click", function () {
        var studentId = $(this).data("student-id");
        if (confirm("정말 삭제하시겠습니까? 삭제 이후에는 복구할 수 없습니다.")) {
            // 확인을 클릭한 경우
            deleteStudent(studentId);
        }
    });

    function deleteStudent(studentId) {
        if (studentId !== null) {
            $.ajax({
                type: "GET",
                url: "/student/delete/" + studentId,
                success: function (data) {
//                    alert("수강생이 삭제되었습니다.");
                    const redirection = document.getElementById('redirection');
                    redirection.click();
                },
                error: function () {
                    alert("수강생 삭제에 실패했습니다.");
                }
            });
        }
    }
});
