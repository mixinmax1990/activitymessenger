package com.example.apiconnect.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.apiconnect.R;
import com.example.apiconnect.activities.chatwindow.ChatWindow;
import com.example.apiconnect.adapters.AvailableContactsListAdapter;
import com.example.apiconnect.adapters.models.ModelAvailableContacts;
import com.example.apiconnect.data.database.local.controller.DatabaseController;
import com.example.apiconnect.data.database.local.models.ModelContact;

import java.util.ArrayList;
import java.util.List;

public class AvailableContacts extends AppCompatActivity {

    DatabaseController myDB;
    ListView availableContactsLV;
    ArrayList<String> contact_id;
    ImageButton backButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.available_contacts);
        setWhiteSysBar();

        myDB = new DatabaseController(this);
        availableContactsLV = findViewById(R.id.availablContactsList);
        backButton = findViewById(R.id.ac_back_button);

        //storeInitContactsInDB();
        setlisteners();
        loadListAdapter();
    }

    private void setlisteners() {

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void loadListAdapter(){

        ArrayList<ModelAvailableContacts> contacts = loadAvailableContacts();

        AvailableContactsListAdapter adapter = new AvailableContactsListAdapter(this, 0,contacts);

        availableContactsLV.setAdapter(adapter);
        availableContactsLV.setDivider(null);

        availableContactsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.d("Tag Name", "Hey Im clicked" + contact_id.get(position));
                //Start Chat Activity and Pass the User ID along
                startChatWindow(view, parent, contact_id.get(position));
            }
        });
    }

    public void setWhiteSysBar(){

        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        window.setStatusBarColor(getResources().getColor(R.color.colorWhite));
    }

    private ArrayList<ModelAvailableContacts> loadAvailableContacts(){

        ArrayList<ModelAvailableContacts> availablecontactsArray = new ArrayList<>();

        //List of ID
        contact_id = new ArrayList<>();

        List<ModelContact> allContacts = myDB.contact.getAllContacts();
        for(ModelContact contact: allContacts){
            ModelAvailableContacts listModel = new ModelAvailableContacts(contact.getOnlineAvatar(), contact.getContact_id(), contact.getUsername(), contact.getPhoneNumber(), contact.getLastconnected());

            //Store Contact Id into Array
            contact_id.add(contact.getContact_id());
            availablecontactsArray.add(listModel);
        }

        return availablecontactsArray;


    }

    public void startChatWindow(View view, View root, String contact_id){

        Intent intent = new Intent(root.getContext(), ChatWindow.class);
        intent.putExtra("contact_id", contact_id);
        startActivity(intent);


    }

    private void storeInitContactsInDB(){

        //myDB.contact.deleteContact("5");

        List<ModelContact> contacts = initialContacts();


        for(ModelContact contact: contacts){
             myDB.contact.createNewContact(contact);

        }



        List<ModelContact> allContacts = myDB.contact.getAllContacts();
        for(ModelContact contact: allContacts){
            Log.d("Contact Name", contact.getUsername() + " - " + contact.getContact_id());
        }


    }

    private List<ModelContact> initialContacts(){

        List<ModelContact> listArray = new ArrayList<>();

        ModelContact usr1 = new ModelContact();
        usr1.setContact_id("");
        usr1.setJID("mixinmax@ec2-3-124-124-253.eu-central-1.compute.amazonaws.com");
        usr1.setPhoneNumber("mixinmax");
        usr1.setUsername("Mixinmax");
        usr1.setLastconnected("today");
        usr1.setLocalAvatar("");
        usr1.setOnlineAvatar("http://p.favim.com/orig/2018/11/01/beauty-tatts-guys-Favim.com-6508418.jpg");
        usr1.setAbout("Just do It");
        usr1.setStatus("Just talk");
        usr1.setBlocked("");
        usr1.setDate_added("today");

        listArray.add(usr1);

        ModelContact usr2 = new ModelContact();
        usr2.setContact_id("");
        usr2.setJID("65467654@ec2-3-124-124-253.eu-central-1.compute.amazonaws.com");
        usr2.setPhoneNumber("65467654");
        usr2.setUsername("Stacey");
        usr2.setLastconnected("today");
        usr2.setLocalAvatar("");
        usr2.setOnlineAvatar("http://p.favim.com/orig/2018/10/22/girls-inspiration-profile-Favim.com-6484318.jpg");
        usr2.setAbout("Just do It");
        usr2.setStatus("Just talk");
        usr2.setBlocked("");
        usr2.setDate_added("today");

        listArray.add(usr2);

        ModelContact usr3 = new ModelContact();
        usr3.setContact_id("");
        usr3.setJID("989898@ec2-3-124-124-253.eu-central-1.compute.amazonaws.com");
        usr3.setPhoneNumber("989898");
        usr3.setUsername("Samantha");
        usr3.setLastconnected("today");
        usr3.setLocalAvatar("");
        usr3.setOnlineAvatar("http://p.favim.com/orig/2018/09/12/syoutubers-pretty-8039s-Favim.com-6290206.jpg");
        usr3.setAbout("Just do It");
        usr3.setStatus("Just talk");
        usr3.setBlocked("");
        usr3.setDate_added("today");

        listArray.add(usr3);

        ModelContact usr4 = new ModelContact();
        usr4.setContact_id("");
        usr4.setJID("123456@ec2-3-124-124-253.eu-central-1.compute.amazonaws.com");
        usr4.setPhoneNumber("123456");
        usr4.setUsername("Tiffany");
        usr4.setLastconnected("today");
        usr4.setLocalAvatar("");
        usr4.setOnlineAvatar("http://s1.favim.com/orig/150915/clothes-cute-girl-picture-Favim.com-3301940.jpg");
        usr4.setAbout("Just do It");
        usr4.setStatus("Just talk");
        usr4.setBlocked("");
        usr4.setDate_added("today");

        listArray.add(usr4);

        ModelContact usr5 = new ModelContact();
        usr5.setContact_id("");
        usr5.setJID("danny@ec2-3-124-124-253.eu-central-1.compute.amazonaws.com");
        usr5.setPhoneNumber("123456");
        usr5.setUsername("Danny");
        usr5.setLastconnected("today");
        usr5.setLocalAvatar("");
        usr5.setOnlineAvatar("http://p.favim.com/orig/2018/10/22/i-want-kicks-red-Favim.com-6469228.jpg");
        usr5.setAbout("Just do It");
        usr5.setStatus("Just talk");
        usr5.setBlocked("");
        usr5.setDate_added("today");

        listArray.add(usr5);

        return listArray;




        //setNewContact("","54321","Alice","today","","https://d30td31q9ky6fi.cloudfront.net/users/401436ca664507f54ab89de7ccf8e0d771457526d87c66e8c41953802c75fe0d/profile/4","","","","");

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }




}
