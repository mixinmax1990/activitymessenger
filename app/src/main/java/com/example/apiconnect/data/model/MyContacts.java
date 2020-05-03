package com.example.apiconnect.data.model;

public class MyContacts {



    String name,number;

    public MyContacts(String Name, String Number) {
        this.name = Name;
        this.number = Number;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }
}
