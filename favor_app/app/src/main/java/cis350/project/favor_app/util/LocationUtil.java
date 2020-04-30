package cis350.project.favor_app.util;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.util.TimeZone;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import cis350.project.favor_app.data.model.Location;



public class LocationUtil {
    public interface LocationCallback {
        void onSuccess(Location location);
        void onFailure(Exception e);
    }

    private static FusedLocationProviderClient client;

    public static void getLastLocation(Activity context, LocationCallback callback) {
        client = LocationServices.getFusedLocationProviderClient(context);

        client.getLastLocation()
            .addOnSuccessListener(context,
                    new OnSuccessListener<android.location.Location>() {
                        @Override
                        public void onSuccess(android.location.Location location) {
                            Log.d("location util pinging location", "success");
                            if (location != null) {
                                Log.d("location util pinging location", "not null");
                                callback.onSuccess(new Location(location.getLatitude(),
                                        location.getLongitude()));
                            }
                        }
                    }).addOnFailureListener(context, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onFailure(e);
            }
        });
    }
}
