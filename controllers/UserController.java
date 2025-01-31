package foodPanda.controllers;

import foodPanda.models.User;
import foodPanda.exceptions.InvalidLoginException;
import java.util.ArrayList;
import java.util.List;

public class UserController {
    private List<User> users;

    public UserController() {
        users = new ArrayList<>();

        users.add(new User(1, "dara", "dara1234"));
        users.add(new User(2, "ellie", "ellie1234"));
    }

    public User login(String username, String password) throws InvalidLoginException {
        return users.stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst()
                .orElseThrow(() -> new InvalidLoginException("Invalid username or password"));
    }
}