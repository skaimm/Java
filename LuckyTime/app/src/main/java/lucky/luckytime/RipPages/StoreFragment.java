package lucky.luckytime.RipPages;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
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

import lucky.luckytime.LoginRegister.LoginActivity;
import lucky.luckytime.Model.Box;
import lucky.luckytime.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import lucky.luckytime.R;
import lucky.luckytime.Utils.FirebaseMethods;

public class StoreFragment extends Fragment {

    private Context mContext;
    private Button altin,gumus,bronz;
    private ProgressBar mProgressBar;


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseMethods mFirebaseMethods;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private String userID;
    private User mUser;
    private ArrayList<Box> boxes;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store,container,false);
        altin = (Button) view.findViewById(R.id.btn_luckaltin);
        gumus = (Button) view.findViewById(R.id.btn_luckgumus);
        bronz = (Button) view.findViewById(R.id.btn_luckbronz);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        mContext=getActivity();
        mFirebaseMethods = new FirebaseMethods(mContext);

        setupFirebaseAuth();
        deneme();
        return view;
    }
    /*private void  getBoxes(){
        for(int i=0;i<boxes.size();i++){
            if(boxes.get(i).getType().equals(mContext.getString(R.string.field_altin))){
                final int finalI = i;
                altin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sansdeneme(boxes.get(finalI),mUser.getAltinbox());
                    }
                });
            }
            if(boxes.get(i).getType().equals(mContext.getString(R.string.field_gumus))){
                final int finalI = i;
                gumus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sansdeneme(boxes.get(finalI),mUser.getGumusbox());
                    }
                });

            }
            if(boxes.get(i).getType().equals(mContext.getString(R.string.field_bronz))){
                final int finalI = i;
                bronz.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sansdeneme(boxes.get(finalI),mUser.getBronzbox());
                    }
                });
            }
        }
    }*/

    private void deneme(){
        mProgressBar.setVisibility(View.INVISIBLE);
        altin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog customDialog = new Dialog(mContext);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.customdialog);
                TextView question = (TextView) customDialog.findViewById(R.id.tv_dialogque);
                final Button yes = (Button) customDialog.findViewById(R.id.btn_diayes);

                question.setText(mContext.getString(R.string.sansaltin) +" "+mContext.getString(R.string.wanttry));
                yes.setText(mContext.getString(R.string.yes));

                Query query = FirebaseDatabase.getInstance().getReference();
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                        final User userinfo = mFirebaseMethods.getUsersInfo(dataSnapshot);
                        if( userinfo.getRip()>1){
                            yes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ArrayList<Box> boxin = mFirebaseMethods.getBoxesInfo(dataSnapshot);
                                    Box box = null;
                                    for(int i=0;i<boxin.size();i++){
                                        if(boxin.get(i).getType().equals(mContext.getString(R.string.field_altin))){
                                            box = boxin.get(i);
                                            break;
                                        }
                                    }
                                    long newRip = userinfo.getRip();
                                    newRip--;
                                    mFirebaseMethods.changeRipCount(newRip);
                                    long winrank = box.getSans_kazan();
                                    long userrank = box.getSans_sira();
                                    long newBox = userinfo.getAltinbox();
                                    userrank++;
                                    if(userrank==winrank){
                                        userrank=0;
                                        newBox++;
                                        altin.setText(mContext.getString(R.string.buttonwon));
                                        altin.setBackgroundResource(R.drawable.btnwon);
                                        mFirebaseMethods.changeBoxCount(mContext.getString(R.string.field_altin), newBox);
                                    }else {
                                        altin.setText(mContext.getString(R.string.buttontryagain));
                                        altin.setBackgroundResource(R.drawable.btnlost);
                                    }
                                    mFirebaseMethods.changeLuck(mContext.getString(R.string.field_altin),userrank);
                                    customDialog.cancel();
                                }
                            });
                        }else{
                            Toast.makeText(mContext,getString(R.string.buttonnorip),Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                customDialog.show();
            }
        });
        gumus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog customDialog = new Dialog(mContext);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.customdialog);
                TextView question = (TextView) customDialog.findViewById(R.id.tv_dialogque);
                final Button yes = (Button) customDialog.findViewById(R.id.btn_diayes);

                question.setText(mContext.getString(R.string.sansgumus) +" "+mContext.getString(R.string.wanttry));
                yes.setText(mContext.getString(R.string.yes));

                Query query = FirebaseDatabase.getInstance().getReference();
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                        final User userinfo = mFirebaseMethods.getUsersInfo(dataSnapshot);
                        if( userinfo.getRip()>1){
                            yes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ArrayList<Box> boxin = mFirebaseMethods.getBoxesInfo(dataSnapshot);
                                    Box box = null;
                                    for(int i=0;i<boxin.size();i++){
                                        if(boxin.get(i).getType().equals(mContext.getString(R.string.field_gumus))){
                                            box = boxin.get(i);
                                            break;
                                        }
                                    }
                                    long newRip = userinfo.getRip();
                                    newRip--;
                                    mFirebaseMethods.changeRipCount(newRip);
                                    long winrank = box.getSans_kazan();
                                    long userrank = box.getSans_sira();
                                    long newBox = userinfo.getGumusbox();
                                    userrank++;
                                    if(userrank==winrank){
                                        userrank=0;
                                        newBox++;
                                        gumus.setText(mContext.getString(R.string.buttonwon));
                                        gumus.setBackgroundResource(R.drawable.btnwon);
                                        mFirebaseMethods.changeBoxCount(mContext.getString(R.string.field_gumus), newBox);
                                    }else {
                                        gumus.setText(mContext.getString(R.string.buttontryagain));
                                        gumus.setBackgroundResource(R.drawable.btnlost);
                                    }
                                    mFirebaseMethods.changeLuck(mContext.getString(R.string.field_gumus),userrank);
                                    customDialog.cancel();
                                }
                            });
                        }else{
                            Toast.makeText(mContext,getString(R.string.buttonnorip),Toast.LENGTH_SHORT).show();
                        };
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                customDialog.show();
            }
        });
        bronz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog customDialog = new Dialog(mContext);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.customdialog);
                TextView question = (TextView) customDialog.findViewById(R.id.tv_dialogque);
                final Button yes = (Button) customDialog.findViewById(R.id.btn_diayes);

                question.setText(mContext.getString(R.string.sansbronz) +" "+mContext.getString(R.string.wanttry));
                yes.setText(mContext.getString(R.string.yes));

                Query query = FirebaseDatabase.getInstance().getReference();
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                        final User userinfo = mFirebaseMethods.getUsersInfo(dataSnapshot);
                        if( userinfo.getRip()>1){
                            yes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ArrayList<Box> boxin = mFirebaseMethods.getBoxesInfo(dataSnapshot);
                                    Box box = null;
                                    for(int i=0;i<boxin.size();i++){
                                        if(boxin.get(i).getType().equals(mContext.getString(R.string.field_bronz))){
                                            box = boxin.get(i);
                                            break;
                                        }
                                    }
                                    long newRip = userinfo.getRip();
                                    newRip--;
                                    mFirebaseMethods.changeRipCount(newRip);
                                    long winrank = box.getSans_kazan();
                                    long userrank = box.getSans_sira();
                                    long newBox = userinfo.getBronzbox();
                                    userrank++;
                                    if(userrank==winrank){
                                        userrank=0;
                                        newBox++;
                                        bronz.setText(mContext.getString(R.string.buttonwon));
                                        bronz.setBackgroundResource(R.drawable.btnwon);
                                        mFirebaseMethods.changeBoxCount(mContext.getString(R.string.field_bronz), newBox);
                                    }else {
                                        bronz.setText(mContext.getString(R.string.buttontryagain));
                                        bronz.setBackgroundResource(R.drawable.btnlost);
                                    }
                                    mFirebaseMethods.changeLuck(mContext.getString(R.string.field_bronz),userrank);
                                    customDialog.cancel();
                                }
                            });
                        }else{
                            Toast.makeText(mContext,getString(R.string.buttonnorip),Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                customDialog.show();
            }
        });
    }
    /*private void sansdeneme(Box box, long boxCount) {
        long sira = box.getSans_sira();
        long kazan = box.getSans_kazan();
        long newRip = mUser.getRip();
        newRip--;
        sira++;
        if(newRip>=0){
            mFirebaseMethods.changeRipCount(newRip);
            if(sira==kazan){
                sira = 0;
                long newBox = boxCount;
                if (box.getType().equals(mContext.getString(R.string.field_altin))) {
                    altin.setText(mContext.getString(R.string.buttonwon));
                    altin.setBackgroundResource(R.drawable.wonclickbutton);
                }
                if (box.getType().equals(mContext.getString(R.string.field_gumus))) {
                    gumus.setText(mContext.getString(R.string.buttonwon));
                    gumus.setBackgroundResource(R.drawable.wonclickbutton);
                }
                if (box.getType().equals(mContext.getString(R.string.field_bronz))) {
                    bronz.setText(mContext.getString(R.string.buttonwon));
                    bronz.setBackgroundResource(R.drawable.wonclickbutton);
                }
                newBox++;
                mFirebaseMethods.changeBoxCount(box.getType(), newBox);
            }else{
                if (box.getType().equals(mContext.getString(R.string.field_altin))) {
                    altin.setText(mContext.getString(R.string.buttontryagain));
                    altin.setBackgroundResource(R.drawable.lostclickbutton);
                }
                if (box.getType().equals(mContext.getString(R.string.field_gumus))) {
                    gumus.setText(mContext.getString(R.string.buttontryagain));
                    gumus.setBackgroundResource(R.drawable.lostclickbutton);
                }
                if (box.getType().equals(mContext.getString(R.string.field_bronz))) {
                    bronz.setText(mContext.getString(R.string.buttontryagain));
                    bronz.setBackgroundResource(R.drawable.lostclickbutton);
                }
            }
            mFirebaseMethods.changeLuck(box.getType(), sira);
        }else{
            Toast.makeText(mContext,mContext.getString(R.string.buttonnorip),Toast.LENGTH_SHORT).show();
        }
    }*/
    /*private void readData(final FirebaseCallback firebaseCallback){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUser = mFirebaseMethods.getUsersInfo(dataSnapshot);
                boxes = mFirebaseMethods.getBoxesInfo(dataSnapshot);
                try {
                    firebaseCallback.userInfo(mUser,boxes);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mFirebaseDatabase.getReference().addValueEventListener(valueEventListener);
    }*/
    private void setupFirebaseAuth(){
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        if(mAuth.getCurrentUser() != null){
            userID = mAuth.getCurrentUser().getUid();
        }
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
                }

            }
        };

        /*readData(new FirebaseCallback() {
            @Override
            public void userInfo(User user, ArrayList<Box> boxes) throws Exception {
                mProgressBar.setVisibility(View.INVISIBLE);
                //getBoxes();
            }
        });*/
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
        void userInfo(User user, ArrayList<Box> boxes) throws Exception;
    }
}
