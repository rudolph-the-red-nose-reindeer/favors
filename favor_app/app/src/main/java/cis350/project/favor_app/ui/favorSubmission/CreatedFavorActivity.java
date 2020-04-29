package cis350.project.favor_app.ui.favorSubmission;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cis350.project.favor_app.R;
import cis350.project.favor_app.data.database.FavorDatabase;
import cis350.project.favor_app.data.model.Favor;

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
                Favor result = FavorDatabase.getInstance().deleteFavorFromDatabase(idToDelete);

                if (result == null) {
                    Toast.makeText(CreatedFavorActivity.this, "Favor Not Deleted",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(CreatedFavorActivity.this, "Successfully Deleted Favor",
                            Toast.LENGTH_LONG).show();
                    CreatedFavorActivity.this.finish();
                }
            }

        });


    }

}
