/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author carlos
 */
public class UserProfileMDL {

    private UserMDL userMDL;
    private String name;
    private String user;

    public UserMDL getUserMDL() {
        return userMDL;
    }

    public void setUserMDL(UserMDL userMDL) {
        this.userMDL = userMDL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

}
