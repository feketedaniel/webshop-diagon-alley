setTotal()


function setTotal() {
    const sub = document.querySelectorAll(".confirmation-subtotal")
    let total = 0
    sub.forEach(subTotal => {
        total += +parseFloat(subTotal.innerText)
    })
    const cell = document.querySelector("#confirmation-total")
    cell.innerText ="Your Total is: " +total.toFixed(2) + " GAL"
}