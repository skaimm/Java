package com.example.ultraslan.football_manager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ultrAslan on 9.05.2018.
 */

public class League {

    private final String name;
    private final int number_Of_Clubs;
    private final ArrayList<Football_Club> league;
    private final ArrayList<Match> matches;

    public League (String league_name,int number_Of_Clubs) {
        this.number_Of_Clubs=number_Of_Clubs;
        name = league_name;
        league = new ArrayList<>();
        matches = new ArrayList<>();
    }

    public List<Match> get_matches(){
        return matches;
    }

    public List<Football_Club> get_leaque(){
        return league;
    }
    public int getNumber_Of_Clubs(){return number_Of_Clubs;}

    public int get_leaque_size(){
        return league.size();
    }
    public String toString(){
        return name;
    }



    /*
    @Override
    public void add_Team(String name, String Location) {
        if(league.size()==number_Of_Clubs) {
        }
        Football_Club club = new Football_Club();
        club.set_Name("name");
        club.set_Location("location");
        league.add(club);
    }

    @Override
    public void delete_Team() {
        System.out.println("Insert Club Name: ");
        String line = scanner.nextLine();
        for(Football_Club club: league) {
            if(club.get_Name().equals(line)) {
                league.remove(club);
                System.out.println("Club " + club.get_Name() +" removed.");
                return;
            }
        }
        System.out.println("No such Club in League");
    }
    @Override
    public void display_Statistics() {
        System.out.println("Insert Club Name:");
        String line = scanner.nextLine();
        for(Football_Club club : league)
        {
            if(club.get_Name().equals(line))
            {
                System.out.println("Club " + club.get_Name() + " matches Won: " + club.get_WinCount());
                System.out.println("Club " + club.get_Name() + " matches Lost: " + club.get_DefeatCount());
                System.out.println("Club " + club.get_Name() + " matches Draw: " + club.get_DrawCount());
                System.out.println("Club " + club.get_Name() + " Scored Goals: " + club.get_ScoredGoalsCount());
                System.out.println("Club " + club.get_Name() + " Recived Goals: " + club.get_ReceivedGoalsCount());
                System.out.println("Club " + club.get_Name() + " Points: " + club.get_Points());
                System.out.println("Club " + club.get_Name() + " matches Played: " + club.get_MatchesPlayed());
                return;
            }
        }
        System.out.println("No such Club in League");
    }
    @Override
    public void display_League_Table() {
        Collections.sort(league, new Custom_Comparator());
        for (Football_Club club : league) {
            System.out.println("Club " + club.get_Name() + " Points: " + club.get_Points() + " Goal Difference : " + (club.get_ScoredGoalsCount()-club.get_ReceivedGoalsCount()));
        }
    }

    @Override
    public void add_Played_Match() {
        System.out.println("Enter Date (format mm-dd-yyyy):  ");
        String line = scanner.nextLine();
        Date date;
        try {
            date = new SimpleDateFormat( "MM-dd-yyyy").parse(line);
        }catch(ParseException ex) {
            System.out.println("You have to enter date in format mm-dd-yyyy");
            return;
        }
        System.out.println("Enter Home Team: ");
        line = scanner.nextLine();
        Football_Club home = null;
        for(Football_Club club : league) {
            if(club.get_Name().equals(line))
                home = club;
        }

        if(home == null) {
            System.out.println("No such Club in League");
            return;
        }
        System.out.println("Enter Away Team: ");
        line = scanner.nextLine();
        Football_Club away = null;
        for(Football_Club club : league) {
            if(club.get_Name().equals(line))
                away = club;
        }

        if(away == null) {
            System.out.println("No such Club in League");
            return;
        }

        System.out.println("Enter Home Team Goals: ");
        line = scanner.nextLine();
        int home_Goals=-1;
        try {
            home_Goals = Integer.parseInt(line);
        }catch(Exception e) {}
        if(home_Goals == -1)
        {
            System.out.println("You have to enter number of Goals");
            return;
        }

        System.out.println("Enter Away Team Goals: ");
        line = scanner.nextLine();
        int away_Goals=-1;
        try {
            away_Goals = Integer.parseInt(line);
        }catch(Exception e) {}
        if(away_Goals == -1)
        {
            System.out.println("You have to enter number of Goals");
            return;
        }

        Match match = new Match();
        match.set_Date(date);
        match.set_TeamA(home);
        match.set_TeamB(away);
        match.set_TeamAScore(home_Goals);
        match.set_TeamBScore(away_Goals);
        matches.add(match);

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

    @Override
    public void display_Calendar() {
        System.out.println("Enter Year: ");
        String line =scanner.nextLine();
        int yyyy = -7777;
        try {
            yyyy = Integer.parseInt(line);
        }catch(Exception e) {}

        if(yyyy == -7777)
        {
            System.out.println("You have to enter a year");
            return;
        }

        System.out.println("Enter Month: ");
        line =scanner.nextLine();
        int mm = 0;
        try {
            mm = Integer.parseInt(line);
        }catch(Exception e) {}

        if(mm == 0)
        {
            System.out.println("You have to enter a month");
            return;
        }

        String[] months = {
                "","January","February","March",
                "April","May","June","July",
                "August","September","October",
                "November","December"
        };

        int [] days = { 0,31,28,31,30,31,30,31,31,30,31,30,31};

        if(mm == 2 && is_LeapYear(yyyy)) days [mm] = 29;

        System.out.println("   " + months[mm] + " " + yyyy);
        System.out.println("S  M  Tu W  Th F  S");

        int d = day(mm, 1,yyyy);
        String space = "";
        for(int i=0 ; i<d ; i++)
            System.out.print("   ");
        for(int i=1 ; i<=days[mm] ; i++) {
            if(i<10)
                System.out.print(i + "  ");
            else
                System.out.print(i +" ");
            if (((i+d)%7==0) || (i==days[mm]))
                System.out.println();
        }

        System.out.println("Enter Day: ");
        line =scanner.nextLine();
        int dd = 0;
        try {
            dd = Integer.parseInt(line);
        }catch(Exception e) {}

        if(dd == 0 || days[mm] < dd)
        {
            System.out.println("You have to enter a day");
            return;
        }

        Calendar cal = Calendar.getInstance();
        cal.set(yyyy,mm-1,dd);
        for(Match m : matches) {
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(m.get_Date());
            if(cal.get(Calendar.YEAR)==cal2.get(Calendar.YEAR) || cal.get(Calendar.DAY_OF_YEAR)==cal2.get(Calendar.DAY_OF_YEAR)) {
                System.out.println(m.get_TeamA().get_Name()+ " " + m.get_TeamB().get_Name() + " : " + m.get_TeamAScore() + " - " + m.get_TeamBScore());
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
    }*/
}
