package com.example.apiconnect.data.database.local.controller;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.apiconnect.data.database.local.DatabaseHelper;
import com.example.apiconnect.data.database.local.models.ModelMessage;
import com.example.apiconnect.data.database.local.statements.TableMessage;


import java.util.ArrayList;
import java.util.List;

public class MessageController extends DatabaseHelper {

    private TableMessage tableMessage;
    private static final String LOG = "DatabaseHelper";

    public MessageController(@Nullable Context context) {
        super(context);

        tableMessage = new TableMessage();
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(tableMessage.CREATE_TABLE_MESSAGE);
    }



    public long sendNewMessage(ModelMessage message){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(tableMessage.getSender(), message.getFrom());
        //values.put(tableMessage.getTIMESTAMP(), message.getTimestamp());
        values.put(tableMessage.getTYPE(), message.getType());
        values.put(tableMessage.getDeliveryStatus(), message.getDelivery_status());
        values.put(tableMessage.getMessageBody(), message.getMessage_body());
        values.put(tableMessage.getRECIPIENT(), message.getRecipient());



        long newmessage = db.insert(tableMessage.TABLE_NAME, null, values);

        return newmessage;
    }

    public ModelMessage getMessage(String MessageID){
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT  * FROM " + tableMessage.TABLE_NAME + " WHERE "
                + tableMessage.getMessageId() + " = " + MessageID;

        Cursor c = db.rawQuery(selectQuery, null);

        if(c != null){
            c.moveToFirst();
        }

        Log.e(LOG, selectQuery);


        ModelMessage message = new ModelMessage();

        message.setMessageID(c.getString(c.getColumnIndex(tableMessage.getMessageId())));
        message.setFrom(c.getString(c.getColumnIndex(tableMessage.getSender())));
        message.setTimestamp(c.getString(c.getColumnIndex(tableMessage.getTIMESTAMP())));
        message.setType(c.getString(c.getColumnIndex(tableMessage.getTYPE())));
        message.setDelivery_status(c.getString(c.getColumnIndex(tableMessage.getDeliveryStatus())));
        message.setMessage_body(c.getString(c.getColumnIndex(tableMessage.getMessageBody())));
        message.setRecipient(c.getString(c.getColumnIndex(tableMessage.getRECIPIENT())));

c.close();
db.close();
        return message;
    }

    public List<ModelMessage> getAllMessage() {
        SQLiteDatabase db = this.getWritableDatabase();

        List<ModelMessage> listMessages = new ArrayList<ModelMessage>();

        String selectQuery = "SELECT * FROM " + tableMessage.TABLE_NAME;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if(c.moveToFirst()){

            do {


                ModelMessage message = new ModelMessage();

                message.setMessageID(c.getString(c.getColumnIndex(tableMessage.getMessageId())));
                message.setFrom(c.getString(c.getColumnIndex(tableMessage.getSender())));
                message.setTimestamp(c.getString(c.getColumnIndex(tableMessage.getTIMESTAMP())));
                message.setType(c.getString(c.getColumnIndex(tableMessage.getTYPE())));
                message.setDelivery_status(c.getString(c.getColumnIndex(tableMessage.getDeliveryStatus())));
                message.setMessage_body(c.getString(c.getColumnIndex(tableMessage.getMessageBody())));
                message.setRecipient(c.getString(c.getColumnIndex(tableMessage.getRECIPIENT())));

                listMessages.add(message);
            }
            while(c.moveToNext());

        }
        return listMessages;

    }

    public int updateChat(ModelMessage message) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(tableMessage.getSender(), message.getFrom());
        values.put(tableMessage.getTIMESTAMP(), message.getTimestamp());
        values.put(tableMessage.getTYPE(), message.getType());
        values.put(tableMessage.getDeliveryStatus(), message.getDelivery_status());
        values.put(tableMessage.getMessageBody(), message.getMessage_body());
        values.put(tableMessage.getRECIPIENT(), message.getRecipient());

        // updating row
        return db.update(tableMessage.TABLE_NAME, values, message.getMessageID() + " = ?", new String[] { String.valueOf(message.getMessageID()) });

    }



    public void deleteMessage(String MessageID) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(tableMessage.TABLE_NAME, tableMessage.getMessageId() + " = ?",
                new String[] { MessageID });
    }

    public ModelMessage getLatestMessage(String SenderID){

        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT  * FROM " + tableMessage.TABLE_NAME + " WHERE "
                + tableMessage.getSender() + " = " + SenderID + " ORDER BY "+ tableMessage.getTIMESTAMP() +" ASC ";

        Cursor c = db.rawQuery(selectQuery, null);

        if(c != null){
            c.moveToFirst();
        }

        Log.e(LOG, selectQuery);


        ModelMessage message = new ModelMessage();

        message.setMessageID(c.getString(c.getColumnIndex(tableMessage.getMessageId())));
        message.setFrom(c.getString(c.getColumnIndex(tableMessage.getSender())));
        message.setTimestamp(c.getString(c.getColumnIndex(tableMessage.getTIMESTAMP())));
        message.setType(c.getString(c.getColumnIndex(tableMessage.getTYPE())));
        message.setDelivery_status(c.getString(c.getColumnIndex(tableMessage.getDeliveryStatus())));
        message.setMessage_body(c.getString(c.getColumnIndex(tableMessage.getMessageBody())));
        message.setRecipient(c.getString(c.getColumnIndex(tableMessage.getRECIPIENT())));

        c.close();
        db.close();
        return message;
    }



}
