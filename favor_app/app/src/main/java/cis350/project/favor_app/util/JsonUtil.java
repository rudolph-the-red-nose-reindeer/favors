package cis350.project.favor_app.util;

import android.annotation.SuppressLint;
import android.util.Log;

import org.json.JSONObject;

import cis350.project.favor_app.data.model.Favor;
import cis350.project.favor_app.data.model.Reward;
import cis350.project.favor_app.data.model.User;

// utility for converting from JSON objects to Java classes
public class JsonUtil {

    @SuppressLint("LongLogTag")
    public static User getUserFromJsonObject(JSONObject obj) {
        try {
            String userId = obj.getString("_id");
            String username = obj.getString("username");
            String email = obj.getString("email");
            String photo = "";
            String bio = "";
            int rating = obj.getInt("rating");
            int points = obj.getInt("points");

            if (obj.has("photo")) {
                photo = obj.getString("photo");
            }

            if (obj.has("bio")) {
                bio = obj.getString("bio");
            }

            return new User(userId, username, email, photo, bio, rating, points);
        } catch (Exception e) {
            Log.d("Error converting JSON object to user", e.toString());
            return null;
        }
    }

    @SuppressLint("LongLogTag")
    public static JSONObject getJsonObjectFromUser(User user) {
        try {
            String userId = user.getUserId();
            String username = user.getUsername();
            String email = user.getEmail();
            String photo = user.getPhoto();
            String bio = user.getBio();
            int rating = user.getRating();
            int points = user.getPoints();

            JSONObject out = new JSONObject();
            out.put("id", userId);
            out.put("username", username);
            out.put("email", email);
            out.put("photo", photo);
            out.put("bio", bio);
            out.put("rating", rating);
            out.put("points", points);

            return out;
        } catch (Exception e) {
            Log.d("Error converting user to JSON object", e.toString());
            return null;
        }
    }

    @SuppressLint("LongLogTag")
    public static Favor getFavorFromJsonObject(JSONObject obj) {
        try {
            String favorId = obj.getString("_id");
            String userId = obj.getString("username");
            String acceptedBy = "";

            if (obj.has("username")) {
                acceptedBy = obj.getString("username");
            }

            String datePosted = obj.getString("datePosted");
            int urgency = obj.getInt("urgency");
            String location = obj.getString("location");
            String details = obj.getString("details");

            return new Favor(favorId, userId, acceptedBy, datePosted, urgency, location, details);
        } catch (Exception e) {
            Log.d("Error converting JSON object to favor", e.toString());
            return null;
        }
    }

    @SuppressLint("LongLogTag")
    public static Reward getRewardFromJsonObject(JSONObject obj) {
        try {
            String rewardId = obj.getString("_id");
            String name = obj.getString("name");
            String photo = "";
            String description = obj.getString("description");
            int points = obj.getInt("points");

            if (obj.has("photo")) {
                photo = obj.getString("photo");
            }

            return new Reward(rewardId, name, photo, description, points);
        } catch (Exception e) {
            Log.d("Error converting JSON object to reward", e.toString());
            return null;
        }
    }
}
