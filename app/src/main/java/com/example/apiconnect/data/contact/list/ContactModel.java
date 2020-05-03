package com.example.apiconnect.data.contact.list;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class ContactModel {
    private static ContactModel sContactModel;
    private List<Contact> mContacts;

    public static ContactModel get(Context context)
    {
        if(sContactModel == null)
        {
            sContactModel = new ContactModel(context);
        }
        return  sContactModel;
    }

    private ContactModel(Context context)
    {
        mContacts = new ArrayList<>();
        populateWithInitialContacts(context);

    }

    private void populateWithInitialContacts(Context context)
    {
        //Create the Foods and add them to the list;


        Contact contact1 = new Contact("danny@ec2-3-124-124-253.eu-central-1.compute.amazonaws.com");
        mContacts.add(contact1);

    }

    public List<Contact> getContacts()
    {
        return mContacts;
    }


}
