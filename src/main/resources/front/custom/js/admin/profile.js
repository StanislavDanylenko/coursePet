function getProfile() {
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

            updateUserLS(data);
            $('.user-name').html(data.username + '<i class="fa fa-angle-down"/>');
        },
        error: function (xhr, ajaxOptions, thrownError) {
            handleError(xhr, GET);
        }
    });
}

function updateProfile() {
    var profile = {
        localization: $('#userLocalization').val(),
        countryId: $('#userCountry').val()
    };

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
            USER.localization = profile.localization;
            saveUserLS(USER);
            if(USER.role == 'ADMIN') {
                setTranslateAdmin();
            } else {
                setTranslationUser();
            }
            handleSuccessOperation(UPDATED);
            renderHome();
        },
        error: function (xhr) {
            handleError(xhr, UPDATE);
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
            handleError(xhr, GET);
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

    validatePassword();
    if (!$('#changePasswordForm').valid()) {
        return;
    }

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
            handleSuccessOperation($.i18n._("changed"));
        },
        error: function() {
            handleCrudError($.i18n._("change"));
        }
    });
}

/////////

function validatePassword() {

    $("label.error").remove();

    $('#changePasswordForm').validate({
        rules: {
            userOldPassword: {
                required: true
            },
            userNewPassword: {
                required: true
            },
            userNewPasswordRepeat: {
                required: true,
                samePassword: true
            }
        },
        messages: {
            userOldPassword: {
                required: $.i18n._('requiredField')
            },
            userNewPassword: {
                required: $.i18n._('requiredField')
            },
            userNewPasswordRepeat: {
                required: $.i18n._('requiredField'),
                samePassword: $.i18n._('samePassword')
            }
        }
    });

}