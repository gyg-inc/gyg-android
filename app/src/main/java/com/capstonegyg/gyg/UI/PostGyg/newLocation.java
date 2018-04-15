package com.capstonegyg.gyg.UI.PostGyg;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.location.Address;
import android.text.TextUtils;


//import com.capstonegyg.gyg.Manifest;
import com.capstonegyg.gyg.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnSuccessListener;

import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class newLocation extends AppCompatActivity implements ConnectionCallbacks,
        OnConnectionFailedListener,OnRequestPermissionsResultCallback {


  //  private FusedLocationProviderClient mmLastLocation;
    private Location mLastLocation;
    private Context context;
    private Activity current_activity;

    double latitude;
    double longitude;

    String currentLocation;
  //  private GoogleApiClient mGoogleApiClient;

    LocationHelper locationHelper;


    public newLocation (Context context) {
           this.context=context;
           this.current_activity= (Activity) context;

        locationHelper=new LocationHelper(this.context);
        locationHelper.checkpermission();
    }

    public String getLocation() {

        mLastLocation=locationHelper.getLocation();

        String address = "";

         if (mLastLocation != null) {
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();

             showToast("Longitude: " + longitude + "\nLatitude: " + latitude);
             address = getAddress();
        }
        else {
            showToast("Couldn't get the location. Make sure location is enabled on the device");
        }

        // check availability of play services
        if (locationHelper.checkPlayServices()) {

        //     showToast("TRUE!");

        // Building the GoogleApi client
            locationHelper.buildGoogleApiClient();
         }

         return address;
    }

    public String getAddress()
    {
        Address locationAddress;

        locationAddress=locationHelper.getAddress(latitude,longitude);

       if(locationAddress!=null)
        {
            String city = locationAddress.getLocality();
            String state = locationAddress.getAdminArea();
            String country = locationAddress.getCountryName();
            String postalCode = locationAddress.getPostalCode();

            if(!TextUtils.isEmpty(city))
            {
                currentLocation=city;

                if (!TextUtils.isEmpty(postalCode))
                    currentLocation+=" - "+postalCode;

                if (!TextUtils.isEmpty(state))
                    currentLocation+="\n"+state;

                if (!TextUtils.isEmpty(country))
                    currentLocation+="\n"+country;
            }

            return currentLocation;
        }
        else {
           showToast("Something went wrong");
           return "";
       }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        locationHelper.onActivityResult(requestCode,resultCode,data);
    }


    @Override
    protected void onResume() {
        super.onResume();
        locationHelper.checkPlayServices();
    }

/**
 * Google api callback methods
 */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i("Connection failed:", " ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {

        // Once connected with google api, get the location
        mLastLocation=locationHelper.getLocation();
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        locationHelper.connectApiClient();
    }

    // Permission check functions
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // redirects to utils
        locationHelper.onRequestPermissionsResult(requestCode,permissions,grantResults);

    }

    public void showToast(String message)
    {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}
