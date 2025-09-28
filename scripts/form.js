var formName = document.getElementById("form-name");
var formSend = document.getElementById("form-send");
formName.addEventListener("change", function () {
    if (/^[a-zA-Zа-яА-ЯёЁ]{2,}$/.test(formName.value)) {
        formName.className = "valid";
        formSend.disabled = false;
    } else {
        formName.className = "invalid";
        formSend.disabled = true;
    }
});
