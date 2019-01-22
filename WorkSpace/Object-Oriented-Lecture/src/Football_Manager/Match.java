package Football_Manager;

import java.util.Date;

public class Match {
	
	private Football_Club team_A;
	private Football_Club team_B;
	private int team_A_Score;
	private int team_B_Score;
	private Date date;
	
	public Football_Club get_TeamA() {
		return team_A;
	}
	public Football_Club get_TeamB() {
		return team_B;
	}
    public int get_TeamAScore() {
    	return team_A_Score;
    }
    public int get_TeamBScore() {
    	return team_B_Score;
    }
    public Date get_Date() {
    	return date;
    }
    public void set_TeamA(Football_Club f) {
    	this.team_A=f;
    }
    public void set_TeamB(Football_Club f) {
    	this.team_B=f;
    }
    public void set_TeamAScore(int i) {
    	this.team_A_Score=i;
    }
    public void set_TeamBScore(int i) {
    	this.team_B_Score=i;
    }
    public void set_Date(Date d) {
    	this.date=d;
    }

}
