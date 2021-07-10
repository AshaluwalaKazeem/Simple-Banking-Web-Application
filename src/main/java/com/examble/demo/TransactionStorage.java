package com.examble.demo;

import java.util.ArrayList;
import java.util.List;

import entity.Transaction;

public class TransactionStorage {
	private List<Transaction> transactionList;

	public List<Transaction> getTransactionList() {
		if(transactionList == null) {
			transactionList = new ArrayList<>();
		}
		return transactionList;
	}

	public void setTransactionList(List<Transaction> transactionList) {
		if(transactionList != null) this.transactionList = transactionList;
	}
	
}
