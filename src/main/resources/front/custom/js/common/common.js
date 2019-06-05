function getID(e, tableId) {
    var rowIndex = $(e.target).parent().parent().index() + 1;
    var id = $(tableId + ' tr').eq(rowIndex).find('td').eq(0).html();
    console.log(rowIndex + "   " + id);
    return id;
}

function setDataTable(tableId) {
    var lang = (USER.localization == "UKRAINIAN") ? langUA : langEN;
    if ($('#' + tableId).length) {
        $('#' + tableId).DataTable({
            responsive: true,
            "language": lang
        });
    }
}

function handleError(xhr, type) {
    if (xhr.status == '403' || xhr.status == '401') {
        handle403();
    } else {
        switch (type) {
            case UPDATE: handleCrudError("update");
                break;
            case GET: handleCrudError("get info");
                break;
            case CREATE: handleCrudError("create");
                break;
            case DELETE: handleCrudError("delete");
                break;
            default: handleCrudError("perform operation");
        }
    }
}

function handle403() {
    window.location = 'login.html'
}

function handleCrudError(operation) {
    Swal.fire(
        'BAD!',
        'Can not ' + operation,
        'error'
    )
}

function addSamePasswordValidator() {
    $.validator.addMethod("samePassword", function(value, element) {
        var firstValue = $('#userNewPassword').val();
        return value == firstValue;

    }, "Passwords must be the same");
}

// datatable lang
var langUA = {
    "sProcessing": "Зачекайте...",
    "sLengthMenu": "Показати _MENU_ записів",
    "sZeroRecords": "Записи відсутні.",
    "sInfo": "Записи з _START_ по _END_ із _TOTAL_ записів",
    "sInfoEmpty": "Записи з 0 по 0 із 0 записів",
    "sInfoFiltered": "(відфільтровано з _MAX_ записів)",
    "sInfoPostFix": "",
    "sSearch": "Пошук:",
    "sUrl": "",
    "oPaginate": {
        "sFirst": "Перша",
        "sPrevious": "Попередня",
        "sNext": "Наступна",
        "sLast": "Остання"
    },
    "oAria": {
        "sSortAscending": ": активувати для сортування стовпців за зростанням",
        "sSortDescending": ": активувати для сортування стовпців за спаданням"
    }
};
var langEN = {
    "sEmptyTable": "No data available in table",
    "sInfo": "Showing _START_ to _END_ of _TOTAL_ entries",
    "sInfoEmpty": "Showing 0 to 0 of 0 entries",
    "sInfoFiltered": "(filtered from _MAX_ total entries)",
    "sInfoPostFix": "",
    "sInfoThousands": ",",
    "sLengthMenu": "Show _MENU_ entries",
    "sLoadingRecords": "Loading...",
    "sProcessing": "Processing...",
    "sSearch": "Search:",
    "sZeroRecords": "No matching records found",
    "oPaginate": {
        "sFirst": "First",
        "sLast": "Last",
        "sNext": "Next",
        "sPrevious": "Previous"
    },
    "oAria": {
        "sSortAscending": ": activate to sort column ascending",
        "sSortDescending": ": activate to sort column descending"
    }
};