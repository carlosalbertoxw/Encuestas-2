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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.PollMDL;
import model.UserMDL;
import model.UserProfileMDL;

/**
 *
 * @author carlos
 */
public class PollDAO {

    private DAO dao;

    public PollDAO() {
        dao = new DAO();
    }

    public Integer deletePoll(int pollKey, int userProfileKey) {
        Connection connection = dao.getConnection();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE FROM a_answers WHERE a_poll_key=?");
            preparedStatement.setInt(1, pollKey);
            preparedStatement.executeUpdate();

            PreparedStatement preparedStatement1 = connection
                    .prepareStatement("DELETE FROM a_polls WHERE p_key=? AND p_user_key=?");
            preparedStatement1.setInt(1, pollKey);
            preparedStatement1.setInt(2, userProfileKey);
            preparedStatement1.executeUpdate();
            return 1;
        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
            return 0;
        } finally {
            dao.closeConnection(connection);
        }
    }

    public Integer updatePoll(PollMDL pollMDL) {
        Connection connection = dao.getConnection();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE a_polls SET p_title=?, p_description=?, p_position=? WHERE p_user_key=? AND p_key=?");
            preparedStatement.setString(1, pollMDL.getTitle());
            preparedStatement.setString(2, pollMDL.getDescription());
            preparedStatement.setInt(3, pollMDL.getPosition());
            preparedStatement.setInt(4, pollMDL.getUserProfileMDL().getUserMDL().getKey());
            preparedStatement.setInt(5, pollMDL.getKey());
            preparedStatement.executeUpdate();
            return 1;
        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
            return 0;
        } finally {
            dao.closeConnection(connection);
        }
    }

    public PollMDL getPoll(int pollKey) {
        Connection connection = dao.getConnection();
        PollMDL pollMDL = new PollMDL();
        UserProfileMDL userProfileMDL = new UserProfileMDL();
        UserMDL userMDL = new UserMDL();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM a_polls AS p JOIN a_users_profiles as up ON p.p_user_key=up.u_p_key WHERE p.p_key=?");
            preparedStatement.setInt(1, pollKey);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                pollMDL.setKey(rs.getInt("p_key"));
                pollMDL.setTitle(rs.getString("p_title"));
                pollMDL.setDescription(rs.getString("p_description"));
                pollMDL.setPosition(rs.getInt("p_position"));
                userProfileMDL.setName(rs.getString("u_p_name"));
                userProfileMDL.setUser(rs.getString("u_p_user_name"));
                userMDL.setKey(rs.getInt("p_user_key"));
                userProfileMDL.setUserMDL(userMDL);
                pollMDL.setUserProfileMDL(userProfileMDL);
            }
        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        } finally {
            dao.closeConnection(connection);
        }
        return pollMDL;
    }

    public List<PollMDL> getPolls(int userKey) {
        Connection connection = dao.getConnection();
        List<PollMDL> list = new ArrayList();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM a_polls WHERE p_user_key=? ORDER BY p_position ASC");
            preparedStatement.setInt(1, userKey);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                PollMDL pollMDL = new PollMDL();
                UserProfileMDL userProfileMDL = new UserProfileMDL();
                UserMDL userMDL = new UserMDL();

                pollMDL.setKey(rs.getInt("p_key"));
                pollMDL.setTitle(rs.getString("p_title"));
                pollMDL.setDescription(rs.getString("p_description"));
                pollMDL.setPosition(rs.getInt("p_position"));
                userMDL.setKey(rs.getInt("p_user_key"));
                userProfileMDL.setUserMDL(userMDL);
                pollMDL.setUserProfileMDL(userProfileMDL);
                list.add(pollMDL);
            }
        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        } finally {
            dao.closeConnection(connection);
        }
        return list;
    }

    public Integer addPoll(PollMDL pollMDL) {
        Connection connection = dao.getConnection();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO a_polls(p_title,p_description,p_position,p_user_key) VALUES(?,?,?,?)");
            preparedStatement.setString(1, pollMDL.getTitle());
            preparedStatement.setString(2, pollMDL.getDescription());
            preparedStatement.setInt(3, pollMDL.getPosition());
            preparedStatement.setInt(4, pollMDL.getUserProfileMDL().getUserMDL().getKey());
            preparedStatement.executeUpdate();
            return 1;
        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
            return 0;
        } finally {
            dao.closeConnection(connection);
        }
    }
}
