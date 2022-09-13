const billingCountries = document.querySelector("#billing-country")
const billingCities = document.querySelector("#billing-city")
const shippingCountries = document.querySelector("#shipping-country")
const shippingCities = document.querySelector("#shipping-city")

fillUpCountriesDials()


async function fillUpCountriesDials() {
    let countriesAndDials = await getCountryAndDial()
    console.log(countriesAndDials)
    countriesAndDials.forEach(data =>{createOption(data.name)})
}

function createOption(text) {
    console.log(text)
}


async function getCountryAndDial() {
    let response = await fetch("https://countriesnow.space/api/v0.1/countries/info?returns=dialCode")
    if (response.ok) {
        return (await response.json()).data
    } else {
        console.log(response)
    }
}

async function getCities(country) {

    let requestOptions = {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({'country': country}),
    };

    let response = await fetch("https://countriesnow.space/api/v0.1/countries/cities", requestOptions)
    let cities = await response.json()
    console.log(cities)
}