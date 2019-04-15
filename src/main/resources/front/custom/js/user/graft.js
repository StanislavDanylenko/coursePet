
function fillGrafts() {

    for(var i = 0; i < ANIMAL.grafts.length; i++) {
        ANIMAL.grafts[i].name = ANIMAL.grafts[i].graft.name;
        ANIMAL.grafts[i].id = ANIMAL.grafts[i].graft.id;
        ANIMAL.grafts[i].frequency = ANIMAL.grafts[i].graft.frequency;
    }

    var html = graftTemplate(ANIMAL.grafts);
    $('.animal-grafts').empty().append(html);
    setDataTable('graftTableTemplate');
}

function getAnimalGrafts() {
    $.ajax({
        url: HOST + "/graft/",
        type: "GET",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function (data) {
            $("#animalGraft").empty();
            for (var i = 0; i < data.length; i++) {
                $("#animalGraft").append(new Option(data[i].name, data[i].id));
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
            // alert($.i18n._('getCountryListError'));
            alert('error');
        }
    });
}

function deleteAnimalGraft(e) {

    var id = getID(e, '#graftTableTemplate');

    $.ajax({
        url: HOST + "/animalGraft/animal/" + ANIMAL.animal.id + "/graft/" + id,
        type: "DELETE",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function () {
            getAnimal(ANIMAL.animal.id);
            Swal.fire(
                'Success!',
                'Was deleted',
                'success'
            )
        },
        error: function (xhr, ajaxOptions, thrownError) {
            // alert($.i18n._('deleteGraftError'));
            Swal.fire(
                'BAD!',
                'Error while deleting',
                'error'
            )
        }
    });
}

function saveAnimalGraft() {

    var graft = {
        animalId: ANIMAL.animal.id,
        graftId: $('#animalGraft').val(),
        date: $('#animalGraftDate').val()
    };

    /*    if (!$('#graftForm').valid()) {
            return;
        }*/

    $.ajax({
        url: HOST + "/animalGraft",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(graft),
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function (data) {
            $("[data-dismiss=modal]").trigger({type: "click"});
            getAnimal(ANIMAL.animal.id);
            Swal.fire(
                'Success!',
                'Was created',
                'success'
            )
        },
        error: function (data) {
            Swal.fire(
                'BAD!',
                'Can not create',
                'error'
            )
            // alert($.i18n._('saveGraftError'));
        }
    });
    $('#graftModal').find('input, select').val('');
}