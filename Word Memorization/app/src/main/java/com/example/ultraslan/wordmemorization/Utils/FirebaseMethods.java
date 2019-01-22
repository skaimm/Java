package com.example.ultraslan.wordmemorization.Utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.ultraslan.wordmemorization.Model.User;
import com.example.ultraslan.wordmemorization.Model.Words;
import com.example.ultraslan.wordmemorization.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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

public class FirebaseMethods {

    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private Context mContext;
    private String userID;

    public FirebaseMethods(Context context){
        mAuth= FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        mContext= context;

        if(mAuth.getCurrentUser()!=null)
        {
            userID = mAuth.getCurrentUser().getUid();
        }

    }

    public boolean checkIfemailExists(String email,DataSnapshot dataSnapshot){
        User user = new User();

        for (DataSnapshot ds:dataSnapshot.child(userID).getChildren()){
            user.setEmail(ds.getValue(User.class).getEmail());

            if(user.getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }

    public void registerNewEmail(final String email,String password,final String username)
    {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            sendVerificationEmail();
                            userID = mAuth.getCurrentUser().getUid();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(mContext, mContext.getString(R.string.registration_emailveriefore),
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }

    public void sendVerificationEmail(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){

                    }else{
                        Toast.makeText(mContext,mContext.getString(R.string.registration_emailverification),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    public void addNewUser(String email , String name){
        User user = new User(userID, name, email);
        myRef.child(mContext.getString(R.string.dbname_users))
                .child(userID)
                .setValue(user);

    }

    public User getUser (DataSnapshot dataSnapshot){
        final User user = new User();
        for (DataSnapshot ds:dataSnapshot.getChildren()){

            if(ds.getKey().equals(mContext.getString(R.string.dbname_users))) {

                user.setName(ds.child(userID).getValue(User.class).getName());
                user.setEmail(ds.child(userID).getValue(User.class).getEmail());
                user.setUser_id(ds.child(userID).getValue(User.class).getUser_id());
            }

        }
        return user;
    }

    public int getWordCount(DataSnapshot dataSnapshot) {
        int count = 0;
        for (DataSnapshot ds: dataSnapshot.child(mContext.getString(R.string.dbname_user_words))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getChildren()){
            count++;
        }
        return count;
    }

    public void uploadWordafterStudy(String key,int situation,String studydate){
        myRef.child(mContext.getString(R.string.dbname_user_words)).child(userID).child(key)
                .child(mContext.getString(R.string.field_wordsituation)).setValue(situation);
        if(studydate != null)
        myRef.child(mContext.getString(R.string.dbname_user_words)).child(userID).child(key)
                .child(mContext.getString(R.string.field_worddate)).setValue(studydate);

    }
    public void uploadWords(String key,boolean oldNew) {
        myRef.child(mContext.getString(R.string.dbname_user_words)).child(userID).child(key)
                .child(mContext.getString(R.string.field_wordoldnew)).setValue(oldNew);
    }

    public void loadNewWord(final ArrayList<Words> list){
        final Query query = myRef.child(mContext.getString(R.string.dbname_user_words)).child(userID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (Words words:list){
                    int i=0;
                    for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                        if(singleSnapshot.getValue(Words.class).getWord_id().equals(words.getWord_id())){
                            i++;
                            break;
                        }
                    }
                    if(i==0){
                        myRef.child(mContext.getString(R.string.dbname_user_words)).child(userID)
                                .child(words.getWord_id()).setValue(words);
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void createNewWord(String mWord, String mMean1, String mMean2, String mMean3,
                              String mMean4, String mMean5) {
        String newWordKey = myRef.child(mContext.getString(R.string.dbname_user_words)).push().getKey();
        Words words = new Words();
        words.setWord(mWord);
        words.setMean1(mMean1);
        words.setMean2(mMean2);
        words.setMean3(mMean3);
        words.setMean4(mMean4);
        words.setMean5(mMean5);
        words.setWord_path(null);
        words.setLevel("addingwords");
        words.setPronuncation(null);
        words.setUser_id(FirebaseAuth.getInstance().getCurrentUser().getUid());
        words.setWord_id(newWordKey);
        words.setSituation(0);
        words.setIsitnew(true);


        myRef.child(mContext.getString(R.string.dbname_user_words)).child(userID)
                .child(newWordKey).setValue(words);
    }
}

