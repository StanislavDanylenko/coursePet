var isUpdate = false;

function renderAnimalBreedList(response) {
    var html = animalBreedTableTemplate(response);
    $(MAIN_CONTAINER).empty().append(html);
    setDataTable('animalBreedTableTemplate');
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
            // alert($.i18n._('getAnimalBreedListError'));
            alert('error');
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
            // alert($.i18n._('getAnimalBreedError'));
            alert('error');
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
}

function editAnimalBreed(e) {

    $('.add-animalBreed')[0].click();

    var id = getID(e, '#animalBreedTableTemplate');
    var button = $('.action-animalBreed');
    button.unbind();
    button.bind('click', updateAnimalBreed);
    // $('#animalBreedOperation')._t('editAnimalBreed');
    getAnimalBreed(id);
}


function saveAnimalBreed() {

    var animalBreed = {
        name: $('#animalBreedName').val(),
        animalClassId: $('#animalBreedClassName').val()
    };

    /*    if (!$('#animalBreedForm').valid()) {
            return;
        }*/

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
            // alert($.i18n._('saveAnimalBreedError'));
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

    /*if (!$('#animalBreedForm').valid()) {
        return;
    }*/

    $.ajax({
        url: "http://localhost:8080/animalBreed/" + animalBreed.id,
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
            // alert($.i18n._('updateAnimalBreedError'));
        }
    });
    $('#animalBreedModal').find('input, select').val('');
}

function deleteAnimalBreed(e) {

    var id = getID(e, '#animalBreedTableTemplate');

    $.ajax({
        url: "http://localhost:8080/animalBreed/" + id,
        type: "DELETE",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function () {
            getAnimalBreeds();
            Swal.fire(
                'Success!',
                'Was deleted',
                'success'
            )
        },
        error: function (xhr, ajaxOptions, thrownError) {
            // alert($.i18n._('deleteAnimalBreedError'));
            Swal.fire(
                'BAD!',
                'Error while deleting',
                'error'
            )
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
            // alert($.i18n._('getCountryListError'));
            alert('error');
        }
    });
}

