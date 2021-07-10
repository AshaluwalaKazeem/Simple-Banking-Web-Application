package entity;

import java.util.Date;

public class Transaction {
	private String accountNumber;
	private Date transactionDate;
	private String transactionType;
	private String narration;
	private Double amount;
	private Double accountBalance;
	
	public Transaction(String accountNumber, Date transactionDate, String transactionType, String narration, Double amount,
			Double accountBalance) {
		this.transactionDate = transactionDate;
		this.transactionType = transactionType;
		this.narration = narration;
		this.amount = amount;
		this.accountBalance = accountBalance;
		this.accountNumber = accountNumber;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getNarration() {
		return narration;
	}

	public void setNarration(String narration) {
		this.narration = narration;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(Double accountBalance) {
		this.accountBalance = accountBalance;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
}
