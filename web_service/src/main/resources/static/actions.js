/**
 * When clicking on the creature button, the main block change
 */
function onclickCreature(){
    document.getElementById("mainBlock").innerHTML = "    <form name=\"researchForm\" class=\"researchForm\">\n" +
        "        <h1>\n" +
        "            Creature Research\n" +
        "        </h1>\n" +
        "        <label>\n" +
        "            Name: <input class=\"input\" name=\"name\">\n" +
        "        </label>\n" +
        "        <label>\n" +
        "<select id=\"search\" name='searchType'>\n" +
        "  <option value=\"creature\">creature</option>\n" +
        "  <option value=\"author\">author</option>\n" +
        "  <option value=\"book\">book</option>\n" +
        "  <option value=\"affiliation\">affiliation</option>\n" +
        "</select>" +
        "        </label>\n" +
        "\n" +
        "        <div>\n" +
        "            <input type=\"button\" class=\"button\" onclick=\"return creatureSearch()\" value=\"search\">\n" +
        "        </div>\n" +
        "    </form>";
    buttonActivation("creature");
}

/**
 * Print the response from the server in the response block after the content was erased
 */
function creatureSearch(){
    let name = document.forms["researchForm"].elements["name"].value;
    let searchType = document.forms["researchForm"].elements["searchType"].value;
    let request;    //http request to ask for information about creature
    request = new XMLHttpRequest();
    request.onreadystatechange = function (){    //apply the function if the if condition passed
        if (this.readyState === 4 && this.status === 200) {
            console.log(this.responseText);
            let jsonResponse = JSON.parse(this.responseText);
            document.getElementById("responseBlock").innerHTML = "";
            for(let i = 0; i < jsonResponse.length; i++){
                document.getElementById("responseBlock").innerHTML += "<div style='border: black solid'><h3>Name:</h3><p>" + jsonResponse[i]['name'] + "</p>"
                    + "<h3>Description:</h3><p>" + jsonResponse[i]['description'] + "</p>"
                    + "<h3>Affiliation:</h3><p>" + jsonResponse[i]['affiliation'] + "</p>"
                    + "<h3>Creator:</h3><p>" + jsonResponse[i]['author'] + "</p>"
                    + "<h3>First appearance:</h3><p>" + jsonResponse[i]['book'] + " (" + jsonResponse[i]['year'] + ")</p></div>";
            }
        }
    };
    request.open("GET", "/creatureSearch/" + searchType + "?name=" + name, true);
    request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    request.send();
}

/**
 * When clicking on the author button, the main block change
 */
function onclickAuthor(){
    document.getElementById("mainBlock").innerHTML = "    <form name=\"researchForm\" class=\"researchForm\">\n" +
        "        <h1>\n" +
        "            Author Research\n" +
        "        </h1>\n" +
        "        <label>\n" +
        "            Search: <input class=\"input\" name=\"name\">\n" +
        "        </label>\n" +
        "        <label>\n" +
        "<select id=\"search\" name='searchType'>\n" +
        "  <option value=\"surname\">surname</option>\n" +
        "  <option value=\"first_name\">first name</option>\n" +
        "  <option value=\"birth\">birth date</option>\n" +
        "  <option value=\"death\">death date</option>\n" +
        "</select>" +
        "        </label>\n" +
        "\n" +
        "        <div>\n" +
        "            <input type=\"button\" class=\"button\" onclick=\"return authorSearch()\" value=\"search\">\n" +
        "        </div>\n" +
        "    </form>";
    buttonActivation("author");
}

/**
 * Print the response from the server in the response block after the content was erased
 */
function authorSearch(){
    let name = document.forms["researchForm"].elements["name"].value;
    let searchType = document.forms["researchForm"].elements["searchType"].value;
    let request;    //http request to ask for information about creature
    request = new XMLHttpRequest();
    request.onreadystatechange = function (){    //apply the function if the if condition passed
        if (this.readyState === 4 && this.status === 200) {
            console.log(this.responseText);
            let jsonResponse = JSON.parse(this.responseText);
            let str = "";
            for(let i = 0; i < jsonResponse.length; i++){
                str += "<div style='border: black solid'><h3>Name:</h3><p>" + jsonResponse[i]['name'] + "</p>"
                    + "<h3>Dates:</h3><p>" + jsonResponse[i]['date'] + "</p><h3>Books:</h3>";
                for(let j = 0; j < jsonResponse[i]['book'].length; j++){
                    str += "<p>" + jsonResponse[i]['book'][j] + "</p>";
                }
                str += "<h3>Creatures:</h3>";
                for(let j = 0; j < jsonResponse[i]['creature'].length; j++){
                    str += "<p>" + jsonResponse[i]['creature'][j] + "</p>";
                }
                str += "</div>";
                document.getElementById("responseBlock").innerHTML = str;
            }
        }
    };
    request.open("GET", "/authorSearch/" + searchType + "?name=" + name, true);
    request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    request.send();
}

/**
 * When clicking on the book button, the main block change
 */
