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
import model.User;

/**
 *
 * @author carlos
 */
public class PublicDAO {

    private static PublicDAO instance;
    private static Connection connection;

    private PublicDAO() {
        connection = DAO.getConnection();
    }

    public static PublicDAO getInstance() {
        if (instance == null) {
            instance = new PublicDAO();
        }
        return instance;
    }

    public UserProfile profile(String user) {
        UserProfile profile = new UserProfile();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM j_user_profile WHERE u_p_user=?");
            preparedStatement.setString(1, user);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                profile.setId(rs.getInt("u_p_key"));
                profile.setName(rs.getString("u_p_name"));
                profile.setUser(rs.getString("u_p_user"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return profile;
    }

    public UserProfile signIn(String email) {
        UserProfile profile = new UserProfile();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM j_user AS u JOIN j_user_profile AS up ON u.u_key=up.u_p_key WHERE u.u_email=?");
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                profile.setId(rs.getInt("u_key"));
                profile.setEmail(rs.getString("u_email"));
                profile.setPassword(rs.getString("u_password"));
                profile.setName(rs.getString("u_p_name"));
                profile.setUser(rs.getString("u_p_user"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return profile;
    }

    public Integer getIdSignUp(String email) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM j_user WHERE u_email=?");
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt("u_key");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer signUp(User user) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO j_user(u_email,u_password) VALUES(?,?)");
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();
            int id = this.getIdSignUp(user.getEmail());
            PreparedStatement preparedStatement2 = connection
                    .prepareStatement("INSERT INTO j_user_profile(u_p_key,u_p_user,u_p_name) VALUES(?,?,?)");
            preparedStatement2.setInt(1, id);
            preparedStatement2.setString(2, "usuario" + id);
            preparedStatement2.setString(3, "Usuario" + id);
            preparedStatement2.executeUpdate();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
