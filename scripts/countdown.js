var countdown = document.getElementById("countdown");
var progressBar = document.getElementById("progress-bar");
var progressText = document.getElementById("progress-text");

function update() {
    var now = new Date();
    var start = new Date("2025-01-01T00:00:00");
    var target = new Date("2025-12-31T23:59:59");
    target.setFullYear(now.getFullYear());
    start.setFullYear(now.getFullYear());

    var remaining = target.getTime() - now.getTime();

    var days = Math.floor(remaining / (1000 * 60 * 60 * 24));
    var hours = Math.floor(
        (remaining % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60),
    );
    var minutes = Math.floor((remaining % (1000 * 60 * 60)) / (1000 * 60));
    var seconds = Math.floor((remaining % (1000 * 60)) / 1000);

    countdown.innerText = `${days}дн ${hours}ч ${minutes}м ${seconds}с`;

    // Progress bar
    var percent =
        ((now.getTime() - start.getTime()) /
            (target.getTime() - start.getTime())) *
        100;
    progressBar.value = Math.floor(percent);
    progressText.innerText = Math.floor(percent) + "%";
}

update();
setInterval(() => {
    update();
}, 1000);
