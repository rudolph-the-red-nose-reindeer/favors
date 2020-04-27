package cis350.project.favor_app.ui.login;

import android.graphics.Bitmap;
import android.media.Image;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private String displayName;
    private String email;
    private String photo;
    private String bio;
    private int rating;
    private int points;

    LoggedInUserView(String displayName, String email, String photo, String bio, int rating,
                     int points) {
        this.displayName = displayName;
        this.email = email;
        this.photo = photo;
        this.bio = bio;
        this.rating = rating;
        this.points = points;
    }

    String getDisplayName() {
        return displayName;
    }

    String getEmail() {
        return email;
    }

    String getPhoto() {
        return photo;
    }

    String getBio() {
        return bio;
    }

    int getRating() {
        return rating;
    }

    int getPoints() {
        return points;
    }

}
