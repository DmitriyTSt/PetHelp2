package hackaton.pethelp2.datasource.models;

import java.util.List;

/**
 * Created by dmitriyt on 25.03.18.
 */

public class EventResponse {
    private boolean success;
    private int code;
    private String error;
    private Event data;

    public EventResponse() {
    }

    public EventResponse(boolean success, int code, String error, Event data) {
        this.success = success;
        this.code = code;
        this.error = error;
        this.data = data;
    }

    @Override
    public String toString() {
        return "EventResponse{" +
                "success=" + success +
                ", code=" + code +
                ", error='" + error + '\'' +
                ", data=" + data +
                '}';
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Event getData() {
        return data;
    }

    public void setData(Event data) {
        this.data = data;
    }
}
