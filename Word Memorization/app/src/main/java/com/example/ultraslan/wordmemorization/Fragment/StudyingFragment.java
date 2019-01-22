package com.example.ultraslan.wordmemorization.Fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatDrawableManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ultraslan.wordmemorization.Activity.StudyingActivity;
import com.example.ultraslan.wordmemorization.Model.Words;
import com.example.ultraslan.wordmemorization.R;
import com.example.ultraslan.wordmemorization.Utils.FirebaseMethods;
import com.example.ultraslan.wordmemorization.Utils.NotificationHelper;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class StudyingFragment extends Fragment {

    private Context mContext;

    private TextView info;
    private Button next,prev,wordlisten,wordmean,exit;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseMethods mFirebaseMethods;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private String userID;

    ArrayList<Words> wordbox;
    private int wordCount = 0;
    private AdView mAdView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_studying,container,false);

        mContext = getActivity();

        info =(TextView) view.findViewById(R.id.tv_infowords);
        wordlisten =(Button) view.findViewById(R.id.iv_listenword);
        wordmean = (Button) view.findViewById(R.id.btn_wordmean);
        next =(Button) view.findViewById(R.id.btn_nextword);
        prev =(Button) view.findViewById(R.id.btn_preword);
        exit = (Button) view.findViewById(R.id.btn_exit);
        wordbox = new ArrayList<>();

        Bundle bundleObject = getActivity().getIntent().getExtras();
        wordbox = (ArrayList<Words>) bundleObject.getSerializable("wordlistbox");

        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mFirebaseMethods = new FirebaseMethods(mContext);

        setupFirebaseAuth();
        setupStudingtoPage();

        return view;
    }

    private void setupStudingtoPage(){

        showTheWord();
        whenClickTheWord();
    }

    private void showTheWord() {
        if(wordCount ==0)
            prev.setVisibility(View.INVISIBLE);
        if(wordCount>=wordbox.size()){
            Toast.makeText(mContext,getString(R.string.studyfrag_infostudy),Toast.LENGTH_SHORT).show();
            wordbox.clear();
            getActivity().finish();
        }else {
            info.setText((wordCount + 1) + " / " + wordbox.size());

            String mWord = wordbox.get(wordCount).getWord();
            final String mMean1 = wordbox.get(wordCount).getMean1();
            final String mMean2 = wordbox.get(wordCount).getMean2();
            final String mMean3 = wordbox.get(wordCount).getMean3();
            final String mMean4 = wordbox.get(wordCount).getMean4();
            final String mMean5 = wordbox.get(wordCount).getMean5();

            wordmean.setText(mWord);

            wordmean.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    wordmean.setText(mMean1 + checkinputs(mMean2) + checkinputs(mMean3)
                            + checkinputs(mMean4) + checkinputs(mMean5));

                    wordmean.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showTheWord();
                        }
                    });
                }
            });
        }
    }

    private String checkinputs(String mean) {
        if(mean != null)
            return "\n" + mean;

        return "";

    }

    private void whenClickTheWord(){

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passthenextbox();
                wordCount++;
                prev.setVisibility(View.VISIBLE);
                showTheWord();
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(wordCount==0)
                    prev.setVisibility(View.INVISIBLE);
                wordCount--;
                showTheWord();
            }
        });
    }

    private void passthenextbox() {
        if(wordbox.get(wordCount).getStudydate() == null ){
            String key = wordbox.get(wordCount).getWord_id();
            int situation = wordbox.get(wordCount).getSituation();
            situation++;
            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String today = dateFormat.format(date);
            mFirebaseMethods.uploadWordafterStudy(key,situation,today);
        }
    }

    private void setupFirebaseAuth(){
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        userID = mAuth.getCurrentUser().getUid();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user!= null)
                {

                }else{

                }

            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mAuthListener!= null)
        {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}
