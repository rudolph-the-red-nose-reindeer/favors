package cis350.project.favor_app.ui.chat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.ConsoleHandler;

import cis350.project.favor_app.R;

public class ChatActivity extends AppCompatActivity {
    LinearLayout layout;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;
    Firebase mReference1, mReference2, fReference1, fReference2;
    String fReference1URL, fReference2URL;
    FirebaseAuth firebaseAuth;
    FirebaseUser currFireBaseUser;
    String chatWith, currUsername;
    static String FIRE_MESSAGE_URL = "https://favor-a2d05.firebaseio.com/messages/";
    static String FIRE_FRIEND_URL = "https://favor-a2d05.firebaseio.com/friend/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        layout = (LinearLayout) findViewById(R.id.layout1);
        sendButton = (ImageView) findViewById(R.id.sendButton);
        messageArea = (EditText) findViewById(R.id.messageArea);
        scrollView = (ScrollView) findViewById(R.id.scrollView);

        currUsername = getIntent().getStringExtra("CURR_USERNAME");
        chatWith = getIntent().getStringExtra("OTHER_USERNAME");
        //Logging test
        Log.d("currUsername", currUsername);
        Log.d("chatWith",  chatWith);
        //
        Firebase.setAndroidContext(this);


        mReference1 = new Firebase(FIRE_MESSAGE_URL + currUsername + "_" + chatWith);
        mReference2 = new Firebase(FIRE_MESSAGE_URL + chatWith + "_" + currUsername);

        //Add to friends list if not already there
        fReference1URL = FIRE_FRIEND_URL  + currUsername + ".json";
        fReference2URL = FIRE_FRIEND_URL + chatWith + ".json";
        fReference1 = new Firebase(FIRE_FRIEND_URL + currUsername);
        fReference2 = new Firebase(FIRE_FRIEND_URL + chatWith);

        Log.d("making sure ", "value");
        StringRequest fReference1Req = new StringRequest(Request.Method.GET, fReference1URL, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                Log.d("referenceUrl1", fReference1URL);
                setUpFriends(s, fReference1, chatWith);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("volley 1 error: ", volleyError.toString());
            }
        });
        //Add to request queue
        RequestQueue rQueue = Volley.newRequestQueue(ChatActivity.this);
        rQueue.add(fReference1Req);


        StringRequest fReference2Req = new StringRequest(Request.Method.GET, fReference2URL, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                Log.d("referenceUrl2", fReference2URL);
                setUpFriends(s, fReference2, currUsername);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("volley 2 error: ", volleyError.toString());
            }
        });
        //Add to request queue
        rQueue.add(fReference2Req);


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();

                if(!messageText.equals("")){
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("message", messageText);
                    map.put("user", currUsername);
                    mReference1.push().setValue(map);
                    mReference2.push().setValue(map);
                    messageArea.setText("");
                }
            }
        });

        mReference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                String message = map.get("message").toString();
                String userName = map.get("user").toString();

                if(userName.equals(currUsername)){
                    addMessageBox("You:-\n" + message, 1);
                }
                else{
                    addMessageBox(chatWith + ":-\n" + message, 2);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void addMessageBox(String message, int type){
        TextView textView = new TextView(ChatActivity.this);
        textView.setText(message);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 10);
        textView.setLayoutParams(lp);

        if(type == 1) {
            textView.setBackgroundResource(R.drawable.rounded_corner1);
        }
        else{
            textView.setBackgroundResource(R.drawable.rounded_corner2);
        }

        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }

    public void setUpFriends(String jsonString, Firebase refForAdd, String toAdd) {
        try {
            Log.e("what is hapenning", "hi");
            if (jsonString.equals(null) || jsonString.equals(JSONObject.NULL)) {
                Log.d("went through", "yerp");
                refForAdd.push().setValue(toAdd);
                return;
            }
            JSONObject obj = new JSONObject(jsonString);
            Log.d("jsonString", jsonString);
            Iterator i = obj.keys();
            String key = "";
            boolean inListAlready = false;
            while(i.hasNext()){
                key = i.next().toString();
                Log.d("json key", key);
                String name = obj.getString(key);
                if (name.equals(toAdd)) {
                    inListAlready = true;
                    break;
                }
            }
            if (!inListAlready) {
                refForAdd.push().setValue(toAdd);
            }

        }

        catch (JSONException e) {
            Log.d("JSONException", e.toString());
            e.printStackTrace();
            refForAdd.push().setValue(toAdd);
        }
    }
}