package lucky.luckytime;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.UniversalTimeScale;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import lucky.luckytime.LoginRegister.LoginActivity;
import lucky.luckytime.Model.User;
import lucky.luckytime.RipPages.DuelloFragment;
import lucky.luckytime.RipPages.RipFragment;
import lucky.luckytime.Utils.ConnectionDetector;
import lucky.luckytime.Utils.FirebaseMethods;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.api.AdUnit;

import java.text.DecimalFormat;

public class GainPrize extends AppCompatActivity implements IUnityAdsListener,RewardedVideoAdListener {

    private static final int ACTIVITY_NUM =1;
    private Context mContext = GainPrize.this;
    private Button watchad;
    private ProgressBar mProgressBar;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseMethods mFirebaseMethods;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private String userID;
    private User mUser;
    private long mDaily;
    private RewardedVideoAd mRewardedVideoAd;
    private ProgressDialog mProgressDialog;
    private ConnectionDetector connectionDetector;
    private Fragment selectedFragment = new PrizeFragment();

    private TextView usermoney;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_gain_prize);


        watchad = (Button) findViewById(R.id.btn_watchad);
        usermoney = (TextView) findViewById(R.id.tv_rip);
        mFirebaseMethods = new FirebaseMethods(mContext);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mProgressDialog = new ProgressDialog(this);


        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container,new DuelloFragment());
        ft.commit();

        BottomNavigationViewEx bottomNavigationView = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        bottomNavigationView.enableShiftingMode(false);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        mProgressBar.setVisibility(View.INVISIBLE);

        setupFirebaseAuth();
        control();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.ic_profile:
                    selectedFragment = new ProfileFragment();
                    break;
                case R.id.ic_gain:
                    selectedFragment = new RipFragment();
                    break;
                case R.id.ic_store:
                    selectedFragment = new PrizeFragment();
                    break;
                case R.id.ic_kazan:
                    selectedFragment = new DuelloFragment();
                    break;
                case R.id.ic_info:
                    selectedFragment = new OynamaFragment();
                    break;
            }

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container,selectedFragment);
            ft.commit();

            return true;
        }
    };


    private void loadRewardedVideoAd() {
        watchad.setText(mContext.getString(R.string.buttoninitial));
        UnityAds.initialize(this,mContext.getString(R.string.UNITYID), this);
        mRewardedVideoAd.loadAd(mContext.getString(R.string.ADMOBREWARDEDKEY),
                new AdRequest.Builder().build());
    }

    private void changeRipDailyAd() {
        Runnable backGroundRunnable = new Runnable() {
            public void run(){
                long newDaily = mDaily;
                newDaily++;
                long newrip = mUser.getRip();
                newrip++;
                long newwad = mUser.getWatch_ad();
                newwad++;
                mFirebaseMethods.changeRipCount(newrip);
                mFirebaseMethods.changeWatchAd(newwad);
                mFirebaseMethods.changeDailyWatchAd(userID,newDaily);
                mFirebaseMethods.changeCanWatch(true);
            }};
        Thread sampleThread = new Thread(backGroundRunnable);
        sampleThread.start();
    }

    private void control(){
        connectionDetector = new ConnectionDetector(mContext);

        if(connectionDetector.isConnected()) {
            mProgressDialog.dismiss();
        }
        else{
            Toast.makeText(mContext,getString(R.string.controlconnection ),Toast.LENGTH_SHORT).show();
            mProgressDialog.show();
            mProgressDialog.setMessage(mContext.getString(R.string.controlconnection));
        }
    }
    private void setWidgets(){

        usermoney.setText(new DecimalFormat("###,###,###").format(mUser.getRip()));

    }

    private void readData(final FirebaseCallback firebaseCallback){

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User xx = mFirebaseMethods.getUsersInfo(dataSnapshot);
                Long dail = (long) dataSnapshot.child(mContext.getString(R.string.dbname_duello)).child(userID).child(mContext.getString(R.string.field_izleme)).getValue();
                firebaseCallback.userInfo(xx,dail);
                try {
                    if(mAuth.getCurrentUser().getUid().equals(mContext.getString(R.string.admin))){
                        mFirebaseMethods.giveTheRaffleGift(dataSnapshot);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            } 
        };

        mFirebaseDatabase.getReference().addValueEventListener(valueEventListener);
    }

    private void setupFirebaseAuth(){
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        if(mAuth.getCurrentUser() != null){
            userID = mAuth.getCurrentUser().getUid();
            MobileAds.initialize(getApplicationContext(), mContext.getString(R.string.ADMOBKEY));
            UnityAds.initialize(this,mContext.getString(R.string.UNITYID), this);
            mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
            mRewardedVideoAd.setRewardedVideoAdListener(this);
            loadRewardedVideoAd();
            mProgressDialog.show();
        }
        else{

        }
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user!= null && user.isEmailVerified())
                {
                }else{
                    Intent intent = new Intent(mContext,LoginActivity.class);
                    startActivity(intent);
                }

            }
        };


        readData(new FirebaseCallback() {
            @Override
            public void userInfo(User user, long daily) {
                mUser = user;
                mDaily = daily;
                setWidgets();
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });

        watchad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(UnityAds.isReady()){
                    UnityAds.show(GainPrize.this);
                }else if(mRewardedVideoAd.isLoaded()){
                    mRewardedVideoAd.show();
                }else{
                    loadRewardedVideoAd();
                }
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


    @Override
    protected void onPause() {
        if(mUser!=null)
            mRewardedVideoAd.pause(this);
        super.onPause();
    }
    @Override
    protected void onResume() {
        if(mUser!=null){
            mRewardedVideoAd.resume(this);
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mRewardedVideoAd.destroy(this);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
/*
    public void after7second(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(UnityAds.isReady()){
                    UnityAds.show(GainPrize.this);
                }else if(mRewardedVideoAd.isLoaded()){
                    mRewardedVideoAd.show();
                }else{
                    loadRewardedVideoAd();
                }
            }
        },7000);
    }*/
    @Override
    public void onUnityAdsReady(String s) {
        watchad.setText(mContext.getString(R.string.buttonizle) );
    }

    @Override
    public void onUnityAdsStart(String s) {

    }

    @Override
    public void onUnityAdsFinish(String s, UnityAds.FinishState finishState) {
        if(finishState!= UnityAds.FinishState.SKIPPED){
            changeRipDailyAd();
        }
    }

    @Override
    public void onUnityAdsError(UnityAds.UnityAdsError unityAdsError, String s) {

    }

    @Override
    public void onRewardedVideoAdLoaded() {
        watchad.setText(mContext.getString(R.string.buttonizle));
    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
        loadRewardedVideoAd();
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {
        changeRipDailyAd();
    }

    private interface FirebaseCallback {
        void userInfo(User user, long daily);
    }

}
