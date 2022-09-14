setUpForm()
setSession()

function setUpForm() {
    let form = document.querySelector("[class='payment-form-card']")
    form.addEventListener("keydown", (ev) => {
        formOnEnter(ev, form)
    })
    let credits = document.querySelectorAll(".credit")
    let paypal = document.querySelector(".paypal")
}

async function setSession() {
    let sessionOrder = await fetch("/api/getSessionOrder")
    if (sessionOrder.ok) {
        const order = await sessionOrder.json()
        const totalContainer = document.querySelector(".payment-total")
        if (order !== null) {
            let total = 0
            for (const orderItem of Object.values( order)) {
                total += orderItem.defaultPrice * orderItem.amount
            }
            totalContainer.innerText = "Total price:"+total.toFixed(2)+" GAL"
        }
        console.log(order)
        console.log(order.id)
        let paypalQrImg = await fetch("https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=http%3A%2F%2Flocalhost%3A8080%2Fpayment%3ForderId%3D"+order.orderId)
        let paypalImgContainer = document.querySelector("#paypal-qr")
        paypalImgContainer.setAttribute("src","https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=http%3A%2F%2Flocalhost%3A8080%2Fpayment%3ForderId%3D"+order.id)
    }
}

function expiryValidation(){
    const expiry = document.querySelector("#card-expiry").value
    const expiryTime = new Date(expiry).getTime()+24*60*60*1000
    const now = new Date().getTime()
    if(expiryTime<=now)alert("Card expired")
    return expiryTime>=now
}