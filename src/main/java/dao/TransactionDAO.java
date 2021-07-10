package dao;

import java.util.List;

import com.examble.demo.TransactionStorage;

import entity.Transaction;

public class TransactionDAO {
	private static TransactionStorage transaction = new TransactionStorage();
	
	public List<Transaction> getTransactionList(){
		return transaction.getTransactionList();
	}
	
	public void saveNewTransaction(Transaction transaction) {
		this.transaction.getTransactionList().add(transaction);
	}
}
