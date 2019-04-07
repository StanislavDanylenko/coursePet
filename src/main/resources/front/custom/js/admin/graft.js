var isUpdate = false;

function renderGraftList(response) {
    var html = graftTableTemplate(response);
    $(MAIN_CONTAINER).empty().append(html);
    setDataTable('graftTableTemplate');
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
            // alert($.i18n._('getGraftListError'));
            alert('error');
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
            // alert($.i18n._('getGraftError'));
            alert('error');
        }
    });
}


function createGraft() {
    if (!isUpdate) {
        var button = $('.action-graft');
        button.unbind();
        button.bind('click', saveGraft);
        // $('#graftOperation')._t('addGraft');
    }
}

function editGraft(e) {

    $('.add-graft')[0].click();

    var id = getID(e, '#graftTableTemplate');
    var button = $('.action-graft');
    button.unbind();
    button.bind('click', updateGraft);
    // $('#graftOperation')._t('editGraft');
    getGraft(id);
}


function saveGraft() {

    var graft = {
        name: $('#graftName').val(),
        frequency: $('#graftFrequency').val()
    };

    /*    if (!$('#graftForm').valid()) {
            return;
        }*/

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
            // alert($.i18n._('saveGraftError'));
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

    /*if (!$('#graftForm').valid()) {
        return;
    }*/

    $.ajax({
        url: "http://localhost:8080/graft/" + graft.id,
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
            // alert($.i18n._('updateGraftError'));
        }
    });
    $('#graftModal').find('input, select').val('');
}

function deleteGraft(e) {

    var id = getID(e, '#graftTableTemplate');

    $.ajax({
        url: "http://localhost:8080/graft/" + id,
        type: "DELETE",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function () {
            getGrafts();
            Swal.fire(
                'Success!',
                'Was deleted',
                'success'
            )
        },
        error: function (xhr, ajaxOptions, thrownError) {
            // alert($.i18n._('deleteGraftError'));
            Swal.fire(
                'BAD!',
                'Error while deleting',
                'error'
            )
        }
    });
}
