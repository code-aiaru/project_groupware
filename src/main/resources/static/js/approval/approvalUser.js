document.addEventListener("DOMContentLoaded", function() {
    const payuserBlock = document.querySelector('#payuserData');
    // payuserBtn 요소를 모두 선택
    const payuserBtns = document.querySelectorAll('.payuserBtn');
    payuserBtns.forEach(function(payuserBtn) {
        payuserBtn.addEventListener('click', function (event) {
            payuserBtn.style.display='none';
            const memberId = payuserBtn.getAttribute("value");
            const memberName = payuserBtn.textContent;

            const divContainer = document.createElement("div");
            divContainer.classList.add("parent");

            // 새로운 input 필드 생성 및 설정
            const nameInput = document.createElement("input");
            nameInput.type = "hidden";
            nameInput.name = "dataArray";
            nameInput.value = memberId;
            nameInput.readOnly = true;

            const textSpan = document.createElement("span");
            textSpan.textContent = memberName;

            // 새로운 라디오 버튼 생성 및 설정
            const appendLabelPayment = document.createElement("label");
            appendLabelPayment.textContent = "결제";
            appendLabelPayment.classList.add("paymentLabel");
            const appendRadioPayment = document.createElement("input");
            appendRadioPayment.type = "checkbox";
            appendRadioPayment.name = "dataArray";
            appendRadioPayment.value = "0";
            appendRadioPayment.classList.add("paymentCheckbox");

            const appendLabelReference = document.createElement("label");
            appendLabelReference.textContent = "참조";
            appendLabelReference.classList.add("referenceLabel");
            const appendRadioReference = document.createElement("input");
            appendRadioReference.type = "checkbox";
            appendRadioReference.name = "dataArray";
            appendRadioReference.value = "1";
            appendRadioReference.checked = true;
            appendRadioReference.classList.add("referenceCheckbox");

            const xButtonPayment = document.createElement('button')
            xButtonPayment.type = "button";
            xButtonPayment.value = memberId;
            xButtonPayment.textContent = "x";
            xButtonPayment.classList.add("xButton");
            const lineBreak = document.createElement("br");


            // 생성한 요소들을 <div>에 추가
                    divContainer.appendChild(textSpan);
                    divContainer.appendChild(nameInput);
                    divContainer.appendChild(appendLabelPayment);
                    divContainer.appendChild(appendRadioPayment);
                    divContainer.appendChild(appendLabelReference);
                    divContainer.appendChild(appendRadioReference);
                    divContainer.appendChild(xButtonPayment);


                    // <div>를 payuserData 블록에 추가
                    payuserBlock.appendChild(divContainer);

            const paymentCheckbox = document.querySelector('.paymentCheckbox')
            const referenceCheckbox = document.querySelector('.referenceCheckbox')

            // "paymentCheckbox" 클래스를 가진 모든 체크박스 요소에 대한 이벤트 핸들러
            const paymentCheckboxes = document.querySelectorAll(".paymentCheckbox");
            paymentCheckboxes.forEach(function (paymentCheckbox) {
                paymentCheckbox.addEventListener("change", function () {
                    if (paymentCheckbox.checked) {
                        // 같은 그룹의 "referenceCheckbox" 체크박스를 해제
                        const referenceCheckbox = paymentCheckbox.nextElementSibling.nextElementSibling;
                        referenceCheckbox.checked = false;
                    }
                });
            });

            // "referenceCheckbox" 클래스를 가진 모든 체크박스 요소에 대한 이벤트 핸들러
            const referenceCheckboxes = document.querySelectorAll(".referenceCheckbox");
            referenceCheckboxes.forEach(function (referenceCheckbox) {
                referenceCheckbox.addEventListener("change", function () {
                    if (referenceCheckbox.checked) {
                        // 같은 그룹의 "paymentCheckbox" 체크박스를 해제
                        const paymentCheckbox = referenceCheckbox.previousElementSibling.previousElementSibling;
                        paymentCheckbox.checked = false;
                    }
                });
            });
            const xButton = document.querySelectorAll(".xButton");
            xButton.forEach(function (xButton){
                 xButton.addEventListener("click", function(){
                  const buttonContainer = xButton.parentNode;
                    const textSpan = buttonContainer.querySelector("span");
                    const nameInput = buttonContainer.querySelector("input[type='hidden']");
                    const appendLabelPayment = buttonContainer.querySelector(".paymentLabel");
                    const appendRadioPayment = buttonContainer.querySelector(".paymentCheckbox");
                    const appendLabelReference = buttonContainer.querySelector(".referenceLabel");
                    const appendRadioReference = buttonContainer.querySelector(".referenceCheckbox");

                    // 요소 삭제
                    textSpan.remove();
                    nameInput.remove();
                    appendLabelPayment.remove();
                    appendRadioPayment.remove();
                    appendLabelReference.remove();
                    appendRadioReference.remove();
                    xButton.remove();

                    // 다른 동작 수행
                    payuserBtn.style.display = 'block';
                    buttonContainer.remove();
                  });
            });

        });
    });
});
