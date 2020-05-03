package com.example.apiconnect.jabber.connect;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.apiconnect.data.database.local.controller.DatabaseController;
import com.example.apiconnect.data.database.local.models.ModelChat;
import com.example.apiconnect.data.database.local.models.ModelChatMessage;
import com.example.apiconnect.data.database.local.models.ModelContact;
import com.example.apiconnect.data.database.local.models.ModelMessage;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.ReconnectionManager;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.chat2.IncomingChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.jid.parts.Localpart;
import org.jxmpp.stringprep.XmppStringprepException;

import java.io.IOException;

/**
 * Created by gakwaya on 4/28/2016.
 */
public class JabberConnection implements ConnectionListener {

    private static final String TAG = "RoosterConnection";

    private  final Context mApplicationContext;
    private  final String mUsername;
    private  final String mPassword;
    private  final String mServiceName;
    private XMPPTCPConnection mConnection;
    private BroadcastReceiver uiThreadMessageReceiver;//Receives messages from the ui thread.
    private String intentAction;
    private DatabaseController myDB;


    public static enum ConnectionState
    {
        CONNECTED ,AUTHENTICATED, CONNECTING ,DISCONNECTING ,DISCONNECTED;
    }

    public static enum LoggedInState
    {
        LOGGED_IN , LOGGED_OUT;
    }


    public JabberConnection( Context context, String action)
    {
        Log.d(TAG,"RoosterConnection Constructor called.");
        myDB = new DatabaseController(context);


        intentAction = action;
        mApplicationContext = context.getApplicationContext();
        String jid = PreferenceManager.getDefaultSharedPreferences(mApplicationContext)
                .getString("xmpp_jid",null);
        mPassword = PreferenceManager.getDefaultSharedPreferences(mApplicationContext)
                .getString("xmpp_password",null);

        if( jid != null)
        {
            mUsername = jid.split("@")[0];
            mServiceName = jid.split("@")[1];
        }else
        {
            mUsername ="";
            mServiceName="";
        }
    }


    public void connect() throws IOException, XMPPException, SmackException, InterruptedException {
        Log.d(TAG, "Connecting to server " + mServiceName);

        XMPPTCPConnectionConfiguration.Builder builder = XMPPTCPConnectionConfiguration.builder();
        builder.setPort(5222);
        builder.setXmppDomain("ec2-3-124-124-253.eu-central-1.compute.amazonaws.com");

        //REMOVE THIS LINE BELOW AND ESTABLISH A SECURE SSL CERTIFICATION CONNECTION ON PRODUCTION SYSTEM
        builder.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);

        //builder.setHost(mServiceName);
        //builder.setHost("ec2-3-124-124-253.eu-central-1.compute.amazonaws.com");
        builder.setUsernameAndPassword(mUsername, mPassword);
        //builder.setRosterLoadedAtLogin(true);
        builder.setResource("API");

        //Set up the ui thread broadcast message receiver.
        //setupUiThreadBroadCastMessageReceiver();

        setupUiThreadBroadCastMessageReceiver();

        mConnection = new XMPPTCPConnection(builder.build());
        mConnection.addConnectionListener(this);

        try {
            Log.d(TAG, "Calling connect() ");
            mConnection.connect();

            switch(intentAction){
                case "Register":
                    //Code to run Registration and then Login
                    registerUser();
                    mConnection.login(mUsername,mPassword);

                    break;
                case "Connect":
                    //Code to just Login
                    mConnection.login(mUsername,mPassword);
                    break;
            }

            Log.d("Tag","Login Succeeded");

        } catch (InterruptedException e) {
            e.printStackTrace();

        }

