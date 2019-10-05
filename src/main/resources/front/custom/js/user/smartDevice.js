var activeDevice;
var activeDeviceId;


function alertClick() {
    $('#pills-contact5-tab').click();
}

function getRecords() {
    $.ajax({
        url: HOST + "/smartDevice/animal/" + ANIMAL.animal.id,
        type: "GET",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function (data) {
            ANIMAL.animal.smartDevices = data;
            fillRecords();
        },
        error: function (xhr, ajaxOptions, thrownError) {
            handleError(xhr, GET);
        }
    });
}

function fillSmartDevices() {
    var html = smartDeviceTemplate(ANIMAL.animal.smartDevices);
    $('.animal-sd').empty().append(html);
    setDataTable('smartDeviceTableTemplate');

    setTranslationUser();
}

function fillRecords() {
    var records = [];
    var devices = ANIMAL.animal.smartDevices;
    for (var i = 0; i < devices.length; i++) {
        var recordz = devices[i].records;
        for (var j = 0; j < recordz.length; j++) {
            recordz[j].creationDate = new Date(recordz[j].creationDate + " UTC").toLocaleString();
            records.push(recordz[j])
        }
    }
    var html = recordsTemplate(records);
    $('.animal-records').empty().append(html);
    setDataTable('recordsTableTemplate');

    setTranslationUser();
}

function getSmartDeviceInfo(e) {
    var sdId = $(e.target).attr('sd-id');

    var data = ANIMAL.animal.smartDevices;
    var device = {};

    for (var i = 0; i < data.length; i++) {
        if (data[i].id == sdId) {
            device = data[i];
            break;
        }
    }
    var html = recordTemplate(device.records);
    $('.recordTableContainer').empty().append(html);
    setDataTable('recordTableTemplate');

    $('.record-window')[0].click();
    setTranslationUser();
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
            handleSuccessOperation(DELETED);
        },
        error: function (xhr, ajaxOptions, thrownError) {
            handleError(xhr, DELETE);
        }
    });
}


///////////////
function chargeSmartDevice(e) {

    var id = getID(e, '#smartDeviceTableTemplate');

    $.ajax({
        url: HOST + "/smartDevice/charge/" + id,
        type: "POST",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function () {
            $('.battery-level[sd-id="' + id + '"]').text(100);
            Swal.fire(
                $.i18n._('success'),
                $.i18n._('charged'),
                'success'
            )
        },
        error: function (xhr, ajaxOptions, thrownError) {
            Swal.fire(
                $.i18n._('error'),
                $.i18n._('updatebl'),
                'error'
            )
        }
    });
}

function processSmartDevice(e) {

    var id = $(e.target).attr('sd-id');

    var enableToggle = e.target;

    if (!enableToggle.checked) {
        offSmartDevice(id, enableToggle);
    } else {
        onSmartDevice(id, enableToggle);
    }
}

function saveSmartDevice() {

    validateSD();

    var smartDevice = {
        name: $('#sdName').val(),
        mac: $('#sdMac').val(),
        animalId: ANIMAL.animal.id,
        batteryLevel: 100
    };

    if (!$('#sdForm').valid()) {
        return;
    }

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
            handleSuccessOperation(CREATED);
        },
        error: function (data) {
            handleError(xhr, CREATE);
        }
    });
    $('#sdModal').find('input, select').val('');
}

function checkActiveSmartDevice() {

    var checkedInputs = $("input:checked.enable-sd");
    if (checkedInputs.length > 0) {
        var checkedInput = checkedInputs[0];
        if (timeoutId === undefined) {
            startEmulation(checkedInput.getAttribute('sd-id'));
        }
    }

}

/*=======*/
function onSmartDevice(id, enableToggle) {
    $.ajax({
        url: HOST + "/smartDevice/enable/" + id,
        type: "GET",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function () {
            var checkedInputs = $("input:checked.enable-sd");
            if (checkedInputs.length > 0) {
                $('.enable-sd').prop('checked', false);
                emulationStoppedAlert();
            }
            $(enableToggle).prop('checked', true);
            activeDevice = enableToggle;
            activeDeviceId = id;
            startEmulation(id);
        },
        error: function (xhr, ajaxOptions, thrownError) {
            $(enableToggle).prop('checked', false);
            var message = $.i18n._('errorUpdating');
            if (xhr.status === 409) {
                message = $.i18n._('errorUpdatingLL');
            }
            Swal.fire(
                $.i18n._('error'),
                message,
                'error'
            )
        }
    });
}

function offSmartDevice(id, enableToggle) {
    $.ajax({
        url: HOST + "/smartDevice/disable/" + id,
        type: "GET",
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function () {
            $(enableToggle).prop('checked', false);
            cleanSetedTimeout();
            emulationStoppedAlert();
        },
        error: function (xhr, ajaxOptions, thrownError) {
            Swal.fire(
                $.i18n._('error'),
                $.i18n._('errorUpdateStatus'),
                'error'
            )
        }
    });
}

/////
function validateSD() {

    $("label.error").remove();
    $(".error").removeClass("error");

    $('#sdForm').validate({
        rules: {
            sdName: {
                required: true
            },
            sdMac: {
                required: true
            }
        },
        messages: {
            sdName: {
                required: $.i18n._('requiredField')
            },
            sdMac: {
                required: $.i18n._('requiredField')
            }
        }
    });

}