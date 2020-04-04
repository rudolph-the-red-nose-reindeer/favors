package cis350.project.favor_app.data;

import android.content.res.Resources;
import android.util.Log;

import org.json.JSONObject;

import cis350.project.favor_app.R;
import cis350.project.favor_app.data.model.LoggedInUser;
import cis350.project.favor_app.ui.login.WebAuthTask;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        try {
            WebAuthTask task = new WebAuthTask();
            task.execute(username, password);
            String res = task.get();
            JSONObject resObj = new JSONObject(res);

            Log.d("RESULT", res);

            if (resObj.getBoolean("auth")) {
                LoggedInUser user = new LoggedInUser(resObj.getString("id"),
                        resObj.getString("username"));

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
