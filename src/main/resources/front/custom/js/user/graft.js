
function fillGrafts() {

    for(var i = 0; i < ANIMAL.grafts.length; i++) {
        ANIMAL.grafts[i].name = ANIMAL.grafts[i].graft.name;
        ANIMAL.grafts[i].frequency = ANIMAL.grafts[i].graft.frequency;
    }

    var html = graftTemplate(ANIMAL.grafts);
    $('.animal-grafts').empty().append(html);
    setDataTable('graftTableTemplate');

    setTranslationUser();
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
            handleError(xhr, GET);
        }
    });
}

function deleteAnimalGraft(e) {

    var id = getID(e, '#graftTableTemplate');

    $.ajax({
        url: HOST + "/animalGraft/" + id,
        type: "DELETE",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function () {
            getAnimal(ANIMAL.animal.id);
            handleSuccessOperation(DELETED);
        },
        error: function (xhr, ajaxOptions, thrownError) {
            handleError(xhr, DELETE);
        }
    });
}

function saveAnimalGraft() {

    validateGraft();

    var graft = {
        animalId: ANIMAL.animal.id,
        graftId: $('#animalGraft').val(),
        date: $('#animalGraftDate').val()
    };

    if (!$('#graftForm').valid()) {
        return;
    }

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
            handleSuccessOperation(CREATED);
        },
        error: function (data) {
            handleError(xhr, CREATE);
        }
    });
    $('#graftModal').find('input, select').val('');
}

///////
function validateGraft() {

    $("label.error").remove();
    $(".error").removeClass("error");

    $('#graftForm').validate({
        rules: {
            animalGraftDate: {
                required: true,
                date: true,
            }
        },
        messages: {
            animalGraftDate: {
                required: $.i18n._('requiredField'),
                date: $.i18n._('date'),
            }
        }
    });

}