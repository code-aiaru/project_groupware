

initializePostList();

function initializePostList() {

    const postLoadSection = document.getElementById('posts');

    getDataFromServer();


    function getDataFromServer() {
        fetch(`/api/posts/`)
        .then(response => {
            if (!response.ok) {
                throw new Error('응답이 올바르지 않습니다.');
            }
            return response.json();
        })
        .then(data => {
            const posts = data.listFromPost.content; // 제목 뿐만 아니라, 전체 공지 데이터를 가져옵니다.
            displayPostTitles(posts);
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error.message);
        });
    }

   function displayPostTitles(posts) {
       let postHtml = '';
       for (let index = 0; index < posts.length; index++) {
           let post = posts[index];
           // let cleanContent = stripHTML(post.content);
           // postHtml += `<li>[${post.writer}] ${post.title} - ${cleanContent}</li>`;
           postHtml += `
               <tr>
                   <td>${index + 1}</td>
                   <td class="left"><a href="/post/view.html?id=${post.id}" data-ajax> ${post.title}</a></td>
               </tr>
           `;
       }

       postLoadSection.innerHTML = postHtml;
   }


}