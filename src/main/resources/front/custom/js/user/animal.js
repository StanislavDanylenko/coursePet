var ANIMAL_LIST = {};
var ANIMAL = {};
var COUNTRIES = {};
var COUNTRIES_LABELS = {};

function renderAnimalList(data) {
    var html = animalCardTemplate(data);
    $('.animal-area').empty().append(html);
}

function getAnimals() {
    $.ajax({
        url: HOST + "/animal/user/" + CUSTOMER.id,
        type: "GET",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function (data) {
            renderAnimalList(data);
            ANIMAL_LIST = data;
        },
        error: function (xhr, ajaxOptions, thrownError) {
            // alert($.i18n._('getCountryListError'));
            alert('error');
        }
    });
}

function getAnimal(id) {
    $.ajax({
        url: HOST + "/animal/full/" + id,
        type: "GET",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function (data) {
            ANIMAL = data;
            fillSmartCard();
            fillDiseases();
            fillGrafts();
            fillSmartDevices();
            getAnimalCountries();
        },
        error: function (xhr, ajaxOptions, thrownError) {
            // alert($.i18n._('getCountryListError'));
            alert('error');
        }
    });
}

function chooseAnimal(e) {
    var animalId = $(e.target).attr('animal-id');
    getAnimal(animalId);
}


function fillSmartCard() {
    //getQR();
    $('#SCID').text(ANIMAL.animal.smartCardId);
}

function getQR() {
    var url = 'https://api.qrserver.com/v1/create-qr-code/?data=' + 'name: ' + ANIMAL.animal.name + '%0d%0a' + 'id: ' + ANIMAL.animal.id;
    $('#QR').attr('src', url);
}

function fillDiseases() {
    var html = diseaseTemplate(ANIMAL.diseases);
    $('.animal-diseases').empty().append(html);
    setDataTable('diseaseTableTemplate');
}

function fillGrafts() {
    var html = graftTemplate(ANIMAL.grafts);
    $('.animal-grafts').empty().append(html);
    setDataTable('graftTableTemplate');
}

function fillSmartDevices() {
    var html = smartDeviceTemplate(ANIMAL.animal.smartDevices);
    $('.animal-sd').empty().append(html);
    setDataTable('smartDeviceTableTemplate');

}

////////////////

function getAnimalCountries() {
    $.ajax({
        url: HOST + "/country/",
        type: "GET",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function (data) {
            COUNTRIES = data;
            getCountriesName();
            setAutocomplete();
        },
        error: function (xhr, ajaxOptions, thrownError) {
            // alert($.i18n._('getCountryListError'));
            alert('error');
        }
    });
}

function getCountriesName() {
    COUNTRIES_LABELS = [];
    for(var i = 0; i < COUNTRIES.length; i++) {
        COUNTRIES_LABELS.push(COUNTRIES[i].name);
    }
}

function setAutocomplete() {
    $( "#countriesAutocomplete" ).autocomplete({
        source: COUNTRIES_LABELS
    });
}

