function checkTokenSelectedAppointment() {
	
	var token_appoint=0;
	var tokens = document.dcu_form.app_walk_id;
	if (typeof tokens.length == "undefined") {
		token_appoint=tokens.value;
	}else{
	//var tokens = document.dcu_form.app_walk_id;
	 for (var i=0; i<tokens.length;i++) {
	    if(tokens[i].checked){
	    	token_appoint=tokens[i].value;
	    	break;
	    }
	 }
	}
	 var txtTokenNo = document.getElementById("hidden_tokenNo");
	 txtTokenNo.value = token_appoint;
}

function updateRowNoAppointmentPatient(tokenNo){
	var tokens = document.dcu_form.app_walk_id;
	 var i = tokens.length;
	 while (i--) {
	    if(tokens[i].value == tokenNo){
	    	tokens[i].checked=true;
	    }
	 }
}

function noSkipBeforeCall(){
	Alert.render("Please call a patient first. Only then you can skip the patient.");
}

function ChangeListType(){
	var listType = document.getElementsByName("btnlisttype");
    if (listType.value=="Fresh List") {
    	var hidden_listType = document.getElementsById("hidden_listType");
    	hidden_listType.value = "true";
    }else{
    	var hidden_listType = document.getElementsById("hidden_listType");
    	hidden_listType.value = "false";
    }
}

function disableAppWalkDropdown() {
    document.getElementById("appWalkDropDown").disabled=true;
}
function enableAppWalkDropdown() {
    document.getElementById("appWalkDropDown").disabled=false;
}
function ShowErrorAlertDCU(errorMsg){
	console.log("error msg: " + errorMsg);
	Alert.render(errorMsg);
}

/*function selectRadioRow() {
    var rows = document.getElementById('table_app').getElementsByTagName('tbody')[0].getElementsByTagName('tr');
    for (i = 0; i < rows.length; i++) {
        rows[i].onclick = function() {
		document.getElementById(this.rowIndex + 1).checked = true;
            alert(this.rowIndex + 1);
        }
    }
}

var allRadios = document.getElementsByName('appRadio');
        var booRadio;
        var x = 0;
        for(x = 0; x < allRadios.length; x++){

            allRadios[x].onclick = function() {
                if(booRadio == this){
                    this.checked = false;
                    booRadio = null;
                }else{
                    booRadio = this;
                }
            };
        }
*/

