var isUpdate = false;

function renderAnimalClassList(response) {
    var html = animalClassTableTemplate(response);
    $(MAIN_CONTAINER).empty().append(html);
    setDataTable('animalClassTableTemplate');
    setTranslateUser();
}

function getAnimalClasses() {
    $.ajax({
        url: HOST + "/animalClass",
        type: "GET",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function (data) {
            renderAnimalClassList(data);
        },
        error: function (xhr, ajaxOptions, thrownError) {
            handleError(xhr, GET);
        }
    });
}

function getAnimalClass(id) {
    console.log('in the get animalClass method, id = ' + id);
    $.ajax({
        url: HOST + "/animalClass/" + id,
        type: "GET",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function (data) {
            $('#animalClassId').val(data.id);
            $('#animalClassName').val(data.name);
        },
        error: function (xhr, ajaxOptions, thrownError) {
            handleError(xhr, GET);
        }
    });
}


function createAnimalClass() {
    if (!isUpdate) {
        var button = $('.action-animalClass');
        button.unbind();
        button.bind('click', saveAnimalClass);
        // $('#animalClassOperation')._t('addAnimalClass');
    }
    validateAnimalClass();
}

function editAnimalClass(e) {

    $('.add-animalClass')[0].click();

    var id = getID(e, '#animalClassTableTemplate');
    var button = $('.action-animalClass');
    button.unbind();
    button.bind('click', updateAnimalClass);
    // $('#animalClassOperation')._t('editAnimalClass');
    getAnimalClass(id);
    validateAnimalClass();
}


function saveAnimalClass() {

    var animalClass = {
        name: $('#animalClassName').val()
    };

    if (!$('#animalClassForm').valid()) {
        return;
    }

    $.ajax({
        url: HOST + "/animalClass",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(animalClass),
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function (data) {
            $("[data-dismiss=modal]").trigger({type: "click"});
            getAnimalClasses();
            handleSuccessOperation(CREATED);
        },
        error: function (xhr) {
            handleError(xhr, CREATE);
        }
    });
    $('#animalClassModal').find('input, select').val('');
}

function updateAnimalClass(animalClass) {
    animalClass = {
        id: $('#animalClassId').val(),
        name: $('#animalClassName').val()
    };

    if (!$('#animalClassForm').valid()) {
        return;
    }

    $.ajax({
        url: "http://localhost:8080/animalClass/" + animalClass.id,
        type: "PUT",
        contentType: "application/json",
        data: JSON.stringify(animalClass),
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function () {
            getAnimalClasses();
            $("[data-dismiss=modal]").trigger({type: "click"});
            handleSuccessOperation(UPDATED);
        },
        error: function (xhr) {
            handleError(xhr, UPDATE);
        }
    });
    $('#animalClassModal').find('input, select').val('');
}

function deleteAnimalClass(e) {

    var id = getID(e, '#animalClassTableTemplate');

    $.ajax({
        url: "http://localhost:8080/animalClass/" + id,
        type: "DELETE",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function () {
            getAnimalClasses();
            handleSuccessOperation(DELETED);
        },
        error: function (xhr, ajaxOptions, thrownError) {
            handleError(xhr, DELETE);
        }
    });
}

/////

function validateAnimalClass() {

    $("label.error").remove();
    $(".error").removeClass("error");

    $('#animalClassForm').validate({
        rules: {
            animalClassName: {
                required: true
            }
        },
        messages: {
            animalClassName: {
                required: $.i18n._('requiredField')
            }
        }
    });

}
