package entity;

public class Account {
	private String accountName;
	private String accountNumber;
	private Double balance;
	
	public Account(String accountName, String accountNumber, Double balance) {
		this.accountName = accountName;
		this.accountNumber = accountNumber;
		this.balance = balance;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
	
	
	
}
