loadBackground();
pageSync();

async function pageSync() {
    let sessionOrderJson = await getSessionOrder()
    if (Object.keys(sessionOrderJson).length !== 0) {
        console.log("TODO: rebuild HTML elements based on orderItems")
        changeAddToCartButtons(sessionOrderJson)
        setCartItemCount(Object.keys(sessionOrderJson).length)
    }
}

function loadBackground() {
    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.has("categoryId")) {
        document.body.style.backgroundImage = "url('/static/img/productCategory_" + urlParams.get("categoryId") + ".jpg')"
    } else {
        document.body.style.backgroundImage = "url('/static/img/background_main.jpg')"
    }
}
