const ACTIVE_INACTIVE = "active_inactive";
const RATED_START_RATING = "rated_start_rating";
const OPTIONAL_COMPULSARY = "optional_compulsary";
const GENERAL_URGENT = "general_urgent";
const YES_NO = "yes_no";
const PAID_UNPAID = "paid_unpaid";
const OFFERED_REFUSAL = "offered_refusal";
const PENDING_RETURNED = "pending_returned";
const PENDING_APPROVED = "pending_approved";
const ISSUED_RETURNED = "issued_returned";
const SALES_RETURNED = "sales_returned";
const BLOCKED_UNBLOCKED = "blocked_unblocked";
const WAITING_STARTED_ENDED = "waiting_started_waiting";
const PENDING_APPROVED_REJECTED = "pending_approved_rejected_cancelled_forwarded";
const JOB_APPLICATION_STATUS = "job_application_status";
const PENDING_ONPROGRESS_SOLVED = "pending_onprogress_solved";
const LOW_NORMAL_HIGH_URGENT = "low_normal_high_urgent";
const QUESTION_INCIDENT_PROBLEM_TASK = "question_incident_problem_task";
const PUBLIC_PRIVATE = "public_private";
const DEFAULT_DATE = "2075-01-01";
const CANCELLED_ASSIGNED = "cancelled_assigned";
const OPEN_CLOSE = "open_close";
const ACTIVE_BLOCK = "active_block";
const PUBLISHED_UNPUBLISHED = "published_unpublished";
const SUPPORT_DEPARTMENT_ID = 1;
const SALARY_TYPES_SELECT_BOX = `<div class="select-outline mt-0">
                                    <select name="type" class="mdb-select md-form md-outline colorful-select dropdown-primary mt-0 no-search">
                                      <option value="Monthly" selected>Monthly</option>
                                  </select>
                                </div>`;
const CALL_STATUS = "call-status";

let MUNICIPALITY_REPORT_DEFAULT_TABLE_OPTIONS = {
    "scrollX": true,
    "ordering": false,
    "responsive": false
};

const exportButtons = {
    'copy': {
        extend: 'copyHtml5',
        text: '<i class="fa fa-files-o"></i>',
        titleAttr: 'Copy to clipboard',
        exportOptions: {
            columns: ':not(:last-child)',
        }
    },
    'excel': {
        extend: 'excelHtml5',
        text: '<i class="fa fa-file-excel-o"></i>',
        titleAttr: 'Export to excel',
        exportOptions: {
            columns: ':not(:last-child)',
        },
        fileName: "text"
    },
    'csv': {
        extend: 'csvHtml5',
        text: '<i class="fa fa-file-text-o"></i>',
        titleAttr: 'Export to CSV',
        exportOptions: {
            columns: ':not(:last-child)',
        }
    },
    'pdf': {
        extend: 'pdfHtml5',
        text: '<i class="fa fa-file-pdf-o"></i>',
        titleAttr: 'Generate PDF',
        exportOptions: {
            columns: ':not(:last-child)',
        }
    },
    'print': {
        extend: 'print',
        text: '<i class="fa fa-print"></i>',
        titleAttr: 'Print',
        exportOptions: {
            columns: ':not(:last-child)',
        }
    }
}
const defaultButtons = Object.values(exportButtons);

let REPORT_DEFAULT_TABLE_OPTIONS = {
    "scrollX": true,
    "ordering": false,
    "responsive": false
};

const FILE_INVALID_REPONSE = {"success": false, "isImage": false};
const IMAGE_FILE_EXTENSIONS = ['jpg', 'jpeg', 'png'];
const VALID_EXTENTIONS = ["pdf", "xls", "xlsx", "ppt", "pptx", "docx", "doc", "jpg", "jpeg", "png"];

$(function () {

    let totalBoxes = $(".box").length;
    if (totalBoxes > 1) $(".box").eq(totalBoxes - 1).addClass("no-mb");

    $("table.advanced_table, table.awesome-table, .dtMdb").not(".marksheet table").addClass("fs-12 table-bordered");
    $("form").find("table").removeClass("table-striped");

    // SideNav Initialization
    // initSideNav();

    //init tooltip
    initTooltip();

    $.each($(".init-select2"), function () {
        const placeholder = $(this).attr("placeholder");
        $(this).select2(placeholder ? {placeholder} : {});
    });

    $(".needs-validation").find(".mandatory").not(".always-display").remove();
    $("input[type=hidden]").attr("autocomplete", "off");
    setRequired($(".needs-validation").find("[required]"));
    $(".disabled .mandatory").remove();
    $("[name=remarks]").attr("maxlength", 500);

    // initialize drag and drop file upload
    if ($.isFunction($.fn.file_upload)) {
        $('.file-upload').file_upload();
    }

    //trigger clone on child instance
    $(document).on("keyup change", ".cloneParent", function () {
        cloneTo(this);
    });

    /*$(".cloneParent").on("keyup change", function () {
        cloneTo(this);
    });*/

    setTimeout(function () {
        $(".alert").not(".always-display").remove();
    }, 10000);
});

function transformToEmployee(id, name, code, designationName) {
    return {
        id,
        name,
        staffCode: code,
        jobTitle: designationName
    };
}

function getDisplayableStaffName(staff, isPureObject = true) {
    const staffDetailRoute = baseUrl + "staff/" + (isPureObject ? staff.id : getValue(staff.staffId, staff.id)) + "/info",
        jobTitle = isPureObject ? (staff.staffJobTitle ? staff.staffJobTitle.jobTitle.name : '') : getValue(staff.jobTitle);
    return `<a target="_blank" href="${staffDetailRoute}">${isPureObject ? staff.name : getValue(staff.staffName, staff.name)}${staff.staffCode ? ` (${staff.staffCode})` : ''}</a><br><span class="text-nowrap" style="font-size: 11px;">${jobTitle}</span>`;
}

// spread operator equivalent(required duet to es6 spread operator not supported by edge)
function concat(item1, item2) {
    const isArray = Array.isArray(item2);
    let tempObj;
    if (isArray) {
        tempObj = item1.concat(item2);
    } else {
        tempObj = item1;
        for (key in item2) {
            tempObj[key] = item2[key];
        }
    }
    return tempObj;
}

function setRequired(instance) {
    $(instance).each(function () {
        resetError(this);
        $(this).attr("required", "");
        const selectOutline = $(this).closest(".select-outline");  //handling required for select box
        const instanceWrapper = selectOutline.length ? selectOutline : $(this).parent();
        $(instanceWrapper).find('.mandatory').remove();
        $(instanceWrapper).find('label').append("<span class='mandatory'>*</span>");
    });
}

function removeRequired(instance) {
    $(instance).each(function () {
        resetError(this);
        $(this).removeAttr("required");
        const selectOutline = $(this).closest(".select-outline");  //handling required for select box
        const instanceWrapper = selectOutline.length ? selectOutline : $(this).parent();
        $(instanceWrapper).find('.mandatory').remove();
    });
    // $(instance).removeAttr("required").parent().find('.mandatory').remove();
}

function initTooltip(instance = null) {
    const tooltipInstance = instance ? instance : '[data-toggle="tooltip"]';
    $(tooltipInstance).tooltip();
}

function initSideNav() {
    if ($.isFunction($.fn.sideNav)) {

        if ($("#sidebarMenuContainer").children('.collapsible-header')) {
            $('.collapsible').collapsible();
        }

        // SideNav Initialization
        $(".button-collapse").sideNav();

        new WOW().init();

        var container = document.querySelector('.custom-scrollbar');
        var ps = new PerfectScrollbar(container, {
            wheelSpeed: 2,
            wheelPropagation: true,
            minScrollbarLength: 20
        });
    }
}

