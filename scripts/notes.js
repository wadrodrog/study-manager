var container = null;
var loaded = 0;
const notes = [
    {
        title: "Преподаватели",
        contents:
            "Базы данных (лекции): Абрамский М.М.\nБазы данных (практика): Гизатуллин А.Н.\nОРИС (лекции): Ференец А.А.\nОРИС (практика): Иванов М.А.",
    },
    {
        title: "Документы",
        contents:
            "Заявление на оказание материальной поддержки (профсоюз).\nКопия продленного профсоюзного билета.\nКопия паспорта с пропиской.\nБилеты до населенного пункта, где студент прописан.\nРеквизиты карты.\nКопия домовой книги.",
    },
];

function loadMore() {
    if (loaded == notes.length) return;

    if (container == null) {
        container = document.getElementById("notes-container");
    }

    var title = document.createElement("h2");
    title.innerText = notes[loaded].title;
    var contents = document.createElement("p");
    contents.innerText = notes[loaded].contents;
    container.appendChild(title);
    container.appendChild(contents);

    loaded++;
    if (loaded == notes.length) {
        var loadMoreButton = document.getElementById("load-more-button");
        loadMoreButton.remove();
    }
}
