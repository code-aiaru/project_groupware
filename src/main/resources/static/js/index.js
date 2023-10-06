document.addEventListener('DOMContentLoaded', function() {

    loadContent('/dashboard');

    document.querySelector('.snb').addEventListener('click', function(event) {
        console.log('SNB clicked:', event);  // 추가

        if (event.target.tagName === 'A' && event.target.getAttribute('data-ajax') === 'true') {
            event.preventDefault();
            const url = event.target.getAttribute('href');
            resetLoadedElements();
            loadContent(url);
        }
    });

});

function resetLoadedElements() {
    // data-loaded 속성이 "true"로 설정된 모든 script 태그를 선택합니다.
    var scripts = document.head.querySelectorAll('script[data-loaded="true"]');

    // 각 script 태그를 순회하며 삭제합니다.
    scripts.forEach(script => {
        document.head.removeChild(script);
    });

    // data-loaded 속성이 "true"로 설정된 모든 style 태그를 선택합니다.
    var styles = document.head.querySelectorAll('style[data-loaded="true"]');

    // 각 style 태그를 순회하며 삭제합니다.
    styles.forEach(style => {
        document.head.removeChild(style);
    });

    // data-loaded 속성이 "true"로 설정된 모든 link 태그를 선택합니다.
    var links = document.head.querySelectorAll('link[data-loaded="true"]');

    // 각 link 태그를 순회하며 삭제합니다.
    links.forEach(link => {
        document.head.removeChild(link);
    });

}

function loadContent(url) {
    fetch(url)
        .then(response => response.text())

        .then(html => {
        

            // html Parse
            const parser = new DOMParser();
            const doc = parser.parseFromString(html, 'text/html');

            console.log('Parsed HTML:', doc.body.innerHTML);  // 추가


            const loadedContent = doc.getElementById('loadedContent');
            if (loadedContent) {
                document.getElementById('contents').innerHTML = loadedContent.innerHTML;
            }

            const loadedTitle = doc.querySelector('meta[name="page-title"]');
            if (loadedTitle) {
                document.title = loadedTitle.getAttribute('content');
            }

            // 스크립트 태그들을 찾아 실행합니다.
            const loadedScripts = doc.querySelectorAll('script');
            loadedScripts.forEach(script => {
                const newScript = document.createElement('script');
                newScript.setAttribute('data-loaded', true);

                // 인라인 스크립트의 경우
                if (script.textContent) {
                    newScript.textContent = script.textContent;
                } 
                // 외부 스크립트의 경우
                else if (script.src) {
                    newScript.src = script.src;
                }
                
                document.head.appendChild(newScript);
            });

            // 스타일 태그들을 찾아 실행합니다.
            // 인라인 스타일의 경우
            const loadedStyles = doc.querySelectorAll('style');
            loadedStyles.forEach(style => {
                const newStyle = document.createElement('style');
                newStyle.setAttribute('data-loaded', true);
                newStyle.textContent = style.textContent;
                document.head.appendChild(newStyle);
            });
            // 외부 스타일의 경우
            const loadedLinks = doc.querySelectorAll('link');
            console.log('Found links: ', loadedLinks.length); // 로깅

            loadedLinks.forEach(link => {
                console.log('Adding link: ', link.href); // 각 링크 로깅

                const newLink = document.createElement('link');
                newLink.setAttribute('data-loaded', true);
                newLink.rel = "stylesheet";
                newLink.href = link.href;
                document.head.appendChild(newLink);
            });

        })
        .catch(error => console.error('Error:', error));
}