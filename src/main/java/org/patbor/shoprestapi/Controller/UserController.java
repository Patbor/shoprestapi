package org.patbor.shoprestapi.Controller;


import org.patbor.shoprestapi.Entity.User;
import org.patbor.shoprestapi.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        userService.addUser(user);
        return user;
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUserById(id);
    }
    @GetMapping("/list")
    public List<User> showAllUsers() {
       return userService.findAll();
    }
}
