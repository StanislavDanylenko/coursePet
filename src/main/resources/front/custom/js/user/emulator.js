var SMART_DEVICE_ID;
var CURRENT_STATE;

var delay5000 = delay(addRecord, 5000);
var timeoutId;

var SLEEP = {
    name: 'SLEEP',
    pulse: [60, 80],
    temperature: [36, 48],
    latitude: 0,
    longitude: 0,
    locationShiftLatitude: 0,
    locationShiftLongitude: 0
};

var ACTIVE = {
    name: 'ACTIVE',
    pulse: [70, 130],
    temperature: [36, 48],
    latitude: 0,
    longitude: 0,
    locationShiftLatitude: -1.15899,
    locationShiftLongitude: 0.9924
};

var RUN = {
    name: 'RUN',
    pulse: [80, 150],
    temperature: [36, 48],
    latitude: 0,
    longitude: 0,
    locationShiftLatitude: 1.256,
    locationShiftLongitude: -0.7753
};


var STATE = [SLEEP, RUN, ACTIVE];


function getRandomNumberInRange(min, max) {
    var rand = min - 0.5 + Math.random() * (max - min + 1);
    rand = Math.round(rand);
    return rand;
}

function getRandomState(min, max) {
    var rand = getRandomNumberInRange(min, max);
    return STATE[rand];
}


function emulationStartedAlert() {
    var Toast = Swal.mixin({
        toast: true,
        position: 'bottom-end',
        showConfirmButton: false,
        timer: 3000
    });

    Toast.fire({
        type: 'success',
        title: 'Emulator is working!'
    })
}

function emulationStoppedAlert() {
    var Toast = Swal.mixin({
        toast: true,
        position: 'bottom-end',
        showConfirmButton: false,
        timer: 3000
    });

    Toast.fire({
        type: 'error',
        title: 'Emulator is stopped!'
    })
}

function emulationAddedAlert() {
    var Toast = Swal.mixin({
        toast: true,
        position: 'bottom-start',
        showConfirmButton: false,
        timer: 2000
    });

    Toast.fire({
        type: 'info',
        title: 'Added new record!'
    })
}

function initStateParams(state) {

    var latitude = getRandomNumberInRange(0, 100);
    var longitude = getRandomNumberInRange(0, 100);
    var locationShiftLatitude = Math.random() - 0.5;
    var locationShiftLongitude = Math.random() - 0.5;

    state.latitude = latitude;
    state.longitude = longitude;
    state.locationShiftLatitude *= locationShiftLatitude;
    state.locationShiftLongitude *= locationShiftLongitude;

    console.log(state);
}

function getRecordFromState() {

    var record = {};

    record.temperature = getRandomNumberInRange(
        CURRENT_STATE.temperature[0],
        CURRENT_STATE.temperature[1]);

    record.pulse = getRandomNumberInRange(
        CURRENT_STATE.pulse[0],
        CURRENT_STATE.pulse[1]);

    var lat = CURRENT_STATE.latitude += CURRENT_STATE.locationShiftLatitude;
    var lon = CURRENT_STATE.longitude += CURRENT_STATE.locationShiftLongitude;

    record.latitude =  Math.round(lat * 1000) / 1000;
    record.longitude = Math.round(lon * 1000) / 1000;

    record.animalState = CURRENT_STATE.name;
    record.smartDeviceId = SMART_DEVICE_ID;

    var changeState = getRandomNumberInRange(1, 100);
    console.log(changeState);
    if (changeState % 15 == 0) {
        CURRENT_STATE = getRandomState(0, STATE.length - 1);
    }

    return record;
}

function startEmulation(id) {

    cleanSetedTimeout();

    SMART_DEVICE_ID = id;
    CURRENT_STATE = getRandomState(0, STATE.length - 1);

    emulationStartedAlert();

    initStateParams(CURRENT_STATE);
    generateRecord();

}

function generateRecord() {
     delay5000();
}

function analyzeRecord(record) {

    var errorMessage = '';

    if (record.temperature > 46) {
        errorMessage += ' Higher temperature'
    }

    if (record.pulse > 125) {
        errorMessage += ' Higher pulse'
    }

    if (errorMessage.length > 0) {
        fireError(errorMessage);
    } else {
        emulationAddedAlert();
    }

}

function fireError(errorMessage) {
    Swal.fire({
        title: 'Warning',
        text: errorMessage,
        type: 'warning',
        confirmButtonText: 'Check',
        preConfirm: function () {
            $('#pills-contact5-tab').click();
        }
    });

    var notification = {
        theme: 'Warning',
        message: errorMessage
    };

    sendNotification(notification);
}

function addRecord() {

    var record = getRecordFromState();

    $.ajax({
        url: HOST + "/record",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(record),
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function (data) {
            var value = +$('.battery-level[sd-id="' + activeDeviceId + '"]').text();
             $('.battery-level[sd-id="' + activeDeviceId + '"]').text(value - 2);
            analyzeRecord(data);
            getRecords();
        },
        error: function (xhr, data) {
            if (xhr.status === 409) {
                cleanSetedTimeout();
                $(activeDevice).prop('checked', false);
                Swal.fire(
                    'BAD!',
                    'Emulation stopped. Low battery level.',
                    'error'
                )
            }
        }
    });

}

/////////
function delay(f, ms) {

    return function() {
        var savedThis = this;
        var savedArgs = arguments;

        timeoutId = setInterval(function() {
            f.apply(savedThis, savedArgs);
        }, ms);
    };

}

function cleanSetedTimeout() {
    if (timeoutId != undefined) {
        clearTimeout(timeoutId);
    }
}