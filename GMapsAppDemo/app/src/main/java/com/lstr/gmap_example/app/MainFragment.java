package com.lstr.gmap_example.app;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by JMTech-Android on 29/12/2015.
 */
public class MainFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener,GoogleMap.OnMarkerClickListener{

    LayoutInflater inflater;
    private OnListener presenter;
    private Context context;

    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private float ZOOM_MAP = 5.5f;

    public static MainFragment instance(){
        return new MainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        this.inflater = inflater;
        this.context = getContext();

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return rootView;
    }

    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof OnListener) {
            presenter = (OnListener) context;
        } else {
            throw new ClassCastException("debe implementar On?Listener");
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    public void clearMap() {
        mMap.clear();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMarkerClickListener(this);
    }

    public interface OnListener{
    }
}