package lucky.luckytime.LoginRegister;

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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import lucky.luckytime.R;
import lucky.luckytime.Utils.ConnectionDetector;
import lucky.luckytime.Utils.FirebaseMethods;

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
    private TextView giz,kul,res;

    private EditText mEmail,mPassword,mUsername;
    private Button btnRegister;
    private TextView sart;
    private CheckBox onay;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.register_layout);
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
        giz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,SozlesmeActivity.class);
                intent.putExtra("title",mContext.getString(R.string.gizlilik));
                intent.putExtra("context",mContext.getString(R.string.gizlilikcontext));
                startActivity(intent);
            }
        });

        res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,SozlesmeActivity.class);
                intent.putExtra("title",mContext.getString(R.string.resmi));
                intent.putExtra("context",mContext.getString(R.string.resmicontext));
                startActivity(intent);

            }
        });

        kul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,SozlesmeActivity.class);
                intent.putExtra("title",mContext.getString(R.string.kullanim));
                intent.putExtra("context",mContext.getString(R.string.kullanimcontext));
                startActivity(intent);

            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = mEmail.getText().toString();
                username = mUsername.getText().toString();
                password = mPassword.getText().toString();

                if(checkInputs(email, username, password)){
                    /*mProgressBar.setVisibility(View.VISIBLE);
                    loadingPleaseWait.setVisibility(View.VISIBLE);*/
                    if(onay.isChecked()){
                        firebaseMethods.registerNewEmail(email, password, username);
                        mAuth.signOut();
                    }else {
                        Toast.makeText(mContext,mContext.getString(R.string.acceptregister),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    private boolean checkInputs(String email, String username, String password){
        if(email.equals("") || username.equals("") || password.equals("")){
            Toast.makeText(mContext,getString(R.string.loginactivity_emptyfield), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void initWidgets(){
        mEmail = (EditText) findViewById(R.id.input_email);
        mUsername = (EditText) findViewById(R.id.input_fullname);
        mPassword = (EditText) findViewById(R.id.input_password);
        btnRegister = (Button) findViewById(R.id.btn_register);
        giz = (TextView) findViewById(R.id.gizlilik);
        kul = (TextView) findViewById(R.id.kullanim);
        res = (TextView) findViewById(R.id.resmi);
        mContext = RegisterActivity.this;
        sart = (TextView) findViewById(R.id.kullanim);
        onay = (CheckBox) findViewById(R.id.checkBox);
    }

    private boolean isStringNull(String string){
        if(string.equals("")){
            return true;
        }
        else{
            return false;
        }
    }

     /*
    ------------------------------------ Firebase ---------------------------------------------
     */

    private void setupFirebaseAuth(){
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull final FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // User is signed in
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            firebaseMethods.addNewUser(username,email);
                            mAuth.signOut();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    finish();

                } else {
                    // User is signed out
                }
                // ...
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
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
