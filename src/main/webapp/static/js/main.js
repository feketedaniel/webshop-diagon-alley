setRouteContent();
pageSync();

async function pageSync() {
    let response = await fetch("/api/getSessionOrder")
    if (response.ok) {
        let sessionOrderJson = await response.json()
        if (sessionOrderJson !== null) {
            refreshCartItems(sessionOrderJson)
            changeAddToCartButtons(sessionOrderJson)
            setCartItemCount(Object.keys(sessionOrderJson).length)
            setCartTotal(sessionOrderJson)
        }
    } else {
        console.log(response)
    }
}

function setRouteContent() {
    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.has("categoryId")) {
        document.body.style.backgroundImage = "url('/static/img/productCategory_" + urlParams.get("categoryId") + ".jpg')"
    } else if (window.location.pathname==="/payment"){
        hideCartFromDom()
        document.body.style.backgroundImage = "url('/static/img/checkout.png')"
    } else {
        document.body.style.backgroundImage = "url('/static/img/background_main.jpg')"
    }
}
