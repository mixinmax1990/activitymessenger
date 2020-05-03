package com.example.apiconnect.data.contact.list;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.apiconnect.R;
import com.example.apiconnect.adapters.PhoneContactsAdapter;
import com.example.apiconnect.data.model.MyContacts;

import java.util.ArrayList;

public class PhoneContacts extends AppCompatActivity {

    RecyclerView recyclerViewPhoneContacts;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_contacts);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerViewPhoneContacts = findViewById(R.id.recyclerPhoneContacts);
        recyclerViewPhoneContacts.setHasFixedSize(true);
        recyclerViewPhoneContacts.setLayoutManager(new LinearLayoutManager(this));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        }
        loadContacts();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                //loadContacts();
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void loadContacts(){


        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,null,null,null,
                ContactsContract.CommonDataKinds.Phone.NUMBER);

        ArrayList<MyContacts> arrayList = new ArrayList<>();

        if(cursor.getCount() > 0){

            while(cursor.moveToNext())
            {
                String id = "2"; //cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                if(number.length() > 0)

                {
                    Cursor phoneCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "-?", new String[]{id}, null);

                    if(phoneCursor.getCount() > 0){

                        while(phoneCursor.moveToNext()){

                            String phoneNumberValue = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                            MyContacts myContacts = new MyContacts(name, phoneNumberValue);

                            arrayList.add(myContacts);

                        }
                    }
                    phoneCursor.close();

                }
            }

            //initialize the adapter and set it to recyclerView

            PhoneContactsAdapter phoneContactsAdapter = new PhoneContactsAdapter(this, arrayList);
            recyclerViewPhoneContacts.setAdapter(phoneContactsAdapter);
            phoneContactsAdapter.notifyDataSetChanged();

        }

        else{

            Toast.makeText(getApplicationContext(), "No Contacts found", Toast.LENGTH_SHORT).show();
        }

    }

}
