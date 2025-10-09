var container;

window.addEventListener("load", function () {
    var trigger = document.getElementById("important-links");
    container = document.getElementById("important-links-container");
    trigger.addEventListener("click", toggleMenu);
});

function toggleMenu() {
    if (container.classList[0] === "fade-in") {
        container.classList.remove("fade-in");
        container.classList.add("fade-out");
    } else {
        container.classList.remove("fade-out");
        container.classList.add("fade-in");
    }
}
