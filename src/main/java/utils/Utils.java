package utils;

import java.util.UUID;

public class Utils {
	public String generateAccountNumber() {
		long accountNumber = (long) Math.floor(Math.random()*(9999999999L-1000000000L+1)+1000000000L);
		return String.valueOf(accountNumber);
	}
	
	public String generateRandomToken() {
		return UUID.randomUUID().toString();
	}
}
