package com.example.ultraslan.wordmemorization.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ultraslan.wordmemorization.R;
import com.example.ultraslan.wordmemorization.Utils.ConnectionDetector;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private Context mContext;
    private EditText memail,mpassword;
    private ConnectionDetector connectionDetector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        memail = (EditText) findViewById(R.id.input_email);
        mpassword = (EditText) findViewById(R.id.input_password);
        mContext = LoginActivity.this;

        control();
    }

    private void control(){
        connectionDetector = new ConnectionDetector(mContext);

        if(connectionDetector.isConnected()) {
            setupFirebaseAuth();
            init();
        }
        else{
            Toast.makeText(mContext,getString(R.string.controlconnection ),Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private boolean isStringNull(String string){
        if(string.equals("")){
            return true;
        }
        else
            return false;
    }


    private void init(){
        Button btnlogin = (Button) findViewById(R.id.btn_login);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = memail.getText().toString();
                String password = mpassword.getText().toString();

                if(isStringNull(email) && isStringNull(password))
                    Toast.makeText(mContext,getString(R.string.loginactivity_emptyfield),Toast.LENGTH_LONG).show();
                else {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    if (task.isSuccessful()) {
                                        try{
                                            if(user.isEmailVerified()){
                                                Intent intent = new Intent(LoginActivity.this,StartActivity.class);
                                                startActivity(intent);
                                            }else {
                                                Toast.makeText(mContext,(R.string.loginactivity_emailverification),Toast.LENGTH_SHORT).show();
                                                mAuth.signOut();
                                            }
                                        }catch (NullPointerException e){
                                        }
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(mContext, R.string.loginactivity_authfailed,
                                                Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });
                }
            }
        });

        TextView linkSignUp = (TextView) findViewById(R.id.link_signup);
        linkSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        if(mAuth.getCurrentUser() !=null){
            Intent intent = new Intent (LoginActivity.this, StartActivity.class);
            startActivity(intent);
            finish();
        }
    }


    private void setupFirebaseAuth(){
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user !=null)
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
        if(mAuthListener!=null)
            mAuth.removeAuthStateListener(mAuthListener);
    }
}
