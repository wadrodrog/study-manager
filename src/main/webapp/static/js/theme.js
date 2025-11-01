const authorized = document.currentScript.getAttribute("data-authorized") != null;

window.addEventListener("load", loadTheme);

function loadTheme() {
    if (authorized) {
        return;
    }
    var theme = localStorage.getItem("theme");
    var themeButton = null;
    switch (theme) {
        case "light":
            themeButton = document.getElementById("theme-light");
            break;
        case "dark":
            themeButton = document.getElementById("theme-dark");
            break;
        default:
            themeButton = document.getElementById("theme-auto");
    }
    if (themeButton != null) {
        themeButton.click();
    }
}

function saveTheme(theme) {
    if (!authorized) {
        localStorage.setItem("theme", theme);
        return;
    }

    fetch(`/settings?theme=${theme}`, {method: "PATCH"})
        .then(response => {
            if (!response.ok) {
                throw new Error("Update user theme error: " + response.statusText);
            }
            return response;
        });
}
