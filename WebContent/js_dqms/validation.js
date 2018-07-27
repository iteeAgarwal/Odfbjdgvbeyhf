function CustomAlert() {
	this.render = function(dialog) {
		var winW = window.innerWidth;
		var winH = window.innerHeight;
		var dialogoverlay = document.getElementById('dialogoverlay');
		var dialogbox = document.getElementById('dialogbox');
		dialogoverlay.style.display = "block";
		dialogoverlay.style.height = winH + "px";
		dialogbox.style.left = (winW / 2) - (500 * .5) + "px";
		dialogbox.style.top = "200px";
		dialogbox.style.display = "block";

		document.getElementById('dialogboxbody').innerHTML = dialog;
		document.getElementById('dialogboxfoot').innerHTML = '<button style="width:100Px" onclick="Alert.ok()">OK</button>';
	}
	this.ok = function() {
		document.getElementById('dialogbox').style.display = "none";
		document.getElementById('dialogoverlay').style.display = "none";
	}
}
var Alert = new CustomAlert();





function beforcall() {
	Alert.render("Please Call Patient First");
}
function noPatients() {
	Alert.render("No More Patients");
}
function skipListEmpty() {
	Alert.render("No Patients in skip list");
}

function getSummary() {
	window.location.assign("./Doctor_summary");
}

function getLogout() {
	window.location.assign("./LogoutAction");
}
function cantListChnage() {
	Alert.render("Please Treat Patient First");
}