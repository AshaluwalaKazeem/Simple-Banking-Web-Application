package com.examble.demo;

import java.util.ArrayList;
import java.util.List;

import entity.Token;

public class TokenStorage {
	private List<Token> tokenList;

	public List<Token> getTokenList() {
		if(tokenList == null) {
			tokenList = new ArrayList<>();
		}
		return tokenList;
	}

	public void setTokenList(List<Token> tokenList) {
		if(tokenList != null) this.tokenList = tokenList;
	}
}
