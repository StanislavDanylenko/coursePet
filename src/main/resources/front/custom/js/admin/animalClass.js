var isUpdate = false;

function renderAnimalClassList(response) {
    var html = animalClassTableTemplate(response);
    $(MAIN_CONTAINER).empty().append(html);
    setDataTable('animalClassTableTemplate');
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
            // alert($.i18n._('getAnimalClassListError'));
            alert('error');
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
            $('#animalClassFrequency').val(data.frequency);
        },
        error: function (xhr, ajaxOptions, thrownError) {
            // alert($.i18n._('getAnimalClassError'));
            alert('error');
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
}

function editAnimalClass(e) {

    $('.add-animalClass')[0].click();

    var id = getID(e, '#animalClassTableTemplate');
    var button = $('.action-animalClass');
    button.unbind();
    button.bind('click', updateAnimalClass);
    // $('#animalClassOperation')._t('editAnimalClass');
    getAnimalClass(id);
}


function saveAnimalClass() {

    var animalClass = {
        name: $('#animalClassName').val()
    };

    /*    if (!$('#animalClassForm').valid()) {
            return;
        }*/

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
            // alert($.i18n._('saveAnimalClassError'));
        }
    });
    $('#animalClassModal').find('input, select').val('');
}

function updateAnimalClass(animalClass) {
    animalClass = {
        id: $('#animalClassId').val(),
        name: $('#animalClassName').val()
    };

    /*if (!$('#animalClassForm').valid()) {
        return;
    }*/

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
            // alert($.i18n._('updateAnimalClassError'));
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
            Swal.fire(
                'Success!',
                'Was deleted',
                'success'
            )
        },
        error: function (xhr, ajaxOptions, thrownError) {
            // alert($.i18n._('deleteAnimalClassError'));
            Swal.fire(
                'BAD!',
                'Error while deleting',
                'error'
            )
        }
    });
}
