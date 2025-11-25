const start = new Date("2025-09-01");

window.addEventListener("load", function () {
    setInterval(updateTime, 5000);
    updateTime();
});

function updateTime() {
    const now = new Date();
    const difference = now - start;
    const daysPassed = Math.floor(difference / (1000 * 60 * 60 * 24));
    const week = Math.floor((daysPassed + start.getDay()) / 7 + 1);
    const options = {
        weekday: 'long',
        year: 'numeric',
        month: 'long',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit',
        hour12: false
    };
    const formatted = new Intl.DateTimeFormat('ru-RU', options).format(now);
    document.getElementById('datetime-display').textContent = formatted + ". Неделя: " + week;
}