function disableFormFields() {
    // $(".disabled input:not([type=radio], [type=checkbox]), .disabled textarea").prop("readonly", true);
    $(".disabled").find("input, textarea").prop("readonly", true);
}

function setModalTitle(modalInstance, title) {
    $(modalInstance).find(".modal-title").html(title);
}

function setModalTitleAndButtonText(modalInstance, title, buttonText) {
    $(modalInstance).find(".modal-title").html(title);
    $(modalInstance).find("#btnSubmit").html(buttonText);
}

function setModalSubmitButton(modalInstance, title, icon) {
    setModalButton($(modalInstance), title, icon ? icon : SAVE_ICON);
}

function setModalButton(modalInstance, title, icon) {
    $(modalInstance).find(".modal-footer button").html(`<i class="material-icons">${icon}</i> ${title}`);
}

//export buttons for excel,pdf,...
function initExportButtons(table, tableToolsInstance = $(".table-tools"), isMdbootstrap = false, exportObj = null) {
    if (table.length === 0) return;

    let expButtons = [];
    if (exportObj && exportObj.buttons && exportObj.buttons.length) {
        exportObj.buttons.forEach(function (key, index) {
            let button = exportButtons[key];
            if (exportObj.filename) button.filename = exportObj.filename;
            if (exportObj.exportAllColumns) button.exportOptions = null;
            if (exportObj.footer) button.footer = true;
            expButtons.push(exportButtons[key]);
        });
    } else expButtons = Object.values(exportButtons);
    var buttons = new $.fn.dataTable.Buttons(table, {buttons: expButtons});

    if (isMdbootstrap) {
        const filterRow = table.closest(".dataTables_wrapper").find(">div.row:first-child");
        filterRow.find(">div").addClass("col-lg-4");
        filterRow.find(".dataTables_filter").addClass("text-right text-lg-center");
        filterRow.append("<div class='col-md-12 col-lg-4 pb-3 text-center text-lg-right order-first order-lg-last'></div>").find(">div:last-child").append(buttons.container()).find('.dt-buttons .btn').tooltip();
    } else {
        $(".table-tools").html(buttons.container());
        $('.dt-buttons .btn').tooltip();
    }
}

function reInitDataTable(tableInstance, tableToolsInstance) {
    initDataTable(tableInstance, tableToolsInstance);
}

function initDataTable(instance, exportButtons = true, options) {
    $(instance).find("thead th:not(.sortable):not(:first-child):not(:nth-child(2)), thead td:not(.sortable):not(:first-child):not(:nth-child(2)), thead tr:not(.sortable):not(:first-child) th, thead tr:not(.sortable):not(:first-child) td").addClass("no-sort");
    const defaultOptions = {
        "lengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]],
        responsive: true,
        ordering: true,
        columnDefs: [
            {
                orderable: false,
                targets: "no-sort"
            },
            {
                className: "text-center",
                targets: [0, -1]
            }]
    };

    if ($(window).width() < 768) {
        if (options && options.fixedColumns) options.fixedColumns = null;
    }

    var table = $(instance).dataTable(concat(defaultOptions, options));

    let isMdbootstrap = $("main").length > 0;

    if (isMdbootstrap) {
        //material table pagelength and search layout
        const filterRow = table.closest(".dataTables_wrapper").find(">div.row:first-child");
        filterRow.find(">div").addClass("pb-3");

        filterRow.find('label').each(function () {
            $(this).parent().append($(this).children());
        });
        const dataTableLength = filterRow.find(".dataTables_length");
        const dataTableFilter = filterRow.find(".dataTables_filter");
        const searchContainer = $(".append-dataTable-search");

        if (searchContainer.length) {
            searchContainer.html("");
            searchContainer.append($(".dataTables_filter"));
            filterRow.remove();
        }

        dataTableFilter.addClass('md-form').find("input").attr("placeholder", "Search").removeClass('form-control-sm');
        dataTableLength.addClass('d-flex flex-row').find("select").removeClass('custom-select custom-select-sm form-control form-control-sm').addClass('mdb-select');

        if (dataTableLength.find("select").length) initMDBSelect(dataTableLength.find("select"));
        dataTableFilter.find("label").remove();

        //append horizontal scrollbar at bottom of screen in case of horizontal scrollable table
        appendHorizontalScrollBar(".dataTables_scrollBody table", table);
    }

    if (exportButtons) {
        let exportObj = options && options.export ? options.export : null;
        initExportButtons(table, exportButtons, isMdbootstrap, exportObj);
    }

    //re-init tooltip if table contains elements having tooltip
    if ($(instance).find("[data-toggle=tooltip]").length > 0) initTooltip();

    return table;
}

function destroyDataTable(instance) {
    if ($.fn.DataTable.fnIsDataTable(instance)) {
        $(instance).find("tbody").html("");
        $(instance).DataTable().destroy();
    }
}

function capitalizeFirstLetter(text) {
    return ucFirst(text);
}

function ucFirst(text) {
    return text.charAt(0).toUpperCase() + text.slice(1);
}

function getDisplayableStatus(status, type, isMDBootstrap = false) {
    isMDBootstrap = isMdBootstrap();
    let className = "", text = "";

    if (status) {
        className = (isMDBootstrap ? "badge" : "label") + "-success";
        if (type === YES_NO) text = "Yes";
        else if (type === PENDING_RETURNED) text = "RETURNED";
        else if (type === OPTIONAL_COMPULSARY) text = "COMPULSARY";
        else if (type === GENERAL_URGENT) text = "URGENT";
        else if (type === PENDING_APPROVED) text = "APPREOVED";
        else if (type === BLOCKED_UNBLOCKED) text = "BLOCKED";
        else if (type === ISSUED_RETURNED) text = "ISSUED";
        else if (type === SALES_RETURNED) text = "SALES";
        else if (type === PAID_UNPAID) text = "PAID";
        else if (type === PUBLIC_PRIVATE) text = "PUBLIC";
        else text = "Active";
    } else {
        className = (isMDBootstrap ? "badge" : "label") + "-danger";
        if (type === YES_NO) text = "No";
        else if (type === PENDING_RETURNED) text = "PENDING";
        else if (type === OPTIONAL_COMPULSARY) text = "OPTIONAL";
        else if (type === GENERAL_URGENT) text = "";
        else if (type === PENDING_APPROVED) text = "PENDING";
        else if (type === BLOCKED_UNBLOCKED) text = "UNBLOCKED";
        else if (type === ISSUED_RETURNED) text = "RETURNED";
        else if (type === SALES_RETURNED) text = "RETURNED";
        else if (type === PAID_UNPAID) text = "UNPAID";
        else if (type === PUBLIC_PRIVATE) text = "PRIVATE";
        else text = "In-Active";
    }

    return "<label class='text-uppercase " + (isMDBootstrap ? "badge " : "label ") + className + "'>" + text + "</label>";
}

function getDisplayableNoticeStatus(status) {
    if (!status) return "";
    let text = "Urgent";
    return `<label data-toggle="tooltip" title="${text}" class="badge badge-danger" style="border-radius: 50%">&nbsp;</label>`;
}

