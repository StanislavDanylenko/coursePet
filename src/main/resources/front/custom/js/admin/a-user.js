var isUpdate = false;

function renderUserList(response) {
    var html = userTableTemplate(response);
    $(MAIN_CONTAINER).empty().append(html);
    setDataTable('userTableTemplate');
    setTranslateAdmin();
}

function getUsers() {
    $.ajax({
        url: HOST + "/user",
        type: "GET",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function (data) {
            renderUserList(data);
        },
        error: function (xhr, ajaxOptions, thrownError) {
            handleError(xhr, GET);
        }
    });
}

function createUser() {
    if (!isUpdate) {
        var button = $('.action-user');
        button.unbind();
        button.bind('click', saveUser);
        // $('#userOperation')._t('addUser');
    }
    validateUser();
}

function saveUser() {

    var user = {
        username: $('#username').val(),
        password: $('#password').val(),
        repeatPassword: $('#repeatPassword').val()
    };

    if (!$('#userForm').valid()) {
        return;
    }

    $.ajax({
        url: HOST + "/user",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(user),
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function (data) {
            $("[data-dismiss=modal]").trigger({type: "click"});
            getUsers();
            handleSuccessOperation(CREATED);
        },
        error: function (xhr) {
            handleError(xhr, CREATE);
        }
    });
    $('#userModal').find('input, select').val('');
}

function deleteUser(e) {

    var id = getID(e, '#userTableTemplate');

    $.ajax({
        url: HOST + "/user/" + id,
        type: "DELETE",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function () {
            getUsers();
            handleSuccessOperation(DELETED);
        },
        error: function (xhr, ajaxOptions, thrownError) {
            handleError(xhr, DELETE);
        }
    });
}

//////
function validateUser() {

    $("label.error").remove();
    $(".error").removeClass("error");

    $('#userForm').validate({
        rules: {
            username: {
                required: true
            },
            password: {
                required: true
            },
            repeatPassword: {
                required: true,
                samePassword: true
            }
        },
        messages: {
            username: {
                required: $.i18n._('requiredField')
            },
            password: {
                required: $.i18n._('requiredField')
            },
            repeatPassword: {
                required: $.i18n._('requiredField'),
                samePassword: $.i18n._('samePassword')
            }
        }
    });

}