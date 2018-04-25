package com.capstonegyg.gyg.UI.PostGyg;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
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

public class newLocation extends AppCompatActivity implements
        OnConnectionFailedListener,OnRequestPermissionsResultCallback {

  //  private FusedLocationProviderClient mmLastLocation;
    private Location mLastLocation;
    private Context context;

    private int checker;

    double latitude;
    double longitude;

    String address;

    String currentLocation;

    LocationHelper locationHelper;


    public newLocation (Context context) {
        this.context=context;

        locationHelper=new LocationHelper(this.context);
        locationHelper.checkpermission();
        checker = 0;
        address = "";
    }

    public String getLocation() {

        mLastLocation=locationHelper.getLocation();

         if (mLastLocation != null) {
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();

             address = getAddress();
             return address;
        }
        else {
             if(checker < 2) {
                 checker += 1;
                 retryFetchLocation();

                 return address;
             }
             else
                return "REBOOT";
        }
    }

    public String getAddress()
    {
        Address locationAddress;

        locationAddress=locationHelper.getAddress(latitude,longitude);

       if(locationAddress!=null)
        {
         //   String city = locationAddress.getLocality();
         //   String state = locationAddress.getAdminArea();
         //   String country = locationAddress.getCountryName();
            String postalCode = locationAddress.getPostalCode();

         //   if(!TextUtils.isEmpty(city))
         //   {
           //     currentLocation=city;

             //   if (!TextUtils.isEmpty(postalCode))
                    currentLocation = postalCode;

              //  if (!TextUtils.isEmpty(state))
              //      currentLocation+="\n"+state;

         //       if (!TextUtils.isEmpty(country))
         //           currentLocation+="\n"+country;
         //   }

            return currentLocation;
        }
        else {
           showToast("Something went wrong");
           return "";
       }
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

    public void showToast(String message)
    {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

    /* Retry fetching Location */
    void retryFetchLocation() {
        /* Handler delays message from disappearing */
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            public void run() {
                address = getLocation();
            }
        }, 20);
    }

}
