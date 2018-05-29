package hackaton.pethelp2.datasource.models;

/**
 * Created by dmitriyt on 25.03.18.
 */

public class User {
    private int id;
    private boolean isOnline;
    private ImageMy avatar;
    private String nickname;
    private String email;
    private String createdAt;
    private String lastLogin;

    public User(int id, ImageMy avatar, String nickname, String email) {
        this.id = id;
        this.avatar = avatar;
        this.nickname = nickname;
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", isOnline=" + isOnline +
                ", avatar=" + avatar +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", lastLogin='" + lastLogin + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public ImageMy getAvatar() {
        return avatar;
    }

    public void setAvatar(ImageMy avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }
}
