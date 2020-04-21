package cis350.project.favor_app.data.database;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.HashSet;

import cis350.project.favor_app.data.model.Favor;
import cis350.project.favor_app.data.model.User;
import cis350.project.favor_app.ui.favorFeed.AccessAllFavorsTask;
import cis350.project.favor_app.ui.favorFeed.AccessAllUsersTask;
import cis350.project.favor_app.ui.favorFeed.AccessFavorsAcceptedByUserTask;
import cis350.project.favor_app.ui.favorFeed.AccessFavorsSubmittedByUserTask;
import cis350.project.favor_app.ui.favorFeed.AccessUserFromFavorTask;

/*
 * Singleton class for the database
 */
public class FavorDatabase {

    private static FavorDatabase instance = null;

    private FavorDatabase() {}

    // static method to create instance of database class
    public static FavorDatabase getInstance()
    {
        if (instance == null)
            instance = new FavorDatabase();

        return instance;
    }

    public HashSet<Favor> getAllFavors() {
        try {
            URL url = new URL("http://10.0.2.2:3000/favors/all");
            AccessAllFavorsTask task = new AccessAllFavorsTask();
            task.execute(url);
            JSONArray allFavorArray = task.get();
            if (allFavorArray == null) {
                return null;
            }
            HashSet allFavorSet = new HashSet<User>();
            for (int i = 0; i < allFavorArray.length(); i++) {
                try {
                    JSONObject jFavor = allFavorArray.getJSONObject(i);
                    Log.d("Json favor string", jFavor.toString());
                    String favorId = jFavor.getString("_id");
                    String userId = jFavor.getString("userId");
                    String acceptedBy = null;
                    double longitude = 0;
                    double latitude = 0;
                    if (jFavor.has("acceptedBy")) {
                        acceptedBy = jFavor.getString("acceptedBy");
                    }
                    if (jFavor.has("longitude")) {
                        longitude = jFavor.getDouble("longitude");
                    }
                    if (jFavor.has("latitude")) {
                        latitude = jFavor.getDouble("latitude");
                    }
                    String date = jFavor.getString("datePosted");

                    int urgency = jFavor.getInt("urgency");
                    String details = jFavor.getString("details");
                    Favor favor = new Favor(favorId, userId, acceptedBy, date, urgency, longitude,
                            latitude, details);
                    allFavorSet.add(favor);
                } catch (JSONException e) {
                    Log.e("Error getting favor", e.toString());
                    return null;
                }
            }
            return allFavorSet;
        } catch (Exception e) {
            Log.e("Error all favors", e.toString());
            return null;
        }
    }

    public HashSet<Favor> getFavorsSubmittedByUser(String userId) {
        try {
            AccessFavorsSubmittedByUserTask task = new AccessFavorsSubmittedByUserTask();
            task.userId = userId;
            Log.d("userId", userId);
            task.execute();
            JSONArray allFavorArray = task.get();
            if (allFavorArray == null) {
                return null;
            }
            HashSet favorSet = new HashSet<User>();
            for (int i = 0; i < allFavorArray.length(); i++) {
                try {
                    JSONObject jFavor = allFavorArray.getJSONObject(i);
                    Log.d("Json favor string", jFavor.toString());
                    String favorId = jFavor.getString("_id");
                    String acceptedBy = null;
                    double longitude = 0;
                    double latitude = 0;
                    if (jFavor.has("acceptedBy")) {
                        acceptedBy = jFavor.getString("acceptedBy");
                    }
                    if (jFavor.has("longitude")) {
                        longitude = jFavor.getDouble("longitude");
                    }
                    if (jFavor.has("latitude")) {
                        latitude = jFavor.getDouble("latitude");
                    }
                    String date = jFavor.getString("datePosted");

                    int urgency = jFavor.getInt("urgency");
                    String details = jFavor.getString("details");
                    Favor favor = new Favor(favorId, userId, acceptedBy, date, urgency, longitude,
                            latitude, details);
                    favorSet.add(favor);
                } catch (JSONException e) {
                    Log.e("Error getting favor", e.toString());
                    return null;
                }
            }
            return favorSet;
        } catch (Exception e) {
            Log.e("Error favors by submitter", e.toString());
            return null;
        }
    }

    public HashSet<Favor> getFavorsAcceptedByUser(String userId) {
        try {
            AccessFavorsAcceptedByUserTask task = new AccessFavorsAcceptedByUserTask();
            task.userId = userId;
            Log.d("userId", userId);
            task.execute();
            JSONArray allFavorArray = task.get();
            if (allFavorArray == null) {
                return null;
            }
            HashSet favorSet = new HashSet<User>();
            for (int i = 0; i < allFavorArray.length(); i++) {
                try {
                    JSONObject jFavor = allFavorArray.getJSONObject(i);
                    Log.d("Json favor string", jFavor.toString());
                    String favorId = jFavor.getString("_id");
                    String acceptedBy = null;
                    double longitude = 0;
                    double latitude = 0;
                    if (jFavor.has("acceptedBy")) {
                        acceptedBy = jFavor.getString("acceptedBy");
                    }
                    if (jFavor.has("longitude")) {
                        longitude = jFavor.getDouble("longitude");
                    }
                    if (jFavor.has("latitude")) {
                        latitude = jFavor.getDouble("latitude");
                    }
                    String date = jFavor.getString("datePosted");

                    int urgency = jFavor.getInt("urgency");
                    String details = jFavor.getString("details");
                    Favor favor = new Favor(favorId, userId, acceptedBy, date, urgency, longitude,
                            latitude, details);
                    favorSet.add(favor);
                } catch (JSONException e) {
                    Log.e("Error getting favor", e.toString());
                    return null;
                }
            }
            return favorSet;
        } catch (Exception e) {
            Log.e("Error favors by acceptor", e.toString());
            return null;
        }
    }
}
