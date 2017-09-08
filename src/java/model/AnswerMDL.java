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
public class AnswerMDL {

    private int key;
    private int stars;
    private String comment;
    private PollMDL pollMDL;
    private UserProfileMDL userProfileMDL;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public PollMDL getPollMDL() {
        return pollMDL;
    }

    public void setPollMDL(PollMDL pollMDL) {
        this.pollMDL = pollMDL;
    }

    public UserProfileMDL getUserProfileMDL() {
        return userProfileMDL;
    }

    public void setUserProfileMDL(UserProfileMDL userProfileMDL) {
        this.userProfileMDL = userProfileMDL;
    }

}
