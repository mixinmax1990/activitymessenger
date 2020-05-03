package com.example.apiconnect.data.database.local.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.apiconnect.data.database.local.DatabaseHelper;
import com.example.apiconnect.data.database.local.models.ModelChat;
import com.example.apiconnect.data.database.local.models.ModelChatMessage;
import com.example.apiconnect.data.database.local.models.ModelMessage;
import com.example.apiconnect.data.database.local.statements.TableChatMessage;

import java.util.ArrayList;
import java.util.List;


public class ChatMessageController  extends DatabaseHelper {
    private TableChatMessage tableChatMessage;
    private static final String LOG = "DatabaseHelper";


    public ChatMessageController(@Nullable Context context) {
        super(context);

        SQLiteDatabase db = this.getWritableDatabase();
        tableChatMessage = new TableChatMessage();
        db.execSQL(tableChatMessage.CREATE_TABLE_CHAT_MESSAGE);

    }

    public long linkChatToMessage(ModelChatMessage chatMessage){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(tableChatMessage.getChatId(), chatMessage.getChatID());
        values.put(tableChatMessage.getMessageId(), chatMessage.getMessageID());


        return db.insert(tableChatMessage.TABLE_NAME, null, values);

    }


    public void deleteChatMessageLink(String ChatMessageID) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(tableChatMessage.TABLE_NAME, tableChatMessage.getChatMessageId() + " = ?",
                new String[] { ChatMessageID });
    }



    public List<ModelChatMessage> getAllMessagesChatLinks(){
        SQLiteDatabase db = this.getWritableDatabase();

        List<ModelChatMessage> listMessages = new ArrayList<ModelChatMessage>();

        String selectQuery = "SELECT * FROM " + tableChatMessage.TABLE_NAME;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if(c.moveToFirst()){

            do {

                ModelChatMessage retievedMessages = new ModelChatMessage();

                retievedMessages.setChatID(c.getInt(c.getColumnIndex(tableChatMessage.getChatId())));
                retievedMessages.setMessageID(c.getInt(c.getColumnIndex(tableChatMessage.getMessageId())));


                listMessages.add(retievedMessages);
            }
            while(c.moveToNext());
        }
        return listMessages;
    }


    public List<ModelChatMessage> getMessagesFromChat(int chatID){
        SQLiteDatabase db = this.getWritableDatabase();

        List<ModelChatMessage> listMessages = new ArrayList<ModelChatMessage>();

        String selectQuery = "SELECT * FROM " + tableChatMessage.TABLE_NAME + " WHERE "
                + tableChatMessage.getChatId() + " LIKE " + chatID;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if(c.moveToFirst()){

            do {

                ModelChatMessage retievedMessages = new ModelChatMessage();

                retievedMessages.setChatID(c.getInt(c.getColumnIndex(tableChatMessage.getChatId())));
                retievedMessages.setMessageID(c.getInt(c.getColumnIndex(tableChatMessage.getMessageId())));


                listMessages.add(retievedMessages);
            }
            while(c.moveToNext());
        }
        return listMessages;
    }
}