package com.example.ultraslan.wordmemorization.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ultraslan.wordmemorization.Activity.AddWordActivity;
import com.example.ultraslan.wordmemorization.Model.Words;
import com.example.ultraslan.wordmemorization.R;
import com.example.ultraslan.wordmemorization.Utils.FirebaseMethods;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyWordFragment extends Fragment {

    private Context mContext;
    private Button back,add;
    private ListView wordlistview;
    private TextView info;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseMethods mfirebaseMethods;
    private DatabaseReference myRef;
    private String userID;
    private ArrayList<Words> wordbox;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myword,container,false);

        mContext = getActivity();

        mfirebaseMethods = new FirebaseMethods(mContext);

        info = (TextView) view.findViewById(R.id.tv_infowordrep);
        back = (Button) view.findViewById(R.id.btn_myback);
        add = (Button) view.findViewById(R.id.btn_addword);
        wordlistview = (ListView) view.findViewById(R.id.lv_words);

        Bundle bundleObject = getActivity().getIntent().getExtras();
        wordbox = (ArrayList<Words>) bundleObject.getSerializable("listbox");

        define();
        setupFirebaseAuth();
        setupListView();

        return view;
    }

    private void define() {
        back.setOnClickListener(buttonClickListener);
        add.setOnClickListener(buttonClickListener);
    }

    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_myback:
                    getActivity().finish();
                    break;
                case R.id.btn_addword:
                    Intent intent = new Intent(mContext, AddWordActivity.class);
                    mContext.startActivity(intent);
                    break;
            }
        }
    };

    private void setupListView(){

        ArrayAdapter<Words> adapter = new ArrayAdapter<Words>(mContext,android.R.layout.simple_list_item_1,wordbox);
        wordlistview.setAdapter(adapter);
        info.setText( wordbox.size()+" " + getActivity().getString(R.string.mywordfrag_infoword));


        wordlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
                AlertDialog.Builder altdial = new AlertDialog.Builder(mContext);
                altdial.setTitle(getString(R.string.mywordfrag_eklecikarqtitle)).setMessage( wordbox.get(position).toString() + " " + getString(R.string.mywordfrag_alertwordquestion ) )
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.mywordfrag_deletewordqansweradd ), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String key = wordbox.get(position).getWord_id();
                                mfirebaseMethods.uploadWords(key,true);
                            }})
                        .setNegativeButton(getString(R.string.mywordfrag_deletewordqanswermin ), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String key = wordbox.get(position).getWord_id();
                                mfirebaseMethods.uploadWords(key,false);
                            }
                        });

                AlertDialog alert = altdial.create();
                alert.show();
            }
        });
        wordlistview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int position, long l) {

                AlertDialog.Builder altdial = new AlertDialog.Builder(mContext);
                altdial.setTitle(getString(R.string.mywordfrag_alertdeleteqtitle)).setMessage( wordbox.get(position).toString() + " " + getString(R.string.mywordfrag_alertdelete ) )
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.mywordfrag_deletewordqansweryes ), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String key = wordbox.get(position).getWord_id();
                                DatabaseReference removeData = myRef.child(mContext.getString(R.string.dbname_user_words))
                                        .child(userID).child(key);
                                removeData.removeValue();
                                wordbox.remove(position);
                                setupListView();
                            }})
                        .setNegativeButton(getString(R.string.mywordfrag_deletewordqanswercan ), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                AlertDialog alert = altdial.create();
                alert.show();
                return true;
            }
        });

    }

    private void setupFirebaseAuth(){
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        userID = mAuth.getCurrentUser().getUid();


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
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        setupListView();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mAuthListener!= null)
        {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
