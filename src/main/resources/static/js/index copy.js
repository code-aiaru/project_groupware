window.globalClickedURL; // 전역 변수 선언


document.addEventListener('DOMContentLoaded', function() {

    // 클릭한 a 태그의 href 값을 전역변수에 저장하기 위한 코드.
    document.body.addEventListener('click', function(e) {
        // let anchor = e.target.closest('a[data-passoveredUrl]');
        let anchor = e.target.closest('a[data-ajax]');
        if (anchor) {
            e.preventDefault();
            window.globalClickedURL = anchor.href;
            console.log('Clicked URL:', window.globalClickedURL);
        }
    });

    
    if (previousURL != null && previousURL !== "" ) {
        console.log("새로고침 로딩");
        loadContent(previousURL, true)
    } else {
        console.log("첫 로딩");
        loadContent('/dashboard', true); 
    }


    // 이전, 이후 페이지 이동 처리
    window.addEventListener('popstate', function(event) {
        console.log('popstate event triggered', event.state);
        if (event.state && event.state.path) {
            loadContent(event.state.path, false, true);  // isPopstate를 true로 설정합니다.

        }
    });

    document.addEventListener('click', function(event) {
        const anchor = event.target.closest('a[data-ajax]');  

        if (anchor) {
            event.preventDefault();
            const hrefUrl = anchor.getAttribute('href');
            console.log(hrefUrl);
            resetLoadedElements();
            // loadContent(hrefUrl);
            loadContent(hrefUrl, false, false);
        }
    });

    document.addEventListener('click', function(event) {
        // input[type="submit"] 요소를 클릭한 경우를 확인
        if (event.target.tagName === 'INPUT' && event.target.type === 'submit') {
            // 부모 폼 요소를 찾습니다.
            const form = event.target.closest('form');
    
            // 폼이 있고, method가 GET인 경우에만 작동합니다.
            if (form && form.method.toUpperCase() === 'GET') {
                event.preventDefault();
                
                const actionUrl = form.getAttribute('action');
                let formData = new FormData(form);
                let queryString = new URLSearchParams(formData).toString();
    
                let formRequestUrl = actionUrl + '?' + queryString;
    
                if (formRequestUrl) {
                    console.log(formRequestUrl);
                    resetLoadedElements();
                    loadContent(formRequestUrl);
                }
            }
        }
    });
    



    // 기타 로직

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

            snbStatus = 'expanded';

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

            snbStatus = 'contracted';
        
        }

        initializeHightlightMenu();
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



let isLoadingContent = false;
let isAsyncUrlRequest = false;

async function loadContent(url, initialLoad = false, isPopstate = false) {
    
    if (isLoadingContent) {
        console.log('Content is loading, ignoring new request');
        return;
    }

    isLoadingContent = true;

    console.log("fetch전 url", url);

    try {
        const response = await fetch(url, {
            headers: {
                "AsyncUrlRequest": isAsyncUrlRequest,
                "CurrentURL": url
            }
        });

        const html = await response.text();

        // html Parse
        const parser = new DOMParser();
        const doc = parser.parseFromString(html, 'text/html');
        
        // 외부 스타일 로드
        const loadedLinks = doc.querySelectorAll('link');
        for (const link of loadedLinks) {
            await loadExtStyle(link.href);
        }

        // 인라인 스타일 로드
        const loadedStyles = doc.querySelectorAll('style');
        for (const style of loadedStyles) {
            await loadLiStyle(style.textContent);
        }
        
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
            // if (script.hasAttribute('data-quickInitScript')) {
            //     console.log('Quick Loading inline-script:', script.textContent);
            //     await loadInlineScript(script.textContent);
            // } else 
            if (script.src) {
                console.log('Loading external-script:', script.src);
                await loadScript(script.src);
            } else {
                console.log('Loading inline-script:', script.textContent);
                eval(script.textContent);
                // await loadInlineScript(script.textContent);
            }
        }

        
        // history 업데이트
        historyUpdate(url, initialLoad, isPopstate);

        // 하이라이트 로직 시작
        initializeHightlightMenu();

        isAsyncUrlRequest = true;

    } catch (error) {
        console.error('Error:', error);
    } finally {
        isLoadingContent = false;
    }

}

function saveState(state) {
    localStorage.setItem('pageState', JSON.stringify(state));
}



async function loadScript(src) {
    return new Promise((resolve, reject) => {
        var script = document.createElement('script');
        script.src = src;
        script.onload = resolve;
        script.onerror = () => {
            console.warn(`Failed to load script: ${src}. Ignoring the error.`);
            resolve();
        };
        document.head.appendChild(script);
    });
}

// async function loadInlineScript(content) {
//     return new Promise((resolve, reject) => {
//         var script = document.createElement('script');
//         script.textContent = content;
//         script.onload = resolve;
//         script.onerror = () => {
//             console.warn(`Failed to execute inline script. Ignoring the error.`);
//             resolve();
//         };
//         document.head.appendChild(script);
//     });
// }

