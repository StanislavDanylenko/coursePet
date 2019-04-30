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

    setTranslateLogin();

    $(document).on('click', '#login_submit', loginUser);

    $(document).on("keyup", function(event) {
        if (event.keyCode === 13) {
            event.preventDefault();
            loginUser();
        }
    });

});

function loginUser() {

    var data = {
        username: $('#username').val(),
        password: $('#password').val()
    };

    $.ajax({
        url: "http://localhost:8080/auth/signin",
        type: "POST",
        data: JSON.stringify(data),
        dataType: "json",
        contentType: "application/json",
        success: function (response, status, xhr) {
            console.log('ok');
            saveUserLS(response);
            if(response.role === 'ADMIN') {
                window.location = 'admin.html'
            } else {
                window.location = 'user.html'
            }
        },
        error: function(xhr, ajaxOptions, thrownError) {
            console.log('bad');
            Swal.fire(
                'BAD!',
                'Bad credentials',
                'error'
            )
        }});
}

