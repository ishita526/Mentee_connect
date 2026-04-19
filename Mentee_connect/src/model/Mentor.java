package model;

public class Mentor {

    private String name;
    private String email;
    private String password;
    private String skills;
    private int experience;

    public Mentor(String name, String email, String password, String skills, int experience) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.skills = skills;
        this.experience = experience;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getSkills() { return skills; }
    public int getExperience() { return experience; }
}