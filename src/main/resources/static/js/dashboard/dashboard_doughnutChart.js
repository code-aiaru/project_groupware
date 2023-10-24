initializeDoughnutChart();

function initializeDoughnutChart() {

    var pendingApprovalsCount;
    var rejectedApprovalsCount;
    var approvedApprovalsCount;

    const pendingApprovalsColor = '#e04581';
    const rejectedApprovalsColor = '#f5ad34';
    const approvedApprovalsColor = '#835fc6';


    getDataFromServer();

    function getDataFromServer() {
        fetch('/api/dashboard/approval')
        .then(response => response.json())
        .then(data => {
            pendingApprovalsCount = data.pendingApprovalsCount;
            rejectedApprovalsCount = data.rejectedApprovalsCount;
            approvedApprovalsCount = data.approvedApprovalsCount;

            document.getElementById("pendingApprovalsCountDisplay").textContent = pendingApprovalsCount;
            document.getElementById("rejectedApprovalsCountDisplay").textContent = rejectedApprovalsCount;
            document.getElementById("approvedApprovalsCountDisplay").textContent = approvedApprovalsCount;

            createDiagram(); // 데이터를 받아온 후 차트 생성 함수 호출
        });
    }

    // // 차트 관련 로직
    // var chart;
    // var currentSlideIndex = 0;

    // // 1. 플러그인 정의
    // const centerTextPlugin = {
    //     id: 'centerText',
    //     afterDraw: function(chart, args, options) {
    //         var ctx = chart.ctx;
    //         ctx.textAlign = 'center';
    //         ctx.textBaseline = 'middle';
        
    //         var label = chart.data.labels[currentSlideIndex];
    //         var dataValue = chart.data.datasets[0].data[currentSlideIndex];
    //         var currentColor = chart.data.datasets[0].backgroundColor[currentSlideIndex];
        
    //         var centerX = chart.width / 2;
    //         var centerY = chart.height / 2;
    //         var radius;
        
    //         // outerRadius 값을 제대로 가져오기
    //         if (chart.getDatasetMeta(0).data[0] && chart.getDatasetMeta(0).data[0]._model) {
    //             radius = chart.getDatasetMeta(0).data[0]._model.outerRadius * 0.6; // 0.6은 원의 크기를 조절하는 인자입니다.
    //         } else {
    //             radius = Math.min(chart.width, chart.height) / 4; // default 값으로 설정합니다.
    //         }
        
    //         // 원형 도형 먼저 그리기
    //         ctx.fillStyle = currentColor;
    //         ctx.beginPath();
    //         ctx.arc(centerX, centerY, radius, 0, 2 * Math.PI);
    //         ctx.fill();
        
    //         // "#" 부분 (dataValue) 스타일 적용
    //         ctx.font = "bold 50px 'Pretendard'";
    //         ctx.fillStyle = '#ffffff';
    //         ctx.fillText(dataValue, centerX - 10, centerY - 6);
        
    //         // "건" 부분 스타일 적용
    //         ctx.font = "20px 'Pretendard'";
    //         ctx.fillText('건', centerX + (dataValue.toString().length * 12), centerY - 16);
        
    //         // label 부분 (e.g. "대기 중인 결재 수") 스타일 적용
    //         ctx.font = "15px 'Pretendard'";
    //         ctx.fillText(label, centerX, centerY + 21);
    //     }

    // };

    function createDiagram() {
        
        var ctx = document.getElementById('myDoughnutChart').getContext('2d');
        
        chart = new Chart(ctx, {
            type: 'doughnut',
            data: {
                labels: [
                    '대기 중인 결재 수', 
                    '거부된 결재 수', 
                    '승인된 결재 수'
                ],
                datasets: [{
                    data: [
                        pendingApprovalsCount, 
                        rejectedApprovalsCount, 
                        approvedApprovalsCount
                    ],
                    backgroundColor: [
                        pendingApprovalsColor,
                        rejectedApprovalsColor,
                        approvedApprovalsColor
                    ],
                }]
            },
            options: {
                cutout: '50%', // 도넛 두께를 얇게 조절
                plugins: {
                    legend: {
                        display: false,  // 범례 숨기기
                    },
                    tooltips: {
                        enabled: true,  // 툴팁 활성화
                    }
                }
            },
            elements: {
                center: {
                    color: '#000',
                    fontStyle: 'Pretendard'
                }
            },
            // plugins: [centerTextPlugin] // 2. 차트 생성 시 플러그인 추가

        });

        const pendingColorIndicator = document.querySelector('.pending-approvals-color');
        pendingColorIndicator.style.backgroundColor = pendingApprovalsColor;
        const rejectedColorIndicator = document.querySelector('.rejected-approvals-color');
        rejectedColorIndicator.style.backgroundColor = rejectedApprovalsColor;
        const approvedColorIndicator = document.querySelector('.approved-approvals-color');
        approvedColorIndicator.style.backgroundColor = approvedApprovalsColor;
        // setInterval(updateSlide, 3000); // 3초마다 슬라이드를 변경
    }

    // function updateSlide() {
    //     currentSlideIndex = (currentSlideIndex + 1) % chart.data.labels.length; // 다음 슬라이드로 이동
    //     chart.update(); // 차트 업데이트하여 플러그인의 afterDraw를 호출
    // }
    

}

