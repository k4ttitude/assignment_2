package fpt.edu.vn.assignment_2.model;

public class Comment {
    private String userId;
    private String docId;
    private String content;
    private long time;//in TimeStamp

    public Comment(String userId, String docId, String content, long time) {
        this.userId = userId;
        this.docId = docId;
        this.content = content;
        this.time = time;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}

