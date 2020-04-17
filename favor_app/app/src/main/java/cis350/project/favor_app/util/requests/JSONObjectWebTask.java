package cis350.project.favor_app.util.requests;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/*
 * AsyncTask for a generic http request with a JSON body, returning a JSONObject
 */
public class JSONObjectWebTask extends AsyncTask<String, Void, JSONObject> {
    private JSONObject body;
    private String connString;

    @Override
    protected JSONObject doInBackground(String... strings) {
        try {
            URL url = new URL(connString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setDoOutput(true);
            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            os.writeBytes(body.toString());
            os.flush();
            os.close();

            Scanner in = new Scanner(conn.getInputStream());
            String response = in.nextLine();
            conn.disconnect();

            return new JSONObject(response);
        } catch (Exception e) {
            Log.d("HTTP request error:", e.toString());
            return null;
        }
    }

    public void setBody(JSONObject obj) {
        this.body = obj;
    }

    public void setConnString(String connString) {
        this.connString = connString;
    }
}
