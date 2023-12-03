const userAjaxUrl = "admin/users/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: userAjaxUrl
};

// $(document).ready(function () {
$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "name"
                },
                {
                    "data": "email"
                },
                {
                    "data": "roles"
                },
                {
                    "data": "enabled"
                },
                {
                    "data": "registered"
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
                    "asc"
                ]
            ]
        })
    );

    $(".enabled").click(function () {
        enableStatusChange($(this));
    })
});

function enableStatusChange(checkbox) {
    let id = checkbox.closest('tr').attr("id");
    let value = checkbox.prop('checked');

    $.ajax({
        type: "POST",
        url: ctx.ajaxUrl + id + "/enable",
        data: "enabled=" + value
    }).done(function () {
        if (value) {
            successNoty("Record enabled");
        } else {
            successNoty("Record disabled");
        }
    });

}


