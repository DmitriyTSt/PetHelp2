package hackaton.pethelp2.controllers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import hackaton.pethelp2.MyApplication;
import hackaton.pethelp2.R;
import hackaton.pethelp2.datasource.models.Event;
import hackaton.pethelp2.datasource.models.Response;
import retrofit2.Call;
import retrofit2.Callback;

public class EventFinish extends AppCompatActivity implements OnMapReadyCallback {

    private Event mEvent;
    private GoogleMap mMap;
    private Context mContext;
    private boolean mapInit = false;
    private boolean mapReady = false;

    private TextView eventDesctiption;
    private ImageView eventPhoto;
    private TextView EventCanHelp;
    private RecyclerView ownUserSupport;
    private Button btnAccept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_finish);
        mContext = this;
        int event_id = getIntent().getIntExtra("event_id", 0);
        MyApplication.getInstance().getMainApiInterface().getEventById(event_id, MyApplication.getInstance().getApiKey())
                .enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        Log.d("GET_EVENT", response + "\nbody: " + response.body());
                        mEvent = response.body().getData().getEvent();
                        setView();
                        if (mapReady && !mapInit) {
                            setMap();
                        }
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                        Log.d("GET_EVENT", t.toString());
                    }
                });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_finish);
        mapFragment.getMapAsync(this);
        eventDesctiption = findViewById(R.id.event_description);
        eventPhoto = findViewById(R.id.event_photo);
        EventCanHelp = findViewById(R.id.event_can_help);
        ownUserSupport = findViewById(R.id.own_user_support);
        btnAccept = findViewById(R.id.btn_accept);

    }

    private void setView() {
        if (mEvent == null) return;
        eventDesctiption.setText(mEvent.getDescription());
        Picasso.get().load(MyApplication.getInstance().getImgUrl() + mEvent.getImage().getPath()).into(eventPhoto);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, MapsActivity.class));
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mapReady = true;
        if (mEvent != null) {
            setMap();
        }
    }

    private void setMap() {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(mEvent.getLatitude(), mEvent.getLongitude())).zoom(18).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mMap.animateCamera(cameraUpdate);
        mMap.getUiSettings().setAllGesturesEnabled(false);
        mapInit = true;
    }
}
