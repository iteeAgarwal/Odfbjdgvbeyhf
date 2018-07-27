function generateNew() {

	window.location.assign("./TokenStatusList");

}
/*
 * 
 * get rooms according department ID
 * 
 */
function GetOptions() {

	var e = document.getElementById("deptID_id");
	var strOptions = e.options[e.selectedIndex].value;
	clearSelectBox();
	insertAlert(strOptions);

	// addOption_list();

}

function insertAlert(deptID) {
	var xmlhttp;

	var url = "GetRoomsWithDept?deptID=" + deptID + "&timestamp=" + new Date().getTime();
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			var alertValue = xmlhttp.responseText;

			addOption_list(alertValue);

		}
	}

	xmlhttp.open("get", url, true);
	xmlhttp.send();
}

function addOption_list(alertValue) {
	var jsonData = JSON.parse(alertValue);
	addOption(document.drop_list.room_group_list, "Select Room Group", "");

	for (var i = 0; i < jsonData.alert.length; i++) {

		var counter = jsonData.alert[i];
		addOption(document.drop_list.room_group_list, counter.token_group_name,
				counter.token_group_id);

	}

}

function addOption(selectbox, text, value) {
	var optn = document.createElement("OPTION");
	optn.text = text;
	optn.value = value;

	selectbox.options.add(optn);

}

function clearSelectBox() {

	document.getElementById("room_group_id").options.length = 0;
}

// ==========================get rooms with group===========================//

function GetRoomOptions() {

	var e = document.getElementById("room_group_id");
	var strOptions = e.options[e.selectedIndex].value;

	clearSelectBox_room();
	getRoomsAjax(strOptions);

	// addOption_list();

}

function getRoomsAjax(groupID) {
	var xmlhttp;

	var url = "GetRoomWithGroup?groupID=" + groupID  + "&timestamp=" + new Date().getTime();
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			var alertValue = xmlhttp.responseText;

			addOption_list_room(alertValue);

		}
	};

	xmlhttp.open("get", url, true);
	xmlhttp.send();
}

function addOption_list_room(alertValue) {

	var jsonData = JSON.parse(alertValue);

	addOption_room(document.drop_list.rooms_list, "Select Room Number", 0);

	for (var i = 0; i < jsonData.alert.length; i++) {

		var counter = jsonData.alert[i];
		addOption_room(document.drop_list.rooms_list, counter.room_no,
				counter.room_id);

	}

}

function addOption_room(selectbox, text, value) {
	var optn = document.createElement("OPTION");
	optn.text = text;
	optn.value = value;

	selectbox.options.add(optn);

}

function clearSelectBox_room() {

	document.getElementById("room_id").options.length = 0;
}

// ===================get doctor with room id

function GetDoctorOptions() {

	var e = document.getElementById("room_id");
	var strOptions = e.options[e.selectedIndex].value;

	clearSelectBox_doctor();
	getDoctorsAjax(strOptions);

	//document.getElementById("doc_id").required = true;

	// addOption_list();

}
function getDoctorsAjax(roomID) {
	var xmlhttp;

	var url = "GetDoctorWithRoom?roomID=" + roomID + "&timestamp=" + new Date().getTime();
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			var alertValue = xmlhttp.responseText;

			addOption_list_doctor(alertValue);

		}
	};

	xmlhttp.open("get", url, true);
	xmlhttp.send();
}

function addOption_list_doctor(alertValue) {

	var jsonData = JSON.parse(alertValue);
	addOption_doctor(document.drop_list.doctor_list, "Select Doctor Name", "");
	
	for (var i = 0; i < jsonData.alert.length; i++) {

		var counter = jsonData.alert[i];

		addOption_doctor(document.drop_list.doctor_list, counter.name,
				counter.user_id);

	}

}

function addOption_doctor(selectbox, text, value) {
	var optn = document.createElement("OPTION");
	optn.text = text;
	optn.value = value;

	selectbox.options.add(optn);

}

