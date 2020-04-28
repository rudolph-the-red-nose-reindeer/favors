package cis350.project.favor_app.ui.favorSubmission;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cis350.project.favor_app.R;
import cis350.project.favor_app.data.database.FavorDatabase;
import cis350.project.favor_app.data.model.Favor;
import cis350.project.favor_app.data.model.Location;
import cis350.project.favor_app.util.JsonUtil;
import cis350.project.favor_app.util.LocationUtil;

public class SubmitFavorActivity extends AppCompatActivity {
    private Activity self = this;
    private String details;
    private int urgency;
    private String location;
    private double longitude;
    private double latitude;
    private EditText detailsText;
    private EditText urgencyText;
    private EditText locationText;
    private EditText longitudeText;
    private EditText latitudeText;
    private TextView categoryText;

    private Button createFavorBtnTwo;
    private String userId;
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favor);

        Spinner spinner = (Spinner) findViewById(R.id.spinnerCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        categoryText = findViewById(R.id.etCategory);
        detailsText = findViewById(R.id.etDetails);
        urgencyText = findViewById(R.id.etUrgency);
        locationText = findViewById(R.id.etLocation);

        // Temp for now (to show deletion), to be updated when merging

        userId = getIntent().getStringExtra("userId");

        createFavorBtnTwo = findViewById(R.id.btCreateFavorTwo);

        createFavorBtnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
                Date now = new Date();
                String date = format.format(now);
                details = detailsText.getText().toString();
                urgency = Integer.parseInt(urgencyText.getText().toString());
                location = locationText.getText().toString();
                category = spinner.getSelectedItem().toString();
                Toast.makeText(SubmitFavorActivity.this, "Category : " + category,
                        Toast.LENGTH_LONG).show();
                if (details == null || location == null) {
                    failure();
                }
                if (!validUrgency(String.valueOf(urgency))) {
                    updateUrgency();
                    return;
                }

                LocationUtil.getLastLocation(SubmitFavorActivity.this, new LocationUtil.LocationCallback() {
                    @Override
                    public void onSuccess(Location loc) {
                        Favor newFavor = FavorDatabase.getInstance().addFavorToDatabase(userId, date,
                                urgency, location, loc.getLat(), loc.getLon(), details, category);

                        Log.d("plzwork", JsonUtil.getJsonObjectFromFavor(newFavor).toString());

                        if (newFavor == null) {
                            failure();
                        } else {
                            Toast.makeText(SubmitFavorActivity.this, "Successfully Registered",
                                    Toast.LENGTH_LONG).show();
                            Intent i = new Intent(SubmitFavorActivity.this, CreatedFavorActivity.class);
                            Log.d("idToDelete", newFavor.getFavorId());
                            i.putExtra("idToDelete", newFavor.getFavorId());
                            startActivity(i);
                        }
                    }

                    public void onFailure(Exception e) {
                        Log.d("Failed to submit favor", e.toString());
                        failure();
                    }
                });


            }
        });

    }

    private void updateUrgency() {
        urgencyText.setHint("urgency is a number between 1 to 10");
        Toast.makeText(SubmitFavorActivity.this, "Favor Creation Failed, incorrect urgency",
                Toast.LENGTH_LONG).show();
    }

    private boolean validUrgency(String urgency) {
        boolean isValidInteger = false;
        try {
            int num = Integer.parseInt(urgency);
            if (num <= 10 && num >= 1) {
                isValidInteger = true;
            }
        } catch (NumberFormatException ex) {
        }
        return isValidInteger;
    }


    private void failure() {
        detailsText.setHint("Details");
        urgencyText.setHint("Urgency");
        locationText.setHint("Location");
        Toast.makeText(SubmitFavorActivity.this, "Favor Creation Failed",
                Toast.LENGTH_LONG).show();
    }

}
