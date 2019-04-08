$(document).ready(function() {

    setUpUserWorkspace();

    $(document).on('click', '.log-out', logout);

    $(window).on('hashchange', function () {
        checkHash();
    });

    checkHash();

    loadProfileAudit();
    loadProfile();
    loadMenuActions();

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
            break;*/
        default: renderHome();
    }
}

function refreshMenu(id) {
    $('.metismenu li').removeClass('active');
    $(id).addClass('active');
}

function loadMenuActions() {

    $(document).on('click', '.animals-button', showAnimalList);
    $(document).on('click', '.statistic-button', showStatistic);
    $(document).on('click', '.actions-button', showAnimalActions);
}

function loadProfile() {

    $(document).on('click', '.update-password', updateUserPassword);
    $(document).on('click', '.action-profile', updateProfile);
    $(document).on('click', '.profile', getProfileCountries);
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
    //$(MAIN_CONTAINER).empty().append('<h1> USER WORKSPACE HERE</h1>');
}

function showStatistic() {
    $('.my-area').hide();
    $('.statistic-area').show();
}

function showAnimalList() {
    $('.my-area').hide();
    $('.animal-area').show();
}

function showAnimalActions() {
    $('.my-area').hide();
    $('.action-area').show();
}