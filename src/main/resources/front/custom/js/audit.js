var USER = {};

function saveUserLS(user) {
    localStorage.setItem('USER', JSON.stringify(user));
}

function loadUserLS() {
    try{
        USER = JSON.parse(localStorage.getItem('USER'));
    } catch (e) {
        USER = {};
    }

}

function updateUserLS(obj) {
    USER.localization = obj.localization;
    USER.role = obj.roles[0];
    saveUserLS(USER);
}


function logout() {
    USER = undefined;
    saveUserLS(USER);
    window.location = 'index.html'
}

function loadProfile() {

    $.ajax({
        url: HOST + "/user/" + USER.id,
        type: "GET",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function (data) {
            updateUserLS(data);
            $('.user-name').text(data.username);
        },
        error: function (xhr, ajaxOptions, thrownError) {
            alert('error');
        }
    });
}

function handle403() {
    window.location = 'login.html'
}