package com.example.ultraslan.wordmemorization.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.ultraslan.wordmemorization.Fragment.AddWordFragment;
import com.example.ultraslan.wordmemorization.R;
import com.example.ultraslan.wordmemorization.Utils.ConnectionDetector;

public class AddWordActivity extends AppCompatActivity {

    private Context mContext = AddWordActivity.this;
    private ConnectionDetector connectionDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addword);

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
        AddWordFragment fragment = new AddWordFragment();
        FragmentTransaction transaction = AddWordActivity.this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,fragment);
        transaction.addToBackStack(getString(R.string.fragment_addword));
        transaction.commit();
    }


}
