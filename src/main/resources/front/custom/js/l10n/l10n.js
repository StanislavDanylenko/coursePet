var locale;

function checkUSER() {
    if (USER != null && USER != undefined) {
        switch (USER.localization)  {
            case "UKRAINIAN":
                $('#localizationSwitcher').val("UA");
                setL10n(UA);
                break;
            case "ENGLISH":
                $('#localizationSwitcher').val("EN");
                setL10n(EN);
                break;
            default: setL10n(EN);
        }
    } else {
        setL10n(EN);
    }
}

function setL10n(loc) {
    locale = loc;
}

function loadLocale() {

    loadUserLS();

   if(USER !== undefined) {
       if(USER.localization === "ENGLISH") {
           locale = EN;
       } else {
           locale = UA;
       }
   }

    $.i18n.unload();
    $.i18n.load(locale)
}

function changeLocaleIndex(e) {

    loadLocale();

    if ($(e).hasClass('en')) {
        USER.localization = "ENGLISH";
        setL10n(EN);
    } else {
        USER.localization = "UKRAINIAN";
        setL10n(UA);
    }
    saveUserLS(USER);
    $.i18n.unload();
    $.i18n.load(locale);
    setTranslateIndex();
}

///////////////

function setTranslateIndex() {
    $('.home')._t("home");
    $(".sign-up")._t("sign-up");
    $(".sign-in")._t("sign-in");
    $(".contact")._t("contact");
    $(".language")._t("language");
    $(".description")._t("description");
    $(".features")._t("features");
    $(".main-features")._t("main-features");
    $(".feature1")._t("feature1");
    $(".feature2")._t("feature2");
    $(".feature3")._t("feature3");
    $(".feature4")._t("feature4");
    $(".feature5")._t("feature5");
    $(".feature6")._t("feature6");
    $(".description1")._t("description1");
    $(".description2")._t("description2");
    $(".description3")._t("description3");
    $(".description4")._t("description4");
    $(".description5")._t("description5");
    $(".description6")._t("description6");
    $(".contact-info")._t("contact-info");
    $(".location")._t("location");
    $(".phone")._t("phone");
    $(".footinfo")._t("footinfo");
}

function setTranslateRegistration() {
    loadLocale();
    $(".sign-up")._t("sign-up");
    $(".username")._t("username");
    $(".password")._t("password");
    $(".repeat-password")._t("repeat-password");
    $(".submit")._t("submit");
    $(".questionR")._t("questionR");
}

function setTranslateLogin() {
    loadLocale();
    $(".sign-in")._t("sign-in");
    $(".username")._t("username");
    $(".password")._t("password");
    $(".submit")._t("submit");
    $(".question")._t("question");
}

function setTranslateUser() {
    loadLocale();
    setTranslateAlert();

    $(".country")._t("country");
    $(".animalClass")._t("animalClass");
    $(".animalBreed")._t("animalBreed");
    $(".graft")._t("graft");
    $(".disease")._t("disease");
    $(".users")._t("users");

    $(".page-title")._t("page-title");


    $(".closeM")._t("closeM");
    $(".modal-title")._t("modal-title");
    $(".saveChanges")._t("saveChanges");
    $(".countryId")._t("countryId");
    $(".countryName")._t("countryName");
    $(".descriptionM")._t("descriptionM");
    $(".countryGrafts")._t("countryGrafts");

    $(".graftId")._t("graftId");
    $(".graftName")._t("graftName");
    $(".graftFrequency")._t("graftFrequency");

    $(".diseaseId")._t("diseaseId");
    $(".diseaseName")._t("diseaseName");

    $(".userId")._t("userId");
    $(".username")._t("username");
    $(".password")._t("password");
    $(".repeatPassword")._t("repeatPassword");

    $(".userOldPassword")._t("userOldPassword");
    $(".userNewPassword")._t("userNewPassword");
    $(".userNewPasswordRepeat")._t("userNewPasswordRepeat");
    $(".update-password")._t("update-password");
    $(".l18n")._t("l18n");

    $(".profile")._t("profile");
    $(".log-out")._t("log-out");

    $("button[class*='add']")._t("add");
    $("button[class*='edit']")._t("edit");
    $("button[class*='delete']")._t("delete");

    $(".name")._t("name");
    $(".frequency")._t("frequency");
    $(".user")._t("user");

}

function setTranslateAlert() {
    UPDATED = $.i18n._('Aupdated');
    CREATED = $.i18n._('Acreated');
    DELETED = $.i18n._('Adeleted');
}

