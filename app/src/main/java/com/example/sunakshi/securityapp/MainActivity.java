package com.example.sunakshi.securityapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DatabaseHandler db;
   // private String contacts;
    // private ArrayList<String> Contacts= new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //floating action button removed
        /*invoking database handler*/
        db = new DatabaseHandler(this);
        /*------------------*/
        /*--------Asks for permissions to use sms permission in android-------*/
        if(ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.SEND_SMS)){
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.SEND_SMS},1);
            }
            else{
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.SEND_SMS},1);
            }

        }
        else{
            // do nothing
        }
        /*-----------------------------------------------------------------*/

        Button btnGeoloc;
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},123);
        btnGeoloc = (Button) findViewById(R.id.button);
        btnGeoloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v  ) {
                String link = getLink();
                    String msgtxt = "I need help. I am in danger. This is my location "+ link;
                    Cursor res = db.getAlldata();
                    String phoneno;
                    while (res.moveToNext()) {
                        phoneno = res.getString(2);
                        if (phoneno.length() > 0 && msgtxt.length() > 0) {
                            sendMessage(phoneno, msgtxt);
                        } else {
                            Toast.makeText(getBaseContext(), "No Contact is added", Toast.LENGTH_SHORT).show();
                        }
                    }

            }
        });


    }
    public String getLink(){
        GPStracker g = new GPStracker(getApplicationContext());
        String LocationLink = null;
        Location l;
       while(true){
             l = g.getLocation();
            if (l != null) {
                LocationLink = "https://maps.google.com/maps/?q=" + l.getLatitude() + "," + l.getLongitude();
                break;
            }
       }
        return LocationLink;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        //Create fragments here
        Fragment fragment= null;
          int id = item.getItemId();
        if (id == R.id.settings) {
            Intent intent = new Intent(MainActivity.this,Settings.class);
            startActivity(intent);
        }
        /*if (id == R.id.dashboard) {
            // Handle the dashboard action
            //fragment = new Dashboard();

        } else if (id == R.id.search) {

        } else if (id == R.id.events) {

        } else if (id == R.id.settings) {
            Intent intent = new Intent(MainActivity.this,Settings.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/
        if(fragment!=null)
        { ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},123);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft=fragmentManager.beginTransaction();
            ft.replace(R.id.screen_area,fragment);
            ft.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    /*-------------function to send sms--------------------*/
    private void sendMessage(String sendto,String message){
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(sendto,null,message,null,null);
            Toast.makeText(getApplicationContext(),"SMS Sent Successfully", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),"ERROR_IN_SENDING", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }
    /*-----------------------------------------------*/

/*----------------Permission method---------------*/

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.SEND_SMS)==PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this,"Permissions Granted!",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(this, "Permissions Not Granted!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return;
    }
/*------------------------------------------------*/
}
