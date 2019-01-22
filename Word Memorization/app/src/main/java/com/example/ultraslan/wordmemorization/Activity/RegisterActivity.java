package com.example.ultraslan.wordmemorization.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ultraslan.wordmemorization.R;
import com.example.ultraslan.wordmemorization.Utils.ConnectionDetector;
import com.example.ultraslan.wordmemorization.Utils.FirebaseMethods;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseMethods firebaseMethods;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private ConnectionDetector connectionDetector;

    private String append = "";

    private Context mContext;
    private String email,password,username;

    private EditText mEmail,mPassword,mUsername;
    private Button btnRegister;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mContext=RegisterActivity.this;
       firebaseMethods=new FirebaseMethods(mContext);

        control();
    }

    private void control(){
        connectionDetector = new ConnectionDetector(mContext);

        if(connectionDetector.isConnected()){
            initWidgets();
            setupFirebaseAuth();
            init();
        }
        else{
            Toast.makeText(mContext,getString(R.string.controlconnection ),Toast.LENGTH_SHORT).show();
            finish();
        }
    }


    private void init(){
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = memail.getText().toString();
                password = mpassword.getText().toString();
                username = musername.getText().toString();

                if(checkinputs(email,username,password)){
                    firebaseMethods.registerNewEmail(email,password,username);

                }
            }
        });
    }

    private boolean checkinputs(String email, String username, String password){
        if(email.equals("") || username.equals("") || password.equals("") )
        {
            Toast.makeText(mContext,R.string.loginactivity_emptyfield,Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    private boolean isStringNull(String string){
        if(string.equals("")){
            return true;
        }
        else
            return false;
    }
    private void setupFirebaseAuth(){
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull final FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user !=null)
                {
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(!firebaseMethods.checkIfemailExists(email,dataSnapshot)){
                                firebaseMethods.addNewUser(email,username);
                                Toast.makeText(mContext,R.string.registrationactivity_success,Toast.LENGTH_SHORT).show();
                                mAuth.signOut();
                            }
                            else
                            {
                                Toast.makeText(mContext,R.string.registrationactivity_sameemail,Toast.LENGTH_SHORT).show();
                            }
                            }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    finish();
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
        if(mAuthListener!=null)
            mAuth.removeAuthStateListener(mAuthListener);
    }
}
