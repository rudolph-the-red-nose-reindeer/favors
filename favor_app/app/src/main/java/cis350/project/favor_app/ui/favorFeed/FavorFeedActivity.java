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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;

import cis350.project.favor_app.R;
import cis350.project.favor_app.data.database.UserDatabase;
import cis350.project.favor_app.data.model.Favor;
import cis350.project.favor_app.data.model.User;
import cis350.project.favor_app.ui.chat.ChatActivity;


public class FavorFeedActivity extends AppCompatActivity {
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
    }

    public void onButtonClick(View v) {
        Spinner spinner = (Spinner)findViewById(R.id.favor_feed_spinner);
        String sortBy = spinner.getSelectedItem().toString();
        Comparator<Favor> c;
        //Log.d("hihihi", "jijai");
        if (sortBy.equals("Urgency")) {
            c = new Sorter().new UrgencyComparator<>();
        } else if (sortBy.equals("Username")) {
            c = null;
        } else {
            c = new Sorter().new DateComparator();
        }
        ArrayList<FavorListItem> listItemsFavorList = new ArrayList<>();
        LinkedHashMap<Favor, User> favorToUser = Grouper.getFirstNFavors(25, c);
        if (c != null) {
            for (Favor f : favorToUser.keySet()) {
                User u = favorToUser.get(f);
                FavorListItem li = new FavorListItem(u.getUsername(), f.getDetails(), "" +
                        f.getUrgency(), f.getDate());
                listItemsFavorList.add(li);
            }
        } else {
            ArrayList<User> userList = new ArrayList<User>();
            LinkedHashMap<User, Favor> userToFavor = new LinkedHashMap<>();
            for (Favor f : favorToUser.keySet()) {
                User u = favorToUser.get(f);
                userList.add(u);
                userToFavor.put(u, f);
            }
            Collections.sort(userList, new Comparator<User>() {
                @Override
                public int compare(User user, User t1) {
                    return user.getUsername().compareTo(t1.getUsername());
                }
            });
            for (User u : userList) {
                Favor f = userToFavor.get(u);
                FavorListItem li = new FavorListItem(u.getUsername(), f.getDetails(), "" +
                        f.getUrgency(), f.getDate());
                listItemsFavorList.add(li);
            }
        }
        final ListView lv = (ListView) findViewById(R.id.favor_feed_user_list);
        lv.setAdapter(new CustomListAdapter(this, listItemsFavorList));
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
}
