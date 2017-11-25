package com.trilogy.turnitup;

public class UserID {
    public String Uid;
    public int score;

    public UserID(String Uid, int score) {
        this.Uid = Uid;
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public String getUid() {
        return Uid;
    }
}
