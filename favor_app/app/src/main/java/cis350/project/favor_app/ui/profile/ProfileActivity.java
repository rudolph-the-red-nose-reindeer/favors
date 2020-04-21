package cis350.project.favor_app.ui.profile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.service.autofill.UserData;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import cis350.project.favor_app.R;
import cis350.project.favor_app.data.database.UserDatabase;
import cis350.project.favor_app.data.model.Location;
import cis350.project.favor_app.data.model.User;
import cis350.project.favor_app.ui.chat.FriendActivity;
import cis350.project.favor_app.ui.favorFeed.FavorFeedActivity;
import cis350.project.favor_app.ui.favorSearch.favorSearchActivity;
import cis350.project.favor_app.ui.favorSubmission.SubmitFavorActivity;
import cis350.project.favor_app.ui.login.LoginActivity;
import cis350.project.favor_app.ui.rewards.RewardActivity;
import cis350.project.favor_app.ui.userFavorHistory.UserFavorHistoryActivity;
import cis350.project.favor_app.util.ImageUtil;
import cis350.project.favor_app.util.LocationUtil;

public class ProfileActivity extends Activity {

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
    private View favorSubmissionButton;
    private View seeUserFavorsButton;
    private View seeRewardsButton;
    private View logoutButton;
    private View chatButton;
    private View findFavorButton;

    private User loggedInUser;
    private Bitmap photo;

    public static final int GET_FROM_GALLERY = 3;
    public static final int REGISTER_ACTIVITY_ID = 4;
    public static final int USER_FAVORS_ACTIVITY_ID = 5;
    public static final int REWARD_REDEMPTION_ACTIVITY_ID = 6;

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
        uploadButton = findViewById(R.id.upload_button);
        seeFavorsButton = findViewById(R.id.see_favors_button);
        favorSubmissionButton = findViewById(R.id.launch_favor_submit_button);
        seeUserFavorsButton = findViewById(R.id.profile_user_favors_button);
        seeRewardsButton = findViewById(R.id.profile_rewards_button);
        logoutButton = findViewById(R.id.profile_logout_button);
        chatButton = findViewById(R.id.see_friends_button);
        findFavorButton = findViewById(R.id.find_favor_search_profile_btn);

        loggedInUser = UserDatabase.getInstance().findUserById(
                getIntent().getStringExtra("userId"));

        assignProfileFeatures();

        bioView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    loggedInUser.setBio(bioView.getText().toString());
                    UserDatabase.getInstance().updateUser(loggedInUser);
                }
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent, GET_FROM_GALLERY);
                ;
            }
        });

        seeFavorsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(self, FavorFeedActivity.class);
                intent.putExtra("CURR_USERNAME", loggedInUser.getUsername());
                startActivityForResult(intent, REGISTER_ACTIVITY_ID);
            }
        });

        favorSubmissionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateFavorActivity();
            }
        });

        seeUserFavorsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(self, UserFavorHistoryActivity.class);
                intent.putExtra("userId", loggedInUser.getUserId());
                startActivityForResult(intent, USER_FAVORS_ACTIVITY_ID);
            }
        });

        seeRewardsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(self, RewardActivity.class);
                intent.putExtra("userId", loggedInUser.getUserId());
                startActivityForResult(intent, REWARD_REDEMPTION_ACTIVITY_ID);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(self, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(self, FriendActivity.class);
                intent.putExtra("CURR_USER", loggedInUser.getUsername());
                startActivity(intent);
            }
        });
        findFavorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(self, favorSearchActivity.class);
                startActivity(intent);
            }
        });
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
                String encodedPhoto = ImageUtil.encodeBase64(uploadedBitmap);
                loggedInUser.setPhoto(encodedPhoto);
                photoView.setImageBitmap(uploadedBitmap);
            } catch (Exception e) {
                Log.e("UPLOAD", e.toString());
            }
        } else if (requestCode == REWARD_REDEMPTION_ACTIVITY_ID) {
            loggedInUser = UserDatabase.getInstance().findUserById(loggedInUser.getUserId());
            assignProfileFeatures();
        }
    }

    private void assignProfileFeatures() {
        usernameView.setText(loggedInUser.getUsername());
        emailView.setText(loggedInUser.getEmail());

        photo = ImageUtil.decodeBase64(loggedInUser.getPhoto());

        if (photo != null) {
            photoView.setImageBitmap(photo);
        }
        bioView.setText(loggedInUser.getBio());
        ratingView.setText("Rating: " + String.valueOf(loggedInUser.getRating()));
        pointsView.setText("Points: " + String.valueOf(loggedInUser.getPoints()));

        UserDatabase.getInstance().updateUser(loggedInUser);
    }


    public void openCreateFavorActivity() {
        Intent intent = new Intent(this, SubmitFavorActivity.class);
        intent.putExtra("userId", loggedInUser.getUserId());
        startActivity(intent);
    }


}
