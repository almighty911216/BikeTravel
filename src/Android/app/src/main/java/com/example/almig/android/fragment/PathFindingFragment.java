package com.example.almig.android.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.almig.android.R;
import com.example.androidbootstrap.BootstrapButton;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapInfo;
import com.skp.Tmap.TMapMarkerItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapPolyLine;
import com.skp.Tmap.TMapView;

import java.util.ArrayList;

import static com.nostra13.universalimageloader.core.ImageLoader.TAG;

/**
 * Created by almig on 2017-06-20.
 */

public class PathFindingFragment extends Fragment implements PlaceSelectionListener {
    private View mRootView;
    private TMapView mTMapView;
    private BootstrapButton mBtnPathFinding;
    private PlaceAutocompleteFragment placeAutocompleteFragmentStarting;
    private PlaceAutocompleteFragment placeAutocompleteFragmentEnd;
    private TMapPoint startingPoint;
    private TMapPoint endPoint;

    public static PathFindingFragment newInstance() {
        return new PathFindingFragment();
    }

    private void initBinding(View rootView) {
        mRootView = rootView;

        mBtnPathFinding = (BootstrapButton)mRootView.findViewById(R.id.btn_pathfinding);
        mBtnPathFinding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(startingPoint == null || endPoint == null) {
                    return;
                }
                mTMapView.removeAllMarkerItem();
                final ArrayList<TMapPoint> arrayList = new ArrayList<>();

                TMapData tMapData = new TMapData();
                tMapData.findPathData(startingPoint, endPoint, new TMapData.FindPathDataListenerCallback() {
                    @Override
                    public void onFindPathData(TMapPolyLine tMapPolyLine) {
                        mTMapView.addTMapPath(tMapPolyLine);
                        //Map Zoom 을 새로 해준다.
                        arrayList.add(startingPoint);
                        arrayList.add(endPoint);

                        TMapInfo info = mTMapView.getDisplayTMapInfo(arrayList);
                        mTMapView.setCenterPoint(info.getTMapPoint().getLongitude(), info.getTMapPoint().getLatitude());
                        mTMapView.setZoomLevel(info.getTMapZoomLevel());

                        Log.i("Path Distance",String.valueOf(tMapPolyLine.getDistance()));
                    }
                });
            }
        });
        placeAutocompleteFragmentStarting = (PlaceAutocompleteFragment)getActivity().getFragmentManager().findFragmentById(R.id.autocomplete_fragment_starting);
        placeAutocompleteFragmentStarting.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // Format the returned place's details and display them in the TextView.
                LatLng latLng = place.getLatLng();
                TMapMarkerItem tMapMarkerItem = new TMapMarkerItem();
                tMapMarkerItem.setName(place.getName().toString());
                startingPoint = new TMapPoint(latLng.latitude, latLng.longitude);
                tMapMarkerItem.setTMapPoint(startingPoint);
                tMapMarkerItem.setVisible(TMapMarkerItem.VISIBLE);
                Bitmap bitmap = BitmapFactory.decodeResource(mRootView.getResources(), R.drawable.ic_marker_starting);
                tMapMarkerItem.setIcon(bitmap);
                tMapMarkerItem.setPosition(0.5f, 1.0f);
                mTMapView.setCenterPoint(latLng.longitude, latLng.latitude);
                mTMapView.setZoomLevel(18);

                mTMapView.addMarkerItem(place.getId(), tMapMarkerItem);
            }

            @Override
            public void onError(Status status) {

            }
        });

        placeAutocompleteFragmentEnd = (PlaceAutocompleteFragment)getActivity().getFragmentManager().findFragmentById(R.id.autocomplete_fragment_end);
        placeAutocompleteFragmentEnd.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // Format the returned place's details and display them in the TextView.
                LatLng latLng = place.getLatLng();
                TMapMarkerItem tMapMarkerItem = new TMapMarkerItem();
                tMapMarkerItem.setName(place.getName().toString());
                endPoint = new TMapPoint(latLng.latitude, latLng.longitude);
                tMapMarkerItem.setTMapPoint(endPoint);
                tMapMarkerItem.setVisible(TMapMarkerItem.VISIBLE);
                Bitmap bitmap = BitmapFactory.decodeResource(mRootView.getResources(), R.drawable.ic_marker_end);
                tMapMarkerItem.setIcon(bitmap);
                tMapMarkerItem.setPosition(0.5f, 1.0f);
                mTMapView.setCenterPoint(latLng.longitude, latLng.latitude);
                mTMapView.setZoomLevel(18);

                mTMapView.addMarkerItem(place.getId(), tMapMarkerItem);
            }

            @Override
            public void onError(Status status) {

            }
        });
        mTMapView = (TMapView) mRootView.findViewById(R.id.tmap_view);
        mTMapView.setSKPMapApiKey(getString(R.string.tmap_api));
    }

    public PathFindingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_path_finding, container, false);

        initBinding(rootView);

        return rootView;
    }

    /**
     * Callback invoked when a place has been selected from the PlaceAutocompleteFragment.
     */
    @Override
    public void onPlaceSelected(Place place) {
        Log.i(TAG, "Place Selected: " + place.getName());


    }

    /**
     * Callback invoked when PlaceAutocompleteFragment encounters an error.
     */
    @Override
    public void onError(Status status) {
        Log.e(TAG, "onError: Status = " + status.toString());
        Toast.makeText(getContext(), "Place selection failed: " + status.getStatusMessage(), Toast.LENGTH_SHORT).show();
    }
}
