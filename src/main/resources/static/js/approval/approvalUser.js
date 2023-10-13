initializeScriptAU();

function initializeScriptAU() {
    const ApprovalUserBlock = document.querySelector('#ApprovalUserData');

    const userAddBtns = document.querySelectorAll('.userAddBtn');
    userAddBtns.forEach(function(userAddBtn) {
        userAddBtn.addEventListener('click', function (event) {
            userAddBtn.style.display='none';
            const employeeNo = userAddBtn.getAttribute("value");
            const employeeInfo = userAddBtn.textContent;

            const divContainer = document.createElement("div");
            divContainer.classList.add("parent");

            const userInput = document.createElement("input");
            userInput.type = "hidden";
            userInput.name = "dataArray";
            userInput.value = employeeNo;
            userInput.readOnly = true;

            const textSpan = document.createElement("span");
            textSpan.textContent = employeeInfo;

            const LabelPayment = document.createElement("label");
            LabelPayment.textContent = "결재";
            LabelPayment.classList.add("paymentLabel");
            const CheckBoxPayment = document.createElement("input");
            CheckBoxPayment.type = "checkbox";
            CheckBoxPayment.name = "dataArray";
            CheckBoxPayment.value = "0";
            CheckBoxPayment.classList.add("paymentCheckbox");

            const LabelReference = document.createElement("label");
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
            xButtonPayment.value = employeeNo;
            xButtonPayment.textContent = "x";
            xButtonPayment.classList.add("xButton");

                    divContainer.appendChild(textSpan);
                    divContainer.appendChild(userInput);
                    divContainer.appendChild(LabelPayment);
                    divContainer.appendChild(CheckBoxPayment);
                    divContainer.appendChild(LabelReference);
                    divContainer.appendChild(CheckBoxReference);
                    divContainer.appendChild(xButtonPayment);

                    ApprovalUserBlock.appendChild(divContainer);

            const paymentCheckbox = document.querySelector('.paymentCheckbox')
            const referenceCheckbox = document.querySelector('.referenceCheckbox')


            const paymentCheckboxes = document.querySelectorAll(".paymentCheckbox");
            paymentCheckboxes.forEach(function (paymentCheckbox) {
                paymentCheckbox.addEventListener("change", function () {
                    if (paymentCheckbox.checked) {
                        const referenceCheckbox = paymentCheckbox.nextElementSibling.nextElementSibling;
                        referenceCheckbox.checked = false;
                    }
                });
            });

            const referenceCheckboxes = document.querySelectorAll(".referenceCheckbox");
            referenceCheckboxes.forEach(function (referenceCheckbox) {
                referenceCheckbox.addEventListener("change", function () {
                    if (referenceCheckbox.checked) {
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
                    userInput.remove();
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
}
