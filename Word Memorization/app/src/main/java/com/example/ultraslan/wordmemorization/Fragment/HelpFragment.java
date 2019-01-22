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

import com.example.ultraslan.wordmemorization.R;

public class HelpFragment extends Fragment {


    private Context mContext;
    private Button back;
    private TextView explaination;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help,container,false);

        mContext = getActivity();
        back = (Button) view.findViewById(R.id.btn_helpback);
        explaination = (TextView) view.findViewById(R.id.tv_howtoplay);

        define();

        return view;
    }

    void define() {
        back.setOnClickListener(buttonClickListener);
    }

    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_helpback:
                    getActivity().finish();
                    break;
            }
        }
    };
}
