package Football_Manager;

public class Football_Club extends Sports_Club{
	
	private int win_Count;
	private int draw_Count;
	private int defeat_Count;
	private int scored_Goals_Count;
	private int received_Goals_Count;
	private int points;
	private int matches_Played;
	
	public int get_WinCount() {
		return win_Count;
	}
	public int get_DrawCount() {
		return draw_Count;
	}
	public int get_DefeatCount() {
		return defeat_Count;
	}
	public int get_ScoredGoalsCount() {
		return scored_Goals_Count;
	}
	public int get_ReceivedGoalsCount() {
		return received_Goals_Count;
	}
	public int get_Points() {
		return points;
	}
	public int get_MatchesPlayed() {
		return matches_Played;
	}
	public void set_WinCount(int i) {
		this.win_Count=i;
	}
	public void set_DrawCount(int i) {
		this.draw_Count=i;
	}
	public void set_DefeatCount(int i) {
		this.defeat_Count=i;
	}
	public void set_ScoredGoalsCount(int i) {
		this.scored_Goals_Count=i;
	}
	public void set_ReceivedGoalsCount(int i) {
		this.received_Goals_Count=i;
	}
	public void set_Points(int i) {
		this.points=i;
	}
	public void set_MatchesPlayed(int i) {
		this.matches_Played=i;
	}

}
