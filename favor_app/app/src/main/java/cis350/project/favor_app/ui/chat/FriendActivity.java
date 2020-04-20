

package cis350.project.favor_app.ui.chat;

import androidx.appcompat.app.AppCompatActivity;

import cis350.project.favor_app.R;
import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import cis350.project.favor_app.R;

public class FriendActivity extends AppCompatActivity {
    ListView usersList;
    TextView noUsersText;
    ArrayList<String> al = new ArrayList<>();
    int totalUsers = 0;
    String url = "https://favor-a2d05.firebaseio.com/friend/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        usersList = (ListView)findViewById(R.id.usersList);
        noUsersText = (TextView)findViewById(R.id.noUsersText);

        String CURR_USER = getIntent().getStringExtra("CURR_USER");
        String friendUrl = url + CURR_USER + ".json";

        StringRequest request = new StringRequest(Request.Method.GET, friendUrl, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                doOnSuccess(s);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(FriendActivity.this);
        rQueue.add(request);

        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String chatWith = al.get(position);
                        Intent intent = new Intent(FriendActivity.this, ChatActivity.class);
                        intent.putExtra("OTHER_USERNAME", chatWith);
                        intent.putExtra("CURR_USERNAME", CURR_USER);
                        startActivity(intent);
            }
        });
    }

    public void doOnSuccess(String s){
        try {
            JSONObject obj = new JSONObject(s);

            Iterator i = obj.keys();
            String key = "";
            String value = "";

            while(i.hasNext()){
                key = i.next().toString();
                JSONObject obj2 = obj.getJSONObject(key);
                Iterator it = obj2.keys();
                while (it.hasNext()) {
                    al.add(it.next().toString());
                }
                totalUsers++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(totalUsers <=1){
            noUsersText.setVisibility(View.VISIBLE);
            usersList.setVisibility(View.GONE);
        }
        else{
            noUsersText.setVisibility(View.GONE);
            usersList.setVisibility(View.VISIBLE);
            usersList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al));
        }

    }
}
