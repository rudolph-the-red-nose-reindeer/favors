package cis350.project.favor_app.data.model;;

public class Favor {
    private String favorId;
    private String userId;
    private String username;
    private String acceptedBy;
    private String date;
    private int urgency;
    private String location;
    private String details;
    private double lat;
    private double lon;
    private String category;

    public Favor(String favorId, String userId, String username, String acceptedBy, String date,
                 int urgency, String location, String details, double lat, double lon,
                 String category) {
        this.favorId = favorId;
        this.userId = userId;
        this.username = username;
        if (acceptedBy != null) {
            this.acceptedBy = acceptedBy;
        } else {
            this.acceptedBy = "";
        }
        this.date = date;
        this.urgency = urgency;
        this.location = location;
        this.details = details;
        this.lat = lat;
        this.lon = lon;
        this.category = category;
    }

    public String getFavorId() {
        return favorId;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getAcceptedBy() {
        return acceptedBy;
    }

    public String getDate() {
        return date;
    }

    public int getUrgency() {
        return urgency;
    }

    public String getLocation() {
        return location;
    }

    public String getDetails() {
        return details;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getCategory() {
        return category;
    }

}
