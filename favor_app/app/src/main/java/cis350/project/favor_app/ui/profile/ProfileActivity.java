package cis350.project.favor_app.ui.profile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import cis350.project.favor_app.R;
import cis350.project.favor_app.ui.favorFeed.FavorFeedActivity;
import cis350.project.favor_app.ui.register.WebRegisterTask;
import cis350.project.favor_app.util.ImageUtil;

public class ProfileActivity extends AppCompatActivity {

    private ProfileActivity self = this;
    private TextView usernameView;
    private TextView emailView;
    private ImageView photoView;
    private EditText bioView;
    private TextView ratingView;
    private TextView pointsView;
    private View saveButton;
    private View uploadButton;
    private View seeFavorsButton;

    private String username;
    private String email;
    private Bitmap photo;
    private String bio;
    private int rating;
    private int points;

    public static final int GET_FROM_GALLERY = 3;
    public static final int REGISTER_ACTIVITY_ID = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        usernameView = findViewById(R.id.name_text);
        emailView = findViewById(R.id.email_text);
        bioView = findViewById(R.id.bio_text);
        photoView = findViewById(R.id.profile_pic);
        ratingView = findViewById(R.id.rating_text);
        pointsView = findViewById(R.id.points_text);
        saveButton = findViewById(R.id.profile_save_button);
        uploadButton = findViewById(R.id.upload_button);
        seeFavorsButton = findViewById(R.id.see_favors_button);

        username = getIntent().getStringExtra("username");
        email = getIntent().getStringExtra("email");
        photo = ImageUtil.decodeBase64(getIntent().getStringExtra("photo"));
        if (photo != null) {
            photo = Bitmap.createScaledBitmap(photo, 250, 250, false);
        }
        bio = getIntent().getStringExtra("bio");
        rating = getIntent().getIntExtra("rating", 0);
        points = getIntent().getIntExtra("points", 0);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String encodedImage = ImageUtil.encodeBase64(self.photo);
                WebSaveProfileTask updateTask = new WebSaveProfileTask();
                updateTask.execute(self.username, encodedImage, self.bio);
            }
        });

        bioView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                self.bio = s.toString();
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent, GET_FROM_GALLERY);;
            }
        });

        seeFavorsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(self, FavorFeedActivity.class);
                startActivityForResult(intent, REGISTER_ACTIVITY_ID);
            }
        });


        assignProfileFeatures();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selection = data.getData();
            Bitmap uploadedBitmap = null;
            try {
                uploadedBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),
                        selection);
                uploadedBitmap = Bitmap.createScaledBitmap(uploadedBitmap, 250, 250,
                        false);
                photo = uploadedBitmap;
                photoView.setImageBitmap(photo);
            } catch (Exception e) {
                Log.e("UPLOAD", e.toString());
            }
        }
    }

    private void assignProfileFeatures() {
        usernameView.setText(username);
        emailView.setText(email);
        if (photo != null) {
            photoView.setImageBitmap(photo);
        }
        bioView.setText(bio);
        ratingView.setText("Rating: " + String.valueOf(rating));
        pointsView.setText("Points: " + String.valueOf(points));
    }


}
