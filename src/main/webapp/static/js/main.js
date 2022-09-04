console.log("main.js loaded")
const urlParams = new URLSearchParams(window.location.search);
loadBackground();
pageSync();

async function pageSync() {
    let response = await fetch("/api/sessionSync")
    let sessionOrderItemsJson = (await response.json())["orderItems"]
    console.log(sessionOrderItemsJson)
    if (Object.keys(sessionOrderItemsJson).length !== 0) {
        console.log("TODO: rebuild HTML elements based on orderItems")
        rebuildButtons(sessionOrderItemsJson)
        console.log("TODO: refresh amounts in button container")
        console.log("TODO: refresh cart item count")
    }
}

function loadBackground() {
    console.log(urlParams)
    if (urlParams.has("categoryId")) {
        document.body.style.backgroundImage = "url('/static/img/productCategory_" + urlParams.get("categoryId") + ".jpg')"
    } else {
        document.body.style.backgroundImage = "url('/static/img/background_main.jpg')"
    }
}
