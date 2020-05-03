package com.example.apiconnect.data.database.local.models;

public class ModelChatMessage {

    public ModelChatMessage() {
    }

    public void setLinkMessage(int ChatMessageID, int chatID, int messageID){
        setChatMessageID(ChatMessageID);
        setChatID(chatID);
        setMessageID(messageID);

    }

    public int ChatMessageID;
    public int chatID;
    public int messageID;

    public int getChatMessageID() {
        return ChatMessageID;
    }

    public void setChatMessageID(int chatMessageID) {
        this.ChatMessageID = chatMessageID;
    }

    public int getChatID() {
        return chatID;
    }

    public void setChatID(int chatID) {
        this.chatID = chatID;
    }

    public int getMessageID() {
        return messageID;
    }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }
}
