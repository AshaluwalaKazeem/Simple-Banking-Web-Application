package utils;

public class LoginRespondBody {
	private boolean success;
	private String accessToken;
	
	public LoginRespondBody(boolean success, String accessToken) {
		super();
		this.success = success;
		this.accessToken = accessToken;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
}
