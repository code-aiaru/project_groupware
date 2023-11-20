const start = document.querySelector('#start');
const end = document.querySelector('#end');
const createOkBtn = document.querySelector('#createOkBtn');

createOkBtn.addEventListener('click', function(event) => {
//input 태그면? .value?
  const attendance_data = {
    'start': start.value,
    'end': end.value,
  };

   const url = "/api/attendance/create";    // get은 여기 + /{id} 처럼
    fetch(url, {                            //{url, 메서드}
        method: "POST",
        body: JSON.stringify(attendance_data),
        headers: {
          "Content-Type": "application/json",
        },
      })
      .then((response) => response.json())
      .then((data) => {
        alert("출결 기간 생성 성공!");
        console.log(data);
//        ajaxAttendanceList();
      }).catch((error) => {
        console.log(error);
      });
  });

//const ToDayOkBtn = document.querySelector('#ToDayOkBtn');
//
//  ToDayOkBtn.addEventListener('click', () => {
//     const url = "/api/attendance/create/now";    // get은 여기 + /{id} 처럼
////     const url = "/api/attendance/now";
//      fetch(url, {                            //{url, 메서드}
//          method: "GET",
////          body: JSON.stringify(attendance_data),
//          headers: {
//            "Content-Type": "application/json",
//          },
//        })
//        .then((response) => response.json())
//        .then((data) => {
//          alert("출결 당일 생성 성공!");
//          console.log(data);
//        }).catch((error) => {
//          console.log(error);
//        });
//    });

const ToDayOkBtn = document.querySelector('#ToDayOkBtn');

  ToDayOkBtn.addEventListener('click', () => {

  console.log('click actiaveted');
            const button = event.target.closest('button');
            if (button) {
     const url = "/api/attendance/create/now";    // get은 여기 + /{id} 처럼
//     const url = "/api/attendance/now";
      fetch(url, {                            //{url, 메서드}
          method: "GET",
//          body: JSON.stringify(attendance_data),
          headers: {
            "Content-Type": "application/json",
          },
        })
        .then((response) => response.json())
        .then((data) => {
          alert("출결 당일 생성 성공!");
          console.log(data);
        }).catch((error) => {
          console.log(error);
        });
        }
    });

const InOkBtn = document.querySelector('#InOkBtn');
const OutOkBtn = document.querySelector('#OutOkBtn');

  InOkBtn.addEventListener('click', () => {
     const url = "/api/attendance/in";    // get은 여기 + /{id} 처럼
//     const url = "/api/attendance/now";
      fetch(url, {                            //{url, 메서드}
          method: "GET",
//          body: JSON.stringify(attendance_data),
          headers: {
            "Content-Type": "application/json",
          },
        })
        .then((response) => response.json())
        .then((data) => {
          alert("출근 처리 성공!");
          console.log(data);

//          InOkBtn.style.display='none';
//          OutOkBtn.style.display='block';

        }).catch((error) => {
          console.log(error);
        });
    });


  OutOkBtn.addEventListener('click', () => {
     const url = "/api/attendance/out";    // get은 여기 + /{id} 처럼
//     const url = "/api/attendance/now";
      fetch(url, {                            //{url, 메서드}
          method: "GET",
//          body: JSON.stringify(attendance_data),
          headers: {
            "Content-Type": "application/json",
          },
        })
        .then((response) => response.json())
        .then((data) => {
          alert("퇴근 처리 성공!");
          console.log(data);

//          InOkBtn.style.display='block';
//          OutOkBtn.style.display='block';

        }).catch((error) => {
          console.log(error);
        });
    });


        // 휴가
        const start2 = document.querySelector('#start2');
        const end2 = document.querySelector('#end2');
        const vacationOkBtn = document.querySelector('#vacationOkBtn');

        vacationOkBtn.addEventListener('click', () => {
        //input 태그면? .value?
          const attendance_data2 = {
            'start': start2.value,
            'end': end2.value,
          };

           const url = "/api/attendance/vacation2";    // get은 여기 + /{id} 처럼
            fetch(url, {                            //{url, 메서드}
                method: "POST",
                body: JSON.stringify(attendance_data2),
                headers: {
                  "Content-Type": "application/json",
                },
              })
              .then((response) => response.json())
              .then((data) => {
                alert("휴가 신청 성공!");
                console.log(data);

              }).catch((error) => {
                console.log(error);
              });
          });

        //병가
        const start3 = document.querySelector('#start3');
        const end3 = document.querySelector('#end3');
        const sickOkBtn = document.querySelector('#sickOkBtn');

        sickOkBtn.addEventListener('click', () => {

          const attendance_data3 = {
            'start': start3.value,
            'end': end3.value,
          };

           const url = "/api/attendance/sick2";    // get은 여기 + /{id} 처럼
            fetch(url, {                            //{url, 메서드}
                method: "POST",
                body: JSON.stringify(attendance_data3),
                headers: {
                  "Content-Type": "application/json",
                },
              })
              .then((response) => response.json())
              .then((data) => {
                alert("병가 신청 성공!");
                console.log(data);

              }).catch((error) => {
                console.log(error);
              });
          });