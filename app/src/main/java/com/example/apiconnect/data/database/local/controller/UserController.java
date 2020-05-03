package com.example.apiconnect.data.database.local.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.apiconnect.data.database.local.DatabaseHelper;
import com.example.apiconnect.data.database.local.models.ModelUser;
import com.example.apiconnect.data.database.local.statements.TableUser;

import java.util.ArrayList;
import java.util.List;

public class UserController extends DatabaseHelper {

    private TableUser tableUser;
    private static final String LOG = "DatabaseHelper";


    public UserController(Context context) {
        super(context);

        SQLiteDatabase db = this.getWritableDatabase();
        tableUser = new TableUser();
        db.execSQL(tableUser.getCreateTableUser());

    }

    public long createNewUser(ModelUser user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(tableUser.getJID(), user.getJID());
        values.put(tableUser.getPhoneNumber(), user.getPhoneNumber());
        values.put(tableUser.getUSERNAME(), user.getUsername());
        values.put(tableUser.getLastConnected(), user.getLastconnected());
        values.put(tableUser.getABOUT(), user.getAbout());
        values.put(tableUser.getSTATUS(), user.getStatus());
        values.put(tableUser.getReadReceipt(), user.getReadReceipt());

        long newuser = db.insert(tableUser.TABLE_NAME, null, values);


        return newuser;
    }

    public ModelUser getUser(String JID){
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT  * FROM " + tableUser.getTableName() + " WHERE "
                + tableUser.getJID() + " = " + JID;

        Cursor c = db.rawQuery(selectQuery, null);

        if(c != null){
            c.moveToFirst();
        }

        Log.e(LOG, selectQuery);


        ModelUser user = new ModelUser();
        user.setJID(c.getString(c.getColumnIndex(tableUser.getJID())));
        user.setPhoneNumber(c.getString(c.getColumnIndex(tableUser.getPhoneNumber())));
        user.setUsername(c.getString(c.getColumnIndex(tableUser.getUSERNAME())));
        user.setLastconnected(c.getString(c.getColumnIndex(tableUser.getLastConnected())));
        user.setAbout(c.getString(c.getColumnIndex(tableUser.getABOUT())));
        user.setStatus(c.getString(c.getColumnIndex(tableUser.getSTATUS())));
        user.setLastconnected(c.getString(c.getColumnIndex(tableUser.getLastConnected())));



        return user;
    }

    public List<ModelUser> getAllUsers() {
        SQLiteDatabase db = this.getWritableDatabase();

        List<ModelUser> listUsers = new ArrayList<ModelUser>();

        String selectQuery = "SELECT * FROM " + tableUser.TABLE_NAME;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if(c.moveToFirst()){

            do {
                ModelUser user = new ModelUser();
                user.setJID(c.getString(c.getColumnIndex(tableUser.getJID())));
                user.setPhoneNumber(c.getString(c.getColumnIndex(tableUser.getPhoneNumber())));
                user.setUsername(c.getString(c.getColumnIndex(tableUser.getUSERNAME())));
                user.setLastconnected(c.getString(c.getColumnIndex(tableUser.getLastConnected())));
                user.setAbout(c.getString(c.getColumnIndex(tableUser.getABOUT())));
                user.setStatus(c.getString(c.getColumnIndex(tableUser.getSTATUS())));
                user.setLastconnected(c.getString(c.getColumnIndex(tableUser.getLastConnected())));

                listUsers.add(user);
            }
            while(c.moveToNext());

        }

        return listUsers;

    }

    public int updateUser(ModelUser user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(tableUser.getJID(), user.getJID());
        values.put(tableUser.getPhoneNumber(), user.getPhoneNumber());
        values.put(tableUser.getUSERNAME(), user.getUsername());
        values.put(tableUser.getLastConnected(), user.getLastconnected());
        values.put(tableUser.getABOUT(), user.getAbout());
        values.put(tableUser.getSTATUS(), user.getStatus());
        values.put(tableUser.getReadReceipt(), user.getReadReceipt());

        // updating row
        return db.update(tableUser.TABLE_NAME, values, tableUser.getJID() + " = ?", new String[] { String.valueOf(user.getJID()) });

    }

    public int updateUsername(String UserID, String newName){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(tableUser.getUSERNAME(), newName);
        return db.update(tableUser.TABLE_NAME, cv, tableUser.getUserId() + "= ?", new String[] {UserID});

    }

    public void deleteUser(String JID) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(tableUser.TABLE_NAME, tableUser.getJID() + " = ?",
                new String[] { JID });
    }

}

