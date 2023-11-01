const search = document.querySelector('#search');
const weatherList = document.querySelector('.weather-list');
const weatherListCon = document.querySelectorAll('.weather-list .con');


function weatherSearch() {
  weatherFn(search.value)
}

function weatherFn(cityVal) {
  weatherListCon.forEach(el => {
    el.innerText = "";
  });

  // 날씨 api - fontawesome 아이콘
  let weatherIcon = {
    '01': 'fas fa-sun',
    '02': 'fas fa-cloud-sun',
    '03': 'fas fa-cloud',
    '04': 'fas fa-cloud-meatball',
    '09': 'fas fa-cloud-sun-rain',
    '10': 'fas fa-cloud-showers-heavy',
    '11': 'fas fa-poo-storm',
    '13': 'far fa-snowflake',
    '50': 'fas fa-smog'
  };

  let appUrl = `/api/weather_java?q=${cityVal}`;

  $.ajax({
    url: appUrl,
    dataType: "json",
    type: "GET",
    success: function (result) {

      console.log(result);
      let jsonRs = JSON.parse(result.weather);

      console.log(jsonRs,' jsonRs');
      let lat = jsonRs.coord.lat; // 위도
      let lon = jsonRs.coord.lon; // 경도

      console.log(lon, lat);

      // 날씨 정보 업데이트 코드
      let $Icon = (jsonRs.weather[0].icon).substr(0, 2);
      let $weather_description = jsonRs.weather[0].main; // 현재 날씨 상태 (맑다)
      let $Temp = Math.floor(jsonRs.main.temp - 273.15) + 'º';
      let $city = cityVal;
      let $feels = Math.floor(jsonRs.main.feels_like - 273.15) + 'º';
      let $temp_max = Math.floor(jsonRs.main.temp_max - 273.15) + 'º';
      let $temp_min = Math.floor(jsonRs.main.temp_min - 273.15) + 'º';
      let $temperatureRange = `최저온도 ${$temp_min} / 최고온도 ${$temp_max}`;
      let $humidity = jsonRs.main.humidity + '%';

      $('.weather_icon').html('<i class="' + weatherIcon[$Icon] + ' fa-5x" style="height : 150px; width : 150px;"></i>');
      $('.current_temp').html($Temp);
      $('.city .con').html($city);
      $('.city .description').html($weather_description);
      $('.temp .con').html($Temp);
      $('.feels_like .con').html($Temp);
//    $('.temp_max .con').html($temp_max);
//    $('.temp_min .con').html($temp_min);
      $('.temperatureRange .con').html($temperatureRange); // 최저, 최고온도
      $('.humidity .con').html($humidity);

      mapFn(lon, lat);
    }
  })
}

(
  () => {
    weatherFn("Seoul")
  }
)();

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