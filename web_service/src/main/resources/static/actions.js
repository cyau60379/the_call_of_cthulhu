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
}

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
    request.open("POST", "/creatureSearch", true);
    request.setRequestHeader("Content-type", "application/json;charset=UTF-8");
    request.send(JSON.stringify({"name": name, "searchType": searchType}));
}