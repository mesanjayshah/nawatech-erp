function convert(n, ch) {
    let response = "";
    let one = ["", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"];
    let ten = ["", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"];

    if (n > 19) response += ten[Math.floor(n / 10)] + " " + one[n % 10];
    else response += one[n];

    if (n > 0) response += ch;
    return response;
}

function getWords(number) {
    if (number <= 0) return "Zero only.";

    let words = "";
    let n = Math.round(number);

    words += convert(Math.floor(n / 1000000000), " Hundred ");
    words += convert(Math.floor((n / 10000000) % 100), " Crore ");
    words += convert(Math.floor((n / 100000) % 100), " Lakh ");
    words += convert(Math.floor((n / 1000) % 100), " Thousand ");
    words += convert(Math.floor((n / 100) % 10), " Hundred ");
    words += convert(Math.floor(n % 100), " ");
    // words = words.toLowerCase();

    return ucFirst((words.trim() + " Only."));
}