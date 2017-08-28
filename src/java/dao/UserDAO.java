/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.UserProfile;

/**
 *
 * @author carlos
 */
public class UserDAO {

    private static UserDAO instance;
    private static Connection connection;

    private UserDAO() {
        connection = DAO.getConnection();
    }

    public static UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    public String getUserPassword(int id) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM j_user WHERE u_key=?");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getString("u_password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer deleteAccount(UserProfile profile) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE FROM j_user_profile WHERE u_p_key=?");
            preparedStatement.setInt(1, profile.getId());
            preparedStatement.executeUpdate();
            PreparedStatement preparedStatement2 = connection
                    .prepareStatement("DELETE FROM j_user WHERE u_key=?");
            preparedStatement2.setInt(1, profile.getId());
            preparedStatement2.executeUpdate();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public Integer changePassword(UserProfile profile) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE j_user SET u_password=? WHERE u_key=?");
            preparedStatement.setString(1, profile.getPassword());
            preparedStatement.setInt(2, profile.getId());
            preparedStatement.executeUpdate();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public Integer changeEmail(UserProfile profile) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE j_user SET u_email=? WHERE u_key=?");
            preparedStatement.setString(1, profile.getEmail());
            preparedStatement.setInt(2, profile.getId());
            preparedStatement.executeUpdate();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public Integer editProfile(UserProfile profile) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE j_user_profile SET u_p_name=?, u_p_user=? WHERE u_p_key=?");
            preparedStatement.setString(1, profile.getName());
            preparedStatement.setString(2, profile.getUser());
            preparedStatement.setInt(3, profile.getId());
            preparedStatement.executeUpdate();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
