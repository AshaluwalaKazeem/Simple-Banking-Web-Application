package utils;

public class LoginData {
	private String accountNumber;
	private String accountPassword;
	
	public LoginData(String accountNumber, String accountPassword) {
		super();
		this.accountNumber = accountNumber;
		this.accountPassword = accountPassword;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountPassword() {
		return accountPassword;
	}

	public void setAccountPassword(String accountPassword) {
		this.accountPassword = accountPassword;
	}
	
	
}
