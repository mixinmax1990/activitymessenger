package com.example.apiconnect.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.AsyncLayoutInflater;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.apiconnect.R;
import com.example.apiconnect.data.database.local.controller.DatabaseController;
import com.example.apiconnect.data.database.local.models.ModelChat;
import com.example.apiconnect.interfaces.RecyclerViewClickListener;
import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static java.lang.Integer.parseInt;

public class ChatRecylerEmptyAdapter extends RecyclerView.Adapter<ChatRecylerEmptyAdapter.ViewHolder>{

    private Context context;
    private List<ModelChat> chats;
    private ModelChat localChat;
    private RecyclerViewClickListener mListener;
    private PrettyTime p;
    private DatabaseController myDB;

    public ChatRecylerEmptyAdapter(@NonNull Context context, int resource, @NonNull List<ModelChat> objects, RecyclerViewClickListener listener, DatabaseController myDB) {

        this.context = context;
        this.chats = objects;
        chats.add(0, null);
        mListener = listener;
        p = new PrettyTime();
        this.myDB = myDB;

    }

    @android.support.annotation.NonNull
    @Override
    public ViewHolder onCreateViewHolder(@android.support.annotation.NonNull ViewGroup viewGroup, int i) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.empty_layout, viewGroup, false), mListener, i);

    }

    int count = 0;
    @Override
    public void onBindViewHolder(@android.support.annotation.NonNull ViewHolder parent, final int i) {

        parent.itemView.setId(i);


        //((ViewGroup)parent.itemView).removeAllViews();

        Log.i("Counter", ""+count++);

        

        //Check for Who the sender is
        if(i == 3 || i == 4 || i == 5 || i == 8){

            AsyncLayoutInflater asyncLayoutInflater = new AsyncLayoutInflater(context);

            try {
                asyncLayoutInflater.inflate(R.layout.liststructure_single_image, (ViewGroup) parent.itemView, new AsyncLayoutInflater.OnInflateFinishedListener() {
                    @Override
                    public void onInflateFinished(@android.support.annotation.NonNull View view, int resid, @Nullable ViewGroup parent) {
                        parent.removeAllViews();
                        parent.addView(view);


                        localChat = chats.get(parent.getId());
                        parent.setTag(localChat.chatAvatar);

                        TextView messageBody;
                        ImageView Avatar;
                        TextView chatName;
                        TextView chatTime;
                        ImageView imageMessage1;
                        ImageView imageMessage2;
                        ImageView imageMessage3;
                        ImageView imageMessage4;
                        
                        parent.setId(i);
                        messageBody = view.findViewById(R.id.chatlist_message);
                        chatName = view.findViewById(R.id.chatlist_name);
                        chatTime = view.findViewById(R.id.chatlist_time);
                        Avatar = view.findViewById(R.id.chatAvatar);

                        imageMessage1 = view.findViewById(R.id.chatmessage_image1);
                        imageMessage2 = view.findViewById(R.id.chatmessage_image2);
                        imageMessage3 = view.findViewById(R.id.chatmessage_image3);
                        imageMessage4 = view.findViewById(R.id.chatmessage_image4);

                        chatName.setText(localChat.chatName);
                        view.setTag(localChat.chatAvatar);

                        Picasso picasso = Picasso.get();
                        picasso.load(localChat.chatAvatar).into(Avatar);
                        
                        if(i == 3){

                            Picasso pic = Picasso.get();
                            pic.load("http://i.imgur.com/QyYuP2l.jpg").into(imageMessage1);
                            Picasso pic2 = Picasso.get();
                            pic2.load("https://pbs.twimg.com/profile_images/378800000298767811/3ecaa4f9af36dba1085c066b23e4938f_400x400.jpeg").into(imageMessage2);
                            Picasso pic3 = Picasso.get();
                            pic3.load("https://assets.simpleviewinc.com/simpleview/image/upload/c_limit,q_65,w_845/v1/clients/vancouverbc/food_truck_friends_844x555_7765a77c-75f2-491c-853b-198e1537e515.jpg").into(imageMessage3);
                            Picasso pic4 = Picasso.get();
                            pic4.load("https://images.wsj.net/im-128757?width=620&size=1").into(imageMessage4);
                            chatTime.setText("15:12");

                            //messageBody.setText("Lets just Type Another Message so that we can CHeck if thing do Work ");
                        }
                        else if(i == 4){

                            imageMessage3.setVisibility(View.GONE);
                            imageMessage4.setVisibility(View.GONE);
                            Picasso pic = Picasso.get();
                            pic.load("https://cdn.teamandroid.com/wp-content/uploads/2018/10/Galaxy-A7-2018-HandsOn-15-1024x768.jpg").into(imageMessage1);
                            Picasso pic2 = Picasso.get();
                            pic2.load("https://images.firstpost.com/wp-content/uploads/2018/09/Samsung-Galaxy-A7-10241.jpg").into(imageMessage2);
                            chatTime.setText("15:12");

                            //messageBody.setText("Lets just Type Another Message so that we can CHeck if thing do Work ");
                        }
                        else if(i == 5){

                            imageMessage4.setVisibility(View.GONE);
                            Picasso pic = Picasso.get();
                            pic.load("https://www.thatericalper.com/wp-content/uploads/2018/07/Dua-Lipa-RBC-Echo-Beach-%C2%A9-Minis-Memories-07-30-2018-2-4.jpg").into(imageMessage1);
                            Picasso pic2 = Picasso.get();
                            pic2.load("https://images.squarespace-cdn.com/content/v1/56433063e4b0a83ded233d0a/1550091713326-WEW3PZ148CFHJCREBTPB/ke17ZwdGBToddI8pDm48kA_SSaoz4elkj-HsZd8gX3Z7gQa3H78H3Y0txjaiv_0fDoOvxcdMmMKkDsyUqMSsMWxHk725yiiHCCLfrh8O1z5QPOohDIaIeljMHgDF5CVlOqpeNLcJ80NK65_fV7S1UWPwZyNcweDIvdeL5kotwkIXjs9g0WibSO_cU-Ijy4Pwg6poS-6WGGnXqDacZer4yQ/dualipa-4556.jpg?format=2500w").into(imageMessage2);
                            Picasso pic3 = Picasso.get();
                            pic3.load("https://aestheticmag.files.wordpress.com/2018/07/dua-lipa-toronto-2018-3-copy.jpg?w=501").into(imageMessage3);
                            chatTime.setText("15:12");

                            //messageBody.setText("Lets just Type Another Message so that we can CHeck if thing do Work ");
                        }
                        else if(i == 8){
                            imageMessage2.setVisibility(View.GONE);
                            imageMessage3.setVisibility(View.GONE);
                            imageMessage4.setVisibility(View.GONE);
                            Picasso pic = Picasso.get();
                            pic.load("https://i.pinimg.com/236x/d6/2e/9c/d62e9cea4e3f730ced7f1bf9ab485e92.jpg").into(imageMessage1);
                            chatTime.setText("15:12");

                            //viewHolder.messageBody.setText("Lets just Type Another Message so that we can CHeck if thing do Work ");
                        }
                    }
                });
            }
            catch (Exception e){
               
            }
        }


        else if(i == 0){

            AsyncLayoutInflater asyncLayoutInflater = new AsyncLayoutInflater(context);

            try {
                asyncLayoutInflater.inflate(R.layout.chatstart, (ViewGroup) parent.itemView, new AsyncLayoutInflater.OnInflateFinishedListener() {
                    @Override
                    public void onInflateFinished(@android.support.annotation.NonNull View view, int resid, @Nullable ViewGroup parent) {
                        parent.removeAllViews();
                        parent.addView(view);


                    }
                });
            }
            catch (Exception e){

            }
        }
        else if(i == getItemCount() - 1){

            AsyncLayoutInflater asyncLayoutInflater = new AsyncLayoutInflater(context);

            try {
                asyncLayoutInflater.inflate(R.layout.chatend, (ViewGroup) parent.itemView, new AsyncLayoutInflater.OnInflateFinishedListener() {
                    @Override
                    public void onInflateFinished(@android.support.annotation.NonNull View view, int resid, @Nullable ViewGroup parent) {
                        parent.removeAllViews();
                        parent.addView(view);

                    }
                });
            }
            catch (Exception e){

            }
        }

        else if(i == 1){

            AsyncLayoutInflater asyncLayoutInflater = new AsyncLayoutInflater(context);

            try {
                asyncLayoutInflater.inflate(R.layout.liststructure_basic_activity_status, (ViewGroup) parent.itemView, new AsyncLayoutInflater.OnInflateFinishedListener() {
                    @Override
                    public void onInflateFinished(@android.support.annotation.NonNull View view, int resid, @Nullable ViewGroup parent) {
                        parent.removeAllViews();
                        parent.addView(view);

                        localChat = chats.get(parent.getId());

                        TextView messageBody;
                        ImageView Avatar;
                        TextView chatName;
                        TextView chatTime;



                        parent.setId(i);
                        parent.setTag(localChat.chatAvatar);
                        messageBody = view.findViewById(R.id.chatlist_message);
                        chatName = view.findViewById(R.id.chatlist_name);
                        chatTime = view.findViewById(R.id.chatlist_time);
                        Avatar = view.findViewById(R.id.chatAvatar);

                        chatName.setText(localChat.chatName);
                        view.setTag(localChat.chatAvatar);

                        Picasso picasso = Picasso.get();
                        picasso.load(localChat.chatAvatar).into(Avatar);

                        chatTime.setText("11:34"); //p.format(ts)
                        if(!localChat.chatDescription.isEmpty()){
                            messageBody.setText(""+myDB.message.getMessage(localChat.chatDescription).message_body);
                        }
                        else {
                            messageBody.setText("There are no Messages yet");
                        }



                    }
                });
            }
            catch (Exception e){

            }

        }

        else if( i == 7){

            AsyncLayoutInflater asyncLayoutInflater = new AsyncLayoutInflater(context);
            try {
                asyncLayoutInflater.inflate(R.layout.liststructure_basic_activity, (ViewGroup) parent.itemView, new AsyncLayoutInflater.OnInflateFinishedListener() {
                    @Override
                    public void onInflateFinished(@android.support.annotation.NonNull View view, int resid, @Nullable ViewGroup parent) {
                        parent.removeAllViews();
                        parent.addView(view);
                        localChat = chats.get(parent.getId());

                        TextView messageBody;
                        ImageView Avatar;
                        TextView chatName;
                        TextView chatTime;

                        parent.setId(i);
                        parent.setTag(localChat.chatAvatar);
                        messageBody = view.findViewById(R.id.chatlist_message);
                        chatName = view.findViewById(R.id.chatlist_name);
                        chatTime = view.findViewById(R.id.chatlist_time);
                        Avatar = view.findViewById(R.id.chatAvatar);

                        chatName.setText(localChat.chatName);
                        view.setTag(localChat.chatAvatar);

                        Picasso picasso = Picasso.get();
                        picasso.load(localChat.chatAvatar).into(Avatar);


                        chatTime.setText("11:34"); //p.format(ts)
                        if(!localChat.chatDescription.isEmpty()){
                            messageBody.setText(""+myDB.message.getMessage(localChat.chatDescription).message_body);
                        }
                        else {
                            Log.i("LatestMessage", "== Running");
                            messageBody.setText("There are no Messages yet");
                        }

                    }
                });
            }
            catch (Exception e){

            }
        }
        else{
            AsyncLayoutInflater asyncLayoutInflater = new AsyncLayoutInflater(context);
            try {
                asyncLayoutInflater.inflate(R.layout.liststructure_basic, (ViewGroup) parent.itemView, new AsyncLayoutInflater.OnInflateFinishedListener() {
                    @Override
                    public void onInflateFinished(@android.support.annotation.NonNull View view, int resid, @Nullable ViewGroup parent) {
                        parent.removeAllViews();
                        parent.addView(view);
                        localChat = chats.get(parent.getId());

                        TextView messageBody;
                        ImageView Avatar;
                        TextView chatName;
                        TextView chatTime;

                        parent.setId(i);
                        parent.setTag(localChat.chatAvatar);
                        messageBody = view.findViewById(R.id.chatlist_message);
                        chatName = view.findViewById(R.id.chatlist_name);
                        chatTime = view.findViewById(R.id.chatlist_time);
                        Avatar = view.findViewById(R.id.chatAvatar);

                        chatName.setText(localChat.chatName);
                        view.setTag(localChat.chatAvatar);

                        Picasso picasso = Picasso.get();
                        picasso.load(localChat.chatAvatar).into(Avatar);

                        //chatTime.setText("11:34"); p.format(ts)
                        if(!localChat.chatDescription.isEmpty()){

                            messageBody.setText(""+myDB.message.getMessage(localChat.chatDescription).message_body);



                           chatTime.setText(""+p.format(convertMilliSecondToDate(convertDateToMilliSeconds(myDB.message.getMessage(localChat.chatDescription).getTimestamp()))));


                        }
                        else {
                            messageBody.setText("There are no Messages yet");
                        }

                    }
                });
            }
            catch (Exception e){

            }
        }





    }

    public static long convertDateToMilliSeconds(String inputDate){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
            //throw new IllegalAccessException("Error in parsing date");
        }
        return date.getTime();
    }
    public static Date convertMilliSecondToDate(long milliSeconds){
        Date date= new Date(milliSeconds);
        return date;
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private RecyclerViewClickListener mListener;


        public ViewHolder(@android.support.annotation.NonNull View itemView, RecyclerViewClickListener listener, int i) {
            super(itemView);

            mListener = listener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            ImageView ca = v.findViewById(R.id.chatAvatar);
            mListener.onClick(v, getAdapterPosition(), ca);
            Log.i("ChatID -----",""+getAdapterPosition());
        }





    }
}

