package utils;

public class DepositData {
	private String accountNumber;
	private Double amount;
	
	public DepositData(String accountNumber, Double amount) {
		super();
		this.accountNumber = accountNumber;
		this.amount = amount;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
}
