var isUpdate = false;

function renderDiseaseList(response) {
    var html = diseaseTableTemplate(response);
    $(MAIN_CONTAINER).empty().append(html);
    setDataTable('diseaseTableTemplate');
}

function getDiseases() {
    $.ajax({
        url: HOST + "/disease",
        type: "GET",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function (data) {
            renderDiseaseList(data);
        },
        error: function (xhr, ajaxOptions, thrownError) {
            handleError(xhr, GET);
        }
    });
}

function getDisease(id) {
    console.log('in the get disease method, id = ' + id);
    $.ajax({
        url: HOST + "/disease/" + id,
        type: "GET",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function (data) {
            $('#diseaseId').val(data.id);
            $('#diseaseName').val(data.name);
        },
        error: function (xhr, ajaxOptions, thrownError) {
            handleError(xhr, GET);
        }
    });
}


function createDisease() {
    if (!isUpdate) {
        var button = $('.action-disease');
        button.unbind();
        button.bind('click', saveDisease);
        // $('#diseaseOperation')._t('addDisease');
        validateDisease();
    }
}

function editDisease(e) {

    $('.add-disease')[0].click();

    var id = getID(e, '#diseaseTableTemplate');
    var button = $('.action-disease');
    button.unbind();
    button.bind('click', updateDisease);
    // $('#diseaseOperation')._t('editDisease');
    getDisease(id);
    validateDisease();
}


function saveDisease() {

    var disease = {
        name: $('#diseaseName').val(),
        frequency: $('#diseaseFrequency').val()
    };

    if (!$('#diseaseForm').valid()) {
        return;
    }

    $.ajax({
        url: HOST + "/disease",
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
            getDiseases();
            Swal.fire(
                'Success!',
                'Was created',
                'success'
            )
        },
        error: function (xhr) {
            handleError(xhr, CREATE);
        }
    });
    $('#diseaseModal').find('input, select').val('');
}

function updateDisease(disease) {
    disease = {
        id: $('#diseaseId').val(),
        name: $('#diseaseName').val(),
    };

    if (!$('#diseaseForm').valid()) {
        return;
    }

    $.ajax({
        url: "http://localhost:8080/disease/" + disease.id,
        type: "PUT",
        contentType: "application/json",
        data: JSON.stringify(disease),
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function () {
            getDiseases();
            $("[data-dismiss=modal]").trigger({type: "click"});
            Swal.fire(
                'Success!',
                'Was created',
                'success'
            )
        },
        error: function (xhr) {
            handleError(xhr, UPDATE);
        }
    });
    $('#diseaseModal').find('input, select').val('');
}

function deleteDisease(e) {

    var id = getID(e, '#diseaseTableTemplate');

    $.ajax({
        url: "http://localhost:8080/disease/" + id,
        type: "DELETE",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function () {
            getDiseases();
            Swal.fire(
                'Success!',
                'Was deleted',
                'success'
            )
        },
        error: function (xhr, ajaxOptions, thrownError) {
            handleError(xhr, DELETE);
        }
    });
}

/////////

function validateDisease() {

    $("label.error").remove();
    $(".error").removeClass("error");

    $('#diseaseForm').validate({
        rules: {
            diseaseName: {
                required: true
            }
        },
        messages: {
            diseaseName: {
                required: "required field"
            }
        }
    });

}
