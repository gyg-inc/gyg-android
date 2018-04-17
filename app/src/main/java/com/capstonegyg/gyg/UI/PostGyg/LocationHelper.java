package com.capstonegyg.gyg.UI.PostGyg;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.capstonegyg.gyg.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class LocationHelper extends AppCompatActivity implements com.capstonegyg.gyg.UI.PostGyg.PermissionUtils.PermissionResultCallback{

    private Context context;
    private Activity current_activity;

    private boolean isPermissionGranted;

    // Location variables
    private Location l;
    private FusedLocationProviderClient mmLastLocation;

    // list of permissions
    private ArrayList<String> permissions=new ArrayList<>();
    private com.capstonegyg.gyg.UI.PostGyg.PermissionUtils permissionUtils;

    private final static int PLAY_SERVICES_REQUEST = 1000;

    /* Constructor */
    public LocationHelper(Context context) {

        this.context=context;
        this.current_activity= (Activity) context;

        permissionUtils=new com.capstonegyg.gyg.UI.PostGyg.PermissionUtils(context,this);

        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);

    }

    /* Checks if location access is granted or not */
    public void checkpermission()
    {
        permissionUtils.check_permission(permissions,"Need GPS permission for getting your location",1);
    }

    private boolean isPermissionGranted() {
        return isPermissionGranted;
    }


    public boolean checkPlayServices() {

        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();

        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(context);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(resultCode)) {
                googleApiAvailability.getErrorDialog(current_activity,resultCode,
                        PLAY_SERVICES_REQUEST).show();
            } else {
                showToast("This device is not supported.");
            }
            return false;
        }
        return true;
    }

    /* Returns Location Object */
    public Location getLocation() {

        if (isPermissionGranted()) {

            try
            {
                mmLastLocation = LocationServices.getFusedLocationProviderClient(context);

                mmLastLocation.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(location != null) {
                            l = location;
                        }
                    }
                });
                return l;
            }
            catch (SecurityException e)
            {
                e.printStackTrace();

            }
        }
        return null;
    }

    /* Returns Address Object based on Latitude and Longitude */
    public Address getAddress(double latitude,double longitude)
    {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(context, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude,longitude, 1);
            return addresses.get(0);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /* Handles Permission Results */
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
       permissionUtils.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    @Override
    public void PermissionGranted(int request_code) {
        Log.i("PERMISSION","GRANTED");
        isPermissionGranted=true;
    }

    @Override
    public void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions) {
        Log.i("PERMISSION PARTIALLY","GRANTED");
    }

    @Override
    public void PermissionDenied(int request_code) {
        Log.i("PERMISSION","DENIED");
    }

    @Override
    public void NeverAskAgain(int request_code) {
        Log.i("PERMISSION","NEVER ASK AGAIN");
    }

    private void showToast(String message)
    {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }


}
