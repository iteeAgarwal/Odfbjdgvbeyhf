setInterval(getServiceData, 10000);

function getServiceData() {
	var xmlhttp;
	var hidden_doc_idID = document.getElementById("hidden_doc_idID").value;
	var hidden_list_typeID = document.getElementById("hidden_listType").value;
	var url = "ServiceData?d=" + hidden_doc_idID + "&t=" + hidden_list_typeID + "&timestamp=" + new Date().getTime();
	console.log("url: " + url );
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			console.log(xmlhttp.responseText);
			var alertValue = xmlhttp.responseText;

			jsonParserData(alertValue, hidden_list_typeID);

		}
	};

	xmlhttp.open("get", url, true);
	xmlhttp.send();
}

function jsonParserData(alertValue, hidden_list_typeID) {
	console.log("json: " + alertValue);
	var jsonData = JSON.parse(alertValue);
	var total_token = jsonData.total_token;
	var total_token_app = jsonData.total_token_app;
	var current_token_app = jsonData.current_token_app;
	var total_token_walk = jsonData.total_token_walk;
	var current_token_walk = jsonData.current_token_walk;

	if (total_token == undefined) {
		total_token = "0";
	}
	if (total_token_app == undefined) {
		total_token_app = "0";
	}
	if (current_token_app == undefined) {
		current_token_app = "0";
	}else{
		current_token_app="C"+current_token_app;
	}
	if (total_token_walk == undefined) {
		total_token_walk = "0";
	}
	if (current_token_walk == undefined) {
		current_token_walk = "0";
	}
	
	console.log("total_token: " + total_token);
	console.log("total_token_app: " + total_token_app);
	console.log("current_token_app: " + current_token_app);
	console.log("total_token_walk: " + total_token_walk);
	console.log("current_token_walk: " + current_token_walk);
	
	if (hidden_list_typeID == "false") {

		document.getElementById('total_token').innerHTML = total_token;
		document.getElementById('total_token_app').innerHTML = total_token_app;
		document.getElementById('current_token_app').innerHTML = current_token_app;
		document.getElementById('total_token_walk').innerHTML = total_token_walk;
		document.getElementById('current_token_walk').innerHTML = current_token_walk;

	} else {

		document.getElementById('skip_total_token').innerHTML = total_token;
		document.getElementById('skip_total_token_app').innerHTML = total_token_app;
		document.getElementById('skip_current_token_app').innerHTML = current_token_app;
		document.getElementById('skip_total_token_walk').innerHTML = total_token_walk;
		document.getElementById('skip_current_token_walk').innerHTML = current_token_walk;

	}

}
