package cis350.project.favor_app.ui.favorFeed;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class AccessUserFromFavorTask extends AsyncTask<URL, String, JSONObject> {

    public String userId;

    @Override
    protected JSONObject doInBackground(URL... urls) {
        try {// get the first URL from the array
            URL url = urls[0];

            // formulate request body
            JSONObject body = new JSONObject();
            body.put("id", userId);

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

            Log.d("result", "no");
            String msg = in.nextLine();
            Log.d("result", msg);
            conn.disconnect();

            //// use Android JSON library to parse JSON
            JSONObject jo = new JSONObject(msg);
            return jo;
        } catch (Exception e) {
            Log.e("error 2", e.toString());
            return null;
        }
    }
}
