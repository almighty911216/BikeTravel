package com.example.almig.android.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.almig.android.R;
import com.example.almig.android.util.PermissionUtils;
import com.example.androidbootstrap.BootstrapButton;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

/**
 * Created by almig on 2017-06-20.
 */

public class ParkingLotFragment extends Fragment implements OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback {
    private static final int MY_LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final int LOCATION_LAYER_PERMISSION_REQUEST_CODE = 2;

    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean mLocationPermissionDenied = false;

    private View mRootView;
    private GoogleMap mMap;
    private UiSettings mUiSettings;
    private BootstrapButton mBtnParking;
    private BootstrapButton mBtnFindParkingLoc;
    private TextView mTvParkingLoc;
    private TextView mTvParkingTime;
    private LocationManager manager;

    private Double mLatitude;
    private Double mLongitude;

    private Double mParkedLatitude;
    private Double mParkedLongitude;

    public static ParkingLotFragment newInstance() {
        return new ParkingLotFragment();
    }

    private void initBinding(View rootView) {
        mRootView = rootView;
        mBtnParking = (BootstrapButton) mRootView.findViewById(R.id.btn_park);
        mBtnParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mParkedLatitude = mLatitude;
                mParkedLongitude = mLongitude;



                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            OkHttpClient client = new OkHttpClient();
                            String url = "https://maps.googleapis.com/maps/api/geocode/json?";
                            url += "latlng=";
                            url += String.valueOf(mLatitude);
                            url += ",";
                            url += String.valueOf(mLongitude);
                            url += "&key=";
                            url += getString(R.string.google_maps_key);

                            Request request = new Request.Builder().url(url).get().addHeader("cache-control", "no-cache").addHeader("postman-token", "41d12ac3-639c-ff36-6051-1a2a24dab5c0").build();

                            Response response = client. newCall(request).execute();
                            response.toString();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();




//                mTvParkingLoc.setText(adressInfo.strFullAddress);
                Date date = new Date(System.currentTimeMillis());
                SimpleDateFormat sdfnow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                String strnow = sdfnow.format(date);

                mTvParkingTime.setText(strnow);

                mMap.addMarker(new MarkerOptions().position(new LatLng(mLatitude, mLongitude)).title("my bike"));
            }
        });
        mBtnFindParkingLoc = (BootstrapButton)mRootView.findViewById(R.id.btn_find_parking_loc);
        mBtnFindParkingLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mParkedLatitude, mParkedLongitude), 18));
            }
        });

        mTvParkingLoc = (TextView)mRootView.findViewById(R.id.tv_parking_loc);
        mTvParkingTime = (TextView)mRootView.findViewById(R.id.tv_parking_time);

        manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }

        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 1, mLocationalListener);
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 1, mLocationalListener);
    }

    private void initGMap() {
        SupportMapFragment mapFragment = (SupportMapFragment)this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(getActivity(), ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, MY_LOCATION_PERMISSION_REQUEST_CODE);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    public ParkingLotFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        stopLocationService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_parking_lot, container, false);

        initBinding(rootView);
        initGMap();

        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        enableMyLocation();

        mUiSettings = mMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setCompassEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != MY_LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults, ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mLocationPermissionDenied = true;
        }
    }

    private void stopLocationService() {
        if (ContextCompat.checkSelfPermission(getActivity(), ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, MY_LOCATION_PERMISSION_REQUEST_CODE);
        }
        manager.removeUpdates(mLocationalListener);
    }

    private final LocationListener mLocationalListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            mLatitude = location.getLatitude();
            mLongitude = location.getLongitude();
//            stopLocationService();
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
    };
}
