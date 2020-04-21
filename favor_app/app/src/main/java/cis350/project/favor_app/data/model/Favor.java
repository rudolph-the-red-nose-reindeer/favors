package cis350.project.favor_app.data.model;

public class Favor {
    private String favorId;
    private String userId;
    private String acceptedBy;
    private String date;
    private int urgency;
    private double longitude;
    private double latitude;
    private String details;

    public Favor(String favorId, String userId, String acceptedBy, String date, int urgency,
                 double longitude, double latitude, String details) {
        this.favorId = favorId;
        this.userId = userId;
        if (acceptedBy != null) {
            this.acceptedBy = acceptedBy;
        } else {
            this.acceptedBy = "";
        }
        this.date = date;
        this.urgency = urgency;
        this.longitude = longitude;
        this.latitude = latitude;
        this.details = details;
    }

    public String getFavorId() {
        return favorId;
    }

    public String getUserId() {
        return userId;
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

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getDetails() {
        return details;
    }
}
