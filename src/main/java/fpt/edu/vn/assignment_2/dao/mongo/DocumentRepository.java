package fpt.edu.vn.assignment_2.dao.mongo;

import fpt.edu.vn.assignment_2.model.Document;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DocumentRepository extends MongoRepository<Document, String> {

    public List<Document> findAllByAuthorId(String authorId);
}
