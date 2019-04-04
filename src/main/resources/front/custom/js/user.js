var USER = {};

function saveUserLS(user) {
    localStorage.setItem('USER', JSON.stringify(user));
}

function loadUserLS() {
    USER = JSON.parse(localStorage.getItem('USER'));
}

function updateUserLS(obj) {
    USER.localization = obj.localization;
    saveUserLS(USER);
}

function initUSER() {
    loadUserLS();
    if(USER === undefined) {
        USER = {};
    }
}