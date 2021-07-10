package com.examble.demo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dao.AccountDAO;
import dao.TokenDAO;
import dao.TransactionDAO;
import dao.UserDAO;
import entity.Account;
import entity.Token;
import entity.Transaction;
import entity.User;
import utils.CreateAccountData;
import utils.DepositData;
import utils.LoginData;
import utils.LoginRespondBody;
import utils.ResponseBody;
import utils.Utils;
import utils.WithdrawalData;


@RestController
@RequestMapping(path = "/")
public class AccountController {

	@Bean
	AccountDAO accountDao() {
        return new AccountDAO();
    }
	@Bean
	TokenDAO tokenDao() {
		return new TokenDAO();
	}
	@Bean
	TransactionDAO transactionDao() {
		return new TransactionDAO();
	}
	@Bean
	UserDAO userDao() {
		return new UserDAO();
	}
	
	@PostMapping(
	        path = "create_account",
	        produces = "application/json",
	        consumes = "application/json")
	public ResponseBody createAccount(
	        @RequestBody CreateAccountData account)
    {
		if(account.getAccountName() == null || account.getAccountPassword() == null || account.getInitialDeposit() == null) {
			return new ResponseBody(400, false, "Bad request");
		}
		if(account.getInitialDeposit() < 500.00) {
			return new ResponseBody(400, false, "Initial deposit must be greater than or equal to #500:00");
		}
		
		
		for(Account rowData : accountDao().getAccountList()) {
			if(rowData.getAccountName().equals(account.getAccountName())) {
				return new ResponseBody(409, false, "Account name already exist");
			}
		}
		String accountNumber = new Utils().generateAccountNumber();
		
		accountDao().addNewAccount(new Account(account.getAccountName(), accountNumber, account.getInitialDeposit()));
		userDao().addNewUser(new User(userDao().getNextId(), accountNumber, account.getAccountPassword()));
		
		return new ResponseBody(201, true, "Account created successfully. Your account number is " + accountNumber);
    }
	
	@PostMapping(
	        path = "login",
	        produces = "application/json",
	        consumes = "application/json")
	public Object login(
	        @RequestBody LoginData login)
    {
		if(login.getAccountNumber() == null || login.getAccountPassword() == null) {
			return new ResponseBody(400, false, "Bad request");
		}
		boolean found = false;
		User userDetails = new User(-1, "", "");
		for(User user : userDao().getUserList()) {
			if(user.getAccountNumber().equals(login.getAccountNumber()) && user.getAccountPassword().equals(login.getAccountPassword())) {
				found = true;
				userDetails = user;
				break;
			}
		}
		if(!found) {
			return new ResponseBody(404, false, "Account not found");
		}
		
		String token = new Utils().generateRandomToken();

		int tokenIdFound = -1;
		int count = -1;
		for(Token tokenData : tokenDao().getTokenList()) {
			count++;
			if(tokenData.getUserId() == userDetails.getId()) {
				tokenIdFound = count;
				break;
			}
		}
		if(tokenIdFound != -1) {
			// Update existing token with new token
			tokenDao().updateToken(new Token(userDetails.getId(), token), tokenIdFound);
		}else {
			// Create a new token because token does not exist for the user
			tokenDao().addNewToken(new Token(userDetails.getId(), token));
		}

		return new LoginRespondBody(true, token);
    }
	
	@RequestMapping(value="account_info/{accountNumber}",
            method= RequestMethod.GET)
	public Map<String, Object> accountInfo(@PathVariable("accountNumber") String accountNumber, @RequestHeader("token") String _token)
    {
		boolean isAccountExist = false;
		Account accountDetails = new Account(null, null, null);
		for(Account accountData : accountDao().getAccountList()) {
			if(accountData.getAccountNumber().equals(accountNumber)) {
				isAccountExist = true;
				accountDetails = accountData;
				break;
			}
		}
		if(!isAccountExist) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("responseCode", 404);
			map.put("success", false);
			map.put("message", "Your account does not exist");
			map.put("account", null);
			return map;
		}
		
