$(document).ready(function () {

    /*================================
   Preloader
   ==================================*/

    var preloader = $('#preloader');
    $(window).on('load', function() {
        preloader.fadeOut('slow', function() { $(this).remove(); });
    });

    /*================================
    form bootstrap validation
    ==================================*/
    $('[data-toggle="popover"]').popover();

    /*================================
    login form
    ==================================*/
    $('.form-gp input').on('focus', function() {
        $(this).parent('.form-gp').addClass('focused');
    });
    $('.form-gp input').on('focusout', function() {
        if ($(this).val().length === 0) {
            $(this).parent('.form-gp').removeClass('focused');
        }
    });

    setTranslateRegistration();

    $(document).on('click', '#register_submit', registerUser);

    $(document).on("keyup", function(event) {
        if (event.keyCode === 13) {
            event.preventDefault();
            registerUser();
        }
    });


});

function registerUser() {

    var data = {
        username: $('#username').val(),
        password: $('#password').val(),
        repeatPassword: $('#repeatPassword').val()
    };

    $.ajax({
        url: "http://localhost:8080/auth/signup",
        type: "POST",
        data: JSON.stringify(data),
        dataType: "json",
        contentType: "application/json",
        success: function (response, status, xhr) {
            Swal.fire(
                'Success registration!',
                'Go to sign in now!',
                'success'
            )
        },
        error: function(xhr, ajaxOptions, thrownError) {
            Swal.fire(
                'BAD!',
                'Can-not register',
                'error'
            )
        }});
}