function onclickBook(){
    document.getElementById("mainBlock").innerHTML = "    <form name=\"researchForm\" class=\"researchForm\">\n" +
        "        <h1>\n" +
        "            Book Research\n" +
        "        </h1>\n" +
        "        <label>\n" +
        "            Search: <input class=\"input\" name=\"name\">\n" +
        "        </label>\n" +
        "        <label>\n" +
        "<select id=\"search\" name='searchType'>\n" +
        "  <option value=\"book\">book</option>\n" +
        "  <option value=\"author\">author</option>\n" +
        "  <option value=\"year\">year</option>\n" +
        "</select>" +
        "        </label>\n" +
        "\n" +
        "        <div>\n" +
        "            <input type=\"button\" class=\"button\" onclick=\"return bookSearch()\" value=\"search\">\n" +
        "        </div>\n" +
        "    </form>";
    buttonActivation("book");
}

/**
 * Print the response from the server in the response block after the content was erased
 */
function bookSearch(){
    let name = document.forms["researchForm"].elements["name"].value;
    let searchType = document.forms["researchForm"].elements["searchType"].value;
    let request;    //http request to ask for information about creature
    request = new XMLHttpRequest();
    request.onreadystatechange = function (){    //apply the function if the if condition passed
        if (this.readyState === 4 && this.status === 200) {
            console.log(this.responseText);
            let jsonResponse = JSON.parse(this.responseText);
            let str = "";
            for(let i = 0; i < jsonResponse.length; i++){
                str += "<div style='border: black solid'><h3>Name:</h3><p>" + jsonResponse[i]['name'] + "</p>"
                    + "<h3>Year:</h3><p>" + jsonResponse[i]['year'] + "</p><h3>Author(s):</h3>";
                for(let j = 0; j < jsonResponse[i]['author'].length; j++){
                    str += "<p>" + jsonResponse[i]['author'][j] + "</p>";
                }
                str += "<h3>Creatures:</h3>";
                for(let j = 0; j < jsonResponse[i]['creature'].length; j++){
                    str += "<p>" + jsonResponse[i]['creature'][j] + "</p>";
                }
                str += "</div>";
                document.getElementById("responseBlock").innerHTML = str;
            }
        }
    };
    request.open("GET", "/bookSearch/" + searchType + "?name=" + name, true);
    request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    request.send();
}

/**
 * When clicking on the affiliation button, the main block change
 */
function onclickAffiliation(){
    document.getElementById("mainBlock").innerHTML = "    <form name=\"researchForm\" class=\"researchForm\">\n" +
        "        <h1>\n" +
        "            Affiliation Research\n" +
        "        </h1>\n" +
        "        <label>\n" +
        "            Search: <input class=\"input\" name=\"name\">\n" +
        "        </label>\n" +
        "        <label>\n" +
        "<select id=\"search\" name='searchType'>\n" +
        "  <option value=\"name\">name</option>\n" +
        "  <option value=\"creature\">creature</option>\n" +
        "</select>" +
        "        </label>\n" +
        "\n" +
        "        <div>\n" +
        "            <input type=\"button\" class=\"button\" onclick=\"return affiliationSearch()\" value=\"search\">\n" +
        "        </div>\n" +
        "    </form>";
    buttonActivation("affiliation");
}

/**
 * Print the response from the server in the response block after the content was erased
 */
function affiliationSearch(){
    let name = document.forms["researchForm"].elements["name"].value;
    let searchType = document.forms["researchForm"].elements["searchType"].value;
    let request;    //http request to ask for information about creature
    request = new XMLHttpRequest();
    request.onreadystatechange = function (){    //apply the function if the if condition passed
        if (this.readyState === 4 && this.status === 200) {
            console.log(this.responseText);
            let jsonResponse = JSON.parse(this.responseText);
            let str = "";
            for(let i = 0; i < jsonResponse.length; i++){
                str += "<div style='border: black solid'><h3>Name:</h3><p>" + jsonResponse[i]['name'] + "</p>"
                    + "<h3>Creatures:</h3>";
                for(let j = 0; j < jsonResponse[i]['creature'].length; j++){
                    str += "<p>" + jsonResponse[i]['creature'][j] + "</p>";
                }
                str += "</div>";
                document.getElementById("responseBlock").innerHTML = str;
            }
        }
    };
    request.open("GET", "/affiliationSearch/" + searchType + "?name=" + name, true);
    request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    request.send();
}

/**
 * Activate the button with the chosen id.
 * @param buttonId
 */
function buttonActivation(buttonId) {
    let len = document.getElementsByClassName("leftButton").length;
    for(let i = 0; i < len; i++){
        if(document.getElementsByClassName("leftButton")[i].classList.contains("activated")){
            document.getElementsByClassName("leftButton")[i].classList.remove("activated");
        }
        if(document.getElementsByClassName("leftButton")[i].id == buttonId){
            document.getElementsByClassName("leftButton")[i].classList.add("activated");
        }
    }
}