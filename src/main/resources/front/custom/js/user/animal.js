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
            fillRecords();
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
    validateAnimal();
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

    if (!$('#animalForm').valid()) {
        return;
    }

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

       /* if (!$('#animalForm').valid()) {
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

function fillAnimalUpdateModal() {
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

//////////

function fillAnimalInfo() {
    $('#animalName').val(ANIMAL.animal.name);
    $('#animalGender').val(ANIMAL.animal.gender);
    $('#animalBirth').val(ANIMAL.animal.birthDate);
    $('#animalWeight').val(ANIMAL.animal.weight);
    $('#animalHeight').val(ANIMAL.animal.height);
    $('#animalLength').val(ANIMAL.animal.length);

    if (ANIMAL.animal.photoURL) {
        $('#animal-img').attr('src', ANIMAL.animal.photoURL);
    } else {
        $('#animal-img').attr('src', '../custom/img/default_animal.jpg');
    }
}

///////
function getAnimalGender() {
    if ($('#animalCreateGenderMale').is(":checked")) {
        return 'MALE';
    }
    return 'FEMALE';
}

/////

function validateAnimal() {

    $("label.error").remove();
    $(".error").removeClass("error");

    $('#animalForm').validate({
        rules: {
            animalCreateName: {
                required: true
            },
            animalCreateWeight: {
                required: true,
                number: true,
                min: 0.1
            },
            animalCreateHeight: {
                required: true,
                number: true,
                min: 0.1
            },
            animalCreateLength: {
                required: true,
                number: true,
                min: 0.1
            },
            animalCreateBirth: {
                required: true,
                notFutureDate: true
            }
        },
        messages: {
            animalCreateName: {
                required: "required field"
            }
        }
    });

}