package fpt.edu.vn.assignment_2.controller;

import fpt.edu.vn.assignment_2.dao.mongo.DocumentRepository;
import fpt.edu.vn.assignment_2.model.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/doc")
public class DocumentController {

    @Autowired
    DocumentRepository documentRepository;

    @GetMapping("/getDocuments")
    public List<Document> getDocuments() {
        return documentRepository.findAll();
    }

    @GetMapping("/getDocumentsByAuthor")
    public List<Document> getDocumentsByAuthor(@RequestParam(name = "authorId") String authorId) {
        return documentRepository.findAllByAuthorId(authorId);
    }

    @PostMapping("/getDocumentsByEg")
    public List<Document> getDocumentsByEg(@RequestBody Document document) {
        if (document == null) {
            return null;
        }
        return documentRepository.findAll(Example.of(document));
    }

    @PutMapping("/saveDocument")
    public void saveDocument(@RequestBody Document document) {
        if (document == null) {
            return;
            // return false;
        }
        documentRepository.save(document);
        // return true;
    }

}
