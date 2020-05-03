package com.example.apiconnect.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.apiconnect.R;
import com.example.apiconnect.data.model.MyContacts;

import java.util.ArrayList;

public class PhoneContactsAdapter extends RecyclerView.Adapter<PhoneContactsAdapter.PhoneContactsAdapterViewHolder>{

    Context context;
    ArrayList<MyContacts> myContactsArrayList;

    public PhoneContactsAdapter(Context context, ArrayList<MyContacts> myContactsArrayList) {

        this.context = context;
        this.myContactsArrayList = myContactsArrayList;
    }

    @NonNull
    @Override
    public PhoneContactsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list,viewGroup, false);

        return new PhoneContactsAdapterViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull PhoneContactsAdapterViewHolder phoneContactsAdapterViewHolder, int i) {

        MyContacts myContacts = myContactsArrayList.get(i);
        phoneContactsAdapterViewHolder.tvName.setText(myContacts.getName());
        phoneContactsAdapterViewHolder.tvNumber.setText(myContacts.getNumber());

        Log.d("Numbers", "this: "+myContacts.getNumber());

    }

    @Override
    public int getItemCount() {
        return myContactsArrayList.size();
    }

    public class PhoneContactsAdapterViewHolder extends RecyclerView.ViewHolder{

        TextView tvName, tvNumber;

        public PhoneContactsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.txtName);
            tvNumber = itemView.findViewById(R.id.txtNumber);


        }
    }
}
