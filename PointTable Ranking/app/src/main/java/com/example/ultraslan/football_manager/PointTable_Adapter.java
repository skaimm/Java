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
 * Created by ultrAslan on 10.05.2018.
 */

public class PointTable_Adapter extends ArrayAdapter<Football_Club> {

    private Context mContext;
    int mResource;
    public PointTable_Adapter(@NonNull Context context, int resource, @NonNull List<Football_Club> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        String name = getItem(position).get_Name();
        int matched_played = getItem(position).get_MatchesPlayed();
        int win = getItem(position).get_WinCount();
        int draw = getItem(position).get_DrawCount();
        int lost = getItem(position).get_DefeatCount();
        int scored = getItem(position).get_ScoredGoalsCount();
        int received = getItem(position).get_ReceivedGoalsCount();
        int point = getItem(position).get_Points();
        int avr = scored - received;

        Football_Club club = new Football_Club();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView club_name = (TextView) convertView.findViewById(R.id.textView9);
        TextView club_match = (TextView) convertView.findViewById(R.id.textView);
        TextView club_win = (TextView) convertView.findViewById(R.id.textView2);
        TextView club_draw = (TextView) convertView.findViewById(R.id.textView3);
        TextView club_lost = (TextView) convertView.findViewById(R.id.textView4);
        TextView club_score = (TextView) convertView.findViewById(R.id.textView5);
        TextView club_rece = (TextView) convertView.findViewById(R.id.textView6);
        TextView club_point = (TextView) convertView.findViewById(R.id.textView7);
        TextView club_avr = (TextView) convertView.findViewById(R.id.textView8);

        club_name.setText(name);
        club_match.setText(String.valueOf(matched_played));
        club_win.setText(String.valueOf(win));
        club_draw.setText(String.valueOf(draw));
        club_lost.setText(String.valueOf(lost));
        club_score.setText(String.valueOf(scored));
        club_rece.setText(String.valueOf(received));
        club_point.setText(String.valueOf(point));
        club_avr.setText(String.valueOf(avr));

        return convertView;
    }
}
