package dao;

import db.DBConnection;
import java.sql.*;

public class LoginDAO {

    public String[] getMenteeLogin(String email, String password) {

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT m.mentee_id, m.name, u.role " +
                         "FROM mentees m " +
                         "JOIN users u ON m.user_id = u.user_id " +
                         "WHERE u.email=? AND u.password=? AND u.role='mentee'";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return new String[]{
                        String.valueOf(rs.getInt("mentee_id")), // ID
                        rs.getString("name"),                   // username
                        rs.getString("role")                    // role
                };
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // OPTIONAL (for mentor login)
    public String[] getMentorLogin(String email, String password) {

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT me.mentor_id, me.name, u.role " +
                         "FROM mentors me " +
                         "JOIN users u ON me.user_id = u.user_id " +
                         "WHERE u.email=? AND u.password=? AND u.role='mentor'";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return new String[]{
                        String.valueOf(rs.getInt("mentor_id")),
                        rs.getString("name"),
                        rs.getString("role")
                };
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}