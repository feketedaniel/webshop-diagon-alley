console.log("main.js loaded")
const urlParams = new URLSearchParams(window.location.search);
const session = window.sessionStorage;

function loadBackground() {
    console.log(urlParams)
    if (urlParams.has("categoryId")) {
        document.body.style.backgroundImage = "url('/static/img/productCategory_" + urlParams.get("categoryId") + ".jpg')"
    } else {
        document.body.style.backgroundImage = "url('/static/img/background_main.jpg')"
    }
}

function sessionTest() {
    session.setItem("teszt", "win")
}
loadBackground();
