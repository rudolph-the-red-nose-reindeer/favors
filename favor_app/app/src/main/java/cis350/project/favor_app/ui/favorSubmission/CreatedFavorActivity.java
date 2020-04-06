package cis350.project.favor_app.ui.favorSubmission;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cis350.project.favor_app.R;

public class CreatedFavorActivity extends AppCompatActivity {
    private Button createDeleteBtn;
    private String idToDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_created_favor);

        idToDelete = getIntent().getStringExtra("idToDelete");

        createDeleteBtn = findViewById(R.id.btDeleteFavor);

        createDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WebFavorDeletionTask favorTask = new WebFavorDeletionTask();
                favorTask.idToDelete = idToDelete;
                favorTask.execute();
                try {
                    Toast.makeText(CreatedFavorActivity.this, "Successfully Deleted Favor",
                            Toast.LENGTH_LONG).show();
                    Intent i = new Intent(CreatedFavorActivity.this, SubmitFavorActivity.class);
                    startActivity(i);
                    CreatedFavorActivity.this.finish();

                } catch (Exception e) {
                    Log.d("Register", e.toString());
                    Toast.makeText(CreatedFavorActivity.this, "Favor Not Deleted",
                            Toast.LENGTH_LONG).show();
                }
            }


        });


    }

}
