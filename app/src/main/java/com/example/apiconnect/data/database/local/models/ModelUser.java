package com.example.apiconnect.data.database.local.models;

public class ModelUser {

    public ModelUser(){


    }

    public void setNewUser( String JID, String phoneNumber, String username, String lastconnected, String about, String status, String readReceipt){

        setJID(JID);
        setPhoneNumber(phoneNumber);
        setUsername(username);
        setLastconnected(lastconnected);
        setAbout(about);
        setStatus(status);
        setReadReceipt(readReceipt);

    }

    public String user_id;
    public String JID;
    public String phoneNumber;
    public String username;
    public String lastconnected;
    public String about;
    public String status;
    public String readReceipt;


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getJID() {
        return JID;
    }

    public void setJID(String JID) {
        this.JID = JID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastconnected() {
        return lastconnected;
    }

    public void setLastconnected(String lastconnected) {
        this.lastconnected = lastconnected;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReadReceipt() {
        return readReceipt;
    }

    public void setReadReceipt(String readReceipt) {
        this.readReceipt = readReceipt;
    }
}
