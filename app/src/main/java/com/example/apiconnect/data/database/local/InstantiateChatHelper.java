package com.example.apiconnect.data.database.local;

import android.content.Context;

import com.example.apiconnect.data.database.local.controller.DatabaseController;
import com.example.apiconnect.data.database.local.models.ModelContact;

public class InstantiateChatHelper {
    DatabaseController myDB;

    public InstantiateChatHelper(Context context) {
        myDB = new DatabaseController(context);
    }

    public int createChat(ModelContact contact){

        return myDB.contact_to_chat.getChatFromContact(contact.getContact_id());
    }
}
