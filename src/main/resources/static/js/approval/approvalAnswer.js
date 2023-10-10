const payAnwserBtn=document.querySelector('#payAnwserBtn');
const XBtn=document.querySelector('#XBtn');
const replyWriteForm=document.querySelector('.reply-write');


payAnwserBtn.addEventListener('click',writeFn);
XBtn.addEventListener('click',xFn);

function writeFn(event){
    scriptBack.style.display='flex';
    replyWriteForm.style.display='flex';
}

function xFn(event){
    scriptBack.style.display='none';
    replyWriteForm.style.display='none';
}