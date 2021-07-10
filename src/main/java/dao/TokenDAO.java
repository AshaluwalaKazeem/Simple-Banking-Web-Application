package dao;

import java.util.List;

import com.examble.demo.TokenStorage;

import entity.Token;

public class TokenDAO {
	private static TokenStorage token = new TokenStorage();
	
	public List<Token> getTokenList(){
		return token.getTokenList();
	}
	
	public void addNewToken(Token token) {
		this.token.getTokenList().add(token);
	}
	
	public void updateToken(Token token, int index) {
		this.token.getTokenList().set(index, token);
	}
}
