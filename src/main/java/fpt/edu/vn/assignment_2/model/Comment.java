package fpt.edu.vn.assignment_2.model;

import java.sql.Timestamp;

public class Comment {
    private String userId;
    private String docId;
    private Timestamp time;

    public Comment(String userId, String docId, Timestamp time) {
        this.userId = userId;
        this.docId = docId;
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
