package cis350.project.favor_app.ui.favorFeed;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class AccessAllUsersTask extends AsyncTask<URL, String, JSONArray> {

    @Override
    protected JSONArray doInBackground(URL... urls) {
        try {// get the first URL from the array
            URL url = urls[0];
            // create connection and send Http request
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            Scanner in = new Scanner(url.openStream());
            String msg = in.nextLine();

            //// use Android JSON library to parse JSON
            JSONArray jo = new JSONArray(msg);
            return jo;
        } catch (Exception e) {
            Log.e("error 1", e.toString());
            return null;
        }
    }
}
