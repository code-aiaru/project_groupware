initializeScriptAW();

function initializeScriptAW(){

document.getElementById("goBackButton").addEventListener("click", function() {
            window.history.back();
        });

const approvalAnswerBtn=document.querySelector('#approvalAnswerBtn');
const XBtn=document.querySelector('#XBtn');
const approvalResultForm=document.querySelector('.approval-Result');
const scriptBack=document.querySelector('.scriptBack');

approvalAnswerBtn.addEventListener('click',approvalFn);
XBtn.addEventListener('click',closeFn);

function approvalFn(event){
    scriptBack.style.display='flex';
    approvalResultForm.style.display='flex';
}

function closeFn(event){
    scriptBack.style.display='none';
    approvalResultForm.style.display='none';
}
}