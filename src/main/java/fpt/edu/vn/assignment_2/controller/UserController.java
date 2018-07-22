package fpt.edu.vn.assignment_2.controller;

import fpt.edu.vn.assignment_2.dao.mongo.UserRepository;
import fpt.edu.vn.assignment_2.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/getUsers")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/getUser/{id}")
    public User getUser(@PathVariable(value = "id") String id) {
        return userRepository.findById(id).orElse(null);
    }

    @PutMapping(value = "/updateUser")
    public void updateUser(@RequestBody User user) {
        System.out.println(user.toString());
        userRepository.save(user);
    }
}
