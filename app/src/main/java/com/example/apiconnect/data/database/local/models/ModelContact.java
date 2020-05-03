package com.example.apiconnect.data.database.local.models;

public class ModelContact {

    public ModelContact() {

    }

    public void setNewContact(String JID, String phoneNumber, String username, String lastconnected, String localAvatar, String onlineAvatar, String about, String status, String blocked, String date_added){

        setJID(JID);
        setPhoneNumber(phoneNumber);
        setUsername(username);
        setLastconnected(lastconnected);
        setLocalAvatar(localAvatar);
        setOnlineAvatar(onlineAvatar);
        setAbout(about);
        setStatus(status);
        setBlocked(blocked);
        setDate_added(date_added);
    }

    public String contact_id;
    public String JID;
    public String phoneNumber;
    public String username;
    public String lastconnected;
    public String localAvatar;
    public String onlineAvatar;
    public String about;
    public String status;
    public String blocked;
    public String date_added;

    public String getContact_id() {
        return contact_id;
    }

    public void setContact_id(String contact_id) {
        this.contact_id = contact_id;
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

    public String getLocalAvatar() {
        return localAvatar;
    }

    public void setLocalAvatar(String localAvatar) {
        this.localAvatar = localAvatar;
    }

    public String getOnlineAvatar() {
        return onlineAvatar;
    }

    public void setOnlineAvatar(String onlineAvatar) {
        this.onlineAvatar = onlineAvatar;
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

    public String getBlocked() {
        return blocked;
    }

    public void setBlocked(String blocked) {
        this.blocked = blocked;
    }

    public String getDate_added() {
        return date_added;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }
}
