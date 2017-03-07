package com.tracker.service;

import com.tracker.model.bindingModels.LoggedUserModel;
import com.tracker.model.bindingModels.RegisterUserModel;

public interface UserService {

    void create(RegisterUserModel registerUserModel);

    RegisterUserModel findByUsername(String username);

    LoggedUserModel findByUsernameAndPassword(String username, String password);
}
