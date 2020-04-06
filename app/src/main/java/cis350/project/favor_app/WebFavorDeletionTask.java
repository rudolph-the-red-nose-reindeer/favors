package cis350.project.favor_app;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WebFavorDeletionTask extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... strings) {
        try {

            // Temp for now, to be updated when merging
            final String id = "5e852c1e6f5c7747eca0ae5d";

            JSONObject body = new JSONObject();
            body.put("id", id);

            String connString = "http://10.0.2.2:3000/favors/delete";
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
            return response;
        } catch (Exception e) {
            Log.d("HTTP request error:", e.toString());

            return null;
        }
    }
}
