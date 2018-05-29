package hackaton.pethelp2.controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.ArrayList;
import java.util.Arrays;

import hackaton.pethelp2.MyApplication;
import hackaton.pethelp2.R;
import hackaton.pethelp2.datasource.models.Response;
import hackaton.pethelp2.datasource.models.Support;
import hackaton.pethelp2.datasource.models.SupportResponse;
import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {

    Context mContext;

    CallbackManager mFacebookCallbackManager;
    LoginManager mLoginManager;
    AccessTokenTracker mAccessTokenTracker;
    SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO facebook login cash
        super.onCreate(savedInstanceState);
        mContext = this;
        setTitle("Авторизация");
        mPreferences = getSharedPreferences("login_data", MODE_PRIVATE);
        if (MyApplication.getInstance().isUserLogin()) {
            Log.d("RUN_AWAY", "from has our apikey " + mPreferences.getString("apiKey", ""));
            runAway();
        } else {
            setContentView(R.layout.activity_login);

            ImageView btnVkLogin = (ImageView) findViewById(R.id.btn_login_vk);
            ImageView btnFbLogin = (ImageView) findViewById(R.id.btn_login_fb);

            setupFacebookStuff();
            updateFacebookButtonUI();

            btnFbLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(mContext, "FB login", Toast.LENGTH_SHORT).show();
                    handleFacebookLogin();
                }
            });
        }
    }

    private void setupFacebookStuff() {

        // This should normally be on your application class
        FacebookSdk.sdkInitialize(getApplicationContext());

        mAccessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                updateFacebookButtonUI();
            }
        };

        mLoginManager = LoginManager.getInstance();
        mFacebookCallbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(mFacebookCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                updateFacebookButtonUI();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                //Toast.makeText(mContext, error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("FB LOGIN", error.toString());
//                if (error.toString().contains("Invalid key hash")) {
//                    mLoginManager.logOut();
//                    AccessToken.setCurrentAccessToken(null);
//                    handleFacebookLogin();
//                }
            }
        });
    }

    private void updateFacebookButtonUI(){
        if (AccessToken.getCurrentAccessToken() != null){
            //Toast.makeText(mContext, AccessToken.getCurrentAccessToken().getToken(), Toast.LENGTH_SHORT).show();
            Log.d("SERVER LOGIN", "login start token " + AccessToken.getCurrentAccessToken().getToken());
            getApikey(AccessToken.getCurrentAccessToken().getToken());

        }else{
            //Toast.makeText(mContext, "anonim", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleFacebookLogin() {
        if (AccessToken.getCurrentAccessToken() != null){
            mLoginManager.logOut();
        }else{
            mAccessTokenTracker.startTracking();
            mLoginManager.logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void getApikey(String tokenFb) {
        Log.d("SERVER LOGIN", "login start 1");
        Call<Response> response = MyApplication.getInstance().getLoginResponseInterface().loginFb(tokenFb);
        Log.d("SERVER LOGIN", "login start 2");
        response.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                Log.d("SERVER LOGIN", "response " + response.body());
                Log.d("SERVER LOGIN", "code: " + response.body().getCode());
                mPreferences = getSharedPreferences("login_data", MODE_PRIVATE);
                if (response.body().getData().getDevice().getApiKey() != null) {
                    SharedPreferences.Editor editor = mPreferences.edit();
                    String apiKey = response.body().getData().getDevice().getApiKey();
                    int userId = response.body().getData().getUser().getId();
                    editor.putString("apiKey", apiKey);
                    editor.putInt("userId", userId);
                    editor.apply();
                    MyApplication.getInstance().setApiKey(apiKey);
                    MyApplication.getInstance().setUserId(userId);
                    MyApplication.getInstance().setUserLogin(true);
                    MyApplication.getInstance().parseMyUser();
                    Log.d("RUN_AWAY", "request 2");
                    runAway();
                } else {
                    Toast.makeText(mContext, "Что-то на сервере пошло не так", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.d("SERVER LOGIN", t.getMessage());
            }
        });
    }

    void runAway() {

        double lat = getIntent().getDoubleExtra("lat", 0);
        double lng = getIntent().getDoubleExtra("lng", 0);
        startActivity(new Intent(mContext, EventAddView.class)
                .putExtra("lat", lat)
                .putExtra("lng", lng));
        finish();
    }
}
