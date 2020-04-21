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

    public HashSet<User> getAllUsers() {
        try {
            URL url = new URL("http://10.0.2.2:3000/users/all");
            AccessAllUsersTask task = new AccessAllUsersTask();
            task.execute(url);
            JSONArray allUsersArray = task.get();
            Log.e("allusersarray", allUsersArray.toString());
            HashSet allUsersSet = new HashSet<User>();
            for (int i = 0; i < allUsersArray.length(); i++) {
                try {
                    JSONObject jUser = (JSONObject) allUsersArray.get(i);
                    String userId = jUser.getString("_id");
                    String name = jUser.getString("username");
                    String email = jUser.getString("email");
                    String photo = jUser.getString("photo");
                    String bio = jUser.getString("bio");
                    int rating = jUser.getInt("rating");
                    int points = jUser.getInt("points");
                    User user = new User(userId, name, email, photo, bio, rating, points);
                    allUsersSet.add(user);
                } catch (JSONException e) {
                    Log.e("Error getting user", e.toString());
                    return null;
                }
            }
            return allUsersSet;
        } catch (Exception e) {
            Log.e("Error getting all users", e.toString());
            return null;
        }
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
                    if (jFavor.has("acceptedBy")) {
                        acceptedBy = jFavor.getString("acceptedBy");
                    }
                    String date = jFavor.getString("datePosted");
                    String location = jFavor.getString("location");
                    int urgency = jFavor.getInt("urgency");
                    String details = jFavor.getString("details");
                    String category = jFavor.getString("category");
                    Favor favor = new Favor(favorId, userId, acceptedBy, date, urgency, location,
                            details, category);
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

    public User getUserFromId(String id) {
        try {
            URL url = new URL("http://10.0.2.2:3000/users/find");
            AccessUserFromFavorTask task = new AccessUserFromFavorTask();
            task.userId = id;
            Log.d("userid", id);
            task.execute(url);
            JSONObject jUser = task.get();
            if (jUser == null) {
                return null;
            }

            try {
                String userId = jUser.getString("_id");
                String name = jUser.getString("username");
                String email = jUser.getString("email");
                String photo = jUser.getString("photo");
                String bio = jUser.getString("bio");
                int rating = jUser.getInt("rating");
                int points = jUser.getInt("points");
                User user = new User(userId, name, email, photo, bio, rating, points);
                return user;
            } catch (JSONException e) {
                Log.e("Could not JSONfy user", e.toString());
                return null;
            }
        } catch (Exception e) {
            Log.e("Getting iduser error", e.toString());
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
                    if (jFavor.has("acceptedBy")) {
                        acceptedBy = jFavor.getString("acceptedBy");
                    }
                    String date = jFavor.getString("datePosted");
                    String location = jFavor.getString("location");
                    int urgency = jFavor.getInt("urgency");
                    String details = jFavor.getString("details");
                    String category = jFavor.getString("category");
                    Favor favor = new Favor(favorId, userId, acceptedBy, date, urgency, location,
                            details, category);
                    favorSet.add(favor);
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
                    String favorId = jFavor.getString("_id");
                    String acceptedBy = null;
                    if (jFavor.has("acceptedBy")) {
                        acceptedBy = jFavor.getString("acceptedBy");
                    }
                    String date = jFavor.getString("datePosted");
                    String location = jFavor.getString("location");
                    int urgency = jFavor.getInt("urgency");
                    String details = jFavor.getString("details");
                    String category = jFavor.getString("category");
                    Favor favor = new Favor(favorId, userId, acceptedBy, date, urgency, location,
                            details, category);
                    favorSet.add(favor);
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
}
