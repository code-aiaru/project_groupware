function weatherSearch() {
      // 선택한 도시 값 가져오기
      var selectedCity = $("#search").val();

      // 도시 정보를 가져올 API 엔드포인트 URL 설정 (예시)
      var apiUrl = "/weather_java?cityVal=" + selectedCity;

      // API를 호출하여 데이터 가져오기
      $.get(apiUrl, function(data) {
        // 가져온 데이터를 화면에 출력
        $(".city .con").text(data.name);
        $(".description").text(data.weather[0].main);
        $(".temp .con").text(data.temp + "°C");
        $(".feels_like .con").text(data.feels_like + "°C");
        $(".temperatureRange .con").text(data.temp_min + "°C ~ " + data.temp_max + "°C");
        $(".humidity .con").text(data.humidity + "%");
      });
    }