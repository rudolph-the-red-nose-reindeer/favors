package cis350.project.favor_app.ui.map;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.Toast;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.HashSet;

public class MapsActivity extends FragmentActivity implements
        GoogleMap.OnMarkerClickListener,
        OnMapReadyCallback {

    private GoogleMap mMap;
    public static final int ID = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /** Called when the map is ready. */
    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        FavorDatabase db = FavorDatabase.getInstance();
        HashSet<Favor> allFavors = db.getAllFavors();


        for (Favor f : allFavors) {
            double lat = f.getLat();
            double lon = f.getLon();
            LatLng place = new LatLng(lat, lon);
            int urgency = f.getUrgency();
            float color = 0;
            if (urgency >= 4) {
                color = 0;
            } else if (urgency >= 2) {
                color = 30;
            } else {
                color = 120;
            }
            Marker m = mMap.addMarker(new MarkerOptions()
                    .position(place)
                    .icon(BitmapDescriptorFactory.defaultMarker(color)));
            m.setTag(f);

        }
        mMap.setOnMarkerClickListener(this);
    }


    /** Called when the user clicks a marker. */
    @Override
    public boolean onMarkerClick(final Marker marker) {

        Favor f = (Favor) marker.getTag();

        Toast.makeText(this,
                f.getDetails(),
                Toast.LENGTH_LONG).show();
        return false;
    }


}


