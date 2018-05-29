package hackaton.pethelp2.datasource.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;

import hackaton.pethelp2.MyApplication;

/**
 * Created by dmitriyt on 24.03.18.
 */

public class ImageUpload implements Serializable {
    public String image;

    public ImageUpload(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();

        image = Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

}