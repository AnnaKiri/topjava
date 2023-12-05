const userAjaxUrl = "admin/users/";

// $(document).ready(function () {
$(function () {
    ctx.ajaxUrl = userAjaxUrl;
    ctx.updateTableFunc = getFullTable;
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

    updateRowsOpacity();
});

function enableStatusChange(checkbox) {
    let row = checkbox.closest('tr');
    let id = row.attr("id");
    let isChecked = checkbox.prop('checked');
    $.ajax({
        type: "PATCH",
        url: ctx.ajaxUrl + id,
        contentType: "application/json",
        data: JSON.stringify({enabled: isChecked})
    }).done(function () {
        row.attr('data-user-enabled', checkbox.is(':checked').toString());
        successNoty(isChecked ? "Record enabled" : "Record disabled");
    }).fail(function () {
        checkbox.prop('checked', !isChecked);
    });
}

function updateRowsOpacity() {
    $('#datatable .enabled').each(function () {
        $(this).closest('tr').attr('data-user-enabled', $(this).is(':checked').toString());
    });
}
