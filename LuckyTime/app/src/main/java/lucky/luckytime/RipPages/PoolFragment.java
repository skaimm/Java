package lucky.luckytime.RipPages;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
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
import lucky.luckytime.LoginRegister.LoginActivity;
import lucky.luckytime.Model.Tahmin;
import lucky.luckytime.Model.TahminCevap;
import lucky.luckytime.R;
import lucky.luckytime.Utils.FirebaseMethods;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class PoolFragment extends Fragment {

    private Context mContext;
    private Button c1,c2,c3;
    private TextView tah,hav,havprize,havprice;
    private ProgressBar mProgressBar;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseMethods mFirebaseMethods;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private TahminCevap mTahminCevap;
    private String userID;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pool,container,false);
        mContext = getActivity();

        c1 = (Button) view.findViewById(R.id.btn_cevap1);
        c2 = (Button) view.findViewById(R.id.btn_cevap2);
        c3 = (Button) view.findViewById(R.id.btn_cevap3);
        tah = (TextView) view.findViewById(R.id.tv_tahmin);
        hav = (TextView) view.findViewById(R.id.tv_pool);
        havprice = (TextView) view.findViewById(R.id.tv_poolprice);
        havprize = (TextView) view.findViewById(R.id.tv_poolprize);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        mFirebaseMethods = new FirebaseMethods(mContext);

        setupFirebaseAuth();
        return view;
    }
    private void setPoolWidgets() throws Exception{

        Tahmin tahmin = mTahminCevap.getTahmin();
        String cevap = mTahminCevap.getUsercevap();

        if (tahmin.isSituation_yayin()) {
            tah.setText(tahmin.getTahmin());
            hav.setVisibility(View.VISIBLE);
            havprize.setVisibility(View.VISIBLE);
            havprice.setVisibility(View.VISIBLE);
            c1.setVisibility(View.VISIBLE);
            c2.setVisibility(View.VISIBLE);
            c3.setVisibility(View.VISIBLE);
            c1.setBackgroundResource(R.drawable.background_signup);
            c2.setBackgroundResource(R.drawable.background_signup);
            c3.setBackgroundResource(R.drawable.background_signup);
            c1.setText(tahmin.getTahmin_cevap1());
            c2.setText(tahmin.getTahmin_cevap2());
            c3.setText(tahmin.getTahmin_cevap3());
            c1.setClickable(true);
            c2.setClickable(true);
            c3.setClickable(true);

            if (tahmin.isSituation_oynanma()) {
                String string = tahmin.getTahmin_odulu();
                if (string.equals(mContext.getString(R.string.field_altin))) {
                    string = mContext.getString(R.string.goldinfo);
                } else if (string.equals(mContext.getString(R.string.field_gumus))) {
                    string = mContext.getString(R.string.silverinfo);
                } else {
                    string = mContext.getString(R.string.bronzinfo);
                }
                hav.setText(tahmin.getKapanma_date()
                        + " " + tahmin.getKapanma_saat());
                havprize.setText(string);
                havprice.setText("" + tahmin.getTahmin_ucreti());

                if (!cevap.isEmpty()) {
                    c1.setClickable(false);
                    c2.setClickable(false);
                    c3.setClickable(false);
                    if (cevap.equals(c1.getText().toString())) {
                        c1.setBackgroundResource(R.drawable.btnwait);
                    }
                    if (cevap.equals(c2.getText().toString())) {
                        c2.setBackgroundResource(R.drawable.btnwait);
                    }
                    if (cevap.equals(c3.getText().toString())) {
                        c3.setBackgroundResource(R.drawable.btnwait);
                    }
                } else {
                    if (mTahminCevap.getUserRip() > tahmin.getTahmin_ucreti()) {
                        final TahminCevap kopya = mTahminCevap;
                        c1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                buttonclick(kopya.getTahmin().getTahmin_cevap1(), kopya);
                            }
                        });
                        c2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                buttonclick(kopya.getTahmin().getTahmin_cevap2(), kopya);
                            }
                        });
                        c3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                buttonclick(kopya.getTahmin().getTahmin_cevap3(), kopya);
                            }
                        });
                    } else {
                        c1.setClickable(false);
                        c2.setClickable(false);
                        c3.setClickable(false);
                    }
                }
            } else {
                hav.setText(mContext.getString(R.string.tahminclosed));
                havprize.setVisibility(View.INVISIBLE);
                havprice.setVisibility(View.INVISIBLE);
                c1.setClickable(false);
                c2.setClickable(false);
                c3.setClickable(false);
                if (tahmin.getTahmin_dogrucevap().equals("")) {
                    if (cevap.equals(c1.getText().toString())) {
                        c1.setBackgroundResource(R.drawable.btnwait);
                    }
                    if (cevap.equals(c2.getText().toString())) {
                        c2.setBackgroundResource(R.drawable.btnwait);
                    }
                    if (cevap.equals(c3.getText().toString())) {
                        c3.setBackgroundResource(R.drawable.btnwait);
                    }
                }else {
                    if (!cevap.isEmpty()) {
                        c1.setBackgroundResource(R.drawable.background_signup);
                        c2.setBackgroundResource(R.drawable.background_signup);
                        c3.setBackgroundResource(R.drawable.background_signup);
                        if (cevap.equals(tahmin.getTahmin_dogrucevap())) {
                            hav.setText(mContext.getString(R.string.buttonwon));
                            if (cevap.equals(c1.getText().toString())) {
                                c1.setBackgroundResource(R.drawable.btnwon);
                            }
                            if (cevap.equals(c2.getText().toString())) {
                                c2.setBackgroundResource(R.drawable.btnwon);
                            }
                            if (cevap.equals(c3.getText().toString())) {
                                c3.setBackgroundResource(R.drawable.btnwon);
                            }
                        }else{
                            hav.setText(mContext.getString(R.string.lostinfo));
                            if (cevap.equals(c1.getText().toString())) {
                                c1.setBackgroundResource(R.drawable.btnlost);
                            }
                            if (cevap.equals(c2.getText().toString())) {
                                c2.setBackgroundResource(R.drawable.btnlost);
                            }
                            if (cevap.equals(c3.getText().toString())) {
                                c3.setBackgroundResource(R.drawable.btnlost);
                            }
                        }
                    }else{
                        hav.setText(mContext.getString(R.string.tahminfinished));
                        if (tahmin.getTahmin_dogrucevap().equals(c1.getText().toString())) {
                            c1.setBackgroundResource(R.drawable.btnwon);
                        }
                        if (tahmin.getTahmin_dogrucevap().equals(c2.getText().toString())) {
                            c2.setBackgroundResource(R.drawable.btnwon);
                        }
                        if (tahmin.getTahmin_dogrucevap().equals(c3.getText().toString())) {
                            c3.setBackgroundResource(R.drawable.btnwon);
                        }
                    }
                }
            }
        } else {
            tah.setText(mContext.getString(R.string.tahminwait));
            hav.setVisibility(View.INVISIBLE);
            havprize.setVisibility(View.INVISIBLE);
            havprice.setVisibility(View.INVISIBLE);
            c1.setVisibility(View.INVISIBLE);
            c2.setVisibility(View.INVISIBLE);
            c3.setVisibility(View.INVISIBLE);
        }
    }

    private void getResult(DataSnapshot tahmin) throws Exception{
        if(mAuth.getCurrentUser().getUid().equals(mContext.getString(R.string.admin))){
            if(!tahmin.getValue(Tahmin.class).getTahmin_dogrucevap().isEmpty()){
                if(tahmin.getValue(Tahmin.class).isSituation_cevap()){
                    final String dogrucevap = tahmin.getValue(Tahmin.class).getTahmin_dogrucevap();
                    final String odul = tahmin.getValue(Tahmin.class).getTahmin_odulu();
                    Query kazanan = FirebaseDatabase.getInstance().getReference().child(mContext.getString(R.string.dbname_tahminoyna));
                    kazanan.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            long bilen=0;
                            for(DataSnapshot users : dataSnapshot.getChildren()){
                                if(users.getValue().equals(dogrucevap)){
                                    bilen++;
                                    mFirebaseMethods.givePrizeBoxForTahmin(odul,users.getKey());
                                }
                                mFirebaseMethods.removeUserFromTahminList(users.getKey());
                            }
                            if(bilen!=0)
                                mFirebaseMethods.tahminbilensayisi(bilen);
                            mFirebaseMethods.changeCevapOnay();
                            mFirebaseMethods.removefromliveTahmin(false);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        }
    }

    private void buttonclick(String mcevap, final TahminCevap mtahmin) {
        final Tahmin tahmin = mtahmin.getTahmin();
        final String cevap = mcevap;
        Calendar calendar ;
        calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone(mContext.getString(R.string.timezone)));
        SimpleDateFormat dateFormat = new SimpleDateFormat(mContext.getString(R.string.timeformat));
        String currentdate = dateFormat.format(calendar.getTime());
        SimpleDateFormat clockFormat = new SimpleDateFormat(mContext.getString(R.string.timehourformat));
        String currentclock = clockFormat.format(calendar.getTime());
        String closedate = tahmin.getKapanma_date();
        String closeclock = tahmin.getKapanma_saat();
        try {
            Date closetime = dateFormat.parse(closedate);
            Date clocktime = clockFormat.parse(closeclock);
            Date date = dateFormat.parse(currentdate);
            Date time = clockFormat.parse(currentclock);
            if(closetime.compareTo(date) == 0){
                if(clocktime.compareTo(time) <= 0){
                    mFirebaseMethods.changeSituationofPlaying();
                }else{
                    long newRip = mtahmin.getUserRip();
                    if(newRip-tahmin.getTahmin_ucreti()>=0){
                        final Dialog customDialog = new Dialog(mContext);
                        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        customDialog.setContentView(R.layout.customdialog);
                        TextView question = (TextView) customDialog.findViewById(R.id.tv_dialogque);
                        Button yes = (Button) customDialog.findViewById(R.id.btn_diayes);

                        question.setText(mContext.getString(R.string.yourtahmin)+ " " + cevap + "\n" +
                                tahmin.getTahmin_ucreti() + " " + mContext.getString(R.string.askpayrip));
                        yes.setText(mContext.getString(R.string.yes));
                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                long newRip = mtahmin.getUserRip();
                                long toplam = tahmin.getSonuc_toplam();
                                toplam++;
                                newRip -= tahmin.getTahmin_ucreti();
                                mFirebaseMethods.changeRipCount(newRip);
                                mFirebaseMethods.predictionUsers(cevap,toplam);
                                customDialog.cancel();
                            }
                        });
                        customDialog.show();
                    }else{
                        Toast.makeText(mContext,mContext.getString(R.string.buttonnorip),Toast.LENGTH_SHORT).show();
                    }
                }

            }else if(closetime.compareTo(date) < 0){
                mFirebaseMethods.changeSituationofPlaying();
            }else{
                long newRip = mtahmin.getUserRip();
                if(newRip-tahmin.getTahmin_ucreti()>=0){
                    final Dialog customDialog = new Dialog(mContext);
                    customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    customDialog.setContentView(R.layout.customdialog);
                    TextView question = (TextView) customDialog.findViewById(R.id.tv_dialogque);
                    Button yes = (Button) customDialog.findViewById(R.id.btn_diayes);

                    question.setText(mContext.getString(R.string.yourtahmin)+ " " + cevap + "\n" +
                            tahmin.getTahmin_ucreti() + " " + mContext.getString(R.string.askpayrip));
                    yes.setText(mContext.getString(R.string.yes));
                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            long newRip = mtahmin.getUserRip();
                            long toplam = tahmin.getSonuc_toplam();
                            toplam++;
                            newRip -= tahmin.getTahmin_ucreti();
                            mFirebaseMethods.changeRipCount(newRip);
                            mFirebaseMethods.predictionUsers(cevap,toplam);
                            customDialog.cancel();
                        }
                    });
                    customDialog.show();
                }else{
                    Toast.makeText(mContext,mContext.getString(R.string.buttonnorip),Toast.LENGTH_SHORT).show();
                }
            }
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
    }

    private void readData(final FirebaseCallback firebaseCallback){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                TahminCevap thmncvp = mFirebaseMethods.getTahminInfo(dataSnapshot);
                try {
                    firebaseCallback.userInfo(thmncvp);
                    if(mAuth.getCurrentUser().getUid().equals(mContext.getString(R.string.admin))){
                        getResult(dataSnapshot.child(mContext.getString(R.string.dbname_tahmin)));
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
            public void userInfo(TahminCevap tahminCevap) throws Exception {
                mTahminCevap = tahminCevap;
                setPoolWidgets();
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
        void userInfo(TahminCevap tahminCevap) throws Exception;
    }
}
