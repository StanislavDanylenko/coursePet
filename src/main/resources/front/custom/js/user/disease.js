
function fillDiseases() {

    for(var i = 0; i < ANIMAL.diseases.length; i++) {
        ANIMAL.diseases[i].name = ANIMAL.diseases[i].disease.name;
    }

    var html = diseaseTemplate(ANIMAL.diseases);
    $('.animal-diseases').empty().append(html);
    setDataTable('diseaseTableTemplate');
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