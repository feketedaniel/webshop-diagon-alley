function rebuildButtons(sessionOrderItemsJson) {
    for (const [productId, orderItem] of Object.entries(sessionOrderItemsJson)) {
        const buttonContainer = document.querySelector('.cart-button-container[data-prod-id="' + productId + '"]')
        if(buttonContainer !==null)changeAddToCartButton(buttonContainer, orderItem.amount)
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

function changeAddToCartButton(buttonContainer, amountCount=1) {
    buttonContainer.innerHTML = ""
    const amountCounter = document.createElement("span")
    const addButton = document.createElement("a")
    const subButton = document.createElement("a")
    amountCounter.dataset.prodId = buttonContainer.dataset.prodId
    amountCounter.classList.add("product-amount-counter")
    addButton.classList.add("btn", "btn-success")
    subButton.classList.add("btn", "btn-success")
    addButton.setAttribute("onclick", "addProductToCart(" + buttonContainer.dataset.prodId + ")")
    subButton.setAttribute("onclick", "subProductToCart(" + buttonContainer.dataset.prodId + ")")
    amountCounter.innerText = amountCount
    addButton.innerText = "+"
    subButton.innerText = "-"
    buttonContainer.insertAdjacentElement("beforeend", amountCounter)
    buttonContainer.insertAdjacentElement("beforeend", addButton)
    buttonContainer.insertAdjacentElement("afterbegin", subButton)
}