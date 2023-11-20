let url = 'https://cors-anywhere.herokuapp.com/http://ws.bus.go.kr/api/rest/';
let serviceKey='FePVBd0x369IVDEu1PmXyAm%2FduVNjCZ9T0jAGuCw1aIbHELh4zys86XdKTNd%2BfJbn1YMyFZoVOmm%2FeF38QYV0w%3D%3D';

const busDetail=document.querySelector('.bus-detail')
let tBodyTag=document.querySelector('#bus1');

function busSearch(){

  let html1="";
  let search=document.querySelector('#search')
  let type='busRouteInfo/getBusRouteList?';
  let strSrch=search.value;

  let apiUrl=`${url}busRouteInfo/getBusRouteList?serviceKey=${serviceKey}&strSrch=${strSrch}&resultType=json`;

     fetch(apiUrl)
     .then(response => response.json())
     .then(function (msg) {
              console.log(msg)
              sendDataToServer(msg); // Json 데이터 보내기
              msg.msgBody.itemList.forEach(el=>{
                       html1 += "<tr>";
                       html1+=`
                           <td>${el.busRouteNm}</td>
                                                 <td>${el.routeType}</td>
                                                 <td>${el.edStationNm}</td>
                                                 <td>${el.stStationNm}</td>
                                                 <td>${el.firstBusTm}</td>
                                                 <td>${el.lastBusTm}</td>
                                                 <td>${el.term}</td>
                            <td onclick='stationPost(event.target.innerText)' style="background-color:#ffff00">${el.busRouteId}</td>
                            <td>${el.routeType}</td>
                       `;
                       html1 += "</tr>";
                  });
                  tBodyTag.innerHTML=html1;
     });


}

// 버스 정류장 조회
const stationNm=document.querySelector('.bus-station');

function stationPost(busId){

  let html1="";
  let type='busRouteInfo/getStaionByRoute?';
  let busRouteId=busId;

  let apiUrl=`${url}busRouteInfo/getStaionByRoute?serviceKey=${serviceKey}&busRouteId=${busRouteId}&resultType=json`;


  fetch(apiUrl)
  .then(response => response.json())
  .then(function (msg) {
              console.log(msg)
              console.log(msg.msgBody)
              console.log(msg.msgBody.itemList)

               msg.msgBody.itemList.forEach(el=>{
                    console.log(el.gpsX, el.gpsY,el.stationNm);
                    html1+=`<div>${el.stationNm}</div>`;
               })

               stationNm.innerHTML=html1;
               positionFn(msg.msgBody.itemList);

  });

}




   function positionFn(dataVal) {
    kakao.maps.load(function() {
     let positions = [];

     let lat = 0;
     let lng = 0;

     dataVal.forEach((el, idx) => {
       lat = el.gpsY;
       lng = el.gpsX;

       let result = {
         title: el.stationNm,
         latlng: new kakao.maps.LatLng(parseFloat(lat), parseFloat(lng))
       };
       positions.push(result);
     });


     let mapContainer = document.getElementById('map'), // 지도를 표시할 div
       mapOption = {
         center: new kakao.maps.LatLng(dataVal[0].gpsY, dataVal[0].gpsX), // 지도의 중심좌표
         level: 5 // 지도의 확대 레벨
       };

     // 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
     let map = new kakao.maps.Map(mapContainer, mapOption);
     // 주소-좌표 변환 객체를 생성합니다
     let geocoder = new kakao.maps.services.Geocoder();

     // 마커 이미지의 이미지 주소입니다
     //let imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";
      let imageSrc = "/images/marker.png";
     for (let i = 0; i < dataVal.length; i++) {
       // 마커 이미지의 이미지 크기 입니다
       let imageSize = new kakao.maps.Size(24, 35);
       // 마커 이미지를 생성합니다
       let markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
       // 마커를 생성합니다
       let marker = new kakao.maps.Marker({
         map: map, // 마커를 표시할 지도
         position: positions[i].latlng, // 마커를 표시할 위치
         title: positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
         image: markerImage // 마커 이미지
       });
     } //for

     map.setCenter(positions[0].latlng); //기점 을 중심좌표
    });

   }


function sendDataToServer(data) {
    fetch('/api/saveDB', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json', // JSON 데이터 전송을 명시합니다.
        },
        body: JSON.stringify(data), // JSON 데이터로 변환하여 전송합니다.
    })
    .then(response => response.json())
    .then(responseData => {
        // 서버로부터의 응답을 처리합니다.
        console.log('서버 응답:', responseData);
    })
    .catch(error => {
        console.error('에러 발생:', error);
    });
}