function getDisplayableStatusApproved(status, type, isMDBootstrap = false) {
    let className = "", text = "";
    isMDBootstrap = isMdBootstrap();

    if (status === 'Approved' || status === 'Selected') {
        className = (isMDBootstrap ? "badge" : "label") + "-success";
        if (type === PENDING_APPROVED_REJECTED) text = status;
    } else if (status === 'Verify') {
        className = (isMDBootstrap ? "badge" : "label") + "-success";
        if (type === PENDING_APPROVED_REJECTED) text = "Verified";
    } else if (status === 'Pending' || status === 'Postponed') {
        className = (isMDBootstrap ? "badge" : "label") + "-warning";
        if (type === PENDING_APPROVED_REJECTED) text = status;
    } else if (status === 'Checked') {
        className = (isMDBootstrap ? "badge" : "label") + "-success";
        if (type === PENDING_APPROVED_REJECTED) text = "Checked";
    } else if (status === 'Forwarded') {
        className = (isMDBootstrap ? "badge" : "label") + "-warning";
        if (type === PENDING_APPROVED_REJECTED) text = "Forwarded";
    } else if (status === 'Cancelled') {
        className = (isMDBootstrap ? "badge" : "label") + "-danger";
        if (type === PENDING_APPROVED_REJECTED) text = "Cancelled";
    } else if (status === 'Terminated') {
        className = (isMDBootstrap ? "badge" : "label") + "-warning";
        if (type === PENDING_APPROVED_REJECTED) text = "Terminated";
    } else if (status === 'Recommended') {
        className = (isMDBootstrap ? "badge" : "label") + "-warning";
        if (type === PENDING_APPROVED_REJECTED) text = "Recommended";
    } else {
        className = (isMDBootstrap ? "badge" : "label") + "-danger";
        if (type === PENDING_APPROVED_REJECTED) text = "Rejected";
    }

    return "<label class='" + (isMDBootstrap ? "badge " : "label ") + className + "'>" + text.toUpperCase() + "</label>";
}

function getDisplayableCallStatus(status, type, isMDBootstrap = false) {
    let className = "", text = "";
    isMDBootstrap = isMdBootstrap();

    if (status === 'Completed') {
        className = (isMDBootstrap ? "badge" : "label") + "-success";
        if (type === CALL_STATUS) text = status;
    } else if (status === 'Busy') {
        className = (isMDBootstrap ? "badge" : "label") + "-warning";
        if (type === CALL_STATUS) text = status;
    } else if (status === 'No-answer') {
        className = (isMDBootstrap ? "badge" : "label") + "-secondary";
        if (type === CALL_STATUS) text = status;
    } else if (status === 'Cancelled') {
        className = (isMDBootstrap ? "badge" : "label") + "-danger";
        if (type === CALL_STATUS) text = "Cancelled";
    } else if (status === 'Not-reachable') {
        className = (isMDBootstrap ? "badge" : "label") + "-light";
        if (type === CALL_STATUS) text = "Not-reachable";
    }

    return "<label class='" + (isMDBootstrap ? "badge " : "label ") + className + "'>" + text.toUpperCase() + "</label>";
}

function getDisplayableZoomStatus(status, type, isMDBootstrap = false) {
    if (!status) return "-";

    let className = "", text = "";
    isMDBootstrap = isMdBootstrap();

    status = status.toLowerCase();
    if (status === 'started') {
        className = (isMDBootstrap ? "badge" : "label") + "-success";
        if (type === WAITING_STARTED_ENDED) text = "Started";
    } else if (status === 'waiting') {
        className = (isMDBootstrap ? "badge" : "label") + "-warning";
        if (type === WAITING_STARTED_ENDED) text = "Waiting";
    } else if (status === 'ended') {
        className = (isMDBootstrap ? "badge" : "label") + "-danger";
        if (type === WAITING_STARTED_ENDED) text = "Ended";
    }

    return "<label class='" + (isMDBootstrap ? "badge " : "label ") + className + "'>" + text.toUpperCase() + "</label>";
}


/**
 * set color for job-application order according to status
 * @param status
 * @param type
 * @param isMDBootstrap
 * @returns {string}
 */
function getJobApplicationDisplayableStatus(status, type, isMDBootstrap = false) {
    let className = "", text = "";
    isMDBootstrap = isMdBootstrap();

    if (status === 'Applied') {
        className = (isMDBootstrap ? "badge" : "label") + "-secondary";
        if (type === JOB_APPLICATION_STATUS) text = "Applied";
    } else if (status === 'Pending') {
        className = (isMDBootstrap ? "badge" : "label") + "-warning";
        if (type === JOB_APPLICATION_STATUS) text = "Pending";
    } else if (status === 'Interview') {
        className = (isMDBootstrap ? "badge" : "label") + "-success";
        if (type === JOB_APPLICATION_STATUS) text = "Interview";
    } else if (status === 'Hired') {
        className = (isMDBootstrap ? "badge" : "label") + "-primary";
        if (type === JOB_APPLICATION_STATUS) text = "Hired";
    } else if (status === 'Rejected') {
        className = (isMDBootstrap ? "badge" : "label") + "-danger";
        if (type === JOB_APPLICATION_STATUS) text = "Rejected";
    } else {
        className = (isMDBootstrap ? "badge" : "label") + "-danger";
        if (type === JOB_APPLICATION_STATUS) text = "Expired";
    }

    return "<label class='" + (isMDBootstrap ? "badge " : "label ") + className + "'>" + text.toUpperCase() + "</label>";
}


function getDisplayable(status, type, isMDBootstrap = false) {
    let className = "", text = "";
    isMDBootstrap = isMdBootstrap();

    if (status === 'Assigned') {
        className = (isMDBootstrap ? "badge" : "label") + "-success";
        if (type === CANCELLED_ASSIGNED) text = status;
    } else if (status === 'Cancelled') {
        className = (isMDBootstrap ? "badge" : "label") + "-danger";
        if (type === CANCELLED_ASSIGNED) text = "Cancelled";
    } else {
        if (type === CANCELLED_ASSIGNED) text = "";
    }

    return "<label class='" + (isMDBootstrap ? "badge " : "label ") + className + "'>" + text.toUpperCase() + "</label>";
}

function getDisplayableOpenClose(status, type, isMDBootstrap = false) {
    isMDBootstrap = isMdBootstrap();
    let className = "", text = "";

    if (status) {
        if (type === OPEN_CLOSE)
            text = "CLOSED";
        className = (isMDBootstrap ? "badge" : "label") + "-danger";
    } else {
        text = "OPEN";
        className = (isMDBootstrap ? "badge" : "label") + "-success";
    }

    return "<label class='text-uppercase " + (isMDBootstrap ? "badge " : "label ") + className + "'>" + text + "</label>";
}

function getDisplayableOfferedRefusal(status, type, isMDBootstrap = false) {
    isMDBootstrap = isMdBootstrap();
    let className = "", text = "";

    if (status) {
        if (type === OFFERED_REFUSAL)
            text = "Offered";
        className = (isMDBootstrap ? "badge" : "label") + "-success";
    } else {
        text = "Refusal";
        className = (isMDBootstrap ? "badge" : "label") + "-danger";
    }

    return "<label class='text-uppercase " + (isMDBootstrap ? "badge " : "label ") + className + "'>" + text + "</label>";
}

function getDisplayableOpenClose(status, type, isMDBootstrap = false) {
    isMDBootstrap = isMdBootstrap();
    let className = "", text = "";

    if (status) {
        if (type === OPEN_CLOSE)
            text = "CLOSED";
        className = (isMDBootstrap ? "badge" : "label") + "-danger";
    } else {
        text = "OPEN";
        className = (isMDBootstrap ? "badge" : "label") + "-success";
    }

    return "<label class='text-uppercase " + (isMDBootstrap ? "badge " : "label ") + className + "'>" + text + "</label>";
}

function getDisplayablePublishUnpublishResult(status, type, isMDBootstrap = false) {
    isMDBootstrap = isMdBootstrap();
    let className = "", text = "";

    if (status) {
        if (type === PUBLISHED_UNPUBLISHED)
            text = "RESULT PUBLISHED";
        className = (isMDBootstrap ? "badge" : "label") + "-success";
    } else {
        text = "NOT PUBLISHED YET";
        className = (isMDBootstrap ? "badge" : "label") + "-danger";
    }

    return "<label class='text-uppercase " + (isMDBootstrap ? "badge " : "label ") + className + "'>" + text + "</label>";
}

