package cis350.project.favor_app.ui.favorFeed;

    public class Favor {
    String fuserId;
    String fdate;
    int furgency;
    String flocation;
    String fdetails;

    public Favor(String userid, String date, int urgency, String location, String details) {
        fuserId = userid;
        fdate = date;
        furgency = urgency;
        flocation = location;
        fdetails = details;
    }

}
