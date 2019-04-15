var ANIMAL_LIST = {};
var ANIMAL = {};
var COUNTRIES = {};
var COUNTRIES_LABELS = {};
var BREEDS = {};

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
    getAnimalBreeds();
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
            fillAnimalInfo();
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
            Swal.fire(
                'SUCCES!',
                'Deleted!',
                'success'
            )
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

function addAnimal() {
    $('.animalCreateModal')[0].click();
}

function saveAnimal() {

    var animal = {
        name: $('#animalCreateName').val(),
        gender: getAnimalGender(),
        weight: $('#animalCreateWeight').val(),
        height: $('#animalCreateHeight').val(),
        length: $('#animalCreateLength').val(),
        animalsBreedId: $('#animalsBreed').val(),
        userId: USER.id,
        birthDate: $('#animalCreateBirth').val()
    };

    /*    if (!$('#countryForm').valid()) {
            return;
        }*/

    $.ajax({
        url: HOST + "/animal",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(animal),
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function (data) {
            $("[data-dismiss=modal]").trigger({type: "click"});
            getAnimals();
            Swal.fire(
                'SUCCES!',
                'Saved!',
                'success'
            )
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
    $('#animalCreateModal').find('input, select').val('');
}

function updateAnimal() {

    var animal = {
        weight: $('#animalUpdateWeight').val(),
        height: $('#animalUpdateHeight').val(),
        length: $('#animalUpdateLength').val()
    };

    /*    if (!$('#countryForm').valid()) {
            return;
        }*/

    $.ajax({
        url: HOST + "/animal/" + ANIMAL.animal.id,
        type: "PUT",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(animal),
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function (data) {
            $("[data-dismiss=modal]").trigger({type: "click"});
            getAnimal(ANIMAL.animal.id);
            Swal.fire(
                'SUCCES!',
                'Updated!',
                'success'
            )
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
    $('#animalUpdateModal').find('input, select').val('');
}

function fillUpdateModal() {
    $('#animalUpdateWeight').val(ANIMAL.animal.weight);
    $('#animalUpdateHeight').val(ANIMAL.animal.height);
    $('#animalUpdateLength').val(ANIMAL.animal.length);
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
    getQR();
    $('#SCID').text(ANIMAL.animal.smartCardId);
}

function getQR() {
    var url = 'https://api.qrserver.com/v1/create-qr-code/?data=' + 'name: ' + ANIMAL.animal.name + '%0d%0a' + 'id: ' + ANIMAL.animal.id;
    $('#QR').attr('src', url);
}

function fillDiseases() {

    for(var i = 0; i < ANIMAL.diseases.length; i++) {
        ANIMAL.diseases[i].name = ANIMAL.diseases[i].disease.name;
        ANIMAL.diseases[i].id = ANIMAL.diseases[i].disease.id;
    }

    var html = diseaseTemplate(ANIMAL.diseases);
    $('.animal-diseases').empty().append(html);
    setDataTable('diseaseTableTemplate');
}

function fillGrafts() {

    for(var i = 0; i < ANIMAL.grafts.length; i++) {
        ANIMAL.grafts[i].name = ANIMAL.grafts[i].graft.name;
        ANIMAL.grafts[i].id = ANIMAL.grafts[i].graft.id;
        ANIMAL.grafts[i].frequency = ANIMAL.grafts[i].graft.frequency;
    }

    var html = graftTemplate(ANIMAL.grafts);
    $('.animal-grafts').empty().append(html);
    setDataTable('graftTableTemplate');
}

function fillSmartDevices() {
    var html = smartDeviceTemplate(ANIMAL.animal.smartDevices);
    $('.animal-sd').empty().append(html);
    setDataTable('smartDeviceTableTemplate');

}

function fillAnimalInfo() {
    $('#animalName').val(ANIMAL.animal.name);
    $('#animalGender').val(ANIMAL.animal.gender);
    $('#animalBirth').val(ANIMAL.animal.birthDate);
    $('#animalWeight').val(ANIMAL.animal.weight);
    $('#animalHeight').val(ANIMAL.animal.height);
    $('#animalLength').val(ANIMAL.animal.length);
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
            Swal.fire(
                'SUCCESS!',
                'Deleted',
                'success'
            )
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
            Swal.fire(
                'SUCCESS!',
                'Saved!',
                'success'
            )
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
    $('#sdModal').find('input, select').val('');
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

/////////////////

function getAnimalBreeds() {
    $.ajax({
        url: HOST + "/animalBreed/",
        type: "GET",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function (data) {
            BREEDS = data;
            $("#animalsBreed").empty();
            for (var i = 0; i < data.length; i++) {
                $("#animalsBreed").append(new Option(data[i].name, data[i].id));
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
            // alert($.i18n._('getCountryListError'));
            alert('error');
        }
    });
}

function getAnimalDiseases() {
    $.ajax({
        url: HOST + "/disease/",
        type: "GET",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function (data) {
            $("#animalDisease").empty();
            for (var i = 0; i < data.length; i++) {
                $("#animalDisease").append(new Option(data[i].name, data[i].id));
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
            // alert($.i18n._('getCountryListError'));
            alert('error');
        }
    });
}

function getAnimalGrafts() {
    $.ajax({
        url: HOST + "/graft/",
        type: "GET",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function (data) {
            $("#animalGraft").empty();
            for (var i = 0; i < data.length; i++) {
                $("#animalGraft").append(new Option(data[i].name, data[i].id));
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
            // alert($.i18n._('getCountryListError'));
            alert('error');
        }
    });
}

////////

function deleteAnimalDisease(e) {

    var id = getID(e, '#diseaseTableTemplate');

    $.ajax({
        url: HOST + "/animalDisease/animal/" +  ANIMAL.animal.id + "/disease/" + id,
        type: "DELETE",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function () {
            getAnimal(ANIMAL.animal.id);
            Swal.fire(
                'Success!',
                'Was deleted',
                'success'
            )
        },
        error: function (xhr, ajaxOptions, thrownError) {
            // alert($.i18n._('deleteDiseaseError'));
            Swal.fire(
                'BAD!',
                'Error while deleting',
                'error'
            )
        }
    });
}

function deleteAnimalGraft(e) {

    var id = getID(e, '#graftTableTemplate');

    $.ajax({
        url: HOST + "/animalGraft/animal/" + ANIMAL.animal.id + "/graft/" + id,
        type: "DELETE",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function () {
            getAnimal(ANIMAL.animal.id);
            Swal.fire(
                'Success!',
                'Was deleted',
                'success'
            )
        },
        error: function (xhr, ajaxOptions, thrownError) {
            // alert($.i18n._('deleteGraftError'));
            Swal.fire(
                'BAD!',
                'Error while deleting',
                'error'
            )
        }
    });
}

/////////////////

function saveAnimalGraft() {

    var graft = {
        animalId: ANIMAL.animal.id,
        graftId: $('#animalGraft').val(),
        date: $('#animalGraftDate').val()
    };

    /*    if (!$('#graftForm').valid()) {
            return;
        }*/

    $.ajax({
        url: HOST + "/animalGraft",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(graft),
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function (data) {
            $("[data-dismiss=modal]").trigger({type: "click"});
            getAnimal(ANIMAL.animal.id);
            Swal.fire(
                'Success!',
                'Was created',
                'success'
            )
        },
        error: function (data) {
            Swal.fire(
                'BAD!',
                'Can not create',
                'error'
            )
            // alert($.i18n._('saveGraftError'));
        }
    });
    $('#graftModal').find('input, select').val('');
}

function saveAnimalDisease() {

    var disease = {
        animalId: ANIMAL.animal.id,
        diseaseId: $('#animalDisease').val(),
        treatment: $('#animalTreathment').val(),
        startData: $('#animalDiseaseStartDate').val(),
        endDate: $('#animalDiseaseFinishDate').val()
    };

    /*    if (!$('#diseaseForm').valid()) {
            return;
        }*/

    $.ajax({
        url: HOST + "/animalDisease",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(disease),
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function (data) {
            $("[data-dismiss=modal]").trigger({type: "click"});
            getAnimal(ANIMAL.animal.id);
            Swal.fire(
                'Success!',
                'Was created',
                'success'
            )
        },
        error: function (data) {
            Swal.fire(
                'BAD!',
                'Can not create',
                'error'
            )
            // alert($.i18n._('saveDiseaseError'));
        }
    });
    $('#diseaseModal').find('input, select').val('');
}
/////////

function getAnimalGender() {
    if($('#animalCreateGenderMale').is(":checked")){
        return 'MALE';
    }
    return 'FEMALE';
}