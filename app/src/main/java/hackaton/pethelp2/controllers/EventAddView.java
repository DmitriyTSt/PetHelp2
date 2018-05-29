package hackaton.pethelp2.controllers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import hackaton.pethelp2.MyApplication;
import hackaton.pethelp2.R;
import hackaton.pethelp2.controllers.SupportRecyclerView.SupportViewAdapter;
import hackaton.pethelp2.datasource.models.Event;
import hackaton.pethelp2.datasource.models.EventResponse;
import hackaton.pethelp2.datasource.models.ImageMy;
import hackaton.pethelp2.datasource.models.Pet;
import hackaton.pethelp2.datasource.models.PetResponse;
import hackaton.pethelp2.datasource.models.Response;
import hackaton.pethelp2.datasource.models.Support;
import hackaton.pethelp2.datasource.models.SupportResponse;
import hackaton.pethelp2.datasource.network.ImageUpload;
import retrofit2.Call;
import retrofit2.Callback;

public class EventAddView extends AppCompatActivity implements OnMapReadyCallback {

    private double lat;
    private double lng;

    private Context mContext;

    private GoogleMap mMap;
    private ImageView mPhoto;
    private Bitmap mBitmap;
    private EditText mDescription;
    private Uri mImageUri;
    private EditText mPetOtherName;
    private SupportViewAdapter mAdapter;
    private ArrayList<Pet> mPets;

    private ImageView btnPetDog;
    private ImageView btnPetCat;
    private ImageView btnPetOther;
    private ProgressBar mProgressBar;
    private ProgressDialog mProgressDialog;

