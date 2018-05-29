package hackaton.pethelp2.datasource.network;

import hackaton.pethelp2.datasource.models.Event;
import hackaton.pethelp2.datasource.models.EventResponse;
import hackaton.pethelp2.datasource.models.PetResponse;
import hackaton.pethelp2.datasource.models.Response;
import hackaton.pethelp2.datasource.models.SupportResponse;
import hackaton.pethelp2.datasource.models.UserResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by dmitriyt on 24.03.18.
 */

public interface MainApiInterface {
    @POST("image/upload-json")
    Call<Response> imageUpload(@Body ImageUpload image, @Header("apiKey") String apiKey);

    @POST("event/create")
    Call<EventResponse> createEvent(@Body Event event, @Header("apiKey") String apiKey);

    @GET("support/list")
    Call<SupportResponse> getSupportList(@Header("apiKey") String apiKey);

    @GET("pet/list")
    Call<PetResponse> getPetList(@Header("apiKey") String apiKey);

    @GET("event/show/{id}")
    Call<Response> getEventById(@Path("id") int id, @Header("apiKey") String apiKey);

    @GET("user/")
    Call<UserResponse> getUserById(@Query("userId") int id, @Header("apiKey") String apiKey);
}
