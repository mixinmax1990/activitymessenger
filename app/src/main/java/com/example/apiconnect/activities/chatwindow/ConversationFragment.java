package com.example.apiconnect.activities.chatwindow;

import android.animation.ValueAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.AsyncLayoutInflater;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apiconnect.R;
import com.example.apiconnect.activities.mainwindow.MainActivity;
import com.example.apiconnect.adapters.IncognitoMessagesListAdapter;
import com.example.apiconnect.adapters.MessagesListAdapter;
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

public class ConversationFragment extends Fragment {

    private String contactJid;
    private EditText mChatEntry;
    private Button mSendButton;
    private TextView mChatView;
    private ImageView mAvatarImage;
    private FrameLayout profileBar;
    private TextView contactTyping;
    private ImageButton emojiButton;
    private ImageButton attachButton;
    private DatabaseController myDB;
    private TextView contactName;
    private ImageButton backButton;
    private Space expandBottom;
    private Space expandTop;
    private RecyclerView chatMessagesRecycler;
    private int globalChatID;
    ArrayList<String> message_id;
    private static MainActivity mainActivity;
    private List<ModelContact> contacts = new ArrayList<>();

    private BroadcastReceiver mBroadcastReceiver;

    public ConversationFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.conversation_layout, container, false);

        globalChatID = Integer.parseInt(getArguments().getString("chat_id"));
        mainActivity = (MainActivity) getActivity();

        myDB = new DatabaseController(getContext());

        AsyncLayoutInflater asyncLayoutInflater = new AsyncLayoutInflater(getContext());

        try {
            asyncLayoutInflater.inflate(R.layout.conversation_content, container, new AsyncLayoutInflater.OnInflateFinishedListener() {
                @Override
                public void onInflateFinished(@NonNull View view, int resid, @Nullable ViewGroup parent) {
                    parent.addView(view);
                    onViewCreated(view, savedInstanceState);


                    findViews(parent);

                    eventListeners();


                    contacts.add(myDB.contact.getContact(getArguments().getString("chat_id")));
                    Picasso.get().load(contacts.get(0).getOnlineAvatar()).into(mAvatarImage);
                    mAvatarImage.setVisibility(View.VISIBLE);
                    contactName.setText(contacts.get(0).getUsername());
                    contactName.setVisibility(View.VISIBLE);

                    //chatHelper = new InstantiateChatHelper();
                    loadMessagesifChatExists(true);

                }
            });
        }
        catch (Exception e){
            Log.i("Asyn","false");
        }



        return root;
    }

    private void findViews(View root){

        mChatEntry = root.findViewById(R.id.chatentry);
        mSendButton = root.findViewById(R.id.send_record_btn);
        mChatView = root.findViewById(R.id.chathistory);
        mAvatarImage = root.findViewById(R.id.chatAvatar);
        contactName = root.findViewById(R.id.c_contact_name);
        backButton = root.findViewById(R.id.c_back_button);
        chatMessagesRecycler = root.findViewById(R.id.chatMessageList);
        profileBar = root.findViewById(R.id.contactProfileBar);
        contactTyping = root.findViewById(R.id.chat_contact_latactiv);
        emojiButton = root.findViewById(R.id.chat_emoji_btn);
        attachButton = root.findViewById(R.id.chat_entry_attach_btn);
        expandBottom = root.findViewById(R.id.expand_bottom);
        expandTop = root.findViewById(R.id.expandTop);

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

    private ModelMessage getMessage(int messageID){
        return myDB.message.getMessage(""+messageID);

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
                    mainActivity.sendBroadcast(intent);


                    // Store Message and Update the Chat

                    createChatContact(message);

                    loadListAdapter();

                    //ModelChat currentChat = myDB.chat.getChat(createChatContact(message));

                    //mChatView.append(message);
                } else {
                    Toast.makeText(mainActivity.getApplicationContext(),
                            "Client not connected to server ,Message not sent!",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

        Intent intent = mainActivity.getIntent();
        contactJid = intent.getStringExtra("EXTRA_CONTACT_JID");


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.onBackPressed();
            }
        });



    }

    @Override
    public void onPause() {
        super.onPause();
        mainActivity.unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    public void onResume() {
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
        mainActivity.registerReceiver(mBroadcastReceiver,filter);


    }


    private void loadListAdapter(){

        // Standard Chat Messages Adapter

        ArrayList<ModelMessage> messages = loadChatMessages();

        MessagesListAdapter adapter = new MessagesListAdapter(getContext(), 0, messages);
        IncognitoMessagesListAdapter incognito_adapter = new IncognitoMessagesListAdapter(getContext(), 0, messages);

        chatMessagesRecycler.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setStackFromEnd(true);
        //llm.setReverseLayout(true);
        chatMessagesRecycler.setLayoutManager(llm);
        // ChatRecycler Scroll Listener

        chatMessagesRecycler.addOnScrollListener(chatRecylerScrollListener());



    }

    private RecyclerView.OnScrollListener chatRecylerScrollListener(){

        final RecyclerView.OnScrollListener scrollListerber = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.i("Scroll State", ""+newState);
                scrollTransparent(newState);

                //0 means no scroll 1 means scrolling
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                //Log.i("I AM Scrolling", "Truuuuue");
            }
        };

        return scrollListerber;

    }

    private void scrollTransparent(int Hide){

        if(Hide == 1){

            toggleChatEntries(true);

           /* mAvatarImage.setAlpha(0f);
            mSendButton.setAlpha(0f);
            mChatEntry.setAlpha(0f);
            contactName.setAlpha(0f);
            backButton.setAlpha(0f);
            profileBar.setAlpha(0f);
            contactTyping.setAlpha(0f);
            emojiButton.setAlpha(0f);
            attachButton.setAlpha(0f);*/

        }
        else{

            toggleChatEntries(false);
            /*
            mAvatarImage.setAlpha(1f);
            mSendButton.setAlpha(1f);
            mChatEntry.setAlpha(1f);
            contactName.setAlpha(1f);
            backButton.setAlpha(1f);
            profileBar.setAlpha(1f);
            contactTyping.setAlpha(1f);
            emojiButton.setAlpha(1f);
            attachButton.setAlpha(1f);*/
        }

    }

    private void toggleChatEntries(boolean state){
        ValueAnimator vaBottom;
        ValueAnimator vaTop;
        final ConstraintLayout.LayoutParams bottomLP = (ConstraintLayout.LayoutParams) expandBottom.getLayoutParams();
        final ConstraintLayout.LayoutParams topLP = (ConstraintLayout.LayoutParams) expandTop.getLayoutParams();





        if(state){
            vaBottom = ValueAnimator.ofInt((int)mainActivity.convertDpToPixel(55), 0);
            vaTop = ValueAnimator.ofInt((int)mainActivity.convertDpToPixel(65), 0);


        }
        else{
            vaBottom = ValueAnimator.ofInt(0, (int)mainActivity.convertDpToPixel(55));
            vaTop = ValueAnimator.ofInt(0, (int)mainActivity.convertDpToPixel(65));
        }

        int duration = 200;

        vaBottom.setDuration(duration);
        vaTop.setDuration(duration);

        vaBottom.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                int animValue = Math.round((int) animation.getAnimatedValue());

                bottomLP.bottomMargin = animValue;
                expandBottom.setLayoutParams(bottomLP);

            }
        });

        vaTop.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                int animValue = Math.round((int) animation.getAnimatedValue());

                topLP.topMargin = animValue;
                expandTop.setLayoutParams(topLP);
            }
        });



        vaBottom.start();
        vaTop.start();



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

        Log.i("NEW Global", "--- "+chatID);

        if(chatID != 0){

            globalChatID = chatID;

            loadListAdapter();

        }

    }

}
