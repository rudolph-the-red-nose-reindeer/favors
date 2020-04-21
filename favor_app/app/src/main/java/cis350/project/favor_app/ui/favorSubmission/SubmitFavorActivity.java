package cis350.project.favor_app.ui.favorSubmission;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import cis350.project.favor_app.R;

public class SubmitFavorActivity extends AppCompatActivity {
    private String details;
    private String urgency;
    private String longitude;
    private String latitude;
    private EditText detailsText;
    private EditText urgencyText;
    private EditText longitudeText;
    private EditText latitudeText;

    private Button createFavorBtnTwo;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favor);
        detailsText = findViewById(R.id.etDetails);
        urgencyText = findViewById(R.id.etUrgency);
        longitudeText = findViewById(R.id.etLongitude);
        latitudeText = findViewById(R.id.etLatitude);

        // Temp for now (to show deletion), to be updated when merging

        userId = getIntent().getStringExtra("userId");

        createFavorBtnTwo = findViewById(R.id.btCreateFavorTwo);

        createFavorBtnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                details = detailsText.getText().toString();
                urgency = urgencyText.getText().toString();
                longitude = longitudeText.getText().toString();
                latitude = latitudeText.getText().toString();
                if (!validUrgency(urgency)) {
                    updateUrgency();
                    return;
                }
                WebFavorCreationTask favorTask = new WebFavorCreationTask();
                favorTask.userId = userId;
                favorTask.execute(urgency, longitude, latitude, details);
                try {

                    String res = favorTask.get();
                    JSONObject resObj = new JSONObject(res);

                    String idToDelete = resObj.getString("_id");

                    Toast.makeText(SubmitFavorActivity.this, "Successfully Registered",
                            Toast.LENGTH_LONG).show();
                    Intent i = new Intent(SubmitFavorActivity.this, CreatedFavorActivity.class);
                    Log.d("idToDelete", idToDelete);
                    i.putExtra("idToDelete", idToDelete);
                    startActivity(i);
                    SubmitFavorActivity.this.finish();

                } catch (Exception e) {
                    Log.d("Register", e.toString());
                    failure();
                }
            }


        });

    }

    private void updateUrgency() {
        urgencyText.setHint("urgency is a number between 1 to 10");
        Toast.makeText(SubmitFavorActivity.this, "Favor Creation Failed",
                Toast.LENGTH_LONG).show();
    }

    private boolean validUrgency(String urgency) {
        boolean isValidInteger = false;
        try {
            Integer.parseInt(urgency);
            isValidInteger = true;
        } catch (NumberFormatException ex) {
        }
        return isValidInteger;
    }


    private void failure() {
        detailsText.setHint("Details");
        urgencyText.setHint("Urgency");
        longitudeText.setText("Longitude");
        latitudeText.setText("Latitude");
        Toast.makeText(SubmitFavorActivity.this, "Favor Creation Failed",
                Toast.LENGTH_LONG).show();
    }

}
