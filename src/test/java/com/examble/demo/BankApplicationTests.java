package com.examble.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import entity.Token;
import entity.Transaction;
import utils.CreateAccountData;
import utils.DepositData;
import utils.LoginData;
import utils.ResponseBody;
import utils.WithdrawalData;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class BankApplicationTests {

	@Autowired
	AccountController accountController;
	
	@Test
	@Order(1)
	public void createAccount() {
		CreateAccountData createAccountData = new CreateAccountData("Kazeem", "password", 1000.00);

		accountController.createAccount(createAccountData);
		assertTrue(accountController.accountDao().getAccountList().get(0).getAccountName() == createAccountData.getAccountName());
		assertEquals(accountController.accountDao().getAccountList().get(0).getAccountNumber().length(), 10);
		assertTrue(accountController.accountDao().getAccountList().get(0).getBalance() >= 500.00);
		assertTrue(accountController.userDao().getUserList().size() > 0);
	}
	
	@Test
	@Order(2)
	public void login() {
		LoginData loginData = new LoginData(accountController.userDao().getUserList().get(0).getAccountNumber(), "password");
		
		assertTrue(accountController.userDao().getUserList().get(0).getAccountNumber() == loginData.getAccountNumber() &&  
				accountController.userDao().getUserList().get(0).getAccountPassword() == loginData.getAccountPassword());
		
		assertNotNull(accountController.login(loginData));
	}
	
	@Test
	@Order(3)
	public void accountInfo() {
		String accountNumber = accountController.userDao().getUserList().get(0).getAccountNumber(); // Valid account number
		String token = accountController.tokenDao().getTokenList().get(0).getToken(); // Valid token
		
		assertTrue(accountController.accountInfo(accountNumber, token).get("responseCode").equals(200));
		
		String invalidAccountNumber = "8928392839"; // invalid account number
		String invalidToken = "892asdf#$#@$SDsdfsdf32$SDFSd434dsfs"; // invalid token
		
		assertFalse(accountController.accountInfo(invalidAccountNumber, invalidToken).get("responseCode").equals(200));
	}
	
	@Test
	@Order(4)
	public void deposit() {
		DepositData depositData = new DepositData(accountController.userDao().getUserList().get(0).getAccountNumber(), 500.00);// valid acccountNumber and amount
		int responseCode = ((ResponseBody) accountController.deposit(depositData)).getResponseCode();
		assertTrue(responseCode == 200);
		
		depositData = new DepositData(accountController.userDao().getUserList().get(0).getAccountNumber(), 1000001.00);// valid acccountNumber and a invalid amount
		responseCode = ((ResponseBody) accountController.deposit(depositData)).getResponseCode();
		assertFalse(responseCode == 200);
		
		depositData = new DepositData(accountController.userDao().getUserList().get(0).getAccountNumber(), 0.90);// valid acccountNumber and a invalid amount
		responseCode = ((ResponseBody) accountController.deposit(depositData)).getResponseCode();
		assertFalse(responseCode == 200);
		
		depositData = new DepositData("0283928372", 500.00);// invalid acccountNumber and a valid amount
		responseCode = ((ResponseBody) accountController.deposit(depositData)).getResponseCode();
		assertFalse(responseCode == 200);
	}
	
	@Test
	@Order(5)
	public void withdraw() {
		WithdrawalData withdrawalData = new 
				WithdrawalData(accountController.userDao().getUserList().get(0).getAccountNumber(), 
						accountController.userDao().getUserList().get(0).getAccountPassword(), 20.00);
		
		int responseCode = ((ResponseBody) 
				accountController.withdrawal(withdrawalData, accountController.tokenDao().getTokenList()
						.get(0).getToken())).getResponseCode();// Valid account details and token with amount
		assertTrue(responseCode == 200);
		
		//Test for valid account number
		withdrawalData = new 
				WithdrawalData("3928284739", "323", 20.00);
		responseCode = ((ResponseBody) 
				accountController.withdrawal(withdrawalData, accountController.tokenDao().getTokenList()
						.get(0).getToken())).getResponseCode();
		assertFalse(responseCode == 200);
		
		//Test for valid token
		withdrawalData = new 
				WithdrawalData(accountController.userDao().getUserList().get(0).getAccountNumber(), 
						accountController.userDao().getUserList().get(0).getAccountPassword(), 20.00);
		responseCode = ((ResponseBody) 
				accountController.withdrawal(withdrawalData, "892asdf#$#@$SDsdfsdf32$SDFSd434dsfs")).getResponseCode();
		assertFalse(responseCode == 200);
		
		//Test for valid amount
		withdrawalData = new 
				WithdrawalData(accountController.userDao().getUserList().get(0).getAccountNumber(), 
						accountController.userDao().getUserList().get(0).getAccountPassword(), 1000009.00);
		responseCode = ((ResponseBody) 
				accountController.withdrawal(withdrawalData, accountController.tokenDao().getTokenList()
						.get(0).getToken())).getResponseCode();
		assertFalse(responseCode == 200);
	}
	
	@Test
	@Order(6)
	public void accountStatment() {
		// Test for invalid account number
		int responseCode = ((ResponseBody) 
				accountController.accountStatment("3829372938", accountController.tokenDao().getTokenList()
						.get(0).getToken())).getResponseCode();
		assertFalse(responseCode == 200);
		// Test for a valid token
		responseCode = ((ResponseBody) 
				accountController.accountStatment(accountController.userDao().getUserList().get(0).getAccountNumber(), "2309238sdkfsdfn#@$#$@#$@#FfsdF@#r2323")).getResponseCode();
		assertFalse(responseCode == 200);

		// Test for transaction length
		@SuppressWarnings("unchecked")
		int length = ((List<Transaction>) 
				accountController.accountStatment(accountController.userDao().getUserList().get(0).getAccountNumber(), accountController.tokenDao().getTokenList()
						.get(0).getToken())).size();
		assertTrue(length > 0);
	}
}
















