

initializeBarChart();

function initializeBarChart() {
    
    let barChart; // 전역 변수로 차트 변수 선언

    document.getElementById('backend').addEventListener('click', function() {
        getDataFromServer('백엔드');
    });
    
    document.getElementById('frontend').addEventListener('click', function() {
        getDataFromServer('프론트엔드');
    });
    
    document.getElementById('photoshop').addEventListener('click', function() {
        getDataFromServer('포토샵');
    });

    getDataFromServer('백엔드');


    function getDataFromServer(className) {
        fetch(`/api/dashboard/attendance?className=${className}`)
        .then(response => response.json())
        .then(data => {
            const labels = Object.keys(data);
            const values = Object.values(data);

            createDiagram(labels, values); // 데이터를 받아온 후 차트 생성 함수 호출
        });
    }

    
    function createDiagram(labels, values) {
        
        var ctx = document.getElementById('myBarChart').getContext('2d');
        
        // 차트가 이미 존재한다면 파괴
        if (barChart) {
            barChart.destroy();
        }

        barChart = new Chart(ctx, {
            type: 'bar',
            data: {
                // labels: [
                //     '입실',
                //     '퇴실',
                //     '지각',
                //     '병가',
                //     '휴가',
                //     '외출',
                //     '결석'
                // ],
                labels: labels,
                datasets: [{
                    data: values,
                    backgroundColor: [
                        'rgba(255, 99, 132, 0.2)',
                        'rgba(255, 159, 64, 0.2)',
                        'rgba(255, 205, 86, 0.2)',
                        'rgba(75, 192, 192, 0.2)',
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(153, 102, 255, 0.2)',
                        'rgba(201, 203, 207, 0.2)'
                    ],
                    borderColor: [
                        'rgb(255, 99, 132)',
                        'rgb(255, 159, 64)',
                        'rgb(255, 205, 86)',
                        'rgb(75, 192, 192)',
                        'rgb(54, 162, 235)',
                        'rgb(153, 102, 255)',
                        'rgb(201, 203, 207)'
                        ],
                    borderWidth: 1,
                    barThickness: 20,

                }]
            },
            options: {
                // indexAxis: 'y',
                responsive: true,
                maintainAspectRatio: false,  // 비율을 고정하지 않고 늘릴 경우
                // aspectRatio: 2,  // 가로-세로 비율 (기본값은 2입니다. 이 경우 가로길이가 세로길이의 두 배가 됩니다)
                scales: {
                    x: {
                        grid: {
                            display: false // x축 그리드 라인 숨기기
                        }
                    },
                    y: {
                        grid: {
                            display: false // x축 그리드 라인 숨기기
                        },
                        beginAtZero: true,
                        ticks: {
                            // stepSize를 1로 설정하여 y축 눈금 간격을 1로 만듭니다.
                            stepSize: 1,
                            // 콜백 함수를 사용하여 소수점 아래 자리를 버립니다.
                            callback: function(value) {
                                return Math.floor(value);
                            }
                        }
                    }
                },
                plugins: {
                    legend: {
                        display: false // 범례 숨김.
                    }
                }
            },
            elements: {
                center: {
                    color: '#000',
                    fontStyle: 'Pretendard'
                }
            },

        });

    }
    







}

