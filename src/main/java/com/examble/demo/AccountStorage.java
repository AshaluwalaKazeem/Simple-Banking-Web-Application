package com.examble.demo;

import java.util.ArrayList;
import java.util.List;

import entity.Account;

public class AccountStorage {
	private List<Account> accountList;

	public List<Account> getAccountList() {
		if(accountList == null) {
			accountList = new ArrayList<>();
		}
		return accountList;
	}

	public void setAccountList(List<Account> accountList) {
		if(accountList != null) this.accountList = accountList;
	}

	
}
