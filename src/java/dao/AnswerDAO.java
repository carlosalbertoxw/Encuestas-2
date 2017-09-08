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
import model.AnswerMDL;
import model.PollMDL;
import model.UserMDL;
import model.UserProfileMDL;

/**
 *
 * @author carlos
 */
public class AnswerDAO {

    private static AnswerDAO instance;
    private static Connection connection;

    private AnswerDAO() {
        connection = DAO.getConnection();
    }

    public static AnswerDAO getInstance() {
        if (instance == null) {
            instance = new AnswerDAO();
        }
        return instance;
    }

    public List<AnswerMDL> getAnswers(int userKey, int pollKey) {
        List<AnswerMDL> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM a_answers WHERE a_poll_key=? AND a_user_key=?");
            preparedStatement.setInt(1, pollKey);
            preparedStatement.setInt(2, userKey);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                UserProfileMDL userProfileMDL = new UserProfileMDL();
                UserMDL userMDL = new UserMDL();
                PollMDL pollMDL = new PollMDL();
                AnswerMDL answerMDL = new AnswerMDL();

                answerMDL.setKey(rs.getInt("a_key"));
                answerMDL.setStars(rs.getInt("a_stars"));
                answerMDL.setComment(rs.getString("a_comment"));
                userMDL.setKey(rs.getInt("a_user_key"));
                userProfileMDL.setUserMDL(userMDL);
                answerMDL.setUserProfileMDL(userProfileMDL);
                pollMDL.setKey(rs.getInt("a_poll_key"));
                answerMDL.setPollMDL(pollMDL);
                list.add(answerMDL);
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public Integer addAnswer(AnswerMDL answerMDL) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO a_answers(a_stars,a_comment,a_poll_key,a_user_key) VALUES(?,?,?,?)");
            preparedStatement.setInt(1, answerMDL.getStars());
            preparedStatement.setString(2, answerMDL.getComment());
            preparedStatement.setInt(3, answerMDL.getPollMDL().getKey());
            preparedStatement.setInt(4, answerMDL.getUserProfileMDL().getUserMDL().getKey());
            preparedStatement.executeUpdate();
            return 1;
        } catch (SQLException e) {
            return 0;
        }
    }
}
