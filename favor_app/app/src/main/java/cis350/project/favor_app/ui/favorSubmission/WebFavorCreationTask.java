package cis350.project.favor_app.ui.favorSubmission;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class WebFavorCreationTask extends AsyncTask<String, Void, String> {
    public String userId;
    @Override
    protected String doInBackground(String... strings) {
        try {
            // Temp for now, to be updated when merging
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            final String date = dtf.format(now);
//        Log.d("date", date);


            JSONObject body = new JSONObject();
            body.put("userId", userId);
            body.put("datePosted", date);
            body.put("urgency", strings[0]);
            body.put("longitude", strings[1]);
            body.put("latitude", strings[2]);
            body.put("details", strings[3]);

            String connString = "http://10.0.2.2:3000/favors/create";

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
