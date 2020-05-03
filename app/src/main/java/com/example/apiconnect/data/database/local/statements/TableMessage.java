package com.example.apiconnect.data.database.local.statements;

public class TableMessage {

    public TableMessage() {
    }

    public static final String TABLE_NAME= "message";


    private static final String MESSAGE_ID = "message_id";
    private static final String SENDER = "sender";
    private static final String TIMESTAMP = "timestamp";
    private static final String TYPE = "type";
    private static final String DELIVERY_STATUS = "delivery_status";
    private static final String MESSAGE_BODY = "message_body";
    private static final String RECIPIENT = "recipient";

    public static final String CREATE_TABLE_MESSAGE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
            + MESSAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + SENDER + " TEXT,"
            + TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + TYPE + " TEXT,"
            + DELIVERY_STATUS + " TEXT,"
            + MESSAGE_BODY + " TEXT,"
            + RECIPIENT + " TEXT" +
            ")";

    public static String getMessageId() {
        return MESSAGE_ID;
    }

    public static String getSender() {
        return SENDER;
    }

    public static String getTIMESTAMP() {
        return TIMESTAMP;
    }

    public static String getTYPE() {
        return TYPE;
    }

    public static String getDeliveryStatus() {
        return DELIVERY_STATUS;
    }

    public static String getMessageBody() {
        return MESSAGE_BODY;
    }

    public static String getRECIPIENT() {
        return RECIPIENT;
    }


}
