package cis350.project.favor_app.data.database;

import android.annotation.SuppressLint;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import cis350.project.favor_app.Constants;
import cis350.project.favor_app.data.model.User;
import cis350.project.favor_app.util.JsonUtil;
import cis350.project.favor_app.util.requests.JSONArrayWebTask;
import cis350.project.favor_app.util.requests.JSONObjectWebTask;

public class UserDatabase {

    private static UserDatabase instance = null;

    private UserDatabase() {}

    public static UserDatabase getInstance() {
        if (instance == null)
            instance = new UserDatabase();

        return instance;
    }

    // returns a list of all users, or an empty list if an error occurs
    public List<User> getAllRewards() {
        String connString = Constants.WEB_CONNECTION_STRING + "users/all";
        JSONObject requestBody = new JSONObject();

        List<User> list = new LinkedList<User>();

        try {
            JSONArrayWebTask task = new JSONArrayWebTask();

            task.setBody(requestBody);
            task.setConnString(connString);
            task.execute();

             JSONArray result = task.get();
             for (int i = 0; i < result.length(); i++) {
                 JSONObject obj = result.getJSONObject(i);
                 User user = JsonUtil.getUserFromJsonObject(obj);
                 if (user != null) {
                     list.add(user);
                 }
             }
            return null;
        } catch (Exception e) {
            Log.d("Error get all users", e.toString());
            return list;
        }
    }

    public User findUserById(String id) {
        String connString = Constants.WEB_CONNECTION_STRING + "users/find";
        JSONObject requestBody = new JSONObject();

        try {
            JSONObjectWebTask task = new JSONObjectWebTask();
            requestBody.put("id", id);

            task.setBody(requestBody);
            task.setConnString(connString);
            task.execute();

            JSONObject result = task.get();
            return JsonUtil.getUserFromJsonObject(result);
        } catch (Exception e) {
            Log.d("Error get all users", e.toString());
            return null;
        }
    }

    @SuppressLint("LongLogTag")
    public User findUserByUsername(String username) {
        String connString = Constants.WEB_CONNECTION_STRING + "users/find";
        JSONObject requestBody = new JSONObject();

        try {
            JSONObjectWebTask task = new JSONObjectWebTask();
            requestBody.put("username", username);

            task.setBody(requestBody);
            task.setConnString(connString);
            task.execute();

            JSONObject result = task.get();
            return JsonUtil.getUserFromJsonObject(result);
        } catch (Exception e) {
            Log.d("Error get user by username", e.toString());
            return null;
        }
    }

    public User findUserByEmail(String email) {
        String connString = Constants.WEB_CONNECTION_STRING + "users/find";
        JSONObject requestBody = new JSONObject();

        try {
            JSONObjectWebTask task = new JSONObjectWebTask();
            requestBody.put("email", email);

            task.setBody(requestBody);
            task.setConnString(connString);
            task.execute();

            JSONObject result = task.get();
            return JsonUtil.getUserFromJsonObject(result);
        } catch (Exception e) {
            Log.d("Error get user by email", e.toString());
            return null;
        }
    }

    public User findUserByUsernameOrEmail(String query) {
        String connString = Constants.WEB_CONNECTION_STRING + "users/find";
        JSONObject requestBody = new JSONObject();

        try {
            JSONObjectWebTask task = new JSONObjectWebTask();
            requestBody.put("username", query);
            requestBody.put("email", query);

            task.setBody(requestBody);
            task.setConnString(connString);
            task.execute();

            JSONObject result = task.get();
            return JsonUtil.getUserFromJsonObject(result);
        } catch (Exception e) {
            Log.d("Error get user by email", e.toString());
            return null;
        }
    }

    public void updateUser(User user) {
        String connString = Constants.WEB_CONNECTION_STRING + "users/update";
        JSONObject requestBody = JsonUtil.getJsonObjectFromUser((user));

        try {
            JSONObjectWebTask task = new JSONObjectWebTask();

            task.setBody(requestBody);
            task.setConnString(connString);
            task.execute();
        } catch (Exception e) {
            Log.d("Error get user by email", e.toString());
        }
    }
}