function getDisplayableStatusPendingApproved(status, type, isMDBootstrap = false) {
    isMDBootstrap = isMdBootstrap();
    let className = "", text = "";

    if (status) {
        if (type === PENDING_APPROVED)
            text = "APPROVED";
        className = (isMDBootstrap ? "badge" : "label") + "-success";
    } else {
        text = "PENDING";
        className = (isMDBootstrap ? "badge" : "label") + "-warning";
    }

    return "<label class='text-uppercase " + (isMDBootstrap ? "badge " : "label ") + className + "'>" + text + "</label>";
}

function getValue(value, defaultValue = "") {
    return value ? value : defaultValue;
}

function calculatePercentage(total, owned) {
    if (owned && total) {
        return (parseFloat(parseFloat(owned) / parseFloat(total)) * 100).toFixed(2);
    }

    return 0;
}

function calculatePercentageAmount(total, percentage) {
    if (total && percentage) {
        return parseFloat(parseFloat(parseFloat(percentage) * parseFloat(total)) / 100).toFixed(2)
    }

    return 0;
}

function resetForm(instance, isMDBootstrap = false) {
    $("select.mdb-select option[selected]", instance).attr("selected", false);
    $("select.mdb-select option[disabled]", instance).attr({"disabled": false, "data-disabled": true});
    $("select.mdb-select option[default-selected]", instance).attr("selected", true);

    $(instance).removeClass("was-validated").trigger("reset");
    $(".file-upload .btn-danger", instance).trigger('click');

    $("select.mdb-select option[data-disabled]", instance).attr("disabled", true);

    resetError($(instance).find(".form-control"));

    $("input.form-control, textarea.form-control", instance).each(function () {
        if (!$(this).val()) $(this).closest(".md-form").find("label").removeClass("active");
    });

    //reset search dropdown in select box
    try {
        $("ul.select-dropdown .search", instance).trigger("keyup");
    } catch (e) {
        console.log(e);
    }
    // console.log($("ul.select-dropdown .search", instance));

    //clear value of ckeditor instances if available
    /*if (CKEDITOR.instances) {
        for (instance in CKEDITOR.instances)
            CKEDITOR.instances[instance].setData('');
    }*/

    $(instance).find("input[type=hidden][name=id]").val("");

    if (!isMdBootstrap()) {
        $(instance).find("select").trigger("change");
        $(instance).find("label.error").remove();
        $(instance).find("*").removeClass("error");
    } else {
        // handling reverse click issue on item with already checked after modal reopen for optgroup
        $(instance).find("select[multiple]").each(function () {
            if ($(this).find("optgroup").length) {
                destroyMDBSelect(this);
                initMDBSelect(this);
            }
        });
    }

    $(".toggleStatus").trigger("change");
}

function ordinalSuffixOf(i) {
    var j = i % 10, k = i % 100;
    if (j == 1 && k != 11) return "st";
    if (j == 2 && k != 12) return "nd";
    if (j == 3 && k != 13) return "rd";
    return "th";
}

function triggerChange(instance, status = false) {
    if (status) {
        if ($(instance).attr('name') && $(instance).attr('name').length) {
            $(instance).trigger('change');
        }
    }
}

function updateLabel(element) {
    resetError(element);
}

function wrapActionButtons(actionButtons, isCustomList = false) {
    const tag = isCustomList ? "div" : "td";
    return `<${tag} class="btn-actions">
                <div class="dropdown action-dropdown">
                    <button class="btn" type="button" data-toggle="dropdown">
                    <i class="material-icons icon_more">${isCustomList ? 'more_vert' : 'more_horiz'}</i>
                    </button>
                    <ul class="dropdown-menu dropdown-primary">${actionButtons}</ul>
                </div>
               </${tag}>`;
}

function generateActionButton(name, icon, href, attributes) {
    let additionalClass = name === DELETE_TEXT ? ' text-danger ' : '', attrs = "";
    if (attributes) {
        $.each(attributes, function (key, value) {
            if (key === "class") additionalClass += value;
            else attrs += key + '="' + value + '" ';
        })
    }
    return "<li><a href='" + (href ? href : '#') + "' class='dropdown-item " + additionalClass + "' " + (attrs) + "><i class=\"material-icons\">" + icon + "</i> " + name + "</a></li>";
}

function reloadPage() {
    window.location.reload();
}

function getVal(map, key) {
    if (map.has(key)) return map.get(key);
    return 0;
}

function getStatusValue(value) {
    return value != null ? (value ? [1, 0] : [0, 1]) : [0, 0];
}

/**
 *
 * @param tableInstance
 */
function reArrangeSN(tableInstance, snIndex = 0) {
    $(tableInstance).find("tbody tr:not(.d-none.sn-ignore)").each(function (index) {
        $(this).find("td:eq(" + snIndex + ")").html((index + 1) + ".");
    });
}

function generateDTSetting(rowsGroup, leftColumns = 2, rightColumns = 1) {
    let fixedColumnsOptions = {};
    if (leftColumns || rightColumns) {
        fixedColumnsOptions = {
            'scrollX': true,
            scrollCollapse: true,
            'fixedColumns': {
                'leftColumns': leftColumns,
                'rightColumns': rightColumns
            },
        }
    }
    let defaultdTSetting = {
        "responsive": false,
        ordering: false,
        rowsGroup: rowsGroup
    };

    let dTSetting = concat(defaultdTSetting, fixedColumnsOptions);

    return dTSetting;
}

// to check input value of integer type is NAN and if return 0
function getValidNumericInputValue(instance) {
    let value = $(instance).val();
    return !value ? 0 : parseFloat(value);
}

function getUniqueId(prefix) {
    return prefix + $.now();
}

function isMdBootstrap() {
    return $("main").length > 0;
}

function resetError(instance) {
    $(instance).closest(".select-wrapper").find("input,select").removeClass("active error").closest(".select-outline").find("span.error, label.error, .form-error").not(".select-wrapper").remove();
    $(instance).closest(".md-form").find("input,select").removeClass("active error").closest(".md-form").find("span.error, label.error,span.error").not(".select-wrapper").remove();
}


function getSearchParams(params) {
    let searchObject = {};
    let searchParams = new URLSearchParams(window.location.search);
    $.each(params, function (index, key) {
        let value = searchParams.get(key);
        searchObject[key] = value;
    });
    return searchObject;
}


function resizeTableRow(tableInstance, finalTableHeight, rowInstance = 'tbody tr:last-child') {
    $(tableInstance).each(function () {
        let lastRow = $(this).find(rowInstance);
        let tableHeight = $(this).outerHeight();
        let extraHeight = finalTableHeight - tableHeight + lastRow.outerHeight();
        if (extraHeight > 0) lastRow.css({'height': extraHeight});
    });
}

function isItemAlreadySelectedInList(instance) {
    let selectedId = $(instance).find("option:selected").val();
    if (!selectedId) return false;
    let arrayName = [];
    let tableInstance = $(instance).closest("table");
    let tableTrInstance = tableInstance.find("tbody tr");
    $.each(tableTrInstance, function () {
        let id = $(this).find("td select.itemId :selected").val();
        if (id) arrayName.push(parseInt(id));
    });
    arrayName.splice(arrayName.indexOf(parseInt(selectedId)), 1);
    let result = $.inArray(parseInt(selectedId), arrayName) !== -1 ? true : false;
    if (result) {
        showAlert("This Item is already listed in table.", INFO_ALERT);
        updateSelectedOptions(instance, "", true)
    }
    return result;
}

