package cis350.project.favor_app.ui.favorFeed;

import org.json.JSONObject;

public class FavorListItem {
    private String username;
    private String details;
    private String urgency;
    private String date;

    public FavorListItem(String username, String details, String urgency, String date) {
        this.username = username;
        this.details = details;
        this.urgency = urgency;
        this.date = date;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getDetails() {
        return details;
    }
    public void setDetails(String details) {
        this.details = details;
    }
    public String getUrgency() {
        return urgency;
    }
    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }
    public String getDate() {
        return date;
    }

    public String toString() {
        JSONObject jo = new JSONObject();

        try {
            jo.put("username", username);
            jo.put("details", details);
            jo.put("urgency", urgency);
            jo.put("date", date);
            return jo.toString();
        } catch (Exception e) {
            return "";
        }
    }
}