async function loadExtStyle(href) {
    return new Promise((resolve, reject) => { 
        const extStyle = document.createElement('link');
        extStyle.rel = "stylesheet";
        extStyle.href = href;
        extStyle.onload = resolve;
        extStyle.onerror = () => {
            console.warn(`Failed to load stylesheet: ${href}. Ignoring the error.`);
            resolve();
        }; 
        document.head.appendChild(extStyle);
    });
}

async function loadLiStyle(textContent) {
    return new Promise((resolve) => { 
        const liStyle = document.createElement('style');
        liStyle.textContent = textContent;
        document.head.appendChild(liStyle);
        resolve(); // style 태그는 즉시 적용되므로 resolve 호출
    });
}




function urlUpdate(url){
    console.log("urlUpdate function called with url:", url);

    if (url == null && url == "") {
        url = '/dashboard';
    } 

    return url;
}

function historyUpdate(url, initialLoad, isPopstate) {

    // URL 업데이트
    if (initialLoad) {
        console.log(`Replacing state with url: ${url}`);
        history.replaceState({ path: url }, '', url);
    } else if (!isPopstate) {

        console.log(`Pushing state with url: ${url}`);
        history.pushState({ path: url }, '', url);
    }
}


// 하이라이트 관련

let snbStatus = 'expanded';

function initializeHightlightMenu() {

    resetHighlightExpanded();
    resetHighlightContracted();

    console.log('snbStatus: ', snbStatus);

    if (snbStatus === 'expanded') {
        highlightCurrentMenuExpanded();
    } else if (snbStatus === 'contracted') {
        highlightCurrentMenuContracted();
    }
}

function highlightCurrentMenuExpanded() {

    console.log('highlightCurrentMenuExpanded activated');

    const currentUrl = window.location.pathname;
    const functionTarget = document.querySelector('[data-snbStatus="expanded"]');
    const hightlightTargetMenu = functionTarget.querySelector(`a[href*="${currentUrl}"]`);

    if (hightlightTargetMenu) {
        const parentLi = hightlightTargetMenu.parentElement; // 부모 li
        const imgTag = hightlightTargetMenu.querySelector('img');
        const spanTag = hightlightTargetMenu.querySelector('span');
        const highlightImage = hightlightTargetMenu.querySelector('.select-highlight');

        if (highlightImage && imgTag && spanTag) {
            parentLi.setAttribute('data-hightlighted', '');
            imgTag.style.filter = 'invert(45%) sepia(95%) saturate(403%) hue-rotate(154deg) brightness(86%) contrast(88%)';
            spanTag.style.color = '#2788b5';
            spanTag.style = 'color: #2788b5; font-weight: bold';
            highlightImage.style.display = 'inline-block';
        }
    }
}

function highlightCurrentMenuContracted() {

    console.log('highlightCurrentMenuContracted activated');

    const currentUrl = window.location.pathname;
    const functionTarget = document.querySelector('[data-snbStatus="contracted"]');
    const hightlightTargetMenu = functionTarget.querySelector(`a[href*="${currentUrl}"]`);
    
    if (hightlightTargetMenu) {
        const parentLi = hightlightTargetMenu.parentElement; // 부모 li
        const imgTag = hightlightTargetMenu.querySelector('img');
        const spanTag = hightlightTargetMenu.querySelector('span');

        if (imgTag && spanTag) {
            parentLi.setAttribute('data-hightlighted', '');
            parentLi.style.backgroundColor = '#e2e7ec';
            imgTag.style.filter = 'invert(45%) sepia(95%) saturate(403%) hue-rotate(154deg) brightness(86%) contrast(88%)';
            spanTag.style.color = '#2788b5';
            spanTag.style = 'color: #2788b5; font-weight: bold';
        }
    }
}


function resetHighlightExpanded() {
    const functionTarget = document.querySelector('[data-hightlighted]');
    if (!functionTarget) return;

    const highlightImg = functionTarget.querySelector('.select-highlight');
    const imgTag = functionTarget.querySelector('img');
    const spanTag = functionTarget.querySelector('span');

    if (highlightImg) highlightImg.style.display = 'none';
    if (imgTag) imgTag.removeAttribute('style');
    if (spanTag) spanTag.removeAttribute('style');
    functionTarget.removeAttribute('data-highlightedMenu');
}

function resetHighlightContracted() {
    const functionTarget = document.querySelector('[data-hightlighted]');
    if (!functionTarget) return;

    const imgTag = functionTarget.querySelector('img');
    const spanTag = functionTarget.querySelector('span');

    if (imgTag) imgTag.removeAttribute('style');
    if (spanTag) spanTag.removeAttribute('style');
    functionTarget.removeAttribute('style');
    functionTarget.removeAttribute('data-hightlighted');
}