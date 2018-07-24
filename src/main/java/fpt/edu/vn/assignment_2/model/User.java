package fpt.edu.vn.assignment_2.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
public class User {

    @Id
    private String id;

    private String username;
    private String password;
    private String name;
    private String type_id;
//    private String avatar;

    private File avatar;

    public User() {
    }

    public User(String username, String password, String name, String type_id, File avatar) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.type_id = type_id;
        this.avatar = avatar;
    }

    public void toExample() {
        if (username == null) username = "";
        if (password == null) password = "";
        if (name == null) name = "";
        if (type_id == null) type_id = "";
        if (avatar == null) avatar = new File();
    }

    @Override
    public String toString() {
        return String.format("User { id: '%s', username: '%s', password: '%s', name: '%s' }",
                id, username, password, name);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public File getAvatar() {
        return avatar;
    }

    public void setAvatar(File avatar) {
        this.avatar = avatar;
    }
}
