package model;

public class Mentee {

    private String name;
    private String email;
    private String interests;
    private String goal;

    public Mentee(String name, String email, String interests, String goal) {
        this.name = name;
        this.email = email;
        this.interests = interests;
        this.goal = goal;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getInterests() { return interests; }
    public String getGoal() { return goal; }
}