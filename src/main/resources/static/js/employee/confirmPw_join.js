$(function() {
    $('#employeePassword, #confirmPassword').on('keyup', function() {
        $('#checkMessage').html('');
        var employeePassword = $('#employeePassword').val();
        var confirmPassword = $('#confirmPassword').val();

        if (employeePassword !== '' && confirmPassword !== '') {
            if (employeePassword === confirmPassword) {
                $('#checkMessage').html('비밀번호 일치함<br><br>');
                $('#checkMessage').css('color', 'green');
            } else {
                $('#checkMessage').html('비밀번호 일치하지 않음<br><br>');
                $('#checkMessage').css('color', 'red');
            }
        }
    });
});