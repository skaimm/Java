package com.example.ultraslan.football_manager;

/**
 * Created by ultrAslan on 9.05.2018.
 */

public interface League_Manager {

    void add_League();
    void add_League(String name, int c_of_club);
    void add_Team(int position);
    void add_Team(String name, String location, League league);
    void delete_Team(Football_Club club,League league);
    void display_Statistics(Football_Club club,int position);
    void display_League_Table(League league);
    void display_Calendar(League league);
    void add_Played_Match(League league,int home,int away);
    void add_Played_Match(League league,Football_Club home,int home_score,Football_Club away,int away_score);

}
