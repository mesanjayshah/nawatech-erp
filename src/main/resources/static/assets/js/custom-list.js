//auto trigger data populate event on document ready if enabled
$(() => {
    $.each($(".list-head"), function () {
        const hasMultipleTableOnCurrentPage = $(this).closest(".custom-list").hasClass("multiple");
        if (!hasMultipleTableOnCurrentPage)
            populateCustomHeader(this, columns);
    })

    if ($(".custom-list").data("trigger-on-ready"))
        $(".btn-filter").trigger('click');

    //focus search keyword input on doc ready
    $("input[data-role='keyword']").focus();
})

function populateCustomHeader(instance, columns) {
    $(instance).html(generateListHead(columns, null));
}

function showCustomListLoader(customList) {
    $(customList).closest(".custom-list-main").append('<div class="list-loader"><div class="lds-facebook"><div></div><div></div><div></div></div>');
}

function hideCustomListLoader(customList) {
    $(customList).closest(".custom-list-main").find(".list-loader").remove();
}

function generateListHead(listColumns, colObj = null) {
    // console.log("List Columns ", listColumns);

    let listHeadRow = '<div class="list-row">';
    $.each(listColumns, function (i, column) {

        if (column) {
            let className = "";
            if (column.className) className += " " + column.className;

            const hasJustifyEnd = getValue(column.className).search("justify-content-end") > -1,
                hasJustifyLeft = getValue(column.className).search("justify-content-left") > -1,
                hasJustifyCenter = getValue(column.className).search("justify-content-center") > -1;

            if (colObj && !colObj[column.key]) className += " d-none";
            if (i > 0 && !hasJustifyEnd && !hasJustifyLeft && !hasJustifyCenter) className += " justify-content-center";
            listHeadRow += `<div class="list-col${className}" width="${column.width}" col-key="${column.key}">${column.title}</div>`;
        }

    });
    listHeadRow += '</div>';

    return listHeadRow;
}

function getColumnVisibilityObj() {
    return $(".column-checkbox-list input").map(function (i, input) {
        let o = {};
        let key = $(input).attr("id");
        o[key] = $(this).prop("checked") ? 1 : 0;
        return o;
    }).get();
}

/**
 *
 * @param customListInstance - id of custom list
 * @param result - html formatted list of items
 * @param chooseColumn
 * @param rowCheck - adds checkbox on each row of list
 */

function initCustomList(customListInstance, result, chooseColumn = false, rowCheck = false) {
    let listHeadRow = $(".list-head>.list-row", customListInstance);
    $(".list-body", customListInstance).html(result.items ? result.items : `<div class="list-row justify-content-center py-3">No data available on selected criteria.</div>`);
    $(customListInstance).attr("total", result.total);
    $(customListInstance).closest(".custom-list-wrapper").find(".pages-counter .total").html(result.total);
    updateCurrentPageHistory(customListInstance, result.size, result.total);

    $(".list-head>.list-row>.list-col:not(.custom-check)", customListInstance).each(function (i, col) {
        let colWidth = $(col).attr("width");
        let tbodyCol = $(`.list-body>.list-row>.list-col:nth-child(${i + 1})`, customListInstance)

        $(col).css("width", colWidth);
        tbodyCol.css("width", colWidth);

        let justifyClass = $(col).hasClass("justify-content-end") ? "justify-content-end" : ($(col).hasClass("justify-content-center") ? "justify-content-center" : "");
        tbodyCol.addClass(justifyClass);

        // adjust max width in column to make action button visible in mobile device
        if ((i == 0 || i == 1) && tbodyCol.length && tbodyCol.eq(0).hasClass("column-action")) {
            let maxWidth = $(window).width() - (30 + (i == 0 ? 0 : 34));
            $(col).css("max-width", maxWidth);
            tbodyCol.css("max-width", maxWidth);
        }

        // handling dynamic text overflow ellipsis
        tbodyCol.find(".overflow-ellipsis").each(function (i, text) {
            $(text).css({maxWidth: $(text).width() + 1, whiteSpace: "nowrap"});
        });
    });

    //prepend check column
    if (rowCheck) {
        let rowCheckHtml = `<div class="list-col custom-check" style="max-width:34px"><input type="checkbox" class="form-check-input"><span></span></div>`;
        if ($(".custom-check", listHeadRow).length) {
            $(".list-body>.list-row", customListInstance).prepend(rowCheckHtml);
        } else {
            $(".list-head>.list-row, .list-body>.list-row", customListInstance).prepend(rowCheckHtml);
        }
    }
    //append choose column link and modal
    if (chooseColumn && !$("a[data-target='#choose-column-modal']", listHeadRow).length) {
        // listHeadRow.append(`<div class="list-col" style="width:115px"><a href="#" data-toggle="modal" data-target="#choose-column-modal"><i class="fa fa-caret-square-o-up"></i> Choose Columns</a></div>`);

        !$("#choose-column-modal").length && $("body").append(`<div class="modal fade right choose-column-modal" id="choose-column-modal" tabindex="-1">
			<div class="modal-dialog modal-full-height modal-right">
				<div class="modal-content">
					<div class="modal-header">
						<h4 class="modal-title w-100" id="myModalLabel">Choose Display Columns</h4>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<p>Please Choose the column that you want to display in the table. Your changes will be auto saved.</p>
						<div class="column-checkbox-list"></div>
					</div>
					<div class="modal-footer justify-content-center">
				        <button type="button" class="btn btn-primary" onClick="updateColumnVisibility(this)">Save changes</button>
			      </div>
				</div>
			</div>
		</div>`);
    }

    appendHorizontalScrollBar(customListInstance);
    customListInstance.closest(".custom-list-main").scrollLeft(0); // reset scroll after pagination

    hideCustomListLoader(customListInstance);
    hideModal("#filter-modal");
    $(customListInstance).css("opacity", 1);
    detectLastPage(customListInstance);
    initTooltip();
}

