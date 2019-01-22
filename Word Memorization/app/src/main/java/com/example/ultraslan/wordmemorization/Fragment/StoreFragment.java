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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.example.ultraslan.wordmemorization.Model.StoreProducts;
import com.example.ultraslan.wordmemorization.Model.Words;
import com.example.ultraslan.wordmemorization.R;
import com.example.ultraslan.wordmemorization.Utils.FirebaseMethods;
import com.example.ultraslan.wordmemorization.Utils.ListViewStoreCustom;
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

import java.util.ArrayList;

public class StoreFragment extends Fragment implements BillingProcessor.IBillingHandler{

    private Context mContext;
    private ListView lvProduct;
    private Button back;
    private ListViewStoreCustom adapter;
    private ArrayList<StoreProducts> mProductList;
    private AdView mAdView;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseMethods mFirebaseMethod;
    private DatabaseReference myRef;
    private String userID;

    private BillingProcessor bp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store,container,false);

        lvProduct = (ListView)view.findViewById(R.id.lv_store);
        back = (Button) view.findViewById(R.id.btn_storeback);
        mProductList = new ArrayList<StoreProducts>();
        mContext = getActivity();
        mFirebaseMethod = new FirebaseMethods(mContext);

        Bundle bundleObject = getActivity().getIntent().getExtras();
        mProductList = (ArrayList<StoreProducts>) bundleObject.getSerializable("listbox");

       // bp = new BillingProcessor(mContext,"license code",this);
        bp = new BillingProcessor(getActivity(),null,this);

        setupFirebaseAuth();
        define();
        setupListView();

        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        return view;
    }

    public void setupListView(){

        adapter = new ListViewStoreCustom(mContext,mProductList);
        lvProduct.setAdapter(adapter);

        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(mProductList.get(i).getDescription().equals("A1 English Words")){
                    final ArrayList<Words> tempwords = new ArrayList<>();
                    final Query query = myRef.child(getString(R.string.dbname_words)).child(getString(R.string.field_ofwords));
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                                    tempwords.add(singleSnapshot.getValue(Words.class));

                            }
                            mFirebaseMethod.loadNewWord(tempwords);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }else {
                    mProductList.get(i).getId();
                    bp.purchase(getActivity(),String.valueOf(mProductList.get(i).getId()));
                }
            }
        });
    }

    private String StringManipaliton(String string){
        return string.replace(" " , ".");
    }

    private void define() {
        back.setOnClickListener(buttonClickListener);
    }

    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_storeback:
                    getActivity().finish();
                    break;
            }
        }
    };


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

    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
        // odendikten sonraki islemler
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(!bp.handleActivityResult(requestCode,resultCode,data))
            super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {

    }

    @Override
    public void onBillingInitialized() {

    }

    @Override
    public void onDestroy() {
        if(bp!=null)
            bp.release();

        super.onDestroy();
    }
}
