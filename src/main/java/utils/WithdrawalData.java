package utils;

public class WithdrawalData {
	private String accountNumber;
	private String accountPassword;
	private Double withdrawnAmount;
	
	public WithdrawalData(String accountNumber, String accountPassword, Double withdrawnAmount) {
		super();
		this.accountNumber = accountNumber;
		this.accountPassword = accountPassword;
		this.withdrawnAmount = withdrawnAmount;
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

	public Double getWithdrawnAmount() {
		return withdrawnAmount;
	}

	public void setWithdrawnAmount(Double withdrawnAmount) {
		this.withdrawnAmount = withdrawnAmount;
	}
}
