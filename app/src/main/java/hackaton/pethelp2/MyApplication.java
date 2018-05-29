package hackaton.pethelp2;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;

import hackaton.pethelp2.datasource.models.ImageMy;
import hackaton.pethelp2.datasource.models.ResponseUser;
import hackaton.pethelp2.datasource.models.Support;
import hackaton.pethelp2.datasource.models.SupportResponse;
import hackaton.pethelp2.datasource.models.User;
import hackaton.pethelp2.datasource.models.UserResponse;
import hackaton.pethelp2.datasource.network.LoginResponseInterface;
import hackaton.pethelp2.datasource.network.MainApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dmitriyt on 24.03.18.
 */

public class MyApplication extends Application {
    private static MyApplication sInstance;
    private String apiKey;
    private int userId;
    private User myUser;
    private LoginResponseInterface loginResponseInterface;
    private MainApiInterface mainApiInterface;
    private ArrayList<Support> supportList;

    private boolean userLogin;

    public static MyApplication getInstance() {
        return sInstance;
    }

    public static Context getContext() {
        return sInstance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;

        supportList = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getApiUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl(getApiUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        loginResponseInterface = retrofit.create(LoginResponseInterface.class);
        mainApiInterface = retrofit1.create(MainApiInterface.class);

        SharedPreferences sharedPreferences = getSharedPreferences("login_data", MODE_PRIVATE);
        if (sharedPreferences.contains("apiKey")) {
            userLogin = true;
            apiKey = sharedPreferences.getString("apiKey", "");
            userId = sharedPreferences.getInt("userId", 1);
            parseMyUser();
        } else {
            userLogin = false;
        }

    }

    public void parseMyUser() {
        final SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
        if (!sp.contains("id")) {
            mainApiInterface.getUserById(userId, apiKey).enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    Log.d("PARSE_USER", response + "\n body: " + response.body());
                    myUser = response.body().getData();
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putInt("id", myUser.getId());
                    editor.putString("nickname", myUser.getNickname());
                    editor.putString("email", myUser.getEmail());
                    editor.putString("ava", myUser.getAvatar().getPath());
                    editor.apply();
                }

                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {
                    Log.d("PARSE_USER", t.toString());
                }
            });
        } else {
            myUser = new User(sp.getInt("id", 0), new ImageMy(sp.getString("ava","")),
                    sp.getString("nickname", ""), sp.getString("email", ""));
        }

    }

    public String getApiUrl() {
        return "http://prod-hakaton.profsoft.online/app_dev.php/api/";
    }

    public String getImgUrl() {
        return "http://prod-hakaton.profsoft.online/uploads/images/originals/";
    }

    public LoginResponseInterface getLoginResponseInterface() {
        return loginResponseInterface;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public MainApiInterface getMainApiInterface() {
        return mainApiInterface;
    }

    public ArrayList<Support> getSupportList() {
        return supportList;
    }

    public void setSupportList(ArrayList<Support> supportList) {
        this.supportList = supportList;
    }

    public boolean isUserLogin() {
        return userLogin;
    }

    public void setUserLogin(boolean userLogin) {
        this.userLogin = userLogin;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
