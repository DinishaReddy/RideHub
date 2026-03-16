package com.ridehub.vehicle.services;

import com.ridehub.vehicle.model.User;
import com.ridehub.vehicle.repositories.UserRepository;
import com.ridehub.vehicle.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User register(User user) {
        return userRepository.save(user);
    }

    public User login(String username, String password) throws UserNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            return user;
        } else {
            throw new UserNotFoundException("Invalid username or password");
        }
    }

    public User updateUser(String username, User updatedUser, String password) throws UserNotFoundException {

        User existingUser = userRepository.findByUsername(username);

        if (existingUser == null) {
            throw new UserNotFoundException("User not found with username: " + username);
        }

        if (!existingUser.getPassword().equals(password)) {
            throw new UserNotFoundException("Incorrect password");
        }

        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setLicenseDetails(updatedUser.getLicenseDetails());
        existingUser.setDob(updatedUser.getDob());

        return userRepository.save(existingUser);
    }

    public User getUserByUsername(String username) throws UserNotFoundException {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UserNotFoundException("User not found with username: " + username);
        }

        return user;
    }
}