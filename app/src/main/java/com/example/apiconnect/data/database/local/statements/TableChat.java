package com.example.apiconnect.data.database.local.statements;

public class TableChat {

    public static final String TABLE_NAME= "chat";

    private static final String CHAT_ID = "chat_id";
    private static final String CHAT_AVATAR = "chat_avatar";
    private static final String CHAT_NAME = "chat_name";
    private static final String CHAT_TYPE = "chat_type";
    private static final String LAST_MESSAGE_ID = "last_message_id";
    private static final String CUSTOM_NOTIFICATIONS = "custom_notification";
    private static final String CHAT_ADMIN = "chat_admin";
    private static final String CHAT_DESCRIPTION = "chat_description";

    public static final String CREATE_TABLE_CHAT = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
            + CHAT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + CHAT_AVATAR + " TEXT,"
            + CHAT_NAME + " TEXT,"
            + CHAT_TYPE + " TEXT,"
            + CHAT_DESCRIPTION + " TEXT,"
            + LAST_MESSAGE_ID + " INTEGER,"
            + CUSTOM_NOTIFICATIONS + " TEXT,"
            + CHAT_ADMIN + " INTEGER" +
            ")";

    public static String getChatId() {
        return CHAT_ID;
    }

    public static String getChatAvatar() {
        return CHAT_AVATAR;
    }

    public static String getChatName() {
        return CHAT_NAME;
    }

    public static String getChatType() {
        return CHAT_TYPE;
    }

    public static String getLastMessageId() {
        return LAST_MESSAGE_ID;
    }

    public static String getCustomNotifications() {
        return CUSTOM_NOTIFICATIONS;
    }

    public static String getChatAdmin() {
        return CHAT_ADMIN;
    }

    public static String getChatDescription() {
        return CHAT_DESCRIPTION;
    }
}
