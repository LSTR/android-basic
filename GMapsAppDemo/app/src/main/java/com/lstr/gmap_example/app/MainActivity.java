package com.lstr.gmap_example.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements MainFragment.OnListener{
    private final MainFragment mainFragment = MainFragment.instance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showFragment();
    }

    private void showFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment).commit();
    }
}