        ChatManager.getInstanceFor(mConnection).addIncomingListener(new IncomingChatMessageListener() {
            @Override
            public void newIncomingMessage(EntityBareJid messageFrom, Message message, Chat chat) {
                ///ADDED
                Log.d(TAG,"message.getBody() :"+message.getBody());
                Log.d(TAG,"message.getFrom() :"+message.getFrom());


                String from = message.getFrom().toString();

                String contactJid="";
                if ( from.contains("/"))
                {
                    contactJid = from.split("/")[0];
                    Log.d(TAG,"The real jid is :" +contactJid);
                    Log.d(TAG,"The message is from :" +from);
                }else
                {
                    contactJid=from;
                }
                // Store Message into the Model

                ModelMessage modelMessage = new ModelMessage();
                modelMessage.setMessageID("");
                modelMessage.setFrom(contactJid);
                modelMessage.setTimestamp("");
                modelMessage.setType("text");
                modelMessage.setDelivery_status("sent");
                modelMessage.setMessage_body(message.getBody());
                modelMessage.setRecipient("me");

                int messageID;
                ModelContact modelContact;

                //New Message received Store into Database
                String phonenumber = contactJid.split("@")[0];

                int contactID = myDB.contact.getContactIDFromPhonenumber(phonenumber);

                if(contactID == 0){
                    //create the new contact
                }
                else{

                    //Check if chat exists
                    int chatID = myDB.contact_to_chat.getChatFromContact("" + contactID);

                    if(chatID != 0){
                        // We already established a Chat lets store the message
                        messageID = (int) myDB.message.sendNewMessage(modelMessage);

                        //Finally link Message to Chat
                        ModelChatMessage modelChatMessage = new ModelChatMessage();

                        modelChatMessage.setMessageID(messageID);
                        modelChatMessage.setChatID(chatID);

                        myDB.message_to_chat.linkChatToMessage(modelChatMessage);
                        //myDB.chat.updateLatestMessage(modelChatMessage.getMessageID(), modelChatMessage.getChatID());

                    }
                    else{
                        // we have to start a new Chat with this contact

                        //We need to retrieve the contact info to do so
                        modelContact = myDB.contact.getContact(""+contactID);
                        // Start a new Chat

                        ModelChat newChat = new ModelChat();

                        newChat.setChat_id("");
                        newChat.setChatAvatar(modelContact.getOnlineAvatar());
                        newChat.setChatName(modelContact.getUsername());
                        newChat.setChatType("text");
                        newChat.setLastMessageID(0);
                        newChat.setCustomNotifications("");
                        newChat.setAdmin("");
                        newChat.setChatDescription("");

                        //retrieve the ChatID
                        int newChatID = (int) myDB.chat.startNewChat(newChat);
                        messageID = (int) myDB.message.sendNewMessage(modelMessage);

                        //Finally link Message to Chat

                        ModelChatMessage modelChatMessage = new ModelChatMessage();

                        modelChatMessage.setMessageID(messageID);
                        modelChatMessage.setChatID(newChatID);

                        myDB.message_to_chat.linkChatToMessage(modelChatMessage);

                        //Set Latest Message to Chat

                        //myDB.chat.updateLatestMessage(modelChatMessage.getMessageID(), modelChatMessage.getChatID());
                    }

                }


                //Bundle up the intent and send the broadcast.
                Intent intent = new Intent(APIConnectionService.NEW_MESSAGE);
                intent.setPackage(mApplicationContext.getPackageName());
                intent.putExtra(APIConnectionService.BUNDLE_FROM_JID,contactJid);
                intent.putExtra(APIConnectionService.BUNDLE_MESSAGE_BODY,message.getBody());
                mApplicationContext.sendBroadcast(intent);
                Log.d(TAG,"Received message from :"+contactJid+" broadcast sent.");
                ///ADDED

            }
        });

