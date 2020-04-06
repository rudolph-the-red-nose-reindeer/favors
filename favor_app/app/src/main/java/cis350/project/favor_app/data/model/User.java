package cis350.project.favor_app.data.model;

import android.graphics.Bitmap;
import android.media.Image;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class User {

    private String userId;
    private String username;
    private String email;
    private String photo;
    private String bio;
    private int rating;
    private int points;

    public User(String userId, String username, String email, String photo, String bio,
                int rating, int points) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.photo = photo;
        this.bio = bio;
        this.rating = rating;
        this.points = points;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
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
