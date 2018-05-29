package hackaton.pethelp2.datasource.models;

/**
 * Created by dmitriyt on 24.03.18.
 */

public class ResponseData {
    private ResponseUser user;
    private ResponseDevice device;
    private String path;
    private Event event;

    @Override
    public String toString() {
        return "ResponseData{" +
                "user=" + user +
                ", device=" + device +
                ", path='" + path + '\'' +
                ", event=" + event +
                '}';
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public ResponseUser getUser() {
        return user;
    }

    public void setUser(ResponseUser user) {
        this.user = user;
    }

    public ResponseDevice getDevice() {
        return device;
    }

    public void setDevice(ResponseDevice device) {
        this.device = device;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
