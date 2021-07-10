package dao;

import java.util.List;

import com.examble.demo.UserStorage;

import entity.User;

public class UserDAO {
	private static UserStorage user = new UserStorage();
	
	public List<User> getUserList(){
		return user.getUserList();
	}
	
	public void addNewUser(User user) {
		this.user.getUserList().add(user);
	}
	
	public int getNextId() {
		return user.getUserList().size() + 1;
	}
}
