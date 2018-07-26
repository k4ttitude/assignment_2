package fpt.edu.vn.assignment_2.controller;

import fpt.edu.vn.assignment_2.dao.mongo.DocumentRepository;
import fpt.edu.vn.assignment_2.dao.neo4j.NeoConnect;
import fpt.edu.vn.assignment_2.model.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/doc")
public class DocumentController {

    private NeoConnect neoConnect;

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

    @PostMapping("/getDocumentsNoContentByEg")
    public List<Document> getDocumentsNoContentByEg(@RequestBody Document document) {
        if (document == null) {
            return null;
        }
        List<Document> docs = documentRepository.findAll(Example.of(document));
        for (Document d: docs) {
            d.setContent(null);
        }
        return docs;
    }

    @GetMapping("/findByTitle")
    public List<Document> findDocumentByTitle(@RequestParam String title) {
        return documentRepository.findAllByTitleContains(title);
    }

    @PutMapping("/saveDocument")
    public boolean saveDocument(@RequestBody Document document) {
        if (document == null) {
            return false;
        }

        // Create Document
        if (document.getId() == null || documentRepository.findById(document.getId()) == null) {
            document = documentRepository.save(document);
            // Neo4j
            neoConnect = new NeoConnect();
            neoConnect.insertDocument(document.getId(), document.getAuthorId());
            return true;
        }

        // Update Document
        documentRepository.save(document);
         return true;
    }

}
