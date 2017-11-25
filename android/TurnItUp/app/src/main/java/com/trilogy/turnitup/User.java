package com.trilogy.turnitup;

import java.util.ArrayList;
import java.util.HashMap;

public class User {

    public String username;
    public String email;
    public String joindate;
    public String birthdate;
    public HashMap<String, Integer> highscore = new HashMap<>();
    public String notificationToken;
    public ArrayList<Boolean> lockedSong;

    public User() {
    }

    public User(String username, String email, String joindate, String birthdate, HashMap highscore, String notificationToken) {
        this.username = username;
        this.email = email;
        this.joindate = joindate;
        this.birthdate = birthdate;
        this.highscore = highscore;
        this.notificationToken = notificationToken;
        lockedSong = new ArrayList<>();
        lockedSong.add(true);
        lockedSong.add(true);
        lockedSong.add(true);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJoindate() {
        return joindate;
    }

    public void setJoindate(String joindate) {
        this.joindate = joindate;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public HashMap<String, Integer> getHighscore() {
        return highscore;
    }

    public void setHighscore(HashMap<String, Integer> highscore) {
        this.highscore = highscore;
    }

    public String getNotificationToken() {
        return notificationToken;
    }

    public void setNotificationToken(String notificationToken) {
        this.notificationToken = notificationToken;
    }

    public ArrayList<Boolean> getLockedSong() {
        return lockedSong;
    }

    public void setLockedSong(ArrayList<Boolean> lockedSong) {
        this.lockedSong = lockedSong;
    }

    public int getHighscore(String song) {
        return Integer.valueOf(highscore.get(song));
    }

    public void setHighscore(String song, int score) {
        highscore.put(song, score);
    }

}