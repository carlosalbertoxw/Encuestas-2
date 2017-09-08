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
public class PollMDL {

    private int key;
    private String title;
    private String description;
    private int position;
    private UserProfileMDL userProfileMDL;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public UserProfileMDL getUserProfileMDL() {
        return userProfileMDL;
    }

    public void setUserProfileMDL(UserProfileMDL userProfileMDL) {
        this.userProfileMDL = userProfileMDL;
    }

}
