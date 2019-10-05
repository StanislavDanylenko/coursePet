var isUpdate = false;

function renderAnimalBreedList(response) {
    var html = animalBreedTableTemplate(response);
    $(MAIN_CONTAINER).empty().append(html);
    setDataTable('animalBreedTableTemplate');
    setTranslateAdmin();
}

function getAnimalBreeds() {
    $.ajax({
        url: HOST + "/animalBreed",
        type: "GET",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function (data) {
            data = processAnimalBreed(data);
            renderAnimalBreedList(data);
            getAnimalClassesForDD();
        },
        error: function (xhr, ajaxOptions, thrownError) {
            handleError(xhr, GET);
        }
    });
}

function getAnimalBreed(id) {
    console.log('in the get animalBreed method, id = ' + id);
    $.ajax({
        url: HOST + "/animalBreed/" + id,
        type: "GET",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function (data) {
            $('#animalBreedId').val(data.id);
            $('#animalBreedName').val(data.name);
            $('#animalBreedClassName').val(data.animalsClass.id);
        },
        error: function (xhr, ajaxOptions, thrownError) {
            handleError(xhr, GET);
        }
    });
}


function createAnimalBreed() {
    if (!isUpdate) {
        var button = $('.action-animalBreed');
        button.unbind();
        button.bind('click', saveAnimalBreed);
        // $('#animalBreedOperation')._t('addAnimalBreed');
    }
    validateAnimalBreed();
}

function editAnimalBreed(e) {

    $('.add-animalBreed')[0].click();

    var id = getID(e, '#animalBreedTableTemplate');
    var button = $('.action-animalBreed');
    button.unbind();
    button.bind('click', updateAnimalBreed);
    // $('#animalBreedOperation')._t('editAnimalBreed');
    getAnimalBreed(id);
    validateAnimalBreed();
}


function saveAnimalBreed() {

    var animalBreed = {
        name: $('#animalBreedName').val(),
        animalClassId: $('#animalBreedClassName').val()
    };

    if (!$('#animalBreedForm').valid()) {
        return;
    }

    $.ajax({
        url: HOST + "/animalBreed",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(animalBreed),
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function (data) {
            $("[data-dismiss=modal]").trigger({type: "click"});
            getAnimalBreeds();
            handleSuccessOperation(CREATED);
        },
        error: function (xhr) {
            handleError(xhr, CREATE);
        }
    });
    $('#animalBreedModal').find('input, select').val('');
}

function updateAnimalBreed(animalBreed) {
    animalBreed = {
        id: $('#animalBreedId').val(),
        name: $('#animalBreedName').val(),
        animalClassId: $('#animalBreedClassName').val()
    };

    if (!$('#animalBreedForm').valid()) {
        return;
    }

    $.ajax({
        url: HOST + "/animalBreed/" + animalBreed.id,
        type: "PUT",
        contentType: "application/json",
        data: JSON.stringify(animalBreed),
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function () {
            getAnimalBreeds();
            $("[data-dismiss=modal]").trigger({type: "click"});
            handleSuccessOperation(UPDATED);
        },
        error: function (xhr) {
            handleError(xhr, UPDATE);
        }
    });
    $('#animalBreedModal').find('input, select').val('');
}

function deleteAnimalBreed(e) {

    var id = getID(e, '#animalBreedTableTemplate');

    $.ajax({
        url: HOST + "/animalBreed/" + id,
        type: "DELETE",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function () {
            getAnimalBreeds();
            handleSuccessOperation(DELETED);
        },
        error: function (xhr, ajaxOptions, thrownError) {
            handleError(xhr, DELETE);
        }
    });
}

function processAnimalBreed(data) {

    for (var i = 0; i < data.length; i++) {
        data[i].animalClass = data[i].animalsClass.name;
    }

    return data;
}

/////////////

function getAnimalClassesForDD() {
    $.ajax({
        url: HOST + "/animalClass",
        type: "GET",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function (data) {
            $("#animalBreedClassName").empty();
            for (var i = 0; i < data.length; i++) {
                $("#animalBreedClassName").append(new Option(data[i].name, data[i].id));
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
            handleError(xhr, GET);
        }
    });
}

///////////
function validateAnimalBreed() {

    $("label.error").remove();
    $(".error").removeClass("error");

    $('#animalBreedForm').validate({
        rules: {
            animalBreedName: {
                required: true
            }
        },
        messages: {
            animalBreedName: {
                required: $.i18n._('requiredField')
            }
        }
    });

}

