/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author carlos
 */
public class DAO {

    private static Connection connection = null;

    private DAO() {

    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/JSPServletsTemplate", "root", "1029384756");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

}
