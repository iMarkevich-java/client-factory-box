package com.markevich.factory.service.user;

import businessObjectFactoryBox.User;

public class UserServices {
    public UserServices() {
    }

    public String verification(User user) {
        VerificationUser verificationUser = new VerificationUser();
        return verificationUser.verificationUser(user);
    }
}
