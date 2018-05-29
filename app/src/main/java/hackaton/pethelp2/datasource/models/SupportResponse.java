package hackaton.pethelp2.datasource.models;

import java.util.List;

/**
 * Created by dmitriyt on 24.03.18.
 */

public class SupportResponse {
    private boolean success;
    private int code;
    private String error;
    private List<Support> data;

    public SupportResponse(boolean success, int code, String error, List<Support> data) {
        this.success = success;
        this.code = code;
        this.error = error;
        this.data = data;
    }

    @Override
    public String toString() {
        return "SupportResponse{" +
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

    public List<Support> getData() {
        return data;
    }

    public void setData(List<Support> data) {
        this.data = data;
    }
}
