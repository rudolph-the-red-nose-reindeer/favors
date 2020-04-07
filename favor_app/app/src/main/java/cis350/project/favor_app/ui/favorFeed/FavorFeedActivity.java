package cis350.project.favor_app.ui.favorFeed;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;

import cis350.project.favor_app.R;


public class FavorFeedActivity extends AppCompatActivity {

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
        Log.d("hihihi", "jijai");
        if (sortBy.equals("Urgency")) {
            c = new Sorter().new UrgencyComparator<>();
        } else if (sortBy.equals("Username")) {
            c = null;
        } else {
            c = new Sorter().new DateComparator();
        }
        ArrayList<ListItem> listItemsList = new ArrayList<>();
        LinkedHashMap<Favor, User> favorToUser = Grouper.getFirstNFavors(25, c);
        if (c != null) {
            for (Favor f : favorToUser.keySet()) {
                User u = favorToUser.get(f);
                ListItem li = new ListItem(u.uname, f.fdetails, "" + f.furgency, f.fdate);
                listItemsList.add(li);
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
                    return user.uname.compareTo(t1.uname);
                }
            });
            for (User u : userList) {
                Favor f = userToFavor.get(u);
                ListItem li = new ListItem(u.uname, f.fdetails, "" + f.furgency, f.fdate);
                listItemsList.add(li);
            }
        }
        final ListView lv = (ListView) findViewById(R.id.favor_feed_user_list);
        lv.setAdapter(new CustomListAdapter(this, listItemsList));
    }
}
