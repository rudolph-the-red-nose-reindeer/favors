package cis350.project.favor_app.ui.favorSearch;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import cis350.project.favor_app.R;
import cis350.project.favor_app.data.database.FavorDatabase;
import cis350.project.favor_app.data.database.UserDatabase;
import cis350.project.favor_app.data.model.Favor;
import cis350.project.favor_app.data.model.User;
import cis350.project.favor_app.ui.favorFeed.Sorter;

public class favorSearchFeed extends AppCompatActivity {
    String searchCat = "";
    TextView favor_filter_results_final_tv;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favor_filter_results);

        List<Favor> allFavorList = FavorDatabase.getInstance().getAllFavors("Urgency");
        List<Favor> outList = new ArrayList<Favor>();
//        Log.d("check favorToUser", "favorUsers: " + favorToUser.toString());
        Bundle incomingMessages = getIntent().getExtras();
        favor_filter_results_final_tv = (TextView) findViewById(R.id.favor_filter_results_final_tv);

        if (incomingMessages != null) {
            searchCat = incomingMessages.getString("searchcategory");

        }
        if (check(searchCat)) {
            String messageText = "Category: " + searchCat;
            favor_filter_results_final_tv.setText(messageText);
            for (Favor f : allFavorList) {
                String category =  f.getCategory();
                if (category.equals(searchCat)) {
                    outList.add(f);
                }
            }
        } else {
            String messageText = "Search Results for: " + searchCat;
            favor_filter_results_final_tv.setText(messageText);
            for (Favor f : allFavorList) {
                String details = f.getDetails();
                if (details.toLowerCase().contains(searchCat.toLowerCase())) {
                    outList.add(f);
                }
            }
        }

//            Log.d("check_ii", "listItemsFavorList: " + listItemsFavorList.toString());
            if (outList.size() == 0) {
                Toast.makeText(favorSearchFeed.this, "Sorry! No results for query found!", Toast.LENGTH_LONG).show();
            }
            final ListView lv = (ListView) findViewById(R.id.favor_filter_results_list_lv);
            ListAdapter adapter = new CustomListAdapterFavorSearch(this, outList);
            lv.setAdapter(adapter);
        }


        private LinkedHashMap<Favor, User> getFavorsUsers () {
            FavorDatabase favorDb = FavorDatabase.getInstance();
            UserDatabase userDb = UserDatabase.getInstance();
            List<Favor> allFavors = favorDb.getAllFavors("Urgency");

            if (allFavors == null) {
                Toast.makeText(favorSearchFeed.this, "Error: There are Favors that are missing field values", Toast.LENGTH_LONG).show();
                return new LinkedHashMap<Favor, User>();
            }
            ArrayList<Favor> allFavorsList = new ArrayList<Favor>();
            for (Favor f : allFavors) {
                allFavorsList.add(f);
            }
            Collections.sort(allFavorsList, Sorter.getInstance().getUrgencyComparator());
            LinkedHashMap<Favor, User> favorToUser = new LinkedHashMap<Favor, User>();
            for (Favor f : allFavorsList) {
                User u = userDb.findUserById(f.getUserId());
                if (u == null) {
                    continue;
                }
                favorToUser.put(f, u);
            }
            if (favorToUser.isEmpty()) {
                return new LinkedHashMap<Favor, User>();
            }
            return favorToUser;
        }


        private boolean check (String searchCat){
            ArrayList<String> categoryStrings = new ArrayList<String>();
            categoryStrings.add("Home and Cleaning");
            categoryStrings.add("Drinks");
            categoryStrings.add("Booze Runs");
            categoryStrings.add("Grocery delivery");
            categoryStrings.add("Food delivery");
            categoryStrings.add("Pharmacy delivery");
            categoryStrings.add("Pet items");
            categoryStrings.add("Stationary and Electronic items");
            categoryStrings.add("Quick Snacks and Meals");
            return categoryStrings.contains(searchCat);

        }
    }