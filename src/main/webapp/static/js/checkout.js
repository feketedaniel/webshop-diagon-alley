const billingCountries = document.querySelector("#billing-country")
const billingCities = document.querySelector("#billing-city")
const shippingCountries = document.querySelector("#shipping-country")
const shippingCities = document.querySelector("#shipping-city")
const emailInput = document.querySelector("#e-mail")

setupArrowbox()
setUpCheckoutPage()
    .catch(error => console.log('error', error))

function setupArrowbox() {
    let arrowbox = document.querySelector("#e-mail-arrowbox")
    arrowbox.addEventListener("mouseleave", () => {
        arrowbox.innerText = ""
        arrowbox.classList.add("hidden")
    })
    arrowbox.addEventListener("click", () => {
        emailInput.value = document.querySelector("#e-mail-suggestion").innerText
    })

}

async function setUpCheckoutPage() {
    let form = document.querySelector(".checkout-form")
    form.addEventListener("keydown", function (ev) {
        if (ev.keyCode === 13) {
            ev.preventDefault()
            let current = $(ev.target);
            let index = parseFloat(current.attr('data-index'));
            $('[data-index="' + (index + 1).toString() + '"]').focus();
        }
    })
    emailInput.addEventListener("change", () => {
        let email = emailInput.value.trim()
        if (email) emailValidation(email);
    })
    let response = await fetch("https://countriesnow.space/api/v0.1/countries/info?returns=dialCode")
    if (response.ok) {
        let countriesAndDials = (await response.json()).data
        countriesAndDials.sort((a, b) => {
            let aName = a.name.toLowerCase(),
                bName = b.name.toLowerCase();

            if (aName < bName) {
                return -1;
            }
            if (aName > bName) {
                return 1;
            }
            return 0;
        });
        countriesAndDials.forEach(data => {
            createCountryOptions(data)
        })
        setupCountryEventListener()
            .catch(error => console.log("error", error))
    } else {
        console.log(response)
    }
}

async function emailValidation(email) {
    let response = await fetch("https://emailvalidation.abstractapi.com/v1/?api_key=afb5d4e2ffc3440093ba67786a0d836d&email=" + email)
    if (response.ok) {
        let json = await response.json()
        let validationIcon = document.querySelector("#email-validation-icon")
        validationIcon.className = ""
        if (json.autocorrect) emailSuggestion(json.autocorrect)
        if (json.is_valid_format.value) {
            validationIcon.classList.add("fa-regular", "fa-circle-check")
            emailInput.value = email
            emailInput.dataset.isValid = "true"
        } else {
            validationIcon.classList.add("fa-solid", "fa-circle-xmark")
            let arrowbox = document.querySelector("#e-mail-arrowbox")
            arrowbox.innerText = "Invalid email address"
            arrowbox.classList.remove("hidden")
            emailInput.dataset.isValid = ""
        }
    } else {
        console.log("API service fail")
    }
}

function invalidEmailAlert() {

    console.log("todo: make some alert")
}

function validateSubmit() {
    return Boolean(emailInput.dataset.isValid)
}

function emailSuggestion(email) {
    let arrowbox = document.querySelector("#e-mail-arrowbox")
    arrowbox.insertAdjacentText("afterbegin", "You mean: ")
    arrowbox.insertAdjacentText("beforeend", "?")
    let textContainer = document.querySelector("#e-mail-suggestion")
    textContainer.innerText = email
    arrowbox.classList.remove("hidden")
}

function createCountryOptions(data) {
    //data object : {name: "country", dialCode: "dialCode"}
    let option = document.createElement("option")
    option.setAttribute("id", data.name)
    option.innerText = data.name
    option.value = data.name.toLowerCase()
    option.dataset.dialCode = data.dialCode
    let optionClone = option.cloneNode(true)
    billingCountries.insertAdjacentElement("beforeend", option)
    shippingCountries.appendChild(optionClone)
}

async function setupCountryEventListener() {
    billingCountries.addEventListener("change", async function (e) {
        await setupCitiesOptions(billingCities, e)
    })
    shippingCountries.addEventListener("change", async function (e) {
        await setupCitiesOptions(shippingCities, e)
    })
}

async function setupCitiesOptions(container, e) {
    let country = e.target.value
    let dialCode = document.querySelector("option[value='" + country + "']").dataset.dialCode
    let dialCodeContainer = document.querySelector('#phone')
    dialCodeContainer.value = "+" + dialCode
    let response = await fetch("https://countriesnow.space/api/v0.1/countries/cities", {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({'country': country})
    })
    let cities
    if (response.ok) {
        cities = (await response.json()).data
    } else {
        console.log("API error, cities replaced with country name ", response)
        cities = [country]
    }
    createCityOptions(container, cities)

}

function createCityOptions(container, cities) {
    container.innerHTML = ""
    let option = document.createElement("option")
    option.value = ""
    option.innerText = "Select a City"
    let optionClone = option.cloneNode(true)
    container.insertAdjacentElement("beforeend", option)
    cities.forEach(city => {
        let option = document.createElement("option")
        option.innerText = city
        option.value = city.toLowerCase()
        container.insertAdjacentElement("beforeend", option)
    })
    option.disabled = true
    option.selected = true
}
