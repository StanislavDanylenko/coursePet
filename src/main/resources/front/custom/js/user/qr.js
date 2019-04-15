function fillSmartCard() {
    getQR();
    $('#SCID').text(ANIMAL.animal.smartCardId);
}

function getQR() {
    var url = 'https://api.qrserver.com/v1/create-qr-code/?data=' + 'name: ' + ANIMAL.animal.name + '%0d%0a' + 'id: ' + ANIMAL.animal.id;
    $('#QR').attr('src', url);
}