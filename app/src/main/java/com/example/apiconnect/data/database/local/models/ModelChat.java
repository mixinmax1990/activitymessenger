package com.example.apiconnect.data.database.local.models;

public class ModelChat {
    public ModelChat() {
    }

    public void setNewChat(String ChatID, String chatAvatar, String chatName, String chatType, int lastMessageID, String customNotifications, String admin, String chatDescription){

        setChat_id(ChatID);
        setChatAvatar(chatAvatar);
        setChatName(chatName);
        setChatType(chatType);
        setLastMessageID(lastMessageID);
        setCustomNotifications(customNotifications);
        setAdmin(admin);
        setChatDescription(chatDescription);


    }

    public String chat_id;
    public String chatAvatar;
    public String chatName;
    public String chatType;
    public int lastMessageID;
    public String customNotifications;
    public String admin;
    public String chatDescription;

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }

    public String getChatAvatar() {
        return chatAvatar;
    }

    public void setChatAvatar(String chatAvatar) {
        this.chatAvatar = chatAvatar;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public String getChatType() {
        return chatType;
    }

    public void setChatType(String chatType) {
        this.chatType = chatType;
    }

    public int getLastMessageID() {
        return lastMessageID;
    }

    public void setLastMessageID(int lastMessageID) {
        this.lastMessageID = lastMessageID;
    }

    public String getCustomNotifications() {
        return customNotifications;
    }

    public void setCustomNotifications(String customNotifications) {
        this.customNotifications = customNotifications;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getChatDescription() {
        return chatDescription;
    }

    public void setChatDescription(String chatDescription) {
        this.chatDescription = chatDescription;
    }
}
