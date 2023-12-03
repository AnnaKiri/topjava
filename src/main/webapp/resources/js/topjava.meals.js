const mealAjaxUrl = "ui/meals/";

$(function () {
    ctx.ajaxUrl = mealAjaxUrl;
    ctx.updateTableFunc = getFilteredTable;
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        })
    );
});

function clearFilter() {
    $("#filter").find(":input").val("");
    updateTable();
}

function updateTableFilter() {
    getFilteredTable();
    successNoty("Filtered");
}

function getFilteredTable() {
    $.ajax({
        type: "GET",
        url: ctx.ajaxUrl + "filter",
        data: $("#filter").serialize()
    }).done(function (data) {
        ctx.datatableApi.clear().rows.add(data).draw();
    });
}