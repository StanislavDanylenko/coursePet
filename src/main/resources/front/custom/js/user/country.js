
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
            // alert($.i18n._('getCountryListError'));
            alert('error');
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
        'INFO',
        'We have no info for you',
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
                    'ALL RIGHT',
                    'You may go here',
                    'success'
                )
            } else {
                var html = getHtmlForNoAlert(data.grafts);
                Swal.fire({
                    title: '<strong>NO YOU CAN`T</strong>',
                    type: 'error',
                    html: html
                })
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
            // alert($.i18n._('getCountryListError'));
            alert('error');
        }
    });
}

function getHtmlForNoAlert(grafts) {
    var html = countryGraftsTemplate(grafts);
    return html;
}