var themeSwitcher;

window.addEventListener("load", function () {
    themeSwitcher = document.getElementById("theme");
});

function updateTheme() {
    fetch(`/settings?theme=${themeSwitcher.selectedIndex}`, {method: "PATCH"})
        .then(response => {
            if (!response.ok) {
                throw new Error("Update user theme error: " + response.statusText);
            }
            return response;
        }).then(_ => location.reload());
}
