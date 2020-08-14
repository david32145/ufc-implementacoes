import services from "./services.js"

window.addEventListener("load", init)

// `
//   <div class="checkbox-group">
//     <input type="checkbox">
//     <label>Processing - 1 micro - $ 1,00 per hour</label>
//   </div>
// `

function findServiceById(id) {
  return services.find(service => service.id === Number(id))
}

function createCheckboxGroupElement(service) {
  const div = document.createElement("div")
  const checkbox = document.createElement("input")
  const label = document.createElement("label")

  div.classList.add("checkbox-group")
  
  checkbox.type = "checkbox"
  checkbox.id = `checkbox-${service.id}`
  checkbox.value = service.id
  label.innerText = service.title
  label.htmlFor = `checkbox-${service.id}`
  
  div.appendChild(checkbox)
  div.appendChild(label)
  return div
}

/**
 * 
 * @param {HTMLDivElement} element 
 */
function createProcessingServices(element){
  const processingServices = services.filter(service => service.type === "PROCESSING")
  processingServices.forEach(service => {
    const checkboxGroupElement = createCheckboxGroupElement(service)
    element.appendChild(checkboxGroupElement)
  })
}

/**
 * 
 * @param {HTMLDivElement} element 
 */
function createStorageServices(element) {
  const storageServices = services.filter(service => service.type === "STORAGE")
  storageServices.forEach(service => {
    const checkboxGroupElement = createCheckboxGroupElement(service)
    element.appendChild(checkboxGroupElement)
  })
}

function createServices() {
  const processingServicesContainer = document.querySelector(".processing-services")
  const storageServicesContainer = document.querySelector(".storage-services")
  createProcessingServices(processingServicesContainer)
  createStorageServices(storageServicesContainer)
}

function init() {
  createServices()
  const name = document.querySelector("#name")
  const email =  document.querySelector("#email")
  const birthDate = document.querySelector("#birthDate")
  const checkboxs = document.querySelectorAll(".checkbox-group input[type=checkbox]")
  const btnClear = document.querySelector("#btn-clear")
  const form = document.getElementById("form")
  const tableBody = document.querySelector("table.table tbody")
  
  function getCheckedServices() {
    const checkedServices = []
    checkboxs.forEach(checkbox => {
      if(checkbox.checked) {
        checkedServices.push(findServiceById(checkbox.value))
      }
    })
    return checkedServices
  }

  function clearForm() {
    name.value = ""
    email.value = ""
    birthDate.value =  ""
    checkboxs.forEach(checkbox => {
      checkbox.checked = false
    })
  }

  function addItem(event) {
    event.preventDefault()
    const tableRow = document.createElement("tr")
    const nameCell = document.createElement("td")
    const emailCell = document.createElement("td")
    const birthDateCell = document.createElement("td")
    const servicesCell = document.createElement("td")
    const totalCell = document.createElement("td")

    const checkedServices = getCheckedServices()
    const servicesInnerText = checkedServices
      .map(service => service.title)
      .join('\n')
    const totalValue = checkedServices
      .reduce((acc, service) => service.pricePerHour + acc, 0)
    console.log(totalValue)

    nameCell.innerText = name.value
    emailCell.innerText = email.value
    birthDateCell.innerText =  birthDate.value
    servicesCell.innerText = servicesInnerText
    totalCell.innerText = `$ ${totalValue.toFixed(2)} per hour`

    tableRow.append(nameCell, emailCell, birthDateCell, servicesCell, totalCell)
    tableBody.append(tableRow)

    clearForm()
  }

  btnClear.addEventListener("click", clearForm)
  form.addEventListener("submit", addItem)
}