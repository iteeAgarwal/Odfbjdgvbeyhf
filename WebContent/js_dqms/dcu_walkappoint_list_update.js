setInterval(getPatientListData, 60000);

function getPatientListData() {
	var xmlhttp;
	var hidden_doc_idID = document.getElementById("hidden_doc_idID").value;
	var hidden_list_typeID = document.getElementById("hidden_listType").value;
	var url = "DCUWalkAppointListAPI?d=" + hidden_doc_idID + "&t=2" + "&timestamp=" + new Date().getTime();
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			var alertValue = xmlhttp.responseText;
			jsonParserDataList(alertValue, hidden_list_typeID);
		}
	};

	xmlhttp.open("get", url, true);
	xmlhttp.send();
}

function jsonParserDataList(alertValue, hidden_list_typeID) {
	
	try{
		var jsonData = JSON.parse(alertValue);
	
		/********************Appoint List************************/
		try{
			var total_row_table_appoint = document.getElementById('table_app').getElementsByTagName('TBODY')[0].rows.length;
			var total_row_json_appoint = jsonData.appointlist.length;
			if(!(total_row_json_appoint == 0) ||(total_row_table_appoint == total_row_json_appoint && total_row_table_appoint>1)){
			
				current_tokenno = checkTokenSelectedAppointmentDCU();
				
			var new_tbody = document.createElement('tbody');
			var old_tbody = document.getElementById('table_app').getElementsByTagName('TBODY')[0];
			var tbodaydata = "";
			for (var i = 0; i < jsonData.appointlist.length; i++) {
				var appoint = jsonData.appointlist[i];
			    var token_no = appoint.token_no;
			    var app_walk_value = appoint.app_walk_value;
			    var patient_name = appoint.patient_name;
			    var status = appoint.status;
			    
			    
			    var trdata = "<tr style='background: #EFEFEF; height: 25px; font-size: 14px; text-align: center'>";
			    if(current_tokenno == token_no){
			    	trdata= trdata + "<td><input type='radio' name='appRadio' id='app_walk_id' value='" + token_no +"' checked='checked'></td>";
			    }else if(current_tokenno == -1 && i==0){
			    	trdata= trdata + "<td><input type='radio' name='appRadio' id='app_walk_id' value='" + token_no +"' checked='checked'></td>";
			    }else{
			    	trdata= trdata + "<td><input type='radio' name='appRadio' id='app_walk_id' value='" + token_no +"'></td>";
			    }
			    var tokenStatusName = "";
				if (status == 1) {
					tokenStatusName = "Called";
				} else if (status == 2) {
					tokenStatusName = "Skipped";
				} else if (status == 3) {
					tokenStatusName = "Treated";
				} else if (status == 4) {
					tokenStatusName = "Cancelled";
				}
			    trdata= trdata + "<td>" + app_walk_value + "</td><td>" + patient_name + "</td><td>" + tokenStatusName + "</td>";
			    tbodaydata = tbodaydata + trdata;
			}
			new_tbody.innerHTML = tbodaydata;
			old_tbody.parentNode.replaceChild(new_tbody, old_tbody);
			}
		}catch(e){
			console.log("exception in appoint json " + e );
		}
		/********************Appoint list end********************/
		
		/*******************Walk List ****************************/
		try{
			var total_row_table_walk = document.getElementById('table_walk').getElementsByTagName('tbody')[0].rows.length;
			var total_row_json_walk = jsonData.walklist.length;
			if(!(total_row_json_walk == 0) || (total_row_table_walk == total_row_json_walk && total_row_table_walk>1)){
				
			var new_tbody_walk = document.createElement('tbody');
			var old_tbody_walk = document.getElementById('table_walk').getElementsByTagName('tbody')[0];
			var tbodaydata_walk = "";
			for (var i = 0; i < jsonData.walklist.length; i++) {
				var walk = jsonData.walklist[i];
			    var token_no_walk = walk.token_no;
			    var patient_name_walk = walk.patient_name;
			    var status_walk = walk.status;
			    
			    var trdata_walk = "<tr style='background: #EFEFEF; height: 25px; font-size: 14px; text-align: center'>";
			    trdata_walk= trdata_walk + "<td>" + token_no_walk +"</td>";
			    
			    var tokenStatusName_walk = "";
				if (status_walk == 1) {
					tokenStatusName_walk = "Called";
				} else if (status_walk == 2) {
					tokenStatusName_walk = "Skipped";
				} else if (status_walk == 3) {
					tokenStatusName_walk = "Treated";
				} else if (status_walk == 4) {
					tokenStatusName_walk = "Cancelled";
				}
			    trdata_walk= trdata_walk + "<td>" + patient_name_walk + "</td><td>" + tokenStatusName_walk + "</td>";
			    tbodaydata_walk = tbodaydata_walk + trdata_walk;
			}
			new_tbody_walk.innerHTML = tbodaydata_walk;
			old_tbody_walk.parentNode.replaceChild(new_tbody_walk, old_tbody_walk);
			}
		}catch(e){
			console.log("exception in walk json " + e );
		}
		
		
		/*******************Walk List Close *********************/
	}catch(e){
		console.log("exception in pateint list " + e );
	}
}

function checkTokenSelectedAppointmentDCU() {
	
	var token_appoint=-1;
	try{
		if (typeof document.dcu_form.app_walk_id == "undefined") {
			return -1;
		}
		
		var tokens = document.dcu_form.app_walk_id;
		 var i = tokens.length;
		 var flag =false;
		 while (i--) {
		    if(tokens[i].checked){
		    	token_appoint=tokens[i].value;
		    	flag =true;
		    }
		 }
		 if(flag==false){
			 token_appoint = -1;
		 }
		 
	}catch(e){
		console.log("exception in checkTokenSelectedAppointmentDCU: " + e);
		return -1;
	}
	return token_appoint;
}
