package com.example.sunakshi.securityapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Vineet on 12/11/2017.
 */

public class Settings extends AppCompatActivity{
    Button b2,b1,b3,b4;
    EditText t1,t2,t3;
    DatabaseHandler db;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        db = new DatabaseHandler(this);
        b2 = (Button) findViewById(R.id.b2);
        b4= (Button) findViewById(R.id.b4);
        b1 = (Button) findViewById(R.id.b1);
        t1=(EditText)findViewById(R.id.t1);
        t2=(EditText)findViewById(R.id.t2);
        b3 = (Button) findViewById(R.id.b3);
        t3=(EditText)findViewById(R.id.t3);
        /**
         * CRUD Operations
         * */
        // Inserting Contacts
        Cursor res = db.getAlldata();
        if (res.getCount() == 0)  // check whether there is value or not
        {
           Toast.makeText(getApplicationContext(),"No Contacts available in the Database",Toast.LENGTH_SHORT).show();
        }


        AddData();
        viewall();
        DeleteData();
        UpdateData();
    }
    //delete
    public void DeleteData()
    {
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deletedRows=db.deleteData(t3.getText().toString());
                if(deletedRows>0) {
                    Toast.makeText(Settings.this, "Data Deleted", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(Settings.this, "Data not Deleted", Toast.LENGTH_LONG).show();
            }
        });
    }
    // Updating
    public void UpdateData() {
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate=db.updateData(t3.getText().toString(),t1.getText().toString(),t2.getText().toString());// t3.get text take id to update
                if (isUpdate==true)
                    Toast.makeText(Settings.this, "Data Updated", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(Settings.this, "Data not Updated", Toast.LENGTH_LONG).show();

            }
        });

    }
    //Adding new contancts
    public void AddData(){

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res =db.getAlldata();
                int count=0;
                while(res.moveToNext())
                {
                    if(t2.getText().toString().equals(res.getString(2)))
                    {
                        count++;
                    }
                }
                long isInsertedto;
                if(count==0)
                 isInsertedto = db.insertData(t1.getText().toString(),t2.getText().toString());
                else{
                    isInsertedto=-1;
                    Toast.makeText(Settings.this, "Data Already present", Toast.LENGTH_LONG).show();}
                if(isInsertedto > -1){

                    Toast.makeText(Settings.this, "Data inserted", Toast.LENGTH_LONG).show();

                    }
                else
                    Toast.makeText(Settings.this, "Data is not inserted", Toast.LENGTH_LONG).show();

            }
        });
    }
    // Reading all contacts

    public void viewall() {
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor res = db.getAlldata();
                if (res.getCount() == 0)  // check whether there is value or not
                {
                    showMessage("ERROR!", "NO DATA PRESENT");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("Id: " + res.getString(0) + "\n");
                    buffer.append("Name: " + res.getString(1) + "\n");
                    buffer.append("Contact: " + res.getString(2) + "\n\n");
                }
                // show all contact
                showMessage("DATA", buffer.toString());
            }
        });

    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}

