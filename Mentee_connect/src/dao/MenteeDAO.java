package dao;

import db.DBConnection;
import model.Mentee;
import java.sql.*;

public class MenteeDAO {

    public void addMenteeWithUserId(Mentee mentee, int userId) {

        try {
            Connection con = DBConnection.getConnection();

            String query = "INSERT INTO mentees(name, email, interests, goal, user_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, mentee.getName());
            ps.setString(2, mentee.getEmail());
            ps.setString(3, mentee.getInterests());
            ps.setString(4, mentee.getGoal());
            ps.setInt(5, userId);

            ps.executeUpdate();

            System.out.println("Mentee registered successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Fetches current mentee details as a String array.
     * Returns: [name, email, interests, goal]
     */
    public String[] getMenteeDetails(int menteeId) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "SELECT name, email, interests, goal FROM mentees WHERE mentee_id = ?"
            );
            ps.setInt(1, menteeId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new String[]{
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("interests"),
                    rs.getString("goal")
                };
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Updates name, email, interests, goal for the given mentee.
     * Returns true on success.
     */
    public boolean updateMentee(int menteeId, String name, String email, String interests, String goal) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "UPDATE mentees SET name=?, email=?, interests=?, goal=? WHERE mentee_id=?"
            );
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, interests);
            ps.setString(4, goal);
            ps.setInt(5, menteeId);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}