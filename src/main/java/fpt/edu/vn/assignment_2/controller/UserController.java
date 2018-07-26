package fpt.edu.vn.assignment_2.controller;

import fpt.edu.vn.assignment_2.dao.mongo.UserRepository;
import fpt.edu.vn.assignment_2.dao.neo4j.NeoConnect;
import fpt.edu.vn.assignment_2.model.User;
import fpt.edu.vn.assignment_2.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    private NeoConnect neoConnect;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/getUsers")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/getUser/{id}")
    public User getUser(@PathVariable(value = "id") String id) {
        User user = userRepository.findById(id).orElse(null);

        if (user != null && user.getAvatar() != null) {
            try {
                FileOutputStream imgOuputStream = new FileOutputStream("avatar.jpg");
                imgOuputStream.write(Utils.decodeImage(user.getAvatar().getData()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return user;
    }

    @PutMapping(value = "/updateUser")
    public void updateUser(@RequestBody User user) {
        userRepository.save(user);
    }

    @PostMapping("/login")
    public boolean validateLogin(@RequestParam(name = "username") String username,
                                 @RequestParam(name = "password") String password) {
        if (username == null || password == null) {
            return false;
        }
        try {
            User user = userRepository.findUserByUsernameAndPassword(username, password);
            return (user != null && user.getUsername().equals(username) && user.getPassword().equals(password));
        } catch (Exception ex) {
            return false;
        }
    }

    @PostMapping("/getUsersByEg")
    public List<User> getUsersByEg(@RequestBody User sampleUser) {
        if (sampleUser == null) {
            return null;
        }
        //sampleUser.toExample();
        Example<User> example = Example.of(sampleUser);
        List<User> users = userRepository.findAll(example);
        return users;
    }

    @PutMapping("/saveUser")
    public boolean saveUser(@RequestBody User user) {
        if (user == null) {
            return false;
        }

        // Create User
        if (user.getId() == null || userRepository.findById(user.getId()) == null) {
            return createUser(user);
        }

        // Update user
        userRepository.save(user);
        return true;
    }

    private boolean createUser(User user) {
        if (userRepository.findAll(Example.of(user)).size() == 0) {
            user = userRepository.save(user);
            // Add userId to Neo4j
            this.neoConnect = new NeoConnect();
            neoConnect.insertUser(user.getId());
            return true;
        }
        return false;
    }
}