function sortColumn(customList, colIndex, sortType) {
    let rows = customList.find(".list-body>.list-row");

    rows.sort(function (a, b) {
        let c1 = $.trim($(">.list-col", a).eq(colIndex).text()).toLowerCase();
        let c2 = $.trim($(">.list-col", b).eq(colIndex).text()).toLowerCase();
        r1 = sortType == "asc" ? 1 : -1;
        r2 = sortType == "asc" ? -1 : 1;

        // if ($.isNumeric(c1) && $.isNumeric(c2)) {
        //     c1 = Number(c1);
        //     c2 = Number(c2);}
        // return (c1 > c2) ? r1 : r2;

        if ($.isNumeric(c1) && $.isNumeric(c2)) {
            c1 = Number(c1);
            c2 = Number(c2);
        } else {
            c1 = c1.replace(/[^a-zA-Z0-9]/g, '');
            c2 = c2.replace(/[^a-zA-Z0-9]/g, '');
        }
        return ((c1.toString().localeCompare(c2, undefined, {
            numeric: true,
            sensitivity: 'base'
        })) > 0) ? r1 : r2;
    });
    customList.find(".list-body").html(rows);
}

function generateDisplayColumns(customList) {
    let checkboxHtml = "";

    $(".list-head>.list-row>.list-col", customList).each(function (i, col) {
        let colKey = $(col).attr("col-key");
        let colText = colKey == "check" ? "Row Checkbox" : $(col).text();
        let isHidden = $(col).hasClass("d-none");
        if (colKey) {
            checkboxHtml += `<div class="form-check"><input type="checkbox" class="form-check-input" id="${colKey}" ${isHidden ? '' : 'checked'} value="${colKey}"><label class="form-check-label" for="${colKey}">${colText}</label></div>`;
        }
    });
    $(".column-checkbox-list").html(checkboxHtml);
}

function updateColumnVisibilityLocal() {
    let customListId = $(".column-checkbox-list").data("list-id");


    $(".column-checkbox-list input").each(function () {
        let colKey = $(this).val();
        let isVisible = $(this).prop("checked");

        let headColInstance = $(`#${customListId} > .list-head > .list-row > .list-col[col-key="${colKey}"]`);
        let bodyColInstance = $(`#${customListId} > .list-body > .list-row > .list-col:nth-child(${headColInstance.index() + 1})`);

        if (isVisible) {
            headColInstance.removeClass("d-none");
            bodyColInstance.removeClass("d-none");
        } else {
            headColInstance.addClass("d-none");
            bodyColInstance.addClass("d-none");
        }
    });

    appendHorizontalScrollBar(`#${customListId}`);
}


$(document).on('show.bs.modal', '#choose-column-modal', function (e) {
    const customListId = $(e.relatedTarget).closest(".custom-list").attr("id");
    $(".column-checkbox-list", this).attr("data-list-id", customListId);
    generateDisplayColumns(`#${customListId}`);
});


//handling custom checkbox
$(document).on("click", ".custom-list .custom-check", function () {
    let checkBox = $(">input", this)
    let isChecked = checkBox.prop("checked");

    const table = $(this).closest(".custom-list");

    let isAllCheck = $(this).closest(".list-head").length;

    let targetCheckBox = isAllCheck ? table.find(".custom-check>input") : $(">input", this);
    targetCheckBox.prop("checked", !isChecked).trigger("change");

    let allChecked = table.find(".list-body .custom-check>input").length === table.find(".list-body .custom-check>input:checked").length;
    table.find(".list-head .custom-check>input").prop("checked", allChecked).trigger("change");
});


//handling row selected on checked
$(document).on("change", ".custom-list>.list-body .custom-check>input[type=checkbox]", function () {
    let row = $(this).closest(".list-row");
    row[$(this).prop("checked") ? 'addClass' : 'removeClass']('selected');
});


//handling sorting of columns
$(document).on("click", ".custom-list .sortable", function (e) {
    let isAsc = $(this).hasClass("asc");
    let isDesc = $(this).hasClass("desc");
    console.log("Asc ", isAsc, " and desc ", isDesc);
    let sortType = (isAsc || isDesc) ? (isAsc ? "desc" : "asc") : "asc";

    $(".custom-list .sortable").removeClass("asc").removeClass("desc"); // reset sorting class for all
    $(this).addClass(sortType);

    sortColumn($(this).closest(".custom-list"), $(this).index(), sortType);
    e.stopPropagation();
});