//merging consecutive row pairs
function rowsGroupPairs(tableInstance) {
    if (!$(tableInstance) && $(tableInstance[0].tBodies[0])) return;
    const rows = $(tableInstance)[0].tBodies[0].rows;
    if (rows.length) {
        const columnsLength = rows[0].cells.length;

        //loop throught pairs
        for (let i = 0; i < rows.length; i = i + 2) {
            for (let j = 0; j < columnsLength; j++) {

                let column = rows[i].cells[j];
                let nextColumn = rows[i + 1].cells[j];

                //compare values in two cell pairs and simulate merge
                if ($.trim(column.innerHTML) === $.trim(nextColumn.innerHTML)) {
                    $(column).attr("rowspan", 2);
                    $(nextColumn).addClass("d-none");
                }
            }
        }
    }
}

$("button[type=submit]").click(function (event) {
    if ($(this).hasClass("ignoreValidation")) return;
    let target = $(this).closest("form").attr("target");
    if (!target) {
        event.preventDefault();
        let formInstance = $(this).closest("form");
        console.log(formInstance)
        let isValid = $.isFunction($.fn.isValidForm) ? isValidForm(formInstance) : formInstance.valid();
        if (isValid) {
            formInstance.submit();
            showButtonLoader($(this), PLEASE_WAIT);
        }
    }
});

function redirectPage(url = baseUrl) {
    window.location.href = url;
}

function showModal(modalInstance, shouldResetForm = false, isSelect2 = false) {
    if (shouldResetForm) resetForm($(modalInstance).find("form"));
    if (isSelect2) $(modalInstance).find(".select2").val("").trigger("change");
    $(modalInstance).modal("show");
}

function hideModal(modalInstance) {
    $(modalInstance).modal("hide");
}

function getValues(instance, isNumeric = false) {
    return $.map(instance, function (c) {
        return (isNumeric ? parseInt(c.value) : c.value)
    });
}

// window opening & printing pages on new window
function openWindow(url) {
    if (url) {
        window.open(url);
    }
}

$(function () {
    if (isMdBootstrap()) {
        appendHorizontalScrollBar(".table-responsive table");
    }
});

function appendHorizontalScrollBar(instance) {
    if (navigator.userAgent.indexOf('Mac OS X') != -1 || $(window).width() < 768) {
        return;
    }

    const table = $(instance);
    const tableContainer = table.closest(".table-responsive, .dataTables_scrollBody, .custom-list-main");
    tableContainer.css({overflow: "hidden"});
    const horizontalScrollBar = $(".table-scrollbar-container", tableContainer);

    if (horizontalScrollBar.length) {
        horizontalScrollBar.remove();
    }

    if (table.outerWidth() > tableContainer.outerWidth()) {
        tableContainer.append("<div class='table-scrollbar-container'><div class='table-scroll'></div></div>");
        const tableScrollBarContainer = $(".table-scrollbar-container", tableContainer);

        const tableScrollBar = $(".table-scroll", tableContainer);
        const tableContainerWidth = $(tableContainer).outerWidth();
        tableScrollBarContainer.css({
            width: tableContainerWidth,
            left: "calc(50% - " + (tableContainerWidth / 2) + "px)"
        });
        tableScrollBar.css({width: $(table).outerWidth()});

        let dataTableScroll = tableContainer.closest(".dataTables_scroll");
        if (dataTableScroll.length) $(".dataTables_scrollBody>table", dataTableScroll)[0].style.setProperty('width', $(".dataTables_scrollHeadInner", dataTableScroll).outerWidth() + 'px', 'important');

        $(tableScrollBarContainer).scroll(function () {
            const tableContainer2 = $(this).closest(".dataTables_scroll").length ? $(this).closest(".dataTables_scroll").find(".dataTables_scrollBody, .dataTables_scrollHead, .dataTables_scrollFoot") : $(this).closest(".table-responsive, .custom-list-main");
            tableContainer2.scrollLeft($(this).scrollLeft());
            if ($(".dataTableFixedHeader-container").length) {
                $(".dataTableFixedHeader-container").scrollLeft($(this).scrollLeft());
            }
            $(".select-dropdown,.dropdown-content", tableContainer2).removeClass("active");
            $(".dropdown-content", tableContainer2).hide();
        });
    }
}


// fix select box in scrollable table
$(window).scroll(function () {
    let dropdownContent = $(".table-responsive .dropdown-content.active, .dataTables_scrollBody .dropdown-content.active, .dataTables_scrollHead .dropdown-content.active");
    let selectDropdown = dropdownContent.prev(".select-dropdown");
    if (dropdownContent.length) {
        dropdownContent.css({
            top: selectDropdown.offset().top + selectDropdown.outerHeight() - $(window).scrollTop(),
            left: selectDropdown.offset().left,
        });
    }
});

//chunk array
Object.defineProperty(Array.prototype, 'chunk', {
    value: function (chunkSize) {
        var temporal = [];
        for (var i = 0; i < this.length; i += chunkSize)
            temporal.push(this.slice(i, i + chunkSize));
        return temporal;
    }
});

/**
 * clone value to all related child instances
 * @param instance
 */
function cloneTo(instance) {
    let isAmount = $(instance).attr("is-amount");
    let isSelect2 = $(instance).closest(".select2-outline").length;

    let cloneToParam = $(instance).attr("clone-to");
    let type = $(instance).prop("tagName");
    let val = $(instance).val();
    let cloneMultitpleParam = $(instance).attr("multiple");

    if (type === 'SELECT') {
        if (cloneMultitpleParam === 'multiple') {
            $("select[name='" + cloneToParam + "']").each(function () {
                if (isSelect2) {
                    $(this).val(val).trigger("change");
                } else updateSelectedOptions(this, val);
            });
        } else {
            $("select[name='" + cloneToParam + "']").each(function () {
                if (isSelect2) {
                    $(this).val(val).trigger("change");
                } else updateSelectedOptions(this, [val]);
            });
        }

    } else {
        if (cloneToParam && cloneToParam.search("param=") !== -1) {//in case of custom checkbox toggle
            const finalParam = cloneToParam.replace("param=", "");
            const isChecked = $(instance).is(":checked");
            $(`input[param='${finalParam}']`).prop("checked", isChecked).trigger("change");
        } else {
            let valInstance = $("input[name=" + cloneToParam + "], textarea[name=" + cloneToParam + "]");
            valInstance.val(val);
            if (isAmount) valInstance.trigger("blur");
        }
    }
}

function simpleTrRemove(instance) {
    let trInstance = $(instance).closest("tr");
    let tableInstance = trInstance.closest("table");
    trInstance.remove();
    reArrangeSN(tableInstance);
}

let enableWatermarkText = typeof (showWatermarkText) !== "undefined" ? showWatermarkText : true;
let enableWatermarkLogo = typeof (showWatermarkLogo) !== "undefined" ? showWatermarkLogo : true;

function showHideWatermarkText() {
    $(".watermarktext")[enableWatermarkText ? 'show' : 'hide']();
    $(".with-watermarkLogo")[!enableWatermarkLogo ? 'addClass' : 'removeClass']('logo-hidden');
}

// handle watermark text show/hide
$("#watermarkText,#watermarkLogo").on("change", function () {
    enableWatermarkText = $("#watermarkText").prop("checked");
    enableWatermarkLogo = $("#watermarkLogo").prop("checked");
    showHideWatermarkText();
});

function scrollPage(scrollToInstance) {
    $("html,body").animate({
        scrollTop: $(scrollToInstance).offset().top - headerHeight
    }, 200)
}

