package fpt.edu.vn.assignment_2.controller;

import fpt.edu.vn.assignment_2.dao.mongo.DocumentRepository;
import fpt.edu.vn.assignment_2.model.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
