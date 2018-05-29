package hackaton.pethelp2.datasource.models;

/**
 * Created by dmitriyt on 24.03.18.
 */

public class EventAnswer {
    private Integer id;
    private int event_id;
    private int support_id;
    private int user_id;

    public EventAnswer(int event_id, int support_id) {
        this.id = null;
        this.event_id = event_id;
        this.support_id = support_id;
    }

    @Override
    public String toString() {
        return "EventAnswer{" +
                "id=" + id +
                ", event_id=" + event_id +
                ", support_id=" + support_id +
                ", user_id=" + user_id +
                '}';
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public int getEvent_id() {
        return event_id;
    }

    public int getSupport_id() {
        return support_id;
    }

    public int getUser_id() {
        return user_id;
    }
}
