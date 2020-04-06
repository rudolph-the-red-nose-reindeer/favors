package cis350.project.favor_app.util.userApi;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import cis350.project.favor_app.R;
import cis350.project.favor_app.data.model.User;

import static cis350.project.favor_app.Constants.WEB_CONNECTION_STRING;

public class UserApiFindTask extends AsyncTask<String, Void, User> {
    @Override
    protected User doInBackground(String... strings){
        try{
            JSONObject body = new JSONObject();
            body.put("username", strings[0]);
            body.put("email", strings[0]);

            String connString = WEB_CONNECTION_STRING + "users/find";
            URL url = new URL(connString);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestProperty("Content-Type","application/json;charset=UTF-8");
            conn.setDoOutput(true);
            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            os.writeBytes(body.toString());
            os.flush();
            os.close();

            Scanner in = new Scanner(conn.getInputStream());
            String response = in.nextLine();
            conn.disconnect();

            JSONObject resObj = new JSONObject(response);
            String id = resObj.getString("_id");
            String user = resObj.getString("username");
            String email = resObj.getString("email");
            String photo = resObj.getString("email");
            String bio = resObj.getString("bio");
            int rating = resObj.getInt("rating");
            int points = resObj.getInt("points");
            User foundUser = new User(id, user, email, photo, bio, rating, points);

            return foundUser;
        } catch (Exception e) {
            Log.d("HTTP request error:", e.toString());
            return null;
        }
    }
}
