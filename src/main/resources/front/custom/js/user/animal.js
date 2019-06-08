var ANIMAL_LIST = {};
var ANIMAL = {};

var COUNTRIES = {};
var COUNTRIES_LABELS = {};

var BREEDS = {};

function renderAnimalList(data) {
    var html = animalCardTemplate(data);
    $('.animal-area').empty().append(html);
    setTranslationUser();
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
            handleError(xhr, GET);
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
            handleError(xhr, GET);
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
            handleSuccessOperation(DELETED);
        },
        error: function (xhr, ajaxOptions, thrownError) {
            handleError(xhr, DELETE);
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
            handleSuccessOperation(CREATED);
        },
        error: function (xhr) {
            handleError(xhr, CREATE);
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

    if (!$('#updateAnimalForm').valid()) {
        return;
    }

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
            handleSuccessOperation(UPDATED);
        },
        error: function (xhr) {
            handleError(xhr, UPDATE);
        }
    });
    $('#animalUpdateModal').find('input, select').val('');
}

function fillAnimalUpdateModal() {
    $('#animalUpdateWeight').val(ANIMAL.animal.weight);
    $('#animalUpdateHeight').val(ANIMAL.animal.height);
    $('#animalUpdateLength').val(ANIMAL.animal.length);
    validateUpdateAnimal();
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
    $('#animalClass').val(ANIMAL.animal.animalsBreed.animalsClass.name);
    $('#animalBreed').val(ANIMAL.animal.animalsBreed.name);

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
                required: $.i18n._('requiredField')
            },
            animalCreateWeight: {
                required: $.i18n._('requiredField'),
                number: $.i18n._('number'),
                min: $.i18n._('min')
            },
            animalCreateHeight: {
                required: $.i18n._('requiredField'),
                number: $.i18n._('number'),
                min: $.i18n._('min')
            },
            animalCreateLength: {
                required: $.i18n._('requiredField'),
                number: $.i18n._('number'),
                min: $.i18n._('min')
            },
            animalCreateBirth: {
                required: $.i18n._('requiredField')
            }
        }
    });

}

function validateUpdateAnimal() {

    $("label.error").remove();
    $(".error").removeClass("error");

    $('#updateAnimalForm').validate({
        rules: {
            animalUpdateWeight: {
                required: true,
                number: true,
                min: 0.1
            },
            animalUpdateHeight: {
                required: true,
                number: true,
                min: 0.1
            },
            animalUpdateLength: {
                required: true,
                number: true,
                min: 0.1
            }
        },
        messages: {
            animalUpdateWeight: {
                required: $.i18n._('requiredField'),
                number: $.i18n._('number'),
                min: $.i18n._('min')
            },
            animalUpdateHeight: {
                required: $.i18n._('requiredField'),
                number: $.i18n._('number'),
                min: $.i18n._('min')
            },
            animalUpdateLength: {
                required: $.i18n._('requiredField'),
                number: $.i18n._('number'),
                min: $.i18n._('min')
            }
        }
    });

}