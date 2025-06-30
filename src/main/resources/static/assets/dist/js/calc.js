function roundOff(val) {
    return Number($.trim(val)).toFixed(2);
}

function sum(a, b) {
    return roundOff(Number(a) + Number(b));
}

function subtract(a, b) {
    if (!a) a = 0;
    if (!b) b = 0;
    return roundOff(Number(a) - Number(b));
}

function multiply(a, b) {
    if (!a) a = 0;
    if (!b) b = 0;
    return roundOff(Number(a) * Number(b));
}

/**
 * calculation functions exists in this js file
 * @param feeType
 * @param initialFee
 * @param taxPercentage
 * @returns
 */

function getFinalFee(feeType, initialFee, taxPercentage, duration) {

    initialFee = Number(initialFee);
    taxPercentage = Number(taxPercentage);

    finalFee = initialFee + ((initialFee * taxPercentage) / 100);

    switch (feeType) {
        case "Monthly":
            finalFee = (finalFee * 12) * duration;
            break;

        case "Quarterly":
            //one quarterly = 3 months
            finalFee = (finalFee * 4) * duration;
            break;

        case "Yearly" :
            finalFee = finalFee * duration;
            break;

        case "Semester" :
            // one Semester = 6 months
            finalFee = (finalFee * 2) * duration;
            break;

        case "Trimester" :
            // one Trimester = 4 months
            finalFee = (finalFee * 3) * duration;
            break;

        default:
            break;
    }

    return roundOff(finalFee);
}

function calculatePerMonthInstallment(feeType, initialFee) {

    initialFee = Number(initialFee);
    finalFee = Number(0);

    switch (feeType) {
        case "Monthly":
            finalFee = initialFee;
            break;

        case "Quarterly":
            //one quarterly = 3 months
            finalFee = initialFee / 3;
            break;

        case "Yearly" :
            finalFee = initialFee / 12;
            break;

        case "Semester" :
            // one Semester = 6 months
            finalFee = initialFee / 6;
            break;

        case "Trimester" :
            // one Trimester = 4 months
            finalFee = initialFee / 4;
            break;

        default:
            break;

    }

    return roundOff(finalFee);

}

function getFinalFeeWithTax(initialFee, taxPercentage) {
    initialFee = Number(initialFee);
    taxPercentage = Number(taxPercentage);
    return initialFee + (initialFee * taxPercentage) / 100;
}

function getFinalTutionFee(feeType, initialTutionFee, validYears) {
    var finalTutionFee = Number(0);
    inititalTutionFee = Number(initialTutionFee);
    validYears = parseInt(validYears);

    switch (feeType) {

        case "One Time":
            finalTutionFee = initialTutionFee;
            break;

        case "Monthly":
            finalTutionFee = (initialTutionFee * 12) * validYears;
            break;

        case "Quarterly":
            //one quarterly = 03 months
            finalTutionFee = (initialTutionFee * 4) * validYears;
            break;

        case "Yearly" :
            finalTutionFee = initialTutionFee * validYears;
            break;

        case "Semester" :
            // one Semester = 6 months
            finalTutionFee = (initialTutionFee * 2) * validYears;
            break;

        case "Trimester" :
            // one Trimester = 3 months
            finalTutionFee = (initialTutionFee * 3) * validYears;
            break;

        default:
            break;
    }

    return finalTutionFee;
}
