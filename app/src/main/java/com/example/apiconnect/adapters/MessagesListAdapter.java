package com.example.apiconnect.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.apiconnect.R;
import com.example.apiconnect.data.database.local.controller.DatabaseController;
import com.example.apiconnect.data.database.local.models.ModelMessage;
import java.util.List;

public class MessagesListAdapter extends RecyclerView.Adapter<MessagesListAdapter.ViewHolder> {

    DatabaseController myDB;
    private Context context;
    private List<ModelMessage> messages;
    private ModelMessage localMessage;

    public MessagesListAdapter(@NonNull Context context, int resource, @NonNull List<ModelMessage> objects) {
        this.context = context;
        this.messages = objects;
        myDB = new DatabaseController(context);
    }

    @android.support.annotation.NonNull
    @Override
    public ViewHolder onCreateViewHolder(@android.support.annotation.NonNull ViewGroup parent, int i) {
        //Check for Who the sender is
        if(i == 0) {



                return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.chat_message_list_item_sent, parent, false));

        }
        else {


                return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.chat_message_list_item_received, parent, false));
            }


    }

    @Override
    public int getItemViewType(int position) {

        localMessage = messages.get(position);

        final int sender = localMessage.getFrom().length();

        if (sender < 3) {

            return 0;
        }
        else{

            return 1;
        }
    }

    @Override
    public void onBindViewHolder(@android.support.annotation.NonNull ViewHolder viewHolder, int i) {

        viewHolder.messageBody.setText(localMessage.message_body);

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView messageBody;

        public ViewHolder(@android.support.annotation.NonNull View itemView) {
            super(itemView);
            messageBody = itemView.findViewById(R.id.chat_message_list_text);


        }
    }
}

