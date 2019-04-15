
function getAnimalBreeds() {
    $.ajax({
        url: HOST + "/animalBreed/",
        type: "GET",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function (data) {
            BREEDS = data;
            $("#animalsBreed").empty();
            for (var i = 0; i < data.length; i++) {
                $("#animalsBreed").append(new Option(data[i].name, data[i].id));
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
            // alert($.i18n._('getCountryListError'));
            alert('error');
        }
    });
}