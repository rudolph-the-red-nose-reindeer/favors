package cis350.project.favor_app;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FavorActivity extends AppCompatActivity {
    private String details;
    private String urgency;
    private String location;
    private EditText detailsText;
    private EditText urgencyText;
    private EditText locationText;
    private Button createFavorBtnTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favor);
        detailsText = findViewById(R.id.etDetails);
        urgencyText = findViewById(R.id.etUrgency);
        locationText = findViewById(R.id.etLocation);
        // Temp for now (to show deletion), to be updated when merging


        createFavorBtnTwo = findViewById(R.id.btCreateFavorTwo);


        createFavorBtnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                details = detailsText.getText().toString();
                urgency = urgencyText.getText().toString();
                location = locationText.getText().toString();
                if (!validUrgency(urgency)) {
                    updateUrgency();
                    return;
                }
                WebFavorCreationTask favorTask = new WebFavorCreationTask();
                favorTask.execute(urgency, location, details);
                try {

                    Toast.makeText(FavorActivity.this, "Successfully Registered",
                            Toast.LENGTH_LONG).show();
                    Intent i = new Intent(FavorActivity.this, CreatedFavorActivity.class);
                    startActivity(i);
                    FavorActivity.this.finish();

                } catch (Exception e) {
                    Log.d("Register", e.toString());
                    failure();
                }
            }


        });

    }

    private void updateUrgency() {
        urgencyText.setHint("urgency is a number between 1 to 10");
        Toast.makeText(FavorActivity.this, "Favor Creation Failed",
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
        locationText.setText("Location");
        Toast.makeText(FavorActivity.this, "Favor Creation Failed",
                Toast.LENGTH_LONG).show();
    }

}
