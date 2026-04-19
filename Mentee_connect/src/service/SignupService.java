package service;

import dao.UserDAO;
import dao.MenteeDAO;
import dao.MentorDAO;
import model.User;
import model.Mentee;
import model.Mentor;

public class SignupService {

    public void registerMentor(String name, String email, String password, String skills, int exp) {

        UserDAO userDAO = new UserDAO();
        int userId = userDAO.addUser(new User(email, password, "mentor"));

        if (userId != -1) {
            Mentor mentor = new Mentor(name, email, password, skills, exp);

            MentorDAO mentorDAO = new MentorDAO();
            mentorDAO.addMentorWithUserId(mentor, userId);
        }
    }
    
    public void registerMentee(String name, String email, String password, String interests, String goal) {

        UserDAO userDAO = new UserDAO();
        int userId = userDAO.addUser(new User(email, password, "mentee"));

        if (userId != -1) {
            Mentee mentee = new Mentee(name, email, interests, goal);

            MenteeDAO menteeDAO = new MenteeDAO();
            menteeDAO.addMenteeWithUserId(mentee, userId);
        }
    }
}