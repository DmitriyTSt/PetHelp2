package hackaton.pethelp2.datasource.network;

import hackaton.pethelp2.datasource.models.Response;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by dmitriyt on 24.03.18.
 */

public interface LoginResponseInterface {

    @POST("fb-login")
    Call<Response> loginFb(@Query("fb-token")String fb_token);
}
