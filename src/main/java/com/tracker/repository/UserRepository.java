package com.tracker.repository;


import com.tracker.entities.User;

public interface UserRepository {

    void create(User create);

    User findByUsername(String username);

    User findByUsernameAndPassword(String username, String password);

    long getTotalUsers();
}
