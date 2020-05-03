package com.example.apiconnect.data.database.local.models;

public class ModelMessage {
    public ModelMessage() {
    }

    public void setNewMessag(String messageID, String from, String timestamp, String type, String delivery_status, String message_body, String recipient){
        setMessageID(messageID);
        setFrom(from);
        setTimestamp(timestamp);
        setType(type);
        setDelivery_status(delivery_status);
        setMessage_body(message_body);
        setRecipient(recipient);

    }

    public String messageID;
    public String from;
    public String timestamp;
    public String type;
    public String delivery_status;
    public String message_body;
    public String recipient;


    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDelivery_status() {
        return delivery_status;
    }

    public void setDelivery_status(String delivery_status) {
        this.delivery_status = delivery_status;
    }

    public String getMessage_body() {
        return message_body;
    }

    public void setMessage_body(String message_body) {
        this.message_body = message_body;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
}
