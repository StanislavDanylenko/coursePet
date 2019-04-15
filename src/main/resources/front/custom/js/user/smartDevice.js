

function fillSmartDevices() {
    var html = smartDeviceTemplate(ANIMAL.animal.smartDevices);
    $('.animal-sd').empty().append(html);
    setDataTable('smartDeviceTableTemplate');

}

function getSmartDeviceInfo(e) {
    var sdId = $(e.target).attr('sd-id');

    var data = ANIMAL.animal.smartDevices;
    var device = {};

    for(var i = 0; i < data.length; i++) {
        if(data[i].id == sdId) {
            device = data[i];
            break;
        }
    }
    var html = recordTemplate(device.records);
    $('.recordTableContainer').empty().append(html);
    setDataTable('recordTableTemplate');

    $('.record-window')[0].click();
}

function deleteSmartDevice(e) {

    var id = getID(e, '#smartDeviceTableTemplate');

    $.ajax({
        url: HOST + "/smartDevice/" + id,
        type: "DELETE",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function () {
            getAnimal(ANIMAL.animal.id);
            Swal.fire(
                'SUCCESS!',
                'Deleted',
                'success'
            )
        },
        error: function (xhr, ajaxOptions, thrownError) {
            // alert($.i18n._('deleteCountryError'));
            Swal.fire(
                'BAD!',
                'Error while deleting',
                'error'
            )
        }
    });
}

function saveSmartDevice() {

    var smartDevice = {
        name: $('#sdName').val(),
        mac: $('#sdMac').val(),
        animalId: ANIMAL.animal.id,
        batteryLevel: 100
    };

    /*    if (!$('#countryForm').valid()) {
            return;
        }*/

    $.ajax({
        url: HOST + "/smartDevice",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(smartDevice),
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function (data) {
            $("[data-dismiss=modal]").trigger({type: "click"});
            getAnimal(ANIMAL.animal.id);
            Swal.fire(
                'SUCCESS!',
                'Saved!',
                'success'
            )
        },
        error: function (data) {
            Swal.fire(
                'BAD!',
                'Can not create',
                'error'
            )
            // alert($.i18n._('saveCountryError'));
        }
    });
    $('#sdModal').find('input, select').val('');
}