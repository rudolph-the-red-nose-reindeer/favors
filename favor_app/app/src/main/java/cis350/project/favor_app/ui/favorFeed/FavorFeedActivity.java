package cis350.project.favor_app.ui.favorFeed;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import cis350.project.favor_app.R;
import cis350.project.favor_app.data.model.Favor;
import cis350.project.favor_app.ui.chat.ChatActivity;
import cis350.project.favor_app.ui.map.MapsActivity;


public class FavorFeedActivity extends AppCompatActivity {
    private static final int ID = 1;
    private FavorFeedActivity self = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favor_feed);

        Spinner spinner = (Spinner) findViewById(R.id.favor_feed_spinner);
        //  // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.favor_feed_sort_values, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String sortBy = spinner.getSelectedItem().toString();
                List<Favor> favors = Grouper.getFirstNFavors(25, sortBy);

                final ListView lv = (ListView) findViewById(R.id.favor_feed_user_list);
                lv.setAdapter(new FavorListAdapter(self, favors));
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                        Intent intent = new Intent(self, ChatActivity.class);
                        FavorListItem clickedOnUser = (FavorListItem) adapterView.getItemAtPosition(pos);
                        intent.putExtra("OTHER_USERNAME", clickedOnUser.getUsername());
                        intent.putExtra("CURR_USERNAME", getIntent().getStringExtra("CURR_USERNAME"));//UserDatabase.getInstance().
                        //findUserById(CURR_ID).getUsername());
                        Log.e("got here", "Just yerp checking babe");
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    public void mapClick(View v) {
        Intent i = new Intent(this, MapsActivity.class);
        startActivityForResult(i, ID);
    }
}
