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
import java.util.logging.Level;
import java.util.logging.Logger;
import model.UserMDL;
import model.UserProfileMDL;

/**
 *
 * @author carlos
 */
public class UserDAO {

    public UserProfileMDL profile(String profile) {
        Connection connection = DAO.getConnection();
        UserProfileMDL userProfileMDL = new UserProfileMDL();
        UserMDL userMDL = new UserMDL();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM a_users_profiles WHERE u_p_user=?");
            preparedStatement.setString(1, profile);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                userMDL.setKey(rs.getInt("u_p_key"));
                userProfileMDL.setUserMDL(userMDL);
                userProfileMDL.setName(rs.getString("u_p_name"));
                userProfileMDL.setUser(rs.getString("u_p_user"));
            }
        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        } finally {
            DAO.closeConnection(connection);
        }
        return userProfileMDL;
    }

    public String getUserPassword(int id) {
        Connection connection = DAO.getConnection();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM a_users WHERE u_key=?");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getString("u_password");
            }
        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        } finally {
            DAO.closeConnection(connection);
        }
        return null;
    }

    public Integer deleteAccount(UserMDL userMDL) {
        Connection connection = DAO.getConnection();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE FROM a_users_profiles WHERE u_p_key=?");
            preparedStatement.setInt(1, userMDL.getKey());
            preparedStatement.executeUpdate();
            PreparedStatement preparedStatement2 = connection
                    .prepareStatement("DELETE FROM a_users WHERE u_key=?");
            preparedStatement2.setInt(1, userMDL.getKey());
            preparedStatement2.executeUpdate();
            return 1;
        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
            return 0;
        } finally {
            DAO.closeConnection(connection);
        }
    }

    public Integer changePassword(UserMDL userMDL) {
        Connection connection = DAO.getConnection();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE a_users SET u_password=? WHERE u_key=?");
            preparedStatement.setString(1, userMDL.getPassword());
            preparedStatement.setInt(2, userMDL.getKey());
            preparedStatement.executeUpdate();
            return 1;
        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
            return 0;
        } finally {
            DAO.closeConnection(connection);
        }
    }

    public Integer changeUser(UserProfileMDL userProfileMDL) {
        Connection connection = DAO.getConnection();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE a_users_profiles SET u_p_user=? WHERE u_p_key=?");
            preparedStatement.setString(1, userProfileMDL.getUser());
            preparedStatement.setInt(2, userProfileMDL.getUserMDL().getKey());
            preparedStatement.executeUpdate();
            return 1;
        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
            return 0;
        } finally {
            DAO.closeConnection(connection);
        }
    }

    public Integer changeEmail(UserMDL userMDL) {
        Connection connection = DAO.getConnection();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE a_users SET u_email=? WHERE u_key=?");
            preparedStatement.setString(1, userMDL.getEmail());
            preparedStatement.setInt(2, userMDL.getKey());
            preparedStatement.executeUpdate();
            return 1;
        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
            return 0;
        } finally {
            DAO.closeConnection(connection);
        }
    }

    public Integer editProfile(UserProfileMDL profile) {
        Connection connection = DAO.getConnection();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE a_users_profiles SET u_p_name=? WHERE u_p_key=?");
            preparedStatement.setString(1, profile.getName());
            preparedStatement.setInt(2, profile.getUserMDL().getKey());
            preparedStatement.executeUpdate();
            return 1;
        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
            return 0;
        } finally {
            DAO.closeConnection(connection);
        }
    }

    public UserProfileMDL signIn(String email) {
        Connection connection = DAO.getConnection();
        UserProfileMDL profile = new UserProfileMDL();
        UserMDL userMDL = new UserMDL();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM a_users AS u JOIN a_users_profiles AS up ON u.u_key=up.u_p_key WHERE u.u_email=?");
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                userMDL.setEmail(rs.getString("u_email"));
                userMDL.setPassword(rs.getString("u_password"));
                userMDL.setKey(rs.getInt("u_key"));
                profile.setUserMDL(userMDL);
                profile.setName(rs.getString("u_p_name"));
                profile.setUser(rs.getString("u_p_user"));
            }
        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        } finally {
            DAO.closeConnection(connection);
        }
        return profile;
    }

    public Integer getIdSignUp(String email) {
        Connection connection = DAO.getConnection();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM a_users WHERE u_email=?");
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt("u_key");
            }
        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        } finally {
            DAO.closeConnection(connection);
        }
        return null;
    }

    public Integer signUp(UserMDL user) {
        Connection connection = DAO.getConnection();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO a_users(u_email,u_password) VALUES(?,?)");
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();
            int id = this.getIdSignUp(user.getEmail());
            PreparedStatement preparedStatement2 = connection
                    .prepareStatement("INSERT INTO a_users_profiles(u_p_key,u_p_user,u_p_name) VALUES(?,?,?)");
            preparedStatement2.setInt(1, id);
            preparedStatement2.setString(2, "usuario" + id);
            preparedStatement2.setString(3, "Usuario" + id);
            preparedStatement2.executeUpdate();
            return 1;
        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
            return 0;
        } finally {
            DAO.closeConnection(connection);
        }
    }
}