function getColorSequence(l) {
    colorArray = [];
    var letters = '0123456789ABCDEF';
    for (var i = 0; i < l; i++) {
        var color = '#';
        for (var j = 0; j < 6; j++) {
            color += letters[((i + 11) * (j + 17)) % 16];
        }
        colorArray.push(color);
    }
    return colorArray;
}

let defaultProfileImage = "", defaultProfileImageMale = "", defaultProfileImageFemale = "";
$(function () {
    defaultProfileImageMale = baseUrl + "resources/mdbootstrap/img/thumb-male.png";
    defaultProfileImageFemale = baseUrl + "resources/mdbootstrap/img/thumb-female.png";
    defaultProfileImage = defaultProfileImageMale;
});

function getStudentProfileImage(profileImage, gender = 'male') {
    if (profileImage) profileImage = baseUrl + "files/student-profile-images/" + profileImage;
    else if (gender === "male") profileImage = defaultProfileImageMale;
    else profileImage = defaultProfileImageFemale;
    return profileImage;
}

function getEmployeeProfileImage(profileImage, gender = 'male') {
    if (profileImage) profileImage = baseUrl + "files/employee-profile-images/" + profileImage;
    else if (gender === "male") profileImage = defaultProfileImageMale;
    else profileImage = defaultProfileImageFemale;
    return profileImage;
}

function getStudentDetailUrl(studentId) {
    if (!studentId) return "";
    return baseUrl + "student/" + studentId + "/view";
}

function getStaffDetailUrl(staffId) {
    if (!staffId) return "";
    return baseUrl + "staff/" + staffId + "/info";
}

function getClassroomDetailUrl(classroomId) {
    if (!classroomId) return "";
    return baseUrl + "classroom/info/" + classroomId;
}

$(document).on("change", "#checkAll", function () {
    if ($(this).hasClass("enable")) toggleCheckAll(this);
});

$(document).on("change", ".check.enable", function () {
    handleSingleCheckToggle();
});

function toggleCheckAll(instance) {
    let isChecked = $(instance).is(":checked");
    $(".check").prop("checked", isChecked);
}

function handleSingleCheckToggle() {
    $("#checkAll").prop("checked", $(".check").length === $(".check:checked").length);
}

// color pickr
function initColorPickr(inputInstance) {
    let color = $(inputInstance).val() ? $(inputInstance).val() : defaultColor;
    const elementId = "#" + $(inputInstance).next("input").attr("id");
    pickr = new Pickr({
        el: elementId,
        default: color,
        components: {
            preview: true,
            hue: true,
            interaction: {
                hex: true,
                rgba: true,
                input: true,
                clear: true,
                save: true,
            }
        },
        onSave(hsva, instance) {
            let newColor = hsva.toHEX().toString();
            $(inputInstance).val(newColor);
            let colorDiplayContainer = $(instance._root.app).closest(".color-container");
            // console.log(instance,$(instance._root.app).closest(".color-container"));
            if (colorDiplayContainer.length && colorDiplayContainer.find(".color-display").length) {
                colorDiplayContainer.find(".color-display").css("background-color", newColor);
            }
        }
    });
}

function destroyColorPickr(inputInstance, setDefault = true, uniquePrefix = "") {
    if (setDefault) {
        $(inputInstance).val("");
    }
    $(inputInstance).next(".pickr").remove();
    $("<input id='colorCode" + uniquePrefix + "' style='display:none'/>").insertAfter(inputInstance);
}

function resetColorPickr(inputInstance, setDefault = true, uniquePrefix = "") {
    destroyColorPickr(inputInstance, setDefault, uniquePrefix);
    initColorPickr(inputInstance);
}

