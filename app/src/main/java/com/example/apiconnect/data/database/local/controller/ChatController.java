package com.example.apiconnect.data.database.local.controller;
import android.app.DownloadManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.apiconnect.data.database.local.DatabaseHelper;
import com.example.apiconnect.data.database.local.models.ModelChat;
import com.example.apiconnect.data.database.local.statements.TableChat;


import java.util.ArrayList;
import java.util.List;

public class ChatController extends DatabaseHelper {

    private TableChat tableChat;
    private static final String LOG = "DatabaseHelper";

    public ChatController(@Nullable Context context) {
        super(context);

        tableChat = new TableChat();
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(tableChat.CREATE_TABLE_CHAT);
    }





    public long startNewChat(ModelChat chat){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(tableChat.getChatAvatar(), chat.getChatAvatar());
        values.put(tableChat.getChatName(), chat.getChatName());
        values.put(tableChat.getChatType(), chat.getChatType());
        values.put(tableChat.getLastMessageId(), chat.getLastMessageID());
        values.put(tableChat.getCustomNotifications(), chat.getCustomNotifications());
        values.put(tableChat.getChatAdmin(), chat.getAdmin());
        values.put(tableChat.getChatDescription(), chat.getChatDescription());


        long newchat = db.insert(tableChat.TABLE_NAME, null, values);


        return newchat;
    }

    public ModelChat getChat(int ChatID){
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT  * FROM " + tableChat.TABLE_NAME + " WHERE "
                + tableChat.getChatId() + " = " + ChatID;

        Cursor c = db.rawQuery(selectQuery, null);

        if(c != null){
            c.moveToFirst();
        }

        Log.e(LOG, selectQuery);


        ModelChat chat = new ModelChat();

        chat.setChat_id(c.getString(c.getColumnIndex(tableChat.getChatId())));
        chat.setChatAvatar(c.getString(c.getColumnIndex(tableChat.getChatAvatar())));
        chat.setChatName(c.getString(c.getColumnIndex(tableChat.getChatName())));
        chat.setChatType(c.getString(c.getColumnIndex(tableChat.getChatType())));
        chat.setLastMessageID(c.getColumnIndex(tableChat.getLastMessageId()));
        chat.setCustomNotifications(c.getString(c.getColumnIndex(tableChat.getCustomNotifications())));
        chat.setAdmin(c.getString(c.getColumnIndex(tableChat.getChatAdmin())));
        chat.setChatDescription(c.getString(c.getColumnIndex(tableChat.getChatDescription())));


        return chat;
    }

    public List<ModelChat> getAllChats() {
        SQLiteDatabase db = this.getWritableDatabase();

        List<ModelChat> listChats = new ArrayList<ModelChat>();

        String selectQuery = "SELECT * FROM " + tableChat.TABLE_NAME;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if(c.moveToFirst()){

            do {


                ModelChat chat = new ModelChat();

                chat.setChat_id(c.getString(c.getColumnIndex(tableChat.getChatId())));
                chat.setChatAvatar(c.getString(c.getColumnIndex(tableChat.getChatAvatar())));
                chat.setChatName(c.getString(c.getColumnIndex(tableChat.getChatName())));
                chat.setChatType(c.getString(c.getColumnIndex(tableChat.getChatType())));
                chat.setLastMessageID(c.getColumnIndex(tableChat.getLastMessageId()));
                chat.setCustomNotifications(c.getString(c.getColumnIndex(tableChat.getCustomNotifications())));
                chat.setAdmin(c.getString(c.getColumnIndex(tableChat.getChatAdmin())));
                chat.setChatDescription(c.getString(c.getColumnIndex(tableChat.getChatDescription())));

                listChats.add(chat);
            }
            while(c.moveToNext());

        }


        return listChats;

    }

    public int updateChat(ModelChat chat) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(tableChat.getChatAvatar(), chat.getChatAvatar());
        values.put(tableChat.getChatName(), chat.getChatName());
        values.put(tableChat.getChatType(), chat.getChatType());
        values.put(tableChat.getLastMessageId(), chat.getLastMessageID());
        values.put(tableChat.getCustomNotifications(), chat.getCustomNotifications());
        values.put(tableChat.getChatAdmin(), chat.getAdmin());
        values.put(tableChat.getChatDescription(), chat.getChatDescription());

        // updating row

        return db.update(tableChat.TABLE_NAME, values, chat.getChat_id() + " = ?", new String[] { String.valueOf(chat.getChat_id()) });

    }

    public int updateLatestMessage(int messageID, int chatID){
        Log.d("DB", "messageid =" + messageID);  // messageID = 175
        Log.d("DB", "chatid =" + chatID); // chatID = 5

        testUpdate(chatID, messageID); // Testing if this Updates and it does update

        SQLiteDatabase db = this.getWritableDatabase();

        try {
            ContentValues cv = new ContentValues();
            cv.put("last_message_id", messageID);

            int result = db.update("chat", cv, "chat_id = ?", new String[]{String.valueOf(chatID)});
            db.close();
            return result;

        }
        catch(Exception e) {
            Log.d("DB", "Error: "+e.toString());
            return 0;
        }

    }

    public void testUpdate(int chatID, int messageID){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("chat_description", ""+messageID);

        db.update("chat", cv, "chat_id = ?", new String[]{String.valueOf(chatID)});
        db.close();
        return;


    }



    public void deleteChat(String ChatID) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(tableChat.TABLE_NAME, tableChat.getChatId() + " = ?",
                new String[] { ChatID });

    }




}
