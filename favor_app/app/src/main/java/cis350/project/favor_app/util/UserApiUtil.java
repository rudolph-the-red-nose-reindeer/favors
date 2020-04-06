package cis350.project.favor_app.util;


import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import cis350.project.favor_app.data.model.User;
import cis350.project.favor_app.util.userApi.UserApiFindTask;

/*
 * Library that holds all the necessary api calls to /users/
 * Returns objects, not JSON files, null if does not exist
 * TODO: return errors?
 */

public class UserApiUtil {
//    public static AsyncTask<String, Void, User> findUserByUsernameOrEmail(String query) {
//        UserApiFindTask task = new UserApiFindTask();
//        task.execute(query);
//        String result;
//        try {
//            result = task.get();
//            JSONObject resObj = new JSONObject(result);
//            String id = resObj.getString("_id");
//            String user = resObj.getString("username");
//            String email = resObj.getString("email");
//            String photo = resObj.getString("email");
//            String bio = resObj.getString("bio");
//            int rating = resObj.getInt("rating");
//            int points = resObj.getInt("points");
//            User foundUser = new User(id, user, email, photo, bio, rating, points);
//
//            return foundUser;
//        } catch (Exception e) {
//            Log.d("Error finding user: ", e.toString());
//            return null;
//        }
//    }
}
