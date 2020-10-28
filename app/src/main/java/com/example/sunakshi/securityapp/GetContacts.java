package com.example.sunakshi.securityapp;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Vineet on 12/11/2017.
 */

public class GetContacts extends AppCompatActivity{
    DatabaseHandler db;
    Context context;
    public GetContacts(Context c){
    context = c;
    }
    public String getContacts(){
        Cursor res = db.getAlldata();
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
           // buffer.append("Id: " + res.getString(0) + "\n");
         //   buffer.append("Name: " + res.getString(1) + "\n");
            buffer.append("" +res.getString(2)+",");

        }
         return buffer.toString();
    }


}