function clearSelectBox_doctor() {

	document.getElementById("doc_id").options.length = 0;
}

// ===================get rooms with department ID

function GetOptionsStatic() {

	var e = document.getElementById("deptID_id");
	var strOptions = e.options[e.selectedIndex].value;
	clearSelectBoxStatic();
	insertAlertStatic(strOptions);

	// addOption_list();

}

function insertAlertStatic(deptID) {
	var xmlhttp;

	var url = "GetRoomWithDept?deptID=" + deptID + "&timestamp=" + new Date().getTime();
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			var alertValue = xmlhttp.responseText;

			addOption_listStatic(alertValue);

		}
	}

	xmlhttp.open("get", url, true);
	xmlhttp.send();
}

function addOption_listStatic(alertValue) {

	var jsonData = JSON.parse(alertValue);
	addOptionstaic(document.drop_list.rooms_list, "Select Room No", "");

	for (var i = 0; i < jsonData.alert.length; i++) {

		var counter = jsonData.alert[i];
		addOptionstaic(document.drop_list.rooms_list, counter.room_no,
				counter.room_id);

	}

}

function addOptionstaic(selectbox, text, value) {
	var optn = document.createElement("OPTION");
	optn.text = text;
	optn.value = value;

	selectbox.options.add(optn);

}

function clearSelectBoxStatic() {

	document.getElementById("room_id").options.length = 0;
}

/** ********* for other form validation between department and room no********** */

function GetOptionsStatic1() {

	var e = document.getElementById("deptID_id");
	var strOptions = e.options[e.selectedIndex].value;
	 clearSelectBoxStatic1();
	insertAlertStatic1(strOptions);

	// addOption_list();

}

function insertAlertStatic1(deptID) {
	var xmlhttp;

	var url = "GetRoomWithDept?deptID=" + deptID + "&timestamp=" + new Date().getTime();
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			var alertValue = xmlhttp.responseText;

			addOption_listStatic1(alertValue);

		}
	}

	xmlhttp.open("get", url, true);
	xmlhttp.send();
}

function addOption_listStatic1(alertValue) {

	var jsonData = JSON.parse(alertValue);

	//var optionArray = [];

	addOptionstaic1(document.drop_list.rooms_list, "Select Room No", "");

	for (var i = 0; i < jsonData.alert.length; i++) {

		var counter = jsonData.alert[i];

		//optionArray.push(counter.room_no);
		addOptionstaic1(document.drop_list.rooms_list, counter.room_no,
				counter.room_id);

	}
	//populate(optionArray);

}

function addOptionstaic1(selectbox, text, value) {
	var optn = document.createElement("OPTION");
	optn.text = text;
	optn.value = value;

	selectbox.options.add(optn);

}

function clearSelectBoxStatic1() {

	document.getElementById("room_id").options.length = 0;
}



/****************************add rooms dynamic with token group****************/


function GetOptionsStatic2() {

	var e = document.getElementById("deptID_id");
	var strOptions = e.options[e.selectedIndex].value;
	// clearSelectBoxStatic1();
	insertAlertStatic2(strOptions);

	// addOption_list();

}

function insertAlertStatic2(deptID) {
	var xmlhttp;

	var url = "GetRoomWithDept?deptID=" + deptID  + "&timestamp=" + new Date().getTime();
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			var alertValue = xmlhttp.responseText;

			addOption_listStatic2(alertValue);

		}
	}

	xmlhttp.open("get", url, true);
	xmlhttp.send();
}