        ReconnectionManager reconnectionManager = ReconnectionManager.getInstanceFor(mConnection);
        reconnectionManager.setEnabledPerDefault(true);
        reconnectionManager.enableAutomaticReconnection();

    }

    private void registerUser() {

        try {
            // connecting...


            // Registering the user
            AccountManager accountManager = AccountManager.getInstance(mConnection);
            accountManager.sensitiveOperationOverInsecureConnection(true);
            accountManager.createAccount(Localpart.from(mUsername), mPassword);   // Skipping optional fields like email, first name, last name, etc..
        } catch (SmackException | IOException | XMPPException e) {
            Log.e("TAG", e.getMessage());
            //xmppClient.setConnection(null);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void showContactListActivityWhenAuthenticated()
    {
        //Send Broadcast That the User is Authenticated
        Intent i = new Intent(APIConnectionService.UI_AUTHENTICATED);
        i.setPackage(mApplicationContext.getPackageName());
        mApplicationContext.sendBroadcast(i);
        Log.d(TAG,"Sent the broadcast that we are authenticated");
    }

    private void setupUiThreadBroadCastMessageReceiver()
    {
        uiThreadMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //Check if the Intents purpose is to send the message.
                String action = intent.getAction();
                if( action.equals(APIConnectionService.SEND_MESSAGE))
                {
                    //SENDS THE ACTUAL MESSAGE TO THE SERVER
                    sendMessage(intent.getStringExtra(APIConnectionService.BUNDLE_MESSAGE_BODY),
                            intent.getStringExtra(APIConnectionService.BUNDLE_TO));
                }
            }
        };

        IntentFilter filter = new IntentFilter();
        filter.addAction(APIConnectionService.SEND_MESSAGE);
        mApplicationContext.registerReceiver(uiThreadMessageReceiver,filter);

    }

    private void sendMessage ( String body ,String toJid)
    {

        String JabberID = "danny@ec2-3-124-124-253.eu-central-1.compute.amazonaws.com";
        Log.d(TAG,"Sending message to :"+ toJid);

        EntityBareJid jid = null;


        ChatManager chatManager = ChatManager.getInstanceFor(mConnection);

        try {
            jid = JidCreate.entityBareFrom(toJid);
        } catch (XmppStringprepException e) {
            e.printStackTrace();
        }
        Chat chat = chatManager.chatWith(jid);
        try {
            Message message = new Message(jid, Message.Type.chat);
            message.setBody(body);
            chat.send(message);
            Log.d(TAG,"Message sent");


        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void disconnect()
    {
        Log.d(TAG,"Disconnecting from server "+ mServiceName);
        if (mConnection != null)
        {
            mConnection.disconnect();
        }

        mConnection = null;


    }


    @Override
    public void connected(XMPPConnection connection) {
        APIConnectionService.sConnectionState=ConnectionState.CONNECTED;
        Log.d(TAG,"Connected Successfully");
    }

     @Override
    public void authenticated(XMPPConnection connection, boolean resumed) {
        APIConnectionService.sConnectionState=ConnectionState.CONNECTED;
        Log.d(TAG,"Authenticated Successfully");

        showContactListActivityWhenAuthenticated();

    }

    @Override
    public void connectionClosed() {
        APIConnectionService.sConnectionState=ConnectionState.DISCONNECTED;
        Log.d(TAG,"Connectionclosed()");

    }

    @Override
    public void connectionClosedOnError(Exception e) {
        APIConnectionService.sConnectionState=ConnectionState.DISCONNECTED;
        Log.d(TAG,"ConnectionClosedOnError, error "+ e.toString());

    }

    @Override
    public void reconnectingIn(int seconds) {
        APIConnectionService.sConnectionState = ConnectionState.CONNECTING;
        Log.d(TAG,"ReconnectingIn() ");

    }

    @Override
    public void reconnectionSuccessful() {
        APIConnectionService.sConnectionState = ConnectionState.CONNECTED;
        Log.d(TAG,"ReconnectionSuccessful()");

    }

    @Override
    public void reconnectionFailed(Exception e) {
        APIConnectionService.sConnectionState = ConnectionState.DISCONNECTED;
        Log.d(TAG,"ReconnectionFailed()");

    }
}
