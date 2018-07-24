package fpt.edu.vn.assignment_2.dao.mongo;

import fpt.edu.vn.assignment_2.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    public User findUserByUsernameAndPassword(String username, String password);
}
