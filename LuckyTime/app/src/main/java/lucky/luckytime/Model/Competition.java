package lucky.luckytime.Model;


import java.io.Serializable;
import java.util.ArrayList;

public class Competition implements Serializable {
    private ArrayList<Prize> prizes;
    private User user;
    private RafflePoint rafflePoint;

    public Competition(ArrayList<Prize> prizes, User user, RafflePoint rafflePoint) {
        this.prizes = prizes;
        this.user = user;
        this.rafflePoint = rafflePoint;
    }

    public Competition() {
    }

    public RafflePoint getRafflePoint() {
        return rafflePoint;
    }

    public void setRafflePoint(RafflePoint rafflePoint) {
        this.rafflePoint = rafflePoint;
    }

    public ArrayList<Prize> getPrizes() {
        return prizes;
    }

    public void setPrizes(ArrayList<Prize> prizes) {
        this.prizes = prizes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
