const start = new Date("2025-09-01");
const current = new Date();
const difference = current - start;
const daysPassed = Math.floor(difference / (1000 * 60 * 60 * 24));
const week = Math.floor((daysPassed + start.getDay()) / 7 + 1);

window.onload = function () {
    document.getElementById("week-display").innerText = week;

    if (week % 2 == 1) {
        for (var even of document.getElementsByClassName("even")) {
            even.style = "text-decoration: line-through;";
        }
    } else {
        for (var odd of document.getElementsByClassName("odd")) {
            odd.style = "text-decoration: line-through;";
        }
    }

    for (var limited of document.getElementsByClassName("limited")) {
        if (week > limited.getAttribute("data-till-week")) {
            limited.style = "text-decoration: line-through;";
        }
    }
};
