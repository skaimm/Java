package com.example.ultraslan.football_manager;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Football_Manager extends AppCompatActivity implements League_Manager{

    RelativeLayout frl;
    ConstraintLayout in_al,in_lp,in_ac,in_scd,in_am,in_dp;
    Button add_leaque,add_club,add_match,sadd_l,cadd_l, sadd_c, cadd_c,backshow_c, sadd_m,cadd_m,okay_date;
    EditText al_et_lname,al_et_coc,ac_et_cname,ac_et_cloca,am_et_hs,am_et_aw;
    TextView tv_cm,tv_mp,tv_w,tv_d,tv_l,tv_sg,tv_rg,tv_p,tv_ps,tv_dp;
    ListView point_table,display_matches;
    DatePicker datePicker;
    Date date;
    Spinner spinner_leaque,spinner_home_team,spinner_away_team;
    List<League> leagues;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_football__manager);

        leagues = new ArrayList<>();

        // cons layouts of all layouts
        frl = (findViewById(R.id.first_layout));
        in_lp = (findViewById(R.id.include_leaque_profile));
        in_al = (findViewById(R.id.include_add_leaque_layout));
        in_ac = (findViewById(R.id.include_add_club_layout));
        in_am = findViewById(R.id.include_add_matches_layout);
        in_scd = findViewById(R.id.include_show_club_details);
        in_dp = findViewById(R.id.include_date_layout);


        //legaue profile layout
        spinner_leaque = (findViewById(R.id.leagues_spinner));
        point_table = (findViewById(R.id.lv_pointtable));
        display_matches = findViewById(R.id.lv_showmatch);


        //add_league layout
        add_leaque = (findViewById(R.id.add_leaque));
        sadd_l = (findViewById(R.id.save_league));
        cadd_l = (findViewById(R.id.cancel_al));
        al_et_lname = (findViewById(R.id.et_league_name));
        al_et_coc = (findViewById(R.id.et_maxcountclub));

        //add_club layout
        add_club = (findViewById(R.id.add_clubs));
        sadd_c = findViewById(R.id.save_club);
        cadd_c = findViewById(R.id.cancel_ac);
        ac_et_cname = (findViewById(R.id.et_club_name));
        ac_et_cloca = (findViewById(R.id.et_location));

        //add_match layout
        add_match = findViewById(R.id.add_match);
        sadd_m = findViewById(R.id.save_match);
        cadd_m = findViewById(R.id.cancel_am);
        am_et_hs = findViewById(R.id.et_homescore);
        am_et_aw = findViewById(R.id.et_away_score);
        datePicker = findViewById(R.id.datePicker);
        tv_dp = findViewById(R.id.tv_date);
        okay_date = findViewById(R.id.okay_button);
        spinner_home_team= findViewById(R.id.home_spinner_am);
        spinner_away_team=findViewById(R.id.away_spinner_am);

        //show_club_detail layout
        backshow_c = findViewById(R.id.backhome);
        tv_cm = findViewById(R.id.tv_team_name);
        tv_mp = findViewById(R.id.tv_matchplayed);
        tv_w = findViewById(R.id.tv_win);
        tv_d = findViewById(R.id.tv_draw);
        tv_l = findViewById(R.id.tv_lost);
        tv_sg = findViewById(R.id.tv_scored);
        tv_rg = findViewById(R.id.tv_received);
        tv_p = findViewById(R.id.tv_points);
        tv_ps = findViewById(R.id.tv_position);


        tv_dp.setText("Choose Date For Match");
        create_league();


        final ArrayAdapter<League> adapter = new ArrayAdapter<League>(this,android.R.layout.simple_spinner_item,leagues);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_leaque.setAdapter(adapter);

        spinner_leaque.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int league_position, long id) {

                display_League_Table(leagues.get(league_position));

                point_table.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                layout_visibilty(in_scd);
                                display_Statistics(leagues.get(league_position).get_leaque().get(position),position);
                                display_Calendar(leagues.get(league_position));

                                backshow_c.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        layout_visibilty(in_lp);
                                    }
                                });


                    }
                });

                point_table.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        delete_Team(leagues.get(league_position).get_leaque().get(position),leagues.get(league_position));
                        display_League_Table(leagues.get(league_position));
                        return false;
                    }
                });


                    add_club.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            layout_visibilty(in_ac);

                            sadd_c.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    add_Team(league_position);

                                }
                            });
                            cadd_c.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    layout_visibilty(in_lp);
                                }
                            });
                        }
                    });

                add_match.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        layout_visibilty(in_am);

                        tv_dp.setOnClickListener(new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onClick(View v) {
                                layout_visibilty(in_dp);
                                datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                                    @Override
                                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                        StringBuilder date = new StringBuilder();
                                        date.append(year).append(".").append(monthOfYear+1).append(".").append(dayOfMonth);
                                        tv_dp.setText(date);
                                    }
                                });
                                okay_date.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        layout_visibilty(in_am);
                                    }
                                });
                            }
                        });

                        final ArrayAdapter<Football_Club> adapter = new ArrayAdapter<Football_Club>(getApplicationContext(),android.R.layout.simple_spinner_item,leagues.get(league_position).get_leaque());
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_home_team.setAdapter(adapter);
                        spinner_home_team.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, final int home_position, long id) {
                                spinner_away_team.setAdapter(adapter);
                                spinner_away_team.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, final int away_position, long id) {

                                        sadd_m.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (leagues.get(league_position).get_leaque().get(home_position).equals(leagues.get(league_position).get_leaque().get(away_position)))
                                                {
                                                    Toast.makeText(getApplicationContext(), "!!Change the Away of Team !!", Toast.LENGTH_SHORT).show();
                                                }
                                                else
                                                {
                                                    add_Played_Match(leagues.get(league_position),home_position,away_position);
                                                    layout_visibilty(in_lp);
                                                    am_et_aw.setText("");
                                                    am_et_hs.setText("");
                                                    tv_dp.setText("Choose Date For Match");
                                                }
                                            }
                                        });
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                        cadd_m.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                layout_visibilty(in_lp);
                            }
                        });
                    }
                });



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        add_leaque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_visibilty(in_al);

                sadd_l.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        add_League();
                    }
                });

                cadd_l.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        layout_visibilty(in_lp);
                    }
                });

            }
        });




    }

    public void layout_visibilty (ConstraintLayout x){
        if(in_al.equals(x)){
            in_lp.setVisibility(View.INVISIBLE);
            in_al.setVisibility(View.VISIBLE);
            in_ac.setVisibility(View.INVISIBLE);
            in_scd.setVisibility(View.INVISIBLE);
            in_am.setVisibility(View.INVISIBLE);
            in_dp.setVisibility(View.INVISIBLE);
        }
        else if(in_ac.equals(x)){
            in_lp.setVisibility(View.INVISIBLE);
            in_al.setVisibility(View.INVISIBLE);
            in_ac.setVisibility(View.VISIBLE);
            in_scd.setVisibility(View.INVISIBLE);
            in_am.setVisibility(View.INVISIBLE);
            in_dp.setVisibility(View.INVISIBLE);
        }
        else if(in_lp.equals(x)){
            in_lp.setVisibility(View.VISIBLE);
            in_al.setVisibility(View.INVISIBLE);
            in_ac.setVisibility(View.INVISIBLE);
            in_scd.setVisibility(View.INVISIBLE);
            in_am.setVisibility(View.INVISIBLE);
            in_dp.setVisibility(View.INVISIBLE);
            display_League_Table(leagues.get(spinner_leaque.getSelectedItemPosition()));
        }
        else if(in_scd.equals(x))
        {
            in_lp.setVisibility(View.INVISIBLE);
            in_al.setVisibility(View.INVISIBLE);
            in_ac.setVisibility(View.INVISIBLE);
            in_scd.setVisibility(View.VISIBLE);
            in_am.setVisibility(View.INVISIBLE);
            in_dp.setVisibility(View.INVISIBLE);
        }
        else if (in_am.equals(x))
        {
            in_lp.setVisibility(View.INVISIBLE);
            in_al.setVisibility(View.INVISIBLE);
            in_ac.setVisibility(View.INVISIBLE);
            in_scd.setVisibility(View.INVISIBLE);
            in_am.setVisibility(View.VISIBLE);
            in_dp.setVisibility(View.INVISIBLE);
        }
        else if(in_dp.equals(x))
        {
            in_lp.setVisibility(View.INVISIBLE);
            in_al.setVisibility(View.INVISIBLE);
            in_ac.setVisibility(View.INVISIBLE);
            in_scd.setVisibility(View.INVISIBLE);
            in_am.setVisibility(View.INVISIBLE);
            in_dp.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void add_League() {
        String name = al_et_lname.getText().toString();
        String scoc = al_et_coc.getText().toString();
        int coc = 0;
        if(name.isEmpty() || name.length()<1 || name.trim().length() == 0)
        {
            Toast.makeText(getApplicationContext(), "!!Check League Name!!", Toast.LENGTH_SHORT).show();

        }
        else
        {
            if(scoc.isEmpty() || 0>Integer.parseInt(scoc) ) {
                Toast.makeText(getApplicationContext(), "!!Maximum clubs Not be empty !!", Toast.LENGTH_SHORT).show();
            }
            else {
                coc = Integer.parseInt(scoc);
                add_League(name, coc);
                layout_visibilty(in_lp);
            }
        }
    }

    public void add_League(String name, int c_of_club) {

        League league = new League(name,c_of_club);
        if(leagues.contains(league)){
            Toast.makeText(getApplicationContext(), "!!This Club is already in the League !!", Toast.LENGTH_SHORT).show();
            return;
        }
        leagues.add(league);
        al_et_lname.setText("");
        al_et_coc.setText("");
    }

    public void add_Team(int position){
        String cname = ac_et_cname.getText().toString();
        String loca = ac_et_cloca.getText().toString();
        if(cname.isEmpty() || cname.length()<1 || cname.trim().length() == 0)
        {
            Toast.makeText(getApplicationContext(), "!!Check Club Name!!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(loca.isEmpty() || loca.length()<1 || loca.trim().length() == 0 ) {
                Toast.makeText(getApplicationContext(), "!!Check Location !!", Toast.LENGTH_SHORT).show();
            }
            else {

                add_Team(cname,loca,leagues.get(position));
                layout_visibilty(in_lp);
            }
        }
    }
    @Override
    public void add_Team(String name, String location , League league) {

        if(league.getNumber_Of_Clubs()==league.get_leaque_size()) {
            Toast.makeText(getApplicationContext(), "!!Can Not add more Clubs to League !!", Toast.LENGTH_SHORT).show();
            return;
        }
        Football_Club club = new Football_Club();
        club.set_Name(name);
        club.set_Location(location);
        if(league.get_leaque().contains(club)){
            Toast.makeText(getApplicationContext(), "!!This Club is already in the League !!", Toast.LENGTH_SHORT).show();
            return;
        }
        league.get_leaque().add(club);
        ac_et_cname.setText("");
        ac_et_cloca.setText("");
    }

    @Override
    public void delete_Team(Football_Club club, League league) {
        league.get_leaque().remove(club);
    }

    @Override
    public void display_Statistics(Football_Club club,int position) {

        tv_cm.setText(club.get_Name());
        tv_w.setText(String.valueOf(club.get_MatchesPlayed()));
        tv_d.setText(String.valueOf(club.get_DrawCount()));
        tv_l.setText(String.valueOf(club.get_DefeatCount()));
        tv_sg.setText(String.valueOf(club.get_ScoredGoalsCount()));
        tv_rg.setText(String.valueOf(club.get_ReceivedGoalsCount()));
        tv_p.setText(String.valueOf(club.get_Points()));
        tv_ps.setText(String.valueOf(position+1));
    }

    @Override
    public void display_League_Table(League league) {
        Collections.sort(league.get_leaque(),new Custom_Comparator());
        PointTable_Adapter adapter = new PointTable_Adapter(getApplicationContext(),R.layout.adapter_view_layout,league.get_leaque());
        point_table.setAdapter(adapter);

    }

    @Override
    public void display_Calendar(League league) {
        DisplayMatch_Adapter adapter = new DisplayMatch_Adapter(getApplicationContext(),R.layout.adapter_view_match,league.get_matches());
        display_matches.setAdapter(adapter);
    }

    @Override
    public void add_Played_Match(League league, int home,int away) {

        String home_Score = am_et_hs.getText().toString();
        String away_score = am_et_aw.getText().toString();
        int homesc=0,awaysc=0;
        if(home_Score.isEmpty() || 0>Integer.parseInt(home_Score) ) {
            Toast.makeText(getApplicationContext(), "!!Enter the Home Score !!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(away_score.isEmpty() || 0>Integer.parseInt(away_score))
            {
                Toast.makeText(getApplicationContext(), "!!Enter the Away Score !!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                if(tv_dp.getText().equals("Choose Date For Match"))
                    Toast.makeText(getApplicationContext(), "!!Enter the DATE !!", Toast.LENGTH_SHORT).show();
                else
                {
                    homesc = Integer.parseInt(home_Score);
                    awaysc = Integer.parseInt(away_score);
                    add_Played_Match(league,league.get_leaque().get(home),homesc,league.get_leaque().get(away),awaysc);
                }
            }
        }

    }

    @Override
    public void add_Played_Match(League league,Football_Club home,int home_Goals,Football_Club away,int away_Goals) {
        Match match = new Match();
        match.set_Date(tv_dp.getText().toString());
        match.set_TeamA(home);
        match.set_TeamB(away);
        match.set_TeamAScore(home_Goals);
        match.set_TeamBScore(away_Goals);
        league.get_matches().add(match);

        home.set_ScoredGoalsCount(home.get_ScoredGoalsCount()+home_Goals);
        away.set_ScoredGoalsCount(away.get_ScoredGoalsCount()+away_Goals);
        home.set_ReceivedGoalsCount(home.get_ReceivedGoalsCount()+away_Goals);
        away.set_ReceivedGoalsCount(away.get_ReceivedGoalsCount()+home_Goals);
        home.set_MatchesPlayed(home.get_MatchesPlayed()+1);
        away.set_MatchesPlayed(away.get_MatchesPlayed()+1);

        if(home_Goals > away_Goals) {

            home.set_Points(home.get_Points()+3);
            home.set_WinCount(home.get_WinCount()+1);
            away.set_DefeatCount(away.get_DefeatCount()+1);
        }
        else if(home_Goals < away_Goals) {

            away.set_Points(away.get_Points()+3);
            away.set_WinCount(away.get_WinCount()+1);
            home.set_DefeatCount(home.get_DefeatCount()+1);
        }
        else {
            home.set_Points(home.get_Points()+1);
            away.set_Points(away.get_Points()+1);
            home.set_DrawCount(home.get_DrawCount()+1);
            away.set_DrawCount(away.get_DrawCount()+1);
        }
    }

    public void create_league()
    {
        League TSL = new League("TURKISH SUPEAR LEAGUE",18);
        League PL = new League("PREMIERE LEAGUE",20);
        League LL = new League("LA_LIGA",20);

        leagues.add(PL);
        leagues.add(LL);
        leagues.add(TSL);

        List<String> tsl_club_name = new ArrayList<>(TSL.getNumber_Of_Clubs());
        tsl_club_name.add("Galatasaray");
        tsl_club_name.add("Fenerbahce");
        tsl_club_name.add("Besiktas");
        tsl_club_name.add("Bursaspor");
        tsl_club_name.add("Konyaspor");
        tsl_club_name.add("Trabzonspor");
        tsl_club_name.add("Basaksehir");
        tsl_club_name.add("Alanyaspor");
        tsl_club_name.add("Antalyaspor");
        tsl_club_name.add("Göztepe");
        tsl_club_name.add("Sivasspor");
        tsl_club_name.add("Kasımpasa");
        tsl_club_name.add("Kayserispor");
        tsl_club_name.add("Yeni Malatya");
        tsl_club_name.add("Akhisarspor");
        tsl_club_name.add("Osmanlıspor");
        tsl_club_name.add("Genclerbirligi");
        tsl_club_name.add("Karabukspor");

        List<String> pl_club_name = new ArrayList<>(PL.getNumber_Of_Clubs());
        pl_club_name.add("Manchester City");
        pl_club_name.add("Manchester United");
        pl_club_name.add("Tottenham");
        pl_club_name.add("Liverpool");
        pl_club_name.add("Chelsea");
        pl_club_name.add("Arsenal");
        pl_club_name.add("Burnley");
        pl_club_name.add("Everton");
        pl_club_name.add("Leicester");
        pl_club_name.add("Newcastle");
        pl_club_name.add("Crystal Palaca");
        pl_club_name.add("Bournemouth");
        pl_club_name.add("Watford");
        pl_club_name.add("Brighton & Hove");
        pl_club_name.add("West Ham");
        pl_club_name.add("Huddersfield");
        pl_club_name.add("Southampton");
        pl_club_name.add("Swensea");
        pl_club_name.add("West Brom");
        pl_club_name.add("Stoke City");

        List<String> ll_club_name = new ArrayList<>(LL.getNumber_Of_Clubs());
        ll_club_name.add("Barcelona");
        ll_club_name.add("Real Madrid");
        ll_club_name.add("Atletico Madrid");
        ll_club_name.add("Valencia");
        ll_club_name.add("Villarreal");
        ll_club_name.add("Real Betis");
        ll_club_name.add("Sevilla");
        ll_club_name.add("Getafe");
        ll_club_name.add("Eibar");
        ll_club_name.add("Real Sociedad");
        ll_club_name.add("Girona");
        ll_club_name.add("Alaves");
        ll_club_name.add("Celta Vigo");
        ll_club_name.add("Athletic Bilbao");
        ll_club_name.add("Espanyol");
        ll_club_name.add("Levante");
        ll_club_name.add("Leganes");
        ll_club_name.add("Deportivo");
        ll_club_name.add("Las Palmas");
        ll_club_name.add("Malaga");
        for (int i=0;i<TSL.getNumber_Of_Clubs();i++)
        {
            Football_Club club = new Football_Club();
            club.set_Name(tsl_club_name.get(i));
            club.set_Location("Turkey");
            TSL.get_leaque().add(club);
        }
        for (int i=0;i<TSL.getNumber_Of_Clubs();i++)
        {
            Football_Club club = new Football_Club();
            club.set_Name(pl_club_name.get(i));
            club.set_Location("England");
            PL.get_leaque().add(club);
        }
        for (int i=0;i<TSL.getNumber_Of_Clubs();i++)
        {
            Football_Club club = new Football_Club();
            club.set_Name(ll_club_name.get(i));
            club.set_Location("Spain");
            LL.get_leaque().add(club);
        }


        int [] days = { 0,31,28,31,30,31,30,31,31,30,31,30,31};

        for (int i=0;i<(TSL.get_leaque().size()) ; i++) {
            int year = 2017;
            int month = 6;
            int day = 10;

            for (int j = 0; j < TSL.get_leaque().size(); j++) {
                if(i==j) {}
                else
                {
                    Random rand = new Random();
                    int  team1s = rand.nextInt(5) + 0;
                    int  team2s = rand.nextInt(5) + 0;
                    day+=7;
                    if(day>days[month])
                    {
                        day= day-days[month];
                        month++;
                        if (month>12)
                        {
                            month=1;
                            year++;
                        }

                    }
                    tv_dp.setText(year + "." + month + "." + day);
                    add_Played_Match(TSL, TSL.get_leaque().get(i), team1s, TSL.get_leaque().get(j), team2s);
                }
            }
        }
        for (int i=0;i<(PL.get_leaque().size()) ; i++) {
            int year = 2017;
            int month = 6;
            int day = 10;
            for (int j = 0; j < PL.get_leaque().size(); j++) {
                if(i==j) {}
                else
                {
                    Random rand = new Random();
                    int  team1s = rand.nextInt(5) + 0;
                    int  team2s = rand.nextInt(5) + 0;
                    day+=7;
                    if(day>days[month])
                    {
                        day= day-days[month];
                        month++;
                        if (month>12)
                        {
                            month=1;
                            year++;
                        }

                    }
                    tv_dp.setText(year + "." + month + "." + day);
                    add_Played_Match(PL, PL.get_leaque().get(i), team1s, PL.get_leaque().get(j), team2s);
                }
            }
        }
        for (int i=0;i<(LL.get_leaque().size()) ; i++) {
            int year = 2017;
            int month = 6;
            int day = 10;
            for (int j = 0; j < LL.get_leaque().size(); j++) {
                if(i==j) {}
                else
                {
                    Random rand = new Random();
                    int  team1s = rand.nextInt(5) + 0;
                    int  team2s = rand.nextInt(5) + 0;
                    day+=7;
                    if(day>days[month])
                    {
                        day= day-days[month];
                        month++;
                        if (month>12)
                        {
                            month=1;
                            year++;
                        }

                    }
                    tv_dp.setText(year + "." + month + "." + day);
                    add_Played_Match(LL, LL.get_leaque().get(i), team1s, LL.get_leaque().get(j), team2s);
                }
            }
        }

    }

    public int day(int M, int D , int Y) {
        int y = Y - (14-M)/12;
        int x = y + y/4 - y/100 + y/400;
        int m = M + 12 * ((14-M) / 12)- 2;
        int d = (D + x + (31*m)/12) % 7;
        return d;
    }

    public boolean is_LeapYear(int year)
    {
        if((year% 4 == 0) && (year % 100!=0)) return true;
        if (year%400==0) return true;
        return false;
    }

}
