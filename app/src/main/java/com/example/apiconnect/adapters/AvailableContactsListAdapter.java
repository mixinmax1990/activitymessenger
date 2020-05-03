package com.example.apiconnect.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.apiconnect.R;
import com.example.apiconnect.adapters.models.ModelAvailableContacts;
import com.squareup.picasso.Picasso;


import java.util.List;

public class AvailableContactsListAdapter  extends ArrayAdapter<ModelAvailableContacts> {
    public AvailableContactsListAdapter(@NonNull Context context, int resource, @NonNull List<ModelAvailableContacts> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ModelAvailableContacts contact = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.available_contacts_item, parent, false);
        }

        TextView contactName = convertView.findViewById(R.id.ACchatlist_name);
        ImageView contactAvatar = (ImageView) convertView.findViewById(R.id.ACAvatar);

        contactName.setText(contact.name);
        Picasso picasso = Picasso.get();
        picasso.load(contact.avatar).into(contactAvatar);


        return convertView;
    }
}
