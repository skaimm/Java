package lucky.luckytime.LoginRegister;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import lucky.luckytime.GainPrize;
import lucky.luckytime.R;
import lucky.luckytime.Utils.ConnectionDetector;
import lucky.luckytime.WelcomeActivity;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private Context mContext;
    private EditText memail,mpassword;
    private ConnectionDetector connectionDetector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login_layout);
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
            //loginfb();
        }
        else{
            Toast.makeText(mContext,getString(R.string.controlconnection ),Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void loginfb() {/*
        LoginButton loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("email"));
        //loginButton.setReadPermissions("email");

        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
               *//* GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                app.setUser(object);
                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender");
                request.setParameters(parameters);
                request.executeAsync();*//*
            }

            @Override
            public void onCancel() {
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                // ...
            }
        });*/
    }


    /*private void handleFacebookAccessToken(AccessToken token) {
        *//*AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Query query = FirebaseDatabase.getInstance().getReference().child("users");
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(!dataSnapshot.child(mAuth.getCurrentUser().getUid()).exists()){
                                        FirebaseMethods firebaseMethods = new FirebaseMethods(mContext);
                                        firebaseMethods.addNewUser(mAuth.getCurrentUser().getDisplayName(),mAuth.getCurrentUser().getEmail());
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            if(mAuth.getCurrentUser() !=null){
                                Intent intent = new Intent (LoginActivity.this, GainPrize.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }
                });*//*
    }*/

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
                                                Toast.makeText(mContext, R.string.loginactivity_authsucces,
                                                        Toast.LENGTH_SHORT).show();
                                                if(mAuth.getCurrentUser() !=null){
                                                    Intent intent = new Intent (LoginActivity.this, WelcomeActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            }else{
                                                Toast.makeText(mContext,mContext.getString(R.string.emailonayinfo),
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }catch (NullPointerException x){
                                            mAuth.signOut();
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

        Button linkSignUp = (Button) findViewById(R.id.link_signup);
        linkSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }*/
}
