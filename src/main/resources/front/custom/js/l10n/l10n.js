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

function setTranslateAlert() {
    UPDATED = $.i18n._('Aupdated');
    CREATED = $.i18n._('Acreated');
    DELETED = $.i18n._('Adeleted');
}

function setTranslationAllRole() {

    $(".descriptionM")._t("descriptionM");

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

    $(".country")._t("country");
    $(".name")._t("name");

    $(".animalClass")._t("animalClass");
    $(".animalBreed")._t("animalBreed");

    $(".graft")._t("graft");
    $(".disease")._t("disease");
    $(".frequency")._t("frequency");

    $(".footer-area")._t("footer-area");

}

function setTranslateButtons() {

    $(".closeM")._t("closeM");
    $(".modal-title")._t("modal-title");
    $(".saveChanges")._t("saveChanges");

    $("button[class*='add']")._t("add");
    $("button[class*='edit']")._t("edit");
    $("button[class*='delete']")._t("delete");

    $(".deleteU")._t("deleteU");
    $(".choose-animal")._t("choose");
    $(".add-animal")._t("add-animal");

}

function setTranslateAdmin() {

    loadLocale();
    setTranslateAlert();
    setTranslationAllRole();
    setTranslateButtons();

    $(".users")._t("users");

    $(".page-title")._t("page-title-admin");

    $(".countryId")._t("countryId");
    $(".countryName")._t("countryName");
    $(".countryGrafts")._t("countryGrafts");

    $(".graftId")._t("graftId");
    $(".graftName")._t("graftName");
    $(".graftFrequency")._t("graftFrequency");

    $(".diseaseId")._t("diseaseId");
    $(".diseaseName")._t("diseaseName");

    $(".userId")._t("userId");

    $(".user")._t("user");

}

function setTranslationSmartCardId() {
    var scid = $('#SCID').text();
    $(".scid")._t("scid");
    $('#SCID').text(scid);
}

