package cis350.project.favor_app.util;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import cis350.project.favor_app.data.model.Location;

public class LocationUtil {
    private static FusedLocationProviderClient client;

    public static Location getLastLocation(Activity context) {
        final Location[] loc = new Location[1];
        client = LocationServices.getFusedLocationProviderClient(context);
        client.getLastLocation().addOnSuccessListener(context, new OnSuccessListener<android.location.Location>() {
            @Override
            public void onSuccess(android.location.Location location) {
                Log.d("location util pinging location", "success");
                if (location != null) {
                    Log.d("location util pinging location", "not null");

                    loc[0] = new Location(location.getLatitude(), location.getLongitude());
                }
            }
        });

        return loc[0];
    }
}
