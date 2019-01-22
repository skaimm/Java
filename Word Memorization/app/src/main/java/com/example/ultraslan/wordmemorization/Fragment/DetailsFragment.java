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
import android.widget.TextView;

import com.example.ultraslan.wordmemorization.Model.Words;
import com.example.ultraslan.wordmemorization.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class DetailsFragment extends Fragment {


    private Context mContext;
    private Button back,study,d1,d2,d3,n2,n3,n7,n15,n30,n60,n120,n180,memorized;
    private AdView mAdView;
    ArrayList<Words> wordbox;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details,container,false);

        mContext = getActivity();

        back = (Button) view.findViewById(R.id.btn_back);
        study = (Button) view.findViewById(R.id.iv_studying);
        d1 = (Button) view.findViewById(R.id.iv_d1);
        d2 = (Button) view.findViewById(R.id.iv_d2);
        d3 = (Button) view.findViewById(R.id.iv_d3);
        n2 = (Button) view.findViewById(R.id.iv_n2);
        n3 = (Button) view.findViewById(R.id.iv_n3);
        n7 = (Button) view.findViewById(R.id.iv_n7);
        n15 = (Button) view.findViewById(R.id.iv_n15);
        n30 = (Button) view.findViewById(R.id.iv_n30);
        n60 = (Button) view.findViewById(R.id.iv_n60);
        n120 = (Button) view.findViewById(R.id.iv_n120);
        n180 = (Button) view.findViewById(R.id.iv_n180);
        memorized = (Button) view.findViewById(R.id.iv_memorized);

        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Bundle bundleObject = getActivity().getIntent().getExtras();
        wordbox = (ArrayList<Words>) bundleObject.getSerializable("wordlistbox");

        showTheInformation();

        return view;
    }

    private void showTheInformation(){
        int s=0,sd1=0,sd2=0,sd3=0,sn2=0,sn3=0,sn7=0,sn15=0,sn30=0,sn60=0,sn120=0,sn180=0,smemorized=0;
        for(Words word:wordbox) {
            if (word.getSituation() == 0)
                s++;
            if (word.getSituation() == 1)
                sd1++;
            if (word.getSituation() == 2)
                sd2++;
            if (word.getSituation() == 3)
                sd3++;
            if (word.getSituation() == 4)
                sn2++;
            if (word.getSituation() == 5)
                sn3++;
            if (word.getSituation() == 6)
                sn7++;
            if (word.getSituation() == 7)
                sn15++;
            if (word.getSituation() == 8)
                sn30++;
            if (word.getSituation() == 9)
                sn60++;
            if (word.getSituation() == 10)
                sn120++;
            if (word.getSituation() == 11)
                sn180++;
            if (word.getSituation() == 12)
                smemorized++;

        }
        study.setText(getString(R.string.detailfrag_wordsstudy) + "\n" + s +" "+ getString(R.string.mywordfrag_infoword) );
        d1.setText(getString(R.string.detailfrag_wordsdaily2) + "\n" + sd1 +" "+ getString(R.string.detailfrag_words) );
        d2.setText(getString(R.string.detailfrag_wordsdaily2) + "\n" + sd2 +" "+ getString(R.string.detailfrag_words) );
        d3.setText(getString(R.string.detailfrag_wordsdaily3) + "\n" + sd3 +" "+ getString(R.string.detailfrag_words) );
        n2.setText(getString(R.string.detailfrag_wordsdaily4) + "\n" + sn2 +" "+ getString(R.string.detailfrag_words) );
        n3.setText(getString(R.string.detailfrag_wordsdaily5) + "\n" + sn3 +" "+ getString(R.string.detailfrag_words) );
        n7.setText(getString(R.string.detailfrag_wordsdaily6) + "\n" + sn7 +" "+ getString(R.string.detailfrag_words) );
        n15.setText(getString(R.string.detailfrag_wordsdaily7) + "\n" + sn15 +" "+ getString(R.string.detailfrag_words) );
        n30.setText(getString(R.string.detailfrag_wordsdaily8) + "\n" + sn30 +" "+ getString(R.string.detailfrag_words) );
        n60.setText(getString(R.string.detailfrag_wordsdaily9) + "\n" + sn60 +" "+ getString(R.string.detailfrag_words) );
        n120.setText(getString(R.string.detailfrag_wordsdaily10) + "\n" + sn120 +" "+ getString(R.string.detailfrag_words) );
        n180.setText(getString(R.string.detailfrag_wordsdaily11) + "\n" + sn180 +" "+ getString(R.string.detailfrag_words) );
        memorized.setText(getString(R.string.detailfrag_wordsmemorized) + "\n" + smemorized +" "+ getString(R.string.detailfrag_words) );

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }

}
