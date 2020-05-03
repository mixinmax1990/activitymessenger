package com.example.apiconnect.adapters.models;

public class ModelAvailableContacts {

    public String avatar;
    public String id;
    public String name;
    public String phonenumber;
    public String lastactive;

    public ModelAvailableContacts(String avatar, String id, String name, String phonenumber, String lastactive) {
        this.avatar = avatar;
        this.id = id;
        this.name = name;
        this.phonenumber = phonenumber;
        this.lastactive = lastactive;
    }
}