function getAcademicYearStartAndEndDate(academicYearInstance = "#selectAcademicYear") {
    academicYearInstance = $(academicYearInstance);

    let startDate = null, endDate = null;
    if (academicYearInstance.length > 0 && (!academicYearInstance.hasClass("invalidate") || academicYearInstance.hasClass("filter-only"))) {
        let data = $(":selected", academicYearInstance).attr("data");
        if (data) {
            const academicYear = JSON.parse(data.replaceAll(/'/g, '"'))[0];
            startDate = academicYear.start_date ? academicYear.start_date : academicYear.startDate;
            endDate = academicYear.end_date ? academicYear.end_date : academicYear.endDate;
        }
    } //else {
    //return getStartAndEndDateByYear(getCurrentYear(operationDateSetting), operationDateSetting);
    //}
    return {startDate, endDate};
}


/**
 * always returns english date
 * @returns {{endDate: (jQuery|string|undefined), startDate: (jQuery|string|undefined)}}
 */
function getPickedStartAndEndDate() {
    let startDate = $("#start-date").val();
    let endDate = $("#end-date").val();

    if (operationDateSetting === 'np') {
        if (startDate) startDate = BS2AD(startDate);
        if (endDate) endDate = BS2AD(endDate);
    }

    return {startDate, endDate};
}

function getFloatValue(value) {
    if (!value) return 0;
    else return $.trim(value) ? parseFloat(value) : 0;
}

function getGender(gender) {
    if (!gender) return "";
    return gender === "male" ? "Boy" : (gender === "female" ? "Girl" : "Others");
}

function toggleCheckValue(instance) {
    let checkedInstance = $(instance).is(":checked");
    if (checkedInstance) {
        $(instance).val(1);
    } else {
        $(instance).val(0);
    }

}

function hideShowStatus() {
    const status = $("select.status :selected").val(),
        checkedPanelInstance = $(".checkedPanel"),
        statusPanelInstance = $(".statusPanel");

    statusPanelInstance.hide();
    checkedPanelInstance.hide();

    if (status !== "Pending") {
        checkedPanelInstance.show();
        if (status !== "Checked") statusPanelInstance.show();
        $(".statusDisplay").html(status);
    }
}

$(".checkStatus").on('change', function (e) {
    let currentDate = getCurrentDate(operationDateSetting);
    let checkStatusVal = $(this).val();
    let closestInput = $(this).closest('.closestDiv').next('.nextDiv').find('.getRequiredAttr');
    if (checkStatusVal) {
        closestInput.attr('required', true);
        closestInput.val(currentDate);
    } else {
        closestInput.removeAttr('required');
        closestInput.val((""));
    }
});

function getLedgerName(name, code) {
    return name + (code && $.trim(code) ? " (" + $.trim(code) + ")" : "");
}

// reset column width for scrollable table
function resetColumnWidth(tableInstance) {
    const scrollBarWidth = window.innerWidth - document.documentElement.clientWidth;
    let colWidth = [];
    tableInstance.find('thead tr:first').children().map(function () {
        if ($(this).attr("colspan") > 1) {
            $(this).closest("tr").next().children().map(function () {
                colWidth.push($(this).outerWidth());
            });
        } else {
            colWidth.push($(this).outerWidth());
        }
    });

    let tbodyRowChilds = tableInstance.find('tbody tr:first').children();
    tbodyRowChilds.each(function (i, v) {
        let width = colWidth[i];
        let colspan = $(this).attr("colspan");
        if (colspan > 1) {
            for (let j = 1; j < colspan; j++) width += colWidth[j];
        }
        $(v).css("width", (tbodyRowChilds.length - 1) == i ? width - scrollBarWidth : width);
    });

    tableInstance.find('tfoot tr:first').children().each(function (i, v) {
        let width = colWidth[i];
        let colspan = $(this).attr("colspan");
        if (colspan > 1) {
            for (let j = 1; j < colspan; j++) width += colWidth[j];
        }
        $(v).css("width", width);
    });
}

$(window).resize(function () {
    $(".scrollable-table").each(function () {
        resetColumnWidth($(this));
    });
});

$(function () {
    resetColumnWidth($(".scrollable-table"));
});

function getGenderForStudent(gender) {
    let finalGender = "Others";
    if (gender === "male") finalGender = "Boy";
    else if (gender === "female") finalGender = "Girl";
    return finalGender;
}

function isImage(file) {
    if (!file) return false;
    let imageFileExtension = ['jpeg', 'jpg', 'png', 'gif'];
    return $.inArray(file.split('.')[1].toLowerCase(), imageFileExtension) !== -1;
}

const adMonths = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
const bsMonths = ["Baishakh", "Jestha", "Aashadha", "Shrawan", "Bhadra", "Ashwin", "Kartik", "Mangsir", "Paush", "Magh", "Falgun", "Chaitra"];

function convertDateToWords(date, operationDateSetting = 'en') {
    if (!date) return "";
    let tempDate = date.split("-");
    let year = parseInt(tempDate[0]);
    let day = parseInt(tempDate[2]);
    let month = operationDateSetting === "np" ? bsMonths[Number(tempDate[1]) - 1] : adMonths[Number(tempDate[1]) - 1];
    return day + "<sup>" + ordinalSuffixOf(day) + "</sup>" + " " + month + ", " + year;
}

function getReadableDateTime(dateTime, isTimeRequired = true) {
    const finalDateTime = getFinalDate(dateTime, isTimeRequired);
    if (!finalDateTime) return "";

    let tempDate = finalDateTime.split("-");
    let year = parseInt(tempDate[0]);
    let day = parseInt(tempDate[2]);
    let time = getReadableTime(dateTime.split(" ")[1]);
    let month = operationDateSetting === "np" ? bsMonths[Number(tempDate[1]) - 1] : adMonths[Number(tempDate[1]) - 1];

    return `<div>
                ${isTimeRequired ? `<span>${time}</span><br>` : ''}
                <span class="${isTimeRequired ? 'text-muted' : ''}">${day} <sup>${ordinalSuffixOf(day)}</sup> ${month}, ${year}</span>
            </div>`;
}

/**
 * convert 24-hour time-of-day string to 12-hour time with AM/PM and no timezone
 * @param time
 * @returns {string}
 */
function getReadableTime(time) {
    // Check correct time format and split into components
    time = time.toString().match(/^([01]\d|2[0-3])(:)([0-5]\d)(:[0-5]\d)?$/) || [time];

    if (time.length > 1) { // If time format correct
        time = time.slice(1);  // Remove full string match value
        time[5] = +time[0] < 12 ? ' AM' : ' PM'; // Set AM/PM
        time[0] = +time[0] % 12 || 12; // Adjust hours
    }
    return time.join(''); // return adjusted time or original string
}

function fileValidation(file, isPost = false) {
    let message, length = file.files.length, validFile = true;
    for (let i = 0; i < length; i++) {
        let fileName = file.files[i].name, extension = fileName.split(".")[1], size = file.files[i].size;
        if (extension !== 'gif' && isImage(fileName) && size > (isPost ? 2048000 : 5120000))
            validFile = false;
        else if ((extension === 'gif' || !isImage(fileName)) && size > (isPost ? 8192000 : 2048000))
            validFile = false;
    }

    if (!validFile) message = (length > 1 ? "Some of the" : "Selected ") + "  file size is more than allowed size" + (length > 1 ? " and will be ignored while saving." : ". Please select valid file.");
    if (!validFile && length === 1) $(file).val("");

    if (length > 5) {
        message = "You've selected more number of files than allowed size. Please select valid number of files."
        $(file).val("");
    }

    showAlert(message, INFO_ALERT);
}

function getDisplayableActiveOrBlock(status, type, isMDBootstrap = false) {
    isMDBootstrap = isMdBootstrap();
    let className = "", text = "";
    if (status) {
        if (type === ACTIVE_BLOCK) {
            text = "active";
            className = (isMDBootstrap ? "badge" : "label") + "-success";
        }
    } else {
        text = "blocked";
        className = (isMDBootstrap ? "badge" : "label") + "-danger";
    }

    return "<label class='text-uppercase " + (isMDBootstrap ? "badge " : "label ") + className + "'>" + text + "</label>";
}

function saveAndExist(isSaveOrExit) {
    if (isSaveOrExit) $("#generatePrint").val(1);
    else $("#generatePrint").val(0);
}

function initCustomToggle(defaultClass = "toggleStatus") {
    $(`.${defaultClass}`).each(function () {
        let defaultVal = $(this).is(":checked") ? 1 : 0;
        let param = $(this).attr("param");
        if (!param) param = 'status';

        let input = `<input type="hidden" name="` + param + `" value="` + defaultVal + `" autocomplete="off">`;
        $(this).closest("div").append(input);
    });
}

function setCursorToEnd(input) {
    if (input.val()) {
        let strLength = input.val().length * 2;
        input.focus();
        input[0].setSelectionRange(strLength, strLength);
    }
}

function saveSeenNotice(noticeId, userType) {
    let url = baseUrl + "notice-event/save-seen-notice";
    $.post(url, {noticeId, userType})
        .done((response) => {
            let data = response.body;
        })
        .fail((xhr, status, error) => {
            showAlert(SOMETHING_WENT_WRONG, false);
        })
        .always(() => {

        })
}

function getSelectedValues(instance, removeZeroOrEmptyValue = true) {
    const values = $(instance).val();
    return values ? ($.isArray(values) ? (removeZeroOrEmptyValue ? refactorValues(values).join(",") : values.join(',')) : values) : "";
}

function refactorValues(values) {
    if (values.indexOf("0") > -1 || values.indexOf("") > -1) values.shift();//remove first item i.e. 0 from an array
    return values;
}

// Converts 18:00:00 to 06:00:00 pm
function convertTimeToReadableFormat(time) {
    // Check correct time format and split into components
    time = time.toString().match(/^([01]\d|2[0-3])(:)([0-5]\d)(:[0-5]\d)?$/) || [time];

    if (time.length > 1) { // If time format correct
        time = time.slice(1);  // Remove full string match value
        time[5] = +time[0] < 12 ? ' AM' : ' PM'; // Set AM/PM
        time[0] = +time[0] % 12 || 12; // Adjust hours
    }
    return time.join(''); // return adjusted time or original string
}

function getStudentSelectExtraCases(selectStudentInstance) {
    const extraCases = $(selectStudentInstance).data("case");
    if (!extraCases) return {includeAlumni: true};
    return JSON.parse(selectStudentInstance.data("case").replaceAll(/'/g, '"'));
}


function stringifyJSON(obj) {
    if (!obj) return "";
    return JSON.stringify(obj).replaceAll(/"/g, "'");
}

function parseJSON(jsonString) {
    if (!jsonString) return {};
    return JSON.parse(jsonString.replaceAll(/'/g, '"'));
}

function validateValue(value) {
    return value && value > 0;
}

function isValidData(data) {
    return data && data.length > 0
}

function getFinalSplitedValue(data) {
    return data ? data.split(",") : 0;
}

function showOrHide(instance, show = false) {
    if (show) $(instance).show();
    else $(instance).hide();
}

function serializeFormToJSON(formInstance) {
    let obj = {};
    $.each($(formInstance).serializeArray(), function (i, item) {
        obj[item.name] = item.value;
    });
    return obj;

}

/**
 * returns letters of first and last word from as string  >> String = "Hello new world"   >> returns HW
 * @param name
 * @returns {string|null}
 */
function getFirstAndLastLetterName(name) {
    if (!name) return null;
    let myName = name.split(" ");
    return myName[0].toUpperCase().charAt(0) + "" + myName[myName.length - 1].toUpperCase().charAt(0);
}

function getBootstrapIcon(iconKey, tooltipTitle) {
    return `<div className="icon" class="text-muted" data-toggle="tooltip" title="${tooltipTitle}" style="display: inline-block; margin-right: 3px;">
                <svg className="bi" width="12" height="12" fill="currentColor">
                    <use xlink:href="${baseUrl}/resources/mdbootstrap/img/icon/bootstrap-icons.svg#${iconKey}"/>
                </svg>
            </div>`;
}

function clearInventoryCloneValue(rowInstance) {
    rowInstance.find(".quantity").val(1);
    rowInstance.find(".rate").val(0).trigger('blur');
    rowInstance.find(".total").val(0).trigger('change');
    rowInstance.find(".description, .code, .uom").val("").trigger('change');
}

function loadRelatedData(data, productInstance) {
    let row = $(productInstance).closest('tr');
    if (data) {
        if (isItemAlreadySelectedInList(productInstance)) return;
        row.find(".uom").val(getValue(data.uom, "-"));
        row.find(".code").val(getValue(data.code));
        row.find(".description").val(getValue(data.specification));
    }
}

// adjust tbody height for table without increasing tr height
function adjustTbodyHeight(tbody, targetHeight, nthChild = 1) {
    const tBody = $(tbody);

    if (tBody.find("tr").length === 0) {
        let emptyRow = tBody.closest("table").find("thead tr:nth-child(" + nthChild + ")").clone();
        emptyRow.children("th").replaceWith(function () {
            return "<td></td>";
        });
        tBody.html(emptyRow);
    }

    const lastRowTd = tBody.find("tr:last-child td");
    lastRowTd.css('height', targetHeight - (tBody.outerHeight() - lastRowTd.outerHeight()));

    // fade in print sheet
    $(tbody).closest(".with-fadeIn").removeClass("with-fadeIn");
}

function getDisplayableProgress(value) {
    if (!value) value = 'Low';

    let icon;
    let colorCode;

    switch (value) {

        case "Completed":
        case "Solved":
            icon = "tour";
            colorCode = "1b5e20";
            break;

        case "In progress":
        case "On progress":
            icon = "looks";
            colorCode = "e65100";
            break;

        case "On hold":
            icon = "pan_tool";
            colorCode = "1565c0";
            break;

        case "Not started":
        default:
            icon = "panorama_fish_eye";
            colorCode = "f9a825";
            break;
    }

    return `<span style="color: #${colorCode};" data-toggle="tooltip" title="${value}" class="material-icons">${icon}</span>`;
}

function getDisplayablePriority(value) {
    if (!value) value = 'Low';

    let icon;
    let className;

    switch (value) {

        case "Urgent":
            icon = "notifications_active";
            className = "text-danger";
            break;

        case "Important":
            icon = "priority_high";
            className = "text-danger";
            break;

        case "Medium":
            icon = "adjust";
            className = "text-dark";
            break;

        default:
            icon = "arrow_downward";
            className = "text-dark";
            break;
    }

    return `<span data-toggle="tooltip" title="${value}" class="material-icons ${className}">${icon}</span>`;
}


function getDisplayableTicketType(type) {
    if (!type) type = "";

    let colorCode;
    let icon;

    switch (type) {
        case "Question":
            icon = "help_outline";
            colorCode = "4052ab";
            break;

        case "Problem":
            icon = "bug_report";
            colorCode = "a4262c";
            break;

        case "Feature Request":
            icon = "all_out";
            colorCode = "a4262c";
            break;

        default:
            icon = "more_horiz";
            colorCode = "737373";
            break;
    }

    return `<span style="color: #${colorCode};" data-toggle="tooltip" title="${type}" class="material-icons">${icon}</span>`;
}

function validateEmail(instance) {
    let email = $.trim($(instance).val());
    if (email === "") return;
    let regex = /^([a-zA-Z0-9_\.\-\+])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    if (!regex.test(email)) {
        showAlert("Please enter valid email.", INFO_ALERT);
        $(instance).val("");
    }
}

function validatePhoneNumber(instance) {
    let phoneNumber = $.trim($(instance).val());
    if (phoneNumber === "") return;
    let filter = /^((\+[1-9]{1,4}[ \-]*)|(\([0-9]{2,3}\)[ \-]*)|([0-9]{2,4})[ \-]*)*?[0-9]{3,4}?[ \-]*[0-9]{3,4}?$/;
    if (filter.test(phoneNumber)) {
        showAlert("Please enter valid phone number.", INFO_ALERT);
        $(instance).val("");
    }
}

function resetCloneTable(tableInstance) {
    $(tableInstance).find("tbody>tr").not($(tableInstance).find("tbody>tr:first")).remove();
    updateCloneButtons();
}

function getNepaliNumber(int) {
    let nepaliNumber = ["०", "१", "२", "३", "४", "५", "६", "७", "८", "९", "१०", "११", "१२", "१३", "१४", "१५", "१६", "१७", "१८", "१९",
        "२०", "२१", "२२", "२३", "२४", "२५", "२६", "२७", "२८", "२९", "३०", "३१", "३२"];
    return nepaliNumber[int];
}

function populateMonthsByAcademicYear(academicYearInstance = "#selectAcademicYear", monthInstance = "#selectMonth", includeAllOptions = true) {
    let dateRange = getAcademicYearStartAndEndDate(academicYearInstance);
    if (!dateRange) {
        updateSelectOptions(monthInstance, "")
        return;
    }

    let startDate = dateRange.startDate;
    let endDate = dateRange.endDate;

    if (operationDateSetting === "np") {
        startDate = getFinalDate(dateRange.startDate);
        endDate = getFinalDate(dateRange.endDate);
    }

    const month = "month"
    const currentDate = getCurrentDate(operationDateSetting);
    const currentYear = getYearOrMonth(currentDate);
    const currentMonth = getYearOrMonth(currentDate, month);

    const startYear = getYearOrMonth(startDate);
    const startMonth = getYearOrMonth(startDate, month);
    const endYear = getYearOrMonth(endDate);
    const endMonth = getYearOrMonth(endDate, month);
    const isSameYearCase = startYear === endYear;

    let selectOptions = "";
    for (let year = startYear; year <= endYear; year++) {
        const initialMonth = startYear === year ? startMonth : 1;
        const finalMonth = endYear === year ? endMonth : 12;
        for (let month = initialMonth; month <= finalMonth; month++) {
            const attrSelected = year === currentYear && month === currentMonth ? "selected" : "";
            const monthDateRange = getDateRangeByYearAndMonth(year, month, operationDateSetting);
            selectOptions += `<option ${attrSelected} data-secondary-text="${isSameYearCase ? "" : year}" data-val="${stringifyJSON(monthDateRange)}" value="${year}_${month}">${getMonthName(month, operationDateSetting)}</option>`;
        }
    }

    /*console.log({
        startYear,
        startMonth,
        endYear,
        endMonth,
        isSameYearCase,
        selectOptions
    });*/

    updateSelectOptions(monthInstance, selectOptions);
}

function isValidAttachment(file, extensions = VALID_EXTENTIONS) {
    if (!file)
        return FILE_INVALID_REPONSE;

    const fileName = file.name;
    const extension = getFileExtension(fileName);

    if ($.inArray(extension, extensions) === -1)
        return FILE_INVALID_REPONSE;

    return {"success": true, "isImage": $.inArray(extension, IMAGE_FILE_EXTENSIONS) !== -1};
    // return {"success": true, "isImage": $.inArray(extension, VALID_EXTENTIONS) !== -1};
}

function getFileExtension(fileName) {
    return fileName.substr((fileName.lastIndexOf('.') + 1)).toLowerCase();
}