package com.example.apiconnect.data.database.local.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.apiconnect.data.database.local.DatabaseHelper;
import com.example.apiconnect.data.database.local.models.ModelContact;
import com.example.apiconnect.data.database.local.models.ModelUser;
import com.example.apiconnect.data.database.local.statements.TableContact;

import java.util.ArrayList;
import java.util.List;


public class ContactController extends DatabaseHelper{
    private TableContact tableContact;
    private static final String LOG = "DatabaseHelper";


    public ContactController(Context context) {
        super(context);
        tableContact = new TableContact();
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(tableContact.CREATE_TABLE_CONTACT);

    }

    public long createNewContact(ModelContact contact){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(tableContact.getJID(), contact.getJID());
        values.put(tableContact.getPhoneNumber(), contact.getPhoneNumber());
        values.put(tableContact.getUSERNAME(), contact.getUsername());
        values.put(tableContact.getLastConnected(), contact.getLastconnected());
        values.put(tableContact.getLocalAvatar(), contact.getLocalAvatar());
        values.put(tableContact.getOnlineAvatar(), contact.getOnlineAvatar());
        values.put(tableContact.getABOUT(), contact.getAbout());
        values.put(tableContact.getSTATUS(), contact.getStatus());
        values.put(tableContact.getBLOCKED(), contact.getBlocked());
        values.put(tableContact.getDateAdded(), contact.getDate_added());

        long newcontact = db.insert(tableContact.TABLE_NAME, null, values);

        return newcontact;
    }

    public ModelContact getContact(String ContactID){
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT  * FROM " + tableContact.TABLE_NAME + " WHERE "
                + tableContact.getContactId() + " = " + ContactID;

        Cursor c = db.rawQuery(selectQuery, null);

        if(c != null){
            c.moveToFirst();
        }

        Log.e(LOG, selectQuery);


        ModelContact contact = new ModelContact();
        contact.setContact_id(c.getString(c.getColumnIndex(tableContact.getContactId())));
        contact.setJID(c.getString(c.getColumnIndex(tableContact.getJID())));
        contact.setPhoneNumber(c.getString(c.getColumnIndex(tableContact.getPhoneNumber())));
        contact.setUsername(c.getString(c.getColumnIndex(tableContact.getUSERNAME())));
        contact.setLastconnected(c.getString(c.getColumnIndex(tableContact.getLastConnected())));
        contact.setLocalAvatar(c.getString(c.getColumnIndex(tableContact.getLocalAvatar())));
        contact.setOnlineAvatar(c.getString(c.getColumnIndex(tableContact.getOnlineAvatar())));
        contact.setAbout(c.getString(c.getColumnIndex(tableContact.getABOUT())));
        contact.setStatus(c.getString(c.getColumnIndex(tableContact.getSTATUS())));
        contact.setBlocked(c.getString(c.getColumnIndex(tableContact.getBLOCKED())));
        contact.setDate_added(c.getString(c.getColumnIndex(tableContact.getDateAdded())));


        return contact;
    }

    public int getContactIDFromPhonenumber(String Phonenumber){
        SQLiteDatabase db = this.getWritableDatabase();

        int contactID = 0;

        String selectQuery = "SELECT  * FROM " + tableContact.TABLE_NAME + " WHERE "
                + tableContact.getPhoneNumber() + " = " + Phonenumber;

        Cursor c = db.rawQuery(selectQuery, null);

        if(c != null){
            c.moveToFirst();
            contactID = c.getInt(c.getColumnIndex(tableContact.getContactId()));
        }

        return contactID;
    }

    public List<ModelContact> getAllContacts() {
        SQLiteDatabase db = this.getWritableDatabase();

        List<ModelContact> listContacts = new ArrayList<ModelContact>();

        String selectQuery = "SELECT * FROM " + tableContact.TABLE_NAME;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if(c.moveToFirst()){

            do {

                ModelContact contact = new ModelContact();
                contact.setContact_id(c.getString(c.getColumnIndex(tableContact.getContactId())));
                contact.setJID(c.getString(c.getColumnIndex(tableContact.getJID())));
                contact.setPhoneNumber(c.getString(c.getColumnIndex(tableContact.getPhoneNumber())));
                contact.setUsername(c.getString(c.getColumnIndex(tableContact.getUSERNAME())));
                contact.setLastconnected(c.getString(c.getColumnIndex(tableContact.getLastConnected())));
                contact.setLocalAvatar(c.getString(c.getColumnIndex(tableContact.getLocalAvatar())));
                contact.setOnlineAvatar(c.getString(c.getColumnIndex(tableContact.getOnlineAvatar())));
                contact.setAbout(c.getString(c.getColumnIndex(tableContact.getABOUT())));
                contact.setStatus(c.getString(c.getColumnIndex(tableContact.getSTATUS())));
                contact.setBlocked(c.getString(c.getColumnIndex(tableContact.getBLOCKED())));
                contact.setDate_added(c.getString(c.getColumnIndex(tableContact.getDateAdded())));

                listContacts.add(contact);
            }
            while(c.moveToNext());

        }
        return listContacts;

    }

    public int updateContact(ModelContact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(tableContact.getJID(), contact.getJID());
        values.put(tableContact.getPhoneNumber(), contact.getPhoneNumber());
        values.put(tableContact.getUSERNAME(), contact.getUsername());
        values.put(tableContact.getLastConnected(), contact.getLastconnected());
        values.put(tableContact.getLocalAvatar(), contact.getLocalAvatar());
        values.put(tableContact.getOnlineAvatar(), contact.getOnlineAvatar());
        values.put(tableContact.getABOUT(), contact.getAbout());
        values.put(tableContact.getSTATUS(), contact.getStatus());
        values.put(tableContact.getBLOCKED(), contact.getBlocked());
        values.put(tableContact.getDateAdded(), contact.getDate_added());

        // updating row
        return db.update(tableContact.TABLE_NAME, values, contact.getContact_id() + " = ?", new String[] { String.valueOf(contact.getContact_id()) });

    }

    public void deleteContact(String ContactID) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(tableContact.TABLE_NAME, tableContact.getContactId() + " = ?",
                new String[] { ContactID });
    }



}