    private int petType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_add_view);

        MyApplication.getInstance().getMainApiInterface().getPetList(MyApplication.getInstance()
                .getApiKey()).enqueue(new Callback<PetResponse>() {
            @Override
            public void onResponse(Call<PetResponse> call, retrofit2.Response<PetResponse> response) {
                mPets = (ArrayList<Pet>)response.body().getData();
                if (mPets != null) {
                    Picasso.get().load(MyApplication.getInstance().getImgUrl() + mPets.get(0).getImage().getPath())
                            .into(btnPetDog);
                    Picasso.get().load(MyApplication.getInstance().getImgUrl() + mPets.get(1).getImage().getPath())
                            .into(btnPetCat);
                    Picasso.get().load(MyApplication.getInstance().getImgUrl() + mPets.get(2).getImage().getPath())
                            .into(btnPetOther);
                }
            }

            @Override
            public void onFailure(Call<PetResponse> call, Throwable t) {
                Log.d("GET_PETS", t.toString());
            }
        });

        MyApplication.getInstance().getMainApiInterface().getSupportList(MyApplication.getInstance()
                .getApiKey()).enqueue(new Callback<SupportResponse>() {
            @Override
            public void onResponse(Call<SupportResponse> call, retrofit2.Response<SupportResponse> response) {
                Log.d("SUPPORT_LIST", response.body().toString());
                MyApplication.getInstance().setSupportList((ArrayList<Support>)response.body().getData());
                if (mAdapter != null) mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<SupportResponse> call, Throwable t) {
                Log.d("SUPPORT_LIST", t.toString());
            }
        });

        mDescription = (EditText)findViewById(R.id.description);
        mPhoto = (ImageView)findViewById(R.id.event_photo);
        mProgressBar = findViewById(R.id.progress_bar);
        mBitmap = null;
        mPhoto.setImageDrawable(getResources().getDrawable(R.drawable.add_photo));
        mContext = this;
        lat = getIntent().getDoubleExtra("lat", 0);
        lng = getIntent().getDoubleExtra("lng", 0);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_add_event);
        mapFragment.getMapAsync(this);



        mPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBitmap == null) addPhoto();
                else {
                    deletePhoto();
                }
            }
        });

        Button btnUploadEvent = (Button)findViewById(R.id.btn_upload_event);
        btnUploadEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBitmap == null) {
                    Toast.makeText(mContext, "Прикрепите фото", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.d("IMAGE_UPLOAD", "START");
                mProgressDialog = new ProgressDialog(mContext);
                mProgressDialog.show();
                uploadImage();
            }
        });

        mPetOtherName = (EditText)findViewById(R.id.pet_other_name);
        btnPetDog = (ImageView)findViewById(R.id.btn_pet_dog);
        btnPetCat = (ImageView)findViewById(R.id.btn_pet_cat);
        btnPetOther = (ImageView)findViewById(R.id.btn_pet_other);

        btnPetCat.setAlpha((float)0.5);
        btnPetOther.setAlpha((float)0.5);
        btnPetOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mPetOtherName.setVisibility(View.VISIBLE);
                btnPetCat.setAlpha((float)0.5);
                btnPetDog.setAlpha((float)0.5);
                btnPetOther.setAlpha((float)1);
                petType = 2;
            }
        });
        btnPetDog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mPetOtherName.setVisibility(View.GONE);
                btnPetCat.setAlpha((float)0.5);
                btnPetOther.setAlpha((float)0.5);
                btnPetDog.setAlpha((float)1);
                petType = 0;
            }
        });
        btnPetCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mPetOtherName.setVisibility(View.GONE);
                btnPetDog.setAlpha((float)0.5);
                btnPetOther.setAlpha((float)0.5);
                btnPetCat.setAlpha((float)1);
                petType = 1;
            }
        });

        mAdapter = new SupportViewAdapter();
        RecyclerView supports = (RecyclerView)findViewById(R.id.support_recycler_view);
        supports.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        supports.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        lat = getIntent().getDoubleExtra("lat", 0);
        lng = getIntent().getDoubleExtra("lng", 0);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        Log.d("COORDINATE", "lat: " + lat + ", lng: "+ lng);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(lat, lng)).zoom(18).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mMap.animateCamera(cameraUpdate);
        mMap.getUiSettings().setAllGesturesEnabled(false);
    }

    private final int ACTION_REQUEST_GALLERY = 1;
    private final int ACTION_REQUEST_CAMERA = 2;

    private void addPhoto() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(new CharSequence[] {"Выбрать из галереи", "Сфотографировать"},
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(pickPhoto , ACTION_REQUEST_GALLERY);
                                break;
                            case 1:
                                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(takePicture, ACTION_REQUEST_CAMERA);
                                break;
                            default:
                                break;
                        }
                    }
                });
        builder.show();
    }

    private void deletePhoto() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(new CharSequence[] {"Выбрать другую", "Удалить"},
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                addPhoto();
                                break;
                            case 1:
                                mPhoto.setImageDrawable(getResources().getDrawable(R.drawable.add_photo));
                                mBitmap = null;
                                break;
                            default:
                                break;
                        }
                    }
                });
        builder.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK)    {

            switch (requestCode) {
                case ACTION_REQUEST_GALLERY:
                    mImageUri = data.getData();
                    try {
                        mBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mImageUri);
                    } catch (IOException e) {}
                    mPhoto.setImageBitmap(mBitmap);
                    break;

                case ACTION_REQUEST_CAMERA:
                    mImageUri = data.getData();
                    Bundle extras = data.getExtras();
                    mBitmap = (Bitmap) extras.get("data");
                    mPhoto.setImageBitmap(mBitmap);
                    break;
            }

        }
    }

    private void uploadImage() {
        Call<Response> call = MyApplication.getInstance().getMainApiInterface()
                .imageUpload(new ImageUpload(mBitmap), MyApplication.getInstance().getApiKey());
        Log.d("IMAGE_UPLOAD", "call: " + call.toString());
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                Log.d("IMAGE_UPLOAD", response.body().toString());
                String path = response.body().getData().getPath();
                createEvent(path);
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.d("IMAGE_UPLOAD", t.toString());
            }
        });
    }

    private void createEvent(String imagePath) {
        Event ev = new Event();
        if (mPets != null) {
            Pet toSend = new Pet();
            toSend.setId(mPets.get(petType).getId());
            ev = new Event(lat, lng, toSend, mDescription.getText().toString(), new ImageMy(imagePath));
        }
        Call<EventResponse> eventCall = MyApplication.getInstance().getMainApiInterface()
                .createEvent(ev, MyApplication.getInstance().getApiKey());
        eventCall.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, retrofit2.Response<EventResponse> response) {
                Log.d("CREATE_EVENT", "response: " + response.body().toString());
                int event_id = response.body().getData().getId();
                mProgressDialog.dismiss();
                startActivity(new Intent(mContext, EventFinish.class).putExtra("event_id", event_id));
                finish();
            }

            @Override
            public void onFailure(Call<EventResponse> call, Throwable t) {
                Log.d("CREATE_EVENT", "error: " + t.toString());
            }
        });
    }

}
