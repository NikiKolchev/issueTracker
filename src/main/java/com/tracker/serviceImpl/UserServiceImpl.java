package com.tracker.serviceImpl;

import com.tracker.entities.User;
import com.tracker.entities.enums.Role;
import com.tracker.model.bindingModels.LoggedUserModel;
import com.tracker.model.bindingModels.LoginUserModel;
import com.tracker.model.bindingModels.RegisterUserModel;
import com.tracker.repository.UserRepository;
import com.tracker.service.UserService;
import com.tracker.utils.parser.interfaces.ModelParser;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Stateless
public class UserServiceImpl implements UserService {

    @Inject
    private UserRepository userRepository;

    @Inject
    private ModelParser modelParser;

    @Override
    public void create(RegisterUserModel registerUserModel) {
        User user = this.modelParser.convert(registerUserModel, User.class);
        long totalUsers = this.userRepository.getTotalUsers();
        if(totalUsers == 0) {
            user.setRole(Role.ADMIN);
        } else {
            user.setRole(Role.USER);
        }

        this.userRepository.create(user);
    }

    @Override
    public RegisterUserModel findByUsername(String username) {
        User user = this.userRepository.findByUsername(username);
        RegisterUserModel registerUserModel = null;
        if(user != null){
            registerUserModel = this.modelParser.convert(user, RegisterUserModel.class);
        }

        return registerUserModel;
    }

    @Transactional
    @Override
    public LoggedUserModel findByUsernameAndPassword(String username, String password) {
        User user = this.userRepository.findByUsernameAndPassword(username, password);
        LoggedUserModel loginUserModel = null;
        if(user != null){
            loginUserModel = this.modelParser.convert(user, LoggedUserModel.class);
        }

        return loginUserModel;
    }
}
