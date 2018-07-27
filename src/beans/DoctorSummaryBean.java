package beans;

public class DoctorSummaryBean {
int doc_id;
int num_treat_patients;
String doc_name,depart_name;
String strAvgWait,strAvgTreat;
String strTotal_working_hours;

public String getStrAvgWait() {
	return strAvgWait;
}
public void setStrAvgWait(String strAvgWait) {
	this.strAvgWait = strAvgWait;
}
public String getStrAvgTreat() {
	return strAvgTreat;
}
public void setStrAvgTreat(String strAvgTreat) {
	this.strAvgTreat = strAvgTreat;
}
public String getStrTotal_working_hours() {
	return strTotal_working_hours;
}
public void setStrTotal_working_hours(String strTotal_working_hours) {
	this.strTotal_working_hours = strTotal_working_hours;
}
public DoctorSummaryBean(){
	
}
public int getDoc_id() {
	return doc_id;
}
public void setDoc_id(int doc_id) {
	this.doc_id = doc_id;
}

public int getNum_treat_patients() {
	return num_treat_patients;
}
public void setNum_treat_patients(int num_treat_patients) {
	this.num_treat_patients = num_treat_patients;
}
public String getDoc_name() {
	return doc_name;
}
public void setDoc_name(String doc_name) {
	this.doc_name = doc_name;
}
public String getDepart_name() {
	return depart_name;
}
public void setDepart_name(String depart_name) {
	this.depart_name = depart_name;
}
}
