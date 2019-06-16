function fillSmartCard() {
    getQR();
    $('#SCID').text(ANIMAL.animal.smartCardId);
}

function getQR() {

    var url = 'https://api.qrserver.com/v1/create-qr-code/?data='
        + 'Name: ' + ANIMAL.animal.name + '%0d%0a'
        + 'Smart Card Id: ' + ANIMAL.animal.smartCardId + '%0d%0a' + '%0d%0a'

        + 'Gender: ' + ANIMAL.animal.gender + '%0d%0a'
        + 'Birth Date: ' + ANIMAL.animal.birthDate + '%0d%0a'
        + 'Animal breed: ' + ANIMAL.animal.animalsBreed.name + '%0d%0a' + '%0d%0a'

        + 'Weight: ' + ANIMAL.animal.weight + '%0d%0a'
        + 'Height: ' + ANIMAL.animal.height + '%0d%0a'
        + 'Length: ' + ANIMAL.animal.length + '%0d%0a';

    $('#QR').attr('src', url);
}