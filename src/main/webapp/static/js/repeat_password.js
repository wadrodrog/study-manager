var password;
var repeatPassword;
var submit;

window.addEventListener("load", function () {
    password = document.getElementById("password");
    repeatPassword = document.getElementById("repeat-password");
    submit = document.getElementById("submit");
});

function check() {
    submit.disabled = password.value !== repeatPassword.value;
    submit.title = submit.disabled ? "Пароли не совпадают" : "";
    repeatPassword.className = submit.disabled ? "invalid" : "";
}