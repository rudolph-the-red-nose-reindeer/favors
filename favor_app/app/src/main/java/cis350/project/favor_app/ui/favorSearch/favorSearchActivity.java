package cis350.project.favor_app.ui.favorSearch;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cis350.project.favor_app.R;
import  cis350.project.favor_app.ui.favorFeed.*;
import cis350.project.favor_app.ui.favorSubmission.CreatedFavorActivity;
import cis350.project.favor_app.ui.favorSubmission.SubmitFavorActivity;
import cis350.project.favor_app.ui.favorSubmission.WebFavorDeletionTask;

import androidx.appcompat.app.AppCompatActivity;

public class favorSearchActivity extends AppCompatActivity {
    Button home_and_cleaning_favor_btn;
    Button drinks_btn;
    Button booze_btn;
    Button grocery_btn;
    Button food_btn;
    Button pharmacy_btn;
    Button pet_btn;
    Button stationary_items_btn;
    Button snacks_btn;
    Button find_favor_search_btn;
    TextView find_favor_search_et;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favor_filter);
        setViews();
        home_and_cleaning_favor_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(favorSearchActivity.this, favorSearchFeed.class);
                String message = "Home and Cleaning";
                i.putExtra("searchcategory", message);
                Toast.makeText(favorSearchActivity.this, "category:" + message,
                        Toast.LENGTH_LONG).show();
                startActivity(i);
                favorSearchActivity.this.finish();

            }
    });
        drinks_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(favorSearchActivity.this, favorSearchFeed.class);
                String message = "Drinks";
                i.putExtra("searchcategory", message);
                Toast.makeText(favorSearchActivity.this, "category:" + message,
                        Toast.LENGTH_LONG).show();
                startActivity(i);
                favorSearchActivity.this.finish();

            }
        });
        booze_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(favorSearchActivity.this, favorSearchFeed.class);
                String message = "Booze Runs";
                i.putExtra("searchcategory", message);
                Toast.makeText(favorSearchActivity.this, "category:" + message,
                        Toast.LENGTH_LONG).show();
                startActivity(i);
                favorSearchActivity.this.finish();
            }
        });
        grocery_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(favorSearchActivity.this, favorSearchFeed.class);
                String message = "Grocery delivery";
                i.putExtra("searchcategory", message);
                Toast.makeText(favorSearchActivity.this, "category:" + message,
                        Toast.LENGTH_LONG).show();
                startActivity(i);
                favorSearchActivity.this.finish();

            }
        });
        food_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(favorSearchActivity.this, favorSearchFeed.class);
                String message = "Food delivery";
                i.putExtra("searchcategory", message);
                Toast.makeText(favorSearchActivity.this, "category:" + message,
                        Toast.LENGTH_LONG).show();
                startActivity(i);
                favorSearchActivity.this.finish();

            }
        });
        pharmacy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(favorSearchActivity.this, favorSearchFeed.class);
                String message = "Pharmacy delivery";
                i.putExtra("searchcategory", message);
                Toast.makeText(favorSearchActivity.this, "category:" + message,
                        Toast.LENGTH_LONG).show();
                startActivity(i);
                favorSearchActivity.this.finish();
            }
        });
        pet_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(favorSearchActivity.this, favorSearchFeed.class);
                String message = "Pet items";
                i.putExtra("searchcategory", message);
                Toast.makeText(favorSearchActivity.this, "category:" + message,
                        Toast.LENGTH_LONG).show();
                startActivity(i);
                favorSearchActivity.this.finish();
            }
        });
        stationary_items_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(favorSearchActivity.this, favorSearchFeed.class);
                String message = "Stationary and Electronic items";
                i.putExtra("searchcategory", message);
                Toast.makeText(favorSearchActivity.this, "category:" + message,
                        Toast.LENGTH_LONG).show();
                startActivity(i);
                favorSearchActivity.this.finish();

            }
        });
        snacks_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(favorSearchActivity.this, favorSearchFeed.class);
                String message = "Quick Snacks and Meals";
                i.putExtra("searchcategory", message);
                Toast.makeText(favorSearchActivity.this, "category:" + message,
                        Toast.LENGTH_LONG).show();
                startActivity(i);
                favorSearchActivity.this.finish();
            }
        });
        String searchMessage = find_favor_search_et.getText().toString();
        find_favor_search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchMessage = find_favor_search_et.getText().toString();
                Intent i = new Intent(favorSearchActivity.this, favorSearchFeed.class);
                i.putExtra("searchcategory", searchMessage);
                Toast.makeText(favorSearchActivity.this, searchMessage,
                        Toast.LENGTH_LONG).show();
                Log.d("message:","Value :" + searchMessage);
                startActivity(i);
                favorSearchActivity.this.finish();
            }
        });

    }



    public void setViews() {
        home_and_cleaning_favor_btn = (Button) findViewById(R.id.home_and_cleaning_favor_btn);
        drinks_btn = (Button) findViewById(R.id.drinks_btn);
        booze_btn = (Button) findViewById(R.id.booze_btn);
        grocery_btn = (Button) findViewById(R.id.grocery_btn);
        food_btn = (Button) findViewById(R.id.food_btn);
        pharmacy_btn = (Button) findViewById(R.id.pharmacy_btn);
        pet_btn = (Button) findViewById(R.id.pet_btn);
        stationary_items_btn = (Button) findViewById(R.id.stationary_items_btn);
        snacks_btn = (Button) findViewById(R.id.snacks_btn);
        find_favor_search_btn = (Button) findViewById(R.id.find_favor_search_btn);
        find_favor_search_et = (EditText) findViewById(R.id.find_favor_search_et);
    }
}