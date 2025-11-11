var sort;
var descending;

window.addEventListener("load", function () {
    sort = document.getElementById("sort");
    descending = document.getElementById("descending");
});

function updateSort() {
    window.location.href = "/tasks?sort=" + sort.value + (descending.checked ? "&descending" : "");
}

function confirmDelete(id) {
    const title = document.querySelector("#task-" + id + " > h2");
    const text = `Вы точно хотите удалить задачу «${title.innerText}»?`;
    return confirm(text);
}

function deleteTask(id) {
    if (!confirmDelete(id)) {
        return;
    }

    fetch("/tasks?task_id=" + id, {method: "DELETE"})
        .then(response => {
            if (!response.ok) {
                throw new Error("Delete task error: " + response.statusText);
            }
            return response;
        }).then(_ => location.reload());
}

function updateStatus(id) {
    const status = document.querySelector("#task-" + id + " select");
    const value = status.options[status.options.selectedIndex].value;

    fetch(`/tasks?task_id=${id}&status=${value}`, {method: "PATCH"})
        .then(response => {
            if (!response.ok) {
                throw new Error("Update task status error: " + response.statusText);
            }
            return response;
        });
}

function updateDue(id) {
    const due = document.querySelector("#task-" + id + " input[type=\"date\"]");

    fetch(`/tasks?task_id=${id}&due=${due.value}`, {method: "PATCH"})
        .then(response => {
            if (!response.ok) {
                throw new Error("Update task due error: " + response.statusText);
            }
            return response;
        });
}
