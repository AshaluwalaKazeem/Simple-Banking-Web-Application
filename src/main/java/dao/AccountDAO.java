package dao;

import java.util.List;

import com.examble.demo.AccountStorage;

import entity.Account;

public class AccountDAO {
	
	private static AccountStorage account = new AccountStorage();
	
	
	public List<Account> getAccountList() {
		return account.getAccountList();
	}
	
	public void addNewAccount(Account account) {
		this.account.getAccountList().add(account);
	}
	
	public void deposit(int index, double amount, Account account) {
		this.account.getAccountList().set(index, 
				new Account(account.getAccountName(), account.getAccountNumber(), account.getBalance() + amount));
	}
	
	public void withdraw(int index, double amount, Account account) {
		this.account.getAccountList().set(index,  
				new Account(account.getAccountName(), account.getAccountNumber(), account.getBalance() - amount));
	}
}
