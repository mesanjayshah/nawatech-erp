$(function () {
    // setInterval(displayGreetingAndDateTime, 1000);

    // Tooltips Initialization
    $('[data-toggle="tooltip"]').tooltip();

    //disable mousewheel on a input number field when in focus
    //(to prevent Cromium browsers change the value when scrolling)
    $('form').on('focus', 'input[type=number]', function (e) {
        $(this).on('mousewheel.disableScroll', function (e) {
            e.preventDefault()
        })
    });

    $('form').on('blur', 'input[type=number]', function (e) {
        $(this).off('mousewheel.disableScroll')
    });

    //disable autocomplete
    $('form').attr('autocomplete', 'off');

    //turn class btn-success located inside modal footer to class btn-primary to match with the theme color
    $(".modal-footer .btn-success").removeClass("btn-success").addClass("btn-primary");

    $("textarea").addClass("md-textarea");
});

function displayGreetingAndDateTime() {
    const greetingInstance = $(".greeting, #greetings"),
        date = new Date(CURRENT_DATE_TIME_EN),
        hr = date.getHours(),
        currentDate = getCurrentDate("en"),
        nepFormattedDate = convertDateToWords(getFinalDate(currentDate, false, "np"), "np"),
        engFormattedDate = convertDateToWords(currentDate, "en"),
        formattedDate = (operationDateSetting === "np" ? `${nepFormattedDate} (${engFormattedDate})` : `${engFormattedDate} (${nepFormattedDate})`) + ".";

    let greet = "";
    if (hr < 12) {
        greet = 'Good Morning';
    } else if (hr >= 12 && hr <= 17) {
        greet = 'Good Afternoon';
    } else if (hr >= 17 && hr <= 24)
        greet = 'Good Evening';

    greetingInstance.html(`${greet}, <b>${userName}</b>. Today is ${DAYS_FULL[date.getDay()]}, ${formattedDate}`);
}

//hide tooltip on focus
$(document).ready(function () {
    $('*[data-toggle="tooltip"]').focus(function () {
        $(this).blur();
    });
});

$(document).on('click', '.side-nav .collapsible-header', function () {
    $li = $(this).parent('li');
    $collapsible = $li.find('>.collapsible-body');
    if (!$li.hasClass('active')) {
        $collapsible.find('li').removeClass('active').find('a').removeClass('active');
        $collapsible.find('.collapsible-body').hide();
    }
});

//auto adjust width of table existed inside table in case of datatable
/*
$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
    $($.fn.dataTable.tables(true)).DataTable()
        .columns.adjust()
        .responsive.recalc();
    const tabId = $(e.target).attr("href");
    const table = `${tabId} .table-responsive table, ${tabId} .dataTables_scrollBody table`;
    appendHorizontalScrollBar(table);
});
*/

/**
 * used on super user and student case
 * @param modules
 * @param includableMenuIds
 * @param disableTopNav
 */
