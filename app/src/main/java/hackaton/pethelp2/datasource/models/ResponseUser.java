package hackaton.pethelp2.datasource.models;

/**
 * Created by dmitriyt on 24.03.18.
 */

public class ResponseUser {
    private boolean isOnline;
    private ImageMy avatar;
    private String email;
    private String nickname;
    private String createdAt;
    private int id;

    @Override
    public String toString() {
        return "ResponseUser{" +
                "avatar=" + avatar +
                ", email='" + email + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }
}
