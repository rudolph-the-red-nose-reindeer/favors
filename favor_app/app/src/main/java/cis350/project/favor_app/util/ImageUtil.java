package cis350.project.favor_app.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class ImageUtil {
    public static String encodeBase64(Bitmap photo) {
        try {
            photo = Bitmap.createScaledBitmap(photo, 250, 250, false);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(bytes, Base64.DEFAULT);
        } catch (Exception e) {
            return "";
        }

    }

    public static Bitmap decodeBase64(String input) {
        try {
            byte[] bytes = Base64.decode(input, Base64.DEFAULT);
            Bitmap photo = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            photo = Bitmap.createScaledBitmap(photo, 250, 250, false);
            return photo;
        } catch (Exception e) {
            return null;
        }

    }
}
