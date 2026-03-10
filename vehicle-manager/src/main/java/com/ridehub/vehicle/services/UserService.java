package com.ridehub.vehicle.service;

import com.ridehub.vehicle.model.User;
import com.ridehub.vehicle.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private List<User> users = new ArrayList<>();

    public User register(User user) {
        users.add(user);
        return user;
    }

    public User login(String username, String password) throws UserNotFoundException {
        for (User user : users) {
            if (user.getUsername().equals(username)
                    && user.getPassword().equals(password)) {
                return user;
            }
        }
        throw new UserNotFoundException("Invalid username or password");
    }

    public User updateUser(String username, User updatedUser, String password)
            throws UserNotFoundException {

        for (User user : users) {
            if (user.getUsername().equals(username)) {

                if (!user.getPassword().equals(password)) {
                    throw new UserNotFoundException("Incorrect password");
                }

                user.setEmail(updatedUser.getEmail());
                user.setLicenseDetails(updatedUser.getLicenseDetails());
                user.setDob(updatedUser.getDob());

                return user;
            }
        }

        throw new UserNotFoundException("User not found with username: " + username);
    }

    public User getUserByUsername(String username)
            throws UserNotFoundException {

        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }

        throw new UserNotFoundException("User not found with username: " + username);
    }
}