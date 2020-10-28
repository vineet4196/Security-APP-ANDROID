package com.example.sunakshi.securityapp; /**
 * Created by sunakshi on 10/12/17.
 */
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

public class GPStracker implements LocationListener {
    Context context;
    public GPStracker(Context c){
    context = c;
    }
    public Location getLocation(){
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(context,"Permission not granted",Toast.LENGTH_SHORT).show();
            return null;
        }
        LocationManager lm =(LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        //to check id GPS  is enabled
        boolean isGPSenabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(isGPSenabled){
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,6000,10,this);
            Location l = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            return l;
        }
        else{
            Toast.makeText(context,"Please enable Internet Connection",Toast.LENGTH_LONG).show();
        }
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    public GPStracker() {
        super();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
