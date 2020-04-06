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
import android.widget.Toast;

public class CreatedFavorActivity extends AppCompatActivity {
    private Button createDeleteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_created_favor);

        createDeleteBtn = findViewById(R.id.btDeleteFavor);

        createDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WebFavorDeletionTask favorTask = new WebFavorDeletionTask();
                favorTask.execute();
                try {

                    Toast.makeText(CreatedFavorActivity.this, "Successfully Deleted Favor",
                            Toast.LENGTH_LONG).show();
                    Intent i = new Intent(CreatedFavorActivity.this, MainActivity.class);
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
