package com.example.apiconnect.data.database.local.statements;

public class TableUser {

    public static final String TABLE_NAME= "user";

    private static final String USER_ID = "user_id";
    private static final String JID = "jid";
    private static final String PHONE_NUMBER = "phonenumber";
    private static final String USERNAME = "username";
    private static final String LAST_CONNECTED= "last_connected";
    private static final String ABOUT = "about";
    private static final String STATUS = "status";
    private static final String READ_RECEIPT = "read_receipt";




    public static final String CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
            + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + JID + " TEXT,"
            + PHONE_NUMBER + " TEXT,"
            + USERNAME + " TEXT,"
            + ABOUT + " TEXT,"
            + STATUS + " TEXT,"
            + READ_RECEIPT + " TEXT,"
            + LAST_CONNECTED + " DATETIME" +
            ")";

    public static String getUserId() {
        return USER_ID;
    }

    public String getTableName() {
        return TABLE_NAME;
    }

    public static String getCreateTableUser() {
        return CREATE_TABLE_USER;
    }


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

    public static String getABOUT() {
        return ABOUT;
    }

    public static String getSTATUS() {
        return STATUS;
    }

    public static String getReadReceipt() {
        return READ_RECEIPT;
    }

}
