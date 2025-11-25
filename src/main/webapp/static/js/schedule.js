const start = new Date("2025-09-01");
const current = new Date();
const difference = current - start;
const daysPassed = Math.floor(difference / (1000 * 60 * 60 * 24));
const week = Math.floor((daysPassed + start.getDay()) / 7 + 1);

window.addEventListener("load", function () {
    document.getElementById("week-display").innerText = week;
});

function confirmDelete(id) {
    const name = document.querySelector("#event-" + id + " .name");
    const text = `Вы точно хотите удалить событие «${name.innerText}»?`;
    return confirm(text);
}

function deleteEvent(id) {
    if (!confirmDelete(id)) {
        return;
    }

    fetch("/schedule?event_id=" + id, {method: "DELETE"})
        .then(response => {
            if (!response.ok) {
                throw new Error("Delete event error: " + response.statusText);
            }
            return response;
        }).then(_ => location.reload());
}
