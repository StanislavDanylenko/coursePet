var isUpdate = false;

function renderUserList(response) {
    var html = userTableTemplate(response);
    $(MAIN_CONTAINER).empty().append(html);
    setDataTable('userTableTemplate');
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
            // alert($.i18n._('getUserListError'));
            // alert('error');
            handle403(xhr);
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
}

function saveUser() {

    var user = {
        username: $('#username').val(),
        password: $('#password').val(),
        repeatPassword: $('#repeatPassword').val()
    };

    /*    if (!$('#userForm').valid()) {
            return;
        }*/

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
             Swal.fire(
                 'Success!',
                 'Was created',
                 'success'
             )
        },
        error: function (data) {
            handle403(data);
            Swal.fire(
                'BAD!',
                'Can not create',
                'error'
            )
            // alert($.i18n._('saveUserError'));
        }
    });
    $('#userModal').find('input, select').val('');
}

function deleteUser(e) {

    var id = getID(e, '#userTableTemplate');

    $.ajax({
        url: "http://localhost:8080/user/" + id,
        type: "DELETE",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function () {
            getUsers();
            Swal.fire(
                'Success!',
                'Was deleted',
                'success'
            )
        },
        error: function (xhr, ajaxOptions, thrownError) {
            // alert($.i18n._('deleteUserError'));
            handle403(xhr);
            Swal.fire(
                'BAD!',
                'Error while deleting',
                'error'
            )
        }
    });
}
