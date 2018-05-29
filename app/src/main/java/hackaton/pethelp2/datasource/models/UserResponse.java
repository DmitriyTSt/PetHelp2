package hackaton.pethelp2.datasource.models;

/**
 * Created by dmitriyt on 25.03.18.
 */

public class UserResponse {
    private boolean success;
    private int code;
    private String error;
    private User data;

    @Override
    public String toString() {
        return "UserResponse{" +
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

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}
