$(document).ready(function() {

    $(document).on('click', '.log-out', logout);

    $(window).on('hashchange', function () {
        checkHash();
    });

    checkHash();

});

function checkHash() {
    switch (window.location.hash) {
        case "#country": alert('hashchange');
            break;
        /*case "#history": getOldOrders();
            break;
        case "#canceled": getCanceledOrders();
            break;
        case "#profile": getOrdinalUser();
            break;
        case "#statistic": getStatistic();
            break;
        case "#home": renderHome();
            break;
        default: renderHome();*/
    }
}