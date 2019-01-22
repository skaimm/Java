package com.example.ultraslan.football_manager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ultrAslan on 12.05.2018.
 */

public class DisplayMatch_Adapter extends ArrayAdapter<Match>{

    private Context mContext;
    int mResource;
    public DisplayMatch_Adapter(@NonNull Context context, int resource, @NonNull List<Match> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        String match_date = getItem(position).get_Date();
        Football_Club home = getItem(position).get_TeamA();
        Football_Club away = getItem(position).get_TeamB();
        int home_Score = getItem(position).get_TeamAScore();
        int away_Score = getItem(position).get_TeamBScore();

        Match match = new Match();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView date = (TextView) convertView.findViewById(R.id.tvshowdate);
        TextView homename = (TextView) convertView.findViewById(R.id.tvshowhomename);
        TextView homescore = (TextView) convertView.findViewById(R.id.tvshowhomescr);
        TextView awayscore = (TextView) convertView.findViewById(R.id.tvshowawaycsr);
        TextView awayname = (TextView) convertView.findViewById(R.id.tvshowawayname);
        TextView club_score = (TextView) convertView.findViewById(R.id.textView5);

        date.setText(match_date);
        homename.setText(String.valueOf(home));
        homescore.setText(String.valueOf(home_Score));
        awayname.setText(String.valueOf(away));
        awayscore.setText(String.valueOf(away_Score));

        return convertView;
    }
}
