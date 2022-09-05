function addCheckOutToCart() {

}

function setCartTotal(sessionOrderJson) {
    const totalContainer = document.querySelector(".cart-total-price")
    if (sessionOrderJson !== null) {
        let total = 0
        for (const orderItem of Object.values(sessionOrderJson)) {
            total += orderItem.defaultPrice * orderItem.amount
        }
        totalContainer.innerText = +total.toFixed(2)
    }
}

async function addOrderItemToLine(orderItem) {
    const cartItemsContainer = document.querySelector(".cart-items")
    const orderItemContainer = createOrderItemContainer(orderItem)
    cartItemsContainer.insertAdjacentElement("beforeend", orderItemContainer)
}

function setCartItemCount(itemCount) {
    let cart = document.querySelector(".fa-solid.fa-cart-shopping")
    cart.setAttribute("value", itemCount)
}

async function addProductToCart(orderItemId) {
    const response = await fetch("/api/cart/add?productId=" + orderItemId)
    if (response.ok) {
        const order = await response.json()
        setCartTotal(order)
        const orderItem = order[orderItemId]
        const buttonContainer = document.querySelector('.cart-button-container[data-prod-id="' + orderItemId + '"]')
        const cardAmountContainer = document.querySelector('.product-amount-counter[data-prod-id="' + orderItemId + '"]')
        if (cardAmountContainer == null) {
            replaceAddToCartButton(buttonContainer)
            let oldItemCount = document.querySelector(".fa-solid.fa-cart-shopping").getAttribute("value")
            setCartItemCount(+oldItemCount + 1)
            await addOrderItemToLine(orderItem)
        } else {
            cardAmountContainer.innerText = orderItem.amount
            const lineAmountContainer = document.querySelector('.line-amount-counter[data-prod-id="' + orderItemId + '"]')
            lineAmountContainer.innerText = orderItem.amount
            const subTotalContainer = document.querySelector('.order-item-subtotal[data-prod-id="' + orderItemId + '"]')
            subTotalContainer.innerText = +(orderItem.defaultPrice * orderItem.amount).toFixed(2) + " GAL"

        }
    } else {
        console.log(response)
    }
}

async function subProductToCart(orderItemId) {
    let response = await fetch("/api/cart/sub?productId=" + orderItemId, {"method": "PUT"})
    if (response.ok) {
        const order = await response.json()
        setCartTotal(order)
        const orderItem = order[orderItemId]
        if (orderItem == null) {
            rebuildToDefault(orderItemId)
        } else {
            const cardAmountContainer = document.querySelector('.product-amount-counter[data-prod-id="' + orderItemId + '"]')
            const lineAmountContainer = document.querySelector('.line-amount-counter[data-prod-id="' + orderItemId + '"]')
            const subTotalContainer = document.querySelector('.order-item-subtotal[data-prod-id="' + orderItemId + '"]')

            cardAmountContainer.innerText = orderItem.amount
            lineAmountContainer.innerText = orderItem.amount
            subTotalContainer.innerText = +(orderItem.defaultPrice * orderItem.amount).toFixed(2) + " GAL"
        }
    } else {
        console.log(response)
    }
}

async function removeOrderItem(orderItemId) {
    const response = await fetch("/api/cart/remove?productId=" + orderItemId, {"method": "DELETE"})
    if (response.ok) {
        const order = await response.json()
        setCartTotal(order)
        rebuildToDefault(orderItemId)
    } else {
        console.log(response)
    }
}

function refreshCartItems(sessionOrderJson) {
    for (const orderItem of Object.values(sessionOrderJson)) {
        const cartItemsContainer = document.querySelector(".cart-items")
        const orderItemContainer = createOrderItemContainer(orderItem)
        cartItemsContainer.insertAdjacentElement("beforeend", orderItemContainer)
    }
}

function rebuildToDefault(orderItemId){
    const buttonContainer = document.querySelector('.cart-button-container[data-prod-id="' + orderItemId + '"]')
    rebuildAddButton(buttonContainer)
    const oldItemCount = document.querySelector(".fa-solid.fa-cart-shopping").getAttribute("value")
    setCartItemCount(+oldItemCount - 1)
    const orderItemContainer = document.querySelector('.order-item-container[data-prod-id="' + orderItemId + '"]')
    setTimeout(() => {
        orderItemContainer.remove()
    }, 500)
    orderItemContainer.classList.add("disappear")
}

function createOrderItemContainer(orderItem) {
    const orderItemContainer = document.createElement("li")
    orderItemContainer.classList.add("order-item-container")
    orderItemContainer.dataset.prodId = orderItem.id

    const nameContainer = document.createElement("div")
    nameContainer.classList.add("order-item-name")
    nameContainer.innerText = orderItem.name

    const buttonContainer = document.createElement("div")
    buttonContainer.classList.add("order-item-button-container")
    buttonContainer.dataset.prodId = orderItem.id

    const addButton = createAddButton(buttonContainer)
    addButton.innerHTML = "<i class=\"fa-solid fa-circle-plus\"></i>"

    const subButton = createSubButton(buttonContainer)
    subButton.innerHTML = "<i class=\"fa-solid fa-circle-minus\"></i>"

    const amountCounter = document.createElement("span")
    amountCounter.dataset.prodId = orderItem.id
    amountCounter.classList.add("line-amount-counter")
    amountCounter.innerText = orderItem.amount
    buttonContainer.insertAdjacentElement("beforeend", amountCounter)
    buttonContainer.insertAdjacentElement("afterbegin", subButton)
    buttonContainer.insertAdjacentElement("beforeend", addButton)

    const priceContainer = document.createElement("div")
    priceContainer.classList.add("order-item-price")
    priceContainer.innerText = orderItem.defaultPrice + " GAL"

    const subTotalContainer = document.createElement("div")
    subTotalContainer.classList.add("order-item-subtotal")
    subTotalContainer.dataset.prodId = orderItem.id
    subTotalContainer.innerText = +(orderItem.amount * orderItem.defaultPrice).toFixed(2) + " GAL"

    const deleteButton = document.createElement("a")
    deleteButton.classList.add("order-item-delete")
    deleteButton.setAttribute("onclick", "removeOrderItem(" + orderItem.id + ")")
    deleteButton.innerHTML = "<i class='fa-solid fa-circle-xmark'></i>"
    const firsRow = document.createElement("div")
    firsRow.classList.add("order-item-container-row")
    firsRow.insertAdjacentElement("beforeend", nameContainer)
    firsRow.insertAdjacentElement("beforeend", deleteButton)

    const secondRow = document.createElement("div")
    secondRow.classList.add("order-item-container-row")
    secondRow.insertAdjacentElement("beforeend", priceContainer)
    secondRow.insertAdjacentElement("beforeend", buttonContainer)
    secondRow.insertAdjacentElement("beforeend", subTotalContainer)

    orderItemContainer.insertAdjacentElement("beforeend", firsRow)
    orderItemContainer.insertAdjacentElement("beforeend", secondRow)

    return orderItemContainer
}