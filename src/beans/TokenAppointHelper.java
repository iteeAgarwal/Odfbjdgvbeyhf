package beans;

public class TokenAppointHelper {

	
	private int app_token_id;
	private String app_token_name;
	
	
	public TokenAppointHelper(int app_token_id, String app_token_name) {
		super();
		this.app_token_id = app_token_id;
		this.app_token_name = app_token_name;
	}
	public int getApp_token_id() {
		return app_token_id;
	}
	public void setApp_token_id(int app_token_id) {
		this.app_token_id = app_token_id;
	}
	public String getApp_token_name() {
		return app_token_name;
	}
	public void setApp_token_name(String app_token_name) {
		this.app_token_name = app_token_name;
	}
	
	
}
