package cis350.project.favor_app.data.database;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import cis350.project.favor_app.Constants;
import cis350.project.favor_app.data.model.Reward;
import cis350.project.favor_app.util.JsonUtil;
import cis350.project.favor_app.util.requests.JSONArrayWebTask;
import cis350.project.favor_app.util.requests.JSONObjectWebTask;

public class RewardDatabase {

    private static RewardDatabase instance = null;

    private RewardDatabase() {}

    public static RewardDatabase getInstance() {
        if (instance == null)
            instance = new RewardDatabase();

        return instance;
    }

    // returns a list of all rewards, or an empty list if an error occurs
    public List<Reward> getAllRewards() {
        String connString = Constants.WEB_CONNECTION_STRING + "rewards/all";
        JSONObject requestBody = new JSONObject();

        List<Reward> list = new LinkedList<Reward>();

        try {
            JSONArrayWebTask task = new JSONArrayWebTask();

            task.setBody(requestBody);
            task.setConnString(connString);
            task.execute();

             JSONArray result = task.get();
             for (int i = 0; i < result.length(); i++) {
                 JSONObject obj = result.getJSONObject(i);
                 Reward reward = JsonUtil.getRewardFromJsonObject(obj);
                 if (reward != null) {
                     list.add(reward);
                 }
             }
            return list;
        } catch (Exception e) {
            Log.d("Error get all rewards", e.toString());
            return list;
        }
    }

    public List<Reward> getAllRewardsRedeemable(int points) {
        String connString = Constants.WEB_CONNECTION_STRING + "rewards/redeemable";
        JSONObject requestBody = new JSONObject();

        List<Reward> list = new LinkedList<Reward>();

        try {
            JSONArrayWebTask task = new JSONArrayWebTask();
            requestBody.put("points", points);

            task.setBody(requestBody);
            task.setConnString(connString);
            task.execute();

            JSONArray result = task.get();
            for (int i = 0; i < result.length(); i++) {
                JSONObject obj = result.getJSONObject(i);
                Reward reward = JsonUtil.getRewardFromJsonObject(obj);
                if (reward != null) {
                    list.add(reward);
                }
            }
            return list;
        } catch (Exception e) {
            Log.d("Error get rewards redeemable", e.toString());
            return list;
        }
    }

    public void assignUserReward(String userId, String rewardId) {
        String connString = Constants.WEB_CONNECTION_STRING + "givenrewards/create";
        JSONObject requestBody = new JSONObject();

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        Date now = new Date();
        String date = format.format(now);

        Log.d("Date formatted", date);

        try {
            JSONObjectWebTask task = new JSONObjectWebTask();
            requestBody.put("userId", userId);
            requestBody.put("rewardId", rewardId);
            requestBody.put("dateRedeemed", date);

            task.setBody(requestBody);
            task.setConnString(connString);
            task.execute();
        } catch (Exception e) {
            Log.d("Error get rewards redeemable", e.toString());
        }
    }
}