function populateMainMenu(modules, includableMenuIds = [], disableTopNav = false) {
    let mainMenu = sidebarMenu = "";

    $.each(modules, function (index, module) {
        let isTopNav = (includableMenuIds.length === 0 || $.inArray(module.id, includableMenuIds) >= 0);

        // console.log(module)
        let isNested = module.subModules;

        if (isTopNav && !disableTopNav) {
            if (isNested) {
                mainMenu += "<li class='dropdown'>\n" +
                    "    <a href='#' class='dropdown-toggle' data-toggle='dropdown'>\n" +
                    "    <i class='material-icons'>" + module.icon + "</i><br>" + module.name + "\n" +
                    "    </a>\n" +
                    "    <ul class='dropdown-menu dropdown-primary'>\n";
            } else {
                let url = shortBaseUrl + module.targetUrl;
                mainMenu += `<li><a href="${url}"><i class="material-icons">${module.icon}</i><br>${module.name}</a></li>`;
            }
        }


        if (isNested) {
            sidebarMenu += `<li><a class="collapsible-header waves-effect arrow-r"><i class="material-icons"> ${module.icon} </i> 
                    ${module.name}<i class="fas fa-angle-down rotate-icon"></i></a>
                    <div class="collapsible-body">
                        <ul class="collapsible collapsible-accordion mt-0">`;

            let moreMenu = (isTopNav && module.subModules.length >= 10);
            $.each(module.subModules, function (index, subModule) {

                if (moreMenu && index === 10) {
                    moreMenu = true;
                    mainMenu += "<li class='dropdown'><a href='#'><i class='material-icons'>menu_open</i><span class='moduleName'>More Menu</span><span class='pull-right-container'> <i class='fa fa-angle-left pull-right'></i> </span> </a>" +
                        "<ul class='dropdown-menu'>";
                }

                if (subModule.subModules) {
                    if (isTopNav) {
                        mainMenu += "<li class='dropdown'><a href='#'><i class='material-icons'>" + subModule.icon + "</i> <span class='moduleName'>" + subModule.name + "</span><span class='pull-right-container'> <i class='fa fa-angle-left pull-right'></i> </span> </a>" +
                            "<ul class='dropdown-menu'>";
                    }
                    sidebarMenu += `<li><a class="collapsible-header waves-effect arrow-r"><i class='material-icons'>${subModule.icon}</i> <span class='moduleName'>${subModule.name}</span><i class="fas fa-angle-down rotate-icon"></i></a>
                    <div class="collapsible-body">
                        <ul class="collapsible collapsible-accordion mt-0">`;

                    $.each(subModule.subModules, function (index, menu) {
                        if (menu.subModules) {
                            if (isTopNav) {
                                mainMenu += "<li class='dropdown'><a href='#'><i class='material-icons'>" + menu.icon + "</i> <span class='moduleName'>" + menu.name + "</span><span class='pull-right-container'> <i class='fa fa-angle-left pull-right'></i> </span> </a>" +
                                    "<ul class='dropdown-menu'>";
                            }
                            sidebarMenu += "<li><a class='collapsible-header waves-effect arrow-r'><i class='material-icons'>" + menu.icon + "</i> <span class='moduleName'>" + menu.name + "</span><i class='fas fa-angle-down rotate-icon'></i></a><div class='collapsible-body'><ul>";

                            $.each(menu.subModules, function (index, subMenu) {
                                let url = shortBaseUrl + subMenu.targetUrl;
                                let menuItem = "<li><a href='" + url + "'><i class='material-icons'>" + subMenu.icon + "</i> <span class='moduleName'>" + subMenu.name + "</span></a></li>";
                                if (isTopNav) {
                                    mainMenu += menuItem;
                                }
                                sidebarMenu += menuItem;
                            });

                            mainMenu += "</ul>" +
                                "</li>";
                            sidebarMenu += `</ul>
                                            </div>
                                        </li>`;
                        } else {
                            let url = shortBaseUrl + menu.targetUrl;
                            if (isTopNav) {
                                mainMenu += "<li><a href='" + url + "' class='dropdown-item'><i class='material-icons'>" + menu.icon + "</i> <span class='module-name'>" + menu.name + "</span></a></li>\n";
                            }
                            sidebarMenu += "<li><a href='" + url + "'><i class='material-icons'>" + menu.icon + "</i> <span class='moduleName'>" + menu.name + "</span></a></li>";
                        }
                    });

                    mainMenu += "</ul>" +
                        "</li>";
                    sidebarMenu += `</ul>
                                </div>
                            </li>`;
                } else {
                    let url = shortBaseUrl + subModule.targetUrl;
                    if (isTopNav) {
                        mainMenu += "<li><a href='" + url + "' class='dropdown-item'><i class='material-icons'>" + subModule.icon + "</i> <span class='module-name'>" + subModule.name + "</span></a></li>\n";
                    }
                    sidebarMenu += `<li><a href="${url}"><i class="material-icons">${subModule.icon}</i> ${subModule.name}</a></li>`;
                }
            });

            if (moreMenu) {
                mainMenu += "</ul>" +
                    "</li>";
            }
        } else {
            let url = shortBaseUrl + module.targetUrl;
            sidebarMenu += `<li><a href="${url}"><i class="material-icons">${module.icon}</i> ${module.name}</a></li>`;
        }

        if (isTopNav) {
            mainMenu += "            </ul>\n" +
                "        </li>\n" +
                "    </ul>\n" +
                "</li>";
        }

        if (isNested) {
            sidebarMenu += `</ul>
                    </div>
                </li>`;
        }
    });
    // console.log(mainMenu);
    // console.log(sidebarMenu);

    $(".main-nav").append(mainMenu);
    $("#sidebarMenuContainer").append(sidebarMenu);
    //saveOrUpdateStatus;

    initSideNav();
    highlightActiveMenu();
}

function highlightActiveMenu(addClassCurrent = false) {
    if (requestPath.search('student/add')) requestPath = requestPath.replace("student/add", "addstudent");
    else if (requestPath.search('subject')) requestPath = requestPath + "/index";

    // let shortendUrl = requestPath.replace("/v2", "");
    let shortendUrl = requestPath;
    let activeAnchorInstance = $("a[href='" + requestPath + "'], a[href='" + shortendUrl + "']");
    activeAnchorInstance.addClass("active").parents("li:not(.menu-toggler)").addClass("active" + (addClassCurrent ? ' current' : '')).attr("data-active", true).find(">a").addClass("active").attr("data-active", true).next(".collapsible-body").show();
}

// WMS Custom JS
function showHideSidebar(){
    $('#slide-out').toggleClass('fixed', $(window).width() >= 992);
}

$(function(){
    $(window).resize(function(){
        showHideSidebar();
    });

    showHideSidebar();
});