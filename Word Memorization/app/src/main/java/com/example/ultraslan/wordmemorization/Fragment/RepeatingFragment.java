package com.example.ultraslan.wordmemorization.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ultraslan.wordmemorization.Activity.RepeatingActivity;
import com.example.ultraslan.wordmemorization.Model.Words;
import com.example.ultraslan.wordmemorization.R;
import com.example.ultraslan.wordmemorization.Utils.FirebaseMethods;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RepeatingFragment extends Fragment {


    private Context mContext;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseMethods mFirebaseMethods;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private String userID;

    private TextView infoCount,word;
    private EditText mean1,mean2,mean3,mean4,mean5;
    private Button next;

    ArrayList<Words> wordbox;
    private int wordCount = 0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repeating,container,false);
        mContext = getActivity();

        infoCount =(TextView) view.findViewById(R.id.tv_infowordrep);
        word =(TextView) view.findViewById(R.id.tv_englishword);
        mean1 =(EditText) view.findViewById(R.id.et_wordmean);
        mean2 =(EditText) view.findViewById(R.id.et_wordmean2);
        mean3 =(EditText) view.findViewById(R.id.et_wordmean3);
        mean4 =(EditText) view.findViewById(R.id.et_wordmean4);
        mean5 =(EditText) view.findViewById(R.id.et_wordmean5);

        next =(Button) view.findViewById(R.id.btn_nextrep);

        wordbox = new ArrayList<>();

        Bundle bundleObject = getActivity().getIntent().getExtras();
        wordbox = (ArrayList<Words>) bundleObject.getSerializable("wordlistbox");

        mFirebaseMethods = new FirebaseMethods(mContext);

        setupFirebaseAuth();
        setupStudyingtoPage();

        return view;
    }

    private void setupStudyingtoPage() {

        showTheWord();
        clickButton();
    }

    private void showTheWord() {
        if(wordCount==wordbox.size()){

            getActivity().finish();
            wordbox.clear();
            Toast.makeText(mContext,getString(R.string.repeatfrag_infoafterstudy),Toast.LENGTH_SHORT).show();
        }else {
            infoCount.setText((wordCount + 1) + " / " + wordbox.size());
            String mWord = wordbox.get(wordCount).getWord();
            word.setText(wordbox.get(wordCount).getWord());
        }
    }

    private void clickButton(){

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String answer1 = mean1.getText().toString();
                String answer2 = mean2.getText().toString();
                String answer3 = mean3.getText().toString();
                String answer4 = mean4.getText().toString();
                String answer5 = mean5.getText().toString();

                String correct1 = wordbox.get(wordCount).getMean1();
                String correct2 = wordbox.get(wordCount).getMean2();
                String correct3 = wordbox.get(wordCount).getMean3();
                String correct4 = wordbox.get(wordCount).getMean4();
                String correct5 = wordbox.get(wordCount).getMean5();

                ArrayList<String> answers = new ArrayList<>(),corrects = new ArrayList<>();
                answers.addAll(checkNullWord(answer1,answer2,answer3,answer4,answer5));
                corrects.addAll(checkNullWord(correct1,correct2,correct3,correct4,correct5));

                int correctanswer = 0;
                int wordanswer = corrects.size();
                for (int i=0; i<answers.size();i++){
                    for (int j=0;j<corrects.size();j++){
                        if(answers.get(i).equals(corrects.get(j))){
                            correctanswer++;
                        }
                    }
                }
                checkTheWord(correctanswer,wordanswer);

                wordCount++;
                showTheWord();
            }
        });
    }

    private void checkTheWord(int correctanswer, int wordanswer) {

        if(wordanswer==5){
            if(correctanswer>2){
                passToNextBox();
                Toast.makeText(mContext,getString(R.string.repeatfrag_password ),Toast.LENGTH_SHORT);
            }else {
                passToStudyBox();
                Toast.makeText(mContext,(R.string.repeatfrag_failword ),Toast.LENGTH_SHORT);
            }
        }
        else if(wordanswer==4 || wordanswer==3)
        {
            if(correctanswer>1){
                passToNextBox();
                Toast.makeText(mContext,getString(R.string.repeatfrag_password ),Toast.LENGTH_SHORT);
            }else {
                passToStudyBox();
                Toast.makeText(mContext,(R.string.repeatfrag_failword ),Toast.LENGTH_SHORT);
            }
        }else {
            if(correctanswer>0){
                passToNextBox();
                Toast.makeText(mContext,getString(R.string.repeatfrag_password ),Toast.LENGTH_SHORT);
            }else {
                passToStudyBox();
                Toast.makeText(mContext,(R.string.repeatfrag_failword ),Toast.LENGTH_SHORT);
            }
        }
    }

    private void passToStudyBox() {
        String key = wordbox.get(wordCount).getWord_id();
        int situation = 1;
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String today = dateFormat.format(date);
        mFirebaseMethods.uploadWordafterStudy(key,situation,today);
    }

    private void passToNextBox() {
        String key = wordbox.get(wordCount).getWord_id();
        int situation = wordbox.get(wordCount).getSituation();
        situation++;
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String today = dateFormat.format(date);
        mFirebaseMethods.uploadWordafterStudy(key,situation,today);
    }


    private ArrayList<String> checkNullWord(String a, String b, String c, String d, String e) {
        ArrayList<String> temp = new ArrayList<>();
        if(a != null)
            temp.add(a);
        if(b != null)
            temp.add(b);
        if(c != null)
            temp.add(c);
        if(d != null)
            temp.add(d);
        if(e != null)
            temp.add(e);

        return temp;
    }

    private int pickTheLowestCount(int dailycount, int wordcount) {
        if(wordcount<dailycount)
            return wordcount;
        return dailycount;
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
