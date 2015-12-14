
var xmlHttp;

function createXMLHttpRequest() {

    if (window.ActiveXObject) {

        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");

    }

    else if (window.XMLHttpRequest) {

        xmlHttp = new XMLHttpRequest();

    }

}

 

function startRequest() {
    
	var username = document.form1.name.value;
	
	createXMLHttpRequest();

    xmlHttp.onreadystatechange = handleStateChange;

    xmlHttp.open("post", "select.do?username="+username, true);

    xmlHttp.send(null);

}

 

function handleStateChange() {

    if(xmlHttp.readyState == 4) {

        if(xmlHttp.status == 200) {

            document.getElementById("results").innerHTML = xmlHttp.responseText;

        }

    }

}



