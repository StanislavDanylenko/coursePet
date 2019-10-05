var isUpdate = false;

function renderGraftList(response) {
    var html = graftTableTemplate(response);
    $(MAIN_CONTAINER).empty().append(html);
    setDataTable('graftTableTemplate');
    setTranslateAdmin();
}

function getGrafts() {
    $.ajax({
        url: HOST + "/graft",
        type: "GET",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function (data) {
            renderGraftList(data);
        },
        error: function (xhr, ajaxOptions, thrownError) {
            handleError(xhr, GET);

        }
    });
}

function getGraft(id) {
    console.log('in the get graft method, id = ' + id);
    $.ajax({
        url: HOST + "/graft/" + id,
        type: "GET",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function (data) {
            $('#graftId').val(data.id);
            $('#graftName').val(data.name);
            $('#graftFrequency').val(data.frequency);
        },
        error: function (xhr, ajaxOptions, thrownError) {
            handleError(xhr, GET);

        }
    });
}


function createGraft() {
    if (!isUpdate) {
        var button = $('.action-graft');
        button.unbind();
        button.bind('click', saveGraft);
        // $('#graftOperation')._t('addGraft');
        validateGraft();
    }
}

function editGraft(e) {

    $('.add-graft')[0].click();

    var id = getID(e, '#graftTableTemplate');
    var button = $('.action-graft');
    button.unbind();
    button.bind('click', updateGraft);
    getGraft(id);
    validateGraft();
}


function saveGraft() {

    var graft = {
        name: $('#graftName').val(),
        frequency: $('#graftFrequency').val()
    };

    if (!$('#graftForm').valid()) {
        return;
    }

    $.ajax({
        url: HOST + "/graft",
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
            getGrafts();
            handleSuccessOperation(CREATED);
        },
        error: function (xhr) {
            handleError(xhr, CREATE);
        }
    });
    $('#graftModal').find('input, select').val('');
}

function updateGraft(graft) {
    graft = {
        id: $('#graftId').val(),
        name: $('#graftName').val(),
        frequency: $('#graftFrequency').val()
    };

    if (!$('#graftForm').valid()) {
        return;
    }

    $.ajax({
        url: HOST + "/graft/" + graft.id,
        type: "PUT",
        contentType: "application/json",
        data: JSON.stringify(graft),
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function () {
            getGrafts();
            $("[data-dismiss=modal]").trigger({type: "click"});
            handleSuccessOperation(UPDATED);
        },
        error: function (xhr) {
            handleError(xhr, UPDATE);
        }
    });
    $('#graftModal').find('input, select').val('');
}

function deleteGraft(e) {

    var id = getID(e, '#graftTableTemplate');

    $.ajax({
        url: HOST + "/graft/" + id,
        type: "DELETE",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function () {
            getGrafts();
            handleSuccessOperation(DELETED);
        },
        error: function (xhr, ajaxOptions, thrownError) {
            handleError(xhr, DELETE);
        }
    });
}


//////////
function validateGraft() {

    $("label.error").remove();
    $(".error").removeClass("error");

    $('#graftForm').validate({
        rules: {
            graftName: {
                required: true
            },
            graftFrequency: {
                required: true
            }
        },
        messages: {
            graftName: {
                required: $.i18n._('requiredField')
            },
            graftFrequency: {
                required: $.i18n._('requiredField')
            }
        }
    });

}
