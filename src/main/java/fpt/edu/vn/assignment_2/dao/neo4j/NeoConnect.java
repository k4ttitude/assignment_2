package fpt.edu.vn.assignment_2.dao.neo4j;

import fpt.edu.vn.assignment_2.model.Comment;
import org.neo4j.driver.v1.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class NeoConnect implements AutoCloseable {

    private final Driver driver;
    private static final String NEO4J_URI = "bolt://localhost:7687";
    private static final String NEO4J_NAME = "neo4j";
    private static final String NEO4J_PASSWORD = "123456";
    private final String INVITE_FRIEND = "FRIEND_OF";
    private final String HAS_DOCUMENT = "HAS_DOCUMENT";
    private final String LIKE_BY = "LIKE_BY";
    private final String COMMENT_BY = "COMMENT_BY";
    private final String RATE_BY = "RATE_BY";


    public NeoConnect() {
        this.driver = GraphDatabase.driver(NEO4J_URI, AuthTokens.basic(NEO4J_NAME, NEO4J_PASSWORD));
    }

    public void close() throws Exception {
        driver.close();
    }

    public static void main(String[] args) {
        try {
            NeoConnect neo = new NeoConnect();
            neo.insertUser("1");
            neo.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //For Users
    public void insertUser(String userId) {
        // Sessions are lightweight and disposable connection wrappers.
        try (Session session = driver.session()) {
            // Wrapping Cypher in an explicit transaction provides atomicity
            // and makes handling errors much easier.
            try (Transaction tx = session.beginTransaction()) {
                tx.run("CREATE (a:User {id: '" + userId + "'})");
                tx.success();  // Mark this write as successful.
            }
        }
    }

    // Create a friendship between two people.
    private void makeFriends(String user1Id, String user2Id) {
        // Sessions are lightweight and disposable connection wrappers.
        try (Session session = driver.session()) {
            // Wrapping Cypher in an explicit transaction provides atomicity
            // and makes handling errors much easier.
            try (Transaction tx = session.beginTransaction()) {
                tx.run("MATCH (a:User {id: $user_1}) " +
                                "MATCH (b:User {id: $user_2}) " +
                                "MERGE (a)-[:" + INVITE_FRIEND + "]->(b)",
                        Values.parameters("user_1", user1Id, "user_2", user2Id));
                tx.run("MATCH (a:User {id: $user_2}) " +
                                "MATCH (b:User {id: $user_1}) " +
                                "MERGE (a)-[:" + INVITE_FRIEND + "]->(b)",
                        Values.parameters("user_2", user2Id, "user_1", user1Id));
                tx.success();  // Mark this write as successful.
            }
        }
    }

    //List friends
    public List<String> getFriendsList(String userId) {
        List<String> list = new ArrayList<>();
        try (Session session = driver.session()) {
            // Wrapping Cypher in an explicit transaction provides atomicity
            // and makes handling errors much easier.
            try (Transaction tx = session.beginTransaction()) {
                StatementResult result = tx.run("MATCH (a:User)-[:" + INVITE_FRIEND + "]->(b:User) " +
                        "WHERE a.id = {userId} return a.id, b.id", Values.parameters("userId", userId));
                while (result.hasNext()) {
                    Record record = result.next();
                    list.add(record.get("b.id").asString());
                }
                tx.success();  // Mark this write as successful.
            }
        }
        return list;
    }

    //Document
    //Insert a Document
    public void insertDocument(String docId, String ownerId) {
        // Sessions are lightweight and disposable connection wrappers.
        try (Session session = driver.session()) {
            // Wrapping Cypher in an explicit transaction provides atomicity
            // and makes handling errors much easier.
            try (Transaction tx = session.beginTransaction()) {
                tx.run("CREATE (a:Document {id: '" + docId + "'})");
                tx.success();  // Mark this write as successful.
            }
        }
        setOwnerOfDocument(ownerId, docId);
    }

    private void setOwnerOfDocument(String ownerId, String docId) {
        // Sessions are lightweight and disposable connection wrappers.
        try (Session session = driver.session()) {
            // Wrapping Cypher in an explicit transaction provides atomicity
            // and makes handling errors much easier.
            try (Transaction tx = session.beginTransaction()) {
                tx.run("MATCH (a:User {id: $ownerId}) " +
                                "MATCH (b:Document {id: $docId}) " +
                                "MERGE (a)-[:" + HAS_DOCUMENT + "]->(b)",
                        Values.parameters("ownerId", ownerId, "docId", docId));
                tx.success();  // Mark this write as successful.
            }
        }
    }

    //List doc of User
    public List<String> getOwnerBooksList(String userId) {
        List<String> list = new ArrayList<>();
        try (Session session = driver.session()) {
            // Wrapping Cypher in an explicit transaction provides atomicity
            // and makes handling errors much easier.
            try (Transaction tx = session.beginTransaction()) {
                StatementResult result = tx.run("MATCH (a:User)-[:" + HAS_DOCUMENT + "]->(b:Document) " +
                        "WHERE a.id = {userId} return a.id, b.id", Values.parameters("userId", userId));
                while (result.hasNext()) {
                    Record record = result.next();
                    list.add(record.get("b.id").asString());
                }
                tx.success();  // Mark this write as successful.
            }
        }
        return list;
    }

    //Like a doc
    public void likeADocument(String userId, String docId) {
        // Sessions are lightweight and disposable connection wrappers.
        try (Session session = driver.session()) {
            // Wrapping Cypher in an explicit transaction provides atomicity
            // and makes handling errors much easier.
            try (Transaction tx = session.beginTransaction()) {
                tx.run("MATCH (a:Document {id: $docId}) " +
                                "MATCH (b:User {id: $userId}) " +
                                "MERGE (a)-[:" + LIKE_BY + "]->(b)",
                        Values.parameters("docId", docId, "userId", userId));
                tx.success();  // Mark this write as successful.
            }
        }
    }

    //unlike A doc
    public void unlikeADocument(String userId, String docId) {
        // Sessions are lightweight and disposable connection wrappers.
        try (Session session = driver.session()) {
            // Wrapping Cypher in an explicit transaction provides atomicity
            // and makes handling errors much easier.
            try (Transaction tx = session.beginTransaction()) {
                tx.run("MATCH (a:Document)-[r:" + LIKE_BY + "]" +
                                "->(b:User) WHERE a.id = {docId} AND b.id = {userId} DELETE r",
                        Values.parameters("docId", docId, "ownerId", userId));
                tx.success();  // Mark this write as successful.
            }
        }
    }

    //List all users who like the doc
    public List<String> getUsersLikeDocument(String docId) {
        List<String> list = new ArrayList<>();
        try (Session session = driver.session()) {
            // Wrapping Cypher in an explicit transaction provides atomicity
            // and makes handling errors much easier.
            try (Transaction tx = session.beginTransaction()) {
                StatementResult result = tx.run("MATCH (a:Document)-[:" + LIKE_BY + "]->(b:User) " +
                        "WHERE a.id = {docId} return a.id, b.id", Values.parameters("docId", docId));
                while (result.hasNext()) {
                    Record record = result.next();
                    list.add(record.get("b.id").asString());
                }
                tx.success();  // Mark this write as successful.
            }
        }
        return list;
    }

    //Comment a doc
    public void commentADocument(Comment c) {
        // Sessions are lightweight and disposable connection wrappers.
        Timestamp ts = new Timestamp(System.currentTimeMillis());

        try (Session session = driver.session()) {
            // Wrapping Cypher in an explicit transaction provides atomicity
            // and makes handling errors much easier.
            try (Transaction tx = session.beginTransaction()) {
                tx.run("MATCH (a:Document {id: $docId}) " +
                                "MATCH (b:User {id: $ownerId}) " +
                                "MERGE (a)-[:" + COMMENT_BY + "{content:{content}, time:{time}}]->(b)",
                        Values.parameters("docId", c.getDocId(), "ownerId", c.getUserId(),
                                "content", c.getContent(), "time", ts.getTime()));
                tx.success();  // Mark this write as successful.
            }
        }
    }

    //List all the comment on this Doc
    public List<Comment> getCommentsOnDocument(String docId) {
        List<Comment> list = new ArrayList<>();
        try (Session session = driver.session()) {
            // Wrapping Cypher in an explicit transaction provides atomicity
            // and makes handling errors much easier.
            try (Transaction tx = session.beginTransaction()) {

                StatementResult result = tx.run("MATCH (a:Document)-[r:" + COMMENT_BY + "]->(b:User) " +
                        "WHERE a.id = {docId} return r.time as time, r.content as content, b.id as id", Values.parameters("docId", docId));
                while (result.hasNext()) {
                    Record record = result.next();
                    list.add(new Comment(record.get("id").asString(), docId,
                            record.get("content").asString(), record.get("time").asLong()));
                }
                tx.success();  // Mark this write as successful.
            }
        }
        return list;
    }

    //For rating
    public void rateADocument(String userId, String docId, int score) {
        if (isRatedDocument(userId, docId)) {
            deleteRatedDocument(userId, docId);
        }
        try (Session session = driver.session()) {
            // Wrapping Cypher in an explicit transaction provides atomicity
            // and makes handling errors much easier.
            try (Transaction tx = session.beginTransaction()) {
                tx.run("MATCH (a:Document {id: $docId}) " +
                                "MATCH (b:User {id: $userId}) " +
                                "MERGE (a)-[r:" + RATE_BY + "{score:{score}}]->(b)",
                        Values.parameters("docId", docId, "userId", userId, "score", score));
                tx.success();  // Mark this write as successful.
            }
        }
    }

    private void deleteRatedDocument(String userId, String docId) {
        try (Session session = driver.session()) {
            // Wrapping Cypher in an explicit transaction provides atomicity
            // and makes handling errors much easier.
            try (Transaction tx = session.beginTransaction()) {
                tx.run("MATCH (a:Document {id:{docId}})-[r:" + RATE_BY + "]->(b:User {id:{userId}}) DELETE r",
                        Values.parameters("docId", docId, "userId", userId));
                tx.success();  // Mark this write as successful.
            }
        }
    }

    private boolean isRatedDocument(String userId, String docId) {
        try (Session session = driver.session()) {
            // Wrapping Cypher in an explicit transaction provides atomicity
            // and makes handling errors much easier.

            try (Transaction tx = session.beginTransaction()) {
                StatementResult result = tx.run("MATCH (a:Document)-[r:" + RATE_BY + "]->(b:User) " +
                                "WHERE a.id = {docId} AND b.id = {userId} return r",
                        Values.parameters("docId", docId, "userId", userId));
                while (result.hasNext()) {
                    return true;
                }
                tx.success();  // Mark this write as successful.
            }
        }
        return false;
    }

    public Double getRateOfDocument(String docId) {
        int ratedTime = 0;
        int totalScore = 0;
        try (Session session = driver.session()) {
            // Wrapping Cypher in an explicit transaction provides atomicity
            // and makes handling errors much easier.

            try (Transaction tx = session.beginTransaction()) {

                StatementResult result = tx.run("MATCH (a:Document)-[r:" + RATE_BY + "]->(b:User) " +
                        "WHERE a.id = {docId} return r.score as score", Values.parameters("docId", docId));
                while (result.hasNext()) {
                    Record record = result.next();
                    totalScore += record.get("score").asInt();
                    ratedTime++;
                }
                tx.success();  // Mark this write as successful.
            }
        }

        return totalScore * 1.0 / ratedTime;
    }

    public static void main(String[] args) throws Exception {
        NeoConnect neo = new NeoConnect();
        neo.rateADocument("111", "111", 2);
        neo.rateADocument("1", "111", 2);
        System.out.println("Rated score turn 1: " + neo.getRateOfDocument("111"));
        neo.rateADocument("1", "111", 1);
        System.out.println("Rated score turn 2: " + neo.getRateOfDocument("111"));

        neo.close();
    }

}


