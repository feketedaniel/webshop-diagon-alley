function changeAddToCartButtons(sessionOrderItemsJson) {
    for (const [productId, orderItem] of Object.entries(sessionOrderItemsJson)) {
        const buttonContainer = document.querySelector('.cart-button-container[data-prod-id="' + productId + '"]')
        if (buttonContainer !== null) replaceAddToCartButton(buttonContainer, orderItem.amount)
    }
}

function rebuildAddButton(buttonContainer) {
    buttonContainer.innerHTML = ""
    const addButton = document.createElement("a")
    addButton.classList.add("btn", "btn-success")
    addButton.setAttribute("onclick", "addProductToCart(" + buttonContainer.dataset.prodId + ")")
    addButton.innerText = "Add to cart"
    buttonContainer.insertAdjacentElement("beforeend", addButton)
}

function replaceAddToCartButton(buttonContainer, amountCount = 1) {
    buttonContainer.innerHTML = ""
    const amountContainer = createAmountContainer(buttonContainer)
    const addButton = createAddButton(buttonContainer)
    const subButton = createSubButton(buttonContainer)
    amountContainer.innerText = amountCount
    buttonContainer.insertAdjacentElement("beforeend", amountContainer)
    buttonContainer.insertAdjacentElement("beforeend", addButton)
    buttonContainer.insertAdjacentElement("afterbegin", subButton)
}

function createAddButton(buttonContainer){
    const addButton = document.createElement("a")
    addButton.classList.add("btn", "btn-success")
    addButton.setAttribute("onclick", "addProductToCart(" + buttonContainer.dataset.prodId + ")")
    addButton.innerText = "+"
    return addButton
}

function createSubButton(buttonContainer){
    const subButton = document.createElement("a")
    subButton.classList.add("btn", "btn-success")
    subButton.setAttribute("onclick", "subProductToCart(" + buttonContainer.dataset.prodId + ")")
    subButton.innerText = "-"
    return subButton
}

function createAmountContainer(buttonContainer){
    const AmountContainer = document.createElement("span")
    AmountContainer.dataset.prodId = buttonContainer.dataset.prodId
    AmountContainer.classList.add("product-amount-counter")
    return AmountContainer
}
