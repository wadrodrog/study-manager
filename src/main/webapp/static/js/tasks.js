var sort;
var descending;

window.addEventListener("load", function () {
    sort = document.getElementById("sort");
    descending = document.getElementById("descending");
});

function updateSort() {
    window.location.href = "/tasks?sort=" + sort.value + (descending.checked ? "&descending" : "");
}

function checkTitle(id) {
    var editor = document.querySelector("#task-" + id + " .title .editor");
    var input = document.querySelector("#task-" + id + " .title .editor input");
    var save = document.querySelector("#task-" + id + " .title .editor button");

    var text = input.value.trim();
    save.disabled = text.length < 1 || text.length > 256;
}

function editTitle(id) {
    var display = document.querySelector("#task-" + id + " .title .display");
    var h2 = document.querySelector("#task-" + id + " .title .display h2");
    var editor = document.querySelector("#task-" + id + " .title .editor");
    var input = document.querySelector("#task-" + id + " .title .editor input");

    editor.className = "editor";
    display.className = "display inactive";
    input.value = h2.innerText;
}

function saveTitle(id) {
    var display = document.querySelector("#task-" + id + " .title .display");
    var h2 = document.querySelector("#task-" + id + " .title .display h2");
    var editor = document.querySelector("#task-" + id + " .title .editor");
    var input = document.querySelector("#task-" + id + " .title .editor input");

    editor.className = "editor inactive";
    display.className = "display";
    h2.innerText = input.value;

    fetch(`/tasks?task_id=${id}&title=${h2.innerText}`, {method: "PATCH"})
        .then(response => {
            if (!response.ok) {
                throw new Error("Update task title: " + response.statusText);
            }
            return response;
        });
}

function editContents(id) {
    var display = document.querySelector("#task-" + id + " .contents .display");
    var p = document.querySelector("#task-" + id + " .contents .display p");
    var editor = document.querySelector("#task-" + id + " .contents .editor");
    var textarea = document.querySelector("#task-" + id + " .contents .editor textarea");

    editor.className = "editor";
    display.className = "display inactive";
    textarea.value = p.innerText;
}

function saveContents(id) {
    var display = document.querySelector("#task-" + id + " .contents .display");
    var p = document.querySelector("#task-" + id + " .contents .display p");
    var editor = document.querySelector("#task-" + id + " .contents .editor");
    var textarea = document.querySelector("#task-" + id + " .contents .editor textarea");

    editor.className = "editor inactive";
    display.className = "display";
    p.innerText = textarea.value;

    fetch(`/tasks?task_id=${id}&contents=${p.innerText}`, {method: "PATCH"})
        .then(response => {
            if (!response.ok) {
                throw new Error("Update task contents: " + response.statusText);
            }
            return response;
        });
}

function updatePriority(id) {
    const priority = document.querySelector("#task-" + id + " input[type=number]");
    const value = priority.value;

    fetch(`/tasks?task_id=${id}&priority=${value}`, {method: "PATCH"})
        .then(response => {
            if (!response.ok) {
                throw new Error("Update task priority error: " + response.statusText);
            }
            return response;
        });
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

function confirmDelete(id) {
    const title = document.querySelector("#task-" + id + " .display h2");
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
