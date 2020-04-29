package cis350.project.favor_app.data.database;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import cis350.project.favor_app.Constants;
import cis350.project.favor_app.data.model.Favor;
import cis350.project.favor_app.util.JsonUtil;
import cis350.project.favor_app.util.requests.JSONArrayWebTask;
import cis350.project.favor_app.util.requests.JSONObjectWebTask;

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

    public List<Favor> getAllFavors(String compare) {
        String connString = Constants.WEB_CONNECTION_STRING + "favors/all";
        JSONObject requestBody = new JSONObject();

        try {
            requestBody.put("compare", compare);

            JSONArrayWebTask task = new JSONArrayWebTask();

            task.setBody(requestBody);
            task.setConnString(connString);
            task.execute();

            List<Favor> favors = new LinkedList<Favor>();


            JSONArray result = task.get();
            for (int i = 0; i < result.length(); i++) {
                JSONObject obj = result.getJSONObject(i);
                Favor favor = JsonUtil.getFavorFromJsonObject(obj);
                if (favor != null) {
                    favors.add(favor);
                }
            }

            return favors;
        } catch (Exception e) {
            Log.d("Error creating favor", e.toString());
            return null;
        }
    }

    public List<Favor> getFavorsSubmittedByUser(String userId, String compare) {
        String connString = Constants.WEB_CONNECTION_STRING + "favors/getallfromuser";
        JSONObject requestBody = new JSONObject();

        try {
            requestBody.put("userId", userId);
            requestBody.put("compare", compare);

            JSONArrayWebTask task = new JSONArrayWebTask();

            task.setBody(requestBody);
            task.setConnString(connString);
            task.execute();

            List<Favor> favors = new LinkedList<Favor>();


            JSONArray result = task.get();
            for (int i = 0; i < result.length(); i++) {
                JSONObject obj = result.getJSONObject(i);
                Favor favor = JsonUtil.getFavorFromJsonObject(obj);
                if (favor != null) {
                    favors.add(favor);
                }
            }

            return favors;
        } catch (Exception e) {
            Log.d("Error creating favor", e.toString());
            return null;
        }
    }

    public List<Favor> getFavorsAcceptedByUser(String userId, String compare) {
        String connString = Constants.WEB_CONNECTION_STRING + "favors/getallacceptedbyuser";
        JSONObject requestBody = new JSONObject();

        try {
            requestBody.put("userId", userId);
            requestBody.put("compare", compare);

            JSONArrayWebTask task = new JSONArrayWebTask();

            task.setBody(requestBody);
            task.setConnString(connString);
            task.execute();

            List<Favor> favors = new LinkedList<Favor>();


            JSONArray result = task.get();
            for (int i = 0; i < result.length(); i++) {
                JSONObject obj = result.getJSONObject(i);
                Favor favor = JsonUtil.getFavorFromJsonObject(obj);
                if (favor != null) {
                    favors.add(favor);
                }
            }

            return favors;
        } catch (Exception e) {
            Log.d("Error creating favor", e.toString());
            return null;
        }
    }

    public Favor addFavorToDatabase(String userId, String username, String date, int urgency,
                                    String location, double lat, double lon, String details,
                                    String category) {
        String connString = Constants.WEB_CONNECTION_STRING + "favors/create";
        JSONObject requestBody = new JSONObject();

        try {
            requestBody.put("userId", userId);
            requestBody.put("username", username);
            requestBody.put("datePosted", date);
            requestBody.put("urgency", urgency);
            requestBody.put("location", location);
            requestBody.put("lat", lat);
            requestBody.put("lon", lon);
            requestBody.put("details", details);
            requestBody.put("category", category);

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
