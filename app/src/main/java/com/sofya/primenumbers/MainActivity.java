package com.sofya.primenumbers;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GametFragment.onGameFragmentInteractionListener, EndFragment.onEndFragmentInteractionListener,
        StartFragment.onStartFragmentInteractionListener {
    FragmentManager fr_man;
    public static ArrayList<Statistic> allResults;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        allResults = new ArrayList<>();
        fr_man = getSupportFragmentManager();
        StartFragment fr = new StartFragment();
        FragmentTransaction fr_trans = fr_man.beginTransaction();
        fr_trans.add(R.id.Box, fr).commit();

    }

    public void goToGame() {
        GametFragment fr = new GametFragment();
        FragmentTransaction fr_trans = fr_man.beginTransaction();
        fr_trans.replace(R.id.Box, fr).commit();
    }


    public void goToResult() {
        EndFragment fr = new EndFragment();
        FragmentTransaction fr_trans = fr_man.beginTransaction();
        fr_trans.replace(R.id.Box, fr).commit();
    }

    public void goToRules() {
        StartFragment fr = new StartFragment();
        FragmentTransaction fr_trans = fr_man.beginTransaction();
        fr_trans.replace(R.id.Box, fr).commit();
    }

    @Override
    public void onGameFragmentInteraction(Uri uri) {

    }


    @Override
    public void onEndFragmentInteraction(Uri uri) {

    }


    @Override
    public void onStartFragmentInteraction(Uri uri) {

    }

    private void uploadTest() {
        int i = 2 + 1;
    }
}
