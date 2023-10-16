const approvalAnswerBtn=document.querySelector('#approvalAnswerBtn');
const XBtn=document.querySelector('#XBtn');
const approvalAnswerForm=document.querySelector('.approval-write');
const scriptBack=document.querySelector('.scriptBack');

approvalAnswerBtn.addEventListener('click',approvalFn);
XBtn.addEventListener('click',closeFn);

function approvalFn(event){
    scriptBack.style.display='flex';
    approvalAnswerForm.style.display='flex';
}

function closeFn(event){
    scriptBack.style.display='none';
    approvalAnswerForm.style.display='none';
}