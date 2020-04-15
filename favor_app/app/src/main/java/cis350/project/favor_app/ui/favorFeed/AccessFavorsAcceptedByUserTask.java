package cis350.project.favor_app.ui.favorFeed;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class AccessFavorsAcceptedByUserTask extends AsyncTask<URL, String, JSONArray> {
    public String userId;
    @SuppressLint("LongLogTag")
    @Override
    protected JSONArray doInBackground(URL... urls) {
        try {
            URL url = new URL("http://10.0.2.2:3000/favors/getallacceptedbyuser");
            // formulate request body
            JSONObject body = new JSONObject();
            body.put("userId", userId);

            // create connection and send Http request
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setDoOutput(true);

            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            os.writeBytes(body.toString());

            os.flush();
            os.close();

            Scanner in = new Scanner(conn.getInputStream());

            String msg = in.nextLine();
            Log.d("result", msg);
            conn.disconnect();

            JSONArray arr = new JSONArray(msg);
            return arr;
        } catch (Exception e) {
            Log.e("Error doing favorbyuser task", e.toString());
            return null;
        }
    }
}
