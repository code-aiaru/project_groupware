document.addEventListener('DOMContentLoaded', function() {

    // html 로딩시 초기 설정
    loadContent('/dashboard');

    document.addEventListener('click', function(event) {
        const anchor = event.target.closest('a[data-ajax]');  
        // 만약 <a> 태그가 있고, data-ajax 속성이 있다면 로직을 실행합니다.
        if (anchor) {
            event.preventDefault();
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
    return new Promise(resolve => {
        var loadedContents = document.getElementById('contents');
        loadedContents.textContent = "";

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

        resolve();
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


        // 외부 스타일 로드
        const loadedLinks = doc.querySelectorAll('link');
        for (const link of loadedLinks) {
            await loadExtStyle(link.href);
        }

        // 인라인 스타일 로드
        const loadedStyles = doc.querySelectorAll('style');
        for (const link of loadedStyles) {
            await loadLiStyle(style.textContent);
        }


        // loadedStyles.forEach(style => {
        //     const newStyle = document.createElement('style');
        //     newStyle.textContent = style.textContent;
        //     document.head.appendChild(newStyle);
        // });
    

        const loadedContent = doc.getElementById('loadedContent');
        
        if (loadedContent) {
            document.getElementById('contents').innerHTML = loadedContent.innerHTML;
        }

        const loadedTitle = doc.querySelector('meta[name="page-title"]');
        if (loadedTitle) {
            document.title = loadedTitle.getAttribute('title');
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

async function loadExtStyle(href) {
    return new Promise((resolve, reject) => {  // Promise를 반환합니다.
        const extStyle = document.createElement('link');
        extStyle.rel = "stylesheet";
        extStyle.href = href;
        extStyle.onload = resolve;  // 로드 성공시 resolve를 호출합니다.
        extStyle.onerror = reject;  // 로드 실패시 reject를 호출합니다.
        document.head.appendChild(extStyle);
    });
}

async function loadLiStyle(textContent) {
    return new Promise((resolve, reject) => {  // Promise를 반환합니다.
        const liStyle = document.createElement('style');
        liStyle.textContent = textContent;
        liStyle.onload = resolve;  // 로드 성공시 resolve를 호출합니다.
        liStyle.onerror = reject;  // 로드 실패시 reject를 호출합니다.
        document.head.appendChild(liStyle);
    });
}