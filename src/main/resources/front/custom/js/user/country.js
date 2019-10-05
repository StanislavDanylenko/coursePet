
function getAnimalCountries() {
    $.ajax({
        url: HOST + "/country/",
        type: "GET",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function (data) {
            COUNTRIES = data;
            getCountriesName();
            setAutocomplete();
        },
        error: function (xhr, ajaxOptions, thrownError) {
            handleError(xhr, GET);
        }
    });
}

function getCountriesName() {
    COUNTRIES_LABELS = [];
    for(var i = 0; i < COUNTRIES.length; i++) {
        COUNTRIES_LABELS.push(COUNTRIES[i].name);
    }
}

function setAutocomplete() {
    $( "#countriesAutocomplete" ).autocomplete({
        source: COUNTRIES_LABELS
    });
}

function checkInfo(name) {
    if(name === undefined || name.type == 'click') {
        name = $('#countriesAutocomplete').val();
    }

    for(var i = 0; i < COUNTRIES.length; i++) {
        if(COUNTRIES[i].name === name) {
            getAnimalAvailability(COUNTRIES[i].id);
            break;
        }
    }

    Swal.fire(
        $.i18n._('infoM'),
        $.i18n._('infoNothing'),
        'info'
    )

}

function getAnimalAvailability(countryId) {
    $.ajax({
        url: HOST + "/animal/country/" + countryId + "/animal/" + ANIMAL.animal.id,
        type: "GET",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function (data) {
            if(data.isAvailable == true) {
                Swal.fire(
                    $.i18n._('allright'),
                    $.i18n._('cango'),
                    'success'
                )
            } else {
                var html = getHtmlForNoAlert(data.grafts);
                Swal.fire({
                    title: $.i18n._('nocant'),
                    type: 'error',
                    html: html
                });
                $(".graftsNeeded")._t("graftsNeeded");
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
            handleError(xhr, GET);
        }
    });
}

function getHtmlForNoAlert(grafts) {
    var html = countryGraftsTemplate(grafts);
    return html;
}