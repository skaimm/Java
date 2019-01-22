package com.example.ultraslan.wordmemorization.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.ultraslan.wordmemorization.Activity.TalepActivity;
import com.example.ultraslan.wordmemorization.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class TalepFragment extends Fragment {

    private Context mContext;
    private Button back, send;
    private EditText writing;
    private AdView mAdView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_talep,container,false);

        back = (Button) view.findViewById(R.id.btn_talepback);
        send = (Button) view.findViewById(R.id.btn_talepsend);
        writing = (EditText) view.findViewById(R.id.et_talepwriting);

        mContext = getActivity();

        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        define();

        return view;
    }

    private void define() {
        back.setOnClickListener(buttonClickListener);
        send.setOnClickListener(buttonClickListener);
    }

    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_talepback:
                    getActivity().finish();
                    break;
                case R.id.btn_talepsend:
                    sendmail();
                    break;

            }
        }
    };

    private void sendmail() {
        String message = writing.getText().toString().trim();
        String []to = {getString(R.string.talepfrag_email)};
        String subject = getString(R.string.talepfrag_subject);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL,to);
        intent.putExtra(Intent.EXTRA_SUBJECT,subject);
        intent.putExtra(Intent.EXTRA_TEXT,message);

        intent.setType("text/rfc822");
        startActivity(Intent.createChooser(intent,getString(R.string.talepfrag_emailclient)));
    }
}
