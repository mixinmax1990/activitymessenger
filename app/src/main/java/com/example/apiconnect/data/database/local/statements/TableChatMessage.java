package com.example.apiconnect.data.database.local.statements;

public class TableChatMessage {
    public TableChatMessage() {
    }

    public static final String TABLE_NAME= "chat_message";

    private static final String CHAT_MESSAGE_ID = "chat_message_id";
    private static final String CHAT_ID = "chat_id";
    private static final String MESSAGE_ID = "message_id";

    public static final String CREATE_TABLE_CHAT_MESSAGE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
            + CHAT_MESSAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + CHAT_ID + " INTEGER,"
            + MESSAGE_ID + " INTEGER" +
            ")";

    public static String getChatMessageId() {
        return CHAT_MESSAGE_ID;
    }

    public static String getChatId() {
        return CHAT_ID;
    }

    public static String getMessageId() {
        return MESSAGE_ID;
    }
}
