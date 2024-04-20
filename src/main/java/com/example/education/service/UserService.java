package com.example.education.service;

import java.util.List;
import java.util.Optional;

import com.example.education.modal.User;

public interface UserService {
    User saveUser(User user);
    List<User> displayAllUsers();
    User updateUser(User user);
    void deleteUser(Long user);
    User findUserByUsername(String username);
    User updateUserByUsername(String username, User updatedUser);
    Optional<User> findUserById(Long id);
    Optional<User> findUserByEmail(String eamil);
}