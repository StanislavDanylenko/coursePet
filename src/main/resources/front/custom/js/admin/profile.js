function getProfile() {
    getCountries();
    console.log('in the get profile method, id = ' + USER.id);
    $.ajax({
        url: HOST + "/user/" + USER.id,
        type: "GET",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function (data) {
            $('#profileId').val(data.id);
            $('#userLocalization').val(data.localization);
            $('#userCountry').val(data.country.id);
            $('.profile-update')[0].click();
        },
        error: function (xhr, ajaxOptions, thrownError) {
            // alert($.i18n._('getUserError'));
            alert('error');
        }
    });
}

function updateProfile() {
    var profile = {
        localization: $('#userLocalization').val(),
        countryId: $('#userCountry').val()
    };

    /*if (!$('#userForm').valid()) {
        return;
    }*/

    $.ajax({
        url: HOST + "/user/" + USER.id,
        type: "PUT",
        contentType: "application/json",
        data: JSON.stringify(profile),
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function () {
            $("[data-dismiss=modal]").trigger({type: "click"});
            Swal.fire(
                'Success!',
                'Was updated',
                'success'
            )
        },
        error: function (data) {
            Swal.fire(
                'BAD!',
                'Can not update',
                'error'
            )
            // alert($.i18n._('updateUserError'));
        }
    });
    $('#profileModal').find('input, select').val('');
}

////////////////

function getProfileCountries() {
    $.ajax({
        url: HOST + "/country/",
        type: "GET",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function (data) {
            if(data) {
                $("#userCountry").empty();
                for(var i = 0; i < data.length; i++) {
                    $("#userCountry").append(new Option(data[i].name, data[i].id));
                }
                getProfile();
            }

        },
        error: function (xhr, ajaxOptions, thrownError) {
            // alert($.i18n._('getCountryListError'));
            alert('error');
        }
    });
}

///////////////////

function updateUserPassword() {

    var newPassword = $('#userNewPassword').val();
    var newPasswordRepeat = $('#userNewPasswordRepeat').val();

    if (newPassword != newPasswordRepeat) {
        console.log('different passwords');
        return;
    }

    /*if (!$('#changePasswordForm').valid()) {
        return;
    }*/

    var user = {
        id: USER.id,
        oldPassword: $('#userOldPassword').val(),
        newPassword: newPassword
    };

    $.ajax({
        url: HOST + "/user/password",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        data: JSON.stringify(user),
        success: function () {
            $('#userOldPassword').val('');
            $('#userNewPassword').val('');
            $('#userNewPasswordRepeat').val('');
            Swal.fire(
                'Success!',
                'Password was changed',
                'success'
            )
        },
        error: function(data) {
            alert($.i18n._('updatePasswordError'));
        }
    });
}