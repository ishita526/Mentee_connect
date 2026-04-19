package dao;

import db.DBConnection;
import model.User;

import java.sql.*;

public class UserDAO {

    public int addUser(User user) {

        int userId = -1;

        try {
            Connection con = DBConnection.getConnection();

            String query = "INSERT INTO users(email, password, role) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                userId = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return userId;
    }
}