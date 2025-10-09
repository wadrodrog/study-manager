var img;
var cnt;
var current = 1;
const TOTAL = 4;

window.addEventListener("load", function () {
    img = document.getElementById("carousel-image");
    cnt = document.getElementById("carousel-counter");
});

function prev() {
    current--;
    if (current == 0) {
        current = TOTAL;
    }
    updateCarousel();
}

function next() {
    current++;
    if (current > TOTAL) {
        current = 1;
    }
    updateCarousel();
}

function updateCarousel() {
    img.src = `img/cats/${current}.gif`;
    cnt.innerText = `${current}/${TOTAL}`;
}
