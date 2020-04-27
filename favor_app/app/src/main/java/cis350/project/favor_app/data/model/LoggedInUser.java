package cis350.project.favor_app.data.model;

import android.graphics.Bitmap;
import android.media.Image;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String userId;
    private String displayName;
    private String email;
    private String photo;
    private String bio;
    private int rating;
    private int points;

    public LoggedInUser(String userId, String displayName, String email, String photo, String bio,
                        int rating, int points) {
        this.userId = userId;
        this.displayName = displayName;
        this.email = email;
        this.photo = photo;
        this.bio = bio;
        this.rating = rating;
        this.points = points;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoto() {
        return photo;
    }

    public String getBio() {
        return bio;
    }

    public int getRating() {
        return rating;
    }

    public int getPoints() {
        return points;
    }
}
