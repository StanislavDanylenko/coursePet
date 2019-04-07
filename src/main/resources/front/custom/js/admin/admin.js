var countryTableTemplate;
var graftTableTemplate;

$(document).ready(function() {

    setUpUserWorkspace();

    $(document).on('click', '.log-out', logout);

    $(window).on('hashchange', function () {
        checkHash();
    });

    checkHash();

    loadProfile();

    loadCountries();
    loadGrafts();

});

function checkHash() {
    switch (window.location.hash) {
        case "#country": getCountries();
            refreshMenu("#country");
            break;
        case "#graft": getGrafts();
            refreshMenu("#graft");
            break;
        /*case "#canceled": getCanceledOrders();
            break;
        case "#profile": getOrdinalUser();
            break;
        case "#statistic": getStatistic();
            break;
        case "#home": renderHome();
            break;*/
        default: renderHome();
    }
}

function refreshMenu(id) {
    $('.metismenu li').removeClass('active');
    $(id).addClass('active');
}

//////////////////////////////

function loadCountries() {
    countryTableTemplate = Handlebars.compile($('#countryTemplate').html());

    $(document).on('click', '.add-country', createCountry);
    $(document).on('click', '.edit-country', editCountry);
    $(document).on('click', '.delete-country', deleteCountry);
}

function loadGrafts() {
    graftTableTemplate = Handlebars.compile($('#graftTemplate').html());

    $(document).on('click', '.add-graft', createGraft);
    $(document).on('click', '.edit-graft', editGraft);
    $(document).on('click', '.delete-graft', deleteGraft);
}

function setUpUserWorkspace() {
    try{
        loadUserLS();
    } catch (e) {
        window.location = 'login.html';
    }
    loadLocale();
}

function renderHome() {
    $(MAIN_CONTAINER).empty().append('<h1> USER WORKSPACE HERE</h1>');

}
