package com.palominolabs.geopic;

import com.stackmob.sdk.model.StackMobUser;

public class User extends StackMobUser {

	protected User(String username, String password) {
		super(User.class, username, password);
	}

}