function addOption_listStatic2(alertValue) {

	var jsonData = JSON.parse(alertValue);

	var optionArray = [];
	
	var s2 = document.getElementById("slct2");
	s2.innerHTML = "";

	//addOptionstaic1(document.drop_list.rooms_list, "Select Room No", "");

	for (var i = 0; i < jsonData.alert.length; i++) {

		var counter = jsonData.alert[i];

		//optionArray.push(counter.room_no);
		
		
		var checkbox = document.createElement("input");
		checkbox.type = "checkbox";
		checkbox.name = "roomsCheckbox";
		checkbox.value = counter.room_id;
		s2.appendChild(checkbox);

		var label = document.createElement('label')
		label.htmlFor = counter.room_no;
		label.appendChild(document.createTextNode(counter.room_no));

		s2.appendChild(label);
		
		//addOptionstaic1(document.drop_list.rooms_list, counter.room_no,
				//counter.room_id);

	}
	//populate(optionArray);

}


/*=-=-=-=-=-=-=-=-=-=-=-=-=22wala=-=-=-=-=-=-=-=-=-=-=-=*/
function GetOptionsStatic22() {

	var e = document.getElementById("deptID_id");
	var strOptions = e.options[e.selectedIndex].value;
	// clearSelectBoxStatic1();
	insertAlertStatic22(strOptions);

	// addOption_list();

}

function insertAlertStatic22(deptID) {
	var xmlhttp;

	var url = "GetUniqueRoomWithDept?deptID=" + deptID + "&timestamp=" + new Date().getTime();
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			var alertValue = xmlhttp.responseText;

			addOption_listStatic22(alertValue);

		}
	}

	xmlhttp.open("get", url, true);
	xmlhttp.send();
}

function addOption_listStatic22(alertValue) {

	var jsonData = JSON.parse(alertValue);

	var optionArray = [];
	
	var s2 = document.getElementById("slct2");
	s2.innerHTML = "";

	//addOptionstaic1(document.drop_list.rooms_list, "Select Room No", "");

	for (var i = 0; i < jsonData.alert.length; i++) {

		var counter = jsonData.alert[i];

		//optionArray.push(counter.room_no);
		
		
		var checkbox = document.createElement("input");
		checkbox.type = "checkbox";
		checkbox.name = "roomsCheckbox";
		checkbox.value = counter.room_id;
		s2.appendChild(checkbox);

		var label = document.createElement('label')
		label.htmlFor = counter.room_no;
		label.appendChild(document.createTextNode(counter.room_no));

		s2.appendChild(label);
		
		//addOptionstaic1(document.drop_list.rooms_list, counter.room_no,
				//counter.room_id);

	}
	//populate(optionArray);

}


function GetOptionsStatic3() {

	var e = document.getElementById("dropdown_conn");
	var strOptions = e.options[e.selectedIndex].value;
	// clearSelectBoxStatic1();
	insertAlertStatic3(strOptions);

	// addOption_list();

}

function insertAlertStatic3(deptID) {
	var xmlhttp;

	var url = "GetRoomWithDept?deptID=" + deptID  + "&timestamp=" + new Date().getTime();
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			var alertValue = xmlhttp.responseText;

			addOption_listStatic3(alertValue);

		}
	}

	xmlhttp.open("get", url, true);
	xmlhttp.send();
}

function addOption_listStatic3(alertValue) {

	var jsonData = JSON.parse(alertValue);

	var optionArray = [];
	
	var s2 = document.getElementById("tb_roomList_edit");
	s2.innerHTML = "";

	//addOptionstaic1(document.drop_list.rooms_list, "Select Room No", "");

	for (var i = 0; i < jsonData.alert.length; i++) {

		var counter = jsonData.alert[i];

		//optionArray.push(counter.room_no);
			
		var checkbox = document.createElement("input");
		checkbox.type = "checkbox";
		checkbox.name = "roomsCheckbox";
		checkbox.value = counter.room_id;
		s2.appendChild(checkbox);

		var label = document.createElement('label')
		label.htmlFor = counter.room_no;
		label.appendChild(document.createTextNode(counter.room_no));

		s2.appendChild(label);
		
		//addOptionstaic1(document.drop_list.rooms_list, counter.room_no,
				//counter.room_id);

	}
	//populate(optionArray);

}


