document.addEventListener("DOMContentLoaded", function() {
    const ApprovalUserBlock = document.querySelector('#ApprovalUserData');
    // payuserBtn 요소를 모두 선택
    const userAddBtns = document.querySelectorAll('.userAddBtn');
    userAddBtns.forEach(function(userAddBtn) {
        userAddBtns.addEventListener('click', function (event) {
            userAddBtn.style.display='none';
            const employeeNo = userAddBtn.getAttribute("value");
            const employeeInfo = userAddBtn.textContent;

            const divContainer = document.createElement("div");
            divContainer.classList.add("parent");

            // 새로운 input 필드 생성 및 설정
            const userInput = document.createElement("input");
            userInput.type = "hidden";
            userInput.name = "dataArray";
            userInput.value = employeeNo;
            userInput.readOnly = true;

            const textSpan = document.createElement("span");
            textSpan.textContent = employeeInfo;

            // 새로운 라디오 버튼 생성 및 설정
            const LabelPayment = document.createElement("label");
            LabelPayment.textContent = "결제";
            LabelPayment.classList.add("paymentLabel");
            const CheckBoxPayment = document.createElement("input");
            CheckBoxPayment.type = "checkbox";
            CheckBoxPayment.name = "dataArray";
            CheckBoxPayment.value = "0";
            CheckBoxPayment.classList.add("paymentCheckbox");

            const appendLabelReference = document.createElement("label");
            LabelReference.textContent = "참조";
            LabelReference.classList.add("referenceLabel");
            const CheckBoxReference = document.createElement("input");
            CheckBoxReference.type = "checkbox";
            CheckBoxReference.name = "dataArray";
            CheckBoxReference.value = "1";
            CheckBoxReference.checked = true;
            CheckBoxReference.classList.add("referenceCheckbox");

            const xButtonPayment = document.createElement('button')
            xButtonPayment.type = "button";
            xButtonPayment.value = memberId;
            xButtonPayment.textContent = "x";
            xButtonPayment.classList.add("xButton");



            // 생성한 요소들을 <div>에 추가
                    divContainer.appendChild(textSpan);
                    divContainer.appendChild(userInput);
                    divContainer.appendChild(LabelPayment);
                    divContainer.appendChild(CheckBoxPayment);
                    divContainer.appendChild(LabelReference);
                    divContainer.appendChild(CheckBoxReference);
                    divContainer.appendChild(xButtonPayment);


                    // <div>를 payuserData 블록에 추가
                    ApprovalUserBlock.appendChild(divContainer);

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
                    const userInput = buttonContainer.querySelector("input[type='hidden']");
                    const LabelPayment = buttonContainer.querySelector(".paymentLabel");
                    const RadioPayment = buttonContainer.querySelector(".paymentCheckbox");
                    const LabelReference = buttonContainer.querySelector(".referenceLabel");
                    const RadioReference = buttonContainer.querySelector(".referenceCheckbox");

                    // 요소 삭제
                    textSpan.remove();
                    nameInput.remove();
                    LabelPayment.remove();
                    RadioPayment.remove();
                    LabelReference.remove();
                    RadioReference.remove();
                    xButton.remove();

                    // 다른 동작 수행
                    userAddBtn.style.display = 'block';
                    buttonContainer.remove();
                  });
            });

        });
    });
});
