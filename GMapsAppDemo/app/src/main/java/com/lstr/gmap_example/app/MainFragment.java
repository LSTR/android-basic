package com.lstr.gmap_example.app;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private AppCompatActivity activity;

    public static MainFragment instance(){
        return new MainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        activity = (AppCompatActivity) getActivity();

        Toolbar toolbar = (Toolbar)rootView.findViewById(R.id.toolbar);
        toolbar.setTitle("Map Demo");
        activity.setSupportActionBar(toolbar);

        this.inflater = inflater;
        this.context = getContext();

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Button btn = (Button) rootView.findViewById(R.id.cargar_ubicacion);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarUbicacion();
            }
        });

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


    private void cargarUbicacion() {
        new LongOperation().execute("");
    }

    private class LongOperation extends AsyncTask<String, Void, String> {
        @Override

        protected String doInBackground(String... params) {
            GetMethodEx test = new GetMethodEx();
            String returned = null;

            try {
                returned = test.getInternetData();

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return returned;
        }

        @Override
        protected void onPostExecute(String result) {
            JSONObject jo = null;
            try {
                jo = new JSONObject(result);
                procesarJson(jo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void procesarJson(JSONObject jo) throws JSONException {
        JSONArray coordenada = jo.getJSONArray("coordenada");
        for (int i=0; i<coordenada.length(); i++){
            JSONObject jsonCoordenadas = coordenada.getJSONObject(i);
            double latitud = jsonCoordenadas.getDouble("latitud");
            double longitud = jsonCoordenadas.getDouble("longitud");
            mostrarUbicacion(latitud, longitud);
        }
    }

    private void mostrarUbicacion(double latitud, double longitud) {
        LatLng coordinate = new LatLng(latitud, longitud);
        CameraUpdate tuUbicacion = CameraUpdateFactory.newLatLngZoom(coordinate, ZOOM_MAP);
        mMap.animateCamera(tuUbicacion);

        mostrarMarcador(coordinate);
    }

    private void mostrarMarcador(LatLng coordinate) {
        MarkerOptions mp = new MarkerOptions();
        mp.position(coordinate);
        mp.title("Ubicacon Encontrada");
        mMap.addMarker(mp);
    }

    public interface OnListener{
    }
}