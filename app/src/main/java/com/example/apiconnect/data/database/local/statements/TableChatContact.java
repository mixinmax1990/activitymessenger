package com.example.apiconnect.data.database.local.statements;

public class TableChatContact {
    public TableChatContact() {
    }

    public static final String TABLE_NAME= "chat_contact";

    private static final String CHAT_CONTACT_ID = "chat_contact_id";
    private static final String CHAT_ID = "chat_id";
    private static final String CONTACT_ID = "contact_id";



    public static final String CREATE_TABLE_CHAT_CONTACT = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
            + CHAT_CONTACT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + CHAT_ID + " INTEGER,"
            + CONTACT_ID + " INTEGER" +
            ")";

    public static String getChatContactId() {
        return CHAT_CONTACT_ID;
    }

    public static String getChatId() {
        return CHAT_ID;
    }

    public static String getContactId() {
        return CONTACT_ID;
    }
}
