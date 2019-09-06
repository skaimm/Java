package lucky.luckytime;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.firebase.internal.FirebaseAppHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lucky.luckytime.LoginRegister.LoginActivity;
import lucky.luckytime.Model.Competition;
import lucky.luckytime.Model.Prize;
import lucky.luckytime.Model.RafflePoint;
import lucky.luckytime.Model.User;
import lucky.luckytime.Utils.Comparator;
import lucky.luckytime.Utils.ComparatorPrice;
import lucky.luckytime.Utils.FirebaseHelper;
import lucky.luckytime.Utils.FirebaseMethods;
import lucky.luckytime.Utils.ListViewPrizeHelper;
import lucky.luckytime.Utils.MyFirebaseInstanceIDService;
import lucky.luckytime.Utils.UniversalImageLoader;

public class PrizeFragment extends Fragment {

    private Context mContext;
    private ListView lvPrize;
    private ProgressBar mProgressBar;

    private static final String LIST_STATE = "listState";
    private Parcelable mListState = null;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseMethods mFirebaseMethods;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private Competition competition;
    private String userID;
    private FirebaseHelper helper;
    private ListViewPrizeHelper prizeHelper;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.center_prize, container, false);
        lvPrize =(ListView) view.findViewById(R.id.lv_prizelist);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        competition = new Competition();

        mContext = getContext();
        mFirebaseMethods = new FirebaseMethods(mContext);

       /* myRef = FirebaseDatabase.getInstance().getReference();
        helper = new FirebaseHelper(myRef, mContext, lvPrize,mProgressBar);*/

        setupFirebaseAuth();
       // setupListView();
        return view;
    }

    private void  setupList(){
        Collections.sort(competition.getPrizes(),new ComparatorPrice());
        if(lvPrize.getItemAtPosition(0)!=null){
            prizeHelper.updateCompetition(competition);
        }else{
            prizeHelper = new ListViewPrizeHelper(getActivity(),competition);
            lvPrize.setAdapter(prizeHelper);
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }
   /* private void setupListView(){

        Query query = FirebaseDatabase.getInstance().getReference();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Competition com= mFirebaseMethods.getCompetitionInfo(dataSnapshot);
                Collections.sort(com.getPrizes(),new ComparatorPrice());
                if(lvPrize.getItemAtPosition(0)!=null){
                }else{
                    prizeHelper = new ListViewPrizeHelper(getActivity(),com);
                    lvPrize.setAdapter(prizeHelper);
                    mProgressBar.setVisibility(View.INVISIBLE);
                }
                prizeHelper.updateCompetition(com);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/
    private void readData(final FirebaseCallback firebaseCallback){

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Competition comp = mFirebaseMethods.getCompetitionInfo(dataSnapshot);
                Collections.sort(comp.getPrizes(),new ComparatorPrice());
                try {
                    firebaseCallback.userInfo(comp);
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
        if(mAuth.getCurrentUser() != null)
            userID = mAuth.getCurrentUser().getUid();
        else{
            Intent intent = new Intent(mContext,LoginActivity.class);
            startActivity(intent);
        }

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user!= null)
                {
                }else{
                    Intent intent = new Intent(mContext,LoginActivity.class);
                    startActivity(intent);
                }
            }
        };

        readData(new FirebaseCallback() {
            @Override
            public void userInfo(Competition compe) throws Exception {
                competition = compe;
                setupList();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mAuthListener!= null)
        {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private interface FirebaseCallback {
        void userInfo(Competition competition) throws Exception;
    }
}
