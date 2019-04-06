var countryTableTemplate;

$(document).ready(function() {

    setUpUserWorkspace();

    $(document).on('click', '.log-out', logout);

    $(window).on('hashchange', function () {
        checkHash();
    });

    checkHash();

    loadProfile();
    loadCountries();

});

function checkHash() {
    switch (window.location.hash) {
        case "#country": getCountries();
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

//////////////////////////////

function loadCountries() {
    countryTableTemplate = Handlebars.compile($('#countryTemplate').html());

    $(document).on('click', '.add-country', createCountry);
    $(document).on('click', '.edit-country', editCountry);
    $(document).on('click', '.delete-country', deleteCountry);
}

function setUpUserWorkspace() {
    try{
        loadUserLS();
    } catch (e) {
        window.location = 'login.html';
    }
    loadLocale();
}
