package com.example.apiconnect.data.database.local.statements;

public class TableContact {


    public TableContact() {
    }

    public static final String TABLE_NAME= "contact";

    private static final String CONTACT_ID = "contact_id";
    private static final String JID = "jid";
    private static final String PHONE_NUMBER = "phonenumber";
    private static final String USERNAME = "username";
    private static final String LAST_CONNECTED = "last_connected";
    private static final String LOCAL_AVATAR = "local_avatar";
    private static final String ONLINE_AVATAR = "online_avatar";
    private static final String ABOUT = "about";
    private static final String STATUS = "status";
    private static final String BLOCKED = "blocked";
    private static final String DATE_ADDED = "date_added";





    public static final String CREATE_TABLE_CONTACT = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
            + CONTACT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + JID + " TEXT,"
            + PHONE_NUMBER + " TEXT,"
            + USERNAME + " TEXT,"
            + LOCAL_AVATAR + " TEXT,"
            + ONLINE_AVATAR + " TEXT,"
            + ABOUT + " TEXT,"
            + STATUS + " TEXT,"
            + BLOCKED + " TEXT,"
            + LAST_CONNECTED + " DATETIME,"
            + DATE_ADDED + " TEXT" +
            ")";

    public static String getJID() {
        return JID;
    }

    public static String getPhoneNumber() {
        return PHONE_NUMBER;
    }

    public static String getUSERNAME() {
        return USERNAME;
    }

    public static String getLastConnected() {
        return LAST_CONNECTED;
    }

    public static String getLocalAvatar() {
        return LOCAL_AVATAR;
    }

    public static String getOnlineAvatar() {
        return ONLINE_AVATAR;
    }

    public static String getABOUT() {
        return ABOUT;
    }

    public static String getSTATUS() {
        return STATUS;
    }

    public static String getBLOCKED() {
        return BLOCKED;
    }

    public static String getDateAdded() {
        return DATE_ADDED;
    }

    public static String getContactId() {
        return CONTACT_ID;
    }
}
