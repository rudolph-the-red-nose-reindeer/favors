package cis350.project.favor_app.ui.notifications;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import java.util.LinkedList;
import java.util.List;

import cis350.project.favor_app.R;
import cis350.project.favor_app.data.model.Favor;
import cis350.project.favor_app.ui.favorFeed.FavorListAdapter;
import cis350.project.favor_app.ui.favorFeed.Grouper;

public class UserFavorsFragment extends Fragment {
    private String userId;
    private boolean submittedView;
    private int spinnerId;
    private int userListId;

    public UserFavorsFragment(boolean sub, String userId) {
        submittedView = sub;
        this.userId = userId;
        if (submittedView) {
            spinnerId = R.id.user_favors_submitted_feed_spinner;
            userListId = R.id.user_favors_submitted_feed_list;
        } else {
            spinnerId = R.id.user_favors_undertaken_feed_spinner;
            userListId = R.id.user_favors_undertaken_feed_list;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceBundle) {

        if (submittedView) {
            return (ViewGroup) inflater.inflate(
                R.layout.user_favors_submitted_view, container, false);
        } else {
            return (ViewGroup) inflater.inflate(
                    R.layout.user_favors_undertaken_view, container, false);
        }
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceBundle) {
        Spinner spinner = (Spinner) v.findViewById(spinnerId);
        //  // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(v.getContext(),
                R.array.favor_feed_sort_values, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String sortBy = spinner.getSelectedItem().toString();
                Log.d("spinner selection:", sortBy);

                List<Favor> favors;

                if (submittedView) {
                    favors = Grouper.getFirstNFavorsSubmittedByUser(25, userId, sortBy);
                } else {
                    favors = Grouper.getFirstNFavorsAcceptedByUser(25, userId, sortBy);
                }

                if (favors == null) {
                    favors = new LinkedList<Favor>();
                }

                final ListView lv = (ListView) v.findViewById(userListId);
                Log.d("listview?", String.valueOf(lv == null));
                lv.setAdapter(new FavorListAdapter(v.getContext(), favors));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
}