$(document).on("click", "[data-toggle=list-col-dropdown]", function (e) {
    let listRow = $(this).closest(".list-row");
    listRow.toggleClass("dropdown-active");
});

$("#selectLimit, .pagination .page-link:not([disabled]), .btn-filter, .search-box .search").bind("keyup change click", function (event) {
    if ($(this).data("role") === 'keyword' && event.keyCode !== 13) return;
    const wrapperInstance = $(this).closest(".custom-list-wrapper"),
        listInstance = wrapperInstance.find(".custom-list"),
        callback = listInstance.data("callback");
    if (callback) {
        //reset meta data if filter button is triggered
        const role = $(this).data("role");
        if (role === "filter" || role === "keyword") {
            if (role === "filter") wrapperInstance.find("input[data-role='keyword']").val("");
            listInstance.attr({"current": 1, "last": 0, "total": 0});
        }

        //trigger callback
        window[callback](this);
    }
});

function getSearchKeyword(triggerInstance) {
    const searchBoxInstance = $(triggerInstance).hasClass("search") ? $(triggerInstance) : $(triggerInstance).closest(".custom-list-wrapper").find(".search-box .search");
    return $.trim(searchBoxInstance.val());
}

function getLimit(triggerInstance) {
    return $(triggerInstance).data("role") === 'limit'
        ? Number($("option:selected", triggerInstance).val())
        : Number($(triggerInstance).closest(".custom-list-wrapper").find(".list-pagination select[data-role='limit']").val());
}

function getCurrentPage(listInstance) {
    return Number($(listInstance).attr("current"));
}

function getLastPage(listInstance) {
    return Number($(listInstance).attr("last"));
}

function getPageNumber(triggerInstance, listInstance = '.custom-list') {
    let pageNumber;

    switch ($(triggerInstance).data("role")) {
        case "first":
            pageNumber = 1;
            break;

        case "prev":
            pageNumber = getCurrentPage(listInstance);
            if (pageNumber > 1) pageNumber = pageNumber - 1;
            break;

        case "next":
            pageNumber = getCurrentPage(listInstance) + 1;
            break;

        case "last":
            pageNumber = Number($(listInstance).attr("last"));
            break;

        case "limit":
            pageNumber = getCurrentPage(listInstance);
            break;

        default:
            pageNumber = 1;
            break;

    }

    if (pageNumber > 1) {
        const total = $(listInstance).attr("total"),
            limit = getLimit(listInstance),
            totalPages = Math.ceil(total / limit);
        if (pageNumber > totalPages) pageNumber = totalPages;
    }

    updatePageNumber(listInstance, pageNumber);

    return pageNumber;
}

function updatePageNumber(listInstance, current) {
    $(listInstance).attr({"current": current});
    handlePaging(listInstance);
}

function handlePaging(listInstance) {
    const current = Number($(listInstance).attr("current")),
        last = Number($(listInstance).attr("last") ? $(listInstance).attr("last") : 0),
        wrapperInstance = $(listInstance).closest(".custom-list-wrapper"),
        firstInstance = wrapperInstance.find(".page-link[data-role='first']").closest(".page-item"),
        prevInstance = wrapperInstance.find(".page-link[data-role='prev']").closest(".page-item"),
        nextInstance = wrapperInstance.find(".page-link[data-role='next']").closest(".page-item"),
        lastInstance = wrapperInstance.find(".page-link[data-role='last']").closest(".page-item"),
        isLastPage = current === last;
    firstInstance[current > 1 ? 'removeClass' : 'addClass']('disabled');
    prevInstance[current > 1 ? 'removeClass' : 'addClass']('disabled');
    lastInstance[!isLastPage ? 'removeClass' : 'addClass']('disabled');
    nextInstance[!isLastPage ? 'removeClass' : 'addClass']('disabled');
}

function detectLastPage(listInstance) {
    const total = Number(listInstance.attr("total")),
        rowsPerPage = getLimit(listInstance),
        maxPages = Math.ceil(total / rowsPerPage);
    listInstance.attr("last", maxPages > 0 ? maxPages : 1);
}

function updateCurrentPageHistory(listInstance, size, total) {
    const currentPage = getCurrentPage(listInstance),
        limit = getLimit(listInstance),
        lastEnd = (currentPage - 1) * limit,
        min = lastEnd + 1,
        max = lastEnd + size;
    $(listInstance).closest(".custom-list-wrapper").find(".pages-counter").html(`${max > 0 ? min : 0} - ${max} of ${total}`);
}

function finalizeRequestParams(requestParams, triggerInstance) {

    if (!requestParams) return {};

    requestParams.limit = getLimit(triggerInstance);
    requestParams.pageNumber = getPageNumber(triggerInstance);
    requestParams.keyword = getSearchKeyword(triggerInstance);

    return requestParams;
}

function wrapResult(items, size, total) {
    return {items, size, total};
}