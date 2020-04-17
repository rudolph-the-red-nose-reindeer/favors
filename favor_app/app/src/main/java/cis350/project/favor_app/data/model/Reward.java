package cis350.project.favor_app.data.model;

public class Reward {
    private String rewardId;
    private String name;
    private String photo;
    private String description;
    private int points;

    public Reward(String rewardId, String name, String photo, String description, int points) {
        this.rewardId = rewardId;
        this.name = name;
        this.photo = photo;
        this.description = description;
        this.points = points;
    }

    public String getRewardId() {
        return rewardId;
    }

    public String getName() {
        return name;
    }

    public String getPhoto() {
        return photo;
    }

    public String getDescription() {
        return description;
    }

    public int getPoints() {
        return points;
    }

}
