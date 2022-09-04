

async function addProductToCart(productId) {
    let response = await fetch("/api/cart/add?productId=" + productId)
    if (response.ok) {
        const buttonContainer = document.querySelector('.cart-button-container[data-prod-id="' + productId + '"]')
        const amountContainer = document.querySelector('.product-amount-counter[data-prod-id="' + productId + '"]')
        if (amountContainer == null) {
            changeAddToCartButton(buttonContainer)
        } else {
            amountContainer.innerText = +amountContainer.innerText + 1
        }
    }
}

async function subProductToCart(productId) {
    let response = await fetch("/api/cart/add?productId=" + productId, {"method": "PUT"})
    if (response.ok) {
        const buttonContainer = document.querySelector('.cart-button-container[data-prod-id="' + productId + '"]')
        const amountContainer = document.querySelector('.product-amount-counter[data-prod-id="' + productId + '"]')
        amountContainer.innerText = +amountContainer.innerText - 1
        if (amountContainer.innerText === "0") {
            rebuildAddButton(buttonContainer)
        }
    }
}

function setCartItemCount(){
    let cart = document.querySelector(".fa-solid .fa-cart-shopping")
    console.log("cartCounter")
    console.log(cart)
}