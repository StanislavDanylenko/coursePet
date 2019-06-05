var currentListOfGrafts;
var isUpdate = false;

function renderCountryList(response) {
    var html = countryTableTemplate(response);
    $(MAIN_CONTAINER).empty().append(html);
    setDataTable('countryTableTemplate');
}

function getCountries() {
    $.ajax({
        url: HOST + "/country/full",
        type: "GET",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function (data) {
            data = prepareData(data);
            renderCountryList(data);
        },
        error: function (xhr, ajaxOptions, thrownError) {
            handleError(xhr, GET);
        }
    });
}

function getCountry(id) {
    console.log('in the get country method, id = ' + id);
    $.ajax({
        url: HOST + "/country/full/" + id,
        type: "GET",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function (data) {
            $('#countryId').val(data.country.id);
            $('#countryName').val(data.country.name);
            $('#countryDescription').val(data.country.description);

            var ids = getGraftsId(data);
            $("#countryGrafts").val(ids);
        },
        error: function (xhr, ajaxOptions, thrownError) {
            handleError(xhr, GET);
        }
    });
}


function createCountry() {
    if (!isUpdate) {
        var button = $('.action-country');
        button.unbind();
        button.bind('click', saveCountry);
        // $('#countryOperation')._t('addCountry');
        getGraftsCountry();
    }
    validateCountry();

}

function editCountry(e) {

    $('.add-country')[0].click();

    var id = getID(e, '#countryTableTemplate');
    var button = $('.action-country');
    button.unbind();
    button.bind('click', updateCountry);
    // $('#countryOperation')._t('editCountry');
    getCountry(id);
    validateCountry();
}


function saveCountry() {

    var country = {
        name: $('#countryName').val(),
        description: $('#countryDescription').val()
    };

    if (!$('#countryForm').valid()) {
        return;
    }

    $.ajax({
        url: HOST + "/country",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(country),
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function (data) {
            $("[data-dismiss=modal]").trigger({type: "click"});
            country.id = data.id;
            updateCountry(country);
            /* getCountries();
             Swal.fire(
                 'Success!',
                 'Was created',
                 'success'
             )*/

        },
        error: function (xhr) {
            handleError(xhr, CREATE);
        }
    });

}

function updateCountry(country) {
    if (country === undefined || country.type === 'click') {
        country = {
            id: $('#countryId').val(),
            name: $('#countryName').val(),
            description: $('#countryDescription').val()
        };
    }
    var grafts = $('#countryGrafts').val();
    var entity = {
        country: country,
        graftIds: grafts
    };

    if (!$('#countryForm').valid()) {
        return;
    }

    $.ajax({
        url: "http://localhost:8080/country/" + country.id,
        type: "PUT",
        contentType: "application/json",
        data: JSON.stringify(entity),
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function () {
            getCountries();
            $("[data-dismiss=modal]").trigger({type: "click"});
            Swal.fire(
                'Success!',
                'Was created',
                'success'
            )
        },
        error: function (xhr) {
            handleError(xhr, UPDATE);
        }
    });
    $('#countryModal').find('input, select').val('');
}

function deleteCountry(e) {

    var id = getID(e, '#countryTableTemplate');

    $.ajax({
        url: "http://localhost:8080/country/" + id,
        type: "DELETE",
        beforeSend: function (xhr) {
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
        error: function (xhr, ajaxOptions, thrownError) {
            handleError(xhr, DELETE);
        }
    });
}

///////////

function prepareData(data) {

    var processedData = [];

    for (var i = 0; i < data.length; i++) {
        var graftList = [];
        var grafts = data[i].grafts;
        for (var j = 0; j < grafts.length; j++) {
            graftList.push(grafts[j].name);
        }
        var item = {
            id: data[i].country.id,
            name: data[i].country.name,
            description: data[i].country.description,
            grafts: graftList
        };
        processedData.push(item);
    }

    return processedData;
}

function getGraftsId(data) {
    var graftIds = [];
    data = data.grafts;

    for (var j = 0; j < data.length; j++) {
        graftIds.push(data[j].id);
    }

    return graftIds;
}

/////////////

function getGraftsCountry() {
    $.ajax({
        url: HOST + "/graft/",
        type: "GET",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function (data) {
            currentListOfGrafts = data;
            $("#countryGrafts").empty();
            for (var i = 0; i < data.length; i++) {
                $("#countryGrafts").append(new Option(data[i].name, data[i].id));
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
            handleError(xhr, GET);
        }
    });
}

/////////

function validateCountry() {

    $("label.error").remove();
    $(".error").removeClass("error");

    $('#countryForm').validate({
        rules: {
            countryName: {
                required: true
            },
            countryDescription: {
                required: true
            },
            countryGrafts: {
                required: true
            }
        },
        messages: {
            countryName: {
                required: "required field"
            },
            countryDescription: {
                required: "required field"
            },
            countryGrafts: {
                required: "required field"
            }
        }
    });

}