var UA = {
    "home": "НАГОРУ",

    "sign-in": "УВІЙТИ",
    "sign-up": "ЗАРЕЄСТРУВАТИСЯ",
    "contact": "КОНТАКТИ",
    "language": "МОВА",
    "description": "Описання тут",
    "features": "МОЖЛИВОСТІ",
    "main-features": "Наші основні можливості",
    "feature1": "Функція#1",
    "feature2": "Функція#2",
    "feature3": "Функція#3",
    "feature4": "Функція#4",
    "feature5": "Функція#5",
    "feature6": "Функція#6",
    "description1": "описання#1",
    "description2": "описання#2",
    "description3": "описання#3",
    "description4": "описання#4",
    "description5": "описання#5",
    "description6": "описання#6",
    "contact-info": "Зв'яжіться з нами, не чекайте",
    "location": "Харків, Україна",
    "phone": "Телефон: +xxxxxxxxx",
    "footinfo": "Виконав <b>Станіслав Даниленко</b> використовуючи w3.css",

    "username": "Нікнейм",
    "password": "Пароль",
    "repeat-password": "Повтор паролю",
    "submit": "Підтвердити<i class=\"ti-arrow-right\"></i>",
    "question": "Ще не маєте акаунту? <a href=\"register.html\" class=\"sign-up\">Зареєструватися</a>",
    "questionR": "Ходімо до входу?<a href=\"login.html\" class=\"sign-in\">Увійти</a>",

    "country": "Країна",
    "animalClass": "Клас тварини",
    "animalBreed": "Порода тварини",
    "graft": "Щеплення",
    "disease": "Хвороба",
    "users": "Користувачі",

    "page-title": "Панель адміністратора",
    "page-workspace": "РОБОЧИЙ ПРОСТІР АДМІНІСТРАТОРА",

    "closeM": "Закрити",
    "modal-title": "Виконати операцію",
    "saveChanges": "Зберігти зміни",

    "countryId": "Id Країни",
    "countryName": "Назва країни",
    "descriptionM": "Описання",
    "countryGrafts": "Необхідні щеплення",

    "graftId": "Id Щеплення",
    "graftName": "Назва Щеплення",
    "graftFrequency": "Періодичність Щеплення",

    "diseaseId": "Id хвороби",
    "diseaseName": "Назва хвороби",

    "userId": "Id Користувача",
    "repeatPassword": "Повторити пароль",

    "userOldPassword": "Старий пароль",
    "userNewPassword": "Новий пароль",
    "userNewPasswordRepeat": "Повторити новий пароль",
    "update-password": "Оновити пароль",

    "l18n": "Локалізація",

    "profile": "Профіль",
    "log-out": "Вихід",

    "name": "Назва",

    "add": "<span class='fa fa-plus'> </span>Додати",
    "edit": "Редагувати <i class='fa fa-edit'></i>",
    "delete": "Видалити <i class='fa fa-close'></i>",

    "frequency": "Частота",
    "user": "Користувач",

    "requiredField": "Обов'язкове поле",
    "samePassword": "Паролі мають співпадати",

    "Aupdate": "оновити",
    "Aupdated": "оновлено",

    "AgetInfo": "отримати інформацію",

    "Acreate": "створити",
    "Acreated": "створено",

    "Adelete": "видалити",
    "Adeleted": "видалено",

    "AperformOperation": "виконати операцію",

    "failed": "НЕВДАЧА!",
    "cannot": "Не вийшло ",

    "success": "УСПІХ!",
    "was": "Було ",

    "changed" : "змінено",
    "change" : "змінити",

    "badCredentials": "Невірний логін/пароль",

    "allFieldsAreRequired": "Необхідно заповнити усі поля!",

    "register": "зареєструватися",
    "registration": " реєстрація",

    'gotosignin': "Перейдіть до входу зараз!"

};

var EN = {
    "home": "HOME",

    "sign-in": "SIGN IN",
    "sign-up": "SIGN UP",
    "contact": "CONTACT",
    "language": "LANGUAGE",
    "description": "Description here",
    "features": "FEATURES",
    "main-features": "Our main features",
    "feature1": "Feature#1",
    "feature2": "Feature#2",
    "feature3": "Feature#3",
    "feature4": "Feature#4",
    "feature5": "Feature#5",
    "feature6": "Feature#6",
    "description1": "description#1",
    "description2": "description#2",
    "description3": "description#3",
    "description4": "description#4",
    "description5": "description#5",
    "description6": "description#6",
    "contact-info": "Contact us, don't wait",
    "location": "Kharkiv, Ukraine",
    "footinfo": "Powered by <b>Stanislav Danylenko</b> using w3.css",

    "username": "Username",
    "password": "Password",
    "repeat-password": "Repeat password",
    "submit": "Submit<i class=\"ti-arrow-right\"></i>",
    "question": "Don't have an account?<a href=\"register.html\" class=\"sign-up\">Sign up</a>",
    "questionR": "Go to login?<a href=\"login.html\" class=\"sign-in\">Sign in</a>",

    "country": "Country",
    "animalClass": "Animal Class",
    "animalBreed": "Animal Breed",
    "graft": "Graft",
    "disease": "Disease",
    "users": "Users",

    "page-title": "Admin dashboard",
    "page-workspace": "ADMIN WORKSPACE",

    "closeM": "Close",
    "saveChanges": "Save changes",
    "modal-title": "Perform action",

    "countryId": "Country Id",
    "countryName": "Country Name",
    "descriptionM": "Description",
    "countryGrafts": "Country Grafts",

    "graftId": "Graft Id",
    "graftName": "Graft Name",
    "graftFrequency": "Graft Frequency",

    "diseaseId": "Disease Id",
    "diseaseName": "Disease Name",

    "userId": "User Id",
    "repeatPassword": "repeatPassword",

    "userOldPassword": "Old Password",
    "userNewPassword": "New password",
    "userNewPasswordRepeat": "Repeat new password",
    "update-password": "Update password",

    "l18n": "Localization",

    "profile": "Profile",
    "log-out": "Log Out",

    "name": "Name",

    "add": "<span class='fa fa-plus'> </span> Add",
    "edit": "Edit <i class='fa fa-edit'></i>",
    "delete": "Delete <i class='fa fa-close'></i>",

    "frequency": "Frequency",
    "user": "User",

    "requiredField": "Required Field",
    "samePassword": "Passwords must be the same",


    "Aupdate": "update",
    "Aupdated": "updated",

    "AgetInfo": "get info",

    "Acreate": "create",
    "Acreated": "created",

    "Adelete": "delete",
    "Adeleted": "deleted",

    "AperformOperation": "perform operation",


    "failed": "FAILED!",
    "cannot": "Can not ",

    "success": "Success!",
    "was": "Was ",

    "changed" : "changed",
    "change" : "change",

    "badCredentials": "Bad credentials",

    "allFieldsAreRequired": "All fields must be filled!",

    "register": "register",
    "registration": " registration",

    'gotosignin': "Go to sign in now!"


};


