package com.capstonegyg.gyg.UI.PostGyg;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.capstonegyg.gyg.R;
import com.capstonegyg.gyg.UI.PostGyg.LocationHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyLocationUsingHelper extends PostGygActivity implements ConnectionCallbacks,
        OnConnectionFailedListener,OnRequestPermissionsResultCallback {


  //  @BindView(R.id.btnLocation)Button btnProceed;
    @BindView(R.id.gyg_area)EditText tvAddress;
 //   @BindView(R.id.tvEmpty)TextView tvEmpty;
    @BindView(R.id.rlPickLocation)TextView rlPick;

    private Location mLastLocation;
  //  private Context context;
  //  private Activity current_activity;

    double latitude;
    double longitude;

    String currentLocation;

    LocationHelper locationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        locationHelper=new LocationHelper(this);
        locationHelper.checkpermission();

        ButterKnife.bind(this);

        rlPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mLastLocation=locationHelper.getLocation();

                if (mLastLocation != null) {
                    latitude = mLastLocation.getLatitude();
                    longitude = mLastLocation.getLongitude();
                    getAddress();

                } else {
                    showToast("Couldn't get the location. Make sure location is enabled on the device");
                }
            }
        });

        // check availability of play services
        if (locationHelper.checkPlayServices()) {

            // Building the GoogleApi client
            locationHelper.buildGoogleApiClient();
        }
    }

    /* get the address from latitude and longitude */

    public void getAddress()
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

                tvAddress.setText(currentLocation);
                tvAddress.setVisibility(View.VISIBLE);
            }
        }
        else
            showToast("Something went wrong");
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
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }



}
