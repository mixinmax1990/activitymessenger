package com.example.apiconnect.data.database.local.models;

public class ModelChatContact {

    private int ChatContactID;
    private int chatID;
    private int contactID;

    public ModelChatContact() {
    }

    public ModelChatContact(int ChatContactID, int chatID, int contactID) {
        this.ChatContactID = ChatContactID;
        this.chatID = chatID;
        this.contactID = contactID;
    }

    public int getChatContactID() {
        return ChatContactID;
    }

    public void setChatContactID(int chatContactID) {
        ChatContactID = chatContactID;
    }

    public int getChatID() {
        return chatID;
    }

    public void setChatID(int chatID) {
        this.chatID = chatID;
    }

    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }
}
