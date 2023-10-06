    $('form').submit(function(e) {
        e.preventDefault();

        $.ajax({
            type: 'POST',
            url: $(this).attr('action'),
            data: $(this).serialize(),
            success: function(response) {
                $('#foundId').text('찾은 아이디: ' + response);
                $('#foundId').show();
            },
            error: function(xhr, textStatus, errorThrown) {
                if (xhr.status === 404 && xhr.responseText === 'IdNotFound') {
                    $('#foundId').text('일치하는 아이디를 찾을 수 없습니다.');
                    $('#foundId').show();
                }
            }
        });
    });
//});