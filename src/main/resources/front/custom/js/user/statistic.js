function renderChart(data) {
    $('#canvas').empty().append('<canvas id="popChart" width="600" height="400"></canvas>');

    var popCanvas = $("#popChart");

    var barChart = new Chart(popCanvas, {
        type: 'bar',
        data: {
            labels: data.countries,
            datasets: [{
                label: 'count',
                data: data.values,
                backgroundColor: [
                    'rgba(255, 99, 132, 0.6)',
                    'rgba(54, 162, 235, 0.6)',
                    'rgba(255, 206, 86, 0.6)',
                    'rgba(75, 192, 192, 0.6)',
                    'rgba(153, 102, 255, 0.6)',
                    'rgba(255, 159, 64, 0.6)',
                    'rgba(255, 99, 132, 0.6)',
                    'rgba(54, 162, 235, 0.6)',
                    'rgba(255, 206, 86, 0.6)',
                    'rgba(75, 192, 192, 0.6)',
                    'rgba(153, 102, 255, 0.6)'
                ]
            }]
        },
        options:{
            scales: {
                yAxes : [{
                    ticks : {
                        min : 0
                    }
                }]
            }
        }
    });
}

function getStatisticData() {
    $.ajax({
        url: HOST + "/statistic/" + CUSTOMER.country.id,
        type: "GET",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function (data) {
            data = processStatistic(data);
            renderChart(data);
        },
        error: function(xhr, ajaxOptions, thrownError) {
            handleError(xhr, GET);
        }});
}

function processStatistic(data) {

    var result = {};

    var countries = [];
    var values = [];

    for(var i = 0; i < data.length; i++) {
        countries.push(data[i].breed);
        values.push(data[i].count);
    }

    result.countries = countries.slice(0, 10);
    result.values = values.slice(0, 10);
    return result;
}