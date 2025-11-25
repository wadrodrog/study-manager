const start = new Date("2025-09-01");
const current = new Date();
const difference = current - start;
const daysPassed = Math.floor(difference / (1000 * 60 * 60 * 24));
const week = Math.floor((daysPassed + start.getDay()) / 7 + 1);

window.addEventListener("load", function () {
    document.getElementById("week-display").innerText = week;
});

function editEvent(id) {
    const timeContents = document.querySelector("#event-" + id + " .time .contents");
    const timeEdit = document.querySelector("#event-" + id + " .time .edit");
    timeContents.style.display = "none";
    timeEdit.style.display = "block";
    var timeRange = timeContents.innerText.split("–");
    document.querySelector("#event-" + id + " .time .edit .start").value = timeRange[0];
    document.querySelector("#event-" + id + " .time .edit .end").value = timeRange[1];

    const nameContents = document.querySelector("#event-" + id + " .name .contents");
    const nameEdit = document.querySelector("#event-" + id + " .name .edit");
    nameContents.style.display = "none";
    nameEdit.style.display = "block";
    nameEdit.value = nameContents.innerText;

    const placeContents = document.querySelector("#event-" + id + " .place .contents");
    const placeEdit = document.querySelector("#event-" + id + " .place .edit");
    placeContents.style.display = "none";
    placeEdit.style.display = "block";
    placeEdit.value = placeContents.innerText;

    const notesContents = document.querySelector("#event-" + id + " .notes .contents");
    const notesEdit = document.querySelector("#event-" + id + " .notes .edit");
    notesContents.style.display = "none";
    notesEdit.style.display = "block";
    notesEdit.value = notesContents.innerText;

    const weekdayEdit = document.querySelector("#event-" + id + " .actions .weekday");
    weekdayEdit.style.display = "inline";

    const editButton = document.querySelector("#event-" + id + " .actions .edit");
    editButton.style.display = "none";
    const saveButton = document.querySelector("#event-" + id + " .actions .save");
    saveButton.style.display = "inline";
}

function saveEvent(id) {
    const timeContents = document.querySelector("#event-" + id + " .time .contents");
    const timeEdit = document.querySelector("#event-" + id + " .time .edit");
    timeContents.style.display = "block";
    timeEdit.style.display = "none";
    var timeStart = document.querySelector("#event-" + id + " .time .edit .start").value;
    var timeEnd = document.querySelector("#event-" + id + " .time .edit .end").value;
    if (timeStart === null) {
        timeStart = "";
    }
    if (timeEnd === null) {
        timeEnd = "";
    }
    if (timeStart !== "" && timeEnd !== "" && timeStart <= timeEnd || timeStart === "" || timeEnd === "") {
        timeContents.innerText = timeStart + "–" + timeEnd;
    } else {
        var timeRange = timeContents.innerText.split("–");
        timeStart = timeRange[0];
        timeEnd = timeRange[1];
    }

    const nameContents = document.querySelector("#event-" + id + " .name .contents");
    const nameEdit = document.querySelector("#event-" + id + " .name .edit");
    nameContents.style.display = "block";
    nameEdit.style.display = "none";
    if (nameEdit.value != "") {
        nameContents.innerText = nameEdit.value;
    }

    const placeContents = document.querySelector("#event-" + id + " .place .contents");
    const placeEdit = document.querySelector("#event-" + id + " .place .edit");
    placeContents.style.display = "block";
    placeEdit.style.display = "none";
    placeContents.innerText = placeEdit.value;

    const notesContents = document.querySelector("#event-" + id + " .notes .contents");
    const notesEdit = document.querySelector("#event-" + id + " .notes .edit");
    notesContents.style.display = "block";
    notesEdit.style.display = "none";
    notesContents.innerText = notesEdit.value;

    const weekdayEdit = document.querySelector("#event-" + id + " .actions .weekday");
    weekdayEdit.style.display = "none";

    const editButton = document.querySelector("#event-" + id + " .actions .edit");
    editButton.style.display = "inline";
    const saveButton = document.querySelector("#event-" + id + " .actions .save");
    saveButton.style.display = "none";

    fetch(`/schedule?event_id=${id}&weekday=${weekdayEdit.selectedIndex + 1}&name=${nameContents.innerText}&time_start=${timeStart}&time_end=${timeEnd}&place=${placeEdit.value}&notes=${notesEdit.value}`, {method: "PATCH"})
        .then(response => {
            if (!response.ok) {
                throw new Error("Update event error: " + response.statusText);
            }
            return response;
        });
}

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
