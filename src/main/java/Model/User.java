package Model;

import java.sql.Timestamp;

public class User {
    private Long id;
    private String username;
    private String password;
    private String profilePictureUrl;
    private Timestamp createdAt;

    public User() {}

    public User(Long id, String username, String password, String profilePictureUrl, Timestamp createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.profilePictureUrl = profilePictureUrl;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}