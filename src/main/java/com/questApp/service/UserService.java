package com.questApp.service;

import com.questApp.data.entity.User;

import java.util.List;

public interface UserService {

     List<User> getAllUsers();

    public User saveOneUser(User newUser);

    public User getOneUserById(Long userId);

    public User updateOneUser(Long userId, User newUser);

    public void deleteById(Long userId);

    public User getOneUserByUserName(String userName);


}
