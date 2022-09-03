console.log("main.js loaded")
const urlParams = new URLSearchParams(window.location.search);

function loadBackground() {
    console.log(urlParams)
    if(urlParams.toString()===""){
        document.body.style.backgroundImage = "url('/static/img/background_main.jpg')"
    } else if(urlParams.has("categoryId")){
        document.body.style.backgroundImage = "url('/static/img/productCategory_"+urlParams.get("categoryId")+".jpg')"
    }
}

loadBackground();