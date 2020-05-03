package com.example.apiconnect.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.view.AsyncLayoutInflater;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.apiconnect.R;
import com.example.apiconnect.data.database.local.controller.DatabaseController;
import com.example.apiconnect.data.database.local.models.ModelChat;
import com.example.apiconnect.data.database.local.models.ModelMessage;
import com.example.apiconnect.fragments.ActivityOneFragment;
import com.example.apiconnect.interfaces.ChatAdapterCallback;
import com.example.apiconnect.interfaces.RecyclerViewClickListener;
import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

public class ChatRecyclerAdapter extends RecyclerView.Adapter<ChatRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<ModelChat> chats;
    private ModelChat localChat;
    private RecyclerViewClickListener mListener;
    private PrettyTime p;


    public ChatRecyclerAdapter(@NonNull Context context, int resource, @NonNull List<ModelChat> objects, RecyclerViewClickListener listener) {
        this.context = context;
        this.chats = objects;
        chats.add(0, null);
        mListener = listener;
        p = new PrettyTime();

    }


    @android.support.annotation.NonNull
    @Override
    public ViewHolder onCreateViewHolder(@android.support.annotation.NonNull ViewGroup parent, final int i) {
        //Check for Who the sender is
        if(i == 3 || i == 4 || i == 5 || i == 8){

            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.liststructure_single_image, parent, false), mListener, i);
        }

        if(i == 0){
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.chatstart, parent, false), mListener, i);
        }
        if(i == getItemCount() - 1){
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.chatend, parent, false), mListener, i);
        }

        if(i == 1){

            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.liststructure_basic_activity_status, parent, false), mListener, i);
        }

        else if( i == 7){

            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.liststructure_basic_activity, parent, false), mListener, i);
        }
        else{

            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.liststructure_basic, parent, false), mListener, i);

        }

    }

    @Override
    public int getItemViewType(int position) {

        localChat = chats.get(position);

       // final int sender = localMessage.getFrom().length();
        return position;

    }

    @Override
    public void onBindViewHolder(@android.support.annotation.NonNull ViewHolder viewHolder, int i) {

        //Load latest message Maybe store more data in ChatDb for Efficiency (Look into That)

        //ModelMessage message = myDB.message.getMessage(""+localChat.getLastMessageID());

        // Set Data in here
        //java.sql.Timestamp ts =  java.sql.Timestamp.valueOf(message.getTimestamp()) ;

        if(i == 0 || i == getItemCount() - 1){

        }
        else{

            /*
            if(i == getItemCount() - 1){
           RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) viewHolder.itemView.getLayoutParams();
           lp.bottomMargin = 2100;

           viewHolder.itemView.setLayoutParams(lp);
        }
            */



            if(i == 2){
                viewHolder.messageBody.setText("Okay ");
                viewHolder.chatTime.setText("11/12/19"); //p.format(ts)
            }
            else if(i == 3){

            Picasso pic = Picasso.get();
            pic.load("http://i.imgur.com/QyYuP2l.jpg").into(viewHolder.imageMessage1);
            Picasso pic2 = Picasso.get();
            pic2.load("https://pbs.twimg.com/profile_images/378800000298767811/3ecaa4f9af36dba1085c066b23e4938f_400x400.jpeg").into(viewHolder.imageMessage2);
                Picasso pic3 = Picasso.get();
                pic3.load("https://assets.simpleviewinc.com/simpleview/image/upload/c_limit,q_65,w_845/v1/clients/vancouverbc/food_truck_friends_844x555_7765a77c-75f2-491c-853b-198e1537e515.jpg").into(viewHolder.imageMessage3);
                Picasso pic4 = Picasso.get();
                pic4.load("https://images.wsj.net/im-128757?width=620&size=1").into(viewHolder.imageMessage4);
            viewHolder.chatTime.setText("15:12");

            //viewHolder.messageBody.setText("Lets just Type Another Message so that we can CHeck if thing do Work ");
           }
            else if(i == 4){

                viewHolder.imageMessage3.setVisibility(View.GONE);
                viewHolder.imageMessage4.setVisibility(View.GONE);
                Picasso pic = Picasso.get();
                pic.load("https://cdn.teamandroid.com/wp-content/uploads/2018/10/Galaxy-A7-2018-HandsOn-15-1024x768.jpg").into(viewHolder.imageMessage1);
                Picasso pic2 = Picasso.get();
                pic2.load("https://images.firstpost.com/wp-content/uploads/2018/09/Samsung-Galaxy-A7-10241.jpg").into(viewHolder.imageMessage2);
                viewHolder.chatTime.setText("15:12");

                //viewHolder.messageBody.setText("Lets just Type Another Message so that we can CHeck if thing do Work ");
            }
            else if(i == 5){

                viewHolder.imageMessage4.setVisibility(View.GONE);
                Picasso pic = Picasso.get();
                pic.load("https://www.thatericalper.com/wp-content/uploads/2018/07/Dua-Lipa-RBC-Echo-Beach-%C2%A9-Minis-Memories-07-30-2018-2-4.jpg").into(viewHolder.imageMessage1);
                Picasso pic2 = Picasso.get();
                pic2.load("https://images.squarespace-cdn.com/content/v1/56433063e4b0a83ded233d0a/1550091713326-WEW3PZ148CFHJCREBTPB/ke17ZwdGBToddI8pDm48kA_SSaoz4elkj-HsZd8gX3Z7gQa3H78H3Y0txjaiv_0fDoOvxcdMmMKkDsyUqMSsMWxHk725yiiHCCLfrh8O1z5QPOohDIaIeljMHgDF5CVlOqpeNLcJ80NK65_fV7S1UWPwZyNcweDIvdeL5kotwkIXjs9g0WibSO_cU-Ijy4Pwg6poS-6WGGnXqDacZer4yQ/dualipa-4556.jpg?format=2500w").into(viewHolder.imageMessage2);
                Picasso pic3 = Picasso.get();
                pic3.load("https://aestheticmag.files.wordpress.com/2018/07/dua-lipa-toronto-2018-3-copy.jpg?w=501").into(viewHolder.imageMessage3);
                viewHolder.chatTime.setText("15:12");

                //viewHolder.messageBody.setText("Lets just Type Another Message so that we can CHeck if thing do Work ");
            }
            else if(i == 8){
                viewHolder.imageMessage2.setVisibility(View.GONE);
                viewHolder.imageMessage3.setVisibility(View.GONE);
                viewHolder.imageMessage4.setVisibility(View.GONE);
                Picasso pic = Picasso.get();
                pic.load("https://i.pinimg.com/236x/d6/2e/9c/d62e9cea4e3f730ced7f1bf9ab485e92.jpg").into(viewHolder.imageMessage1);
                viewHolder.chatTime.setText("15:12");

                //viewHolder.messageBody.setText("Lets just Type Another Message so that we can CHeck if thing do Work ");
            }
            else{
                viewHolder.chatTime.setText("11:34"); //p.format(ts)
                viewHolder.messageBody.setText("Whatever the Message is, it has to look good and clean, Do you agree? With this kind of solution you wouldn't need to be actively checking if an animation has ");
            }

            // message.getMessage_body()
            viewHolder.chatName.setText(localChat.chatName);
            viewHolder.itemView.setTag(localChat.chatAvatar);

            Picasso picasso = Picasso.get();
            picasso.load(localChat.chatAvatar).into(viewHolder.Avatar);

        }

    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    /*
    public boolean addStatus(ConstraintLayout parent){


        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(parent);


        int imageviewID = (Integer)parent.findViewById(R.id.imageView).getId();

        FrameLayout statusLine = createStatusLine();

        parent.addView(statusLine);

        //Set The Constraints
        constraintSet.connect(statusLine.getId(),ConstraintSet.TOP,imageviewID,ConstraintSet.BOTTOM,0);
        constraintSet.connect(statusLine.getId(),ConstraintSet.LEFT,imageviewID,ConstraintSet.LEFT,0);
        constraintSet.connect(statusLine.getId(),ConstraintSet.RIGHT,imageviewID,ConstraintSet.RIGHT,0);
        constraintSet.applyTo(parent);

        return true;
    }

    public FrameLayout createStatusLine(){

        FrameLayout statusLine = new FrameLayout(context);
        statusLine.setId(R.id.chat_status_line);
        ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(5,400);
        statusLine.setBackgroundColor(Color.BLACK);
        statusLine.setLayoutParams(lp);

        return statusLine;

    }
*/
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView messageBody;
        ImageView Avatar;
        TextView chatName;
        TextView chatTime;
        ImageView imageMessage1;
        ImageView imageMessage2;
        ImageView imageMessage3;
        ImageView imageMessage4;
        View parent;

        private RecyclerViewClickListener mListener;


        public ViewHolder(@android.support.annotation.NonNull View itemView, RecyclerViewClickListener listener, int i) {
            super(itemView);

            if(i == 0 || i == getItemCount() - 1){

            }
            else{
                parent = itemView;
                parent.setId(i);
                messageBody = itemView.findViewById(R.id.chatlist_message);
                chatName = itemView.findViewById(R.id.chatlist_name);
                chatTime = itemView.findViewById(R.id.chatlist_time);
                Avatar = itemView.findViewById(R.id.chatAvatar);




                if(i == 3 || i == 4 || i == 5 || i == 8){
                    imageMessage1 = itemView.findViewById(R.id.chatmessage_image1);
                    imageMessage2 = itemView.findViewById(R.id.chatmessage_image2);
                    imageMessage3 = itemView.findViewById(R.id.chatmessage_image3);
                    imageMessage4 = itemView.findViewById(R.id.chatmessage_image4);
                }

                mListener = listener;
                itemView.setOnClickListener(this);

            }

        }

        @Override
        public void onClick(View v) {

            mListener.onClick(v, getAdapterPosition(), (ImageView) v.findViewById(R.id.chatAvatar));
        }





    }
}

