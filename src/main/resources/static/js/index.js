document.addEventListener('DOMContentLoaded', function() {

    // html 로딩시 초기 설정
    loadContent('/dashboard');




    // // SNB 관련 로직
    // document.querySelector('.snb').addEventListener('click', function(event) {
    // // document.addEventListener('click', function(event) {
    //     console.log('data-ajax clicked:', event);

    //     const anchor = event.target.closest('a');  // 가장 가까운 <a> 태그를 찾습니다.

    //     if (anchor && anchor.getAttribute('data-ajax')) {
    //         event.preventDefault();
    //         console.log('ajax loaded:', event);
    //         const url = anchor.getAttribute('href');
    //         resetLoadedElements();
    //         loadContent(url);
    //     }
    // });

    document.addEventListener('click', function(event) {
        const anchor = event.target.closest('a[data-ajax]');  
    
        // 만약 <a> 태그가 있고, data-ajax 속성이 있다면 로직을 실행합니다.
        if (anchor) {
            event.preventDefault();
            console.log('ajax loaded:', event);
            const url = anchor.getAttribute('href');
            resetLoadedElements();
            loadContent(url);
        }
    });

    document.getElementById('size-control_btn').addEventListener('click', function(event) {
        var contractBtn = document.querySelector('.contract_btn');
        var expandBtn = document.querySelector('.expand_btn');
        var snb = document.querySelector('.snb');
        var dummyBox = document.querySelector('.dummy_box');
        
        if (snb.classList.contains('contracted')) {
            snb.classList.remove('contracted');
            snb.classList.add('expanded');
            dummyBox.classList.remove('contracted')
            dummyBox.classList.add('expanded')
            contractBtn.style.display = 'block';
            expandBtn.style.display = 'none';

            document.querySelectorAll('.contract-shown').forEach(el => {
                el.style.display = 'none';
            });
            document.querySelectorAll('.expand-shown').forEach(el => {
                el.style.display = 'block';
            });

        } else if(snb.classList.contains('expanded')) {
            snb.classList.remove('expanded');
            snb.classList.add('contracted');
            dummyBox.classList.remove('expanded')
            dummyBox.classList.add('contracted')
            contractBtn.style.display = 'none';
            expandBtn.style.display = 'block';
            
            document.querySelectorAll('.contract-shown').forEach(el => {
                el.style.display = 'block';
            });
            document.querySelectorAll('.expand-shown').forEach(el => {
                el.style.display = 'none';
            });
        }
    });
    


    // GNB 관련 로직

    // 드롭다운메뉴 관련 로직
    var dropdownMenu = document.querySelector('.dropdown-menu');
    var userImgBox = document.getElementById('user-img_box_small');

    userImgBox.addEventListener('click', function(event) {
        if (dropdownMenu.style.display === 'block') {
            dropdownMenu.style.display = 'none';
        } else {
            dropdownMenu.style.display = 'block';
        }
        event.stopPropagation();
    });

    // userImgBox.addEventListener('click', toggleMenu);
    
    // 드롭다운메뉴 닫기
    document.body.addEventListener('click', function(event) {
        if (!userImgBox.contains(event.target) && !dropdownMenu.contains(event.target)) {
            dropdownMenu.style.display = 'none';
        }
    });



});





function resetLoadedElements() {
    var styleTags = document.querySelectorAll('style:not([data-static])');
    var scriptTags = document.querySelectorAll('script:not([data-static])');
    var linkTags = document.querySelectorAll('link:not([data-static])');

    styleTags.forEach(function(tag) {
        tag.remove();
    });

    scriptTags.forEach(function(tag) {
        tag.remove();
    });

    linkTags.forEach(function(tag) {
        tag.remove();
    });
}



async function loadContent(url) {
    try {
        const response = await fetch(url);
        const html = await response.text();

        // html Parse
        const parser = new DOMParser();
        const doc = parser.parseFromString(html, 'text/html');

        console.log('Parsed HTML:', doc.body.innerHTML);

        const loadedContent = doc.getElementById('loadedContent');
        
        if (loadedContent) {
            document.getElementById('contents').innerHTML = loadedContent.innerHTML;
        }

        const loadedTitle = doc.querySelector('meta[name="page-title"]');
        if (loadedTitle) {
            document.title = loadedTitle.getAttribute('content');
        }

        const loadedScripts = doc.querySelectorAll('script');

        for (const script of loadedScripts) {
            if (script.src) {
                console.log('Loading script:', script.src);
                await loadScript(script.src);
            } else {
                // 인라인 스크립트의 경우, 스크립트 내용을 실행합니다.
                eval(script.textContent);
            }
        }

        // 스타일 태그들을 찾아 실행합니다.
        const loadedStyles = doc.querySelectorAll('style');

        loadedStyles.forEach(style => {
            const newStyle = document.createElement('style');
            // newStyle.setAttribute('data-loaded', true);
            newStyle.textContent = style.textContent;
            document.head.appendChild(newStyle);
        });

        // 외부 스타일의 경우
        const loadedLinks = doc.querySelectorAll('link');
        console.log('Found links: ', loadedLinks.length); // 로깅

        loadedLinks.forEach(link => {
            console.log('Adding link: ', link.href); // 각 링크 로깅

            const newLink = document.createElement('link');
            // newLink.setAttribute('data-loaded', true);
            newLink.rel = "stylesheet";
            newLink.href = link.href;
            document.head.appendChild(newLink);
        });

    } catch (error) {
        console.error('Error:', error);
    }
}

async function loadScript(src) {
    return new Promise((resolve, reject) => {
        var script = document.createElement('script');
        script.type = 'text/javascript';
        script.src = src;
        script.onload = resolve; // 스크립트 로드가 완료되면 Promise를 resolve 합니다.
        script.onerror = reject; // 에러 발생시 Promise를 reject 합니다.
        document.head.appendChild(script);
    });
}

