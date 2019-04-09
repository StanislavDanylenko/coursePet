var animalCardTemplate;
var diseaseTemplate;
var graftTemplate;
var smartDeviceTemplate;
var recordTemplate;
var countryGraftsTemplate;

$(document).ready(function() {

    setUpUserWorkspace();

    $(document).on('click', '.log-out', logout);

    $(window).on('hashchange', function () {
        checkHash();
    });

    checkHash();
    hideAll();

    loadProfileAudit();
    loadProfile();
    loadMenuActions();
    getUser();
    loadAnimal();
    loadSmartDevice();
    loadMap();

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

function loadAnimal() {

    animalCardTemplate = Handlebars.compile($('#animalCardTemplate').html());
    diseaseTemplate = Handlebars.compile($('#diseaseTemplate').html());
    graftTemplate = Handlebars.compile($('#graftTemplate').html());
    smartDeviceTemplate = Handlebars.compile($('#smartDeviceTemplate').html());

    $(document).on('click', '.choose-animal', chooseAnimal);
    $(document).on('click', '.remove-animal', removeAnimal);
    $(document).on('click', '.add-animal', addAnimal);
    $(document).on('click', '.save-animal', saveAnimal);
    $(document).on('click', '.update-animal', updateAnimal);
    $(document).on('click', '.edit-animal', fillUpdateModal);
}

function loadSmartDevice() {

    recordTemplate = Handlebars.compile($('#recordTemplate').html());

    $(document).on('click', '.sd-info', getSmartDeviceInfo);
    $(document).on('click', '.sd-delete', deleteSmartDevice);
    $(document).on('click', '.action-sd', saveSmartDevice);
}

function loadMap() {
    $(document).on('click', '.check-info', checkInfo);
    countryGraftsTemplate = Handlebars.compile($('#countryGraftsTemplate').html());
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
    getStatisticData();
}

function showAnimalList() {
    $('.my-area').hide();
    $('.animal-area').show();
    getAnimals();
}

function showAnimalActions() {
    $('.my-area').hide();
    $('.action-area').show();
}

function hideAll() {
    $('.my-area').hide();
}

function getUser() {
    console.log('in the get profile method, id = ' + USER.id);
    $.ajax({
        url: HOST + "/user/" + USER.id,
        type: "GET",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function (data) {
            CUSTOMER = data;
            getAnimals();
        },
        error: function (xhr, ajaxOptions, thrownError) {
            // alert($.i18n._('getUserError'));
            alert('error');
        }
    });
}