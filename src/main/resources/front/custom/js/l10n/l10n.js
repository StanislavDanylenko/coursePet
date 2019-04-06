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

function setTranslateIndex() {
    $('.home')._t("home");
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

function setTranslateRegistration() {
    loadLocale();
    $(".sign-up")._t("sign-up");
}

function setTranslateLogin() {
    loadLocale();
    $(".sign-in")._t("sign-in");
}

var UA = {
    "home": "НАГОРУ",

    "sign-in": "УВІЙТИ",
    "sign-up": "ЗАРЕЄСТРУВАТИСЯ"
};

var EN = {
    "home": "HOME",

    "sign-in": "Sign In",
    "sign-up": "Sign up"
};


