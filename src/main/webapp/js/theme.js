window.addEventListener("load", loadTheme);

function loadTheme() {
    var theme = localStorage.getItem("theme");
    var themeButton = null;
    switch (theme) {
        case "light":
            themeButton = document.getElementById("theme-light");
            break;
        case "dark":
            themeButton = document.getElementById("theme-dark");
            break;
    }
    if (themeButton != null) {
        themeButton.click();
    }
}

function saveTheme(theme) {
    localStorage.setItem("theme", theme);
}
