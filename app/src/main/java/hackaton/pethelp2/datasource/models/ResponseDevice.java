package hackaton.pethelp2.datasource.models;

/**
 * Created by dmitriyt on 24.03.18.
 */

public class ResponseDevice {
    private String apiKey;

    @Override
    public String toString() {
        return "ResponseDevice{" +
                "apiKey='" + apiKey + '\'' +
                '}';
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
