package com.example.apiconnect.data.database.local.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import androidx.annotation.Nullable;
import com.example.apiconnect.data.database.local.DatabaseHelper;
import com.example.apiconnect.data.database.local.models.ModelChatContact;
import com.example.apiconnect.data.database.local.statements.TableChatContact;

import java.util.ArrayList;
import java.util.List;


public class ChatContactController  extends DatabaseHelper {
    private TableChatContact tableChatContact;
    private static final String LOG = "DatabaseHelper";


    public ChatContactController(@Nullable Context context) {
        super(context);

        SQLiteDatabase db = this.getWritableDatabase();
        tableChatContact = new TableChatContact();
        db.execSQL(tableChatContact.CREATE_TABLE_CHAT_CONTACT);


    }

    public long linkChatToContact(ModelChatContact chatcontact){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(tableChatContact.getChatId(), chatcontact.getChatID());
        values.put(tableChatContact.getContactId(), chatcontact.getContactID());


        long data = db.insert(tableChatContact.TABLE_NAME, null, values);
        return data;

    }


    public void deleteChatContactLink(String ChatContactID) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(tableChatContact.TABLE_NAME, tableChatContact.getChatContactId() + " = ?",
                new String[] { ChatContactID });
    }

    public int getChatFromContact(String ContactID){
        SQLiteDatabase db = this.getWritableDatabase();

        Log.d("SQL QUery", "" + ContactID);
        Log.d("SQL QUery", "TABLE NAME = " + tableChatContact.getContactId());


        String selectQuery = " SELECT * FROM " + tableChatContact.TABLE_NAME + " WHERE "
                + tableChatContact.getContactId() + " = " + Integer.parseInt(ContactID);

        Cursor c = db.rawQuery(selectQuery, null);

                 // Don't forget to close your cursor



        if(c.moveToFirst()){

            Log.d("SQL QUery", "" + c.getInt(c.getColumnIndex(tableChatContact.getChatId())));
            return c.getInt(c.getColumnIndex(tableChatContact.getChatId()));

        }
        else{
            return 0;
        }
       // AND your Database!


    }
    public List<Integer> getContactsFromChat(String ChatID){
        SQLiteDatabase db = this.getWritableDatabase();
        List<Integer> contactIDs = new ArrayList<>();

        String selectQuery = " SELECT * FROM " + tableChatContact.TABLE_NAME + " WHERE "
                + tableChatContact.getChatId() + " = " + Integer.parseInt(ChatID);

        Cursor c = db.rawQuery(selectQuery, null);

        // Don't forget to close your cursor



        if(c.moveToFirst()){

            do{
                contactIDs.add(c.getInt(c.getColumnIndex(tableChatContact.getContactId())));
            }
            while(c.moveToNext());

        }

        return contactIDs;

    }


    public List<ModelChatContact> getAllChatContacts(){
        SQLiteDatabase db = this.getWritableDatabase();

        List<ModelChatContact> listChatContact = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + tableChatContact.TABLE_NAME;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if(c.moveToFirst()){

            do {

                ModelChatContact retievedChatContact = new ModelChatContact();

                retievedChatContact.setChatContactID(c.getInt(c.getColumnIndex(tableChatContact.getChatContactId())));
                retievedChatContact.setChatID(c.getInt(c.getColumnIndex(tableChatContact.getChatId())));
                retievedChatContact.setContactID(c.getInt(c.getColumnIndex(tableChatContact.getContactId())));


                listChatContact.add(retievedChatContact);
            }
            while(c.moveToNext());
        }

        return listChatContact;


    }
}
