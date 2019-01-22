package com.example.ultraslan.wordmemorization.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ultraslan.wordmemorization.Activity.StoreActivity;
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
import com.google.firebase.database.ValueEventListener;

public class AddWordFragment extends Fragment {

    private Context mContext;
    private Button back,add;
    private EditText word,mean1,mean2,mean3,mean4,mean5;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseMethods mFirebaseMethods;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    private AdView mAdView;

    private int wordCount = 0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addword,container,false);

        mContext = getActivity();

        mFirebaseMethods = new FirebaseMethods(mContext);

        back = (Button) view.findViewById(R.id.btn_wordback);
        add = (Button) view.findViewById(R.id.btn_wordadd);
        word = (EditText) view.findViewById(R.id.et_wordnew);
        mean1 = (EditText) view.findViewById(R.id.et_wordmean);
        mean2 = (EditText) view.findViewById(R.id.et_wordmean2);
        mean3 = (EditText) view.findViewById(R.id.et_wordmean3);
        mean4 = (EditText) view.findViewById(R.id.et_wordmean4);
        mean5 = (EditText) view.findViewById(R.id.et_wordmean5);

        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        define();
        setupFirebaseAuth();

        return view;
    }

    void define(){
        back.setOnClickListener(buttonClickListener);
        add.setOnClickListener(buttonClickListener);
    }

    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){

                case R.id.btn_wordback:
                    getActivity().finish();
                    break;
                case R.id.btn_wordadd:
                    String mWord = word.getText().toString();
                    String mMean1 = mean1.getText().toString();
                    String mMean2 = mean2.getText().toString();
                    String mMean3 = mean3.getText().toString();
                    String mMean4 = mean4.getText().toString();
                    String mMean5 = mean5.getText().toString();
                    if(checkinputs(mWord,mMean1)) {

                        mFirebaseMethods.createNewWord(mWord, mMean1, checkallmeans(mMean2),
                                checkallmeans(mMean3), checkallmeans(mMean4), checkallmeans(mMean5));
                        Toast.makeText(mContext,getString(R.string.addwordfrag_info),Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    }
                    else {
                        Toast.makeText(mContext,getString(R.string.addwordfrag_warninfo),Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    private String checkallmeans(String m2) {
        if(m2.equals(""))
            m2 = null;
        return m2;
    }

    private boolean checkinputs(String mWord, String mMean1) {
        if(mWord.equals("") || mMean1.equals(""))
            return false;

        return true;
    }

    private void setupFirebaseAuth(){
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

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

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                wordCount = mFirebaseMethods.getWordCount(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
