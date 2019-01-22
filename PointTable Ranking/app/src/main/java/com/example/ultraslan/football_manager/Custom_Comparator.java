package com.example.ultraslan.football_manager;

import java.util.Comparator;

/**
 * Created by ultrAslan on 9.05.2018.
 */
public class Custom_Comparator implements Comparator<Football_Club> {

    @Override
    public int compare(Football_Club t1, Football_Club t2) {
        if(t1.get_Points()>t2.get_Points())
            return -1;
        else if(t1.get_Points()<t2.get_Points())
            return 1;
        else {
            int goal_Diff1 = t1.get_ScoredGoalsCount()-t1.get_ReceivedGoalsCount();
            int goal_Diff2 = t2.get_ScoredGoalsCount()-t2.get_ReceivedGoalsCount();
            if(goal_Diff1 > goal_Diff2)
                return -1;
            else
            if(goal_Diff1<goal_Diff2)
                return 1;
            else return 0;
        }
    }

}
