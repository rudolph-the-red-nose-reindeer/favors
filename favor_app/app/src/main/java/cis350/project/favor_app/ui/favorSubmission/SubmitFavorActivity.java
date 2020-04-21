package cis350.project.favor_app.ui.favorSubmission;

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

import cis350.project.favor_app.R;

public class SubmitFavorActivity extends AppCompatActivity {
    private String details;
    private String urgency;
    private String location;
    private EditText detailsText;
    private EditText urgencyText;
    private EditText locationText;
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

        category = spinner.getSelectedItem().toString();
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
                details = detailsText.getText().toString();
                urgency = urgencyText.getText().toString();
                location = locationText.getText().toString();
                Toast.makeText(SubmitFavorActivity.this, "Category : " + category,
                        Toast.LENGTH_LONG).show();
                if ((details == null) || (urgency == null) || (location == null)) {
                    failure();
                }
                if (!validUrgency(urgency)) {
                    updateUrgency();
                    return;
                }
                WebFavorCreationTask favorTask = new WebFavorCreationTask();
                favorTask.userId = userId;
                favorTask.execute(urgency, location, details, category);
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
        Toast.makeText(SubmitFavorActivity.this, "Favor Creation Failed, incorrect urgency",
                Toast.LENGTH_LONG).show();
    }

    private boolean validUrgency(String urgency) {
        boolean isValidInteger = false;
        try {
            int num = Integer.parseInt(urgency);
            for (int i = 1; i < 11; i++) {
                if (num == i) {
                    isValidInteger = true;
                }
            }
        } catch (NumberFormatException ex) {
        }
        return isValidInteger;
    }


    private void failure() {
        detailsText.setHint("Details");
        urgencyText.setHint("Urgency");
        locationText.setText("Location");
        Toast.makeText(SubmitFavorActivity.this, "Favor Creation Failed",
                Toast.LENGTH_LONG).show();
    }

}
