package utils;

public class ResponseBody {
	private int responseCode;
	private boolean success;
	private String message;
	public ResponseBody(int responseCode, boolean success, String message) {
		super();
		this.responseCode = responseCode;
		this.success = success;
		this.message = message;
	}
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
