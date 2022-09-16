setTotal()


function setTotal() {
    const sub = document.querySelectorAll(".confirmation-subtotal")
    let total = 0
    sub.forEach(subTotal => {
console.log(subTotal.innerText)
console.log(parseFloat(subTotal.innerText))
        total += +parseFloat(subTotal.innerText)
    })
    const cell = document.querySelector("#confirmation-total")
    cell.innerText = +total.toFixed(2)
}