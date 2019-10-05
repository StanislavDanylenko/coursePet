window.ajaxSuccess = function () {
    var response = JSON.parse(this.responseText);
    console.log("ajaxSuccess", typeof this.responseText);
    document.getElementById('animal-img').setAttribute("src", response["secure_url"]);
    updatePhoto(response["secure_url"]);
};

window.AJAXSubmit = function (formElement) {
    console.log("starting AJAXSubmit");
    if (!formElement.action) { return; }
    var xhr = new XMLHttpRequest();
    xhr.onload = ajaxSuccess;
    xhr.open("post", "https://api.cloudinary.com/v1_1/s191s/image/upload");
    xhr.send(new FormData(formElement));
};

function readURL(input) {

    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function(e) {
            $('#animal-img').attr('src', e.target.result);
        };

        reader.readAsDataURL(input.files[0]);
    }
}

$("#avatar").change(function() {
    readURL(this);
});


function updatePhoto(url) {

    var photo = {
        photoURL: url
    };

    $.ajax({
        url: HOST + "/animal/" + ANIMAL.animal.id,
        type: "PUT",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(photo),
        beforeSend: function (xhr) {
            if (USER.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + USER.token);
            }
        },
        success: function (data) {
            handleSuccessOperation(UPDATED);
        },
        error: function (data) {

        }
    });
}
