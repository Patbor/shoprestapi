package org.patbor.shoprestapi.Service;

import org.patbor.shoprestapi.Entity.User;
import org.patbor.shoprestapi.Exceptions.AlreadyExistsException;
import org.patbor.shoprestapi.Exceptions.NotFoundException;
import org.patbor.shoprestapi.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(User user) {
        if (userRepository.findUserByEmail(user.getEmail()) != null)
            throw new AlreadyExistsException("This email has already existed, try with new");
        else
            userRepository.save(user);
    }

    public void deleteUserById(int id) {
        Optional<User> tempUser = userRepository.findById(id);
        if (tempUser.isPresent()) {
            User user = tempUser.get();
            userRepository.delete(user);
        } else {
            throw new NotFoundException("Customer with id " + id + " doesn't exist");
        }
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findUserByID(int id) {
        Optional<User> OptUser = userRepository.findById(id);
        User user;
        if (OptUser.isPresent()) {
            user = OptUser.get();
        } else {
            throw new NotFoundException("Customer with id " + id + " doesn't exist");
        }
        return user;
    }
}