		boolean invalidToken = true;
		Token token = new Token(-1, "");
		for(Token tokenData : tokenDao().getTokenList()) {
			if(tokenData.getToken().equals(_token)) {
				invalidToken = false;
				token = tokenData;
				break;
			}
		}
		if(invalidToken) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("responseCode", 401);
			map.put("success", false);
			map.put("message", "You are unauthorized to perform this requst");
			map.put("account", null);
			return map;
		}
		
		if(!userDao().getUserList().get(token.getUserId()-1).getAccountNumber().equals(accountNumber)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("responseCode", 401);
			map.put("success", false);
			map.put("message", "You are unauthorized to perform this requst");
			map.put("account", null);
			return map;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("responseCode", 200);
		map.put("success", false);
		map.put("message", "Account details found");
		map.put("account", accountDetails);
		return map;
    }
	
	@PostMapping(
	        path = "deposit",
	        produces = "application/json",
	        consumes = "application/json")
	public Object deposit(
	        @RequestBody DepositData depositData)
    {
		if(depositData.getAccountNumber() == null || depositData.getAmount() == null) {
			return new ResponseBody(400, false, "Bad request");
		}
		
		if(depositData.getAmount() > 1000000.00) {
			return new ResponseBody(403, false, "The maximum amount you can transfer is #1,000,000:00");
		}
		
		if(depositData.getAmount() < 1.00) {
			return new ResponseBody(403, false, "The minimum amount you can transfer is #1:00");
		}
		
		boolean isAccountExist = false;
		Account accountDetails = new Account(null, null, null);
		int count = -1;
		for(Account accountData : accountDao().getAccountList()) {
			count++;
			if(accountData.getAccountNumber().equals(depositData.getAccountNumber())) {
				isAccountExist = true;
				accountDetails = accountData;
				break;
			}
		}
		if(!isAccountExist) {
			return new ResponseBody(404, false, "The account you are transferring money to does not exist");
		}
		
		accountDao().deposit(count, depositData.getAmount(), accountDetails);
		transactionDao().saveNewTransaction(
				new Transaction(depositData.getAccountNumber(), new Date(), "Deposit",
						"#" + depositData.getAmount() + " has been deposited into your account", 
						depositData.getAmount(), accountDao().getAccountList().get(count).getBalance()));
		return new ResponseBody(200, true, "transaction done successfully");
    }
	
	@PostMapping(
	        path = "withdrawal",
	        produces = "application/json",
	        consumes = "application/json")
	public Object withdrawal(
	        @RequestBody WithdrawalData withdrawalData, @RequestHeader("token") String _token)
    {
		if(withdrawalData.getAccountNumber() == null || withdrawalData.getAccountPassword() == null || 
				withdrawalData.getWithdrawnAmount() == null) {
			return new ResponseBody(400, false, "Bad request");
		}
		
		boolean isAccountExist = false;
		Account accountDetails = new Account(null, null, null);
		int count = -1;
		for(Account accountData : accountDao().getAccountList()) {
			count++;
			if(accountData.getAccountNumber().equals(withdrawalData.getAccountNumber())) {
				isAccountExist = true;
				accountDetails = accountData;
				break;
			}
		}
		
		if(!isAccountExist) {
			return new ResponseBody(404, false, "Your account does not exist");
		}
		
		boolean invalidToken = true;
		Token token = new Token(-1, "");
		for(Token tokenData : tokenDao().getTokenList()) {
			if(tokenData.getToken().equals(_token)) {
				invalidToken = false;
				token = tokenData;
				break;
			}
		}
		if(invalidToken) {
			return new ResponseBody(401, false, "You are unauthorized to perform this requst");
		}
		
		if(!userDao().getUserList().get(token.getUserId()-1).getAccountNumber().equals(withdrawalData.getAccountNumber())) {
			return new ResponseBody(401, false, "You are unauthorized to perform this requst");
		}

		boolean found = false;
		User userDetails = new User(-1, null, null);
		for(User user : userDao().getUserList()) {
			if(user.getAccountNumber().equals(withdrawalData.getAccountNumber()) && 
					user.getAccountPassword().equals(withdrawalData.getAccountPassword())) {
				found = true;
				userDetails = user;
				break;
			}
		}
		if(!found) {
			return new ResponseBody(401, false, "You are unauthorized to perform this requst. Please enter correct account details");
		}
		if(accountDetails.getBalance() < 1.00 || accountDetails.getBalance()-withdrawalData.getWithdrawnAmount() < 500.00) {
			return new ResponseBody(400, false, "You do not have sufficient money to perform this transaction");
		}
		
		accountDao().withdraw(count, withdrawalData.getWithdrawnAmount(), accountDetails);

		transactionDao().saveNewTransaction(
				new Transaction(withdrawalData.getAccountNumber(), new Date(), "Withdrawal", "#" + withdrawalData.getWithdrawnAmount() +
						" withdrawn from your account", withdrawalData.getWithdrawnAmount(), accountDao().getAccountList().get(count).getBalance()));
		return new ResponseBody(200, true, "transaction done successfully");
    }
	
	@RequestMapping(value="account_statement/{accountNumber}",
            method= RequestMethod.GET)
	public Object accountStatment(@PathVariable("accountNumber") String accountNumber, @RequestHeader("token") String _token)
    {
		boolean isAccountExist = false;
		Account accountDetails = new Account(null, null, null);
		for(Account accountData : accountDao().getAccountList()) {
			if(accountData.getAccountNumber().equals(accountNumber)) {
				isAccountExist = true;
				accountDetails = accountData;
				break;
			}
		}
		if(!isAccountExist) {
			return new ResponseBody(404, false, "Your account does not exist");
		}
		
		boolean invalidToken = true;
		Token token = new Token(-1, "");
		for(Token tokenData : tokenDao().getTokenList()) {
			if(tokenData.getToken().equals(_token)) {
				invalidToken = false;
				token = tokenData;
				break;
			}
		}
		if(invalidToken) {
			Map<String, Object> map = new HashMap<String, Object>();
			return new ResponseBody(401, false, "You are unauthorized to perform this requst");
		}
		
		if(!userDao().getUserList().get(token.getUserId()-1).getAccountNumber().equals(accountNumber)) {
			return new ResponseBody(401, false, "You are unauthorized to perform this requst");
		}
		ArrayList<Transaction> transaction = new ArrayList<>();
		for(Transaction transactionData :transactionDao().getTransactionList()) {
			if(transactionData.getAccountNumber().equals(accountNumber)) {
				transaction.add(transactionData);
			}
		}
		return transaction;
    }
}























