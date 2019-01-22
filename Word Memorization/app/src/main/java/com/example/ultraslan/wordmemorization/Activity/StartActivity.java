package com.example.ultraslan.wordmemorization.Activity;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ultraslan.wordmemorization.Fragment.StartFragment;
import com.example.ultraslan.wordmemorization.R;
import com.example.ultraslan.wordmemorization.Utils.ConnectionDetector;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class StartActivity extends AppCompatActivity {

    private Context mContext = StartActivity.this;
    private ConnectionDetector connectionDetector;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        control();
    }

    private void control(){
        connectionDetector = new ConnectionDetector(mContext);

        if(connectionDetector.isConnected())
            init();
        else{
            Toast.makeText(mContext,getString(R.string.controlconnection ),Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void init(){
        StartFragment fragment = new StartFragment();
        FragmentTransaction transaction = StartActivity.this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,fragment);
        transaction.addToBackStack(getString(R.string.start_fragment));
        transaction.commit();
    }
}
