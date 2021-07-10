package entity;

public class User {
	private int id;
	private String accountNumber;
	private String accountPassword;
	
	public User(int id, String accountNumber, String accountPassword) {
		this.id = id;
		this.accountNumber = accountNumber;
		this.accountPassword = accountPassword;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountName) {
		this.accountNumber = accountName;
	}

	public String getAccountPassword() {
		return accountPassword;
	}

	public void setAccountPassword(String accountPassword) {
		this.accountPassword = accountPassword;
	}
}
