package com.example.ultraslan.football_manager;

/**
 * Created by ultrAslan on 9.05.2018.
 */

public abstract class Sports_Club {

    private String name;
    private String location;
    private String statistics;

    @Override
    public boolean equals(Object o) {
        return this.name.equals(((Sports_Club)o).name);
    }
    public String get_Name() {
        return name;
    }
    public String get_Location() {
        return location;
    }
    public String set_Statistics() {
        return statistics;
    }
    public void set_Name(String s) {
        this.name=s;
    }
    public void set_Location(String s) {
        this.location=s;
    }
    public void set_Statistics(String s) {
        this.statistics=s;
    }

    public String toString(){
        return name;
    }
}
