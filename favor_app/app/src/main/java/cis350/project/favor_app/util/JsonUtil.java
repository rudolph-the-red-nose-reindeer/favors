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
    public static JSONObject getJsonObjectFromFavor(Favor favor) {
        try {
            String favorId = favor.getFavorId();
            String userId = favor.getUserId();
            String acceptedby = favor.getAcceptedBy();
            String date = favor.getDate();
            int urgency = favor.getUrgency();
            String location = favor.getLocation();
            String details = favor.getDetails();
            double lat = favor.getLat();
            double lon = favor.getLon();
            String category = favor.getCategory();

            JSONObject out = new JSONObject();
            out.put("_id", favorId);
            out.put("userId", userId);
            out.put("acceptedBy", acceptedby);
            out.put("datePosted", date);
            out.put("urgency", urgency);
            out.put("location", location);
            out.put("details", details);
            out.put("lat", lat);
            out.put("lon", lon);
            out.put("category", category);

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
            String userId = obj.getString("userId");
            String acceptedBy = "";
            double lat = 0;
            double lon = 0;
            String date = obj.getString("datePosted");
            int urgency = obj.getInt("urgency");
            String details = obj.getString("details");
            String location = "";
            String category = obj.getString("category");

            if (obj.has("acceptedBy") && !obj.isNull("acceptedBy")) {
                acceptedBy = obj.getString("acceptedBy");
            }
            if (obj.has("lat") && !obj.isNull("lat")) {
                lat = obj.getDouble("lat");
            }
            if (obj.has("lon") && !obj.isNull("lon")) {
                lon = obj.getDouble("lon");
            }
            if (obj.has("location") && !obj.isNull("location")) {
                location = obj.getString("location");
            }

            return new Favor(favorId, userId, acceptedBy, date, urgency, location, details,
                    lat, lon, category);
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
