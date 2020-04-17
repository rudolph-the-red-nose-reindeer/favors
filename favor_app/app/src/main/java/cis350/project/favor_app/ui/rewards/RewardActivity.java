package cis350.project.favor_app.ui.rewards;

import android.app.Activity;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import cis350.project.favor_app.R;
import cis350.project.favor_app.data.database.RewardDatabase;
import cis350.project.favor_app.data.database.UserDatabase;
import cis350.project.favor_app.data.model.Reward;
import cis350.project.favor_app.data.model.User;

public class RewardActivity extends Activity {

    private User user;

    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.activity_reward_redemption);

        Log.d("userId", getIntent().getStringExtra("userId"));

        user = UserDatabase.getInstance().findUserById(
                getIntent().getStringExtra("userId"));

        Spinner spinner = (Spinner) findViewById(R.id.reward_view_spinner);
        ListView listView = (ListView) findViewById(R.id.reward_view_list);

        RewardsListAdapter adapter = new RewardsListAdapter(this,
                RewardDatabase.getInstance().getAllRewards());

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Reward reward = adapter.getItem(i);
                int userPoints = user.getPoints();

                if (reward.getPoints() <= userPoints) {
                    user.setPoints(userPoints - reward.getPoints());
                    RewardDatabase.getInstance().assignUserReward(user.getUserId(), reward.getRewardId());
                    UserDatabase.getInstance().updateUser(user);
                }

            }
        });
    }

}