/*=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=33wala=-=-=-=-=-=-=-=-=-=-=-=-=*/
function GetOptionsStatic33() {

	var e = document.getElementById("dropdown_conn");
	var strOptions = e.options[e.selectedIndex].value;
	// clearSelectBoxStatic1();
	insertAlertStatic33(strOptions);

	// addOption_list();

}

function insertAlertStatic33(deptID) {
	var xmlhttp;

	var url = "GetRoomWithDept?deptID=" + deptID  + "&timestamp=" + new Date().getTime();
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			var alertValue = xmlhttp.responseText;

			addOption_listStatic33(alertValue);

		}
	}

	xmlhttp.open("get", url, true);
	xmlhttp.send();
}

function addOption_listStatic33(alertValue) {

	var jsonData = JSON.parse(alertValue);

	var optionArray = [];
	
	var s2 = document.getElementById("tb_roomList_edit");
	s2.innerHTML = "";

	//addOptionstaic1(document.drop_list.rooms_list, "Select Room No", "");

	for (var i = 0; i < jsonData.alert.length; i++) {

		var counter = jsonData.alert[i];

		//optionArray.push(counter.room_no);
		
		
		var checkbox = document.createElement("input");
		checkbox.type = "checkbox";
		checkbox.name = "roomsCheckbox";
		checkbox.value = counter.room_id;
		s2.appendChild(checkbox);

		
		
		
		var label = document.createElement('label')
		label.htmlFor = counter.room_no;
		label.appendChild(document.createTextNode(counter.room_no));

		s2.appendChild(label);
		
		//addOptionstaic1(document.drop_list.rooms_list, counter.room_no,
				//counter.room_id);

	}
	//populate(optionArray);

}

function populate(optionArray) {
	// var s1 = document.getElementById(slct1);
	var s2 = document.getElementById("slct2");
	s2.innerHTML = "";

	
	for ( var option in optionArray) {
		if (optionArray.hasOwnProperty(option)) {
			var pair = optionArray[option];
			var checkbox = document.createElement("input");
			checkbox.type = "checkbox";
			checkbox.name = "roomsCheckbox";
			checkbox.value = pair;
			s2.appendChild(checkbox);

			var label = document.createElement('label')
			label.htmlFor = pair;
			label.appendChild(document.createTextNode(pair));

			s2.appendChild(label);
			//s2.appendChild(document.createElement("br"));
		}
	}
}


/**************************Appointment Token List According to doctor ID***************/

function GetAppointmentListStatic() {

	var e = document.getElementById("doc_id");
	var strOptions = e.options[e.selectedIndex].value;
	clearAppointmentListStatic();
	apiCallAppointmentListStatic(strOptions);

	// addOption_list();

}

function apiCallAppointmentListStatic(docID) {
	var xmlhttp;

	var url = "GetTokenAppointWithDoc?doc_id=" + docID + "&timestamp=" + new Date().getTime();
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			var alertValue = xmlhttp.responseText;

			addOptionsToAppointListStatic(alertValue);
		}
	}
	xmlhttp.open("get", url, true);
	xmlhttp.send();
}

function addOptionsToAppointListStatic(alertValue) {

	var jsonData = JSON.parse(alertValue);
	addOptionAppointListstaic(document.drop_list.select_token, "Select Token", "");

	for (var i = 0; i < jsonData.alert.length; i++) {

		var counter = jsonData.alert[i];
		addOptionAppointListstaic(document.drop_list.select_token, counter.app_token_name,
				counter.app_token_id);

	}

}

function addOptionAppointListstaic(selectbox, text, value) {
	var optn = document.createElement("OPTION");
	optn.text = text;
	optn.value = value;

	selectbox.options.add(optn);

}

function clearAppointmentListStatic() {

	document.getElementById("appointlist_select").options.length = 0;
}

/***********************End of Appointment Token List******************************/