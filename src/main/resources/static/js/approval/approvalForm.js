$(document).ready(function () {
    $('form').submit(function (event) {

        const paymentCheckboxes = $('.paymentCheckbox:checked');

        if (paymentCheckboxes.length === 0) {
            event.preventDefault();
            alert("결재자가 없습니다");
        }
    });
});