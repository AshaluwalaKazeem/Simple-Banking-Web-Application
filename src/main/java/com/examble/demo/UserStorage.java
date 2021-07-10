package com.examble.demo;

import java.util.ArrayList;
import java.util.List;

import entity.User;

public class UserStorage {
	private List<User> userList;

	public List<User> getUserList() {
		if(userList == null) {
			userList = new ArrayList<>();
		}
		return userList;
	}

	public void setUserList(List<User> userList) {
		if(userList != null) this.userList = userList;
	}
}
