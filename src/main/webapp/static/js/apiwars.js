const billingCountries = document.querySelector("#billing-country")
const billingCities = document.querySelector("#billing-city")
const shippingCountries = document.querySelector("#shipping-country")
const shippingCities = document.querySelector("#shipping-city")

setUpCheckoutPage()
    .catch(error => console.log('error', error))


async function setUpCheckoutPage() {
    let response = await fetch("https://countriesnow.space/api/v0.1/countries/info?returns=dialCode")
    if (response.ok) {
        let countriesAndDials = (await response.json()).data
        countriesAndDials.forEach(data => {
            createCountryOptions(data)
        })
        setupCountryEventListener()
            .catch(error => console.log("error", error))
    } else {
        console.log(response)
    }
}

function createCountryOptions(data) {
    //data object : {name: "country", dialCode: "dialCode"}
    let option = document.createElement("option")
    option.setAttribute("id",data.name)
    option.innerText = data.name
    option.value = data.name.toLowerCase()
    option.dataset.dialCode = data.dialCode
    let optionClone = option.cloneNode(true)
    billingCountries.insertAdjacentElement("beforeend", option)
    shippingCountries.appendChild(optionClone)
}

async function setupCountryEventListener() {
    billingCountries.addEventListener("change", async (e) => {
        let country = e.target.value
        let dialCode = document.querySelector("option[value="+country+"]").dataset.dialCode
        document.querySelector('.phone-dial').innerText = "+" + dialCode + "/"
        let response = await fetch("https://countriesnow.space/api/v0.1/countries/cities", {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({'country': country})
        })
        if (response.ok) {
            let cities = (await response.json()).data
            createCityOptions(cities)
        }
    })
}

function createCityOptions(cities) {
    billingCities.innerHTML = ""
    shippingCities.innerHTML = ""
    let option = document.createElement("option")
    option.innerText = "Select a City"
    let optionClone = option.cloneNode(true)
    billingCities.insertAdjacentElement("beforeend", option)
    shippingCities.appendChild(optionClone)
    cities.forEach(city => {
        let option = document.createElement("option")
        option.innerText = city
        option.value = city.toLowerCase()
        let optionClone = option.cloneNode(true)
        billingCities.insertAdjacentElement("beforeend", option)
        shippingCities.appendChild(optionClone)
    })
    option.disabled = true
    option.selected = true
    optionClone.disabled = true
    optionClone.selected = true
}
