package lucky.luckytime.RipPages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.TimeZone;

import lucky.luckytime.LoginRegister.LoginActivity;
import lucky.luckytime.Model.Duello;
import lucky.luckytime.Model.DuelloOdul;
import lucky.luckytime.Model.Odul;
import lucky.luckytime.Model.Ranking;
import lucky.luckytime.R;
import lucky.luckytime.Utils.Comparator;
import lucky.luckytime.Utils.FirebaseMethods;

public class DuelloFragment extends Fragment {

    private Context mContext;
    private TextView s1,s2,s3,s4,s5,s6,s1o,s2o,s3o,s4o,s5o,s6o,durumsira,durumad,dunwonname,dunsensira,dunsenad,durumtl;
    private LinearLayout lastsituation;
    private ProgressBar mProgressBar;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseMethods mFirebaseMethods;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private DuelloOdul mDuelloOdul;
    private ArrayList<Duello> ranking;
    private String userID;
    private double money;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_duello,container,false);

        s1 = (TextView) view.findViewById(R.id.tv_sira1);
        s2 = (TextView) view.findViewById(R.id.tv_sira2);
        s3 = (TextView) view.findViewById(R.id.tv_sira3);
        s4 = (TextView) view.findViewById(R.id.tv_sira4);
        s5 = (TextView) view.findViewById(R.id.tv_sira5);
        s6 = (TextView) view.findViewById(R.id.tv_sira6);
        s1o = (TextView) view.findViewById(R.id.tv_siraodul1);
        s2o = (TextView) view.findViewById(R.id.tv_siraodul2);
        s3o = (TextView) view.findViewById(R.id.tv_siraodul3);
        s4o = (TextView) view.findViewById(R.id.tv_siraodul4);
        s5o = (TextView) view.findViewById(R.id.tv_siraodul5);
        s6o = (TextView) view.findViewById(R.id.tv_siraodul6);
        durumtl = (TextView) view.findViewById(R.id.tv_durumtl);

        durumsira = (TextView) view.findViewById(R.id.tv_durumsira);
        durumad = (TextView) view.findViewById(R.id.tv_durumwatch);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        mContext = getActivity();

        dunwonname = (TextView) view.findViewById(R.id.tv_kazananUsername);
        dunsensira = (TextView) view.findViewById(R.id.tv_sensira);
        dunsenad = (TextView) view.findViewById(R.id.tv_senwatch);
        lastsituation = (LinearLayout) view.findViewById(R.id.linlast);
        mFirebaseMethods = new FirebaseMethods(mContext);

        setupFirebaseAuth();
        return view;
    }

    public void setOdulDurumInfo() throws Exception{
        Odul mOdul = mDuelloOdul.getOdul();
        Duello mDuello = mDuelloOdul.getDuello();

        long sira1 = mOdul.getSira1();
        long sira2 = mOdul.getSira2();
        long sira3 = mOdul.getSira3();
        long sira4 = mOdul.getSira4();
        long sira5 = mOdul.getSira5();
        long sira6 = mOdul.getSira6();
        String odul2 = mOdul.getSira2odul();
        String odul3 = mOdul.getSira3odul();
        long tane1 = mOdul.getSira1tane();
        long tane2 = mOdul.getSira2tane();
        long tane3 = mOdul.getSira3tane();
        long odul4 = mOdul.getSira4odul();
        long odul5 = mOdul.getSira5odul();
        long odul6 = mOdul.getSira6odul();

        if(odul2.equals(mContext.getString(R.string.field_altin))){
            if(tane2==1)
                s2o.setText(mContext.getString(R.string.goldinfo));
            else
                s2o.setText(tane2 + " " + mContext.getString(R.string.goldinfo));
        }
        if(odul2.equals(mContext.getString(R.string.field_gumus))){
            if(tane2==1)
                s2o.setText(mContext.getString(R.string.silverinfo));
            else
                s2o.setText(tane2 + " " + mContext.getString(R.string.silverinfo));
        }
        if(odul2.equals(mContext.getString(R.string.field_bronz))){
            if(tane2==1)
                s2o.setText(mContext.getString(R.string.bronzinfo));
            else
                s2o.setText(tane2 + " " + mContext.getString(R.string.bronzinfo));
        }
        if(odul3.equals(mContext.getString(R.string.field_altin))){
            if(tane3==1)
                s3o.setText(mContext.getString(R.string.goldinfo));
            else
                s3o.setText(tane3 + " " + mContext.getString(R.string.goldinfo));
        }
        if(odul3.equals(mContext.getString(R.string.field_gumus))){
            if(tane3==1)
                s3o.setText(mContext.getString(R.string.silverinfo));
            else
                s3o.setText(tane3 + " " + mContext.getString(R.string.silverinfo));
        }
        if(odul3.equals(mContext.getString(R.string.field_bronz))){
            if(tane3==1)
                s3o.setText(mContext.getString(R.string.bronzinfo));
            else
                s3o.setText(tane3 + " " + mContext.getString(R.string.bronzinfo));
        }

        s4o.setText(odul4 + " " + mContext.getString(R.string.rip));
        s5o.setText(odul5 + " " + mContext.getString(R.string.rip));
        s6o.setText(odul6 + " " + mContext.getString(R.string.rip));

        long toplam = sira1+sira2+sira3+sira4+sira5+sira6;
        if(sira1 == 1){
            s1.setText("1");
        }
        else if(sira1==0) {
            s1.setText("-");
        }
        else {
            s1.setText("1 - " + sira1);
        }
        s2.setText((1+sira1 +" - " + (sira1+sira2)));
        s3.setText(1+sira1+sira2 + " - " +(sira1+sira2+sira3));
        s4.setText(1+sira1+sira2+sira3 +" - " +(sira1+sira2+sira3+sira4));
        s5.setText(1+sira1+sira2+sira3+sira4 +" - " +(sira1+sira2+sira3+sira4+sira5));
        s6.setText(1+sira1+sira2+sira3+sira4+sira5 +" - " +(sira1+sira2+sira3+sira4+sira5+sira6));

        durumad.setText(String.valueOf(mDuello.getIzleme()));
        if(mDuello.getSira()!=0)
            durumsira.setText(String.valueOf(mDuello.getSira()));
        else
            durumsira.setText(" - ");
        dunsenad.setText(String.valueOf(mDuello.getIzlemedun()));
        if(mDuello.getSira()!=0)
            dunsensira.setText(String.valueOf(mDuello.getSiradun()));
        else
            dunsensira.setText(" - ");
        if(mOdul.getWonname().equals(""))
            dunwonname.setText(mContext.getString(R.string.buttonfinishedwait));
        else
            dunwonname.setText(mOdul.getWonname());

        durumtl.setText(new DecimalFormat("###,###.#####").format(mOdul.getSira1odul()));

    }
    private void getRanking() throws Exception{/*
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            if(ds.getKey().equals(mContext.getString(R.string.dbname_odul))){
                */
                for(int i=0;i<ranking.size();i++){
                    if(ranking.get(i).getUser_id().equals(userID)){
                        if(ranking.get(i).getIzleme()>20){
                            mFirebaseMethods.changeDailyRankinginDay(ranking.get(i).getUser_id(),(i+1));
                        }else{
                            mFirebaseMethods.changeDailyRankinginDay(ranking.get(i).getUser_id(),0);
                        }
                    }
                }
                /*
            }
        }*/
    }
    private void readData(final FirebaseCallback firebaseCallback){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DuelloOdul duel = mFirebaseMethods.getOdulInfo(dataSnapshot);
                ArrayList<Duello> rank = mFirebaseMethods.getDailyRanking(dataSnapshot);
                mFirebaseMethods.getDailyRankingMOney(dataSnapshot.child(mContext.getString(R.string.dbname_duello)));
                Collections.sort(rank,new Comparator());
                try {
                    firebaseCallback.userInfo(duel,rank);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    getRanking();
                    if(mAuth.getCurrentUser().getUid().equals(mContext.getString(R.string.admin))){
                        mFirebaseMethods.giveThePrizeForRankingifAdmin(mDuelloOdul,dataSnapshot,ranking);
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
                    public void userInfo(DuelloOdul duelloOdul,ArrayList<Duello> ranki) throws Exception {
                        mDuelloOdul = duelloOdul;
                        ranking = ranki;
                        setOdulDurumInfo();
                        mProgressBar.setVisibility(View.INVISIBLE);
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
        void userInfo(DuelloOdul duelloOdul,ArrayList<Duello> rank) throws Exception;
    }
}
