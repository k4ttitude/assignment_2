package fpt.edu.vn.assignment_2.dao.mongo;

import fpt.edu.vn.assignment_2.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface UserRepository extends MongoRepository<User, String>, QueryByExampleExecutor<User> {

    public User findUserByUsernameAndPassword(String username, String password);
}
