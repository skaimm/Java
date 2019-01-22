package com.example.ultraslan.wordmemorization.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ParseException;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ultraslan.wordmemorization.Activity.DetailsActivity;
import com.example.ultraslan.wordmemorization.Activity.LoginActivity;
import com.example.ultraslan.wordmemorization.Activity.RepeatingActivity;
import com.example.ultraslan.wordmemorization.Activity.StudyingActivity;
import com.example.ultraslan.wordmemorization.Model.User;
import com.example.ultraslan.wordmemorization.Model.Words;
import com.example.ultraslan.wordmemorization.R;
import com.example.ultraslan.wordmemorization.Utils.FirebaseMethods;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class StudyFragment extends Fragment {


    private Context mContext;
    private Button back,study,repeat,detail;
    private AdView mAdView;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseMethods mFirebaseMethods;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private String userID;

    ArrayList<Words> wordbox;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_study,container,false);

        mContext = getActivity();
        mFirebaseMethods = new FirebaseMethods(mContext);

        back = (Button) view.findViewById(R.id.btn_studyback);
        study = (Button) view.findViewById(R.id.btn_studynewword);
        repeat = (Button) view.findViewById(R.id.btn_studyrepeatword);
        detail = (Button) view.findViewById(R.id.btn_studydetails);

        Bundle bundleObject = getActivity().getIntent().getExtras();
        wordbox = (ArrayList<Words>) bundleObject.getSerializable("listbox");

        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        setupFirebaseAuth();

        define();
        return view;
    }

    void define() {
        back.setOnClickListener(buttonClickListener);
        study.setOnClickListener(buttonClickListener);
        repeat.setOnClickListener(buttonClickListener);
        detail.setOnClickListener(buttonClickListener);
    }

    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_studyback:
                    getActivity().finish();
                    break;
                case R.id.btn_studynewword:
                    Intent intent1 = new Intent(mContext, StudyingActivity.class);
                    directToPages(intent1,"studying");
                    break;
                case R.id.btn_studyrepeatword:
                    Intent intent2 = new Intent(mContext, RepeatingActivity.class);
                    directToPages(intent2,"repeating");
                    break;
                case R.id.btn_studydetails:
                    Intent intent3 = new Intent(mContext, DetailsActivity.class);
                    directToPages(intent3,"all");
                    break;

            }
        }
    };

    private void directToPages(Intent intent, String key){
        ArrayList<Words> wordToPages = new ArrayList<>();
        for(Words words: wordbox){
            if(key.equals("studying")) {
                if (words.isIsitnew())
                    wordToPages.add(words);
            }
            if(key.equals("all")){
                wordToPages.add(words);
            }
            if(key.equals("repeating")){
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date mydate = new Date(System.currentTimeMillis()- (1000 * 60 * 60 * 24));
                String yesterday = dateFormat.format(mydate);

                if(words.getSituation() == 1 ){
                    try {
                        Date startdate = dateFormat.parse(yesterday);
                        Date studydate = dateFormat.parse(words.getStudydate()); //06.08.2018
                        if(startdate.compareTo(studydate) == 0){
                            wordToPages.add(words);
                        }
                        if(startdate.compareTo(studydate) > 0){
                            mFirebaseMethods.uploadWordafterStudy(words.getWord_id(),1,yesterday);
                            wordToPages.add(words);
                        }
                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }
                }
                if(words.getSituation() == 2 ){
                    try {
                        Date startdate = dateFormat.parse(yesterday);
                        Date studydate = dateFormat.parse(words.getStudydate()); //06.08.2018
                        if(startdate.compareTo(studydate) == 0){
                            wordToPages.add(words);
                        }
                        if(startdate.compareTo(studydate) > 0){
                            mFirebaseMethods.uploadWordafterStudy(words.getWord_id(),1,yesterday);
                            wordToPages.add(words);
                        }
                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }
                }if(words.getSituation() == 3 ){
                    try {
                        Date startdate = dateFormat.parse(yesterday); //05.08.2018 // 06.08.2018
                        Date studydate = dateFormat.parse(words.getStudydate()); //06.08.2018
                        if(startdate.compareTo(studydate) == 0){
                            wordToPages.add(words);
                        }
                        if(startdate.compareTo(studydate) > 0){
                            mFirebaseMethods.uploadWordafterStudy(words.getWord_id(),1,yesterday);
                            wordToPages.add(words);
                        }
                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }

                }
                if(words.getSituation() == 4 ){
                    Date mydate2 = new Date(System.currentTimeMillis()- (1000 * 60 * 60 * 24 * 2));
                    String lastday = dateFormat.format(mydate2);

                    try {
                        Date startdate = dateFormat.parse(lastday);
                        Date studydate = dateFormat.parse(words.getStudydate());
                        if(startdate.compareTo(studydate) == 0){
                            wordToPages.add(words);
                        }
                        if(startdate.compareTo(studydate) > 0){
                            mFirebaseMethods.uploadWordafterStudy(words.getWord_id(),1,yesterday);
                            wordToPages.add(words);
                        }
                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }
                }
                if(words.getSituation() == 5 ){
                    Date mydate2 = new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24*3));
                    String lastday = dateFormat.format(mydate2);

                    try {
                        Date startdate = dateFormat.parse(lastday);
                        Date studydate = dateFormat.parse(words.getStudydate());
                        if(startdate.compareTo(studydate) == 0){
                            wordToPages.add(words);
                        }
                        if(startdate.compareTo(studydate) > 0){
                            mFirebaseMethods.uploadWordafterStudy(words.getWord_id(),1,yesterday);
                            wordToPages.add(words);
                        }
                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }
                }
                if(words.getSituation() == 6 ){
                    Date mydate2 = new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24*7));
                    String lastday = dateFormat.format(mydate2);
                    try {
                        Date startdate = dateFormat.parse(lastday);
                        Date studydate = dateFormat.parse(words.getStudydate());
                        if(startdate.compareTo(studydate) == 0){
                            wordToPages.add(words);
                        }
                        if(startdate.compareTo(studydate) > 0){
                            mFirebaseMethods.uploadWordafterStudy(words.getWord_id(),1,yesterday);
                            wordToPages.add(words);
                        }
                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }
                }
                if(words.getSituation() == 7 ){
                    Date mydate2 = new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24 *15));
                    String lastday = dateFormat.format(mydate2);
                    try {
                        Date startdate = dateFormat.parse(lastday);
                        Date studydate = dateFormat.parse(words.getStudydate());
                        if(startdate.compareTo(studydate) == 0){
                            wordToPages.add(words);
                        }
                        if(startdate.compareTo(studydate) > 0){
                            mFirebaseMethods.uploadWordafterStudy(words.getWord_id(),1,yesterday);
                            wordToPages.add(words);
                        }
                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }
                }
                if(words.getSituation() == 8 ){
                    Date mydate2 = new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24 *30));
                    String lastday = dateFormat.format(mydate2);
                    try {
                        Date startdate = dateFormat.parse(lastday);
                        Date studydate = dateFormat.parse(words.getStudydate());
                        if(startdate.compareTo(studydate) == 0){
                            wordToPages.add(words);
                        }
                        if(startdate.compareTo(studydate) > 0){
                            mFirebaseMethods.uploadWordafterStudy(words.getWord_id(),1,yesterday);
                            wordToPages.add(words);
                        }
                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }
                }
                if(words.getSituation() == 9 ){
                    Date mydate2 = new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24*60));
                    String lastday = dateFormat.format(mydate2);
                    try {
                        Date startdate = dateFormat.parse(lastday);
                        Date studydate = dateFormat.parse(words.getStudydate());
                        if(startdate.compareTo(studydate) == 0){
                            wordToPages.add(words);
                        }
                        if(startdate.compareTo(studydate) > 0){
                            mFirebaseMethods.uploadWordafterStudy(words.getWord_id(),1,yesterday);
                            wordToPages.add(words);
                        }
                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }
                }
                if(words.getSituation() == 10 ){
                    Date mydate2 = new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24*120));
                    String lastday = dateFormat.format(mydate2);
                    try {
                        Date startdate = dateFormat.parse(lastday);
                        Date studydate = dateFormat.parse(words.getStudydate());
                        if(startdate.compareTo(studydate) == 0){
                            wordToPages.add(words);
                        }
                        if(startdate.compareTo(studydate) > 0){
                            mFirebaseMethods.uploadWordafterStudy(words.getWord_id(),1,yesterday);
                            wordToPages.add(words);
                        }
                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }
                }
                if(words.getSituation() == 11 ){
                    Date mydate2 = new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24*180));
                    String lastday = dateFormat.format(mydate2);
                    try {
                        Date startdate = dateFormat.parse(lastday);
                        Date studydate = dateFormat.parse(words.getStudydate());
                        if(startdate.compareTo(studydate) == 0){
                            wordToPages.add(words);
                        }
                        if(startdate.compareTo(studydate) > 0){
                            mFirebaseMethods.uploadWordafterStudy(words.getWord_id(),1,yesterday);
                            wordToPages.add(words);
                        }
                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        TransferToPages(intent,wordToPages);
    }

    private void TransferToPages(Intent intent,ArrayList wordsbox) {

        Bundle bundle = new Bundle();
        bundle.putSerializable("wordlistbox",wordsbox);
        intent.putExtras(bundle);
        startActivity(intent);
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
