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
            $('.my-area').hide();
            $('.animal-area').show();
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

function deleteAnimal(id) {

    $.ajax({
        url: HOST + "/animal/" + id,
        type: "DELETE",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function () {
           getAnimals();
        },
        error: function (xhr, ajaxOptions, thrownError) {
            // alert($.i18n._('deleteCountryError'));
            Swal.fire(
                'BAD!',
                'Error while deleting',
                'error'
            )
        }
    });
}

function chooseAnimal(e) {
    var animalId = $(e.target).attr('animal-id');
    getAnimal(animalId);
}

function removeAnimal(e) {
    var animalId = $(e.target).attr('animal-id');
    deleteAnimal(animalId);
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

//////////

function getSmartDeviceInfo(e) {
    var sdId = $(e.target).attr('sd-id');

    var data = ANIMAL.animal.smartDevices;
    var device = {};

    for(var i = 0; i < data.length; i++) {
        if(data[i].id == sdId) {
            device = data[i];
            break;
        }
    }
    var html = recordTemplate(device.records);
    $('.recordTableContainer').empty().append(html);
    setDataTable('recordTableTemplate');

    $('.record-window')[0].click();
}

function deleteSmartDevice(e) {

    var id = getID(e, '#smartDeviceTableTemplate');

    $.ajax({
        url: HOST + "/smartDevice/" + id,
        type: "DELETE",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function () {
            getAnimal(ANIMAL.animal.id);
        },
        error: function (xhr, ajaxOptions, thrownError) {
            // alert($.i18n._('deleteCountryError'));
            Swal.fire(
                'BAD!',
                'Error while deleting',
                'error'
            )
        }
    });
}

function saveSmartDevice() {

    var smartDevice = {
        name: $('#sdName').val(),
        mac: $('#sdMac').val(),
        animalId: ANIMAL.animal.id,
        batteryLevel: 100
    };

    /*    if (!$('#countryForm').valid()) {
            return;
        }*/

    $.ajax({
        url: HOST + "/smartDevice",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(smartDevice),
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function (data) {
            $("[data-dismiss=modal]").trigger({type: "click"});
            getAnimal(ANIMAL.animal.id);
        },
        error: function (data) {
            Swal.fire(
                'BAD!',
                'Can not create',
                'error'
            )
            // alert($.i18n._('saveCountryError'));
        }
    });

}

///////////////
function checkInfo(name) {
    if(name === undefined || name.type == 'click') {
        name = $('#countriesAutocomplete').val();
    }

    for(var i = 0; i < COUNTRIES.length; i++) {
        if(COUNTRIES[i].name === name) {
            getAnimalAvailability(COUNTRIES[i].id);
            break;
        }
    }

    Swal.fire(
        'INFO',
        'We have no info for you',
        'info'
    )

}

function getAnimalAvailability(countryId) {
    $.ajax({
        url: HOST + "/animal/country/" + countryId + "/animal/" + ANIMAL.animal.id,
        type: "GET",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function (data) {
            if(data.isAvailable == true) {
                Swal.fire(
                    'ALL RIGHT',
                    'You may go here',
                    'success'
                )
            } else {
                var html = getHtmlForNoAlert(data.grafts);
                Swal.fire({
                    title: '<strong>NO YOU CAN`T</strong>',
                    type: 'error',
                    html: html
                })
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
            // alert($.i18n._('getCountryListError'));
            alert('error');
        }
    });
}

function getHtmlForNoAlert(grafts) {
    var html = countryGraftsTemplate(grafts);
    return html;
}