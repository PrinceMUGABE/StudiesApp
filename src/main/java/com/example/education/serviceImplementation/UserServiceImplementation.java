package com.example.education.serviceImplementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.education.modal.User;
import com.example.education.repository.UserRepository;
import com.example.education.service.UserService;


@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    UserRepository repo;

    @Override
    public User saveUser(User user) {
        return repo.save(user);
    }

    @Override
    public List<User> displayAllUsers() {
        return repo.findAll();
    }

    @Override
    public User updateUser(User updatedUser) {
        Optional<User> savedUser = repo.findById(updatedUser.getId());
        if (savedUser.isPresent()) {
            User existingUser = savedUser.get();
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setRole(updatedUser.getRole());
            return repo.save(existingUser);
        } else {
            throw new RuntimeException("User not found with id: " + updatedUser.getId());
        }
    }

    @Override
    public void deleteUser(Long user) {
        repo.deleteById(user);
    }

    @Override
    public User findUserByUsername(String username) {
        return repo.findByUsername(username);
    }

    @Override
    public User updateUserByUsername(String username, User updatedUser) {
        User existingUser = repo.findByUsername(username);
        if (existingUser != null) {
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setRole(updatedUser.getRole());
            return repo.save(existingUser);
        } else {
            throw new RuntimeException("User not found with username: " + username);
        }
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return repo.findById(id);
    }

    @Override
    public Optional<User> findUserByEmail(String eamil) {
        return repo.findUserByEmail(eamil);
    }
    

}