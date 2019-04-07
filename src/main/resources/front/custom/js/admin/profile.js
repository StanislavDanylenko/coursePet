function getUser(id) {
    console.log('in the get user method, id = ' + id);
    $.ajax({
        url: HOST + "/user/" + id,
        type: "GET",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function (data) {
            $('#userId').val(data.id);
            $('#userName').val(data.name);
            $('#userFrequency').val(data.frequency);
        },
        error: function (xhr, ajaxOptions, thrownError) {
            // alert($.i18n._('getUserError'));
            alert('error');
        }
    });
}

function updateUser(user) {
    user = {
        id: $('#userId').val(),
        name: $('#userName').val(),
    };

    /*if (!$('#userForm').valid()) {
        return;
    }*/

    $.ajax({
        url: "http://localhost:8080/user/" + user.id,
        type: "PUT",
        contentType: "application/json",
        data: JSON.stringify(user),
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function () {
            getUsers();
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
            // alert($.i18n._('updateUserError'));
        }
    });
    $('#userModal').find('input, select').val('');
}