function setTranslationUser() {

    loadLocale();
    setTranslateAlert();
    setTranslationAllRole();
    setTranslateButtons();
    setTranslationSmartCardId();

    $(".animals")._t("animals");
    $(".statistic")._t("statistic");
    $(".brincountries")._t("brincountries");

    $(".page-title")._t("page-title-user");

    $(".info")._t("info");
    $(".grafts")._t("grafts");
    $(".diseases")._t("diseases");
    $(".smartDevices")._t("smartDevices");
    $(".records")._t("records");
    $(".smartCard")._t("smartCard");

    $(".animal-name")._t("animal-name");
    $(".animal-date")._t("animal-date");
    $(".animal-weight")._t("animal-weight");
    $(".animal-height")._t("animal-height");
    $(".animal-length")._t("animal-length");
    $(".animal-gender")._t("animal-gender");

    $(".selectImage")._t("selectImage");
    $(".submitButton").val($.i18n._("submitButton"));

    $(".check-info")._t("check-info");

    $(".male")._t("male");
    $(".female")._t("female");

    $(".startDate")._t("startDate");
    $(".finishDate")._t("finishDate");
    $(".treathment")._t("treathment");
    $(".date")._t("date");

    $(".smartDevice")._t("smartDevice");
    $(".battery-levell")._t("battery-levell");
    $(".switcht")._t("switcht");
    $(".sd-info")._t("sd-info");
    $(".sd-charge")._t("sd-charge");

    $(".animal-state")._t("animal-state");
    $(".creation-date")._t("creation-date");
    $(".pulze")._t("pulze");
    $(".temperature")._t("temperature");
    $(".longitude")._t("longitude");
    $(".latitude")._t("latitude");

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

    "page-title-admin": "Панель адміністратора",
    "page-workspace-admin": "РОБОЧИЙ ПРОСТІР АДМІНІСТРАТОРА",

    "page-title-user": "Панель користувача",
    "page-workspace-user": "РОБОЧИЙ ПРОСТІР КОРИСТУВАЧА",

    "closeM": "Закрити",
    "modal-title": "Виконати операцію",
    "saveChanges": "Зберегти зміни",

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

    'gotosignin': "Перейдіть до входу зараз!",



    'animals': "Тварини",
    'statistic': "Статистика",
    'brincountries': "Породи в країнах",

    'info': "Інформація",
    'grafts': "Щеплення",
    'diseases': "Хвороби",
    'smartDevices': "Розумні присторої",
    'smartDevice': "Розумний присторій",
    'records': "Записи",
    'smartCard': "Смарт-карта",

    'animal-name': "Кличка",
    'animal-date': "Дата народження",
    'animal-weight': "Маса",
    'animal-height': "Висота",
    'animal-length': "Довжина",
    'animal-gender': "Стать",

    'selectImage': "Обрати зображення",
    'submitButton': "Підтвердити",

    'check-info': "Перевірити",
    'scid': "ID Розумної карти: <b id='SCID'></b>",

    'male': "Чоловіча",
    'female': "Жіноча",

    'startDate': "Дата початку",
    'finishDate': "Дата закінчення",
    'treathment': "Лікування",
    'date': "Дата",

    'footer-area': "<p>© Copyright 2019. Усі права захищено. <b>Даниленко Станіслав.</b> Шаблон <a\n" +
        "                href=\"https://colorlib.com/wp/\">Colorlib</a>.</p>",

    'deleteU': "ВИДАЛИТИ",
    'choose': "ВИБРАТИ",
    'add-animal': "ДОДАТИ НОВУ",

    'battery-levell': "Рівень заряду батареї",
    'switcht': "Перемкнути",
    'sd-info': "Показати записи",
    'sd-charge': "ЗАРЯДИТИ",

    'animal-state': "Стан тварини",
    'creation-date': "Дата створення",
    'pulze': "Пульс",
    'temperature': "Температура",
    'longitude': "Довгота",
    'latitude': "Широта",

    "graftsNeeded": "Необхідні щеплення",
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

    "page-title-admin": "Admin dashboard",
    "page-workspace-admin": "ADMIN WORKSPACE",

    "page-title-user": "User dashboard",
    "page-workspace-user": "USER WORKSPACE",

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

    'gotosignin': "Go to sign in now!",



    'animals': "Animals",
    'statistic': "Statistic",
    'brincountries': "Breed in countries",

    'info': "Info",
    'grafts': "Grafts",
    'diseases': "Diseases",
    'smartDevices': "Smart Devices",
    'smartDevice': "Smart Device",
    'records': "Records",
    'smartCard': "Smart Card",

    'animal-name': "Name",
    'animal-date': "Date of birth",
    'animal-weight': "Weight",
    'animal-height': "Height",
    'animal-length': "Length",
    'animal-gender': "Gender",

    'selectImage': "Select Image",
    'submitButton': "Submit",

    'check-info': "Get info",
    'scid': "SmartCard ID: <b id='SCID'></b>",

    'male': "Male",
    'female': "Female",

    'startDate': "Start Date",
    'finishDate': "Finish Date",
    'treathment': "Treathment",
    'date': "Date",

    'footer-area': "<p>© Copyright 2019. All right reserved. <b>Stanislav Danylenko.</b> Template by <a\n" +
        "                href=\"https://colorlib.com/wp/\">Colorlib</a>.</p>",

    'deleteU': "DELETE",
    'choose': "CHOOSE",
    'add-animal': "ADD NEW",

    'battery-levell': "Battery level",
    'switcht': "Switch",
    'sd-info': "Show records",
    'sd-charge': "CHARGE",

    'animal-state': "Animal State",
    'creation-date': "Creation Date",
    'pulze': "Pulse",
    'temperature': "Temperature",
    'longitude': "Longitude",
    'latitude': "Latitude",

    'latitude': "Latitude",

    "graftsNeeded": "Grafts yoa are needed",
};


