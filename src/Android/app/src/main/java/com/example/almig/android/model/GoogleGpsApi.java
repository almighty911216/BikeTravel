package com.example.almig.android.model;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by ailab on 2017-05-29.
 */

public class GoogleGpsApi extends Thread implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener{
    private Activity activity;
    private static final LatLng DEFAULT_LOCATION = new LatLng(37.54, 126.97);
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 2002;
    private static final int UPDATE_INTERVAL_MS =  200;  // 0.5초
    private static final int FASTEST_UPDATE_INTERVAL_MS = 200; // 0.5초
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private double last_latitude = 0;
    private double last_longitude = 0;
    private double last_updates = 0;
    private Cycling cycling;
    private GoogleApiClient googleApiClient = null;
    boolean askPermissionOnceAgain = false;
    private LocationManager locationManager;
    private final String TAG = "googleGpsAPI";
    private Handler handler;
    private final android.location.LocationListener locationListener = new android.location.LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if(location.getProvider().equals(LocationManager.GPS_PROVIDER)){
                cycling.UpdateVelocity(last_latitude, last_longitude, location.getLatitude(), location.getLongitude(), last_updates);
                last_latitude = location.getLatitude();
                last_longitude = location.getLongitude();
                last_updates = System.currentTimeMillis();
                Message message = handler.obtainMessage();
                Object[] objects = new Object[6];
                objects[0] = cycling.getCurrent_velocity();
                objects[1] = cycling.getAvg_velocity();
                objects[2] = cycling.getRPM();
                objects[3] = cycling.CalcualteCalories();
                objects[4] = cycling.getTotal_distance();
                objects[5] = cycling.getTime();
                message.what = 0;
                message.obj = objects;
                handler.sendMessage(message);
            }else if(location.getProvider().equals(LocationManager.NETWORK_PROVIDER)){
                cycling.UpdateVelocity(last_latitude, last_longitude, location.getLatitude(), location.getLongitude(), last_updates);
                last_latitude = location.getLatitude();
                last_longitude = location.getLongitude();
                last_updates = System.currentTimeMillis();
                Message message = handler.obtainMessage();
                Object[] objects = new Object[6];
                objects[0] = cycling.getCurrent_velocity();
                objects[1] = cycling.getAvg_velocity();
                objects[2] = cycling.getRPM();
                objects[3] = cycling.CalcualteCalories();
                objects[4] = cycling.getTotal_distance();
                objects[5] = cycling.getTime();
                message.what = 0;
                message.obj = objects;
                handler.sendMessage(message);
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.d(TAG, "provider disabled");
        }
    };


    public GoogleGpsApi(Activity activity, Handler handler, Cycling cycling) {
        this.activity = activity;
        this.cycling = cycling;
        this.handler = handler;
        buildGoogleApiClient();
        locationManager = (LocationManager)activity.getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void run() {
        super.run();
        while(googleApiClient == null){
            googleApiClient.reconnect();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        cycling.UpdateVelocity(last_latitude, last_longitude, location.getLatitude(), location.getLongitude(), last_updates);
        last_latitude = location.getLatitude();
        last_longitude = location.getLongitude();
        last_updates = System.currentTimeMillis();
        Message message = handler.obtainMessage();
        Object[] objects = new Object[6];
        objects[0] = cycling.getCurrent_velocity();
        objects[1] = cycling.getAvg_velocity();
        objects[2] = cycling.getRPM();
        objects[3] = cycling.CalcualteCalories();
        objects[4] = cycling.getTotal_distance();
        objects[5] = cycling.getTime();
        message.what = 0;
        message.obj = objects;
        handler.sendMessage(message);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "Connected");
        if(!checkLocationServicesStatus()){
            showDialogForLocationServiceSetting();
        }
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL_MS);
        locationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_MS);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(activity,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
            if(ActivityCompat.checkSelfPermission(activity,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ){
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
            if(googleApiClient.isConnected()) {
                LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
            }else{
                registerLocationUpdates();
            }
        }else{
            if(googleApiClient.isConnected()) {
                LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
            }else{
                registerLocationUpdates();
            }
        }
    }

    @Override
    public void onConnectionSuspended(int cause) {
        if(cause == CAUSE_NETWORK_LOST){
            Log.e(TAG, "onConnectionSuspended() : Google Play Service" + " : Network Lost");
        }else if(cause == CAUSE_SERVICE_DISCONNECTED){
            Log.e(TAG, "onConnectionSuspended() : Google Play Service" + " : Service Disconnected");
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Location location = null;
        location.setLatitude(DEFAULT_LOCATION.latitude);
        location.setLongitude(DEFAULT_LOCATION.longitude);
        Log.d(TAG,"Connection Failed" + connectionResult);

    }

    protected synchronized void buildGoogleApiClient(){
        googleApiClient = new GoogleApiClient.Builder(activity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }

    private void checkPermissions(){
        if(ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions((Activity)activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
        if(ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ){
            ActivityCompat.requestPermissions((Activity)activity,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    public boolean checkLocationServicesStatus(){
        LocationManager locationManager = (LocationManager)activity.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void showDialogForPermission(String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("알림");
        builder.setMessage(msg);
        builder.setCancelable(true);
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCompat.requestPermissions((Activity)activity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
        });
        builder.setNegativeButton("아니요",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                application_finish(activity);
            }
        });
        builder.create().show();
    }

    public void showDialogForPermissionSetting(String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("알림");
        builder.setMessage(msg);
        builder.setCancelable(true);
        builder.setPositiveButton("예",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                askPermissionOnceAgain = true;
                Intent myAppSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package: " + activity.getPackageName()));
                myAppSettings.addCategory(Intent.CATEGORY_DEFAULT);
                myAppSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(myAppSettings);
            }
        });
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                application_finish(activity);
            }
        });
        builder.create().show();
    }

    public void showDialogForLocationServiceSetting(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n" + "위치 설정을 하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent callGPSSettingIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                activity.startActivity(callGPSSettingIntent);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    public void FinishThread(){
        googleApiClient.unregisterConnectionCallbacks(this);
        googleApiClient.unregisterConnectionFailedListener(this);
    }


    public void application_finish(Context context){
        Intent homeintent = new Intent(Intent.ACTION_MAIN);
        homeintent.addCategory(Intent.CATEGORY_HOME);
        homeintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(homeintent);
    }


    private void registerLocationUpdates(){
        if(ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 1, locationListener);
        }else{
            ActivityCompat.requestPermissions((Activity)activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 1, locationListener);
        }

        if(ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 1, locationListener);
        }else{
            ActivityCompat.requestPermissions((Activity)activity,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 1, locationListener);
        }
    }
}
