function hideCartFromDom() {
    const cart = document.querySelector(".cart")
    cart.style.display = "none"
}

function setCartTotal(orderItems) {
    const totalContainer = document.querySelector(".cart-total-price")
    if (orderItems !== null) {
        let total = 0
        for (const orderItem of Object.values(orderItems)) {
            total += orderItem.defaultPrice * orderItem.amount
        }
        totalContainer.innerText = +total.toFixed(2)
    }
}

// async function addOrderItemToLine(orderItem) {
//     const cartItemsContainer = document.querySelector(".cart-items")
//     const orderItemContainer = createOrderItemContainer(orderItem)
//     cartItemsContainer.insertAdjacentElement("beforeend", orderItemContainer)
// }

function setCartItemCount(itemCount) {
    let cart = document.querySelector(".fa-solid.fa-cart-shopping")
    cart.setAttribute("value", itemCount)
}

async function addProductToCart(productId) {
    const response = await fetch("/api/cart/add?productId=" + productId)
    if (response.ok) {
        await pageSync()
    } else {
        console.log(response)
    }
}

async function subProductToCart(productId) {
    let response = await fetch("/api/cart/sub?productId=" + productId, {"method": "PUT"})
    if (response.ok) {
        const cardAmountContainer = document.querySelector('.line-amount-counter[data-prod-id="' + productId + '"]')
        if (parseInt(cardAmountContainer.innerText) === 1) rebuildToDefault(productId)
        await pageSync()
    } else {
        console.log(response)
    }
}

async function removeOrderItem(orderItemId) {
    const response = await fetch("/api/cart/remove?productId=" + orderItemId, {"method": "DELETE"})
    if (response.ok) {
        const session = await response.json()
        const order = await session.orderItems
        setCartTotal(order)
        rebuildToDefault(orderItemId)
    } else {
        console.log(response)
    }
}

function refreshCartItems(orderItemList) {
    for (const orderItem of orderItemList) {
        const cartItemsContainer = document.querySelector(".cart-items")
        const orderItemContainer = document.querySelector('.order-item-container[data-prod-id="' + orderItem.productId + '"]')
        if (orderItemContainer == null) {
            cartItemsContainer.insertAdjacentElement("beforeend", createOrderItemContainer(orderItem))
        } else {
            const lineAmountContainer = document.querySelector('.line-amount-counter[data-prod-id="' + orderItem.productId + '"]')
            lineAmountContainer.innerText = orderItem.amount
            const subTotalContainer = document.querySelector('.order-item-subtotal[data-prod-id="' + orderItem.productId + '"]')
            subTotalContainer.innerText = +(orderItem.defaultPrice * orderItem.amount).toFixed(2) + " GAL"
        }
    }
}

function rebuildToDefault(productId) {
    const buttonContainer = document.querySelector('.cart-button-container[data-prod-id="' + productId + '"]')
    if (buttonContainer) {
        rebuildAddButton(buttonContainer)
    }
    const oldItemCount = document.querySelector(".fa-solid.fa-cart-shopping").getAttribute("value")
    setCartItemCount(+oldItemCount - 1)
    const orderItemContainer = document.querySelector('.order-item-container[data-prod-id="' + productId + '"]')
    setTimeout(() => {
        orderItemContainer.remove()
    }, 1000)
    orderItemContainer.classList.add("disappear")
}

function createOrderItemContainer(orderItem) {
    const orderItemContainer = document.createElement("li")
    orderItemContainer.classList.add("order-item-container")
    orderItemContainer.dataset.prodId = orderItem.productId

    const nameContainer = document.createElement("div")
    nameContainer.classList.add("order-item-name")
    nameContainer.innerText = orderItem.name

    const buttonContainer = document.createElement("div")
    buttonContainer.classList.add("order-item-button-container")
    buttonContainer.dataset.prodId = orderItem.productId

    const addButton = createAddButton(buttonContainer)
    addButton.innerHTML = "<i class=\"fa-solid fa-circle-plus\"></i>"

    const subButton = createSubButton(buttonContainer)
    subButton.innerHTML = "<i class=\"fa-solid fa-circle-minus\"></i>"

    const amountCounter = document.createElement("span")
    amountCounter.dataset.prodId = orderItem.productId
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
    subTotalContainer.dataset.prodId = orderItem.productId
    subTotalContainer.innerText = +(orderItem.amount * orderItem.defaultPrice).toFixed(2) + " GAL"

    const deleteButton = document.createElement("a")
    deleteButton.classList.add("order-item-delete")
    deleteButton.setAttribute("onclick", "removeOrderItem(" + orderItem.productId + ")")
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