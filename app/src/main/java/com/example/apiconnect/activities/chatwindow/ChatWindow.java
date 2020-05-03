package com.example.apiconnect.activities.chatwindow;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apiconnect.R;
import com.example.apiconnect.adapters.IncognitoMessagesListAdapter;
import com.example.apiconnect.adapters.MessagesListAdapter;
import com.example.apiconnect.adapters.models.ModelChatMessages;
import com.example.apiconnect.data.database.local.InstantiateChatHelper;
import com.example.apiconnect.data.database.local.controller.DatabaseController;
import com.example.apiconnect.data.database.local.models.ModelChat;
import com.example.apiconnect.data.database.local.models.ModelChatContact;
import com.example.apiconnect.data.database.local.models.ModelChatMessage;
import com.example.apiconnect.data.database.local.models.ModelContact;
import com.example.apiconnect.data.database.local.models.ModelMessage;
import com.example.apiconnect.jabber.connect.APIConnectionService;
import com.example.apiconnect.jabber.connect.JabberConnection;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ChatWindow extends AppCompatActivity {

    private String contactJid;
    private EditText mChatEntry;
    private Button mSendButton;
    private TextView mChatView;
    private CardView mAvatarCard;
    private ImageView mAvatarImage;
    private DatabaseController myDB;
    private TextView contactName;
    private ImageButton backButton;
    private List<ModelContact> contacts = new ArrayList<>();
    private int globalChatID = 0;
    private int DisplayWidth;
    private ChatWindowAnimator chatWindowAnimator;
    Window window;


    // Incognito Items
    private Button mIncognitoButton;
    private RecyclerView incognitoChatMessagesRecycler;
    private boolean incognitoOpen = false;
    private FrameLayout incognito_hider;
    ConstraintLayout constraintLayout;
    ConstraintSet constraintSet;


    //SliderViews

    private FrameLayout sliderFrame;

    //ChatListMessages

    private RecyclerView chatMessagesRecycler;
    ArrayList<String> message_id;
    InstantiateChatHelper chatHelper;

    private BroadcastReceiver mBroadcastReceiver;
    private ConstraintLayout chatwindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_window);



        findViews();
        instatiate();

        setWhiteSysBar();

        //Temporary functions

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        DisplayWidth = size.x;

        ConstraintLayout.LayoutParams IncogntioRVLP = (ConstraintLayout.LayoutParams) incognitoChatMessagesRecycler.getLayoutParams();
        ConstraintLayout.LayoutParams stdChatRVLP = (ConstraintLayout.LayoutParams) chatMessagesRecycler.getLayoutParams();
        IncogntioRVLP.width = DisplayWidth;
        stdChatRVLP.width = DisplayWidth;

        incognitoChatMessagesRecycler.setLayoutParams(IncogntioRVLP);
        chatMessagesRecycler.setLayoutParams(stdChatRVLP);

        ConstraintLayout.LayoutParams sliderFrameLP = (ConstraintLayout.LayoutParams) sliderFrame.getLayoutParams();

        sliderFrameLP.width = DisplayWidth;
        sliderFrame.setLayoutParams(sliderFrameLP);


        //--------------------------------
        eventListeners();
        Intent intent = getIntent();
        myDB = new DatabaseController(this);



        //if we received a contact_id check for chat
        if(intent.hasExtra("contact_id")){

            String received_contact_id = intent.getStringExtra("contact_id");
            contacts.add(myDB.contact.getContact(received_contact_id));
            Picasso.get().load(contacts.get(0).getOnlineAvatar()).into(mAvatarImage);
            contactName.setText(contacts.get(0).getUsername());

            //chatHelper = new InstantiateChatHelper();
            loadMessagesifChatExists(true);

        }

        //if we received a chat_id load the chat

        if(intent.hasExtra("chat_id")) {

            globalChatID = Integer.parseInt(intent.getStringExtra("chat_id"));
            ModelChat localChat = myDB.chat.getChat(globalChatID);
            List<Integer> contactsIDs = new ArrayList<>();
            contactsIDs = myDB.contact_to_chat.getContactsFromChat(""+globalChatID);

            // make contact a List
            for(Integer contactid: contactsIDs){

                contacts.add(myDB.contact.getContact(contactid.toString()));
            }

            Picasso.get().load(localChat.getChatAvatar()).into(mAvatarImage);
            contactName.setText(localChat.getChatName());

            loadListAdapter();

        }

    }
    public void setWhiteSysBar(){


        toggleStatusBar();
    }



    private void toggleStatusBar() {

        if(!incognitoOpen){

            setLightStatusBar(window.getDecorView(),this);

        }
        else{

           clearLightStatusBar(this);

        }

    }

    public static void setLightStatusBar(View view,Activity activity){


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            activity.getWindow().setStatusBarColor(Color.WHITE);
        }
    }

    public static void clearLightStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = activity.getWindow();
            window.setStatusBarColor(ContextCompat
                    .getColor(activity,R.color.incognitoBaseBG));

            window.getDecorView().setSystemUiVisibility(0);
        }
    }

    private void findViews(){

        chatwindow = findViewById(R.id.chat_window);
        mChatEntry = findViewById(R.id.chatentry);
        mSendButton = findViewById(R.id.send_record_btn);
        mChatView = findViewById(R.id.chathistory);
        mAvatarCard = findViewById(R.id.CImageCard);
        mAvatarImage = findViewById(R.id.CAvatar);
        contactName = findViewById(R.id.c_contact_name);
        backButton = findViewById(R.id.c_back_button);
        chatMessagesRecycler = findViewById(R.id.chatMessageList);
        incognitoChatMessagesRecycler = findViewById(R.id.incognitoChatMessageList);
        mIncognitoButton = findViewById(R.id.incognito_mode_btn);
        sliderFrame = findViewById(R.id.sliderFrame);
        incognito_hider = findViewById(R.id.incog_hider);


        //chatMessagesList.setDivider(null); //Removes border Bottom
    }

    private void instatiate(){

        chatWindowAnimator = new ChatWindowAnimator();
        window = this.getWindow();
    }
    private void eventListeners() {

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Only send the message if the client is connected
                //to the server.
                if (APIConnectionService.getState().equals(JabberConnection.ConnectionState.CONNECTED)) {
                    Log.d("Tag", "The client is connected to the server,Sending Message");
                    //Send the message to the server
                    String message = mChatEntry.getText().toString();

                    Intent intent = new Intent(APIConnectionService.SEND_MESSAGE);
                    intent.putExtra(APIConnectionService.BUNDLE_MESSAGE_BODY,
                            message);
                    intent.putExtra(APIConnectionService.BUNDLE_TO, contacts.get(0).getJID());
                    sendBroadcast(intent);


                    // Store Message and Update the Chat

                    createChatContact(message);

                    loadListAdapter();

                    //ModelChat currentChat = myDB.chat.getChat(createChatContact(message));

                    //mChatView.append(message);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Client not connected to server ,Message not sent!",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

        Intent intent = getIntent();
        contactJid = intent.getStringExtra("EXTRA_CONTACT_JID");
        setTitle(contactJid);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mIncognitoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openIncognito();
            }
        });

    }



    private void openIncognito() {
        //Slide Open Ingognito Mode
        if(incognitoOpen){

            chatWindowAnimator.slideChats(sliderFrame, 0f, (float) DisplayWidth);
            contactName.setTextColor(Color.BLACK);
            incognitoOpen = false;
            incognitoHiderMove();
            toggleStatusBar();

        }
        else{
            chatWindowAnimator.slideChats(sliderFrame, (float) DisplayWidth, 0f);
            incognitoOpen = true;
            incognitoHiderMove();
            contactName.setTextColor(Color.WHITE);
            toggleStatusBar();
        }


    }



    private void incognitoHiderMove(){

        constraintSet = new ConstraintSet();
        constraintSet.clone(chatwindow);

        if(!incognitoOpen) {
            constraintSet.connect(mIncognitoButton.getId(), ConstraintSet.LEFT, chatMessagesRecycler.getId(), ConstraintSet.LEFT, 0);
            //constraintSet.clear(mIncognitoButton.getId(), ConstraintSet.TOP);
            //constraintSet.connect(mIncognitoButton.getId(), ConstraintSet.BOTTOM, R.id.send_record_btn, ConstraintSet.TOP, 0);
            constraintSet.clear(mIncognitoButton.getId(), ConstraintSet.RIGHT);
        }
        else{
            constraintSet.connect(mIncognitoButton.getId(), ConstraintSet.RIGHT, incognitoChatMessagesRecycler.getId(), ConstraintSet.RIGHT, 0);
            //constraintSet.connect(mIncognitoButton.getId(), ConstraintSet.TOP, R.id.chatentry, ConstraintSet.TOP, 0);
            //constraintSet.connect(mIncognitoButton.getId(), ConstraintSet.BOTTOM, R.id.chatentry, ConstraintSet.BOTTOM, 0);
            constraintSet.clear(mIncognitoButton.getId(), ConstraintSet.LEFT);
        }
        constraintSet.applyTo(chatwindow);
    }


    private void loadListAdapter(){

        // Standard Chat Messages Adapter

        ArrayList<ModelMessage> messages = loadChatMessages();

        MessagesListAdapter adapter = new MessagesListAdapter(this, 0, messages);
        IncognitoMessagesListAdapter incognito_adapter = new IncognitoMessagesListAdapter(this, 0, messages);

        chatMessagesRecycler.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        //llm.setReverseLayout(true);
        chatMessagesRecycler.setLayoutManager(llm);


        // Incognito Chat Messages Adapter

        incognitoChatMessagesRecycler.setAdapter(incognito_adapter);
        LinearLayoutManager llmIncog = new LinearLayoutManager(this);
        llmIncog.setStackFromEnd(true);
        //llm.setReverseLayout(true);
        incognitoChatMessagesRecycler.setLayoutManager(llmIncog);

    }
    private ModelMessage getMessage(int messageID){
        return myDB.message.getMessage(""+messageID);

    }

    private ArrayList<ModelMessage> loadChatMessages(){

        ArrayList<ModelMessage> messages = new ArrayList<>();

        //List of ID
        message_id = new ArrayList<>();

        List<ModelChatMessage> allMessages = myDB.message_to_chat.getMessagesFromChat(globalChatID);
        for(ModelChatMessage chatTOmessage: allMessages){
            ModelMessage message = getMessage(chatTOmessage.getMessageID());

            //Store Contact Id into Array
            message_id.add(""+ message.getMessageID());
            messages.add(message);
        }



        return messages;

    }



    private void storeChatMessage(String message){

        ModelMessage modelMessage = new ModelMessage();

        modelMessage.setMessageID("");
        modelMessage.setFrom("me");
        modelMessage.setTimestamp("");
        modelMessage.setType("text");
        modelMessage.setDelivery_status("sent");
        modelMessage.setMessage_body(message);
        modelMessage.setRecipient(contacts.get(0).getJID());

        long messageID = myDB.message.sendNewMessage(modelMessage);


        //Link Chat to message
        if(messageID > 0) {
            //Message Successfully Stored
             ModelChatMessage modelChatMessage = new ModelChatMessage();

             modelChatMessage.setMessageID((int)messageID);
             modelChatMessage.setChatID(globalChatID);

             long newChatMessageID = myDB.message_to_chat.linkChatToMessage(modelChatMessage);

            Log.d("DatabaseController", ""+newChatMessageID);

            Log.d("Update Latest Message", "Id Before "+ myDB.chat.getChat(globalChatID).getLastMessageID() + " , Message ID = " +messageID +" , Chat ID = " +globalChatID  );
            int update = myDB.chat.updateLatestMessage((int)messageID, globalChatID);

            //Log.d("Update Latest Message", "Did it Update = "+ update);
            Log.d("Update Latest Message", "Id After "+ myDB.chat.getChat(globalChatID).getLastMessageID());


             // Update UI to show saved message

        }
        else{
            Log.d("DatabaseController", "Error! Message couldnt be Stored");

        }

    }

    private void loadMessagesifChatExists(boolean isLaunch){
        int chatID = myDB.contact_to_chat.getChatFromContact(contacts.get(0).getContact_id());

        if(chatID != 0){

            globalChatID = chatID;

            loadListAdapter();

        }

    }

    private void createChatContact(String message){

        //SQL Create a New Chat Locally if one with this User doesn't exist

        if(globalChatID != 0){
            //A Chat exits

            storeChatMessage(message);

        }
        else{


            // Start a new Chat

            ModelChat newChat = new ModelChat();

            newChat.setChat_id("");
            newChat.setChatAvatar(contacts.get(0).getOnlineAvatar());
            newChat.setChatName(contacts.get(0).getUsername());
            newChat.setChatType("text");
            newChat.setLastMessageID(1);
            newChat.setCustomNotifications("");
            newChat.setAdmin("");
            newChat.setChatDescription("");

            //retrieve the ChatID


            int newChatID = (int) myDB.chat.startNewChat(newChat);
            globalChatID = newChatID;

            //link Contact to Chat

            ModelChatContact linkChatToContact = new ModelChatContact();

            linkChatToContact.setChatID(newChatID);
            linkChatToContact.setContactID(Integer.parseInt(contacts.get(0).getContact_id()));

            long chatContactID = myDB.contact_to_chat.linkChatToContact(linkChatToContact);

            storeChatMessage(message);



        }

    }




    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                switch (action)
                {
                    case APIConnectionService.NEW_MESSAGE:
                        String from = intent.getStringExtra(APIConnectionService.BUNDLE_FROM_JID);
                        String body = intent.getStringExtra(APIConnectionService.BUNDLE_MESSAGE_BODY);

                        if ( from.equals(contactJid))
                        {
                            //mChatView.setText(body);
                            loadListAdapter();

                        }else
                        {
                           // Log.d("Hello","Got a message from jid :"+from);
                            //mChatView.append(body);
                            loadListAdapter();
                        }

                        return;
                }

            }
        };

        IntentFilter filter = new IntentFilter(APIConnectionService.NEW_MESSAGE);
        registerReceiver(mBroadcastReceiver,filter);


    }


}
