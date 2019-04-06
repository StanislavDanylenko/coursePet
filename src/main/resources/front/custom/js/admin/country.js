function renderCountryList(response) {
    var html = countryTableTemplate(response);
    $(MAIN_CONTAINER).empty().append(html);
    setDataTable('countryTableTemplate');
}

function renderCountryEntity() {
    // soon
}


function getCountries() {
    $.ajax({
        url: HOST +  "/country",
        type: "GET",
        beforeSend: function(xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function (data) {
            renderCountryList(data);
        },
        error: function(xhr, ajaxOptions, thrownError) {
            // alert($.i18n._('getCountryListError'));
            alert('error');
        }});
}

function getCountry(id) {
    console.log('in the get country method, id = ' + id);
    $.ajax({
        url: HOST + "/countries/" + id,
        type: "GET",
        beforeSend: function(xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function (data) {
            $('#countryId').val(data.id);
            $('#countryName').val(data.name);
        },
        error: function(xhr, ajaxOptions, thrownError) {
            // alert($.i18n._('getCountryError'));
            alert('error');
        }});
}


function createCountry() {
    var button = $('.action-country')
    button.unbind();
    button.bind('click', saveCountry);
    // $('#countryOperation')._t('addCountry');

}

function editCountry(e) {
    var id = getID(e, '#countryListTable');
    renderCountryEntity();
    var button = $('.action-country');
    button.unbind();
    button.bind('click', updateCountry);
    // $('#countryOperation')._t('editCountry');
    getCountry(id);
}


function saveCountry() {

    var country = {
        name: $('#countryName').val(),
        description: $('#countryDescription').val()
    };

/*    if (!$('#countryForm').valid()) {
        return;
    }*/

    $.ajax({
        url: HOST + "/country",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(country),
        beforeSend: function(xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function () {
            getCountries();
            $("[data-dismiss=modal]").trigger({ type: "click" });
            Swal.fire(
                'Success!',
                'Was created',
                'success'
            )

        },
        error: function(data) {
            Swal.fire(
                'BAD!',
                'Can not create',
                'error'
            )
            // alert($.i18n._('saveCountryError'));
        }
    });

}

function updateCountry() {
    var country = {
        id: $('#countryId').val(),
        name: $('#countryName').val()
    };

    if (!$('#countryForm').valid()) {
        return;
    }

    $.ajax({
        url: "http://localhost:8080/countries/" + country.id,
        type: "PUT",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(country),
        beforeSend: function(xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function () {
            getCountries();
        },
        error: function(data) {
            // alert($.i18n._('updateCountryError'));
        }
    });
}

function deleteCountry(e) {

    var id = getID(e, '#countryTableTemplate');

    $.ajax({
        url: "http://localhost:8080/country/" + id,
        type: "DELETE",
        beforeSend: function(xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function () {
            getCountries();
            Swal.fire(
                'Success!',
                'Was deleted',
                'success'
            )
        },
        error: function(xhr, ajaxOptions, thrownError) {
            // alert($.i18n._('deleteCountryError'));
            Swal.fire(
                'BAD!',
                'Error while deleting',
                'error'
            )
        }});
}
