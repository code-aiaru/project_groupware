
    function findAllNoticePost() {
        // API 호출 및 데이터 렌더링 로직
        fetch('/api/posts/notice')
            .then(response => response.json())
            .then(list => {
                if (list.length < 1) {
                    document.querySelector('#posts').innerHTML = '<tr ><td colspan="2">검색된 결과가 없습니다.</td></tr>';
                    return;
                }

                let html = '';
                list.forEach((item, index) => {
                    html += `
                        <tr>
                            <td>${index + 1}</td>
                            <td class="left"><a href="/notice/view.html?id=${item.id}" data-ajax>${item.title}</a></td>
                        </tr>
                    `;
                });

                document.querySelector('#posts').innerHTML = html;
            })
            .catch(error => console.error(error));
    }
    findAllNoticePost(); // 비동기 함수 호출