
initializeApprovalList();

function initializeApprovalList() {

    let currentUrl;

    if (window.globalClickedURL) {
        currentUrl = window.globalClickedURL;
    } else {
        currentUrl = window.location.href;
    }
    
    console.log('넘겨받은 url',currentUrl);
    
    const contents = document.getElementById('contents');

    const links = contents.querySelectorAll('a[data-ajax]');

    showActiveApprovalList();

    links.forEach(function(link) {
        link.addEventListener('click', function(event) {
            console.log('clicked')
            initializeApprovalList();
        });
    });
    
    function showActiveApprovalList() {
        console.log('showActiveApprovalList')
        
        resetActiveApprovalList()
        
        // 현재 URL을 가져옵니다.
        
        // 모든 <a> 태그를 가져와서 각각에 대해 작업을 수행합니다.
        links.forEach(function(link) {
            // <a> 태그의 href 값을 가져옵니다.
            var hrefValue = link.getAttribute('href');
            console.log(hrefValue);

            // currentUrl이 hrefValue를 포함하고 있는지 확인
            if (hrefValue && currentUrl.includes(hrefValue.replace('@{', '').replace('}', ''))) {
                link.parentElement.classList.add('active');
            }
        });
    }
    
    function resetActiveApprovalList() {
        console.log('resetActiveApprovalList')
        links.forEach(function(link) {
            link.parentElement.classList.remove('active');
        });
    }
}

