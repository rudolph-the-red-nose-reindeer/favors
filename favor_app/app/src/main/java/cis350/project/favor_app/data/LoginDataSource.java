package cis350.project.favor_app.data;

import android.util.Log;

import org.json.JSONObject;

import cis350.project.favor_app.data.model.User;
import cis350.project.favor_app.ui.login.WebAuthTask;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<User> login(String username, String password) {

        try {
            WebAuthTask task = new WebAuthTask();
            task.execute(username, password);
            String res = task.get();
            JSONObject resObj = new JSONObject(res);
            JSONObject userObj = resObj.getJSONObject("user");

            Log.d("RESULT", res);

            if (resObj.getBoolean("auth")) {
                User user = new User(userObj.getString("_id"),
                        userObj.getString("username"),
                        userObj.getString("email"),
                        userObj.getString("photo"),
                        userObj.getString("bio"),
                        userObj.getInt("rating"),
                        userObj.getInt("points"));

                return new Result.Success<>(user);
            } else {
                return new Result.Error(new IOException("Error logging in"));
            }
        } catch (Exception e) {
            Log.e("error", e.toString());
            return new Result.Error(e);
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
