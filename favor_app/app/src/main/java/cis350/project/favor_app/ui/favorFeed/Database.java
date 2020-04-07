package cis350.project.favor_app.ui.favorFeed;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.concurrent.ExecutionException;

public class Database {
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
                    String name = (String) jUser.get("username");
                    String email = (String) jUser.get("email");
                    String photo = (String) jUser.get("photo");
                    int rating = (Integer) jUser.get("rating");
                    int points = (Integer) jUser.get("points");
                    User user = new User(name, email, photo, rating, points);
                    allUsersSet.add(user);
                } catch (JSONException e) {
                    Log.e("Error getting JObject", e.toString());
                    return null;
                }
            }
            return allUsersSet;
        } catch (InterruptedException e) {
            Log.e("errpr", e.toString());
            return null;
        } catch (ExecutionException e) {
            Log.e("errpr", e.toString());
            return null;
        } catch (MalformedURLException e) {
            Log.e("errpr", e.toString());
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
                    JSONObject jFavor = (JSONObject) allFavorArray.get(i);
                    String userid = (String) jFavor.get("userId");
                    String date = (String) jFavor.get("date");
                    String location = (String) jFavor.get("location");
                    int urgency = (Integer) jFavor.get("urgency");
                    String details = (String) jFavor.get("details");
                    Favor favor = new Favor(userid, date, urgency, location, details);
                    allFavorSet.add(favor);
                } catch (JSONException e) {
                    Log.e("Error getting jUser", e.toString());
                    return null;
                }
            }
            return allFavorSet;
        } catch (InterruptedException e) {
            Log.e("errpr", e.toString());
            return null;
        } catch (ExecutionException e) {
            Log.e("errpr", e.toString());
            return null;
        } catch (MalformedURLException e) {
            Log.e("errpr", e.toString());
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
                String name = (String) jUser.get("username");
                String email = (String) jUser.get("email");
                //String photo = (String) jUser.get("photo");
                int rating = (Integer) jUser.get("rating");
                int points = (Integer) jUser.get("points");
                User user = new User(name, email, "", rating, points);
                return user;
            } catch (JSONException e) {
                Log.e("errpr", e.toString());
                return null;
            } catch (Exception e) {
                Log.e("errpr", e.toString());
                return null;
            }
        } catch (InterruptedException e) {
            Log.e("errpr", e.toString());
            return null;
        } catch (ExecutionException e) {
            Log.e("errpr", e.toString());
            return null;
        } catch (MalformedURLException e) {
            Log.e("errpr", e.toString());
            return null;
        }
    }
}
