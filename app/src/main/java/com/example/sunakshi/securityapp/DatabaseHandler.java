package com.example.sunakshi.securityapp;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    // Contacts table name
    private static final String TABLE_CONTACTS = "contacts";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_PH_NO + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact

    public long insertData(String name,String ContactNumber){
        if(!name.isEmpty() && !ContactNumber.isEmpty()){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // Contact Name
        values.put(KEY_NAME, name);
        values.put(KEY_PH_NO, ContactNumber);

            long result= db.insert(TABLE_CONTACTS, null,values);
            return result;
        }
        else{
            return -1;
        }

    }

    // Getting All Contacts
    public Cursor getAlldata(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("Select *from "+TABLE_CONTACTS,null);
        return res;
    }


    public boolean updateData(String id,String name,String contact)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_ID , id);
        values.put(KEY_NAME , name); // Contact Name
        values.put(KEY_PH_NO, contact);
        db.update(TABLE_CONTACTS,values,"id= ?",new String[]{id});
            return true;
    }


 public Integer deleteData(String id){
     SQLiteDatabase db=this.getWritableDatabase();
     return db.delete(TABLE_CONTACTS,"id= ?",new String[]{id});


 }



}