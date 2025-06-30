$(function () {
    $(".datepicker, .timepicker").keydown(false);
    initInputClearButton(".datepicker.clearable, .timepicker.clearable, .clockpicker.clearable, .clearable-input");
    // $(".datepicker.clearable, .timepicker.clearable, .clockpicker.clearable").keydown(false).before('<i class="material-icons btn-clear" style="display: none;">close</i>');
    $(".datepicker, .timepicker, .ndp-nepali-calendar").attr("autocomplete", "off");
    $(".datepicker, .timepicker").trigger('onload');
    initCurrentDate();
});

function initCurrentDate() {
    let d = new Date(),
        h = d.getHours(),
        m = d.getMinutes();
    if (h < 10) h = '0' + h;
    if (m < 10) m = '0' + m;
    $(".current-time").attr({'value': h + ':' + m});
}

function initInputClearButton(instance) {
    $(instance).keydown(false).before('<i class="material-icons btn-clear" style="display: none;">close</i>');
}

// clear button for datepicker and timepicker
$(document).on('mouseover', '.md-form, .datepicker-container, .clearable-input-container', function () {
    let btnClear = $(this).find('.btn-clear');
    if (btnClear.length) $('input', this).val() ? btnClear.show() : btnClear.hide();
});
$(document).on('mouseout', '.md-form, .datepicker-containers, .clearable-input-container', function () {
    let btnClear = $(this).find('.btn-clear');
    if (btnClear.length) btnClear.hide();
});
$(document).on('click', '.btn-clear', function () {
    $(this).next('input').trigger("clear");
});

// custom clearable input handler
$(document).on('clear', 'input.clearable, input.clearable-input', function () {
    $this = $(this);
    setDate($this, "", operationDateSetting);
    $this.trigger("changeDate");
    $this.prev('.btn-clear').hide();

    $this.trigger("cleared"); // dispatch custom event after input clear (useful for further action. eg. $(document).on("cleared", selector, function...)
});

function initDatePicker(instance, operationDateSetting, onChangeMethod, setCurrentDate = false, params = [], attachInstanceOnCallbackParam = true) {
    if (!params) params = [];
    if ($(instance).is("[readonly]")) return;
    switch (operationDateSetting) {
        case "np":
            $(instance).nepaliDatePicker({
                onChange: function () {
                    if ($(instance).val()) resetError(instance);
                    if (onChangeMethod) {
                        if (attachInstanceOnCallbackParam) onChangeMethod(instance, ...params);
                        else onChangeMethod(...params);

                        //call secondary callback function if exists
                        const secCallback = $(instance).attr("sec-callback");
                        if (secCallback) window[secCallback]();
                    }
                    handleDateCloneCase($(instance));
                },
                npdMonth: true,
                npdYear: true,
            });
            break;

        case "en":
            $(instance).datepicker({
                format: "yyyy-mm-dd",
                startDate: "1900-01-01",
                autoclose: true,
                orientation: "bottom auto"
            });
            $(instance).on('changeDate', function () {
                if ($(instance).val()) resetError(instance);

                if (onChangeMethod) {
                    if (attachInstanceOnCallbackParam) onChangeMethod(instance, ...params);
                    else onChangeMethod(...params);

                    //call secondary callback function if exists
                    const secCallback = $(instance).attr("sec-callback");
                    if (secCallback) eval(secCallback);
                }

                handleDateCloneCase($(instance));
            });
            break;

        default:
            break;
    }
    if (setCurrentDate) setDate($(instance), getCurrentDate(operationDateSetting), operationDateSetting);
}

function handleDateCloneCase(instance) {
    if (instance.hasClass('cloneParent')) {
        const cloneToInstance = instance.attr("clone-to");
        setDate($(`input[name=${cloneToInstance}]`), instance.val(), operationDateSetting);
    }
}

function setDate(inputFieldInstance, date, operationDateSetting) {
    if (operationDateSetting === 'np') {
        inputFieldInstance.val(date);
    } else {
        setTimeout(function () {
            inputFieldInstance.datepicker('update', date);
        });
    }
    if (date) resetError(inputFieldInstance);
}

function setTime(instance, time) {
    $(instance).val((time ? time : "").substr(0, 5));
}

function destroyDatePickerInstance(instance, operationDateSetting) {
    if (operationDateSetting === 'np') {
        instance.nepaliDatePicker("destroy");
    } else {
        instance.datepicker("destroy");
    }
}

function initTimePicker(instance, onChangeMethod, params = [], attachInstanceOnCallbackParam = true) {
    const input = $(instance).not('[readonly]').pickatime({
        placement: 'right',
        align: 'left',
        donetext: 'DONE',
        autoclose: true,
        'default': 'now',
        afterDone: function () {
            if (!params) params = [];
            if (onChangeMethod) {
                if (attachInstanceOnCallbackParam) onChangeMethod(input, ...params);
                else onChangeMethod(...params);
            }
        }
    });
}
