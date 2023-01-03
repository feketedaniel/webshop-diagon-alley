setSession().then(r => setUpEventHandlers())

function setUpEventHandlers() {
    let form = document.querySelector(".payment-form-card")
    form.addEventListener("keydown", (ev) => {
        formOnEnter(ev, form)
    })
    let credits = document.querySelectorAll(".credit")
    let paypal = document.querySelector(".paypal")
    let creditForm = document.querySelector(".payment-form-card")
    let paypalForm = document.querySelector(".payment-form-paypal")
    credits.forEach(credit => {
        credit.addEventListener("click", (e) => {
            creditForm.classList.remove("hidden")
            paypalForm.classList.add("hidden")
        })
    })
    paypal.addEventListener("click", (e) => {
        paypalForm.classList.remove("hidden")
        creditForm.classList.add("hidden")
    })
}

async function setSession() {
    let sessionOrderJson = await fetch("/api/getSessionOrder")
    if (sessionOrderJson.ok) {
        const sessionOrder = await sessionOrderJson.json()
        const orderItemList = await sessionOrder.orderItems
        const totalContainer = document.querySelector(".payment-total")
        if (orderItemList !== null) {
            let total = 0
            for (const orderItem of Object.values(orderItemList)) {
                total += orderItem.defaultPrice * orderItem.amount
            }
            totalContainer.innerText = "Total price:" + total.toFixed(2) + " GAL"
        }
        let paypalImgContainer = document.querySelector("#paypal-qr")
        paypalImgContainer.setAttribute("src", "https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=http%3A%2F%2Flocalhost%3A8080%2Fpayment%3ForderId%3D" + sessionOrder.id)
        let paypalLinkContainer = document.querySelector("#paypal-link")
        paypalLinkContainer.setAttribute("href", "http://localhost:8080/payment?orderId=" + sessionOrder.id)
        setMinExpiry()
    }
}

function setMinExpiry() {
    const currentDate = new Date()
    const year = currentDate.toLocaleString("default", {year: "numeric"})
    const month = currentDate.toLocaleString("default", {month: "2-digit"})
    const formattedMin = year + "-" + month
    document.querySelector("#card-expiry").setAttribute("min", formattedMin)
}

function expiryValidation() {
    const expiry = document.querySelector("#card-expiry").value
    const expiryTime = new Date(expiry)
    expiryTime.setMonth(expiryTime.getMonth() + 1)
    const now = new Date()
    if (expiryTime <= now) alert("Card expired")
    return expiryTime > now
}