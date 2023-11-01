const search = document.querySelector('#search');
const weatherListCon = document.querySelectorAll('.weather-list .con');

function weatherSearch() {
  // search.value;
  // search.val();
  weatherFn(search.value)
}


function weatherFn(cityVal) {
  weatherListCon.forEach(el => {
    el.innerText = "";
  });

    // 날씨 API에 요청을 보냅니다.
    let appUrl = `https://api.openweathermap.org/data/2.5/weather?q=${cityVal}&appid=31baec95fb6d389a7195e4f5dc84530b`; // 내 키

    $.ajax({
      url: appUrl,
      dataType: "json",
      type: "GET",
      success: function (result) {
        let lon = result.coord.lon; // 경도
        let lat = result.coord.lat; //위도

        $.ajax({
          url: '/post/weather2',
          dataType: "json",
          type: "POST",
          data: JSON.stringify(result),
          contentType: "application/json",
          success: function (response) {
            console.log(response);

            // 날씨 정보 업데이트 코드
            let $Icon = (result.weather[0].icon).substr(0, 2);
            let $weather_description = result.weather[0].main; // 현재 날씨 상태 (맑다)
            let $Temp = Math.floor(result.main.temp - 273.15) + 'º';
            let $city = cityVal;
            let $feels = Math.floor(result.main.feels_like - 273.15) + 'º';
            let $temp_max = Math.floor(result.main.temp_max - 273.15) + 'º';
            let $temp_min = Math.floor(result.main.temp_min - 273.15) + 'º';
            let $temperatureRange = `최저온도 ${$temp_min} / 최고온도 ${$temp_max}`;
            let $humidity = result.main.humidity + '%';

//            $('.weather_icon').html('<i class="' + weatherIcon[$Icon] + ' fa-5x" style="height : 150px; width : 150px;"></i>');
            $('.current_temp').html($Temp);
            $('.city .con').html($city);
            $('.city .description').html($weather_description);
            $('.temp .con').html($Temp);
            $('.feels_like .con').html($Temp);
//            $('.temp_max .con').html($temp_max);
//            $('.temp_min .con').html($temp_min);
            $('.temperatureRange .con').html($temperatureRange); // 최저, 최고온도
            $('.humidity .con').html($humidity);

            mapFn(lon, lat);
          },
          error: function (error) {
            console.error("에러 발생:", error);
          }
        });
      },
      error: function (error) {
        console.error("에러 발생:", error);
      }
    });
  }

// 초기에 서울 날씨 불러옴
  (
    () => {
      weatherFn("Seoul")
    }
  )();


//function mapFn(lon, lat) {
//  // 카카오 지도 API
//  var mapContainer = document.getElementById('map'); // 지도를 표시할 div
//  var mapOption = {
//    center: new kakao.maps.LatLng(lat, lon), // 지도의 중심좌표
//    level: 10 // 지도의 확대 레벨
//  };
//
//  var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
//  var markerPosition = new kakao.maps.LatLng(lat, lon);
//  var marker = new kakao.maps.Marker({
//    position: markerPosition
//  });
//
//  marker.setMap(map);
//}
function mapFn(lon, lat) {
    kakao.maps.load(function() {
      // 지도를 표시할 div와 지도 옵션 설정
      var mapContainer = document.getElementById('map'),
          mapOption = {
            center: new kakao.maps.LatLng(lat, lon), // 지도의 중심좌표
            level: 10 // 지도의 확대 레벨
          };

      // 지도 생성
      var map = new kakao.maps.Map(mapContainer, mapOption);

      // 마커 위치 설정 및 마커 생성
      var markerPosition = new kakao.maps.LatLng(lat, lon),
          marker = new kakao.maps.Marker({
            position: markerPosition
          });

      // 마커 지도에 표시
      marker.setMap(map);
    });
}