package cis350.project.favor_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private Button create_favor_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        create_favor_btn = (Button) findViewById(R.id.btCreateNewFavorOne);
        create_favor_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateFavorActivity();
            }
        });

    }

    public void openCreateFavorActivity() {
        Intent intent = new Intent(this, FavorActivity.class);
        startActivity(intent);
    }



}
