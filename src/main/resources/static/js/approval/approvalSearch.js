initializeScriptAS();

function initializeScriptAS() {
  const inputField = document.querySelector("#nameSearch");
  const searchBox = document.querySelector(".searchBox");
  if (inputField) {
    inputField.addEventListener("click", function (event) {
      searchBox.style.display = "block";
    });
    searchBox.addEventListener("mouseleave", function (event) {
      searchBox.style.display = "none";
    });
  } else {
    console.error("Input field not found");
  }
}