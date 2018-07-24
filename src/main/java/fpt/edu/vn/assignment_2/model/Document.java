package fpt.edu.vn.assignment_2.model;

import org.springframework.data.annotation.Id;

@org.springframework.data.mongodb.core.mapping.Document(collection = "document")
public class Document {

    @Id
    private String id;

    private String title;
    private String description;
    private String authorId;
    private File content;
    private int view;
    private String status;

    public Document() {
    }

    public Document(String title, String description, String authorId, File content, int view, String status) {
        this.title = title;
        this.description = description;
        this.authorId = authorId;
        this.content = content;
        this.view = view;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public File getContent() {
        return content;
    }

    public void setContent(File content) {
        this.content = content;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
