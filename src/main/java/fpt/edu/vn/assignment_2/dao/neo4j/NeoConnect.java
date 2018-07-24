package fpt.edu.vn.assignment_2.dao.neo4j;

import org.neo4j.driver.v1.*;

import java.util.ArrayList;
import java.util.List;

public class NeoConnect implements AutoCloseable {

    private final Driver driver;
    private static final String NEO4J_URI = "bolt://localhost:7687";
    private static final String NEO4J_NAME = "neo4j";
    private static final String NEO4J_PASSWORD = "mockiembinh";
    private final String INVITE_FRIEND = "FRIEND_OF";
    private final String HAS_DOCUMENT = "HAS_DOCUMENT";
    private final String LIKE_BY = "LIKE_BY";
    private final String RATE_BY = "RATE_BY";
    private final String COMMENT_BY = "COMMENT_BY";


    private static void main(String[] args) throws Exception {
        NeoConnect model = new NeoConnect(NEO4J_URI, NEO4J_NAME, NEO4J_PASSWORD);
        for (String s : model.getFriendsList("111")) {
            System.out.println(s);
        }
        model.close();
    }

    public NeoConnect(String uri, String user, String password) {
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }

    public void close() throws Exception {
        driver.close();
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

    //List Book of User
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

    public void likeADocument(String ownerId, String docId) {
        // Sessions are lightweight and disposable connection wrappers.
        try (Session session = driver.session()) {
            // Wrapping Cypher in an explicit transaction provides atomicity
            // and makes handling errors much easier.
            try (Transaction tx = session.beginTransaction()) {
                tx.run("MATCH (a:Document {id: $docId}) " +
                                "MATCH (b:User {id: $ownerId}) " +
                                "MERGE (a)-[:" + LIKE_BY + "]->(b)",
                        Values.parameters("docId", docId, "ownerId", ownerId));
                tx.success();  // Mark this write as successful.
            }
        }
    }

    public void unlikeADocument(String ownerId, String docId) {
        // Sessions are lightweight and disposable connection wrappers.
        try (Session session = driver.session()) {
            // Wrapping Cypher in an explicit transaction provides atomicity
            // and makes handling errors much easier.
            try (Transaction tx = session.beginTransaction()) {
                tx.run("MATCH (a:Document {id: $docId}) " +
                                "MATCH (b:User {id: $ownerId}) " +
                                "MERGE (a)-[:" + LIKE_BY + "]->(b)",
                        Values.parameters("docId", docId, "ownerId", ownerId));
                tx.success();  // Mark this write as successful.
            }
        }
    }
}


