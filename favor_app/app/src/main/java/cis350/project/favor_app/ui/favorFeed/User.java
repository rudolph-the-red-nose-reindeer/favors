package cis350.project.favor_app.ui.favorFeed;

class User {
    String uname;
    String uemail;
    String upassword;
    String uphoto;
    int urating;
    int upoints;
    public User(String name, String email, String password, String photo, int rating, int points) {
        uname = name;
        uemail = email;
        upassword = password;
        uphoto = photo;
        urating = rating;
        upoints = points;
    }
}
