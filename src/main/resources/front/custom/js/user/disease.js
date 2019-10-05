
function fillDiseases() {

    for(var i = 0; i < ANIMAL.diseases.length; i++) {
        ANIMAL.diseases[i].name = ANIMAL.diseases[i].disease.name;
    }

    var html = diseaseTemplate(ANIMAL.diseases);
    $('.animal-diseases').empty().append(html);
    setDataTable('diseaseTableTemplate');

    setTranslationUser();
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
            handleError(xhr, GET);
        }
    });
}

function deleteAnimalDisease(e) {

    var id = getID(e, '#diseaseTableTemplate');

    $.ajax({
        url: HOST + "/animalDisease/" + id,
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

function saveAnimalDisease() {

    validateDisease();

    var disease = {
        animalId: ANIMAL.animal.id,
        diseaseId: $('#animalDisease').val(),
        treatment: $('#animalTreathment').val(),
        startData: $('#animalDiseaseStartDate').val(),
        endDate: $('#animalDiseaseFinishDate').val()
    };

        if (!$('#diseaseForm').valid()) {
            return;
        }

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
            handleSuccessOperation(CREATED);
        },
        error: function (data) {
            handleError(xhr, CREATE);
        }
    });
    $('#diseaseModal').find('input, select').val('');
}

/////
function validateDisease() {

    $("label.error").remove();
    $(".error").removeClass("error");

    $('#diseaseForm').validate({
        rules: {
            animalDiseaseStartDate: {
                required: true,
                startDate: true,
                date: true,
            },
            animalDiseaseFinishDate: {
                required: true,
                finishDate: true,
                date: true,
            },
            animalTreathment: {
                required: true,
            }
        },
        messages: {
            animalDiseaseStartDate: {
                required: $.i18n._('requiredField'),
                date: $.i18n._('date'),
                startDate: $.i18n._('startDateV')
            },
            animalDiseaseFinishDate: {
                required: $.i18n._('requiredField'),
                date: $.i18n._('date'),
                finishDate: $.i18n._('finishDateV')
            },
            animalTreathment: {
                required: $.i18n._('requiredField'),
            }
        }
    });

}

/////////

function getEpidemicData() {
    $.ajax({
        url: HOST + "/statistic/epidemic",
        type: "GET",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function (data) {
            var html = epidemicTemplate(data);
            $(".epidemicContainer").empty().append(html);
            setTranslationUser();
        },
        error: function (xhr, ajaxOptions, thrownError) {
            handleError(xhr, GET);
        }
    });
}