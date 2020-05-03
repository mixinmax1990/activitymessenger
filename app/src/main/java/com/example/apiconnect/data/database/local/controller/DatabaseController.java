package com.example.apiconnect.data.database.local.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseController{

    public UserController user;
    public ContactController contact;
    public ChatController chat;
    public MessageController message;
    public ChatMessageController message_to_chat;
    public ChatContactController contact_to_chat;


    public DatabaseController(Context context) {
        this.user = new UserController(context);
        this.contact = new ContactController(context);
        this.chat = new ChatController(context);
        this.message = new MessageController(context);
        this.contact_to_chat = new ChatContactController(context);
        this.message_to_chat = new ChatMessageController(context);

    }
}
