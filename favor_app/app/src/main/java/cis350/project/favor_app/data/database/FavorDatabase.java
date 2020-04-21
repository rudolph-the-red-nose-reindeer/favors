package cis350.project.favor_app.data.database;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.HashSet;

import cis350.project.favor_app.Constants;
import cis350.project.favor_app.data.model.Favor;
import cis350.project.favor_app.data.model.User;
import cis350.project.favor_app.ui.favorFeed.AccessAllFavorsTask;
import cis350.project.favor_app.ui.favorFeed.AccessAllUsersTask;
import cis350.project.favor_app.ui.favorFeed.AccessFavorsAcceptedByUserTask;
import cis350.project.favor_app.ui.favorFeed.AccessFavorsSubmittedByUserTask;
import cis350.project.favor_app.ui.favorFeed.AccessUserFromFavorTask;
import cis350.project.favor_app.util.JsonUtil;
import cis350.project.favor_app.util.requests.JSONObjectWebTask;
import cis350.project.favor_app.ui.favorSearch.favorSearchActivity;

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
                    Favor favor = JsonUtil.getFavorFromJsonObject(jFavor);
                    if (favor != null) {
                        allFavorSet.add(favor);
                    }
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
                    Favor favor = JsonUtil.getFavorFromJsonObject(jFavor);
                    if (favor != null) {
                        favorSet.add(favor);
                    }
                } catch (JSONException e) {
                    Log.e("Error getting favor", e.toString());
                    return null;
                }
            }
            return favorSet;
        } catch (Exception e) {
            Log.e("Error favs by submitter", e.toString());
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
                    Favor favor = JsonUtil.getFavorFromJsonObject(jFavor);
                    if (favor != null) {
                        favorSet.add(favor);
                    }
                } catch (JSONException e) {
                    Log.e("Error getting favor", e.toString());
                    return null;
                }
            }
            return favorSet;
        } catch (Exception e) {
            Log.e("Error favs by acceptor", e.toString());
            return null;
        }
    }

    public Favor addFavorToDatabase(String userId, String date, int urgency, String location,
                                    double lat, double lon, String details) {
        String connString = Constants.WEB_CONNECTION_STRING + "favors/create";
        JSONObject requestBody = new JSONObject();

        try {
            requestBody.put("userId", userId);
            requestBody.put("datePosted", date);
            requestBody.put("urgency", urgency);
            requestBody.put("location", location);
            requestBody.put("lat", lat);
            requestBody.put("lon", lon);
            requestBody.put("details", details);

            JSONObjectWebTask task = new JSONObjectWebTask();

            task.setBody(requestBody);
            task.setConnString(connString);
            task.execute();

            Favor created = JsonUtil.getFavorFromJsonObject(task.get());

            return created;
        } catch (Exception e) {
            Log.d("Error creating favor", e.toString());
            return null;
        }
    }

    public Favor deleteFavorFromDatabase(String favorId) {
        String connString = Constants.WEB_CONNECTION_STRING + "favors/delete";
        JSONObject requestBody = new JSONObject();

        try {
            requestBody.put("id", favorId);
            JSONObjectWebTask task = new JSONObjectWebTask();
            task.setBody(requestBody);

            task.setConnString(connString);
            task.execute();

            Favor deleted = JsonUtil.getFavorFromJsonObject(task.get());

            return deleted;
        } catch (Exception e) {
            Log.d("Error creating favor", e.toString());
            return null;
        }
    }
}
