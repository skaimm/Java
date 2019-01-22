package com.example.ultraslan.wordmemorization.Fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ultraslan.wordmemorization.Activity.HelpActivity;
import com.example.ultraslan.wordmemorization.Activity.LoginActivity;
import com.example.ultraslan.wordmemorization.Activity.MyWordActivity;
import com.example.ultraslan.wordmemorization.Activity.StoreActivity;
import com.example.ultraslan.wordmemorization.Activity.StudyActivity;
import com.example.ultraslan.wordmemorization.Activity.TalepActivity;
import com.example.ultraslan.wordmemorization.Model.StoreProducts;
import com.example.ultraslan.wordmemorization.Model.Words;
import com.example.ultraslan.wordmemorization.R;
import com.example.ultraslan.wordmemorization.Utils.FirebaseMethods;
import com.example.ultraslan.wordmemorization.Utils.ListViewStoreCustom;
import com.example.ultraslan.wordmemorization.Utils.NotificationHelper;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class StartFragment extends Fragment {


    private Button word,study,store,help,talep;
    private ImageView appname;
    private TextView created;

    private Context mContext;
    private AdView mAdView;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseMethods mFirebaseMethods;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private String userID;


    private ArrayList<Words> allword;
    private ArrayList<StoreProducts> allproducts;

    //private static final String APP_ID = "ca-app-pub-2648309604179255~9239190229";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start,container,false);

        word = (Button) view.findViewById(R.id.button_mywords);
        study = (Button) view.findViewById(R.id.button_study);
        store = (Button) view.findViewById(R.id.button_store);
        help = (Button) view.findViewById(R.id.button_help);
        talep = (Button) view.findViewById(R.id.button_talep);
        mContext = getActivity();
        mFirebaseMethods = new FirebaseMethods(mContext);
        allproducts = new ArrayList<>();
        allword = new ArrayList<>();

        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        notification();


        define();
        setupFirebaseAuth();

        return view;
    }
    private void notification(){

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);

        Intent intent = new Intent(getActivity().getApplicationContext(),NotificationHelper.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext,100,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
    }

    private void define(){
        word.setOnClickListener(buttonClickListener);
        study.setOnClickListener(buttonClickListener);
        store.setOnClickListener(buttonClickListener);
        help.setOnClickListener(buttonClickListener);
        talep.setOnClickListener(buttonClickListener);
    }

    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){

                case R.id.button_mywords:
                    Intent intent4 = new Intent(mContext, MyWordActivity.class);
                    loadTheWordsFromDB(intent4);
                    break;
                case R.id.button_study:
                    Intent intent2 = new Intent(mContext, StudyActivity.class);
                    loadTheWordsFromDB(intent2);
                    break;
                case R.id.button_store:
                    Intent intent1 = new Intent(mContext, StoreActivity.class);
                    loadTheProductsFromDB(intent1);
                    break;
                case R.id.button_help:
                    Intent intent3 = new Intent(mContext, HelpActivity.class);
                    mContext.startActivity(intent3);
                    break;
                case R.id.button_talep:
                    Intent intent6 = new Intent(mContext, TalepActivity.class);
                    mContext.startActivity(intent6);
                    break;
            }
        }
    };

    private void checkCurrentUser(FirebaseUser user){
        if(user == null)
        {
            Intent intent = new Intent(mContext,LoginActivity.class);
            startActivity(intent);
        }
    }

    private void loadTheWordsFromDB(final Intent intent){
        final ArrayList<Words> wordslist = new ArrayList<>();
        final Query query = myRef.child(mContext.getString(R.string.dbname_user_words)).child(userID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                wordslist.clear();
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    wordslist.add(singleSnapshot.getValue(Words.class));
                }
                transferToPages(intent,wordslist);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadTheProductsFromDB(final Intent intent){
        final ArrayList<StoreProducts> tempProduct = new ArrayList<>();
        final Query query = myRef.child(getActivity().getString(R.string.dbname_store));
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    tempProduct.add(singleSnapshot.getValue(StoreProducts.class));
                }
                transferToPages(intent,tempProduct);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void transferToPages(Intent intent,ArrayList wordsbox) {

        Bundle bundle = new Bundle();
        bundle.putSerializable("listbox",wordsbox);
        intent.putExtras(bundle);
        startActivity(intent);
    }



    //--------------------------------------------------------------------------------------//
    //------------------------------------ FireBase ----------------------------------------//
    //--------------------------------------------------------------------------------------//
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
        checkCurrentUser(mAuth.getCurrentUser());
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
