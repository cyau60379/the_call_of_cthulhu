function onclickCreature(){
    document.getElementById("mainBlock").innerHTML = "    <form name=\"researchForm\" class=\"researchForm\">\n" +
        "        <h1>\n" +
        "            Creature Research\n" +
        "        </h1>\n" +
        "        <label>\n" +
        "            Name: <input class=\"input\" name=\"name\">\n" +
        "        </label>\n" +
        "\n" +
        "        <label>\n" +
        "            Author: <input class=\"input\" name=\"author\">\n" +
        "        </label>\n" +
        "\n" +
        "        <label>\n" +
        "            Book: <input class=\"input\" name=\"book\">\n" +
        "        </label>\n" +
        "\n" +
        "        <label>\n" +
        "            Affiliation: <input class=\"input\" name=\"affiliation\">\n" +
        "        </label>\n" +
        "\n" +
        "        <div>\n" +
        "            <input type=\"button\" class=\"button\" onclick=\"return creatureSearch()\" value=\"search\">\n" +
        "        </div>\n" +
        "    </form>";
}

function creatureSearch(){
    let name = document.forms["researchForm"].elements["name"].value;
    let author = document.forms["researchForm"].elements["author"].value;
    let book = document.forms["researchForm"].elements["book"].value;
    let affiliation = document.forms["researchForm"].elements["affiliation"].value;
    let request;    //http request to ask for information about creature
    request = new XMLHttpRequest();
    request.onreadystatechange = function (){    //apply the function if the if condition passed
        if (this.readyState === 4 && this.status === 200) {
            document.getElementById("responseBlock").innerHTML = this.responseText;
        }
    };
    request.open("POST", "/creatureSearch", true);
    request.setRequestHeader("Content-type", "application/json;charset=UTF-8");
    request.send(JSON.stringify({"name": name, "author": author, "book": book, "affiliation": affiliation}));
}


function modificationUser() {
    //recuperation des informations du formulaire modification
    let nom = document.forms["modification"].elements["user_nom"].value;
    let prenom = document.forms["modification"].elements["user_prenom"].value;
    let mail = document.forms["modification"].elements["user_email"].value;
    let phone = document.forms["modification"].elements["user_phone"].value;
    let naissance = document.forms["modification"].elements["user_date"].value;
    let question = document.forms["modification"].elements["user_question"].value;
    let reponse = document.forms["modification"].elements["user_response"].value;

    let date = naissance.split("-");
    if((date[0] > 2020) || (date[0] < 1900)){                   //verification de la date pour eviter tout problème avec
        document.getElementById("divReponse").style.zIndex = '1';
        document.getElementById("divReponse").style.display = 'initial';
        alerter("Date de naissance invalide");
    } else if(mail !== "" && !checkMail(mail)) {                               //verification que l'adresse est bien une adresse mail
        document.getElementById("divReponse").style.zIndex = '1';
        document.getElementById("divReponse").style.display = 'initial';
        alerter("Mail non valide");
    } else {
        let request;                         //requete http permettant d'envoyer au fichier serveur de modifier la page
        request = new XMLHttpRequest();
        request.onreadystatechange = function () {                    //applique la fonction défini après lorsque le changement s'opère
            if (this.readyState === 4 && this.status === 200) {      // 4 = reponse prete / 200 = OK
                document.getElementById("divReponse").innerHTML = this.responseText;   //rempli le corps de la page avec la réponse
                document.getElementById("divReponse").style.zIndex = '1';
                document.getElementById("divReponse").style.display = 'initial';
            }
        };
        request.open("POST", "controller/editionProfil.php", true);
        request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        request.send("user_prenom=" + prenom + "&user_nom=" + nom
            + "&user_email=" + mail + "&user_phone=" + phone
            + "&user_date=" + naissance + "&user_question=" + question + "&user_response=" + reponse);                      //envoie le resultat de la requete au serveur
    }
}