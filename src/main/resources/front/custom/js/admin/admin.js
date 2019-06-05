var countryTableTemplate;
var graftTableTemplate;
var animalClassTableTemplate;
var diseaseTableTemplate;
var userTableTemplate;

$(document).ready(function() {

    setUpUserWorkspace();

    $(document).on('click', '.log-out', logout);

    $(window).on('hashchange', function () {
        checkHash();
    });

    checkHash();

    loadProfileAudit();

    loadCountries();
    loadGrafts();
    loadDiseases();
    loadUsers();
    loadProfile();
    loadAnimalClass();

});

function checkHash() {
    switch (window.location.hash) {
        case "#country": getCountries();
            refreshMenu("#country");
            break;
        case "#graft": getGrafts();
            refreshMenu("#graft");
            break;
        case "#disease": getDiseases();
            refreshMenu("#disease");
            break;
        case "#users": getUsers();
            refreshMenu("#users");
            break;
        case "#animal_class": getAnimalClasses();
            refreshMenu("#animalClass");
            break;
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

function loadDiseases() {
    diseaseTableTemplate = Handlebars.compile($('#diseaseTemplate').html());

    $(document).on('click', '.add-disease', createDisease);
    $(document).on('click', '.edit-disease', editDisease);
    $(document).on('click', '.delete-disease', deleteDisease);
}

function loadUsers() {
    userTableTemplate = Handlebars.compile($('#userTemplate').html());

    $(document).on('click', '.add-user', createUser);
    $(document).on('click', '.delete-user', deleteUser);
}

function loadProfile() {

    $(document).on('click', '.update-password', updateUserPassword);
    $(document).on('click', '.action-profile', updateProfile);
    $(document).on('click', '.profile', getProfileCountries);
}

function loadAnimalClass() {
    animalClassTableTemplate = Handlebars.compile($('#animalClassTemplate').html());

    $(document).on('click', '.add-animalClass', createAnimalClass);
    $(document).on('click', '.edit-animalClass', editAnimalClass);
    $(document).on('click', '.delete-animalClass', deleteAnimalClass);
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
